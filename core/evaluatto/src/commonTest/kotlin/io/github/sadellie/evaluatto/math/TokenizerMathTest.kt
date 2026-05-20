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

package io.github.sadellie.evaluatto.math

import com.sadellie.unitto.core.common.Token
import kotlin.test.Test
import kotlin.test.assertEquals

class TokenizerMathTest {

  @Test fun `simple number`() = assertEquals(listOf(Token.Number("789")), "789".tokenizeMath())

  @Test
  fun `simple expression`() =
    assertEquals(
      listOf(Token.Number("789"), Token.Plus, Token.Number("200")),
      "789+200".tokenizeMath(),
    )

  @Test
  fun `simple expression with decimals`() =
    assertEquals(
      listOf(Token.Number("0.1"), Token.Plus, Token.Number("0.2")),
      "0.1+0.2".tokenizeMath(),
    )

  @Test
  fun `incomplete decimals`() =
    assertEquals(listOf(Token.Number(".1"), Token.Plus, Token.Number(".2")), ".1+.2".tokenizeMath())

  @Test
  fun `expression with func`() =
    assertEquals(
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
      "789+200+cos(456)".tokenizeMath(),
    )

  @Test fun `empty input`() = assertEquals(emptyList(), "".tokenizeMath())

  @Test
  fun `allow only known tokens`() =
    assertEquals(listOf(Token.E), "something".tokenizeMath()) // Tokenizer knows "e"

  @Test fun `ignore all unknown tokens`() = assertEquals(emptyList(), "funnyword".tokenizeMath())

  @Test
  fun `complex expression`() =
    assertEquals(
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
      "7!÷3!−5!÷2!".tokenizeMath(),
    )

  @Test
  fun `unary minus fixup`() =
    assertEquals(listOf(Token.UnaryMinus, Token.Number(".1")), "−.1".tokenizeMath())

  @Test
  fun `unary minus fixup after operator`() =
    assertEquals(
      listOf(Token.Number("1"), Token.Plus, Token.UnaryMinus, Token.Number("2")),
      "1+−2".tokenizeMath(),
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
