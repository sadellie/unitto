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

package com.sadellie.unitto.core.ui

import com.sadellie.unitto.core.ui.common.textfield.ExpressionTransformer
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ExpressionTransformerTest {

    private val expr = ExpressionTransformer(FormatterSymbols.Comma)

    private fun origToTrans(orig: String, trans: String, offset: Int): Int =
        expr.ExpressionMapping(orig, trans).originalToTransformed(offset)

    private fun transToOrig(trans: String, orig: String, offset: Int): Int =
        expr.ExpressionMapping(orig, trans).transformedToOriginal(offset)

    @Test
    fun `test 1234`() {
        // at the start
        assertEquals(0, origToTrans("1,234", "1234", 0))
        assertEquals(0, transToOrig("1,234", "1234", 0))

        // somewhere in inside, no offset needed
        assertEquals(1, origToTrans("1234", "1,234", 1))
        assertEquals(1, transToOrig("1,234", "1234", 1))

        // somewhere in inside, offset needed
        assertEquals(1, transToOrig("1,234", "1234", 2))

        // at the end
        assertEquals(5, origToTrans("1234", "1,234", 4))
        assertEquals(4, transToOrig("1,234", "1234", 5))
    }

    @Test
    fun `test 123`() {
        // at the start
        assertEquals(0, origToTrans("123", "123", 0))
        assertEquals(0, transToOrig("123", "123", 0))

        // somewhere in inside
        assertEquals(1, origToTrans("123", "123", 1))
        assertEquals(1, transToOrig("123", "123", 1))

        // at the end
        assertEquals(3, origToTrans("123", "123", 3))
        assertEquals(3, transToOrig("123", "123", 3))
    }
}
