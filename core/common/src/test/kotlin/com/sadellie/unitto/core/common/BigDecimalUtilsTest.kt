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

import java.math.BigDecimal
import org.junit.Assert.assertEquals
import org.junit.Test

class BigDecimalUtilsTest {
  @Test
  fun toFormattedString_test() {
    val defaultPrecision = 3

    // zero
    var input = BigDecimal("0")
    assertEquals("0", input.toFormattedString(defaultPrecision, OutputFormat.PLAIN))
    assertEquals("0", input.toFormattedString(defaultPrecision, OutputFormat.ALLOW_ENGINEERING))
    assertEquals("0", input.toFormattedString(defaultPrecision, OutputFormat.FORCE_ENGINEERING))

    // zero with scale
    input = BigDecimal("0.0000")
    assertEquals("0", input.toFormattedString(defaultPrecision, OutputFormat.PLAIN))
    assertEquals("0", input.toFormattedString(defaultPrecision, OutputFormat.ALLOW_ENGINEERING))
    assertEquals("0", input.toFormattedString(defaultPrecision, OutputFormat.FORCE_ENGINEERING))

    // negative with no scale
    input = BigDecimal("-123")
    assertEquals("-123", input.toFormattedString(defaultPrecision, OutputFormat.PLAIN))
    assertEquals("-123", input.toFormattedString(defaultPrecision, OutputFormat.ALLOW_ENGINEERING))
    assertEquals("-123", input.toFormattedString(defaultPrecision, OutputFormat.FORCE_ENGINEERING))

    // negative with some trailing zeros
    input = BigDecimal("-123.0000")
    assertEquals("-123", input.toFormattedString(defaultPrecision, OutputFormat.PLAIN))
    assertEquals("-123", input.toFormattedString(defaultPrecision, OutputFormat.ALLOW_ENGINEERING))
    assertEquals("-123", input.toFormattedString(defaultPrecision, OutputFormat.FORCE_ENGINEERING))

    // negative with some scale
    input = BigDecimal("-123.4567")
    assertEquals("-123.457", input.toFormattedString(defaultPrecision, OutputFormat.PLAIN))
    assertEquals(
      "-123.457",
      input.toFormattedString(defaultPrecision, OutputFormat.ALLOW_ENGINEERING),
    )
    assertEquals(
      "-123.457",
      input.toFormattedString(defaultPrecision, OutputFormat.FORCE_ENGINEERING),
    )

    // positive with no scale
    input = BigDecimal("123")
    assertEquals("123", input.toFormattedString(defaultPrecision, OutputFormat.PLAIN))
    assertEquals("123", input.toFormattedString(defaultPrecision, OutputFormat.ALLOW_ENGINEERING))
    assertEquals("123", input.toFormattedString(defaultPrecision, OutputFormat.FORCE_ENGINEERING))

    // positive with some trailing zeros
    input = BigDecimal("123.0000")
    assertEquals("123", input.toFormattedString(defaultPrecision, OutputFormat.PLAIN))
    assertEquals("123", input.toFormattedString(defaultPrecision, OutputFormat.ALLOW_ENGINEERING))
    assertEquals("123", input.toFormattedString(defaultPrecision, OutputFormat.FORCE_ENGINEERING))

    // positive with some scale
    input = BigDecimal("123.4567")
    assertEquals("123.457", input.toFormattedString(defaultPrecision, OutputFormat.PLAIN))
    assertEquals(
      "123.457",
      input.toFormattedString(defaultPrecision, OutputFormat.ALLOW_ENGINEERING),
    )
    assertEquals(
      "123.457",
      input.toFormattedString(defaultPrecision, OutputFormat.FORCE_ENGINEERING),
    )
  }

  @Test
  fun trimZeros_test() {
    // zero
    var input = BigDecimal("0")
    assertEquals(BigDecimal("0"), input.trimZeros())
    assertEquals(BigDecimal("0"), input.trimZeros())
    assertEquals(BigDecimal("0"), input.trimZeros())

    // zero with scale
    input = BigDecimal("0.0000")
    assertEquals(BigDecimal("0"), input.trimZeros())
    assertEquals(BigDecimal("0"), input.trimZeros())
    assertEquals(BigDecimal("0"), input.trimZeros())

    // negative with no scale
    input = BigDecimal("-123")
    assertEquals(BigDecimal("-123"), input.trimZeros())
    assertEquals(BigDecimal("-123"), input.trimZeros())
    assertEquals(BigDecimal("-123"), input.trimZeros())

    // negative with some trailing zeros
    input = BigDecimal("-123.0000")
    assertEquals(BigDecimal("-123"), input.trimZeros())
    assertEquals(BigDecimal("-123"), input.trimZeros())
    assertEquals(BigDecimal("-123"), input.trimZeros())

    // negative with some scale
    input = BigDecimal("-123.4567")
    assertEquals(BigDecimal("-123.4567"), input.trimZeros())
    assertEquals(BigDecimal("-123.4567"), input.trimZeros())
    assertEquals(BigDecimal("-123.4567"), input.trimZeros())

    // positive with no scale
    input = BigDecimal("123")
    assertEquals(BigDecimal("123"), input.trimZeros())
    assertEquals(BigDecimal("123"), input.trimZeros())
    assertEquals(BigDecimal("123"), input.trimZeros())

    // positive with some trailing zeros
    input = BigDecimal("123.0000")
    assertEquals(BigDecimal("123"), input.trimZeros())
    assertEquals(BigDecimal("123"), input.trimZeros())
    assertEquals(BigDecimal("123"), input.trimZeros())

    // positive with some scale
    input = BigDecimal("123.4567")
    assertEquals(BigDecimal("123.4567"), input.trimZeros())
    assertEquals(BigDecimal("123.4567"), input.trimZeros())
    assertEquals(BigDecimal("123.4567"), input.trimZeros())
  }

  @Test
  fun isEqualTo_test() {
    var leftSide = BigDecimal("0")
    var rightSide = BigDecimal("0")
    assertEquals(true, leftSide.isEqualTo(rightSide))
    assertEquals(false, leftSide.isGreaterThan(rightSide))
    assertEquals(false, leftSide.isLessThan(rightSide))

    // scale should not matter
    leftSide = BigDecimal("0.000")
    rightSide = BigDecimal("0.000000")
    assertEquals(true, leftSide.isEqualTo(rightSide))
    assertEquals(false, leftSide.isGreaterThan(rightSide))
    assertEquals(false, leftSide.isLessThan(rightSide))
    leftSide = BigDecimal("0.1234")
    rightSide = BigDecimal("0.1234000")
    assertEquals(true, leftSide.isEqualTo(rightSide))
    assertEquals(false, leftSide.isGreaterThan(rightSide))
    assertEquals(false, leftSide.isLessThan(rightSide))

    // scale should not matter
    leftSide = BigDecimal("0.1234")
    rightSide = BigDecimal("0.1234567")
    assertEquals(false, leftSide.isEqualTo(rightSide))
    assertEquals(false, leftSide.isGreaterThan(rightSide))
    assertEquals(true, leftSide.isLessThan(rightSide))

    // scale should not matter
    leftSide = BigDecimal("0.1234567")
    rightSide = BigDecimal("0.1234")
    assertEquals(false, leftSide.isEqualTo(rightSide))
    assertEquals(true, leftSide.isGreaterThan(rightSide))
    assertEquals(false, leftSide.isLessThan(rightSide))
  }

  @Test
  fun setMinimumRequiredScale_test() {
    // prefer scale of 2, which is too low
    var bd = BigDecimal("0.000000123456")
    assertEquals("0.0000001", bd.setMinimumRequiredScale(2).toPlainString())

    // prefer scale of 15, which is too high
    bd = BigDecimal("0.000000123456")
    assertEquals("0.000000123456000", bd.setMinimumRequiredScale(15).toPlainString())

    // prefer scale of 9, which will cut this value
    bd = BigDecimal("0.000000123456")
    assertEquals("0.000000123", bd.setMinimumRequiredScale(9).toPlainString())

    // small, but has no leading zeroes in fractional part, should use preferred scale
    bd = BigDecimal("0.123456")
    assertEquals("0.123", bd.setMinimumRequiredScale(3).toPlainString())

    // big value
    bd = BigDecimal("123.000000123456")
    assertEquals("123.000000123", bd.setMinimumRequiredScale(9).toPlainString())

    // zero without scale
    bd = BigDecimal("0")
    assertEquals("0", bd.setMinimumRequiredScale(3).toPlainString())

    // big value without scale
    bd = BigDecimal("123")
    assertEquals("123", bd.setMinimumRequiredScale(3).toPlainString())
  }
}
