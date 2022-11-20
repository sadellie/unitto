/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

package com.sadellie.unitto.screens

import com.sadellie.unitto.data.preferences.Separator
import org.junit.Assert.assertEquals
import org.junit.Test

private val formatter = Formatter

private const val ENG_VALUE = "123.3E+21"
private const val COMPLETE_VALUE = "123456.789"
private const val INCOMPLETE_VALUE = "123456."
private const val NO_FRACTIONAL_VALUE = "123456"
private const val INCOMPLETE_EXPR = "50+123456/8*0.8-12+"
private const val COMPLETE_EXPR = "50+123456/8*0.8-12+0"

class FormatterTest {

    @Test
    fun setSeparatorSpaces() {
        formatter.setSeparator(Separator.SPACES)
        assertEquals(".", formatter.fractional)
        assertEquals("123.3E+21", formatter.format(ENG_VALUE))
        assertEquals("123 456.789", formatter.format(COMPLETE_VALUE))
        assertEquals("123 456.", formatter.format(INCOMPLETE_VALUE))
        assertEquals("123 456", formatter.format(NO_FRACTIONAL_VALUE))
        assertEquals("50+123 456÷8×0.8–12+", formatter.format(INCOMPLETE_EXPR))
        assertEquals("50+123 456÷8×0.8–12+0", formatter.format(COMPLETE_EXPR))
    }

    @Test
    fun setSeparatorComma() {
        formatter.setSeparator(Separator.COMMA)
        assertEquals(".", formatter.fractional)
        assertEquals("123.3E+21", formatter.format(ENG_VALUE))
        assertEquals("123,456.789", formatter.format(COMPLETE_VALUE))
        assertEquals("123,456.", formatter.format(INCOMPLETE_VALUE))
        assertEquals("123,456", formatter.format(NO_FRACTIONAL_VALUE))
        assertEquals("50+123,456÷8×0.8–12+", formatter.format(INCOMPLETE_EXPR))
        assertEquals("50+123,456÷8×0.8–12+0", formatter.format(COMPLETE_EXPR))
    }

    @Test
    fun setSeparatorPeriod() {
        formatter.setSeparator(Separator.PERIOD)
        assertEquals(",", formatter.fractional)
        assertEquals("123,3E+21", formatter.format(ENG_VALUE))
        assertEquals("123.456,789", formatter.format(COMPLETE_VALUE))
        assertEquals("123.456,", formatter.format(INCOMPLETE_VALUE))
        assertEquals("123.456", formatter.format(NO_FRACTIONAL_VALUE))
        assertEquals("50+123.456÷8×0,8–12+", formatter.format(INCOMPLETE_EXPR))
        assertEquals("50+123.456÷8×0,8–12+0", formatter.format(COMPLETE_EXPR))
    }

}