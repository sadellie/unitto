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

package com.sadellie.unitto.feature.calculator

import com.sadellie.unitto.core.base.Separator
import com.sadellie.unitto.core.ui.Formatter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class TextFieldControllerTest {
    private lateinit var textFieldController: TextFieldController

    private val TextFieldController.text: String
        get() = this.input.value.text
            .replace("`", ",")
            .replace("|", ".")

    private val TextFieldController.selection: IntRange
        get() = this.input.value.selection.start..this.input.value.selection.end

    @Before
    fun setUp() {
        textFieldController = TextFieldController()
        Formatter.setSeparator(Separator.COMMA)
    }

    @Test
    fun `add when empty`() {
        // Add one symbol
        textFieldController.addToInput("1")
        assertEquals("1", textFieldController.text)
        assertEquals(1..1, textFieldController.selection)
        textFieldController.clearInput()

        // Add multiple
        textFieldController.addToInput("123")
        assertEquals("123", textFieldController.text)
        assertEquals(3..3, textFieldController.selection)
        textFieldController.clearInput()

        // Add multiple
        textFieldController.addToInput("1234")
        assertEquals("1,234", textFieldController.text)
        assertEquals(5..5, textFieldController.selection)
        textFieldController.clearInput()

        // Add multiple
        textFieldController.addToInput("123456.789")
        assertEquals("123,456.789", textFieldController.text)
        assertEquals(11..11, textFieldController.selection)
        textFieldController.clearInput()
    }

    @Test
    fun `Add when not empty one symbol at a time (check formatting)`() {
        // Should be 1|
        textFieldController.addToInput("1")
        assertEquals("1", textFieldController.text)
        assertEquals(1..1, textFieldController.selection)

        // Should be 12|
        textFieldController.addToInput("2")
        assertEquals("12", textFieldController.text)
        assertEquals(2..2, textFieldController.selection)

        // Should be 123|
        textFieldController.addToInput("3")
        assertEquals("123", textFieldController.text)
        assertEquals(3..3, textFieldController.selection)

        // Should be 1,234|
        textFieldController.addToInput("4")
        assertEquals("1,234", textFieldController.text)
        assertEquals(5..5, textFieldController.selection)

        // Should be 12,345|
        textFieldController.addToInput("5")
        assertEquals("12,345", textFieldController.text)
        assertEquals(6..6, textFieldController.selection)
    }

    @Test
    fun `Delete on empty input`() {
        // Delete on empty input
        textFieldController.delete()
        assertEquals("", textFieldController.text)
        assertEquals(0..0, textFieldController.selection)
        textFieldController.clearInput()
    }

    @Test
    fun `Delete last remaining symbol`() {
        textFieldController.addToInput("1")
        textFieldController.delete()
        assertEquals("", textFieldController.text)
        assertEquals(0..0, textFieldController.selection)
        textFieldController.clearInput()
    }

    @Test
    fun `Delete by one symbol (check formatting)`() {
        textFieldController.addToInput("123456")
        // Input is formatted into 123,456
        textFieldController.delete()
        assertEquals("12,345", textFieldController.text)
        assertEquals(6..6, textFieldController.selection)
        textFieldController.delete()
        assertEquals("1,234", textFieldController.text)
        assertEquals(5..5, textFieldController.selection)
        textFieldController.delete()
        assertEquals("123", textFieldController.text)
        println("in 123: ${textFieldController.selection}")
        assertEquals(3..3, textFieldController.selection)
        textFieldController.clearInput()
    }

    @Test
    fun `Delete multiple symbols, selected before separator`() {
        textFieldController.addToInput("123789456")
        // Input is formatted to 123,789,456
        textFieldController.moveCursor(3..7)
        textFieldController.delete()
        assertEquals("123,456", textFieldController.text)
        assertEquals(3..3, textFieldController.selection)
        textFieldController.clearInput()
    }

    @Test
    fun `Delete multiple symbols, selected not near separator`() {
        textFieldController.addToInput("123789456")
        // Input is formatted to 123,789,456
        textFieldController.moveCursor(3..9)
        textFieldController.delete()
        assertEquals("12,356", textFieldController.text)
        assertEquals(4..4, textFieldController.selection)
        textFieldController.clearInput()
    }

    @Test
    fun `Delete multiple symbols in weird input`() {
        textFieldController.addToInput("123...789456")
        // Input is formatted to 123...789456
        textFieldController.moveCursor(3..9)
        textFieldController.delete()
        assertEquals(4..4, textFieldController.selection)
        assertEquals("123,456", textFieldController.text)
        textFieldController.clearInput()
    }

    @Test
    fun `placed cursor illegally`() {
        textFieldController.addToInput("123456.789")
        // Input is 123,456.789
        textFieldController.moveCursor(4..4)
        // Cursor should be placed like this 123|,456.789
        assertEquals(3..3, textFieldController.selection)
        textFieldController.clearInput()

        textFieldController.addToInput("123456.789+cos(")
        // Input is 123,456.789+cos(
        textFieldController.moveCursor(13..13)
        // Cursor should be placed like this 123,456.789+c|os(
        assertEquals(12..12, textFieldController.selection)
        textFieldController.clearInput()
    }

    @Test
    fun `get clear input text without formatting`() {
        textFieldController.addToInput("123456.789+cos(..)")
        // Input is 123,456.789

        assertEquals("123456.789+cos(..)", textFieldController.inputTextWithoutFormatting())
    }
}
