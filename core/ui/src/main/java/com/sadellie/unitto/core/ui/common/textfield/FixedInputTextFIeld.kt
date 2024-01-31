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

package com.sadellie.unitto.core.ui.common.textfield

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.common.textfield.texttoolbar.UnittoTextToolbar
import com.sadellie.unitto.core.ui.theme.LocalNumberTypography

@Composable
fun FixedInputTextField(
    modifier: Modifier = Modifier,
    value: String,
    formatterSymbols: FormatterSymbols,
    textColor: Color,
    onClick: (cleanValue: String) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val expression = value.take(1000)
    var expressionValue by remember(expression) {
        mutableStateOf(TextFieldValue(expression, TextRange(expression.length)))
    }

    val expressionInteractionSource = remember(expression) { MutableInteractionSource() }
    LaunchedEffect(expressionInteractionSource) {
        expressionInteractionSource.interactions.collect {
            if (it is PressInteraction.Release) onClick(value)
        }
    }

    CompositionLocalProvider(
        LocalTextInputService provides null,
        LocalTextToolbar provides UnittoTextToolbar(
            view = LocalView.current,
            copyCallback = {
                clipboardManager.copyWithFractional(expressionValue, formatterSymbols)
                expressionValue = expressionValue.copy(selection = TextRange(expressionValue.selection.end))
            }
        )
    ) {
        BasicTextField(
            value = expressionValue,
            onValueChange = { expressionValue = it },
            maxLines = 1,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .horizontalScroll(rememberScrollState(), reverseScrolling = true),
            textStyle = LocalNumberTypography.current.displaySmall.copy(color = textColor, textAlign = TextAlign.End),
            readOnly = true,
            visualTransformation = ExpressionTransformer(formatterSymbols),
            interactionSource = expressionInteractionSource
        )
    }
}
