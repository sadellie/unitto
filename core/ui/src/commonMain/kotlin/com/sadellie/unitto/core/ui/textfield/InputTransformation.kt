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

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.delete
import androidx.compose.runtime.Stable
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.trimZeros

/**
 * - Remove formatter symbol (for evaluatto)
 * - Replaces ugly tokens with appropriate versions (for evaluatto)
 * - Allow only legal tokens
 * - Fixes cursor position
 * - TODO Fix cursor when near grouping symbol
 * - TODO Keyboard support: Allow jumping over entire token using arrows
 */
@Stable
data class ExpressionInputTransformation(private val formatterSymbols: FormatterSymbols) :
  InputTransformationWithReplacement {
  override fun TextFieldBuffer.transformInput() =
    transformInputWithReplacements(legalTokens, replacementMap)

  override val legalTokens =
    listOf(
      Token.Func.ARCOS_BRACKET,
      Token.Func.ARSIN_BRACKET,
      Token.Func.ACTAN_BRACKET,
      Token.Func.SIN_BRACKET,
      Token.Func.COS_BRACKET,
      Token.Func.TAN_BRACKET,
      Token.Func.LOG_BRACKET,
      Token.Func.EXP_BRACKET,
      Token.Func.LN_BRACKET,
      Token.Digit.DOT,
      Token.Digit.DIGIT_0,
      Token.Digit.DIGIT_1,
      Token.Digit.DIGIT_2,
      Token.Digit.DIGIT_3,
      Token.Digit.DIGIT_4,
      Token.Digit.DIGIT_5,
      Token.Digit.DIGIT_6,
      Token.Digit.DIGIT_7,
      Token.Digit.DIGIT_8,
      Token.Digit.DIGIT_9,
      Token.Operator.MINUS,
      Token.Operator.DIVIDE,
      Token.Operator.MULTIPLY,
      Token.Operator.PLUS,
      Token.Operator.LEFT_BRACKET,
      Token.Operator.RIGHT_BRACKET,
      Token.Operator.POWER,
      Token.Operator.FACTORIAL,
      Token.Operator.MODULO,
      Token.Operator.PERCENT,
      Token.Operator.SQRT,
      Token.Const.PI,
      Token.Const.E,
    )

  override val replacementMap =
    mapOf(
      // formatterSymbols.grouping doesn't break replacement for other ugly symbols
      formatterSymbols.grouping to "",
      "arcos(" to Token.Func.ARCOS_BRACKET,
      "arsin(" to Token.Func.ARSIN_BRACKET,
      "actan(" to Token.Func.ACTAN_BRACKET,
      formatterSymbols.fractional to Token.Digit.DOT,
      "-" to Token.Operator.MINUS,
      "–" to Token.Operator.MINUS,
      "—" to Token.Operator.MINUS,
      "/" to Token.Operator.DIVIDE,
      "*" to Token.Operator.MULTIPLY,
      "•" to Token.Operator.MULTIPLY,
    )
}

/**
 * - Allow only [Token.Digit.all] and [Token.Letter.all]. No fractional symbols
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
      Token.Digit.DIGIT_0,
      Token.Digit.DIGIT_1,
      Token.Digit.DIGIT_2,
      Token.Digit.DIGIT_3,
      Token.Digit.DIGIT_4,
      Token.Digit.DIGIT_5,
      Token.Digit.DIGIT_6,
      Token.Digit.DIGIT_7,
      Token.Digit.DIGIT_8,
      Token.Digit.DIGIT_9,
      Token.Letter.LETTER_A,
      Token.Letter.LETTER_B,
      Token.Letter.LETTER_C,
      Token.Letter.LETTER_D,
      Token.Letter.LETTER_E,
      Token.Letter.LETTER_F,
    )

  override val replacementMap =
    mapOf(
      "a" to Token.Letter.LETTER_A,
      "b" to Token.Letter.LETTER_B,
      "c" to Token.Letter.LETTER_C,
      "d" to Token.Letter.LETTER_D,
      "e" to Token.Letter.LETTER_E,
      "f" to Token.Letter.LETTER_F,
    )
}

/**
 * - Allow any digit
 * - Allow using any fractional symbol as input ([Token.COMMA] and [Token.PERIOD]), but only if
 *   [allowFraction] is True
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
          in Token.Digit.all -> true
          Token.PERIOD -> allowFraction
          Token.COMMA -> {
            if (allowFraction) {
              replace(cursor, cursor + Token.PERIOD.length, Token.PERIOD)
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
