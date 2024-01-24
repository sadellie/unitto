/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

package com.sadellie.unitto.core.ui

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.sadellie.unitto.core.ui.model.DrawerItem
import com.sadellie.unitto.core.ui.model.Shortcut
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Tries to push a dynamic shortcut. Does nothing if [DrawerItem.shortcut] is `null`
 *
 * @param drawerItem [DrawerItem]
 */
suspend fun Context.pushDynamicShortcut(
    drawerItem: DrawerItem,
) = withContext(Dispatchers.IO) {
    val shortcut = drawerItem.shortcut ?: return@withContext
    // Resource intensive method!
    val context = this@pushDynamicShortcut

    val shortcutCompat = shortcutInfoCompat(
        context = context,
        route = drawerItem.graph,
        shortcut = shortcut
    )

    kotlin.runCatching {
        ShortcutManagerCompat.pushDynamicShortcut(context, shortcutCompat)
    }
}

/**
 * Tries to pin shortcut. Does nothing if [DrawerItem.shortcut] is `null`
 *
 * @param drawerItem [DrawerItem]
 */
@RequiresApi(Build.VERSION_CODES.N_MR1)
fun Context.addShortcut(
    drawerItem: DrawerItem,
) {
    val shortcut = drawerItem.shortcut ?: return
    val context = this@addShortcut

    val shortcutCompat = shortcutInfoCompat(
        context = context,
        route = drawerItem.graph,
        shortcut = shortcut
    )

    val shortCutIntent = ShortcutManagerCompat.createShortcutResultIntent(context, shortcutCompat)

    try {
        ShortcutManagerCompat.requestPinShortcut(
            context,
            shortcutCompat,
            PendingIntent.getBroadcast(context, 0, shortCutIntent, FLAG_IMMUTABLE).intentSender
        )
    } catch (e: Exception) {
        showToast(context, e.message ?: "Error")
    }
}

private fun Context.shortcutInfoCompat(
    context: Context,
    route: String,
    shortcut: Shortcut,
): ShortcutInfoCompat {
    return ShortcutInfoCompat.Builder(context, route)
        .setShortLabel(getString(shortcut.shortcutShortLabel))
        .setLongLabel(getString(shortcut.shortcutLongLabel))
        .setIcon(IconCompat.createWithResource(context, shortcut.shortcutDrawable))
        .setIntent(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("app://com.sadellie.unitto/$route"),
                context,
                context.javaClass
            )
        )
        .build()
}
