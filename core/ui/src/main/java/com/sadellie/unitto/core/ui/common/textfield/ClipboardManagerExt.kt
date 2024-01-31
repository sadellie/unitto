/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import com.sadellie.unitto.core.base.Token

/**
 * Copy value to clipboard with fractional symbols.
 *
 * Example:
 * "123456.789" will be copied as "123456,789"
 *
 * @param value Internal [TextFieldValue] without formatting with [Token.Digit.dot] as fractional.
 */
internal fun ClipboardManager.copyWithFractional(
    value: TextFieldValue,
    formatterSymbols: FormatterSymbols
) = this.setText(
    AnnotatedString(
        value.annotatedString
            .subSequence(value.selection)
            .text
            .replace(Token.Digit.dot, formatterSymbols.fractional)
    )
)

internal fun ClipboardManager.copy(value: TextFieldValue) = this.setText(
    AnnotatedString(
        value.annotatedString
            .subSequence(value.selection)
            .text
    )
)
