/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

package com.sadellie.unitto.core.ui

import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.formatExpression
import org.junit.Assert.assertEquals
import org.junit.Test

private const val ENG_VALUE = "123E+21"
private const val ENG_VALUE_FRACTIONAL = "123.3E+21"
private const val COMPLETE_VALUE = "123456.789"
private const val INCOMPLETE_VALUE = "123456."
private const val NO_FRACTIONAL_VALUE = "123456"
private const val INCOMPLETE_EXPR = "50+123456÷8×0.8-12+"
private const val COMPLETE_EXPR = "50+123456÷8×0.8-12+0-√9×4^9+2×(9+8×7)"
private const val LONG_HALF_COMPLETE_EXPR = "50+123456÷89078..9×0.8-12+0-√9×4^9+2×(9+8×7)×sin(13sin123cos"
private const val SOME_BRACKETS = "(((((((("
private const val FRACTION_VALUE = "1600 1234⁄56789"

class FormatterExpressionTest {

    @Test
    fun setSeparatorSpaces() {
        fun String.format(): String = formatExpression(FormatterSymbols.Spaces)
        assertEquals("123E+21", ENG_VALUE.format())
        assertEquals("123.3E+21", ENG_VALUE_FRACTIONAL.format())
        assertEquals("123 456.789", COMPLETE_VALUE.format())
        assertEquals("123 456.", INCOMPLETE_VALUE.format())
        assertEquals("123 456", NO_FRACTIONAL_VALUE.format())
        assertEquals("50+123 456÷8×0.8−12+", INCOMPLETE_EXPR.format())
        assertEquals("50+123 456÷8×0.8−12+0−√9×4^9+2×(9+8×7)", COMPLETE_EXPR.format())
        assertEquals("50+123 456÷89 078..9×0.8−12+0−√9×4^9+2×(9+8×7)×sin(13sin123cos", LONG_HALF_COMPLETE_EXPR.format())
        assertEquals("((((((((", SOME_BRACKETS.format())
        assertEquals("1 600 1234⁄56789", FRACTION_VALUE.format())
    }

    @Test
    fun setSeparatorComma() {
        fun String.format(): String = formatExpression(FormatterSymbols.Comma)
        assertEquals("123E+21", ENG_VALUE.format())
        assertEquals("123.3E+21", ENG_VALUE_FRACTIONAL.format())
        assertEquals("123,456.789", COMPLETE_VALUE.format())
        assertEquals("123,456.", INCOMPLETE_VALUE.format())
        assertEquals("123,456", NO_FRACTIONAL_VALUE.format())
        assertEquals("50+123,456÷8×0.8−12+", INCOMPLETE_EXPR.format())
        assertEquals("50+123,456÷8×0.8−12+0−√9×4^9+2×(9+8×7)", COMPLETE_EXPR.format())
        assertEquals("50+123,456÷89,078..9×0.8−12+0−√9×4^9+2×(9+8×7)×sin(13sin123cos", LONG_HALF_COMPLETE_EXPR.format())
        assertEquals("((((((((", SOME_BRACKETS.format())
        assertEquals("1,600 1234⁄56789", FRACTION_VALUE.format())
    }

    @Test
    fun setSeparatorPeriod() {
        fun String.format(): String = formatExpression(FormatterSymbols.Period)
        assertEquals("123E+21", ENG_VALUE.format())
        assertEquals("123,3E+21", ENG_VALUE_FRACTIONAL.format())
        assertEquals("123.456,789", COMPLETE_VALUE.format())
        assertEquals("123.456,", INCOMPLETE_VALUE.format())
        assertEquals("123.456", NO_FRACTIONAL_VALUE.format())
        assertEquals("50+123.456÷8×0,8−12+", INCOMPLETE_EXPR.format())
        assertEquals("50+123.456÷8×0,8−12+0−√9×4^9+2×(9+8×7)", COMPLETE_EXPR.format())
        assertEquals("50+123.456÷89.078,,9×0,8−12+0−√9×4^9+2×(9+8×7)×sin(13sin123cos", LONG_HALF_COMPLETE_EXPR.format())
        assertEquals("((((((((", SOME_BRACKETS.format())
        assertEquals("1.600 1234⁄56789", FRACTION_VALUE.format())
    }
}