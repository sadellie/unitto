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

package io.github.sadellie.evaluatto

import org.junit.jupiter.api.Test

class TokenizerTest {

    @Test
    fun tokens1() = assertLex(listOf("789"), "789")

    @Test
    fun tokens2() = assertLex(listOf("789", "+", "200"), "789+200")

    @Test
    fun tokens3() = assertLex(listOf("0.1", "+", "0.2"), "0.1+0.2")

    @Test
    fun tokens4() = assertLex(listOf(".1", "+", ".2"), ".1+.2")

    @Test
    fun tokens5() = assertLex(listOf(".1", "+", ".2"), ".1+.2")

    @Test
    fun tokens6() = assertLex(listOf("789", "+", "200", "+", "cos", "(", "456", ")"), "789+200+cos(456)")

    @Test
    fun tokens8() = assertLex(emptyList(), "")

    @Test
    fun tokens9() = assertLex(listOf("e"), "something") // Tokenizer knows "e"

    @Test
    fun tokens10() = assertLex(emptyList(), "funnyword")
}
