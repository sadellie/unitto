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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.ui.common.UnittoTopAppBar
import com.sadellie.unitto.core.ui.theme.NumbersTextStyleDisplayMedium
import com.sadellie.unitto.feature.calculator.components.CalculatorKeyboard
import com.sadellie.unitto.feature.calculator.components.InputTextField

@Composable
internal fun CalculatorRoute(
    navigateUpAction: () -> Unit,
    viewModel: CalculatorViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    CalculatorScreen(
        uiState = uiState.value,
        navigateUpAction = navigateUpAction,
        addSymbol = viewModel::addSymbol,
        clearSymbols = viewModel::clearSymbols,
        deleteSymbol = viewModel::deleteSymbol,
        onCursorChange = viewModel::onCursorChange,
        toggleAngleMode = viewModel::toggleCalculatorMode,
        evaluate = viewModel::evaluate
    )
}

@Composable
private fun CalculatorScreen(
    uiState: CalculatorUIState,
    navigateUpAction: () -> Unit,
    addSymbol: (String) -> Unit,
    clearSymbols: () -> Unit,
    deleteSymbol: () -> Unit,
    onCursorChange: (IntRange) -> Unit,
    toggleAngleMode: () -> Unit,
    evaluate: () -> Unit
) {
    UnittoTopAppBar(
        title = stringResource(R.string.calculator),
        navigateUpAction = navigateUpAction,
    ) {
        Column(Modifier.padding(it)) {
            InputTextField(
                modifier = Modifier.fillMaxWidth(),
                value = TextFieldValue(
                    text = uiState.input,
                    selection = TextRange(uiState.selection.first, uiState.selection.last)
                ),
                onCursorChange = onCursorChange
            )
            AnimatedVisibility(visible = uiState.output.isNotEmpty()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    // Quick fix to prevent the UI from crashing
                    text = uiState.output,
                    textAlign = TextAlign.End,
                    softWrap = false,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    style = NumbersTextStyleDisplayMedium,
                )
            }
            CalculatorKeyboard(
                modifier = Modifier,
                addSymbol = addSymbol,
                clearSymbols = clearSymbols,
                deleteSymbol = deleteSymbol,
                toggleAngleMode = toggleAngleMode,
                angleMode = uiState.angleMode,
                evaluate = evaluate
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCalculatorScreen() {
    CalculatorScreen(
        uiState = CalculatorUIState(),
        navigateUpAction = {},
        addSymbol = {},
        clearSymbols = {},
        deleteSymbol = {},
        onCursorChange = {},
        toggleAngleMode = {},
        evaluate = {}
    )
}