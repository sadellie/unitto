/*
 * Unitto is a unit converter for Android
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

package com.sadellie.unitto.feature.glance.glance

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.sadellie.unitto.core.ui.common.textfield.addBracket
import com.sadellie.unitto.core.ui.common.textfield.addTokens

internal fun String.addToken(token: String): String =
    TextFieldValue(this, TextRange(length)).addTokens(token).text

internal fun String.addBracket(): String =
    TextFieldValue(this, TextRange(length)).addBracket().text
