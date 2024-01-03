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

import com.sadellie.unitto.data.model.ALL_UNIT_GROUPS
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.UnitsListSorting
import com.sadellie.unitto.data.model.userprefs.AboutPreferences
import com.sadellie.unitto.data.model.userprefs.AddSubtractPreferences
import com.sadellie.unitto.data.model.userprefs.AppPreferences
import com.sadellie.unitto.data.model.userprefs.CalculatorPreferences
import com.sadellie.unitto.data.model.userprefs.ConverterPreferences
import com.sadellie.unitto.data.model.userprefs.DisplayPreferences
import com.sadellie.unitto.data.model.userprefs.FormattingPreferences
import com.sadellie.unitto.data.model.userprefs.GeneralPreferences
import com.sadellie.unitto.data.model.userprefs.StartingScreenPreferences
import com.sadellie.unitto.data.model.userprefs.UnitGroupsPreferences

data class AppPreferencesImpl(
    override val themingMode: String,
    override val enableDynamicTheme: Boolean,
    override val enableAmoledTheme: Boolean,
    override val customColor: Long,
    override val monetMode: String,
    override val startingScreen: String,
    override val enableToolsExperiment: Boolean,
    override val systemFont: Boolean,
    override val rpnMode: Boolean,
) : AppPreferences

data class GeneralPreferencesImpl(
    override val enableVibrations: Boolean,
) : GeneralPreferences

data class CalculatorPreferencesImpl(
    override val radianMode: Boolean,
    override val enableVibrations: Boolean,
    override val separator: Int,
    override val middleZero: Boolean,
    override val acButton: Boolean,
    override val partialHistoryView: Boolean,
    override val precision: Int,
    override val outputFormat: Int,
) : CalculatorPreferences

data class ConverterPreferencesImpl(
    override val enableVibrations: Boolean,
    override val separator: Int,
    override val middleZero: Boolean,
    override val acButton: Boolean,
    override val precision: Int,
    override val outputFormat: Int,
    override val unitConverterFormatTime: Boolean,
    override val unitConverterSorting: UnitsListSorting,
    override val shownUnitGroups: List<UnitGroup>,
    override val unitConverterFavoritesOnly: Boolean,
    override val enableToolsExperiment: Boolean,
    override val latestLeftSideUnit: String,
    override val latestRightSideUnit: String,
) : ConverterPreferences

data class DisplayPreferencesImpl(
    override val systemFont: Boolean,
    override val middleZero: Boolean,
    override val acButton: Boolean,
) : DisplayPreferences

data class FormattingPreferencesImpl(
    override val digitsPrecision: Int,
    override val separator: Int,
    override val outputFormat: Int,
) : FormattingPreferences

data class UnitGroupsPreferencesImpl(
    override val shownUnitGroups: List<UnitGroup> = ALL_UNIT_GROUPS,
) : UnitGroupsPreferences

data class AddSubtractPreferencesImpl(
    override val separator: Int,
    override val enableVibrations: Boolean,
) : AddSubtractPreferences

data class AboutPreferencesImpl(
    override val enableToolsExperiment: Boolean,
) : AboutPreferences

data class StartingScreenPreferencesImpl(
    override val startingScreen: String,
) : StartingScreenPreferences
