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

import com.sadellie.unitto.data.database.TimeZoneEntity
import com.sadellie.unitto.data.database.UnitsEntity

// Don't move to model module. This uses entity classes from database module
data class UserData(
    val themingMode: String,
    val enableDynamicTheme: Boolean,
    val enableAmoledTheme: Boolean,
    val customColor: Long,
    val monetMode: String,
    val startingScreen: String,
    val enableToolsExperiment: Boolean,
    val enableVibrations: Boolean,
    val middleZero: Boolean,
    val acButton: Boolean,
    val rpnMode: Boolean,

    val precision: Int,
    val separator: Int,
    val outputFormat: Int,

    val radianMode: Boolean,
    val partialHistoryView: Boolean,
    val clearInputAfterEquals: Boolean,

    val latestLeftSide: String,
    val latestRightSide: String,
    val shownUnitGroups: String,
    val unitConverterFavoritesOnly: Boolean,
    val unitConverterFormatTime: Boolean,
    val unitConverterSorting: String,

    val unitsTable: List<UnitsEntity>,
    val timeZoneTable: List<TimeZoneEntity>,
)
