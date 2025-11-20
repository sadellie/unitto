/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sadellie.unitto.core.navigation

import androidx.navigation3.runtime.NavKey
import com.eygraber.uri.Uri
import com.eygraber.uri.toKmpUri
import com.sadellie.unitto.core.navigation.CalculatorStartRoute.serializer
import com.sadellie.unitto.core.navigation.ConverterStartRoute.Companion.DEEP_LINK_PATTERN
import com.sadellie.unitto.core.navigation.deeplink.DeepLinkMatcher
import com.sadellie.unitto.core.navigation.deeplink.DeepLinkPattern
import com.sadellie.unitto.core.navigation.deeplink.DeepLinkRequest
import com.sadellie.unitto.core.navigation.deeplink.KeyDecoder
import kotlinx.serialization.Serializable

interface Route : NavKey {
  /** Don't touch, users have "..._route" in their settings as initial route and in shortcuts */
  val routeId: String

  companion object {
    /**
     * Extract [Route] from [uri]
     *
     * @return matched [Route] (using [DeepLinkPattern]) or null if no matches
     * @author https://github.com/android/nav3-recipes
     */
    fun extractRouteFromDeeplink(uri: Uri): Route? {
      val deepLinkPatterns: List<DeepLinkPattern<out Route>> =
        listOf(
          DeepLinkPattern(serializer(), deepLink(CalculatorStartRoute).toKmpUri()),
          DeepLinkPattern(
            ConverterStartRoute.serializer(),
            deepLink(ConverterStartRoute()).toKmpUri(),
          ),
          DeepLinkPattern(ConverterStartRoute.serializer(), (DEEP_LINK_PATTERN.toKmpUri())),
          DeepLinkPattern(
            DateCalculatorStartRoute.serializer(),
            deepLink(DateCalculatorStartRoute).toKmpUri(),
          ),
          DeepLinkPattern(TimeZoneStartRoute.serializer(), deepLink(TimeZoneStartRoute).toKmpUri()),
          DeepLinkPattern(BodyMassStartRoute.serializer(), deepLink(BodyMassStartRoute).toKmpUri()),
        )
      /** STEP 2. Parse requested deeplink */
      val request = DeepLinkRequest(uri)
      /** STEP 3. Compared requested with supported deeplink to find match */
      val match =
        deepLinkPatterns.firstNotNullOfOrNull { pattern ->
          DeepLinkMatcher(request, pattern).match()
        } ?: return null
      /** STEP 4. If match is found, associate match to the correct key */
      // leverage kotlinx.serialization's Decoder to decode
      // match result into a backstack key
      val deepLinkRoute = KeyDecoder(match.args).decodeSerializableValue(match.serializer)
      return deepLinkRoute
    }
  }
}

/** Special marker for top level routes */
interface TopLevelRoute : Route

@Serializable
data object CalculatorStartRoute : TopLevelRoute {
  override val routeId = "calculator_route"
}

@Serializable
data class ConverterStartRoute(val unitFromId: String = "", val unitToId: String = "") :
  TopLevelRoute {
  companion object {
    private const val ROUTE_ID = "converter_route"

    // parameter names must match with constructor
    fun generateRoute(unitFromId: String?, unitToId: String?) =
      "$NAVIGATION_BASE_URI/$ROUTE_ID?unitFromId=$unitFromId&unitToId=$unitToId"

    val DEEP_LINK_PATTERN =
      "$NAVIGATION_BASE_URI/$ROUTE_ID" +
        "?${ConverterStartRoute::unitFromId.name}={${ConverterStartRoute::unitFromId.name}}" +
        "&${ConverterStartRoute::unitToId.name}={${ConverterStartRoute::unitToId.name}}"
  }

  override val routeId = ROUTE_ID
}

@Serializable
data object DateCalculatorStartRoute : TopLevelRoute {
  override val routeId = "date_calculator_route"
}

@Serializable
data object TimeZoneStartRoute : TopLevelRoute {
  override val routeId = "time_zone_route"
}

@Serializable
data object BodyMassStartRoute : TopLevelRoute {
  override val routeId = "body_mass_route"
}

@Serializable
data object SettingsStartRoute : TopLevelRoute {
  override val routeId = "settings_route"
}

val graphRoutes = (mainDrawerItems + additionalDrawerItems).map { it.topLevelRoute }

fun deepLink(graphRoute: TopLevelRoute): String = "$NAVIGATION_BASE_URI/${graphRoute.routeId}"

private const val NAVIGATION_BASE_URI = "app://com.sadellie.unitto"
