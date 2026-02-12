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
import com.sadellie.unitto.core.common.Token2
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
    transformInputWithReplacements(legalTokens, replacementMap)

  override val legalTokens =
    listOf(
      Token2.ArCos.WithBracket.symbol,
      Token2.ArSin.WithBracket.symbol,
      Token2.ArTan.WithBracket.symbol,
      Token2.Sin.WithBracket.symbol,
      Token2.Cos.WithBracket.symbol,
      Token2.Tan.WithBracket.symbol,
      Token2.Log.WithBracket.symbol,
      Token2.Exp.WithBracket.symbol,
      Token2.Ln.WithBracket.symbol,
      Token2.Dot.symbol,
      Token2.Digit0.symbol,
      Token2.Digit1.symbol,
      Token2.Digit2.symbol,
      Token2.Digit3.symbol,
      Token2.Digit4.symbol,
      Token2.Digit5.symbol,
      Token2.Digit6.symbol,
      Token2.Digit7.symbol,
      Token2.Digit8.symbol,
      Token2.Digit9.symbol,
      Token2.Minus.symbol,
      Token2.Divide.symbol,
      Token2.Multiply.symbol,
      Token2.Plus.symbol,
      Token2.LeftBracket.symbol,
      Token2.RightBracket.symbol,
      Token2.Power.symbol,
      Token2.Factorial.symbol,
      Token2.Modulo.symbol,
      Token2.Percent.symbol,
      Token2.Sqrt.symbol,
      Token2.Pi.symbol,
      Token2.E.symbol,
    )

  override val replacementMap =
    mapOf(
      // formatterSymbols.grouping doesn't break replacement for other ugly symbols
      formatterSymbols.grouping.symbol to "",
      "arcos(" to Token2.ArCos.WithBracket.symbol,
      "arsin(" to Token2.ArSin.WithBracket.symbol,
      "actan(" to Token2.ArTan.WithBracket.symbol,
      formatterSymbols.fractional.symbol to Token2.Dot.symbol,
      "-" to Token2.Minus.symbol,
      "–" to Token2.Minus.symbol,
      "—" to Token2.Minus.symbol,
      "/" to Token2.Divide.symbol,
      "*" to Token2.Multiply.symbol,
      "•" to Token2.Multiply.symbol,
    )
}

/**
 * - Allow only [Token2.Digit.all] and [Token2.Letter.all]. No fractional symbols
 * - Replaces lowercase letters with uppercase
 *
 * @see TextFieldBuffer.transformInputWithReplacements
 */
@Stable
data object NumberBaseInputTransformation : InputTransformationWithReplacement {
  override fun TextFieldBuffer.transformInput() =
    transformInputWithReplacements(legalTokens, replacementMap)

  override val legalTokens =
    listOf(
      Token2.Digit0.symbol,
      Token2.Digit1.symbol,
      Token2.Digit2.symbol,
      Token2.Digit3.symbol,
      Token2.Digit4.symbol,
      Token2.Digit5.symbol,
      Token2.Digit6.symbol,
      Token2.Digit7.symbol,
      Token2.Digit8.symbol,
      Token2.Digit9.symbol,
      Token2.LetterA.symbol,
      Token2.LetterB.symbol,
      Token2.LetterC.symbol,
      Token2.LetterD.symbol,
      Token2.LetterE.symbol,
      Token2.LetterF.symbol,
    )

  override val replacementMap =
    mapOf(
      "a" to Token2.LetterA.symbol,
      "b" to Token2.LetterB.symbol,
      "c" to Token2.LetterC.symbol,
      "d" to Token2.LetterD.symbol,
      "e" to Token2.LetterE.symbol,
      "f" to Token2.LetterF.symbol,
    )
}

/**
 * - Allow any digit
 * - Allow using any fractional symbol as input ([Token2.Comma.symbol] and [Token2.Period.symbol]),
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
          in Token2.Digit.allSymbols -> true
          Token2.Period.symbol -> allowFraction
          Token2.Comma.symbol -> {
            if (allowFraction) {
              replace(cursor, cursor + Token2.Period.symbol.length, Token2.Period.symbol)
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

private fun TextFieldBuffer.transformInputWithReplacements(
  legalTokens: List<String>,
  replacementMap: Map<String, String>,
) {
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

  val fixedSelection = this.fixTextRange()
  selection = fixedSelection
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

private interface InputTransformationWithReplacement : InputTransformation {
  /** Allowed tokens. Order matters, longest first. */
  val legalTokens: List<String>
  /** Ugly tokens and their replacements. Order matters, prefer longest first. */
  val replacementMap: Map<String, String>
}
