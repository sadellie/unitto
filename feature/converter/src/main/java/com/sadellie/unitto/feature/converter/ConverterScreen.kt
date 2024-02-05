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
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.LocalLocale
import com.sadellie.unitto.core.ui.common.ColumnWithConstraints
import com.sadellie.unitto.core.ui.common.EmptyScreen
import com.sadellie.unitto.core.ui.common.MenuButton
import com.sadellie.unitto.core.ui.common.PortraitLandscape
import com.sadellie.unitto.core.ui.common.ScaffoldWithTopBar
import com.sadellie.unitto.core.ui.common.SettingsButton
import com.sadellie.unitto.core.ui.common.textfield.ExpressionTextField
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.NumberBaseTextField
import com.sadellie.unitto.core.ui.common.textfield.SimpleTextField
import com.sadellie.unitto.core.ui.datetime.formatDateWeekDayMonthYear
import com.sadellie.unitto.data.common.format
import com.sadellie.unitto.data.converter.UnitID
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.feature.converter.components.DefaultKeyboard
import com.sadellie.unitto.feature.converter.components.NumberBaseKeyboard
import com.sadellie.unitto.feature.converter.components.UnitSelectionButton
import java.util.Locale

@Composable
internal fun ConverterRoute(
    viewModel: ConverterViewModel = hiltViewModel(),
    navigateToLeftScreen: () -> Unit,
    navigateToRightScreen: () -> Unit,
    navigateToMenu: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    val uiState = viewModel.converterUiState.collectAsStateWithLifecycle()

    ConverterScreen(
        uiState = uiState.value,
        navigateToLeftScreen = navigateToLeftScreen,
        navigateToRightScreen = navigateToRightScreen,
        navigateToSettings = navigateToSettings,
        navigateToMenu = navigateToMenu,
        swapUnits = viewModel::swapUnits,
        processInput = viewModel::addTokens,
        deleteDigit = viewModel::deleteTokens,
        clearInput = viewModel::clearInput,
        onValueChange = viewModel::updateInput,
        onFocusOnInput2 = viewModel::updateFocused,
        onErrorClick = viewModel::updateCurrencyRates,
        addBracket = viewModel::addBracket
    )
}

@Composable
private fun ConverterScreen(
    uiState: UnitConverterUIState,
    navigateToLeftScreen: () -> Unit,
    navigateToRightScreen: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToMenu: () -> Unit,
    swapUnits: () -> Unit,
    processInput: (String) -> Unit,
    deleteDigit: () -> Unit,
    clearInput: () -> Unit,
    onValueChange: (TextFieldValue) -> Unit,
    onFocusOnInput2: (Boolean) -> Unit,
    onErrorClick: (AbstractUnit) -> Unit,
    addBracket: () -> Unit,
) {
    when (uiState) {
        UnitConverterUIState.Loading -> EmptyScreen()

        is UnitConverterUIState.NumberBase -> {
            UnitConverterTopBar(
                navigateToMenu = navigateToMenu,
                navigateToSettings = navigateToSettings
            ) {
                NumberBase(
                    modifier = Modifier.padding(it),
                    uiState = uiState,
                    onValueChange = onValueChange,
                    processInput = processInput,
                    deleteDigit = deleteDigit,
                    navigateToLeftScreen = navigateToLeftScreen,
                    swapUnits = swapUnits,
                    navigateToRightScreen = navigateToRightScreen,
                    clearInput = clearInput
                )
            }
        }

        is UnitConverterUIState.Default -> {
            UnitConverterTopBar(
                navigateToMenu = navigateToMenu,
                navigateToSettings = navigateToSettings
            ) {
                Default(
                    modifier = Modifier.padding(it),
                    uiState = uiState,
                    onValueChange = onValueChange,
                    onFocusOnInput2 = onFocusOnInput2,
                    processInput = processInput,
                    deleteDigit = deleteDigit,
                    navigateToLeftScreen = navigateToLeftScreen,
                    swapUnits = swapUnits,
                    navigateToRightScreen = navigateToRightScreen,
                    clearInput = clearInput,
                    refreshCurrencyRates = onErrorClick,
                    addBracket = addBracket,
                )
            }
        }
    }
}

@Composable
private fun UnitConverterTopBar(
    navigateToMenu: () -> Unit,
    navigateToSettings: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    ScaffoldWithTopBar(
        title = { Text(stringResource(R.string.unit_converter_title)) },
        navigationIcon = { MenuButton { navigateToMenu() } },
        actions = {
            SettingsButton(navigateToSettings)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
        content = { content(it) }
    )
}

@Composable
private fun NumberBase(
    modifier: Modifier,
    uiState: UnitConverterUIState.NumberBase,
    onValueChange: (TextFieldValue) -> Unit,
    processInput: (String) -> Unit,
    deleteDigit: () -> Unit,
    navigateToLeftScreen: () -> Unit,
    swapUnits: () -> Unit,
    navigateToRightScreen: () -> Unit,
    clearInput: () -> Unit,
) {
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
                    onValueChange = onValueChange,
                )
                AnimatedUnitShortName(stringResource(uiState.unitFrom.shortName))

                ConverterResultTextField(
                    modifier = textFieldModifier,
                    result = uiState.result
                )
                AnimatedUnitShortName(stringResource(uiState.unitTo.shortName))

                Spacer(modifier = Modifier.height(it.maxHeight * 0.03f))

                UnitSelectionButtons(
                    unitFromLabel = stringResource(uiState.unitFrom.displayName),
                    unitToLabel = stringResource(uiState.unitTo.displayName),
                    swapUnits = swapUnits,
                    navigateToLeftScreen = navigateToLeftScreen,
                    navigateToRightScreen = navigateToRightScreen
                )
            }
        },
        content2 = {
            NumberBaseKeyboard(
                modifier = it,
                addDigit = processInput,
                deleteDigit = deleteDigit,
                clearInput = clearInput,
            )
        }
    )
}

@Composable
private fun Default(
    modifier: Modifier,
    uiState: UnitConverterUIState.Default,
    onValueChange: (TextFieldValue) -> Unit,
    onFocusOnInput2: (Boolean) -> Unit,
    processInput: (String) -> Unit,
    deleteDigit: () -> Unit,
    navigateToLeftScreen: () -> Unit,
    swapUnits: () -> Unit,
    navigateToRightScreen: () -> Unit,
    clearInput: () -> Unit,
    refreshCurrencyRates: (AbstractUnit) -> Unit,
    addBracket: () -> Unit,
) {
    val locale: Locale = LocalLocale.current
    var calculation by remember(uiState.calculation) {
        mutableStateOf(
            TextFieldValue(uiState.calculation?.format(uiState.scale, uiState.outputFormat) ?: "")
        )
    }
    val connection by connectivityState()
    val lastUpdate by remember(uiState.currencyRateUpdateState) {
        derivedStateOf {
            if (uiState.currencyRateUpdateState !is CurrencyRateUpdateState.Ready) return@derivedStateOf null
            uiState.currencyRateUpdateState.date.formatDateWeekDayMonthYear(locale)
        }
    }

    LaunchedEffect(connection) {
        if ((connection == ConnectionState.Available) and (uiState.result == ConverterResult.Error)) {
            val unitFrom = uiState.unitFrom
            if (unitFrom.group == UnitGroup.CURRENCY) refreshCurrencyRates(unitFrom)
        }
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
                        textAlign = TextAlign.Center
                    )
                }

                if (uiState.unitFrom.id == UnitID.foot) {
                    Row(
                        modifier = textFieldModifier,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            ExpressionTextField(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                value = uiState.input1,
                                minRatio = 0.7f,
                                onValueChange = onValueChange,
                                formatterSymbols = uiState.formatterSymbols,
                                placeholder = Token.Digit._0,
                            )
                            AnimatedUnitShortName(stringResource(uiState.unitFrom.shortName))
                        }

                        VerticalDivider()

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            ExpressionTextField(
                                modifier = Modifier.fillMaxWidth().weight(1f)
                                    .onFocusEvent { state -> onFocusOnInput2(state.hasFocus) },
                                value = uiState.input2,
                                minRatio = 0.7f,
                                onValueChange = onValueChange,
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
                        onValueChange = onValueChange,
                        formatterSymbols = uiState.formatterSymbols,
                        placeholder = Token.Digit._0,
                    )
                    AnimatedVisibility(
                        visible = calculation.text.isNotEmpty(),
                        modifier = Modifier.weight(1f),
                        enter = expandVertically(clip = false),
                        exit = shrinkVertically(clip = false)
                    ) {
                        ExpressionTextField(
                            modifier = Modifier,
                            value = calculation,
                            minRatio = 0.7f,
                            onValueChange = { calculation = it },
                            textColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                            formatterSymbols = uiState.formatterSymbols,
                            readOnly = true
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
                    onErrorClick = { refreshCurrencyRates(uiState.unitFrom) }
                )
                AnimatedUnitShortName(
                    stringResource(
                        if (uiState.result is ConverterResult.Error) R.string.click_to_try_again_label
                        else uiState.unitTo.shortName
                    )
                )

                Spacer(modifier = Modifier.height(boxWithConstraintsScope.maxHeight * 0.03f))

                UnitSelectionButtons(
                    unitFromLabel = stringResource(uiState.unitFrom.displayName),
                    unitToLabel = stringResource(uiState.unitTo.displayName),
                    swapUnits = swapUnits,
                    navigateToLeftScreen = navigateToLeftScreen,
                    navigateToRightScreen = navigateToRightScreen
                )
            }
        },
        content2 = {
            DefaultKeyboard(
                modifier = it,
                addDigit = processInput,
                deleteDigit = deleteDigit,
                clearInput = clearInput,
                fractional = uiState.formatterSymbols.fractional,
                middleZero = uiState.middleZero,
                acButton = uiState.acButton,
                addBracket = addBracket
            )
        }
    )
}

@Composable
private fun ConverterResultTextField(
    modifier: Modifier,
    result: ConverterResult,
    scale: Int = 0,
    outputFormat: Int = OutputFormat.PLAIN,
    formatterSymbols: FormatterSymbols = FormatterSymbols.Spaces,
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
                readOnly = true
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
                readOnly = true
            )
        }

        is ConverterResult.NumberBase -> {
            NumberBaseTextField(
                modifier = modifier,
                value = resultTextField,
                onValueChange = { resultTextField = it },
                minRatio = 0.7f,
                readOnly = true
            )
        }

        is ConverterResult.Time,
        is ConverterResult.FootInch -> {
            SimpleTextField(
                modifier = modifier,
                value = resultTextField,
                onValueChange = { resultTextField = it },
                minRatio = 0.7f,
                readOnly = true
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
            (expandHorizontally(clip = false, expandFrom = Alignment.Start) + fadeIn()
                    togetherWith fadeOut()) using SizeTransform(clip = false)
        },
        label = "Animated short name from"
    ) { value ->
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.End)
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
        label = "Swap button rotation"
    )

    Row(verticalAlignment = Alignment.CenterVertically) {
        UnitSelectionButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            label = unitFromLabel,
            onClick = navigateToLeftScreen
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
                contentDescription = stringResource(R.string.converter_swap_units_description)
            )
        }
        UnitSelectionButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            label = unitToLabel,
            onClick = navigateToRightScreen
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
        navigateToLeftScreen = {},
        navigateToRightScreen = {},
        navigateToSettings = {},
        navigateToMenu = {},
        swapUnits = {},
        processInput = {},
        deleteDigit = {},
        clearInput = {},
        onValueChange = {},
        onFocusOnInput2 = {},
        onErrorClick = {},
        addBracket = {}
    )
}
