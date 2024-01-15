/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.core.ui.model

import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.outlined.AccessibilityNew
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.ui.graphics.vector.ImageVector
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.TopLevelDestinations

sealed class DrawerItem(
    val graph: String,
    val start: String,
    @StringRes val name: Int,
    val shortcut: Shortcut?,
    val selectedIcon: ImageVector,
    val defaultIcon: ImageVector
) {
    data object Calculator : DrawerItem(
        graph = TopLevelDestinations.CALCULATOR_GRAPH,
        start = TopLevelDestinations.CALCULATOR_START,
        name = R.string.calculator_title,
        shortcut = Shortcut(
            R.string.calculator_title,
            R.string.calculator_title,
            R.drawable.ic_shortcut_calculator
        ),
        selectedIcon = Icons.Filled.Calculate,
        defaultIcon = Icons.Outlined.Calculate
    )

    data object Converter : DrawerItem(
        graph = TopLevelDestinations.CONVERTER_GRAPH,
        start = TopLevelDestinations.CONVERTER_START,
        name = R.string.unit_converter_title,
        shortcut = Shortcut(
            R.string.unit_converter_title,
            R.string.unit_converter_title,
            R.drawable.ic_shortcut_unit_converter
        ),
        selectedIcon = Icons.Filled.SwapHoriz,
        defaultIcon = Icons.Outlined.SwapHoriz
    )

    data object DateCalculator : DrawerItem(
        graph = TopLevelDestinations.DATE_CALCULATOR_GRAPH,
        start = TopLevelDestinations.DATE_CALCULATOR_START,
        name = R.string.date_calculator_title,
        shortcut = Shortcut(
            R.string.date_calculator_title,
            R.string.date_calculator_title,
            R.drawable.ic_shortcut_date_calculator
        ),
        selectedIcon = Icons.Filled.Event,
        defaultIcon = Icons.Outlined.Event
    )

    data object TimeZones : DrawerItem(
        graph = TopLevelDestinations.TIME_ZONE_GRAPH,
        start = TopLevelDestinations.TIME_ZONE_START,
        name = R.string.time_zone_title,
        shortcut = Shortcut(
            R.string.time_zone_title,
            R.string.time_zone_title,
            R.drawable.ic_shortcut_time_zone
        ),
        selectedIcon = Icons.Filled.Schedule,
        defaultIcon = Icons.Outlined.Schedule
    )

    data object BodyMass : DrawerItem(
        graph = TopLevelDestinations.BODY_MASS_GRAPH,
        start = TopLevelDestinations.BODY_MASS_START,
        name = R.string.body_mass_title,
        shortcut = Shortcut(
            R.string.body_mass_title,
            R.string.body_mass_title,
            R.drawable.ic_shortcut_body_mass
        ),
        selectedIcon = Icons.Filled.AccessibilityNew,
        defaultIcon = Icons.Outlined.AccessibilityNew
    )

    data object Settings : DrawerItem(
        graph = TopLevelDestinations.SETTINGS_GRAPH,
        start = TopLevelDestinations.SETTINGS_START,
        name = R.string.settings_title,
        shortcut = null,
        selectedIcon = Icons.Filled.Settings,
        defaultIcon = Icons.Outlined.Settings
    )

    companion object {
        /**
         * Except for [Settings]
         */
        val main by lazy {
            var all = listOf(
                Calculator,
                Converter,
                DateCalculator,
                TimeZones,
                BodyMass,
            )

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                all = all - TimeZones
            }

            all
        }

        // Only routes, not graphs!
        val startRoutes by lazy { main.map { it.start } }
    }
}
