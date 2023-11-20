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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.ui.common.textfield.ExpressionTextField
import com.sadellie.unitto.core.ui.common.textfield.FixedInputTextField
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.data.common.format
import java.math.BigDecimal

@Composable
internal fun InputBox(
    modifier: Modifier,
    input: TextFieldValue,
    onCursorChange: (TextRange) -> Unit,
    stack: List<BigDecimal>,
    formatterSymbols: FormatterSymbols,
    precision: Int,
    outputFormat: Int,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(stack) {
        listState.animateScrollToItem(stack.lastIndex.coerceAtLeast(0))
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            state = listState,
            verticalArrangement = Arrangement.Bottom,
        ) {
            items(stack) {
                FixedInputTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = it.format(precision, outputFormat),
                    formatterSymbols = formatterSymbols,
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = {}
                )
            }
        }

        ExpressionTextField(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.25f),
            value = input,
            minRatio = 0.6f,
            onCursorChange = onCursorChange,
            formatterSymbols = formatterSymbols,
            textColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(device = "spec:width=1080px,height=2160px,dpi=440")
@Composable
fun PreviewInputBox() {
    InputBox(
        modifier = Modifier.fillMaxSize(),
        input = TextFieldValue("123456.789"),
        onCursorChange = {},
        stack = listOf(
            BigDecimal("123456.7890"),
            BigDecimal("123456.7890"),
            BigDecimal("123456.7890"),
            BigDecimal("123456.7890"),
            BigDecimal("123456.7890"),
            BigDecimal("123456.7890"),
        ),
        formatterSymbols = FormatterSymbols.Spaces,
        precision = 3,
        outputFormat = OutputFormat.PLAIN
    )
}
