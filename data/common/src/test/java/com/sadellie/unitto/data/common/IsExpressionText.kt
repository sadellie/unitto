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

package com.sadellie.unitto.data.common

import org.junit.Assert.assertEquals
import org.junit.Test


class IsExpressionText {
    @Test
    fun `empty string`() = assertEquals(false, "".isExpression())

    @Test
    fun `positive real number`() = assertEquals(false, "123".isExpression())

    @Test
    fun `positive float`() = assertEquals(false, "123.123".isExpression())

    @Test
    fun `negative real`() = assertEquals(false, "−123".isExpression())

    @Test
    fun `negative float`() = assertEquals(false, "−123.123".isExpression())

    @Test
    fun `super negative float`() = assertEquals(false, "−−123.123".isExpression())

    @Test
    fun expression1() = assertEquals(true, "123.123+456".isExpression())

    @Test
    fun expression2() = assertEquals(true, "−123.123+456".isExpression())
}
