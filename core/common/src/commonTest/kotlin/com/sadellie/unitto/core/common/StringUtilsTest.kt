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

package com.sadellie.unitto.core.common

import kotlin.test.Test
import kotlin.test.assertEquals

class StringUtilsTest {

  @Test fun lev_identical() = assertEquals(0, "popcorn and bananas".lev("popcorn and bananas"))

  @Test fun lev_oneReplace() = assertEquals(1, "popcorn and bananas".lev("popcorn and bananan"))

  @Test fun lev_oneAdd() = assertEquals(1, "popcorn and bananas".lev("popcorn and banana"))

  @Test fun lev_oneDelete() = assertEquals(1, "popcorn and bananas".lev("popcorn and bananasa"))

  @Test fun lev_completelyDifferent() = assertEquals(10, "red truck".lev("tinny kitty"))

  @Test fun lev_emptyA() = assertEquals(11, "".lev("tinny kitty"))

  @Test fun lev_emptyB() = assertEquals(9, "red truck".lev(""))

  @Test fun lev_differentCases() = assertEquals(0, "red truck".lev("red TRUCK"))

  @Test fun isExpression_emptyString() = assertEquals(false, "".isExpression())

  @Test fun isExpression_positiveRealNumber() = assertEquals(false, "123".isExpression())

  @Test fun isExpression_positiveFloat() = assertEquals(false, "123.123".isExpression())

  @Test fun isExpression_negativeReal() = assertEquals(false, "−123".isExpression())

  @Test fun isExpression_negativeFloat() = assertEquals(false, "−123.123".isExpression())

  @Test fun isExpression_superNegativeFloat() = assertEquals(false, "−−123.123".isExpression())

  @Test fun isExpression_valid1() = assertEquals(true, "123.123+456".isExpression())

  @Test fun isExpression_valid2() = assertEquals(true, "−123.123+456".isExpression())

  @Test
  fun normalizeSuperscript_test() = assertEquals("0123456789", "⁰¹²³⁴⁵⁶⁷⁸⁹".normalizeSuperscript())
}
