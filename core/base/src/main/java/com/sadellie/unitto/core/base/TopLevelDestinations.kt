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

sealed class TopLevelDestinations(
    val route: String,
    @StringRes val name: Int
) {
    object Converter : TopLevelDestinations(
        route = "converter_route",
        name = R.string.unit_converter
    )

    object Calculator : TopLevelDestinations(
        route = "calculator_route",
        name = R.string.calculator
    )

    object DateDifference : TopLevelDestinations(
        route = "date_difference_route",
        name = R.string.date_difference
    )

    object TimeZone : TopLevelDestinations(
        route = "time_zone_graph",
        name = R.string.time_zone_screen
    )

    object Settings : TopLevelDestinations(
        route = "settings_graph",
        name = R.string.settings_screen
    )
}

val TOP_LEVEL_DESTINATIONS: Map<TopLevelDestinations, Int> by lazy {
    mapOf(
        TopLevelDestinations.Converter to R.string.unit_converter,
        TopLevelDestinations.Calculator to R.string.calculator,
        TopLevelDestinations.DateDifference to R.string.date_difference,
    )
}
