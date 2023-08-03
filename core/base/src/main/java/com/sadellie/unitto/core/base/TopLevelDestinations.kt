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

package com.sadellie.unitto.core.base

import androidx.annotation.StringRes

// Don't touch, users have "..._route" in their settings
private const val CONVERTER_GRAPH = "converter_route"
private const val CONVERTER_START = "converter_start"

private const val CALCULATOR_GRAPH = "calculator_route"
private const val CALCULATOR_START = "calculator_start"

private const val DATE_CALCULATOR_GRAPH = "date_calculator_route"
private const val DATE_CALCULATOR_START = "date_calculator_start"

private const val TIME_ZONE_GRAPH = "time_zone_route"
private const val TIME_ZONE_START = "time_zone_start"

private const val SETTINGS_GRAPH = "settings_route"
private const val SETTINGS_START = "settings_start"

sealed class TopLevelDestinations(
    val graph: String,
    val start: String = graph,
    @StringRes val name: Int,
) {
    data object Converter : TopLevelDestinations(
        graph = CONVERTER_GRAPH,
        start = CONVERTER_START,
        name = R.string.unit_converter
    )

    data object Calculator : TopLevelDestinations(
        graph = CALCULATOR_GRAPH,
        start = CALCULATOR_START,
        name = R.string.calculator
    )

    data object DateCalculator : TopLevelDestinations(
        graph = DATE_CALCULATOR_GRAPH,
        start = DATE_CALCULATOR_START,
        name = R.string.date_calculator
    )

    data object TimeZone : TopLevelDestinations(
        graph = TIME_ZONE_GRAPH,
        start = TIME_ZONE_START,
        name = R.string.time_zone_screen
    )

    data object Settings : TopLevelDestinations(
        graph = SETTINGS_GRAPH,
        start = SETTINGS_START,
        name = R.string.settings_screen
    )
}

// Shown in settings
val TOP_LEVEL_GRAPH_ROUTES: Map<String, Int> by lazy {
    mapOf(
        TopLevelDestinations.Calculator.graph to R.string.calculator,
        TopLevelDestinations.Converter.graph to R.string.unit_converter,
        TopLevelDestinations.DateCalculator.graph to R.string.date_calculator,
    )
}

// Only routes, not graphs!
val TOP_LEVEL_START_ROUTES by lazy {
    listOf(
        CONVERTER_START,
        CALCULATOR_START,
        DATE_CALCULATOR_START,
        TIME_ZONE_START,
        SETTINGS_START,
    )
}
