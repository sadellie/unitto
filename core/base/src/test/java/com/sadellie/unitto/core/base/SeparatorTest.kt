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

package com.sadellie.unitto.core.base

import org.junit.Assert
import org.junit.Test

class SeparatorTest {

    @Test
    fun testExists() {
        Assert.assertNotNull(Separator)
    }

    @Test
    fun testSeparatorSpace() {
        Assert.assertEquals(0, Separator.SPACE)
    }

    @Test
    fun testSeparatorPeriod() {
        Assert.assertEquals(1, Separator.PERIOD)
    }

    @Test
    fun testSeparatorComma() {
        Assert.assertEquals(2, Separator.COMMA)
    }
}