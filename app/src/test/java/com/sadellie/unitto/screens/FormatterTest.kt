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

class FormatterTest {

    @Test
    fun setSeparatorSpaces() {
        formatter.setSeparator(Separator.SPACES)
        assertEquals(".", formatter.fractional)
    }

    @Test
    fun setSeparatorComma() {
        formatter.setSeparator(Separator.COMMA)
        assertEquals(".", formatter.fractional)
    }

    @Test
    fun setSeparatorPeriod() {
        formatter.setSeparator(Separator.PERIOD)
        assertEquals(",", formatter.fractional)
    }

    // ENGINEERING
    @Test
    fun formatEngineeringWithSpaces() {
        formatter.setSeparator(Separator.SPACES)
        assertEquals("123.3E+21", formatter.format(ENG_VALUE))
    }

    @Test
    fun formatEngineeringWithComma() {
        formatter.setSeparator(Separator.COMMA)
        assertEquals("123.3E+21", formatter.format(ENG_VALUE))
    }

    @Test
    fun formatEngineeringWithPeriod() {
        formatter.setSeparator(Separator.PERIOD)
        assertEquals("123,3E+21", formatter.format(ENG_VALUE))
    }

    // COMPLETE
    @Test
    fun formatCompleteWithSpaces() {
        formatter.setSeparator(Separator.SPACES)
        assertEquals("123 456.789", formatter.format(COMPLETE_VALUE))
    }

    @Test
    fun formatCompleteWithComma() {
        formatter.setSeparator(Separator.COMMA)
        assertEquals("123,456.789", formatter.format(COMPLETE_VALUE))
    }

    @Test
    fun formatCompleteWithPeriod() {
        formatter.setSeparator(Separator.PERIOD)
        assertEquals("123.456,789", formatter.format(COMPLETE_VALUE))
    }

    // INCOMPLETE
    @Test
    fun formatIncompleteWithSpaces() {
        formatter.setSeparator(Separator.SPACES)
        assertEquals("123 456.", formatter.format(INCOMPLETE_VALUE))
    }

    @Test
    fun formatIncompleteWithComma() {
        formatter.setSeparator(Separator.COMMA)
        assertEquals("123,456.", formatter.format(INCOMPLETE_VALUE))
    }

    @Test
    fun formatIncompleteWithPeriod() {
        formatter.setSeparator(Separator.PERIOD)
        assertEquals("123.456,", formatter.format(INCOMPLETE_VALUE))
    }

}