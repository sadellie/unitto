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

package com.sadellie.unitto.core.ui.calculators

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.designsystem.LocalWindowSize
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.ui.textfield.ExpressionTextField
import com.sadellie.unitto.core.ui.textfield.SimpleTextField
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.calculator_divide_by_zero_error
import unitto.core.common.generated.resources.common_error

sealed interface CalculationResult {
  data class Success(val text: String) : CalculationResult

  data object Empty : CalculationResult

  data object DivideByZeroError : CalculationResult

  data object Error : CalculationResult
}

@Composable
fun TextBox(
  modifier: Modifier,
  formatterSymbols: FormatterSymbols,
  input: @Composable () -> Unit,
  output: CalculationResult,
  showHandle: Boolean,
) {
  Column(
    modifier =
      modifier
        .semantics { testTag = "inputBox" }
        .background(
          MaterialTheme.colorScheme.surfaceVariant,
          RoundedCornerShape(
            topStartPercent = 0,
            topEndPercent = 0,
            bottomStartPercent = 20,
            bottomEndPercent = 20,
          ),
        )
        .padding(top = Sizes.extraSmall),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Box(
      Modifier.weight(CalculatorTextBoxDefaults.INPUT_WEIGHT)
        .fillMaxWidth()
        .padding(horizontal = Sizes.small)
    ) {
      input()
    }
    if (LocalWindowSize.current.heightSizeClass > WindowHeightSizeClass.Compact) {
      CalculationResultTextField(
        modifier =
          Modifier.weight(CalculatorTextBoxDefaults.CALCULATION_WEIGHT)
            .fillMaxWidth()
            .padding(horizontal = Sizes.small),
        output = output,
        formatterSymbols = formatterSymbols,
      )
    }
    if (showHandle) {
      // Handle
      Box(
        Modifier.padding(Sizes.small)
          .background(MaterialTheme.colorScheme.onSurfaceVariant, RoundedCornerShape(2.dp))
          .size(24.dp, 4.dp)
      )
    }
  }
}

@Composable
private fun CalculationResultTextField(
  modifier: Modifier,
  output: CalculationResult,
  formatterSymbols: FormatterSymbols,
) {
  when (output) {
    is CalculationResult.Empty -> Spacer(modifier)
    is CalculationResult.Success ->
      ExpressionTextField(
        modifier = modifier,
        state = remember(output) { TextFieldState(output.text) },
        minRatio = CalculatorTextBoxDefaults.OUTPUT_TEXT_FIELD_MIN_RATIO,
        textColor =
          MaterialTheme.colorScheme.onSurfaceVariant.copy(
            CalculatorTextBoxDefaults.CALCULATION_ALPHA
          ),
        formatterSymbols = formatterSymbols,
        readOnly = true,
      )
    is CalculationResult.DivideByZeroError ->
      SimpleTextField(
        modifier = modifier,
        state = TextFieldState(stringResource(Res.string.calculator_divide_by_zero_error)),
        minRatio = CalculatorTextBoxDefaults.OUTPUT_TEXT_FIELD_MIN_RATIO,
        textColor = MaterialTheme.colorScheme.error,
        readOnly = true,
      )
    is CalculationResult.Error ->
      SimpleTextField(
        modifier = modifier,
        state = TextFieldState(stringResource(Res.string.common_error)),
        minRatio = CalculatorTextBoxDefaults.OUTPUT_TEXT_FIELD_MIN_RATIO,
        textColor = MaterialTheme.colorScheme.error,
        readOnly = true,
      )
  }
}

data object CalculatorTextBoxDefaults {
  const val INPUT_WEIGHT = 3f
  const val CALCULATION_WEIGHT = 2f
  const val CALCULATION_ALPHA = 0.6f
  const val INPUT_TEXT_FIELD_MIN_RATIO = 0.25f
  const val OUTPUT_TEXT_FIELD_MIN_RATIO = 0.35f
}

@Composable
@Preview
private fun PreviewTextBox() {
  val formatterSymbols = remember { FormatterSymbols(Token.Space, Token.Period, false) }
  TextBox(
    modifier = Modifier.height(200.dp),
    formatterSymbols = formatterSymbols,
    input = {
      ExpressionTextField(
        modifier = Modifier,
        state = rememberTextFieldState("Text"),
        formatterSymbols = formatterSymbols,
        textColor = MaterialTheme.colorScheme.onSurface,
        minRatio = 0.5f,
      )
    },
    output = CalculationResult.Success("789012.345"),
    showHandle = true,
  )
}

@Composable
@Preview
private fun PreviewTextBoxNoHandle() {
  val formatterSymbols = remember { FormatterSymbols(Token.Space, Token.Period, false) }
  TextBox(
    modifier = Modifier.height(200.dp),
    formatterSymbols = formatterSymbols,
    input = {
      ExpressionTextField(
        modifier = Modifier,
        state = rememberTextFieldState("Text"),
        formatterSymbols = formatterSymbols,
        textColor = MaterialTheme.colorScheme.onSurface,
        minRatio = 0.5f,
      )
    },
    output = CalculationResult.Success("789012.345"),
    showHandle = false,
  )
}
