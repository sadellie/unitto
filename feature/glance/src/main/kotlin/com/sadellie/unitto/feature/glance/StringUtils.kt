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

package com.sadellie.unitto.feature.glance

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.text.TextRange
import com.sadellie.unitto.core.ui.textfield.addBracket
import com.sadellie.unitto.core.ui.textfield.addTokens

internal fun String.addToken(token: String): String =
  TextFieldState(this, TextRange(length)).apply { this.addTokens(token) }.text.toString()

internal fun String.addBracket(): String =
  TextFieldState(this, TextRange(length)).apply { this.addBracket() }.text.toString()
