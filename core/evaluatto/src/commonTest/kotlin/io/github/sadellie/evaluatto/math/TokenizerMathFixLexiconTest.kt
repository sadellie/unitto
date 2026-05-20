/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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

package io.github.sadellie.evaluatto.math

import kotlin.test.Test
import kotlin.test.assertEquals

class TokenizerMathFixLexiconTest {

  @Test
  fun `missing multiply`() {
    assertLex("2×(69−420)", "2(69−420)")

    assertLex("0.×(69−420)", "0.(69−420)")

    assertLex(".0×(69−420)", ".0(69−420)")

    assertLex(".×(69−420)", ".(69−420)")

    assertLex("2×(69−420)×(23−4)×cos(9)×tan((sin⁻¹(.9)))", "2(69−420)(23−4)cos(9)tan((sin⁻¹(.9)))")

    assertLex("e×e+π", "ee+π")

    assertLex("(69)×420", "(69)420")

    assertLex("123!×456", "123!456")
  }

  @Test
  fun `balanced brackets`() {
    assertLex("123×(12+4)", "123(12+4")

    assertLex("12312+4", "12312+4")

    assertLex("123)))×12+4", "123)))12+4")

    assertLex("sin(cos(tan(3)))", "sin(cos(tan(3")

    assertLex("sin(cos(tan(3)))", "sin(cos(tan(3)")

    assertLex("sin(cos(tan(3)))", "sin(cos(tan(3))")
  }

  @Test
  fun `scientific notation`() {
    assertLex("1.2×10^3", "1.2E+3")

    assertLex("1.2÷10^3", "1.2E−3")

    assertLex("1.2×10^3+4.5×10^6", "1.2E+3+4.5E+6")
  }

  private fun assertLex(expected: String, actual: String) =
    assertEquals(expected, actual.tokenizeMath().joinToString("") { it.symbol })
}
