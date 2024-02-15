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

package com.sadellie.unitto.feature.datecalculator.addsubtract

import androidx.compose.ui.text.input.TextFieldValue
import com.sadellie.unitto.core.base.FormatterSymbols
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.feature.datecalculator.ZonedDateTimeUtils
import java.time.ZonedDateTime

internal data class AddSubtractUIState(
    val start: ZonedDateTime = ZonedDateTimeUtils.nowWithMinutes(),
    val result: ZonedDateTime = ZonedDateTimeUtils.nowWithMinutes(),
    val years: TextFieldValue = TextFieldValue(),
    val months: TextFieldValue = TextFieldValue(),
    val days: TextFieldValue = TextFieldValue(),
    val hours: TextFieldValue = TextFieldValue(),
    val minutes: TextFieldValue = TextFieldValue(),
    val addition: Boolean = true,
    val formatterSymbols: FormatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
    val allowVibration: Boolean = false,
)
