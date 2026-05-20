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

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.delete
import androidx.compose.runtime.Stable
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.trimZeros

/**
 * - Removes formatter symbol (for evaluatto)
 * - Replaces ugly tokens with appropriate versions (for evaluatto)
 * - Allows only legal tokens
 * - Fixes cursor position
 */
@Stable
data class ExpressionInputTransformation(private val formatterSymbols: FormatterSymbols) :
  InputTransformationWithReplacement {
  override fun TextFieldBuffer.transformInput() =
    transformInputWithReplacements(longTokens = Token.Func.allMathSymbolsWithBracket)

  override val legalTokens =
    listOf(
      Token.ArCos.WithBracket.symbol,
      Token.ArSin.WithBracket.symbol,
      Token.ArTan.WithBracket.symbol,
      Token.Sin.WithBracket.symbol,
      Token.Cos.WithBracket.symbol,
      Token.Tan.WithBracket.symbol,
      Token.Log.WithBracket.symbol,
      Token.Exp.WithBracket.symbol,
      Token.Ln.WithBracket.symbol,
      Token.Dot.symbol,
      Token.Digit0.symbol,
      Token.Digit1.symbol,
      Token.Digit2.symbol,
      Token.Digit3.symbol,
      Token.Digit4.symbol,
      Token.Digit5.symbol,
      Token.Digit6.symbol,
      Token.Digit7.symbol,
      Token.Digit8.symbol,
      Token.Digit9.symbol,
      Token.Minus.symbol,
      Token.Divide.symbol,
      Token.Multiply.symbol,
      Token.Plus.symbol,
      Token.LeftBracket.symbol,
      Token.RightBracket.symbol,
      Token.Power.symbol,
      Token.Factorial.symbol,
      Token.Modulo.symbol,
      Token.Percent.symbol,
      Token.Sqrt.symbol,
      Token.Pi.symbol,
      Token.E.symbol,
    )

  override val replacementMap =
    mapOf(
      // formatterSymbols.grouping doesn't break replacement for other ugly symbols
      formatterSymbols.grouping.symbol to "",
      "arcos(" to Token.ArCos.WithBracket.symbol,
      "arsin(" to Token.ArSin.WithBracket.symbol,
      "actan(" to Token.ArTan.WithBracket.symbol,
      formatterSymbols.fractional.symbol to Token.Dot.symbol,
      "-" to Token.Minus.symbol,
      "–" to Token.Minus.symbol,
      "—" to Token.Minus.symbol,
      "/" to Token.Divide.symbol,
      "*" to Token.Multiply.symbol,
      "•" to Token.Multiply.symbol,
    )
}

/**
 * - Allow only [Token.Digit] and [Token.Letter]. No fractional symbols
 * - Replaces lowercase letters with uppercase
 *
 * @see TextFieldBuffer.transformInputWithReplacements
 */
@Stable
data object NumberBaseInputTransformation : InputTransformationWithReplacement {
  override fun TextFieldBuffer.transformInput() =
    transformInputWithReplacements(longTokens = emptyList())

  override val legalTokens =
    listOf(
      Token.Digit0.symbol,
      Token.Digit1.symbol,
      Token.Digit2.symbol,
      Token.Digit3.symbol,
      Token.Digit4.symbol,
      Token.Digit5.symbol,
      Token.Digit6.symbol,
      Token.Digit7.symbol,
      Token.Digit8.symbol,
      Token.Digit9.symbol,
      Token.LetterA.symbol,
      Token.LetterB.symbol,
      Token.LetterC.symbol,
      Token.LetterD.symbol,
      Token.LetterE.symbol,
      Token.LetterF.symbol,
    )

  override val replacementMap =
    mapOf(
      "a" to Token.LetterA.symbol,
      "b" to Token.LetterB.symbol,
      "c" to Token.LetterC.symbol,
      "d" to Token.LetterD.symbol,
      "e" to Token.LetterE.symbol,
      "f" to Token.LetterF.symbol,
    )
}

/**
 * - Allow any digit
 * - Allow using any fractional symbol as input ([Token.Comma.symbol] and [Token.Period.symbol]),
 *   but only if [allowFraction] is True
 * - Limit using [maxValue]
 */
@Stable
data class UnexpectedDigitsInputTransformation(
  private val maxValue: Double,
  private val allowFraction: Boolean,
) : InputTransformation {
  override fun TextFieldBuffer.transformInput() {
    if (length == 0) return
    var cursor = 0
    while (cursor < length) {
      val char = charAt(cursor).toString()

      val legalToken =
        when (char) {
          in Token.Digit.allSymbols -> true
          Token.Period.symbol -> allowFraction
          Token.Comma.symbol -> {
            if (allowFraction) {
              replace(cursor, cursor + Token.Period.symbol.length, Token.Period.symbol)
            }
            allowFraction
          }
          else -> false
        }

      if (legalToken) {
        cursor++
      } else {
        delete(cursor, cursor + 1)
      }
    }

    val value = this.toString().toDoubleOrNull() ?: return
    if (value > maxValue) {
      val maxValueBD = KBigDecimal(maxValue)
      replace(0, length, maxValueBD.trimZeros().toPlainString())
    }
  }
}

private fun TextFieldBuffer.matchLegalToken(
  cursor: Int,
  charsLeft: Int,
  legalTokens: List<String>,
): String? {
  for (legalToken in legalTokens) {
    if (legalToken.length > charsLeft) continue
    val charsInFront = getCharsInFront(cursor, legalToken.length)
    if (charsInFront == legalToken) {
      // matched, move on
      return legalToken
    }
  }
  return null
}

private fun TextFieldBuffer.matchAndReplaceToken(
  cursor: Int,
  charsLeft: Int,
  replacementMap: Map<String, String>,
): String? {
  for ((ugly, replacement) in replacementMap) {
    if (ugly.length > charsLeft) continue
    val charsInFront = getCharsInFront(cursor, ugly.length)
    if (charsInFront == ugly) {
      // this works even if ugly and replacement have different length
      replace(cursor, cursor + ugly.length, replacement)
      return replacement
    }
  }
  return null
}

private fun TextFieldBuffer.getCharsInFront(cursor: Int, count: Int): String {
  var charsInFront = ""
  repeat(count) { charsInFront += charAt(cursor + it) }
  return charsInFront
}

interface InputTransformationWithReplacement : InputTransformation {
  /** Allowed tokens. Order matters, longest first. */
  val legalTokens: List<String>
  /** Ugly tokens and their replacements. Order matters, prefer longest first. */
  val replacementMap: Map<String, String>

  fun TextFieldBuffer.transformInputWithReplacements(longTokens: List<String>) {
    if (length == 0) return

    val isTextChanged = this.toString() != originalText.toString()
    if (isTextChanged) {
      // process tokens
      var cursor = 0

      while (cursor < length) {
        val charsLeft = length - cursor

        // try to match with replacement map
        var matched = matchAndReplaceToken(cursor, charsLeft, replacementMap)
        if (matched == null) {
          // try to find legal token ahead
          matched = matchLegalToken(cursor, charsLeft, legalTokens)
        }

        if (matched == null) {
          // illegal token
          delete(cursor, cursor + 1)
        } else {
          cursor += matched.length
        }
      }
    }

    val fixedSelection = this.fixTextRange(longTokens)
    selection = fixedSelection
  }
}
