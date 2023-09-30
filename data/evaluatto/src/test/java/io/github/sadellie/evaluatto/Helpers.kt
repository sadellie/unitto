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

package io.github.sadellie.evaluatto

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import java.math.BigDecimal
import java.math.RoundingMode

fun assertExpr(expr: String, result: String, radianMode: Boolean = true) =
    assertEquals(
        BigDecimal(result).setScale(10, RoundingMode.HALF_EVEN),
        Expression(expr, radianMode).calculate().setScale(10, RoundingMode.HALF_EVEN)
    )

fun <T : Throwable?> assertExprFail(
    expectedThrowable: Class<T>?,
    expr: String,
    radianMode: Boolean = true
) {
    assertThrows(expectedThrowable) {
        Expression(expr, radianMode = radianMode).calculate()
    }
}

fun assertLex(expected: List<String>, actual: String) =
    assertEquals(expected, Tokenizer(actual).tokenize())

fun assertLex(expected: String, actual: String) =
    assertEquals(expected, Tokenizer(actual).tokenize().joinToString(""))
