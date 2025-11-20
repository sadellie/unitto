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

import androidx.compose.ui.graphics.vector.ImageVector
import com.sadellie.unitto.core.designsystem.icons.symbols.AccessibilityNew
import com.sadellie.unitto.core.designsystem.icons.symbols.Calculate
import com.sadellie.unitto.core.designsystem.icons.symbols.CalculateFill
import com.sadellie.unitto.core.designsystem.icons.symbols.Event
import com.sadellie.unitto.core.designsystem.icons.symbols.EventFill
import com.sadellie.unitto.core.designsystem.icons.symbols.Schedule
import com.sadellie.unitto.core.designsystem.icons.symbols.ScheduleFill
import com.sadellie.unitto.core.designsystem.icons.symbols.Settings
import com.sadellie.unitto.core.designsystem.icons.symbols.SettingsFill
import com.sadellie.unitto.core.designsystem.icons.symbols.SwapHoriz
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import org.jetbrains.compose.resources.StringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.body_mass_title
import unitto.core.common.generated.resources.calculator_title
import unitto.core.common.generated.resources.converter_title
import unitto.core.common.generated.resources.date_calculator_title
import unitto.core.common.generated.resources.settings_title
import unitto.core.common.generated.resources.time_zone_title

sealed interface DrawerItem {
  val topLevelRoute: TopLevelRoute
  val name: StringResource
  val selectedIcon: ImageVector
  val defaultIcon: ImageVector
}

data object CalculatorDrawerItem : DrawerItem {
  override val topLevelRoute = CalculatorStartRoute
  override val name = Res.string.calculator_title
  override val selectedIcon = Symbols.CalculateFill
  override val defaultIcon = Symbols.Calculate
}

data object ConverterDrawerItem : DrawerItem {
  override val topLevelRoute = ConverterStartRoute()
  override val name = Res.string.converter_title
  override val selectedIcon = Symbols.SwapHoriz
  override val defaultIcon = Symbols.SwapHoriz
}

// unused until solver update
// data object GraphingDrawerItem : DrawerItem {
//  override val graphRoute = GraphingGraphRoute
//  override val name = Res.string.graphing_title
//  override val selectedIcon = Symbols.LineAxis
//  override val defaultIcon = Symbols.LineAxis
// }

data object DateCalculatorDrawerItem : DrawerItem {
  override val topLevelRoute = DateCalculatorStartRoute
  override val name = Res.string.date_calculator_title
  override val selectedIcon = Symbols.EventFill
  override val defaultIcon = Symbols.Event
}

data object TimeZonesDrawerItem : DrawerItem {
  override val topLevelRoute = TimeZoneStartRoute
  override val name = Res.string.time_zone_title
  override val selectedIcon = Symbols.ScheduleFill
  override val defaultIcon = Symbols.Schedule
}

data object BodyMassDrawerItem : DrawerItem {
  override val topLevelRoute = BodyMassStartRoute
  override val name = Res.string.body_mass_title
  override val selectedIcon = Symbols.AccessibilityNew
  override val defaultIcon = Symbols.AccessibilityNew
}

data object SettingsDrawerItem : DrawerItem {
  override val topLevelRoute = SettingsStartRoute
  override val name = Res.string.settings_title
  override val selectedIcon = Symbols.SettingsFill
  override val defaultIcon = Symbols.Settings
}

expect val mainDrawerItems: List<DrawerItem>

val additionalDrawerItems: List<DrawerItem> = listOf(SettingsDrawerItem)
