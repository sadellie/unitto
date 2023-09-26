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

package com.sadellie.unitto.feature.calculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.textfield.ExpressionTransformer
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.UnittoTextToolbar
import com.sadellie.unitto.core.ui.common.textfield.clearAndFilterExpression
import com.sadellie.unitto.core.ui.common.textfield.copyWithoutGrouping
import com.sadellie.unitto.core.ui.theme.numbersDisplayMedium
import com.sadellie.unitto.data.model.HistoryItem
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
internal fun HistoryList(
    modifier: Modifier,
    historyItems: List<HistoryItem>,
    heightCallback: (Dp) -> Unit,
    formatterSymbols: FormatterSymbols,
    addTokens: (String) -> Unit,
) {
    if (historyItems.isEmpty()) {
        HistoryListPlaceholder(
            modifier = modifier,
            heightCallback = heightCallback
        )
    } else {
        HistoryListContent(
            modifier = modifier,
            historyItems = historyItems,
            addTokens = addTokens,
            formatterSymbols = formatterSymbols,
            heightCallback = heightCallback
        )
    }
}

@Composable
private fun HistoryListPlaceholder(
    modifier: Modifier,
    heightCallback: (Dp) -> Unit,
) {
    val density = LocalDensity.current

    Column(
        modifier = modifier.wrapContentHeight(unbounded = true),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .onPlaced { heightCallback(with(density) { it.size.height.toDp() }) }
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.History, null)
            Text(stringResource(R.string.calculator_no_history))
        }
    }
}

@Composable
private fun HistoryListContent(
    modifier: Modifier,
    historyItems: List<HistoryItem>,
    addTokens: (String) -> Unit,
    formatterSymbols: FormatterSymbols,
    heightCallback: (Dp) -> Unit,
) {
    val density = LocalDensity.current
    val state = rememberLazyListState()
    val firstItem by remember(historyItems) { mutableStateOf(historyItems.first()) }
    val restOfTheItems by remember(firstItem) { mutableStateOf(historyItems.drop(1)) }

    LaunchedEffect(historyItems) { state.scrollToItem(0) }

    LazyColumn(
        modifier = modifier,
        state = state,
        reverseLayout = true,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom)
    ) {
        // We do this so that callback for items height is called only once
        item(firstItem.id) {
            HistoryListItem(
                modifier = Modifier.onPlaced { heightCallback(with(density) { it.size.height.toDp() }) },
                historyItem = historyItems.first(),
                formatterSymbols = formatterSymbols,
                addTokens = addTokens,
            )
        }

        items(restOfTheItems, { it.id }) { historyItem ->
            HistoryListItem(
                modifier = Modifier,
                historyItem = historyItem,
                formatterSymbols = formatterSymbols,
                addTokens = addTokens,
            )
        }
    }
}

@Composable
private fun HistoryListItem(
    modifier: Modifier = Modifier,
    historyItem: HistoryItem,
    formatterSymbols: FormatterSymbols,
    addTokens: (String) -> Unit,
) {
    val clipboardManager = LocalClipboardManager.current
    val expression = historyItem.expression.take(1000)
    var expressionValue by remember(expression) {
        mutableStateOf(TextFieldValue(expression, TextRange(expression.length)))
    }
    val result = historyItem.result.take(1000)
    var resultValue by remember(result) {
        mutableStateOf(TextFieldValue(result, TextRange(result.length)))
    }

    val expressionInteractionSource = remember(expression) { MutableInteractionSource() }
    LaunchedEffect(expressionInteractionSource) {
        expressionInteractionSource.interactions.collect {
            if (it is PressInteraction.Release) addTokens(expression.clearAndFilterExpression(formatterSymbols))
        }
    }

    val resultInteractionSource = remember(result) { MutableInteractionSource() }
    LaunchedEffect(resultInteractionSource) {
        resultInteractionSource.interactions.collect {
            if (it is PressInteraction.Release) addTokens(result.clearAndFilterExpression(formatterSymbols))
        }
    }

    Column(modifier = modifier) {
        CompositionLocalProvider(
            LocalTextInputService provides null,
            LocalTextToolbar provides UnittoTextToolbar(
                view = LocalView.current,
                copyCallback = {
                    clipboardManager.copyWithoutGrouping(expressionValue, formatterSymbols)
                    expressionValue = expressionValue.copy(selection = TextRange(expressionValue.selection.end))
                }
            )
        ) {
            BasicTextField(
                value = expressionValue,
                onValueChange = { expressionValue = it },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .horizontalScroll(rememberScrollState(), reverseScrolling = true),
                textStyle = MaterialTheme.typography.numbersDisplayMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.End),
                readOnly = true,
                visualTransformation = ExpressionTransformer(formatterSymbols),
                interactionSource = expressionInteractionSource
            )
        }

        CompositionLocalProvider(
            LocalTextInputService provides null,
            LocalTextToolbar provides UnittoTextToolbar(
                view = LocalView.current,
                copyCallback = {
                    clipboardManager.copyWithoutGrouping(resultValue, formatterSymbols)
                    resultValue = resultValue.copy(selection = TextRange(resultValue.selection.end))
                }
            )
        ) {
            BasicTextField(
                value = resultValue,
                onValueChange = { resultValue = it },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .horizontalScroll(rememberScrollState(), reverseScrolling = true),
                textStyle = MaterialTheme.typography.numbersDisplayMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f), textAlign = TextAlign.End),
                readOnly = true,
                visualTransformation = ExpressionTransformer(formatterSymbols),
                interactionSource = resultInteractionSource
            )
        }
    }
}

@Preview
@Composable
private fun PreviewHistoryList() {
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
            result = "67890"
        )
    }

    HistoryList(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .fillMaxSize(),
        historyItems = historyItems,
        formatterSymbols = FormatterSymbols.Spaces,
        heightCallback = {},
        addTokens = {}
    )
}
