/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2026 Elshan Agaev
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
import com.sadellie.unitto.core.ui.textfield.TextFieldStateTokenExtensionsMath.addBracket
import com.sadellie.unitto.core.ui.textfield.TextFieldStateTokenExtensionsMath.addTokens
import kotlin.test.Test

class TextFieldStateTokenExtensionsMathTest {

  @Test
  fun addTokens_addPlus() {
    // EMPTY
    compareTextStates("[]123+456", "[]123+456") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("[]+456", "[123]+456") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("[]", "[123+456]") { it.addTokens(Token.Plus.symbol) }

    // PLUS
    compareTextStates("123+[]", "123+[]") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]", "123[+]") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]56", "123+[4]56") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]", "123+[456]") { it.addTokens(Token.Plus.symbol) }

    // MINUS
    compareTextStates("123+[]", "123-[]") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]", "123[-]") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]56", "123-[4]56") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]", "123-[456]") { it.addTokens(Token.Plus.symbol) }

    // MULTIPLY
    compareTextStates("123+[]", "123*[]") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]", "123[*]") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]56", "123*[4]56") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]", "123*[456]") { it.addTokens(Token.Plus.symbol) }

    // DIVIDE
    compareTextStates("123+[]", "123/[]") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]", "123[/]") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]56", "123/[4]56") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]", "123/[456]") { it.addTokens(Token.Plus.symbol) }

    // SQRT
    compareTextStates("123+[]", "123√[]") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]", "123[√]") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]56", "123√[4]56") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]", "123√[456]") { it.addTokens(Token.Plus.symbol) }

    // POWER
    compareTextStates("123+[]", "123^[]") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]", "123[^]") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]56", "123^[4]56") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]", "123^[456]") { it.addTokens(Token.Plus.symbol) }
  }

  @Test
  fun addTokens_addMinus() {
    // EMPTY
    compareTextStates("-[]123+456", "[]123+456") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("-[]+456", "[123]+456") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("-[]", "[123+456]") { it.addTokens(Token.Minus.symbol) }

    // PLUS
    compareTextStates("123-[]", "123+[]") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123-[]", "123[+]") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123-[]56", "123+[4]56") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123-[]", "123+[456]") { it.addTokens(Token.Minus.symbol) }

    // MINUS
    compareTextStates("123-[]", "123-[]") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123-[]", "123[-]") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123-[]56", "123-[4]56") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123-[]", "123-[456]") { it.addTokens(Token.Minus.symbol) }

    // MULTIPLY
    compareTextStates("123*-[]", "123*[]") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123-[]", "123[*]") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123*-[]56", "123*[4]56") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123*-[]", "123*[456]") { it.addTokens(Token.Minus.symbol) }

    // DIVIDE
    compareTextStates("123/-[]", "123/[]") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123-[]", "123[/]") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123/-[]56", "123/[4]56") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123/-[]", "123/[456]") { it.addTokens(Token.Minus.symbol) }

    // SQRT
    compareTextStates("123√-[]", "123√[]") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123-[]", "123[√]") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123√-[]56", "123√[4]56") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123√-[]", "123√[456]") { it.addTokens(Token.Minus.symbol) }

    // POWER
    compareTextStates("123^-[]", "123^[]") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123-[]", "123[^]") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123^-[]56", "123^[4]56") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123^-[]", "123^[456]") { it.addTokens(Token.Minus.symbol) }
  }

  @Test
  fun addTokens_addMultiply() {
    // EMPTY
    compareTextStates("[]123+456", "[]123+456") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("[]+456", "[123]+456") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("[]", "[123+456]") { it.addTokens(Token.Multiply.symbol) }

    // PLUS
    compareTextStates("123*[]", "123+[]") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]", "123[+]") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]56", "123+[4]56") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]", "123+[456]") { it.addTokens(Token.Multiply.symbol) }

    // MINUS
    compareTextStates("123*[]", "123-[]") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]", "123[-]") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]56", "123-[4]56") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]", "123-[456]") { it.addTokens(Token.Multiply.symbol) }

    // MULTIPLY
    compareTextStates("123*[]", "123*[]") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]", "123[*]") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]56", "123*[4]56") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]", "123*[456]") { it.addTokens(Token.Multiply.symbol) }

    // DIVIDE
    compareTextStates("123*[]", "123/[]") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]", "123[/]") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]56", "123/[4]56") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]", "123/[456]") { it.addTokens(Token.Multiply.symbol) }

    // SQRT
    compareTextStates("123*[]", "123√[]") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]", "123[√]") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]56", "123√[4]56") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]", "123√[456]") { it.addTokens(Token.Multiply.symbol) }

    // POWER
    compareTextStates("123*[]", "123^[]") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]", "123[^]") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]56", "123^[4]56") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]", "123^[456]") { it.addTokens(Token.Multiply.symbol) }
  }

  @Test
  fun addTokens_addDivide() {
    // EMPTY
    compareTextStates("[]123+456", "[]123+456") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("[]+456", "[123]+456") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("[]", "[123+456]") { it.addTokens(Token.Divide.symbol) }

    // PLUS
    compareTextStates("123/[]", "123+[]") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]", "123[+]") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]56", "123+[4]56") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]", "123+[456]") { it.addTokens(Token.Divide.symbol) }

    // MINUS
    compareTextStates("123/[]", "123-[]") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]", "123[-]") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]56", "123-[4]56") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]", "123-[456]") { it.addTokens(Token.Divide.symbol) }

    // MULTIPLY
    compareTextStates("123/[]", "123*[]") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]", "123[*]") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]56", "123*[4]56") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]", "123*[456]") { it.addTokens(Token.Divide.symbol) }

    // DIVIDE
    compareTextStates("123/[]", "123/[]") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]", "123[/]") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]56", "123/[4]56") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]", "123/[456]") { it.addTokens(Token.Divide.symbol) }

    // SQRT
    compareTextStates("123/[]", "123√[]") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]", "123[√]") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]56", "123√[4]56") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]", "123√[456]") { it.addTokens(Token.Divide.symbol) }

    // POWER
    compareTextStates("123/[]", "123^[]") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]", "123[^]") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]56", "123^[4]56") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]", "123^[456]") { it.addTokens(Token.Divide.symbol) }
  }

  @Test
  fun addTokens_addPower() {
    // EMPTY
    compareTextStates("[]123+456", "[]123+456") { it.addTokens(Token.Power.symbol) }
    compareTextStates("[]+456", "[123]+456") { it.addTokens(Token.Power.symbol) }
    compareTextStates("[]", "[123+456]") { it.addTokens(Token.Power.symbol) }

    // PLUS
    compareTextStates("123^[]", "123+[]") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]", "123[+]") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]56", "123+[4]56") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]", "123+[456]") { it.addTokens(Token.Power.symbol) }

    // MINUS
    compareTextStates("123^[]", "123-[]") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]", "123[-]") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]56", "123-[4]56") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]", "123-[456]") { it.addTokens(Token.Power.symbol) }

    // MULTIPLY
    compareTextStates("123^[]", "123*[]") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]", "123[*]") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]56", "123*[4]56") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]", "123*[456]") { it.addTokens(Token.Power.symbol) }

    // DIVIDE
    compareTextStates("123^[]", "123/[]") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]", "123[/]") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]56", "123/[4]56") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]", "123/[456]") { it.addTokens(Token.Power.symbol) }

    // SQRT
    compareTextStates("123^[]", "123√[]") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]", "123[√]") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]56", "123√[4]56") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]", "123√[456]") { it.addTokens(Token.Power.symbol) }

    // POWER
    compareTextStates("123^[]", "123^[]") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]", "123[^]") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]56", "123^[4]56") { it.addTokens(Token.Power.symbol) }
    compareTextStates("123^[]", "123^[456]") { it.addTokens(Token.Power.symbol) }
  }

  @Test
  fun addTokens_addDot() {
    // EMPTY
    compareTextStates(".[]123+456", "[]123+456") { it.addTokens(Token.Dot.symbol) }
    compareTextStates(".[]+456", "[123]+456") { it.addTokens(Token.Dot.symbol) }
    compareTextStates(".[]", "[123+456]") { it.addTokens(Token.Dot.symbol) }

    compareTextStates("123+456.[]78", "123+456.[]78") { it.addTokens(Token.Dot.symbol) }
    compareTextStates("123+456.[]78", "123+456[.]78") { it.addTokens(Token.Dot.symbol) }
    compareTextStates("123+456.[]8", "123+456[.7]8") { it.addTokens(Token.Dot.symbol) }
    compareTextStates("123+45.[]78", "123+45[6.]78") { it.addTokens(Token.Dot.symbol) }
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
