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

import com.eygraber.uri.toKmpUri
import kotlin.test.Test
import kotlin.test.assertEquals

class ExtractRouteFromDeepLinkTest {
  @Test fun test_empty() = assertExtractedRoute(null, "")

  @Test fun test_invalid() = assertExtractedRoute(null, "test")

  @Test fun test_invalid2() = assertExtractedRoute(null, "app://com.sadellie.unitto/")

  @Test
  fun test_calculatorStartRoute() =
    assertExtractedRoute(CalculatorStartRoute, "app://com.sadellie.unitto/calculator_route")

  @Test
  fun test_converterStartRoute1() =
    assertExtractedRoute(ConverterStartRoute(), "app://com.sadellie.unitto/converter_route")

  @Test
  fun test_converterStartRoute2_valid() =
    assertExtractedRoute(
      ConverterStartRoute("value1", "value2"),
      "app://com.sadellie.unitto/converter_route?unitFromId=value1&unitToId=value2",
    )

  @Test
  fun test_converterStartRoute2_invalid_emptyFirstParam() =
    assertExtractedRoute(
      ConverterStartRoute("", "value2"),
      "app://com.sadellie.unitto/converter_route?unitFromId=&unitToId=value2",
    )

  @Test
  fun test_converterStartRoute2_invalid_emptySecondParam() =
    assertExtractedRoute(
      ConverterStartRoute("value1", ""),
      "app://com.sadellie.unitto/converter_route?unitFromId=value1&unitToId=",
    )

  @Test
  fun test_dateCalculatorStartRoute() =
    assertExtractedRoute(
      DateCalculatorStartRoute,
      "app://com.sadellie.unitto/date_calculator_route",
    )

  @Test
  fun test_timeZoneStartRoute() =
    assertExtractedRoute(TimeZoneStartRoute, "app://com.sadellie.unitto/time_zone_route")

  @Test
  fun test_bodyMassStartRoute() =
    assertExtractedRoute(BodyMassStartRoute, "app://com.sadellie.unitto/body_mass_route")

  private fun assertExtractedRoute(expected: Route?, uriString: String) =
    assertEquals(expected, Route.extractRouteFromDeeplink(uriString.toKmpUri()))
}
