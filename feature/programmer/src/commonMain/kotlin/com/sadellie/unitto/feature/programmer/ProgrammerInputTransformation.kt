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

import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.runtime.Stable
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.ui.textfield.InputTransformationWithReplacement

@Stable
internal data class ProgrammerInputTransformation(private val grouping: Token.Formatter) :
  InputTransformationWithReplacement {
  override val legalTokens: List<String> =
    listOf(
      Token.Nand.symbol,
      Token.Or.symbol,
      Token.And.symbol,
      Token.Not.symbol,
      Token.Nor.symbol,
      Token.Xor.symbol,
      Token.Lsh.symbol,
      Token.Rsh.symbol,
      Token.RoL.symbol,
      Token.RoR.symbol,
      Token.Mod.symbol,
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
      Token.Minus.symbol,
      Token.Divide.symbol,
      Token.Multiply.symbol,
      Token.Plus.symbol,
      Token.LeftBracket.symbol,
      Token.RightBracket.symbol,
    )

  override val replacementMap: Map<String, String> =
    mapOf(
      grouping.symbol to "",
      "-" to Token.Minus.symbol,
      "–" to Token.Minus.symbol,
      "—" to Token.Minus.symbol,
      "/" to Token.Divide.symbol,
      "*" to Token.Multiply.symbol,
      "•" to Token.Multiply.symbol,
      "a" to Token.LetterA.symbol,
      "b" to Token.LetterB.symbol,
      "c" to Token.LetterC.symbol,
      "d" to Token.LetterD.symbol,
      "e" to Token.LetterE.symbol,
      "f" to Token.LetterF.symbol,
    )

  private val longProgrammerTokens =
    listOf(
      Token.Or.symbol,
      Token.And.symbol,
      Token.Not.symbol,
      Token.Nand.symbol,
      Token.Nor.symbol,
      Token.Xor.symbol,
      Token.Lsh.symbol,
      Token.Rsh.symbol,
      Token.RoL.symbol,
      Token.RoR.symbol,
      Token.Mod.symbol,
    )

  override val illegalTokens: List<String> = emptyList()

  override fun TextFieldBuffer.transformInput() =
    transformInputWithReplacements(longProgrammerTokens)
}
