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

package com.sadellie.unitto.data.backup

import com.sadellie.unitto.data.model.ALL_UNIT_GROUPS
import com.sadellie.unitto.data.model.UnitsListSorting

object FakeUsrPreferenceValues {
    const val themingMode = "ThemingMode"
    const val enableDynamicTheme = false
    const val enableAmoledTheme = false
    const val customColor = 777L
    const val monetMode = "MonetMode"
    const val startingScreen = "StartingScreen"
    const val enableToolsExperiment = false
    const val enableVibrations = false
    const val middleZero = false
    const val acButton = false
    const val rpnMode = false
    const val precision = 69
    const val separator = 1
    const val outputFormat = 1
    const val radianMode = false
    const val partialHistoryView = false
    const val clearInputAfterEquals = false
    const val latestLeftSide = "LeftSideUnit"
    const val latestRightSide = "RightSideUnit"
    val shownUnitGroups = ALL_UNIT_GROUPS
    const val unitConverterFavoritesOnly = false
    const val unitConverterFormatTime = false
    val unitConverterSorting = UnitsListSorting.USAGE
}
