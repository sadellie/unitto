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

package com.sadellie.unitto.data.userprefs

import androidx.compose.ui.graphics.Color
import com.sadellie.unitto.data.model.ALL_UNIT_GROUPS
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.UnitsListSorting
import io.github.sadellie.themmo.MonetMode
import io.github.sadellie.themmo.ThemingMode

data class AppPreferences(
    val themingMode: ThemingMode,
    val enableDynamicTheme: Boolean,
    val enableAmoledTheme: Boolean,
    val customColor: Color,
    val monetMode: MonetMode,
    val startingScreen: String,
    val enableToolsExperiment: Boolean,
    val systemFont: Boolean,
)

data class GeneralPreferences(
    val enableVibrations: Boolean,
)

data class CalculatorPreferences(
    val radianMode: Boolean,
    val enableVibrations: Boolean,
    val separator: Int,
    val middleZero: Boolean,
    val acButton: Boolean,
    val partialHistoryView: Boolean,
    val precision: Int,
    val outputFormat: Int,
)

data class ConverterPreferences(
    val enableVibrations: Boolean,
    val separator: Int,
    val middleZero: Boolean,
    val acButton: Boolean,
    val precision: Int,
    val outputFormat: Int,
    val unitConverterFormatTime: Boolean,
    val unitConverterSorting: UnitsListSorting,
    val shownUnitGroups: List<UnitGroup>,
    val unitConverterFavoritesOnly: Boolean,
    val enableToolsExperiment: Boolean,
    val latestLeftSideUnit: String,
    val latestRightSideUnit: String,
)

data class DisplayPreferences(
    val systemFont: Boolean,
    val middleZero: Boolean,
    val acButton: Boolean,
)

data class FormattingPreferences(
    val digitsPrecision: Int,
    val separator: Int,
    val outputFormat: Int,
)

data class UnitGroupsPreferences(
    val shownUnitGroups: List<UnitGroup> = ALL_UNIT_GROUPS,
)

data class AddSubtractPreferences(
    val separator: Int,
    val enableVibrations: Boolean,
)

data class AboutPreferences(
    val enableToolsExperiment: Boolean,
)

data class StartingScreenPreferences(
    val startingScreen: String,
)
