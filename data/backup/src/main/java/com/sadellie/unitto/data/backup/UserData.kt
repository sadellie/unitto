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
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// Don't move to model module. This uses entity classes from database module
@JsonClass(generateAdapter = true)
internal data class UserData(
    @Json(name = "themingMode") val themingMode: String,
    @Json(name = "enableDynamicTheme") val enableDynamicTheme: Boolean,
    @Json(name = "enableAmoledTheme") val enableAmoledTheme: Boolean,
    @Json(name = "customColor") val customColor: Long,
    @Json(name = "monetMode") val monetMode: String,
    @Json(name = "startingScreen") val startingScreen: String,
    @Json(name = "enableToolsExperiment") val enableToolsExperiment: Boolean,
    @Json(name = "enableVibrations") val enableVibrations: Boolean,
    @Json(name = "middleZero") val middleZero: Boolean,
    @Json(name = "acButton") val acButton: Boolean,
    @Json(name = "rpnMode") val rpnMode: Boolean,

    @Json(name = "precision") val precision: Int,
    @Json(name = "separator") val separator: Int,
    @Json(name = "outputFormat") val outputFormat: Int,

    @Json(name = "radianMode") val radianMode: Boolean,
    @Json(name = "partialHistoryView") val partialHistoryView: Boolean,
    @Json(name = "clearInputAfterEquals") val clearInputAfterEquals: Boolean,

    @Json(name = "latestLeftSide") val latestLeftSide: String,
    @Json(name = "latestRightSide") val latestRightSide: String,
    @Json(name = "shownUnitGroups") val shownUnitGroups: String,
    @Json(name = "unitConverterFavoritesOnly") val unitConverterFavoritesOnly: Boolean,
    @Json(name = "unitConverterFormatTime") val unitConverterFormatTime: Boolean,
    @Json(name = "unitConverterSorting") val unitConverterSorting: String,

    @Json(name = "unitsTable") val unitsTable: List<UnitsEntity>,
    @Json(name = "timeZoneTable") val timeZoneTable: List<TimeZoneEntity>,
)
