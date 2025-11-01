/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2025 Elshan Agaev
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

package com.sadellie.unitto.core.ui.textfield

import com.sadellie.unitto.core.common.Token
import kotlin.test.Test

class TextFieldStateExtensionsTest {

  @Test
  fun addTokens_addPlus() {
    // EMPTY
    compareTextStates("[]123+456", "[]123+456") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("[]+456", "[123]+456") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("[]", "[123+456]") { it.addTokens(Token.Operator.PLUS) }

    // PLUS
    compareTextStates("123+[]", "123+[]") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]", "123[+]") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]56", "123+[4]56") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]", "123+[456]") { it.addTokens(Token.Operator.PLUS) }

    // MINUS
    compareTextStates("123+[]", "123-[]") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]", "123[-]") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]56", "123-[4]56") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]", "123-[456]") { it.addTokens(Token.Operator.PLUS) }

    // MULTIPLY
    compareTextStates("123+[]", "123*[]") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]", "123[*]") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]56", "123*[4]56") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]", "123*[456]") { it.addTokens(Token.Operator.PLUS) }

    // DIVIDE
    compareTextStates("123+[]", "123/[]") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]", "123[/]") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]56", "123/[4]56") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]", "123/[456]") { it.addTokens(Token.Operator.PLUS) }

    // SQRT
    compareTextStates("123+[]", "123√[]") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]", "123[√]") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]56", "123√[4]56") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]", "123√[456]") { it.addTokens(Token.Operator.PLUS) }

    // POWER
    compareTextStates("123+[]", "123^[]") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]", "123[^]") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]56", "123^[4]56") { it.addTokens(Token.Operator.PLUS) }
    compareTextStates("123+[]", "123^[456]") { it.addTokens(Token.Operator.PLUS) }
  }

  @Test
  fun addTokens_addMinus() {
    // EMPTY
    compareTextStates("-[]123+456", "[]123+456") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("-[]+456", "[123]+456") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("-[]", "[123+456]") { it.addTokens(Token.Operator.MINUS) }

    // PLUS
    compareTextStates("123-[]", "123+[]") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123-[]", "123[+]") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123-[]56", "123+[4]56") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123-[]", "123+[456]") { it.addTokens(Token.Operator.MINUS) }

    // MINUS
    compareTextStates("123-[]", "123-[]") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123-[]", "123[-]") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123-[]56", "123-[4]56") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123-[]", "123-[456]") { it.addTokens(Token.Operator.MINUS) }

    // MULTIPLY
    compareTextStates("123*-[]", "123*[]") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123-[]", "123[*]") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123*-[]56", "123*[4]56") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123*-[]", "123*[456]") { it.addTokens(Token.Operator.MINUS) }

    // DIVIDE
    compareTextStates("123/-[]", "123/[]") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123-[]", "123[/]") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123/-[]56", "123/[4]56") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123/-[]", "123/[456]") { it.addTokens(Token.Operator.MINUS) }

    // SQRT
    compareTextStates("123√-[]", "123√[]") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123-[]", "123[√]") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123√-[]56", "123√[4]56") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123√-[]", "123√[456]") { it.addTokens(Token.Operator.MINUS) }

    // POWER
    compareTextStates("123^-[]", "123^[]") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123-[]", "123[^]") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123^-[]56", "123^[4]56") { it.addTokens(Token.Operator.MINUS) }
    compareTextStates("123^-[]", "123^[456]") { it.addTokens(Token.Operator.MINUS) }
  }

  @Test
  fun addTokens_addMultiply() {
    // EMPTY
    compareTextStates("[]123+456", "[]123+456") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("[]+456", "[123]+456") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("[]", "[123+456]") { it.addTokens(Token.Operator.MULTIPLY) }

    // PLUS
    compareTextStates("123*[]", "123+[]") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]", "123[+]") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]56", "123+[4]56") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]", "123+[456]") { it.addTokens(Token.Operator.MULTIPLY) }

    // MINUS
    compareTextStates("123*[]", "123-[]") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]", "123[-]") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]56", "123-[4]56") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]", "123-[456]") { it.addTokens(Token.Operator.MULTIPLY) }

    // MULTIPLY
    compareTextStates("123*[]", "123*[]") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]", "123[*]") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]56", "123*[4]56") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]", "123*[456]") { it.addTokens(Token.Operator.MULTIPLY) }

    // DIVIDE
    compareTextStates("123*[]", "123/[]") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]", "123[/]") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]56", "123/[4]56") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]", "123/[456]") { it.addTokens(Token.Operator.MULTIPLY) }

    // SQRT
    compareTextStates("123*[]", "123√[]") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]", "123[√]") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]56", "123√[4]56") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]", "123√[456]") { it.addTokens(Token.Operator.MULTIPLY) }

    // POWER
    compareTextStates("123*[]", "123^[]") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]", "123[^]") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]56", "123^[4]56") { it.addTokens(Token.Operator.MULTIPLY) }
    compareTextStates("123*[]", "123^[456]") { it.addTokens(Token.Operator.MULTIPLY) }
  }

  @Test
  fun addTokens_addDivide() {
    // EMPTY
    compareTextStates("[]123+456", "[]123+456") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("[]+456", "[123]+456") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("[]", "[123+456]") { it.addTokens(Token.Operator.DIVIDE) }

    // PLUS
    compareTextStates("123/[]", "123+[]") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]", "123[+]") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]56", "123+[4]56") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]", "123+[456]") { it.addTokens(Token.Operator.DIVIDE) }

    // MINUS
    compareTextStates("123/[]", "123-[]") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]", "123[-]") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]56", "123-[4]56") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]", "123-[456]") { it.addTokens(Token.Operator.DIVIDE) }

    // MULTIPLY
    compareTextStates("123/[]", "123*[]") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]", "123[*]") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]56", "123*[4]56") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]", "123*[456]") { it.addTokens(Token.Operator.DIVIDE) }

    // DIVIDE
    compareTextStates("123/[]", "123/[]") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]", "123[/]") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]56", "123/[4]56") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]", "123/[456]") { it.addTokens(Token.Operator.DIVIDE) }

    // SQRT
    compareTextStates("123/[]", "123√[]") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]", "123[√]") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]56", "123√[4]56") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]", "123√[456]") { it.addTokens(Token.Operator.DIVIDE) }

    // POWER
    compareTextStates("123/[]", "123^[]") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]", "123[^]") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]56", "123^[4]56") { it.addTokens(Token.Operator.DIVIDE) }
    compareTextStates("123/[]", "123^[456]") { it.addTokens(Token.Operator.DIVIDE) }
  }

  @Test
  fun addTokens_addPower() {
    // EMPTY
    compareTextStates("[]123+456", "[]123+456") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("[]+456", "[123]+456") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("[]", "[123+456]") { it.addTokens(Token.Operator.POWER) }

    // PLUS
    compareTextStates("123^[]", "123+[]") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]", "123[+]") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]56", "123+[4]56") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]", "123+[456]") { it.addTokens(Token.Operator.POWER) }

    // MINUS
    compareTextStates("123^[]", "123-[]") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]", "123[-]") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]56", "123-[4]56") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]", "123-[456]") { it.addTokens(Token.Operator.POWER) }

    // MULTIPLY
    compareTextStates("123^[]", "123*[]") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]", "123[*]") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]56", "123*[4]56") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]", "123*[456]") { it.addTokens(Token.Operator.POWER) }

    // DIVIDE
    compareTextStates("123^[]", "123/[]") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]", "123[/]") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]56", "123/[4]56") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]", "123/[456]") { it.addTokens(Token.Operator.POWER) }

    // SQRT
    compareTextStates("123^[]", "123√[]") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]", "123[√]") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]56", "123√[4]56") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]", "123√[456]") { it.addTokens(Token.Operator.POWER) }

    // POWER
    compareTextStates("123^[]", "123^[]") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]", "123[^]") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]56", "123^[4]56") { it.addTokens(Token.Operator.POWER) }
    compareTextStates("123^[]", "123^[456]") { it.addTokens(Token.Operator.POWER) }
  }

  @Test
  fun addTokens_addDot() {
    // EMPTY
    compareTextStates(".[]123+456", "[]123+456") { it.addTokens(Token.Digit.DOT) }
    compareTextStates(".[]+456", "[123]+456") { it.addTokens(Token.Digit.DOT) }
    compareTextStates(".[]", "[123+456]") { it.addTokens(Token.Digit.DOT) }

    compareTextStates("123+456.[]78", "123+456.[]78") { it.addTokens(Token.Digit.DOT) }
    compareTextStates("123+456.[]78", "123+456[.]78") { it.addTokens(Token.Digit.DOT) }
    compareTextStates("123+456.[]8", "123+456[.7]8") { it.addTokens(Token.Digit.DOT) }
    compareTextStates("123+45.[]78", "123+45[6.]78") { it.addTokens(Token.Digit.DOT) }
  }

  @Test
  fun addBracket_test() {
    // Open on empty in front
    compareTextStates("([]", "[]") { it.addBracket() }
    compareTextStates("([]123(", "[]123(") { it.addBracket() }
    compareTextStates("([](", "[123](") { it.addBracket() }
    compareTextStates("([]", "[123(]") { it.addBracket() }

    // Close before multiply
    compareTextStates("123)[]*456", "123[]*456") { it.addBracket() }
    compareTextStates("(123)[]*456", "(123[]*456") { it.addBracket() }
    compareTextStates(")123)[]*456", ")123[]*456") { it.addBracket() }
    // Close before divide
    compareTextStates("123)[]/456", "123[]/456") { it.addBracket() }
    compareTextStates("(123)[]/456", "(123[]/456") { it.addBracket() }
    compareTextStates(")123)[]/456", ")123[]/456") { it.addBracket() }
    // Close before plus
    compareTextStates("123)[]+456", "123[]+456") { it.addBracket() }
    compareTextStates("(123)[]+456", "(123[]+456") { it.addBracket() }
    compareTextStates(")123)[]+456", ")123[]+456") { it.addBracket() }
    // Close before minus
    compareTextStates("123)[]-456", "123[]-456") { it.addBracket() }
    compareTextStates("(123)[]-456", "(123[]-456") { it.addBracket() }
    compareTextStates(")123)[]-456", ")123[]-456") { it.addBracket() }
    // Close before power
    compareTextStates("123)[]^456", "123[]^456") { it.addBracket() }
    compareTextStates("(123)[]^456", "(123[]^456") { it.addBracket() }
    compareTextStates(")123)[]^456", ")123[]^456") { it.addBracket() }

    // Open on balanced in front
    compareTextStates("123([]", "123[]") { it.addBracket() }
    compareTextStates("123([]((((", "123[]((((") { it.addBracket() }

    // Open after multiply
    compareTextStates("123*([]456", "123*[]456") { it.addBracket() }
    compareTextStates("123*([]", "123*[456]") { it.addBracket() }
    // Open after divide
    compareTextStates("123/([]456", "123/[]456") { it.addBracket() }
    compareTextStates("123/([]", "123/[456]") { it.addBracket() }
    // Open after plus
    compareTextStates("123+([]456", "123+[]456") { it.addBracket() }
    compareTextStates("123+([]", "123+[456]") { it.addBracket() }
    // Open after minus
    compareTextStates("123-([]456", "123-[]456") { it.addBracket() }
    compareTextStates("123-([]", "123-[456]") { it.addBracket() }
    // Open after power
    compareTextStates("123^([]456", "123^[]456") { it.addBracket() }
    compareTextStates("123^([]", "123^[456]") { it.addBracket() }

    // Default
    compareTextStates("123([]", "123[]") { it.addBracket() }
    compareTextStates("123(456+789)[]", "123(456+789[]") { it.addBracket() }
  }
}
