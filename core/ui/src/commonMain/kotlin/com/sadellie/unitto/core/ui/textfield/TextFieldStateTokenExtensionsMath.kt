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

package com.sadellie.unitto.core.ui.textfield

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.text.TextRange
import com.sadellie.unitto.core.common.Token

object TextFieldStateTokenExtensionsMath : TextFieldStateTokenExtensions {
  override val closeBeforeOperators by lazy {
    listOf(
      Token.Multiply.symbol,
      Token.Divide.symbol,
      Token.Plus.symbol,
      Token.Minus.symbol,
      Token.Power.symbol,
    )
  }
  override val openAfterOperators by lazy { closeBeforeOperators + Token.LeftBracket.symbol }

  override val longTokens = Token.Func.allMathSymbolsWithBracket

  override fun TextFieldState.addTokens(tokens: String) {
    when (tokens) {
      Token.Plus.symbol,
      Token.Multiply.symbol,
      Token.Divide.symbol,
      Token.Power.symbol -> {
        val tokenAhead = text.toString().tokenAhead(selection.min, longTokens)
        when (tokenAhead) {
          Token.Plus.symbol,
          Token.Minus.symbol,
          Token.Multiply.symbol,
          Token.Divide.symbol,
          Token.Sqrt.symbol,
          Token.Power.symbol -> return deleteAheadAndAdd(tokens)
          "" -> return deleteTokens()
        }
      }

      Token.Minus.symbol -> {
        val tokenAhead = text.toString().tokenAhead(selection.min, longTokens)
        when (tokenAhead) {
          Token.Plus.symbol,
          Token.Minus.symbol -> return deleteAheadAndAdd(tokens)
        }
      }

      Token.Dot.symbol -> {
        val tokenAhead = text.toString().tokenAhead(selection.min, longTokens)
        if (tokenAhead == Token.Dot.symbol) return deleteAheadAndAdd(tokens)
      }
    }
    this.edit {
      replace(selection.min, selection.max, tokens)
      selection = TextRange(selection.max)
    }
  }
}
