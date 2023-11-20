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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.textfield.FixedInputTextField
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.data.model.HistoryItem
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
internal fun HistoryList(
    modifier: Modifier,
    historyItems: List<HistoryItem>,
    formatterSymbols: FormatterSymbols,
    addTokens: (String) -> Unit,
) {
    if (historyItems.isEmpty()) {
        HistoryListPlaceholder(
            modifier = modifier,
        )
    } else {
        HistoryListContent(
            modifier = modifier,
            historyItems = historyItems,
            addTokens = addTokens,
            formatterSymbols = formatterSymbols,
        )
    }
}

@Composable
private fun HistoryListPlaceholder(
    modifier: Modifier,
) {
    Column(
        modifier = modifier.wrapContentHeight(unbounded = true),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.height(HistoryItemHeight),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.History, stringResource(R.string.calculator_no_history))
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
) {
    val state = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    // Very bad workaround for https://issuetracker.google.com/issues/295745063
    // Will remove once the fix is released
    LaunchedEffect(state.isScrollInProgress) {
        focusManager.clearFocus(true)
    }

    LaunchedEffect(historyItems) { state.scrollToItem(0) }

    LazyColumn(
        modifier = modifier,
        state = state,
        reverseLayout = true,
    ) {
        items(historyItems, { it.id }) { historyItem ->
            HistoryListItem(
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
    Column(
        modifier = modifier.height(HistoryItemHeight),
        verticalArrangement = Arrangement.Center
    ) {
        FixedInputTextField(
            value = historyItem.expression,
            formatterSymbols = formatterSymbols,
            textColor = MaterialTheme.colorScheme.onSurfaceVariant,
            onClick = addTokens,
        )

        FixedInputTextField(
            value = historyItem.result,
            formatterSymbols = formatterSymbols,
            textColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            onClick = addTokens,
        )
    }
}

internal val HistoryItemHeight = 108.dp

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
        addTokens = {}
    )
}
