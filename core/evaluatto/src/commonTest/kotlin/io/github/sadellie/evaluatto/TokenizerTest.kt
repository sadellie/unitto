/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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

import com.sadellie.unitto.core.common.Token
import kotlin.test.Test
import kotlin.test.assertEquals

class TokenizerTest {

  @Test fun tokens1() = assertLex(listOf(Token.Number("789")), "789")

  @Test
  fun tokens2() = assertLex(listOf(Token.Number("789"), Token.Plus, Token.Number("200")), "789+200")

  @Test
  fun tokens3() = assertLex(listOf(Token.Number("0.1"), Token.Plus, Token.Number("0.2")), "0.1+0.2")

  @Test
  fun tokens4() = assertLex(listOf(Token.Number(".1"), Token.Plus, Token.Number(".2")), ".1+.2")

  @Test
  fun tokens5() = assertLex(listOf(Token.Number(".1"), Token.Plus, Token.Number(".2")), ".1+.2")

  @Test
  fun tokens6() =
    assertLex(
      listOf(
        Token.Number("789"),
        Token.Plus,
        Token.Number("200"),
        Token.Plus,
        Token.Cos,
        Token.LeftBracket,
        Token.Number("456"),
        Token.RightBracket,
      ),
      "789+200+cos(456)",
    )

  @Test fun tokens8() = assertLex(emptyList(), "")

  @Test fun tokens9() = assertLex(listOf(Token.E), "something") // Tokenizer knows "e"

  @Test fun tokens10() = assertLex(emptyList(), "funnyword")

  @Test
  fun tokens11() =
    assertLex(
      listOf(
        Token.Number("7"),
        Token.Factorial,
        Token.Divide,
        Token.Number("3"),
        Token.Factorial,
        Token.Minus,
        Token.Number("5"),
        Token.Factorial,
        Token.Divide,
        Token.Number("2"),
        Token.Factorial,
      ),
      "7!÷3!−5!÷2!",
    )

  @Test
  fun getBaseBefore_number() {
    // 132.5+14%
    val input = mutableListOf(Token.Number("132.5"), Token.Plus, Token.Number("14"), Token.Percent)

    val expected = mutableListOf(Token.Number("132.5"))
    val actual = input.getExpressionBefore(0)

    assertEquals(expected, actual)
  }

  @Test
  fun getBaseBefore_expression1() {
    // (132.5+12%)+(15+4)%
    val input =
      mutableListOf(
        Token.LeftBracket,
        Token.Number("132.5"),
        Token.Plus,
        Token.Number("12"),
        Token.Percent,
        Token.RightBracket,
      )

    val expected = mutableListOf(Token.Number("132.5"))
    val actual = input.getExpressionBefore(1)

    assertEquals(expected, actual)
  }

  @Test
  fun getBaseBefore_expression3() {
    // (132.5+5)+90%
    val input =
      mutableListOf(
        Token.LeftBracket,
        Token.Number("132.5"),
        Token.Plus,
        Token.Number("5"),
        Token.RightBracket,
        Token.Plus,
        Token.Number("90"),
        Token.Percent,
      )

    val expected =
      mutableListOf(
        Token.LeftBracket,
        Token.Number("132.5"),
        Token.Plus,
        Token.Number("5"),
        Token.RightBracket,
      )
    val actual = input.getExpressionBefore(4)

    assertEquals(expected, actual)
  }

  @Test
  fun getBaseBefore_factorial() {
    // 2!+5%
    val input =
      mutableListOf(
        Token.Number("2"),
        Token.Factorial,
        Token.Plus,
        Token.Number("5"),
        Token.Percent,
      )

    val expected = mutableListOf(Token.Number("2"), Token.Factorial)
    val actual = input.getExpressionBefore(1)

    assertEquals(expected, actual)
  }
}
