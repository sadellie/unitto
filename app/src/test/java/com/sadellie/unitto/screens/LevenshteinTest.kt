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

import org.junit.Assert.assertEquals
import org.junit.Test

class LevenshteinTest {

    @Test
    fun levIdentical() {
        assertEquals(0, "popcorn and bananas".lev("popcorn and bananas"))
    }

    @Test
    fun levOneReplace() {
        assertEquals(1, "popcorn and bananas".lev("popcorn and bananan"))
    }

    @Test
    fun levOneAdd() {
        assertEquals(1, "popcorn and bananas".lev("popcorn and banana"))
    }

    @Test
    fun levOneDelete() {
        assertEquals(1, "popcorn and bananas".lev("popcorn and bananasa"))
    }

    @Test
    fun levCompletelyDifferent() {
        assertEquals(10, "red truck".lev("tinny kitty"))
    }

    @Test
    fun levEmptyA() {
        assertEquals(11, "".lev("tinny kitty"))
    }

    @Test
    fun levEmptyB() {
        assertEquals(9, "red truck".lev(""))
    }

    @Test
    fun levDifferentCases() {
        assertEquals(0, "red truck".lev("red TRUCK"))
    }
}