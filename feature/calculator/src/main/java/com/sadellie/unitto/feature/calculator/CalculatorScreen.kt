/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.FormatterSymbols
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.LocalWindowSize
import com.sadellie.unitto.core.ui.WindowHeightSizeClass
import com.sadellie.unitto.core.ui.common.DrawerButton
import com.sadellie.unitto.core.ui.common.EmptyScreen
import com.sadellie.unitto.core.ui.common.ScaffoldWithTopBar
import com.sadellie.unitto.data.model.HistoryItem
import com.sadellie.unitto.feature.calculator.components.CalculatorKeyboard
import com.sadellie.unitto.feature.calculator.components.HistoryItemHeight
import com.sadellie.unitto.feature.calculator.components.HistoryList
import com.sadellie.unitto.feature.calculator.components.TextBox
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

@Composable
internal fun CalculatorRoute(
    openDrawer: () -> Unit,
    viewModel: CalculatorViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    CalculatorScreen(
        uiState = uiState.value,
        openDrawer = openDrawer,
        addTokens = viewModel::addTokens,
        addBracket = viewModel::addBracket,
        clearInput = viewModel::clearInput,
        deleteTokens = viewModel::deleteTokens,
        onValueChange = viewModel::updateInput,
        toggleCalculatorMode = viewModel::updateRadianMode,
        equal = viewModel::equal,
        clearHistory = viewModel::clearHistory,
        onDelete = viewModel::deleteHistoryItem,
    )
}

@Composable
internal fun CalculatorScreen(
    uiState: CalculatorUIState,
    openDrawer: () -> Unit,
    addTokens: (String) -> Unit,
    addBracket: () -> Unit,
    clearInput: () -> Unit,
    deleteTokens: () -> Unit,
    onValueChange: (TextFieldValue) -> Unit,
    toggleCalculatorMode: (Boolean) -> Unit,
    equal: () -> Unit,
    clearHistory: () -> Unit,
    onDelete: (HistoryItem) -> Unit,
) {
    when (uiState) {
        is CalculatorUIState.Loading -> EmptyScreen()
        is CalculatorUIState.Ready -> Ready(
            uiState = uiState,
            openDrawer = openDrawer,
            addSymbol = addTokens,
            addBracket = addBracket,
            clearSymbols = clearInput,
            deleteSymbol = deleteTokens,
            onValueChange = onValueChange,
            toggleAngleMode = { toggleCalculatorMode(!uiState.radianMode) },
            equal = equal,
            clearHistory = clearHistory,
            onDelete = onDelete,
        )
    }
}

@Composable
private fun Ready(
    uiState: CalculatorUIState.Ready,
    openDrawer: () -> Unit,
    addSymbol: (String) -> Unit,
    addBracket: () -> Unit,
    clearSymbols: () -> Unit,
    deleteSymbol: () -> Unit,
    onValueChange: (TextFieldValue) -> Unit,
    toggleAngleMode: () -> Unit,
    equal: () -> Unit,
    clearHistory: () -> Unit,
    onDelete: (HistoryItem) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var showClearHistoryDialog by rememberSaveable { mutableStateOf(false) }
    val dragState = remember {
        AnchoredDraggableState(
            initialValue = DragState.CLOSED,
            positionalThreshold = { 0f },
            velocityThreshold = { 0f },
            animationSpec = tween()
        )
    }
    val isOpen = dragState.currentValue == DragState.OPEN

    ScaffoldWithTopBar(
        title = {},
        navigationIcon = { DrawerButton { openDrawer() } },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.surfaceVariant),
        actions = {
            AnimatedVisibility(isOpen, label = "Clear button reveal") {
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

            var historyListHeight by remember { mutableStateOf(0.dp) }
            val keyboardHeight by remember(historyListHeight, textBoxHeight) {
                derivedStateOf {
                    if (historyListHeight > HistoryItemHeight) {
                        maxHeight - textBoxHeight - HistoryItemHeight
                    } else {
                        maxHeight - textBoxHeight - historyListHeight
                    }
                }
            }

            LaunchedEffect(uiState.partialHistoryView, textBoxHeight) {
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
                addTokens = addSymbol,
                onDelete = onDelete,
                showDeleteButtons = isOpen
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
                onValueChange = onValueChange,
                output = uiState.output
            )

            CalculatorKeyboard(
                modifier = Modifier
                    .semantics { testTag = "ready" }
                    .offset {
                        IntOffset(
                            x = 0,
                            y = (historyListHeight + textBoxHeight)
                                .toPx()
                                .roundToInt()
                        )
                    }
                    .height(keyboardHeight)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                radianMode = uiState.radianMode,
                fractional = uiState.formatterSymbols.fractional,
                addSymbol = addSymbol,
                clearSymbols = clearSymbols,
                deleteSymbol = deleteSymbol,
                toggleAngleMode = toggleAngleMode,
                equal = { focusManager.clearFocus(); equal() },
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
            formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
            history = historyItems,
            middleZero = false,
            acButton = true,
            partialHistoryView = true
        ),
        openDrawer = {},
        addTokens = {},
        addBracket = {},
        clearInput = {},
        deleteTokens = {},
        onValueChange = {},
        toggleCalculatorMode = {},
        equal = {},
        clearHistory = {},
        onDelete = {},
    )
}