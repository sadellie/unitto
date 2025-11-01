/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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

package com.sadellie.unitto.core.navigation

import kotlinx.serialization.Serializable

interface Route {
  /** Don't touch, users have "..._route" in their settings */
  val id: String
}

@Serializable
data object CalculatorGraphRoute : Route {
  override val id = "calculator_route"
}

@Serializable
data object ConverterGraphRoute : Route {
  override val id = "converter_route"
}

@Serializable
data class ConverterStartRoute(val unitFromId: String? = null, val unitToId: String? = null) :
  Route {
  companion object {
    private const val ROUTE_ID = "converter_start"

    // parameter names must match with constructor
    fun generateRoute(unitFromId: String?, unitToId: String?) =
      "$NAVIGATION_BASE_URI/$ROUTE_ID?unitFromId=$unitFromId&unitToId=$unitToId"

    const val BASE_PATH = "$NAVIGATION_BASE_URI/$ROUTE_ID"
  }

  override val id = ROUTE_ID
}

@Serializable
data object GraphingGraphRoute : Route {
  override val id = "graphing_route"
}

@Serializable
data object DateCalculatorGraphRoute : Route {
  override val id = "date_calculator_route"
}

@Serializable
data object TimeZoneGraphRoute : Route {
  override val id = "time_zone_route"
}

@Serializable
data object BodyMassGraphRoute : Route {
  override val id = "body_mass_route"
}

@Serializable
data object SettingsGraphRoute : Route {
  override val id = "settings_route"
}

val graphRoutes = (mainDrawerItems + additionalDrawerItems).map { it.graphRoute }

fun deepLink(graphRoute: Route): String = "$NAVIGATION_BASE_URI/${graphRoute.id}"

private const val NAVIGATION_BASE_URI = "app://com.sadellie.unitto"
