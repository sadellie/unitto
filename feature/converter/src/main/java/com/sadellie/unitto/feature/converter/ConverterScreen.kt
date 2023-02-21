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

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.ui.R
import com.sadellie.unitto.core.ui.common.AnimatedTopBarText
import com.sadellie.unitto.core.ui.common.MenuButton
import com.sadellie.unitto.core.ui.common.PortraitLandscape
import com.sadellie.unitto.core.ui.common.UnittoScreenWithTopBar
import com.sadellie.unitto.feature.converter.components.Keyboard
import com.sadellie.unitto.feature.converter.components.TopScreenPart
import kotlinx.coroutines.delay

@Composable
internal fun ConverterRoute(
    viewModel: ConverterViewModel = hiltViewModel(),
    navigateToLeftScreen: (String) -> Unit,
    navigateToRightScreen: (unitFrom: String, unitTo: String, input: String) -> Unit,
    navigateToMenu: () -> Unit,
    navigateToSettings: () -> Unit
) {
    val uiState = viewModel.uiStateFlow.collectAsStateWithLifecycle()

    ConverterScreen(
        uiState = uiState.value,
        navigateToLeftScreen = navigateToLeftScreen,
        navigateToRightScreen = navigateToRightScreen,
        navigateToSettings = navigateToSettings,
        navigateToMenu = navigateToMenu,
        swapMeasurements = viewModel::swapUnits,
        processInput = viewModel::processInput,
        deleteDigit = viewModel::deleteDigit,
        clearInput = viewModel::clearInput,
        onOutputTextFieldClick = viewModel::toggleFormatTime
    )
}

@Composable
private fun ConverterScreen(
    uiState: ConverterUIState,
    navigateToLeftScreen: (String) -> Unit,
    navigateToRightScreen: (unitFrom: String, unitTo: String, input: String) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToMenu: () -> Unit,
    swapMeasurements: () -> Unit,
    processInput: (String) -> Unit,
    deleteDigit: () -> Unit,
    clearInput: () -> Unit,
    onOutputTextFieldClick: () -> Unit
) {
    var launched: Boolean by rememberSaveable { mutableStateOf(false) }

    UnittoScreenWithTopBar(
        title = { AnimatedTopBarText(launched) },
        navigationIcon = { MenuButton { navigateToMenu() } },
        actions = {
            IconButton(onClick = navigateToSettings) {
                Icon(
                    Icons.Outlined.MoreVert,
                    contentDescription = stringResource(R.string.open_settings_description)
                )
            }
        },
        colors = TopAppBarDefaults
            .centerAlignedTopAppBarColors(containerColor = Color.Transparent),
        content = { padding ->
            PortraitLandscape(
                modifier = Modifier.padding(padding),
                content1 = {
                    TopScreenPart(
                        modifier = it,
                        inputValue = uiState.inputValue,
                        calculatedValue = uiState.calculatedValue,
                        outputValue = uiState.resultValue,
                        unitFrom = uiState.unitFrom,
                        unitTo = uiState.unitTo,
                        networkLoading = uiState.showLoading,
                        networkError = uiState.showError,
                        navigateToLeftScreen = navigateToLeftScreen,
                        navigateToRightScreen = navigateToRightScreen,
                        swapUnits = swapMeasurements,
                        converterMode = uiState.mode,
                        formatTime = uiState.formatTime,
                        onOutputTextFieldClick = onOutputTextFieldClick
                    )
                },
                content2 = {
                    Keyboard(
                        modifier = it,
                        addDigit = processInput,
                        deleteDigit = deleteDigit,
                        clearInput = clearInput,
                        converterMode = uiState.mode,
                        allowVibration = uiState.allowVibration
                    )
                }
            )
        }
    )

    LaunchedEffect(Unit) {
        /**
         * 1.5 seconds is enough for user to see "Hello" in app bar title. Also, since we are using
         * Unit as key, "Hello" will be switched to app name only when composable is not getting
         * recomposed for 1.5 seconds.
         */
        delay(1500)
        launched = true
    }
}

@Preview
@Composable
private fun PreviewConverterScreen() {
    ConverterScreen(
        uiState = ConverterUIState(),
        navigateToLeftScreen = {},
        navigateToRightScreen = {_, _, _ -> },
        navigateToSettings = {},
        navigateToMenu = {},
        swapMeasurements = {},
        processInput = {},
        deleteDigit = {},
        clearInput = {},
        onOutputTextFieldClick = {}
    )
}