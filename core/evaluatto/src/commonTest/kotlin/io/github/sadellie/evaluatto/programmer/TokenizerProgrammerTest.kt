/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
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

package io.github.sadellie.evaluatto.programmer

import com.sadellie.unitto.core.common.Token
import kotlin.test.Test
import kotlin.test.assertEquals

class TokenizerProgrammerTest {
  @Test fun empty() = assertEquals(emptyList(), "".tokenizeProgrammer())

  @Test fun `base2 number`() = assertEquals(listOf(Token.Number("101")), "101".tokenizeProgrammer())

  @Test fun `base8 number`() = assertEquals(listOf(Token.Number("70")), "70".tokenizeProgrammer())

  @Test fun `base10 number`() = assertEquals(listOf(Token.Number("99")), "99".tokenizeProgrammer())

  @Test
  fun `base16 number`() =
    assertEquals(listOf(Token.Number("123ABC")), "123ABC".tokenizeProgrammer())

  @Test
  fun `base16 number starts with letters`() =
    assertEquals(listOf(Token.Number("ABC123")), "ABC123".tokenizeProgrammer())

  @Test
  fun `unary minus fixup`() =
    assertEquals(listOf(Token.UnaryMinus, Token.Number("ABC123")), "−ABC123".tokenizeProgrammer())

  @Test
  fun `complex expression`() =
    assertEquals(
      listOf(
        Token.Number("123"),
        Token.Rsh,
        Token.LeftBracket,
        Token.Number("ABC123"),
        Token.Plus,
        Token.Number("456"),
        Token.RightBracket,
        Token.Or,
        Token.Number("6"),
      ),
      "123rsh(ABC123+456)or6".tokenizeProgrammer(),
    )

  @Test
  fun `add missing right brackets`() =
    assertEquals(
      listOf(
        Token.LeftBracket,
        Token.LeftBracket,
        Token.LeftBracket,
        Token.Number("ABC123"),
        Token.Plus,
        Token.Number("456"),
        Token.RightBracket,
        Token.RightBracket,
        Token.RightBracket,
      ),
      "(((ABC123+456)".tokenizeProgrammer(),
    )
}
