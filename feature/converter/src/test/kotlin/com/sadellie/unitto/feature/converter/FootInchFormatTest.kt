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

package com.sadellie.unitto.feature.converter

import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.data.converter.ConverterResult
import org.junit.Assert.assertEquals
import org.junit.Test

class FootInchFormatTest {
  private val scale = 3
  private val outputFormat = OutputFormat.PLAIN
  private val formatterSymbols = FormatterSymbols(Token.PERIOD, Token.COMMA)

  @Test
  fun testNegative() {
    val converterResult =
      ConverterResult.FootInch(foot = KBigDecimal("-1"), inch = KBigDecimal("-2"))
    val expected = "−1ft −2in"
    val actual =
      converterResult.format(
        footLabel = "ft",
        inchLabel = "in",
        scale = scale,
        outputFormat = outputFormat,
        formatterSymbols = formatterSymbols,
      )
    assertEquals(expected, actual)
  }

  @Test
  fun testZeroBoth() {
    val converterResult = ConverterResult.FootInch(foot = KBigDecimal.ZERO, inch = KBigDecimal.ZERO)
    val expected = "0"
    val actual =
      converterResult.format(
        footLabel = "ft",
        inchLabel = "in",
        scale = scale,
        outputFormat = outputFormat,
        formatterSymbols = formatterSymbols,
      )
    assertEquals(expected, actual)
  }

  @Test
  fun testZeroFoot() {
    val converterResult =
      ConverterResult.FootInch(foot = KBigDecimal.ZERO, inch = KBigDecimal("123"))
    val expected = "0ft 123in"
    val actual =
      converterResult.format(
        footLabel = "ft",
        inchLabel = "in",
        scale = scale,
        outputFormat = outputFormat,
        formatterSymbols = formatterSymbols,
      )
    assertEquals(expected, actual)
  }

  @Test
  fun testZeroInch() {
    val converterResult =
      ConverterResult.FootInch(foot = KBigDecimal("123"), inch = KBigDecimal.ZERO)
    val expected = "123"
    val actual =
      converterResult.format(
        footLabel = "ft",
        inchLabel = "in",
        scale = scale,
        outputFormat = outputFormat,
        formatterSymbols = formatterSymbols,
      )
    assertEquals(expected, actual)
  }

  @Test
  fun testBothPositive() {
    val converterResult =
      ConverterResult.FootInch(foot = KBigDecimal("123"), inch = KBigDecimal("456"))
    val expected = "123ft 456in"
    val actual =
      converterResult.format(
        footLabel = "ft",
        inchLabel = "in",
        scale = scale,
        outputFormat = outputFormat,
        formatterSymbols = formatterSymbols,
      )
    assertEquals(expected, actual)
  }
}
