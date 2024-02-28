/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

package com.sadellie.unitto.feature.calculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.base.FormatterSymbols
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.LocalWindowSize
import com.sadellie.unitto.core.ui.WindowHeightSizeClass
import com.sadellie.unitto.core.ui.common.textfield.ExpressionTextField
import com.sadellie.unitto.core.ui.common.textfield.SimpleTextField
import com.sadellie.unitto.feature.calculator.CalculationResult

@Composable
fun TextBox(
    modifier: Modifier,
    formatterSymbols: FormatterSymbols,
    input: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    output: CalculationResult,
) {
    Column(
        modifier = modifier
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
            .padding(top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ExpressionTextField(
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            value = input,
            minRatio = 0.5f,
            onValueChange = onValueChange,
            formatterSymbols = formatterSymbols,
        )
        if (LocalWindowSize.current.heightSizeClass > WindowHeightSizeClass.Compact) {
            val calculationResultModifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)

            when (output) {
                is CalculationResult.Empty -> {
                    Spacer(
                        modifier = calculationResultModifier,
                    )
                }

                is CalculationResult.Success -> {
                    var outputTF by remember(output) { mutableStateOf(TextFieldValue(output.text)) }

                    ExpressionTextField(
                        modifier = calculationResultModifier,
                        value = outputTF,
                        minRatio = 0.8f,
                        onValueChange = { outputTF = outputTF.copy(selection = it.selection) },
                        textColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f),
                        formatterSymbols = formatterSymbols,
                        readOnly = true,
                    )
                }

                is CalculationResult.DivideByZeroError -> {
                    SimpleTextField(
                        modifier = calculationResultModifier,
                        value = TextFieldValue(stringResource(R.string.calculator_divide_by_zero_error)),
                        minRatio = 0.8f,
                        onValueChange = {},
                        textColor = MaterialTheme.colorScheme.error,
                        readOnly = true,
                    )
                }

                is CalculationResult.Error -> {
                    SimpleTextField(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        value = TextFieldValue(stringResource(R.string.error_label)),
                        minRatio = 0.8f,
                        onValueChange = {},
                        textColor = MaterialTheme.colorScheme.error,
                        readOnly = true,
                    )
                }
            }
        }
        // Handle
        Box(
            Modifier
                .padding(8.dp)
                .background(
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    RoundedCornerShape(100),
                )
                .sizeIn(24.dp, 4.dp),
        )
    }
}
