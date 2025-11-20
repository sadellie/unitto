/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.net.toUri
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.getString
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.body_mass_title
import unitto.core.common.generated.resources.calculator_title
import unitto.core.common.generated.resources.converter_title
import unitto.core.common.generated.resources.date_calculator_title
import unitto.core.common.generated.resources.time_zone_title

actual val mainDrawerItems: List<DrawerItem> by lazy {
  var all =
    listOf(
      CalculatorDrawerItem,
      ConverterDrawerItem,
      DateCalculatorDrawerItem,
      TimeZonesDrawerItem,
      BodyMassDrawerItem,
    )

  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
    all = all - TimeZonesDrawerItem
  }

  all
}

/** Tries to push a dynamic shortcut. Does nothing if [Shortcut] can't be generated. */
suspend fun DrawerItem.pushDynamicShortcut(context: Context) {
  withContext(Dispatchers.IO) {
    val drawerItem = this@pushDynamicShortcut
    val shortcut = generateShortcut(drawerItem) ?: return@withContext
    val shortcutCompat = shortcutInfoCompat(context, drawerItem.topLevelRoute, shortcut)
    kotlin.runCatching { ShortcutManagerCompat.pushDynamicShortcut(context, shortcutCompat) }
  }
}

/** Tries to pin shortcut. Does nothing if [Shortcut] can't be generated. */
@RequiresApi(Build.VERSION_CODES.N_MR1)
suspend fun DrawerItem.addShortcut(context: Context) {
  val drawerItem = this@addShortcut
  val shortcut = generateShortcut(drawerItem) ?: return
  val shortcutCompat = shortcutInfoCompat(context, drawerItem.topLevelRoute, shortcut)
  val shortCutIntent = ShortcutManagerCompat.createShortcutResultIntent(context, shortcutCompat)

  try {
    ShortcutManagerCompat.requestPinShortcut(
      context,
      shortcutCompat,
      PendingIntent.getBroadcast(context, 0, shortCutIntent, FLAG_IMMUTABLE).intentSender,
    )
  } catch (e: Exception) {
    Logger.e(TAG, e) { "addShortcut: Failed to pin shortcut" }
  }
}

private fun generateShortcut(drawerItem: DrawerItem): Shortcut? {
  return when (drawerItem) {
    BodyMassDrawerItem ->
      Shortcut(
        Res.string.body_mass_title,
        Res.string.body_mass_title,
        R.drawable.ic_shortcut_body_mass,
      )
    CalculatorDrawerItem ->
      Shortcut(
        Res.string.calculator_title,
        Res.string.calculator_title,
        R.drawable.ic_shortcut_calculator,
      )
    ConverterDrawerItem ->
      Shortcut(
        Res.string.converter_title,
        Res.string.converter_title,
        R.drawable.ic_shortcut_unit_converter,
      )
    DateCalculatorDrawerItem ->
      Shortcut(
        Res.string.date_calculator_title,
        Res.string.date_calculator_title,
        R.drawable.ic_shortcut_date_calculator,
      )
    TimeZonesDrawerItem ->
      Shortcut(
        Res.string.time_zone_title,
        Res.string.time_zone_title,
        R.drawable.ic_shortcut_time_zone,
      )
    SettingsDrawerItem -> null
  }
}

private suspend fun shortcutInfoCompat(
  context: Context,
  graphRoute: TopLevelRoute,
  shortcut: Shortcut,
) =
  ShortcutInfoCompat.Builder(context, graphRoute.routeId)
    .setShortLabel(getString(shortcut.shortcutShortLabel))
    .setLongLabel(getString(shortcut.shortcutLongLabel))
    .setIcon(IconCompat.createWithResource(context, shortcut.shortcutDrawable))
    .setIntent(Intent(Intent.ACTION_VIEW, deepLink(graphRoute).toUri(), context, context.javaClass))
    .build()

private const val TAG = "DrawerItem"
