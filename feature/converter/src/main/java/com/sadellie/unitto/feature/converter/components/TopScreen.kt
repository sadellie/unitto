/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

package com.sadellie.unitto.feature.converter.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.ColumnWithConstraints
import com.sadellie.unitto.core.ui.common.textfield.ExpressionTextField
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.UnformattedTextField
import com.sadellie.unitto.core.ui.common.textfield.formatExpression
import com.sadellie.unitto.core.ui.common.textfield.formatTime
import com.sadellie.unitto.data.model.AbstractUnit
import com.sadellie.unitto.feature.converter.ConversionResult
import com.sadellie.unitto.feature.converter.ConverterMode

/**
 * Top of the main screen. Contains input and output TextFields, and unit selection row of buttons.
 * It's a separate composable, so that we support album orientation (this element will be on the left)
 *
 * @param modifier Modifier that is applied to Column.
 * @param inputValue Current input value (like big decimal).
 * @param calculatedValue Current calculated value (like big decimal), will be shown under input when it
 * has an expression in it.
 * @param outputValue Current output value (like big decimal).
 * @param unitFrom [AbstractUnit] on the left.
 * @param unitTo [AbstractUnit] on the right.
 * @param navigateToLeftScreen Function that is called when clicking left unit selection button.
 * @param navigateToRightScreen Function that is called when clicking right unit selection button.
 * @param swapUnits Method to swap units.
 * @param converterMode [ConverterMode.BASE] doesn't use formatting for input/output.
 */
@Composable
internal fun TopScreenPart(
    modifier: Modifier,
    inputValue: TextFieldValue,
    calculatedValue: String?,
    outputValue: ConversionResult,
    unitFrom: AbstractUnit?,
    unitTo: AbstractUnit?,
    navigateToLeftScreen: (String) -> Unit,
    navigateToRightScreen: (unitFrom: String, unitTo: String, input: String?) -> Unit,
    swapUnits: () -> Unit,
    converterMode: ConverterMode,
    onCursorChange: (TextRange) -> Unit,
    cutCallback: () -> Unit,
    pasteCallback: (String) -> Unit,
    formatterSymbols: FormatterSymbols,
    onErrorClick: () -> Unit,
) {
    var swapped by remember { mutableStateOf(false) }
    val swapButtonRotation: Float by animateFloatAsState(
        targetValue = if (swapped) 0f else 180f,
        animationSpec = tween(easing = FastOutSlowInEasing),
        label = "Swap button rotation"
    )
    val mContext = LocalContext.current

    ColumnWithConstraints(
        modifier = modifier,
    ) {
        Crossfade(modifier = Modifier.weight(2f), targetState = converterMode) { mode ->
            if (mode == ConverterMode.BASE) {
                UnformattedTextField(
                    modifier = Modifier,
                    value = inputValue,
                    onCursorChange = onCursorChange,
                    minRatio = 0.7f,
                    cutCallback = cutCallback,
                    pasteCallback = pasteCallback,
                    placeholder = Token.Digit._0
                )
            } else {
                ExpressionTextField(
                    modifier = Modifier,
                    value = inputValue,
                    onCursorChange = onCursorChange,
                    formatterSymbols = formatterSymbols,
                    minRatio = 0.7f,
                    cutCallback = cutCallback,
                    pasteCallback = pasteCallback,
                    placeholder = Token.Digit._0
                )
            }
        }
        AnimatedVisibility(
            visible = !calculatedValue.isNullOrEmpty(),
            modifier = Modifier.weight(1f),
            enter = expandVertically(clip = false),
            exit = shrinkVertically(clip = false)
        ) {
            var calculatedTextFieldValue by remember(calculatedValue) {
                mutableStateOf(
                    TextFieldValue(calculatedValue?.formatExpression(formatterSymbols) ?: "")
                )
            }
            ExpressionTextField(
                modifier = Modifier,
                value = calculatedTextFieldValue,
                onCursorChange = { newSelection ->
                    calculatedTextFieldValue =
                        calculatedTextFieldValue.copy(selection = newSelection)
                },
                formatterSymbols = formatterSymbols,
                textColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                minRatio = 0.7f
            )
        }
        AnimatedContent(
            modifier = Modifier.fillMaxWidth(),
            targetState = stringResource(unitFrom?.shortName ?: R.string.loading_label),
            transitionSpec = {
                // Enter animation
                (expandHorizontally(clip = false, expandFrom = Alignment.Start) + fadeIn()
                        togetherWith fadeOut())
                    .using(SizeTransform(clip = false))
            },
            label = "Animated short name from"
        ) { value ->
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.End)
            )
        }

        when (outputValue) {
            is ConversionResult.Default -> {
                var outputTextFieldValue: TextFieldValue by remember(outputValue) {
                    mutableStateOf(TextFieldValue(outputValue.result))
                }
                ExpressionTextField(
                    modifier = Modifier.weight(2f),
                    value = outputTextFieldValue,
                    onCursorChange = { newSelection ->
                        outputTextFieldValue = outputTextFieldValue.copy(selection = newSelection)
                    },
                    formatterSymbols = formatterSymbols,
                    readOnly = true,
                    minRatio = 0.7f
                )
            }

            is ConversionResult.Time -> {
                var outputTextFieldValue: TextFieldValue by remember(outputValue) {
                    mutableStateOf(
                        TextFieldValue(
                            outputValue.result
                                .formatTime(mContext, unitTo?.basicUnit, formatterSymbols)
                        )
                    )
                }
                UnformattedTextField(
                    modifier = Modifier.weight(2f),
                    value = outputTextFieldValue,
                    onCursorChange = { newSelection ->
                        outputTextFieldValue = outputTextFieldValue.copy(selection = newSelection)
                    },
                    minRatio = 0.7f,
                    readOnly = true
                )
            }

            is ConversionResult.NumberBase -> {
                var outputTextFieldValue: TextFieldValue by remember(outputValue) {
                    mutableStateOf(TextFieldValue(outputValue.result.uppercase()))
                }
                UnformattedTextField(
                    modifier = Modifier.weight(2f),
                    value = outputTextFieldValue,
                    onCursorChange = { newSelection ->
                        outputTextFieldValue = outputTextFieldValue.copy(selection = newSelection)
                    },
                    minRatio = 0.7f,
                    readOnly = true
                )
            }

            is ConversionResult.Loading -> {
                UnformattedTextField(
                    modifier = Modifier.weight(2f),
                    value = TextFieldValue(stringResource(R.string.loading_label)),
                    onCursorChange = {},
                    minRatio = 0.7f,
                    readOnly = true
                )
            }

            is ConversionResult.Error -> {
                UnformattedTextField(
                    modifier = Modifier.weight(2f),
                    value = TextFieldValue(stringResource(R.string.error_label)),
                    onCursorChange = { onErrorClick() },
                    minRatio = 0.7f,
                    readOnly = true,
                    textColor = MaterialTheme.colorScheme.error
                )
            }
        }

        val supportLabelTo = when {
            outputValue is ConversionResult.Error -> R.string.try_again_label
            (unitTo?.shortName != null) -> unitTo.shortName
            else -> R.string.loading_label
        }

        AnimatedContent(
            modifier = Modifier.fillMaxWidth(),
            targetState = stringResource(supportLabelTo),
            transitionSpec = {
                // Enter animation
                (expandHorizontally(clip = false, expandFrom = Alignment.Start) + fadeIn()
                        // Exit animation
                        togetherWith fadeOut())
                    .using(SizeTransform(clip = false))
            },
            label = "Animated short label to"
        ) { value ->
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.End)
            )
        }

        Spacer(modifier = Modifier.height(it.maxHeight * 0.03f))

        Row(verticalAlignment = Alignment.CenterVertically) {
            UnitSelectionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = { unitFrom?.let { unit -> navigateToLeftScreen(unit.unitId) } },
                label = unitFrom?.displayName ?: R.string.loading_label,
            )
            IconButton(
                onClick = {
                    swapUnits()
                    swapped = !swapped
                },
                enabled = unitFrom != null
            ) {
                Icon(
                    modifier = Modifier.rotate(swapButtonRotation),
                    imageVector = Icons.Outlined.SwapHoriz,
                    contentDescription = stringResource(R.string.swap_units_description)
                )
            }
            UnitSelectionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = {
                    if (unitTo == null) return@UnitSelectionButton
                    if (unitFrom == null) return@UnitSelectionButton

                    val input = when (outputValue) {
                        is ConversionResult.Error, ConversionResult.Loading -> null
                        else -> calculatedValue ?: inputValue.text
                    }

                    navigateToRightScreen(
                        unitFrom.unitId,
                        unitTo.unitId,
                        input?.ifEmpty { "0" }
                    )
                },
                label = unitTo?.displayName ?: R.string.loading_label,
            )
        }
    }
}
