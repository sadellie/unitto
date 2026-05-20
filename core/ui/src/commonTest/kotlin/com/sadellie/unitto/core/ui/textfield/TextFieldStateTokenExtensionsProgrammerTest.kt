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
import com.sadellie.unitto.core.ui.textfield.TextFieldStateTokenExtensionsProgrammer.addTokens
import kotlin.test.Test

class TextFieldStateTokenExtensionsProgrammerTest {

  @Test
  fun addTokens_addPlus() {
    // EMPTY
    compareTextStates("[]123+456", "[]123+456") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("[]+456", "[123]+456") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("[]", "[123+456]") { it.addTokens(Token.Plus.symbol) }

    compareTextStates("123+[]", "123+[]") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]", "123-[]") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]", "123/[]") { it.addTokens(Token.Plus.symbol) }
    compareTextStates("123+[]", "123*[]") { it.addTokens(Token.Plus.symbol) }
  }

  @Test
  fun addTokens_addMinus() {
    compareTextStates("123-[]", "123-[]") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123-[]", "123+[]") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123/-[]", "123/[]") { it.addTokens(Token.Minus.symbol) }
    compareTextStates("123*-[]", "123*[]") { it.addTokens(Token.Minus.symbol) }
  }

  @Test
  fun addTokens_addMultiply() {
    compareTextStates("123*[]", "123-[]") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]", "123+[]") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]", "123/[]") { it.addTokens(Token.Multiply.symbol) }
    compareTextStates("123*[]", "123*[]") { it.addTokens(Token.Multiply.symbol) }
  }

  @Test
  fun addTokens_addDivide() {
    compareTextStates("123/[]", "123-[]") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]", "123+[]") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]", "123/[]") { it.addTokens(Token.Divide.symbol) }
    compareTextStates("123/[]", "123*[]") { it.addTokens(Token.Divide.symbol) }
  }
}
