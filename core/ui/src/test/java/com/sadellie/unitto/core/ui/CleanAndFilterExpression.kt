/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

import com.sadellie.unitto.core.base.FormatterSymbols
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.textfield.clearAndFilterExpression
import org.junit.Assert.assertEquals
import org.junit.Test

class CleanAndFilterExpression {

    private val formatterSymbols = FormatterSymbols(Token.COMMA, Token.PERIOD)

    @Test
    fun noAdditionalSymbols() {
        assertEquals("123", "123".clearAndFilterExpression(formatterSymbols))
        assertEquals("123.456", "123.456".clearAndFilterExpression(formatterSymbols))
    }

    @Test
    fun hasFormatterSymbol() {
        assertEquals("123456", "123,456".clearAndFilterExpression(formatterSymbols))
        assertEquals("123456.789", "123,456.789".clearAndFilterExpression(formatterSymbols))
    }

    @Test
    fun hasWrongFormatterSymbol() {
        assertEquals("123456", "123 456".clearAndFilterExpression(formatterSymbols))
        assertEquals("123456.789", "123 456.789".clearAndFilterExpression(formatterSymbols))
    }

    @Test
    fun fractionExpression() {
        assertEquals("1600+1234÷56789", "1,600 1234⁄56789".clearAndFilterExpression(formatterSymbols))
        assertEquals("123456.789+1234÷56789", "123,456.789 1234⁄56789".clearAndFilterExpression(formatterSymbols))
    }

    @Test
    fun garbage() {
        // 'e' is a known symbol
        assertEquals("eeee−123", "pee pee poo poo -123".clearAndFilterExpression(formatterSymbols))
        assertEquals("eeee−123.456", "pee pee poo poo -123.456".clearAndFilterExpression(formatterSymbols))
    }
}
