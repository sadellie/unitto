/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.core.database.converters

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class ConvertersTest {

  private val converters = Converters()

  @Test
  fun stringToBigDecimal_validShouldPass() =
    assertEquals(BigDecimal("123456.789"), converters.stringToBigDecimal("123456.789"))

  @Test
  fun stringToBigDecimal_invalidShouldFallback() =
    assertEquals(null, converters.stringToBigDecimal("null"))

  @Test
  fun bigDecimalToString_validShouldPass() =
    assertEquals("123456.789", converters.bigDecimalToString(BigDecimal("123456.789")))

  @Test
  fun bigDecimalToString_invalidShouldFallback() =
    assertEquals(null, converters.bigDecimalToString(null))
}
