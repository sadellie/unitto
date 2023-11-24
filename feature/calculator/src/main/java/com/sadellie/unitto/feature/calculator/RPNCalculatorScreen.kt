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

package com.sadellie.unitto.feature.calculator

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.MenuButton
import com.sadellie.unitto.core.ui.common.SettingsButton
import com.sadellie.unitto.core.ui.common.UnittoEmptyScreen
import com.sadellie.unitto.core.ui.common.UnittoScreenWithTopBar
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import io.github.sadellie.evaluatto.RPNCalculation
import java.math.BigDecimal

@Composable
internal fun RPNCalculatorRoute(
    openDrawer: () -> Unit,
    navigateToSettings: () -> Unit,
    viewModel: RPNCalculatorViewModel = hiltViewModel(),
) {
    when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
        RPNCalculatorUIState.Loading -> UnittoEmptyScreen()
        is RPNCalculatorUIState.Ready -> RPNCalculatorScreen(
            uiState = uiState,
            openDrawer = openDrawer,
            navigateToSettings = navigateToSettings,
            onCursorChange = viewModel::onCursorChange,
            onCalculationClick = viewModel::onCalculationClick,
            onInputEditClick = viewModel::onInputEdit
        )
    }
}

@Composable
internal fun RPNCalculatorScreen(
    uiState: RPNCalculatorUIState.Ready,
    openDrawer: () -> Unit,
    navigateToSettings: () -> Unit,
    onCursorChange: (TextRange) -> Unit,
    onCalculationClick: (RPNCalculation) -> Unit,
    onInputEditClick: (RPNInputEdit) -> Unit,
) {
    UnittoScreenWithTopBar(
        title = { Text(stringResource(id = R.string.calculator_title)) },
        navigationIcon = { MenuButton(openDrawer) },
        actions = { SettingsButton(navigateToSettings) }
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues)
        ) {
            InputBox(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight(if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) 0.3f else 0.5f),
                input = uiState.input,
                stack = uiState.stack,
                formatterSymbols = uiState.formatterSymbols,
                precision = uiState.precision,
                outputFormat = uiState.outputFormat,
                onCursorChange = onCursorChange
            )
            RPNCalculatorKeyboard(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxSize(),
                fractional = uiState.formatterSymbols.fractional,
                middleZero = uiState.middleZero,
                allowVibration = uiState.allowVibration,
                onCalculationClick = onCalculationClick,
                onInputEditClick = onInputEditClick
            )
        }
    }
}

@Preview(widthDp = 432, heightDp = 1008, device = "spec:parent=pixel_5,orientation=portrait")
@Preview(widthDp = 432, heightDp = 864, device = "spec:parent=pixel_5,orientation=portrait")
@Preview(widthDp = 597, heightDp = 1393, device = "spec:parent=pixel_5,orientation=portrait")
@Composable
private fun RPNCalculatorScreenPreview() {
    RPNCalculatorScreen(
        uiState = RPNCalculatorUIState.Ready(
            input = TextFieldValue("test"),
            stack = listOf(
                BigDecimal("123456.7890"),
                BigDecimal("123456.7890"),
                BigDecimal("123456.7890"),
                BigDecimal("123456.7890"),
                BigDecimal("123456.7890"),
                BigDecimal("123456.7890"),
            ),
            precision = 3,
            outputFormat = OutputFormat.PLAIN,
            formatterSymbols = FormatterSymbols.Spaces,
            allowVibration = true,
            middleZero = true,
        ),
        openDrawer = {},
        navigateToSettings = {},
        onCalculationClick = {},
        onInputEditClick = {},
        onCursorChange = {}
    )
}
