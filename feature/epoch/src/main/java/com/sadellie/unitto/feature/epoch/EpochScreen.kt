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

package com.sadellie.unitto.feature.epoch

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.ui.common.PortraitLandscape
import com.sadellie.unitto.core.ui.common.UnittoTopAppBar
import com.sadellie.unitto.feature.epoch.component.EpochKeyboard
import com.sadellie.unitto.feature.epoch.component.TopPart

@Composable
internal fun EpochRoute(
    navigateUpAction: () -> Unit,
    viewModel: EpochViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    EpochScreen(
        navigateUpAction = navigateUpAction,
        uiState = uiState.value,
        addSymbol = viewModel::addSymbol,
        deleteSymbol = viewModel::deleteSymbol,
        swap = viewModel::swap,
        clearSymbols = viewModel::clearSymbols,
        onCursorChange = viewModel::onCursorChange
    )
}

@Composable
private fun EpochScreen(
    navigateUpAction: () -> Unit,
    uiState: EpochUIState,
    addSymbol: (String) -> Unit,
    deleteSymbol: () -> Unit,
    clearSymbols: () -> Unit,
    swap: () -> Unit,
    onCursorChange: (IntRange) -> Unit
) {
    UnittoTopAppBar(
        title = stringResource(R.string.epoch_converter),
        navigateUpAction = navigateUpAction
    ) { padding ->
        PortraitLandscape(
            modifier = Modifier.padding(padding),
            content1 = { topContentModifier ->
                TopPart(
                    modifier = topContentModifier.padding(horizontal = 8.dp),
                    dateToUnix = uiState.dateToUnix,
                    dateValue = uiState.dateField,
                    unixValue = uiState.unixField,
                    swap = swap,
                    selection = TextRange(uiState.selection.first, uiState.selection.last),
                    onCursorChange = onCursorChange
                )
            },
            content2 = { bottomModifier ->
                EpochKeyboard(
                    modifier = bottomModifier,
                    addSymbol = addSymbol,
                    clearSymbols = clearSymbols,
                    deleteSymbol = deleteSymbol
                )
            }
        )
    }
}
