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

package com.sadellie.unitto.feature.converter

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.MenuButton
import com.sadellie.unitto.core.ui.common.PortraitLandscape
import com.sadellie.unitto.core.ui.common.SettingsButton
import com.sadellie.unitto.core.ui.common.UnittoScreenWithTopBar
import com.sadellie.unitto.feature.converter.components.Keyboard
import com.sadellie.unitto.feature.converter.components.TopScreenPart

@Composable
internal fun ConverterRoute(
    viewModel: ConverterViewModel = hiltViewModel(),
    navigateToLeftScreen: (String) -> Unit,
    navigateToRightScreen: (unitFrom: String, unitTo: String, input: String?) -> Unit,
    navigateToMenu: () -> Unit,
    navigateToSettings: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    ConverterScreen(
        uiState = uiState.value,
        navigateToLeftScreen = navigateToLeftScreen,
        navigateToRightScreen = navigateToRightScreen,
        navigateToSettings = navigateToSettings,
        navigateToMenu = navigateToMenu,
        swapMeasurements = viewModel::swapUnits,
        processInput = viewModel::addTokens,
        deleteDigit = viewModel::deleteTokens,
        clearInput = viewModel::clearInput,
        onCursorChange = viewModel::onCursorChange,
        cutCallback = viewModel::deleteTokens,
    )
}

@Composable
private fun ConverterScreen(
    uiState: ConverterUIState,
    navigateToLeftScreen: (String) -> Unit,
    navigateToRightScreen: (unitFrom: String, unitTo: String, input: String?) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToMenu: () -> Unit,
    swapMeasurements: () -> Unit,
    processInput: (String) -> Unit,
    deleteDigit: () -> Unit,
    clearInput: () -> Unit,
    onCursorChange: (TextRange) -> Unit,
    cutCallback: () -> Unit,
) {
    UnittoScreenWithTopBar(
        title = { Text(stringResource(R.string.unit_converter)) },
        navigationIcon = { MenuButton { navigateToMenu() } },
        actions = {
            SettingsButton(navigateToSettings)
        },
        colors = TopAppBarDefaults
            .centerAlignedTopAppBarColors(containerColor = Color.Transparent),
        content = { padding ->
            PortraitLandscape(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                content1 = {
                    TopScreenPart(
                        modifier = it,
                        inputValue = uiState.inputValue,
                        calculatedValue = uiState.calculatedValue,
                        outputValue = uiState.resultValue,
                        unitFrom = uiState.unitFrom,
                        unitTo = uiState.unitTo,
                        navigateToLeftScreen = navigateToLeftScreen,
                        navigateToRightScreen = navigateToRightScreen,
                        swapUnits = swapMeasurements,
                        converterMode = uiState.mode,
                        onCursorChange = onCursorChange,
                        cutCallback = cutCallback,
                        pasteCallback = processInput,
                        formatterSymbols = uiState.formatterSymbols,
                    )
                },
                content2 = {
                    Keyboard(
                        modifier = it,
                        addDigit = processInput,
                        deleteDigit = deleteDigit,
                        clearInput = clearInput,
                        converterMode = uiState.mode,
                        allowVibration = uiState.allowVibration,
                        fractional = uiState.formatterSymbols.fractional,
                    )
                }
            )
        }
    )
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
        uiState = ConverterUIState(inputValue = TextFieldValue("1234"), calculatedValue = null, resultValue = ConversionResult.Default("5678"), showLoading = false),
        navigateToLeftScreen = {},
        navigateToRightScreen = {_, _, _ -> },
        navigateToSettings = {},
        navigateToMenu = {},
        swapMeasurements = {},
        processInput = {},
        deleteDigit = {},
        clearInput = {},
        onCursorChange = {},
        cutCallback = {}
    )
}
