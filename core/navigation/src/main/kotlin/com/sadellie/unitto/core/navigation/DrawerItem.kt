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

package com.sadellie.unitto.core.navigation

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.net.toUri
import com.sadellie.unitto.core.common.showToast
import com.sadellie.unitto.core.designsystem.icons.symbols.AccessibilityNew
import com.sadellie.unitto.core.designsystem.icons.symbols.Calculate
import com.sadellie.unitto.core.designsystem.icons.symbols.CalculateFill
import com.sadellie.unitto.core.designsystem.icons.symbols.Event
import com.sadellie.unitto.core.designsystem.icons.symbols.EventFill
import com.sadellie.unitto.core.designsystem.icons.symbols.LineAxis
import com.sadellie.unitto.core.designsystem.icons.symbols.Schedule
import com.sadellie.unitto.core.designsystem.icons.symbols.ScheduleFill
import com.sadellie.unitto.core.designsystem.icons.symbols.Settings
import com.sadellie.unitto.core.designsystem.icons.symbols.SettingsFill
import com.sadellie.unitto.core.designsystem.icons.symbols.SwapHoriz
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed interface DrawerItem {
  val graphRoute: Route
  @get:StringRes val name: Int
  val selectedIcon: ImageVector
  val defaultIcon: ImageVector
  val shortcut: Shortcut?

  /** Tries to push a dynamic shortcut. Does nothing if [DrawerItem.shortcut] is `null` */
  suspend fun pushDynamicShortcut(context: Context) {
    withContext(Dispatchers.IO) {
      val drawerItem = this@DrawerItem
      val shortcut = drawerItem.shortcut ?: return@withContext
      val shortcutCompat = context.shortcutInfoCompat(drawerItem.graphRoute, shortcut)
      kotlin.runCatching { ShortcutManagerCompat.pushDynamicShortcut(context, shortcutCompat) }
    }
  }

  /** Tries to pin shortcut. Does nothing if [DrawerItem.shortcut] is `null` */
  @RequiresApi(Build.VERSION_CODES.N_MR1)
  fun addShortcut(context: Context) {
    val drawerItem = this@DrawerItem
    val shortcut = drawerItem.shortcut ?: return
    val shortcutCompat = context.shortcutInfoCompat(drawerItem.graphRoute, shortcut)
    val shortCutIntent = ShortcutManagerCompat.createShortcutResultIntent(context, shortcutCompat)

    try {
      ShortcutManagerCompat.requestPinShortcut(
        context,
        shortcutCompat,
        PendingIntent.getBroadcast(context, 0, shortCutIntent, FLAG_IMMUTABLE).intentSender,
      )
    } catch (e: Exception) {
      showToast(context, e.message ?: "Error")
    }
  }
}

data object CalculatorDrawerItem : DrawerItem {
  override val graphRoute = CalculatorGraphRoute
  override val name = R.string.calculator_title
  override val selectedIcon = Symbols.CalculateFill
  override val defaultIcon = Symbols.Calculate
  override val shortcut =
    Shortcut(
      R.string.calculator_title,
      R.string.calculator_title,
      R.drawable.ic_shortcut_calculator,
    )
}

data object ConverterDrawerItem : DrawerItem {
  override val graphRoute = ConverterGraphRoute
  override val name = R.string.converter_title
  override val selectedIcon = Symbols.SwapHoriz
  override val defaultIcon = Symbols.SwapHoriz
  override val shortcut =
    Shortcut(
      R.string.converter_title,
      R.string.converter_title,
      R.drawable.ic_shortcut_unit_converter,
    )
}

data object GraphingDrawerItem : DrawerItem {
  override val graphRoute = GraphingGraphRoute
  override val name = R.string.graphing_title
  override val selectedIcon = Symbols.LineAxis
  override val defaultIcon = Symbols.LineAxis
  override val shortcut = null
}

data object DateCalculatorDrawerItem : DrawerItem {
  override val graphRoute = DateCalculatorGraphRoute
  override val name = R.string.date_calculator_title
  override val selectedIcon = Symbols.EventFill
  override val defaultIcon = Symbols.Event
  override val shortcut =
    Shortcut(
      R.string.date_calculator_title,
      R.string.date_calculator_title,
      R.drawable.ic_shortcut_date_calculator,
    )
}

data object TimeZonesDrawerItem : DrawerItem {
  override val graphRoute = TimeZoneGraphRoute
  override val name = R.string.time_zone_title
  override val selectedIcon = Symbols.ScheduleFill
  override val defaultIcon = Symbols.Schedule
  override val shortcut =
    Shortcut(R.string.time_zone_title, R.string.time_zone_title, R.drawable.ic_shortcut_time_zone)
}

data object BodyMassDrawerItem : DrawerItem {
  override val graphRoute = BodyMassGraphRoute
  override val name = R.string.body_mass_title
  override val selectedIcon = Symbols.AccessibilityNew
  override val defaultIcon = Symbols.AccessibilityNew
  override val shortcut =
    Shortcut(R.string.body_mass_title, R.string.body_mass_title, R.drawable.ic_shortcut_body_mass)
}

data object SettingsDrawerItem : DrawerItem {
  override val graphRoute = SettingsGraphRoute
  override val name = R.string.settings_title
  override val selectedIcon = Symbols.SettingsFill
  override val defaultIcon = Symbols.Settings
  override val shortcut = null
}

val mainDrawerItems: List<DrawerItem> = run {
  var all =
    listOf(
      CalculatorDrawerItem,
      ConverterDrawerItem,
      // GraphingDrawerItem,
      DateCalculatorDrawerItem,
      TimeZonesDrawerItem,
      BodyMassDrawerItem,
    )

  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
    all = all - TimeZonesDrawerItem
  }

  all
}

val additionalDrawerItems: List<DrawerItem> = listOf(SettingsDrawerItem)

private fun Context.shortcutInfoCompat(graphRoute: Route, shortcut: Shortcut): ShortcutInfoCompat {
  return ShortcutInfoCompat.Builder(this, graphRoute.id)
    .setShortLabel(getString(shortcut.shortcutShortLabel))
    .setLongLabel(getString(shortcut.shortcutLongLabel))
    .setIcon(IconCompat.createWithResource(this, shortcut.shortcutDrawable))
    .setIntent(Intent(Intent.ACTION_VIEW, deepLink(graphRoute).toUri(), this, this.javaClass))
    .build()
}
