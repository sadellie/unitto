/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

package com.sadellie.unitto.feature.converter

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.FormatterSymbols
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.LocalLocale
import com.sadellie.unitto.core.ui.common.ColumnWithConstraints
import com.sadellie.unitto.core.ui.common.DrawerButton
import com.sadellie.unitto.core.ui.common.EmptyScreen
import com.sadellie.unitto.core.ui.common.PortraitLandscape
import com.sadellie.unitto.core.ui.common.ScaffoldWithTopBar
import com.sadellie.unitto.core.ui.common.textfield.ExpressionTextField
import com.sadellie.unitto.core.ui.common.textfield.NumberBaseTextField
import com.sadellie.unitto.core.ui.common.textfield.SimpleTextField
import com.sadellie.unitto.core.ui.common.textfield.addBracket
import com.sadellie.unitto.core.ui.common.textfield.addTokens
import com.sadellie.unitto.core.ui.common.textfield.deleteTokens
import com.sadellie.unitto.core.ui.datetime.formatDateWeekDayMonthYear
import com.sadellie.unitto.data.common.format
import com.sadellie.unitto.data.common.isEqualTo
import com.sadellie.unitto.data.common.isExpression
import com.sadellie.unitto.data.converter.ConverterResult
import com.sadellie.unitto.data.converter.UnitID
import com.sadellie.unitto.data.model.converter.UnitGroup
import com.sadellie.unitto.feature.converter.components.DefaultKeyboard
import com.sadellie.unitto.feature.converter.components.NumberBaseKeyboard
import com.sadellie.unitto.feature.converter.components.UnitSelectionButton
import java.math.BigDecimal
import java.util.Locale

@Composable
internal fun ConverterRoute(
    viewModel: ConverterViewModel = hiltViewModel(),
    navigateToLeftScreen: (unitFromId: String, group: UnitGroup) -> Unit,
    navigateToRightScreen: (unitFromId: String, unitToId: String, group: UnitGroup, input: String) -> Unit,
    openDrawer: () -> Unit,
) {
    val uiState = viewModel.converterUIState.collectAsStateWithLifecycle()

    ConverterScreen(
        uiState = uiState.value,
        navigateToLeftScreen = navigateToLeftScreen,
        navigateToRightScreen = navigateToRightScreen,
        openDrawer = openDrawer,
        swapUnits = viewModel::swapUnits,
        updateInput1 = viewModel::updateInput1,
        updateInput2 = viewModel::updateInput2,
        convertDefault = viewModel::convertDefault,
        convertNumberBase = viewModel::convertNumberBase,
    )
}

@Composable
private fun ConverterScreen(
    uiState: UnitConverterUIState,
    navigateToLeftScreen: (unitFromId: String, group: UnitGroup) -> Unit,
    navigateToRightScreen: (unitFromId: String, unitToId: String, group: UnitGroup, input: String) -> Unit,
    openDrawer: () -> Unit,
    swapUnits: (String, String) -> Unit,
    updateInput1: (TextFieldValue) -> Unit,
    updateInput2: (TextFieldValue) -> Unit,
    convertDefault: () -> Unit,
    convertNumberBase: () -> Unit,
) {
    when (uiState) {
        UnitConverterUIState.Loading -> EmptyScreen()

        is UnitConverterUIState.NumberBase -> {
            UnitConverterTopBar(
                openDrawer = openDrawer,
            ) {
                NumberBase(
                    modifier = Modifier.padding(it),
                    uiState = uiState,
                    updateInput1 = updateInput1,
                    navigateToLeftScreen = navigateToLeftScreen,
                    swapUnits = swapUnits,
                    navigateToRightScreen = navigateToRightScreen,
                    convert = convertNumberBase,
                )
            }
        }

        is UnitConverterUIState.Default -> {
            UnitConverterTopBar(
                openDrawer = openDrawer,
            ) {
                Default(
                    modifier = Modifier.padding(it),
                    uiState = uiState,
                    updateInput1 = updateInput1,
                    updateInput2 = updateInput2,
                    navigateToLeftScreen = navigateToLeftScreen,
                    swapUnits = swapUnits,
                    navigateToRightScreen = navigateToRightScreen,
                    convert = convertDefault,
                )
            }
        }
    }
}

@Composable
private fun UnitConverterTopBar(
    openDrawer: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    ScaffoldWithTopBar(
        title = {},
        navigationIcon = { DrawerButton { openDrawer() } },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
        content = { content(it) },
    )
}

@Composable
private fun NumberBase(
    modifier: Modifier,
    uiState: UnitConverterUIState.NumberBase,
    updateInput1: (TextFieldValue) -> Unit,
    navigateToLeftScreen: (unitFromId: String, group: UnitGroup) -> Unit,
    swapUnits: (String, String) -> Unit,
    navigateToRightScreen: (unitFromId: String, unitToId: String, group: UnitGroup, input: String) -> Unit,
    convert: () -> Unit,
) {
    LaunchedEffect(
        uiState.input.text,
        uiState.unitFrom.id,
        uiState.unitTo.id,
    ) {
        convert()
    }

    PortraitLandscape(
        modifier = modifier.fillMaxSize(),
        content1 = { contentModifier ->
            ColumnWithConstraints(modifier = contentModifier) {
                val textFieldModifier = Modifier.weight(2f)

                NumberBaseTextField(
                    modifier = textFieldModifier,
                    minRatio = 0.7f,
                    placeholder = Token.Digit._0,
                    value = uiState.input,
                    onValueChange = updateInput1,
                )
                AnimatedUnitShortName(stringResource(uiState.unitFrom.shortName))

                ConverterResultTextField(
                    modifier = textFieldModifier,
                    result = uiState.result,
                )
                AnimatedUnitShortName(stringResource(uiState.unitTo.shortName))

                Spacer(modifier = Modifier.height(it.maxHeight * 0.03f))

                UnitSelectionButtons(
                    unitFromLabel = stringResource(uiState.unitFrom.displayName),
                    unitToLabel = stringResource(uiState.unitTo.displayName),
                    swapUnits = { swapUnits(uiState.unitTo.id, uiState.unitFrom.id) },
                    navigateToLeftScreen = {
                        navigateToLeftScreen(uiState.unitFrom.id, uiState.unitFrom.group)
                    },
                    navigateToRightScreen = {
                        navigateToRightScreen(
                            uiState.unitFrom.id,
                            uiState.unitTo.id,
                            uiState.unitFrom.group,
                            uiState.input.text,
                        )
                    },
                )
            }
        },
        content2 = { modifier2 ->
            NumberBaseKeyboard(
                modifier = modifier2,
                addDigit = { updateInput1(uiState.input.addTokens(it)) },
                deleteDigit = { updateInput1(uiState.input.deleteTokens()) },
                clearInput = { updateInput1(TextFieldValue()) },
            )
        },
    )
}

@Composable
private fun Default(
    modifier: Modifier,
    uiState: UnitConverterUIState.Default,
    updateInput1: (TextFieldValue) -> Unit,
    updateInput2: (TextFieldValue) -> Unit,
    navigateToLeftScreen: (unitFromId: String, group: UnitGroup) -> Unit,
    swapUnits: (String, String) -> Unit,
    navigateToRightScreen: (unitFromId: String, unitToId: String, group: UnitGroup, input: String) -> Unit,
    convert: () -> Unit,
) {
    val locale: Locale = LocalLocale.current
    val showCalculation = remember(uiState.input1.text, uiState.result) {
        if (uiState.input1.text.isExpression()) {
            if (uiState.result is ConverterResult.Default) {
                return@remember !uiState.result.calculation.isEqualTo(BigDecimal.ZERO)
            }
        }
        false
    }
    val connection by connectivityState()
    val lastUpdate by remember(uiState.currencyRateUpdateState) {
        derivedStateOf {
            if (uiState.currencyRateUpdateState !is CurrencyRateUpdateState.Ready) return@derivedStateOf null
            uiState.currencyRateUpdateState.date.formatDateWeekDayMonthYear(locale)
        }
    }
    var focusedOnInput1 by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(connection) {
        if ((connection == ConnectionState.Available) and (uiState.result is ConverterResult.Error)) {
            val unitFrom = uiState.unitFrom
            if (unitFrom.group == UnitGroup.CURRENCY) convert()
        }
    }

    LaunchedEffect(
        uiState.input1.text,
        uiState.input2.text,
        uiState.unitFrom.id,
        uiState.unitTo.id,
        uiState.formatTime,
    ) {
        convert()
    }

    PortraitLandscape(
        modifier = modifier.fillMaxSize(),
        content1 = { contentModifier ->
            ColumnWithConstraints(modifier = contentModifier) { boxWithConstraintsScope ->
                val textFieldModifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)

                AnimatedVisibility(
                    visible = lastUpdate != null,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut(),
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                            .fillMaxWidth(),
                        text = lastUpdate.orEmpty(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    )
                }

                if (uiState.unitFrom.id == UnitID.foot) {
                    Row(
                        modifier = textFieldModifier,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                        ) {
                            ExpressionTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                value = uiState.input1,
                                minRatio = 0.7f,
                                onValueChange = updateInput1,
                                formatterSymbols = uiState.formatterSymbols,
                                placeholder = Token.Digit._0,
                            )
                            AnimatedUnitShortName(stringResource(uiState.unitFrom.shortName))
                        }

                        VerticalDivider()

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                        ) {
                            ExpressionTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .onFocusEvent { state -> focusedOnInput1 = !state.hasFocus },
                                value = uiState.input2,
                                minRatio = 0.7f,
                                onValueChange = updateInput2,
                                formatterSymbols = uiState.formatterSymbols,
                                placeholder = Token.Digit._0,
                            )
                            AnimatedUnitShortName(stringResource(R.string.unit_inch_short))
                        }
                    }
                } else {
                    ExpressionTextField(
                        modifier = textFieldModifier,
                        value = uiState.input1,
                        minRatio = 0.7f,
                        onValueChange = updateInput1,
                        formatterSymbols = uiState.formatterSymbols,
                        placeholder = Token.Digit._0,
                    )
                    AnimatedVisibility(
                        visible = showCalculation,
                        modifier = Modifier.weight(1f),
                        enter = expandVertically(clip = false),
                        exit = shrinkVertically(clip = false),
                    ) {
                        var calculationTextField by remember(uiState.result) {
                            val text = if (uiState.result is ConverterResult.Default) {
                                uiState.result.calculation
                                    .format(uiState.scale, uiState.outputFormat)
                            } else {
                                ""
                            }
                            mutableStateOf(TextFieldValue(text))
                        }
                        ExpressionTextField(
                            modifier = Modifier,
                            value = calculationTextField,
                            minRatio = 0.7f,
                            onValueChange = { calculationTextField = it },
                            textColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                            formatterSymbols = uiState.formatterSymbols,
                            readOnly = true,
                        )
                    }
                    AnimatedUnitShortName(stringResource(uiState.unitFrom.shortName))
                }

                ConverterResultTextField(
                    modifier = textFieldModifier,
                    result = uiState.result,
                    scale = uiState.scale,
                    outputFormat = uiState.outputFormat,
                    formatterSymbols = uiState.formatterSymbols,
                    onErrorClick = convert,
                )
                AnimatedUnitShortName(
                    stringResource(
                        when (uiState.result) {
                            // Currency conversion can be retried
                            is ConverterResult.Error.Currency -> R.string.click_to_try_again_label
                            else -> uiState.unitTo.shortName
                        },
                    ),
                )

                Spacer(modifier = Modifier.height(boxWithConstraintsScope.maxHeight * 0.03f))

                UnitSelectionButtons(
                    unitFromLabel = stringResource(uiState.unitFrom.displayName),
                    unitToLabel = stringResource(uiState.unitTo.displayName),
                    swapUnits = { swapUnits(uiState.unitTo.id, uiState.unitFrom.id) },
                    navigateToLeftScreen = {
                        navigateToLeftScreen(uiState.unitFrom.id, uiState.unitFrom.group)
                    },
                    navigateToRightScreen = {
                        val input = if (uiState.result is ConverterResult.Default) {
                            uiState.result.calculation.toPlainString()
                        } else {
                            uiState.input1.text
                        }

                        navigateToRightScreen(
                            uiState.unitFrom.id,
                            uiState.unitTo.id,
                            uiState.unitFrom.group,
                            input,
                        )
                    },
                )
            }
        },
        content2 = { modifier2 ->
            DefaultKeyboard(
                modifier = modifier2,
                addDigit = {
                    if (focusedOnInput1) {
                        updateInput1(uiState.input1.addTokens(it))
                    } else {
                        updateInput2(uiState.input2.addTokens(it))
                    }
                },
                deleteDigit = {
                    if (focusedOnInput1) {
                        updateInput1(uiState.input1.deleteTokens())
                    } else {
                        updateInput2(uiState.input2.deleteTokens())
                    }
                },
                clearInput = {
                    updateInput1(TextFieldValue())
                    updateInput2(TextFieldValue())
                },
                fractional = uiState.formatterSymbols.fractional,
                middleZero = uiState.middleZero,
                acButton = uiState.acButton,
                addBracket = {
                    if (focusedOnInput1) {
                        updateInput1(uiState.input1.addBracket())
                    } else {
                        updateInput2(uiState.input2.addBracket())
                    }
                },
            )
        },
    )
}

@Composable
private fun ConverterResultTextField(
    modifier: Modifier,
    result: ConverterResult,
    scale: Int = 0,
    outputFormat: Int = OutputFormat.PLAIN,
    formatterSymbols: FormatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
    onErrorClick: () -> Unit = {},
) {
    val mContext = LocalContext.current
    var resultTextField by remember(result) {
        val value = when (result) {
            is ConverterResult.Default -> result.value.format(scale, outputFormat)
            is ConverterResult.NumberBase -> result.value.uppercase()
            is ConverterResult.Time -> result.format(mContext, formatterSymbols)
            is ConverterResult.FootInch -> result.format(mContext, scale, outputFormat, formatterSymbols)
            else -> ""
        }
        mutableStateOf(TextFieldValue(value))
    }

    when (result) {
        is ConverterResult.Loading -> {
            SimpleTextField(
                modifier = modifier,
                value = TextFieldValue(stringResource(R.string.loading_label)),
                onValueChange = {},
                minRatio = 0.7f,
                readOnly = true,
            )
        }

        is ConverterResult.Error.DivideByZero -> {
            SimpleTextField(
                modifier = modifier,
                value = TextFieldValue(stringResource(R.string.calculator_divide_by_zero_error)),
                onValueChange = { onErrorClick() },
                minRatio = 0.7f,
                readOnly = true,
                textColor = MaterialTheme.colorScheme.error,
            )
        }

        is ConverterResult.Error -> {
            SimpleTextField(
                modifier = modifier,
                value = TextFieldValue(stringResource(R.string.error_label)),
                onValueChange = { onErrorClick() },
                minRatio = 0.7f,
                readOnly = true,
                textColor = MaterialTheme.colorScheme.error,
            )
        }

        is ConverterResult.Default -> {
            ExpressionTextField(
                modifier = modifier,
                value = resultTextField,
                minRatio = 0.7f,
                onValueChange = { resultTextField = it },
                formatterSymbols = formatterSymbols,
                readOnly = true,
            )
        }

        is ConverterResult.NumberBase -> {
            NumberBaseTextField(
                modifier = modifier,
                value = resultTextField,
                onValueChange = { resultTextField = it },
                minRatio = 0.7f,
                readOnly = true,
            )
        }

        is ConverterResult.Time,
        is ConverterResult.FootInch,
        -> {
            SimpleTextField(
                modifier = modifier,
                value = resultTextField,
                onValueChange = { resultTextField = it },
                minRatio = 0.7f,
                readOnly = true,
            )
        }
    }
}

@Composable
private fun AnimatedUnitShortName(
    label: String = stringResource(R.string.loading_label),
) {
    AnimatedContent(
        modifier = Modifier.fillMaxWidth(),
        targetState = label,
        transitionSpec = {
            // Enter animation
            (
                expandHorizontally(clip = false, expandFrom = Alignment.Start) + fadeIn()
                    togetherWith fadeOut()
                ) using SizeTransform(clip = false)
        },
        label = "Animated short name from",
    ) { value ->
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.End),
        )
    }
}

@Composable
private fun UnitSelectionButtons(
    unitFromLabel: String = stringResource(R.string.loading_label),
    unitToLabel: String = stringResource(R.string.loading_label),
    swapUnits: () -> Unit = {},
    navigateToLeftScreen: () -> Unit = {},
    navigateToRightScreen: () -> Unit = {},
) {
    var swapped by remember { mutableStateOf(false) }
    val swapButtonRotation: Float by animateFloatAsState(
        targetValue = if (swapped) 0f else 180f,
        animationSpec = tween(easing = FastOutSlowInEasing),
        label = "Swap button rotation",
    )

    Row(verticalAlignment = Alignment.CenterVertically) {
        UnitSelectionButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            label = unitFromLabel,
            onClick = navigateToLeftScreen,
        )
        IconButton(
            onClick = {
                swapUnits()
                swapped = !swapped
            },
        ) {
            Icon(
                modifier = Modifier.rotate(swapButtonRotation),
                imageVector = Icons.Outlined.SwapHoriz,
                contentDescription = stringResource(R.string.converter_swap_units_description),
            )
        }
        UnitSelectionButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            label = unitToLabel,
            onClick = navigateToRightScreen,
        )
    }
}

@Preview(widthDp = 432, heightDp = 1008, device = "spec:parent=pixel_5,orientation=portrait")
@Preview(widthDp = 432, heightDp = 864, device = "spec:parent=pixel_5,orientation=portrait")
@Preview(widthDp = 597, heightDp = 1393, device = "spec:parent=pixel_5,orientation=portrait")
@Preview(heightDp = 432, widthDp = 1008, device = "spec:parent=pixel_5,orientation=landscape")
@Preview(heightDp = 432, widthDp = 864, device = "spec:parent=pixel_5,orientation=landscape")
@Preview(heightDp = 597, widthDp = 1393, device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun PreviewConverterScreen() {
    ConverterScreen(
        uiState = UnitConverterUIState.Loading,
        navigateToLeftScreen = { _, _ -> },
        navigateToRightScreen = { _, _, _, _ -> },
        openDrawer = {},
        swapUnits = { _, _ -> },
        updateInput1 = {},
        updateInput2 = {},
        convertDefault = {},
        convertNumberBase = {},
    )
}
