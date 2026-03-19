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

package com.sadellie.unitto.feature.programmer

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.text.TextRange
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.ui.textfield.TextFieldStateTokenExtensions
import com.sadellie.unitto.core.ui.textfield.TextFieldStateTokenExtensionsMath

object TextFieldStateTokenExtensionsProgrammer : TextFieldStateTokenExtensions {
  override val closeBeforeOperators: List<String> by lazy {
    listOf(
      Token.Plus.symbol,
      Token.Minus.symbol,
      Token.Multiply.symbol,
      Token.Divide.symbol,
      Token.Or.symbol,
      Token.And.symbol,
      Token.Not.symbol,
      Token.Nand.symbol,
      Token.Nor.symbol,
      Token.Xor.symbol,
      Token.Lsh.symbol,
      Token.Rsh.symbol,
      Token.Mod.symbol,
    )
  }

  override val openAfterOperators by lazy {
    TextFieldStateTokenExtensionsMath.closeBeforeOperators + Token.LeftBracket.symbol
  }

  override val longTokens: List<String> by lazy {
    listOf(
      Token.Nand.symbol,
      Token.Xor.symbol,
      Token.And.symbol,
      Token.Not.symbol,
      Token.Nor.symbol,
      Token.Lsh.symbol,
      Token.Rsh.symbol,
      Token.Mod.symbol,
      Token.Or.symbol,
    )
  }

  override fun TextFieldState.addTokens(tokens: String) {
    // TODO Replace tokens like in math
    this.edit {
      replace(selection.min, selection.max, tokens)
      selection = TextRange(selection.max)
    }
  }
}
