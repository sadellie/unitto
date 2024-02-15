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

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.textfield.addBracket
import com.sadellie.unitto.core.ui.common.textfield.addTokens
import org.junit.Assert.assertEquals
import org.junit.Test

class TextFieldValueExtensionsTest {

    @Test
    fun addPlus() {
        // EMPTY
        assertEquals(tf("[]123+456"), tf("[]123+456").addTokens(Token.Operator.plus))
        assertEquals(tf("[]+456"), tf("[123]+456").addTokens(Token.Operator.plus))
        assertEquals(tf("[]"), tf("[123+456]").addTokens(Token.Operator.plus))

        // PLUS
        assertEquals(tf("123+[]"), tf("123+[]").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]"), tf("123[+]").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]56"), tf("123+[4]56").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]"), tf("123+[456]").addTokens(Token.Operator.plus))

        // MINUS
        assertEquals(tf("123+[]"), tf("123-[]").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]"), tf("123[-]").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]56"), tf("123-[4]56").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]"), tf("123-[456]").addTokens(Token.Operator.plus))

        // MULTIPLY
        assertEquals(tf("123+[]"), tf("123*[]").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]"), tf("123[*]").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]56"), tf("123*[4]56").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]"), tf("123*[456]").addTokens(Token.Operator.plus))

        // DIVIDE
        assertEquals(tf("123+[]"), tf("123/[]").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]"), tf("123[/]").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]56"), tf("123/[4]56").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]"), tf("123/[456]").addTokens(Token.Operator.plus))

        // SQRT
        assertEquals(tf("123+[]"), tf("123√[]").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]"), tf("123[√]").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]56"), tf("123√[4]56").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]"), tf("123√[456]").addTokens(Token.Operator.plus))

        // POWER
        assertEquals(tf("123+[]"), tf("123^[]").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]"), tf("123[^]").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]56"), tf("123^[4]56").addTokens(Token.Operator.plus))
        assertEquals(tf("123+[]"), tf("123^[456]").addTokens(Token.Operator.plus))
    }

    @Test
    fun addMinus() {
        // EMPTY
        assertEquals(tf("-[]123+456"), tf("[]123+456").addTokens(Token.Operator.minus))
        assertEquals(tf("-[]+456"), tf("[123]+456").addTokens(Token.Operator.minus))
        assertEquals(tf("-[]"), tf("[123+456]").addTokens(Token.Operator.minus))

        // PLUS
        assertEquals(tf("123-[]"), tf("123+[]").addTokens(Token.Operator.minus))
        assertEquals(tf("123-[]"), tf("123[+]").addTokens(Token.Operator.minus))
        assertEquals(tf("123-[]56"), tf("123+[4]56").addTokens(Token.Operator.minus))
        assertEquals(tf("123-[]"), tf("123+[456]").addTokens(Token.Operator.minus))

        // MINUS
        assertEquals(tf("123-[]"), tf("123-[]").addTokens(Token.Operator.minus))
        assertEquals(tf("123-[]"), tf("123[-]").addTokens(Token.Operator.minus))
        assertEquals(tf("123-[]56"), tf("123-[4]56").addTokens(Token.Operator.minus))
        assertEquals(tf("123-[]"), tf("123-[456]").addTokens(Token.Operator.minus))

        // MULTIPLY
        assertEquals(tf("123*-[]"), tf("123*[]").addTokens(Token.Operator.minus))
        assertEquals(tf("123-[]"), tf("123[*]").addTokens(Token.Operator.minus))
        assertEquals(tf("123*-[]56"), tf("123*[4]56").addTokens(Token.Operator.minus))
        assertEquals(tf("123*-[]"), tf("123*[456]").addTokens(Token.Operator.minus))

        // DIVIDE
        assertEquals(tf("123/-[]"), tf("123/[]").addTokens(Token.Operator.minus))
        assertEquals(tf("123-[]"), tf("123[/]").addTokens(Token.Operator.minus))
        assertEquals(tf("123/-[]56"), tf("123/[4]56").addTokens(Token.Operator.minus))
        assertEquals(tf("123/-[]"), tf("123/[456]").addTokens(Token.Operator.minus))

        // SQRT
        assertEquals(tf("123√-[]"), tf("123√[]").addTokens(Token.Operator.minus))
        assertEquals(tf("123-[]"), tf("123[√]").addTokens(Token.Operator.minus))
        assertEquals(tf("123√-[]56"), tf("123√[4]56").addTokens(Token.Operator.minus))
        assertEquals(tf("123√-[]"), tf("123√[456]").addTokens(Token.Operator.minus))

        // POWER
        assertEquals(tf("123^-[]"), tf("123^[]").addTokens(Token.Operator.minus))
        assertEquals(tf("123-[]"), tf("123[^]").addTokens(Token.Operator.minus))
        assertEquals(tf("123^-[]56"), tf("123^[4]56").addTokens(Token.Operator.minus))
        assertEquals(tf("123^-[]"), tf("123^[456]").addTokens(Token.Operator.minus))
    }

    @Test
    fun addMultiply() {
        // EMPTY
        assertEquals(tf("[]123+456"), tf("[]123+456").addTokens(Token.Operator.multiply))
        assertEquals(tf("[]+456"), tf("[123]+456").addTokens(Token.Operator.multiply))
        assertEquals(tf("[]"), tf("[123+456]").addTokens(Token.Operator.multiply))

        // PLUS
        assertEquals(tf("123*[]"), tf("123+[]").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]"), tf("123[+]").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]56"), tf("123+[4]56").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]"), tf("123+[456]").addTokens(Token.Operator.multiply))

        // MINUS
        assertEquals(tf("123*[]"), tf("123-[]").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]"), tf("123[-]").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]56"), tf("123-[4]56").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]"), tf("123-[456]").addTokens(Token.Operator.multiply))

        // MULTIPLY
        assertEquals(tf("123*[]"), tf("123*[]").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]"), tf("123[*]").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]56"), tf("123*[4]56").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]"), tf("123*[456]").addTokens(Token.Operator.multiply))

        // DIVIDE
        assertEquals(tf("123*[]"), tf("123/[]").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]"), tf("123[/]").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]56"), tf("123/[4]56").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]"), tf("123/[456]").addTokens(Token.Operator.multiply))

        // SQRT
        assertEquals(tf("123*[]"), tf("123√[]").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]"), tf("123[√]").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]56"), tf("123√[4]56").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]"), tf("123√[456]").addTokens(Token.Operator.multiply))

        // POWER
        assertEquals(tf("123*[]"), tf("123^[]").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]"), tf("123[^]").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]56"), tf("123^[4]56").addTokens(Token.Operator.multiply))
        assertEquals(tf("123*[]"), tf("123^[456]").addTokens(Token.Operator.multiply))
    }

    @Test
    fun addDivide() {
        // EMPTY
        assertEquals(tf("[]123+456"), tf("[]123+456").addTokens(Token.Operator.divide))
        assertEquals(tf("[]+456"), tf("[123]+456").addTokens(Token.Operator.divide))
        assertEquals(tf("[]"), tf("[123+456]").addTokens(Token.Operator.divide))

        // PLUS
        assertEquals(tf("123/[]"), tf("123+[]").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]"), tf("123[+]").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]56"), tf("123+[4]56").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]"), tf("123+[456]").addTokens(Token.Operator.divide))

        // MINUS
        assertEquals(tf("123/[]"), tf("123-[]").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]"), tf("123[-]").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]56"), tf("123-[4]56").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]"), tf("123-[456]").addTokens(Token.Operator.divide))

        // MULTIPLY
        assertEquals(tf("123/[]"), tf("123*[]").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]"), tf("123[*]").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]56"), tf("123*[4]56").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]"), tf("123*[456]").addTokens(Token.Operator.divide))

        // DIVIDE
        assertEquals(tf("123/[]"), tf("123/[]").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]"), tf("123[/]").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]56"), tf("123/[4]56").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]"), tf("123/[456]").addTokens(Token.Operator.divide))

        // SQRT
        assertEquals(tf("123/[]"), tf("123√[]").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]"), tf("123[√]").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]56"), tf("123√[4]56").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]"), tf("123√[456]").addTokens(Token.Operator.divide))

        // POWER
        assertEquals(tf("123/[]"), tf("123^[]").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]"), tf("123[^]").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]56"), tf("123^[4]56").addTokens(Token.Operator.divide))
        assertEquals(tf("123/[]"), tf("123^[456]").addTokens(Token.Operator.divide))
    }

    @Test
    fun addPower() {
        // EMPTY
        assertEquals(tf("[]123+456"), tf("[]123+456").addTokens(Token.Operator.power))
        assertEquals(tf("[]+456"), tf("[123]+456").addTokens(Token.Operator.power))
        assertEquals(tf("[]"), tf("[123+456]").addTokens(Token.Operator.power))

        // PLUS
        assertEquals(tf("123^[]"), tf("123+[]").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]"), tf("123[+]").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]56"), tf("123+[4]56").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]"), tf("123+[456]").addTokens(Token.Operator.power))

        // MINUS
        assertEquals(tf("123^[]"), tf("123-[]").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]"), tf("123[-]").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]56"), tf("123-[4]56").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]"), tf("123-[456]").addTokens(Token.Operator.power))

        // MULTIPLY
        assertEquals(tf("123^[]"), tf("123*[]").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]"), tf("123[*]").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]56"), tf("123*[4]56").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]"), tf("123*[456]").addTokens(Token.Operator.power))

        // DIVIDE
        assertEquals(tf("123^[]"), tf("123/[]").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]"), tf("123[/]").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]56"), tf("123/[4]56").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]"), tf("123/[456]").addTokens(Token.Operator.power))

        // SQRT
        assertEquals(tf("123^[]"), tf("123√[]").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]"), tf("123[√]").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]56"), tf("123√[4]56").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]"), tf("123√[456]").addTokens(Token.Operator.power))

        // POWER
        assertEquals(tf("123^[]"), tf("123^[]").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]"), tf("123[^]").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]56"), tf("123^[4]56").addTokens(Token.Operator.power))
        assertEquals(tf("123^[]"), tf("123^[456]").addTokens(Token.Operator.power))
    }

    @Test
    fun addDot() {
        // EMPTY
        assertEquals(tf(".[]123+456"), tf("[]123+456").addTokens(Token.Digit.dot))
        assertEquals(tf(".[]+456"), tf("[123]+456").addTokens(Token.Digit.dot))
        assertEquals(tf(".[]"), tf("[123+456]").addTokens(Token.Digit.dot))

        assertEquals(tf("123+456.[]78"), tf("123+456.[]78").addTokens(Token.Digit.dot))
        assertEquals(tf("123+456.[]78"), tf("123+456[.]78").addTokens(Token.Digit.dot))
        assertEquals(tf("123+456.[]8"), tf("123+456[.7]8").addTokens(Token.Digit.dot))
        assertEquals(tf("123+45.[]78"), tf("123+45[6.]78").addTokens(Token.Digit.dot))
    }

    @Test
    fun auto() {
        // Open on empty in front
        assertEquals(tf("([]"), tf("[]").addBracket())
        assertEquals(tf("([]123("), tf("[]123(").addBracket())
        assertEquals(tf("([]("), tf("[123](").addBracket())
        assertEquals(tf("([]"), tf("[123(]").addBracket())

        // Close before multiply
        assertEquals(tf("123)[]*456"), tf("123[]*456").addBracket())
        assertEquals(tf("(123)[]*456"), tf("(123[]*456").addBracket())
        assertEquals(tf(")123)[]*456"), tf(")123[]*456").addBracket())
        // Close before divide
        assertEquals(tf("123)[]/456"), tf("123[]/456").addBracket())
        assertEquals(tf("(123)[]/456"), tf("(123[]/456").addBracket())
        assertEquals(tf(")123)[]/456"), tf(")123[]/456").addBracket())
        // Close before plus
        assertEquals(tf("123)[]+456"), tf("123[]+456").addBracket())
        assertEquals(tf("(123)[]+456"), tf("(123[]+456").addBracket())
        assertEquals(tf(")123)[]+456"), tf(")123[]+456").addBracket())
        // Close before minus
        assertEquals(tf("123)[]-456"), tf("123[]-456").addBracket())
        assertEquals(tf("(123)[]-456"), tf("(123[]-456").addBracket())
        assertEquals(tf(")123)[]-456"), tf(")123[]-456").addBracket())
        // Close before power
        assertEquals(tf("123)[]^456"), tf("123[]^456").addBracket())
        assertEquals(tf("(123)[]^456"), tf("(123[]^456").addBracket())
        assertEquals(tf(")123)[]^456"), tf(")123[]^456").addBracket())

        // Open on balanced in front
        assertEquals(tf("123([]"), tf("123[]").addBracket())
        assertEquals(tf("123([](((("), tf("123[]((((").addBracket())

        // Open after multiply
        assertEquals(tf("123*([]456"), tf("123*[]456").addBracket())
        assertEquals(tf("123*([]"), tf("123*[456]").addBracket())
        // Open after divide
        assertEquals(tf("123/([]456"), tf("123/[]456").addBracket())
        assertEquals(tf("123/([]"), tf("123/[456]").addBracket())
        // Open after plus
        assertEquals(tf("123+([]456"), tf("123+[]456").addBracket())
        assertEquals(tf("123+([]"), tf("123+[456]").addBracket())
        // Open after minus
        assertEquals(tf("123-([]456"), tf("123-[]456").addBracket())
        assertEquals(tf("123-([]"), tf("123-[456]").addBracket())
        // Open after power
        assertEquals(tf("123^([]456"), tf("123^[]456").addBracket())
        assertEquals(tf("123^([]"), tf("123^[456]").addBracket())

        // Default
        assertEquals(tf("123([]"), tf("123[]").addBracket())
        assertEquals(tf("123(456+789)[]"), tf("123(456+789[]").addBracket())
    }

    // Use [] for selection
    private fun tf(
        text: String = "",
    ): TextFieldValue {
        val selectionStart = text.indexOf("[")
        val selectionEnd = text.indexOf("]") - 1

        if (selectionStart < 0) throw Exception("forgot selectionStart")
        if (selectionEnd < 0) throw Exception("forgot selectionEnd")

        return TextFieldValue(
            text = text
                .replace("[", "")
                .replace("]", "")
                .replace("-", Token.Operator.minus)
                .replace("/", Token.Operator.divide)
                .replace("*", Token.Operator.multiply),
            selection = TextRange(selectionStart, selectionEnd),
        )
    }
}
