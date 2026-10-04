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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.designsystem.theme.LocalNumberTypography
import com.sadellie.unitto.core.ui.calculators.CalculationResult
import com.sadellie.unitto.core.ui.calculators.CalculatorTextBoxDefaults
import com.sadellie.unitto.core.ui.calculators.TextBox
import com.sadellie.unitto.core.ui.textfield.AutoSizeTextField

@Composable
internal fun TextFieldsBox(
  modifier: Modifier,
  formatterSymbols: FormatterSymbols,
  state: TextFieldState,
  output: CalculationResult,
  // todo onEnter: () -> Unit,
  showHandle: Boolean,
  // todo onHardwareInput: (() -> Unit)?,
) {
  TextBox(
    modifier = modifier,
    formatterSymbols = formatterSymbols,
    input = {
      AutoSizeTextField(
        state = state,
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        inputTransformation = ProgrammerInputTransformation(formatterSymbols.grouping),
        textStyle =
          LocalNumberTypography.current.displayLarge.copy(
            MaterialTheme.colorScheme.onSurfaceVariant
          ),
        lineLimits = TextFieldLineLimits.SingleLine,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
        minRatio = CalculatorTextBoxDefaults.INPUT_TEXT_FIELD_MIN_RATIO,
      )
    },
    output = output,
    showHandle = showHandle,
  )
}
