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
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.MenuButton
import com.sadellie.unitto.core.ui.common.SettingsButton
import com.sadellie.unitto.core.ui.common.UnittoScreenWithTopBar
import com.sadellie.unitto.core.ui.common.textfield.ExpressionTextField
import com.sadellie.unitto.core.ui.common.textfield.UnformattedTextField
import com.sadellie.unitto.data.model.HistoryItem
import com.sadellie.unitto.feature.calculator.components.CalculatorKeyboard
import com.sadellie.unitto.feature.calculator.components.HistoryList
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
internal fun CalculatorRoute(
    navigateToMenu: () -> Unit,
    navigateToSettings: () -> Unit,
    viewModel: CalculatorViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    CalculatorScreen(
        uiState = uiState.value,
        navigateToMenu = navigateToMenu,
        navigateToSettings = navigateToSettings,
        addSymbol = viewModel::addTokens,
        clearSymbols = viewModel::clearInput,
        deleteSymbol = viewModel::deleteTokens,
        onCursorChange = viewModel::onCursorChange,
        toggleAngleMode = viewModel::toggleCalculatorMode,
        evaluate = viewModel::evaluate,
        clearHistory = viewModel::clearHistory
    )
}

@Composable
private fun CalculatorScreen(
    uiState: CalculatorUIState,
    navigateToMenu: () -> Unit,
    navigateToSettings: () -> Unit,
    addSymbol: (String) -> Unit,
    clearSymbols: () -> Unit,
    deleteSymbol: () -> Unit,
    onCursorChange: (TextRange) -> Unit,
    toggleAngleMode: () -> Unit,
    evaluate: () -> Unit,
    clearHistory: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var showClearHistoryDialog by rememberSaveable { mutableStateOf(false) }
    var showClearHistoryButton by rememberSaveable { mutableStateOf(false) }

    UnittoScreenWithTopBar(
        title = { Text(stringResource(R.string.calculator)) },
        navigationIcon = { MenuButton { navigateToMenu() } },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.surfaceVariant),
        actions = {
            Crossfade(showClearHistoryButton, label = "Clear button reveal") {
                if (it) {
                    IconButton(
                        onClick = { showClearHistoryDialog = true },
                        content = {
                            Icon(
                                Icons.Default.Delete,
                                stringResource(R.string.calculator_clear_history_title)
                            )
                        }
                    )
                } else {
                    SettingsButton(navigateToSettings)
                }
            }
        }
    ) { paddingValues ->
        BoxWithConstraints(
            modifier = Modifier.padding(paddingValues),
        ) {
            val density = LocalDensity.current
            var historyItemHeight by remember { mutableStateOf(0.dp) }
            val textBoxHeight = maxHeight * 0.25f
            var dragStateCurrentValue by rememberSaveable { mutableStateOf(DragState.CLOSED) }

            val dragState = rememberDragState(
                historyItem = historyItemHeight,
                max = maxHeight - textBoxHeight,
                initialValue = dragStateCurrentValue
            )
            val dragDp by remember(dragState.requireOffset()) {
                derivedStateOf {
                    focusManager.clearFocus(true)
                    with(density) { dragState.requireOffset().toDp() }
                }
            }
            val keyboardHeight by remember(dragState.requireOffset()) {
                derivedStateOf {
                    if (dragDp > historyItemHeight) {
                        maxHeight - textBoxHeight - historyItemHeight
                    } else {
                        maxHeight - textBoxHeight - dragDp
                    }
                }
            }

            LaunchedEffect(dragState.currentValue) {
                dragStateCurrentValue = dragState.currentValue
                showClearHistoryButton = dragState.currentValue == DragState.OPEN
            }

            // History
            HistoryList(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .fillMaxWidth()
                    .height(dragDp),
                historyItems = uiState.history,
                heightCallback = { historyItemHeight = it },
                formatterSymbols = uiState.formatterSymbols,
                addTokens = addSymbol,
            )

            // Input
            Column(
                Modifier
                    .offset(y = dragDp)
                    .height(textBoxHeight)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(
                            topStartPercent = 0, topEndPercent = 0,
                            bottomStartPercent = 20, bottomEndPercent = 20
                        )
                    )
                    .anchoredDraggable(
                        state = dragState,
                        orientation = Orientation.Vertical
                    )
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ExpressionTextField(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    value = uiState.input,
                    minRatio = 0.5f,
                    cutCallback = deleteSymbol,
                    pasteCallback = addSymbol,
                    onCursorChange = onCursorChange,
                    formatterSymbols = uiState.formatterSymbols
                )
                if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    when (uiState.output) {
                        is CalculationResult.Default -> {
                            var output by remember(uiState.output) {
                                mutableStateOf(TextFieldValue(uiState.output.text))
                            }

                            ExpressionTextField(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                value = output,
                                minRatio = 1f,
                                onCursorChange = { output = output.copy(selection = it) },
                                formatterSymbols = uiState.formatterSymbols,
                                textColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f),
                                readOnly = true,
                            )
                        }

                        else -> {
                            val label = uiState.output.label?.let { stringResource(it) } ?: ""

                            UnformattedTextField(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                value = TextFieldValue(label),
                                minRatio = 1f,
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

            // Keyboard
            CalculatorKeyboard(
                modifier = Modifier
                    .offset(y = dragDp + textBoxHeight)
                    .height(keyboardHeight)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                radianMode = uiState.radianMode,
                fractional = uiState.formatterSymbols.fractional,
                allowVibration = uiState.allowVibration,
                addSymbol = addSymbol,
                clearSymbols = clearSymbols,
                deleteSymbol = deleteSymbol,
                toggleAngleMode = toggleAngleMode,
                evaluate = evaluate,
                middleZero = uiState.middleZero,
            )
        }
    }

    if (showClearHistoryDialog) {
        AlertDialog(
            icon = {
                Icon(Icons.Default.Delete, stringResource(R.string.calculator_clear_history_title))
            },
            title = {
                Text(stringResource(R.string.calculator_clear_history_title))
            },
            text = {
                Text(stringResource(R.string.calculator_clear_history_support))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        clearHistory()
                        showClearHistoryDialog = false
                    }
                ) {
                    Text(stringResource(R.string.calculator_clear_history_label))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showClearHistoryDialog = false }
                ) {
                    Text(stringResource(R.string.cancel_label))
                }
            },
            onDismissRequest = { showClearHistoryDialog = false }
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
private fun PreviewCalculatorScreen() {
    val dtf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())

    val historyItems = listOf(
        "13.06.1989 23:59:15",
        "13.06.1989 23:59:16",
        "13.06.1989 23:59:17",
        "14.06.1989 23:59:17",
        "14.06.1989 23:59:18",
        "14.07.1989 23:59:18",
        "14.07.1989 23:59:19",
        "14.07.2005 23:59:19",
    ).map {
        HistoryItem(
            id = it.hashCode(),
            date = dtf.parse(it)!!,
            expression = "12345".repeat(10),
            result = "1234"
        )
    }

    CalculatorScreen(
        uiState = CalculatorUIState(
            input = TextFieldValue("1.2345"),
            output = CalculationResult.Default("1234"),
            history = historyItems
        ),
        navigateToMenu = {},
        navigateToSettings = {},
        addSymbol = {},
        clearSymbols = {},
        deleteSymbol = {},
        onCursorChange = {},
        toggleAngleMode = {},
        evaluate = {},
        clearHistory = {}
    )
}