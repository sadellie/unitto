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

internal val fakeUserData = UserData(
    themingMode = "AUTO",
    enableDynamicTheme = false,
    enableAmoledTheme = false,
    customColor = 777L,
    monetMode = "TonalSpot",
    startingScreen = "calculator_route",
    enableToolsExperiment = false,
    systemFont = false,
    enableVibrations = false,
    middleZero = false,
    acButton = false,
    rpnMode = false,
    precision = 11,
    separator = 1,
    outputFormat = 1,
    radianMode = false,
    partialHistoryView = false,
    latestLeftSide = "kilometer",
    latestRightSide = "mile",
    shownUnitGroups = "LENGTH",
    unitConverterFavoritesOnly = false,
    unitConverterFormatTime = false,
    unitConverterSorting = "USAGE",
    calculatorHistoryTable = calculatorHistory,
    unitsTable = units,
    timeZoneTable = timeZones
)
