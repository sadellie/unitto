/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
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

package com.sadellie.unitto.core.ui.textfield

import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token
import kotlin.test.Test
import kotlin.test.assertEquals

class FormatterExtensionFormatNumberTest {

  @Test fun formatNumber_testEmpty() = assertFormatNumber("", "", "")

  @Test fun formatNumber_testDot() = assertFormatNumber(".", ".", ".")

  @Test fun formatNumber_testSingleDigit() = assertFormatNumber("1", "1", "1")

  @Test fun formatNumber_testFractionalOnly() = assertFormatNumber(".123456", ".123456", ".123456")

  @Test fun formatNumber_testShortNumber() = assertFormatNumber("123", "123", "123")

  @Test
  fun formatNumber_testShortNumberWithFractional() =
    assertFormatNumber("123.456", "123.456", "123.456")

  @Test fun formatNumber_testLongNumber() = assertFormatNumber("123456", "123,456", "1,23,456")

  @Test
  fun formatNumber_testLongNumberWithFractional() =
    assertFormatNumber("123456.789", "123,456.789", "1,23,456.789")

  private fun assertFormatNumber(
    input: String,
    expectedInternational: String,
    expectedIndian: String,
  ) {
    assertEquals(
      expectedInternational,
      input.formatNumber(FormatterSymbols(Token.Comma, Token.Period, false)),
      "Unexpected International",
    )
    assertEquals(
      expectedIndian,
      input.formatNumber(FormatterSymbols(Token.Comma, Token.Period, true)),
      "Unexpected Indian",
    )
  }
}
