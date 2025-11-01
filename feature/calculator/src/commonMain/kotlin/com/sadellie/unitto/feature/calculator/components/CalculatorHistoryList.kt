/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.designsystem.icons.symbols.Close
import com.sadellie.unitto.core.designsystem.icons.symbols.History
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.model.calculator.CalculatorHistoryItem
import com.sadellie.unitto.core.ui.ProvideColor
import com.sadellie.unitto.core.ui.textfield.FixedExpressionInputTextField
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.calculator_no_history
import unitto.core.common.generated.resources.common_delete

@Composable
internal fun CalculatorHistoryList(
  modifier: Modifier,
  calculatorHistoryItems: List<CalculatorHistoryItem>,
  formatterSymbols: FormatterSymbols,
  addTokens: (String) -> Unit,
  onDelete: (CalculatorHistoryItem) -> Unit,
  showDeleteButtons: Boolean,
) {
  Crossfade(targetState = calculatorHistoryItems.isEmpty(), label = "History list") { emptyList ->
    if (emptyList) {
      HistoryListPlaceholder(modifier = modifier)
    } else {
      HistoryListContent(
        modifier = modifier,
        calculatorHistoryItems = calculatorHistoryItems,
        formatterSymbols = formatterSymbols,
        addTokens = addTokens,
        onDelete = onDelete,
        showDeleteButtons = showDeleteButtons,
      )
    }
  }
}

@Composable
private fun HistoryListPlaceholder(modifier: Modifier) {
  Column(
    modifier = modifier.wrapContentHeight(unbounded = true),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Column(
      modifier = Modifier.height(HistoryItemHeight),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      ProvideColor(MaterialTheme.colorScheme.onSurfaceVariant) {
        Icon(
          imageVector = Symbols.History,
          contentDescription = stringResource(Res.string.calculator_no_history),
        )
        Text(stringResource(Res.string.calculator_no_history))
      }
    }
  }
}

@Composable
private fun HistoryListContent(
  modifier: Modifier,
  calculatorHistoryItems: List<CalculatorHistoryItem>,
  formatterSymbols: FormatterSymbols,
  addTokens: (String) -> Unit,
  onDelete: (CalculatorHistoryItem) -> Unit,
  showDeleteButtons: Boolean,
) {
  val state = rememberLazyListState()
  val focusManager = LocalFocusManager.current

  // Selection handles cause lag
  LaunchedEffect(state.isScrollInProgress) { focusManager.clearFocus(true) }

  LaunchedEffect(calculatorHistoryItems) {
    // Don't scroll when the UI is in state where user can delete an item. This fixes items
    // placement animation
    if (!showDeleteButtons) state.scrollToItem(0)
  }

  LazyColumn(modifier = modifier, state = state, reverseLayout = true) {
    items(calculatorHistoryItems, { it.id }) { historyItem ->
      HistoryListItem(
        modifier = Modifier.animateItem(),
        calculatorHistoryItem = historyItem,
        formatterSymbols = formatterSymbols,
        addTokens = addTokens,
        onDelete = { onDelete(historyItem) },
        showDeleteButton = showDeleteButtons,
      )
    }
  }
}

@Composable
private fun HistoryListItem(
  modifier: Modifier = Modifier,
  calculatorHistoryItem: CalculatorHistoryItem,
  formatterSymbols: FormatterSymbols,
  addTokens: (String) -> Unit,
  onDelete: () -> Unit,
  showDeleteButton: Boolean,
) {
  Row(
    modifier = modifier.height(HistoryItemHeight),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    AnimatedVisibility(visible = showDeleteButton) {
      IconButton(onClick = onDelete) {
        Icon(
          modifier = Modifier,
          imageVector = Symbols.Close,
          contentDescription = stringResource(Res.string.common_delete),
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
    }
    Column(
      modifier = Modifier.weight(1f).fillMaxHeight().padding(end = 8.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.End,
    ) {
      FixedExpressionInputTextField(
        modifier = Modifier.fillMaxWidth(),
        value = calculatorHistoryItem.expression,
        formatterSymbols = formatterSymbols,
        textColor = MaterialTheme.colorScheme.onSurfaceVariant,
        onClick = { addTokens(calculatorHistoryItem.expression) },
      )

      FixedExpressionInputTextField(
        modifier = Modifier.fillMaxWidth(),
        value = calculatorHistoryItem.result,
        formatterSymbols = formatterSymbols,
        textColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
        onClick = { addTokens(calculatorHistoryItem.result) },
      )
    }
  }
}

internal val HistoryItemHeight = 108.dp

@Preview(showBackground = true)
@Composable
private fun PreviewHistoryListPlaceholder() {
  HistoryListPlaceholder(modifier = Modifier.fillMaxSize())
}

@Preview(showBackground = true)
@Composable
private fun PreviewCalculatorHistoryList() {
  val calculatorHistoryItems =
    listOf(
        "13.06.1989 23:59:15",
        "13.06.1989 23:59:16",
        "13.06.1989 23:59:17",
        "14.06.1989 23:59:17",
        "14.06.1989 23:59:18",
        "14.07.1989 23:59:18",
        "14.07.1989 23:59:19",
        "14.07.2005 23:59:19",
      )
      .map {
        CalculatorHistoryItem(
          id = it.hashCode(),
          expression = "12345".repeat(10),
          result = "67890",
        )
      }

  CalculatorHistoryList(
    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh).fillMaxSize(),
    calculatorHistoryItems = calculatorHistoryItems,
    formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
    addTokens = {},
    onDelete = {},
    showDeleteButtons = true,
  )
}
