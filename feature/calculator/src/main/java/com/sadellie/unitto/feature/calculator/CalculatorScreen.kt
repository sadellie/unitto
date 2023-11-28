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

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import com.sadellie.unitto.core.ui.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.LocalWindowSize
import com.sadellie.unitto.core.ui.common.MenuButton
import com.sadellie.unitto.core.ui.common.SettingsButton
import com.sadellie.unitto.core.ui.common.UnittoEmptyScreen
import com.sadellie.unitto.core.ui.common.UnittoScreenWithTopBar
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.data.model.HistoryItem
import com.sadellie.unitto.feature.calculator.components.CalculatorKeyboard
import com.sadellie.unitto.feature.calculator.components.HistoryItemHeight
import com.sadellie.unitto.feature.calculator.components.HistoryList
import com.sadellie.unitto.feature.calculator.components.TextBox
import kotlinx.coroutines.launch
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
        addTokens = viewModel::addTokens,
        clearInput = viewModel::clearInput,
        deleteTokens = viewModel::deleteTokens,
        onCursorChange = viewModel::onCursorChange,
        toggleCalculatorMode = viewModel::updateRadianMode,
        evaluate = viewModel::evaluate,
        clearHistory = viewModel::clearHistory,
        addBracket = viewModel::addBracket
    )
}

@Composable
internal fun CalculatorScreen(
    uiState: CalculatorUIState,
    navigateToMenu: () -> Unit,
    navigateToSettings: () -> Unit,
    addTokens: (String) -> Unit,
    addBracket: () -> Unit,
    clearInput: () -> Unit,
    deleteTokens: () -> Unit,
    onCursorChange: (TextRange) -> Unit,
    toggleCalculatorMode: (Boolean) -> Unit,
    evaluate: () -> Unit,
    clearHistory: () -> Unit
) {
    when (uiState) {
        is CalculatorUIState.Loading -> UnittoEmptyScreen()
        is CalculatorUIState.Ready -> Ready(
            uiState = uiState,
            navigateToMenu = navigateToMenu,
            navigateToSettings = navigateToSettings,
            addSymbol = addTokens,
            clearSymbols = clearInput,
            deleteSymbol = deleteTokens,
            onCursorChange = onCursorChange,
            toggleAngleMode = { toggleCalculatorMode(!uiState.radianMode) },
            evaluate = evaluate,
            clearHistory = clearHistory,
            addBracket = addBracket
        )
    }
}

@Composable
private fun Ready(
    uiState: CalculatorUIState.Ready,
    navigateToMenu: () -> Unit,
    navigateToSettings: () -> Unit,
    addSymbol: (String) -> Unit,
    addBracket: () -> Unit,
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
        title = { Text(stringResource(R.string.calculator_title)) },
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
                                stringResource(R.string.clear_history_label)
                            )
                        },
                        modifier = Modifier.semantics { testTag = "historyButton" }
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
            val coroutineScope = rememberCoroutineScope()
            val textBoxFill = if (LocalWindowSize.current.heightSizeClass < WindowHeightSizeClass.Medium) 0.4f else 0.25f

            val textBoxHeight = maxHeight * textBoxFill

            val dragState = remember {
                AnchoredDraggableState(
                    initialValue = DragState.CLOSED,
                    positionalThreshold = { 0f },
                    velocityThreshold = { 0f },
                    animationSpec = tween()
                )
            }

            var historyListHeight by remember { mutableStateOf(0.dp) }
            val keyboardHeight by remember(historyListHeight) {
                derivedStateOf {
                    if (historyListHeight > HistoryItemHeight) {
                        maxHeight - textBoxHeight - HistoryItemHeight
                    } else {
                        maxHeight - textBoxHeight - historyListHeight
                    }
                }
            }

            LaunchedEffect(uiState.partialHistoryView) {
                val anchors: DraggableAnchors<DragState> = with(density) {
                    if (uiState.partialHistoryView) {
                        DraggableAnchors {
                            DragState.CLOSED at 0f
                            DragState.SMALL at HistoryItemHeight.toPx()
                            DragState.OPEN at (maxHeight - textBoxHeight).toPx()
                        }
                    } else {
                        DraggableAnchors {
                            DragState.CLOSED at 0f
                            DragState.OPEN at (maxHeight - textBoxHeight).toPx()
                        }
                    }
                }
                dragState.updateAnchors(anchors)
            }

            LaunchedEffect(dragState.offset) {
                with(density) {
                    if (!dragState.offset.isNaN()) {
                        historyListHeight = dragState.requireOffset().toDp()
                    }
                }
                focusManager.clearFocus()
            }

            LaunchedEffect(dragState.currentValue) {
                showClearHistoryButton = dragState.currentValue == DragState.OPEN
            }

            BackHandler(dragState.currentValue != DragState.CLOSED) {
                coroutineScope.launch {
                    dragState.animateTo(DragState.CLOSED)
                }
            }

            HistoryList(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .fillMaxWidth()
                    .height(historyListHeight),
                historyItems = uiState.history,
                formatterSymbols = uiState.formatterSymbols,
                addTokens = addSymbol
            )

            TextBox(
                modifier = Modifier
                    .offset(y = historyListHeight)
                    .height(textBoxHeight)
                    .fillMaxWidth()
                    .anchoredDraggable(
                        state = dragState,
                        orientation = Orientation.Vertical
                    ),
                formatterSymbols = uiState.formatterSymbols,
                input = uiState.input,
                deleteSymbol = deleteSymbol,
                addSymbol = addSymbol,
                onCursorChange = onCursorChange,
                output = uiState.output
            )

            CalculatorKeyboard(
                modifier = Modifier
                    .semantics { testTag = "ready" }
                    .offset(y = historyListHeight + textBoxHeight)
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
                acButton = uiState.acButton,
                addBracket = addBracket
            )
        }
    }

    if (showClearHistoryDialog) {
        AlertDialog(
            icon = {
                Icon(Icons.Default.Delete, stringResource(R.string.clear_history_label))
            },
            title = {
                Text(stringResource(R.string.clear_history_label))
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
                    Text(stringResource(R.string.clear_label))
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
        uiState = CalculatorUIState.Ready(
            input = TextFieldValue("1.2345"),
            output = CalculationResult.Default("1234"),
            radianMode = false,
            precision = 3,
            outputFormat = OutputFormat.PLAIN,
            formatterSymbols = FormatterSymbols.Spaces,
            history = historyItems,
            allowVibration = false,
            middleZero = false,
            acButton = true,
            partialHistoryView = true
        ),
        navigateToMenu = {},
        navigateToSettings = {},
        addTokens = {},
        clearInput = {},
        deleteTokens = {},
        onCursorChange = {},
        toggleCalculatorMode = {},
        evaluate = {},
        clearHistory = {},
        addBracket = {}
    )
}