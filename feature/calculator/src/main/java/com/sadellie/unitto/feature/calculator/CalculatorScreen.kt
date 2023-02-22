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

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.ui.common.MenuButton
import com.sadellie.unitto.core.ui.common.UnittoScreenWithTopBar
import com.sadellie.unitto.core.ui.Formatter
import com.sadellie.unitto.core.ui.theme.NumbersTextStyleDisplayMedium
import com.sadellie.unitto.data.model.HistoryItem
import com.sadellie.unitto.feature.calculator.components.CalculatorKeyboard
import com.sadellie.unitto.feature.calculator.components.DragDownView
import com.sadellie.unitto.feature.calculator.components.HistoryList
import com.sadellie.unitto.feature.calculator.components.InputTextField
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.roundToInt

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
        addSymbol = viewModel::addSymbol,
        clearSymbols = viewModel::clearSymbols,
        deleteSymbol = viewModel::deleteSymbol,
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
    onCursorChange: (IntRange) -> Unit,
    toggleAngleMode: () -> Unit,
    evaluate: () -> Unit,
    clearHistory: () -> Unit
) {
    var showClearHistoryButton by rememberSaveable { mutableStateOf(false) }
    var showClearHistoryDialog by rememberSaveable { mutableStateOf(false) }

    val dragAmount = remember { Animatable(0f) }
    val dragCoroutineScope = rememberCoroutineScope()
    val dragAnimDecay = rememberSplineBasedDecay<Float>()

    var textThingyHeight by remember { mutableStateOf(0) }
    var historyItemHeight by remember { mutableStateOf(0) }

    UnittoScreenWithTopBar(
        title = { Text(stringResource(R.string.calculator)) },
        navigationIcon = { MenuButton { navigateToMenu() } },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.surfaceVariant),
        actions = {
            Crossfade(showClearHistoryButton) {
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
                    IconButton(onClick = navigateToSettings) {
                        Icon(
                            Icons.Outlined.MoreVert,
                            contentDescription = stringResource(R.string.open_settings_description)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        DragDownView(
            modifier = Modifier.padding(paddingValues),
            drag = dragAmount.value.roundToInt().coerceAtLeast(0),
            historyItemHeight = historyItemHeight,
            historyList = {
                HistoryList(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .fillMaxSize(),
                    historyItems = uiState.history,
                    historyItemHeightCallback = { historyItemHeight = it },
                    onTextClick = addSymbol
                )
            },
            textFields = { maxDragAmount ->
                Column(
                    Modifier
                        .onPlaced { textThingyHeight = it.size.height }
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(
                                topStart = 0.dp, topEnd = 0.dp,
                                bottomStart = 32.dp, bottomEnd = 32.dp
                            )
                        )
                        .draggable(
                            orientation = Orientation.Vertical,
                            state = rememberDraggableState { delta ->
                                dragCoroutineScope.launch {
                                    val draggedAmount = (dragAmount.value + delta).coerceAtLeast(0f)
                                    dragAmount.snapTo(draggedAmount)
                                }
                            },
                            onDragStopped = { velocity ->
                                dragCoroutineScope.launch {
                                    dragAmount.animateDecay(velocity, dragAnimDecay)

                                    // Snap to closest anchor (0, one history item, all history items)
                                    val draggedAmount = listOf(0, historyItemHeight, maxDragAmount)
                                        .minBy { abs(dragAmount.value.roundToInt() - it) }
                                        .also {
                                            // Show button only when fully history view is fully expanded
                                            showClearHistoryButton = it == maxDragAmount
                                        }
                                        .toFloat()
                                    dragAmount.animateTo(draggedAmount)
                                }
                            }
                        )
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InputTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        value = TextFieldValue(
                            text = uiState.input,
                            selection = TextRange(uiState.selection.first, uiState.selection.last)
                        ),
                        onCursorChange = onCursorChange,
                        pasteCallback = addSymbol,
                        cutCallback = deleteSymbol
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        text = Formatter.format(uiState.output),
                        textAlign = TextAlign.End,
                        softWrap = false,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        style = NumbersTextStyleDisplayMedium,
                    )
                    // Handle
                    Box(
                        Modifier
                            .padding(16.dp)
                            .background(
                                MaterialTheme.colorScheme.onSurfaceVariant,
                                RoundedCornerShape(100)
                            )
                            .sizeIn(36.dp, 4.dp)
                    )
                }
            },
            numPad = {
                CalculatorKeyboard(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    addSymbol = addSymbol,
                    clearSymbols = clearSymbols,
                    deleteSymbol = deleteSymbol,
                    toggleAngleMode = toggleAngleMode,
                    angleMode = uiState.angleMode,
                    evaluate = evaluate
                )
            }
        )
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

@Preview
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
            date = dtf.parse(it)!!,
            expression = "12345".repeat(10),
            result = "67890"
        )
    }

    CalculatorScreen(
        uiState = CalculatorUIState(
            input = "12345",
            output = "12345",
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