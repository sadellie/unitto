const CACHE_NAME = "unitto-wasm-cache-{VERSION}";

const PRECACHED_ASSETS = [/*__PRECACHED_ASSETS__*/]

self.addEventListener("install", (event) => {
  event.waitUntil(
    (async () => {
      const cache = await caches.open(CACHE_NAME);
      await cache.addAll(PRECACHED_ASSETS);
      // force activation
      await self.skipWaiting();
    })(),
  );
});

self.addEventListener("activate", (event) => {
  event.waitUntil(
    (async () => {
      const keys = await caches.keys();
      await Promise.all(
        keys
          .filter((key) => key !== CACHE_NAME)
          .map((key) => caches.delete(key)),
      );
      await self.clients.claim();
    })(),
  );
});

self.addEventListener("fetch", (event) => {
  // ignore non-GET requests
  if (event.request.method !== "GET") return;

  // ignore cross-origin requests
  if (!event.request.url.startsWith(self.location.origin)) return;

  event.respondWith(
    (async () => {
      // try cache first
      const cached = await caches.match(event.request);
      if (cached) return cached;

      // try update cache if not found
      try {
        const response = await fetch(event.request);
        if (response.ok && response.type === "basic") {
          // update cache
          const responseClone = response.clone();
          caches.open(CACHE_NAME).then((cache) => {
            cache.put(event.request, responseClone);
          });
        }

        return response;
      } catch (error) {
        if (event.request.mode === "navigate") {
          const fallback = await caches.match("/index.html");
          if (fallback) return fallback;
        }

        return new Response("Offline. Check your connection", {
          status: 503,
          statusText: "Unavailable",
          headers: { "Content-Type": "text/plain" },
        });
      }
    })(),
  );
});
