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

// Don't touch, users have "..._route" in their settings
object TopLevelDestinations {
    const val CONVERTER_GRAPH = "converter_route"
    const val CONVERTER_START = "converter_start"
    const val CALCULATOR_GRAPH = "calculator_route"
    const val CALCULATOR_START = "calculator_start"
    const val DATE_CALCULATOR_GRAPH = "date_calculator_route"
    const val DATE_CALCULATOR_START = "date_calculator_start"
    const val TIME_ZONE_GRAPH = "time_zone_route"
    const val TIME_ZONE_START = "time_zone_start"
    const val BODY_MASS_GRAPH = "body_mass_route"
    const val BODY_MASS_START = "body_mass_start"
    const val SETTINGS_GRAPH = "settings_route"
    const val SETTINGS_START = "settings_start"
}
