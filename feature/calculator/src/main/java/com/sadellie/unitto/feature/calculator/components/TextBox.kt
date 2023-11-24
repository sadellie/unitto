/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.common.textfield.ExpressionTextField
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.UnformattedTextField
import com.sadellie.unitto.feature.calculator.CalculationResult

@Composable
fun TextBox(
    modifier: Modifier,
    formatterSymbols: FormatterSymbols,
    input: TextFieldValue,
    deleteSymbol: () -> Unit,
    addSymbol: (String) -> Unit,
    onCursorChange: (TextRange) -> Unit,
    output: CalculationResult,
) {
    Column(
        modifier = modifier
            .semantics { testTag = "inputBox" }
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(
                    topStartPercent = 0, topEndPercent = 0,
                    bottomStartPercent = 20, bottomEndPercent = 20
                )
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
            cutCallback = deleteSymbol,
            pasteCallback = addSymbol,
            onCursorChange = onCursorChange,
            formatterSymbols = formatterSymbols
        )
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
            when (output) {
                is CalculationResult.Empty -> {
                    Spacer(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )
                }

                is CalculationResult.Default -> {
                    var outputTF by remember(output) {
                        mutableStateOf(TextFieldValue(output.text))
                    }

                    ExpressionTextField(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        value = outputTF,
                        minRatio = 1f,
                        onCursorChange = { outputTF = outputTF.copy(selection = it) },
                        formatterSymbols = formatterSymbols,
                        textColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f),
                        readOnly = true,
                    )
                }

                is CalculationResult.Fraction -> {
                    var outputTF by remember(output) {
                        mutableStateOf(TextFieldValue(output.text))
                    }
                    ExpressionTextField(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        value = outputTF,
                        minRatio = 1f,
                        onCursorChange = { outputTF = outputTF.copy(selection = it) },
                        formatterSymbols = formatterSymbols,
                        textColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f),
                        readOnly = true,
                    )
                }

                is CalculationResult.DivideByZeroError -> {
                    UnformattedTextField(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        value = TextFieldValue(stringResource(output.label)),
                        minRatio = 0.8f,
                        onCursorChange = {},
                        textColor = MaterialTheme.colorScheme.error,
                        readOnly = true,
                    )
                }

                is CalculationResult.Error -> {
                    UnformattedTextField(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        value = TextFieldValue(stringResource(output.label)),
                        minRatio = 0.8f,
                        onCursorChange = {},
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
                    RoundedCornerShape(100)
                )
                .sizeIn(24.dp, 4.dp)
        )
    }
}