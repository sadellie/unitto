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
import org.junit.Assert.assertEquals
import org.junit.Test

class ExpressionTransformerTest {

    private val expr = ExpressionTransformer(FormatterSymbols.Comma)

    // Use "|" for cursor
    private fun origToTrans(orig: String, trans: String) {
        val transformed = trans.replace("|", "")
        val original = orig.replace("|", "")

        val offsetInTrans = trans.indexOf("|")
        val offsetInOrig = orig.indexOf("|")

        val expressionMapping = expr.ExpressionMapping(original, transformed)

        assertEquals(offsetInTrans, expressionMapping.originalToTransformed(offsetInOrig))
    }

    private fun transToOrig(trans: String, orig: String) {
        val transformed = trans.replace("|", "")
        val original = orig.replace("|", "")

        val offsetInTrans = trans.indexOf("|")
        val offsetInOrig = orig.indexOf("|")

        val expressionMapping = expr.ExpressionMapping(original, transformed)

        assertEquals(offsetInOrig, expressionMapping.transformedToOriginal(offsetInTrans))
    }

    @Test
    fun `test 1234`() {
        transToOrig("|123", "|123")
        origToTrans("12|3", "12|3")

        transToOrig("|1,234", "|1234")
        origToTrans("|1234", "|1,234")

        transToOrig("1,234|", "1234|")
        origToTrans("1234|", "1,234|")

        transToOrig("|cos(1)+1,234", "|cos(1)+1234")
        origToTrans("co|s(1)+1234", "|cos(1)+1,234")

        transToOrig("cos|(1)+1,234", "cos(|1)+1234")
        origToTrans("cos|(1)+1234", "cos(|1)+1,234")
    }
}
