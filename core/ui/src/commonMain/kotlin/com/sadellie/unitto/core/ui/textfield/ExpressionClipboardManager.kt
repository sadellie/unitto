/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2025 Elshan Agaev
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

package com.sadellie.unitto.core.ui.textfield

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.NativeClipboard
import com.sadellie.unitto.core.common.FormatterSymbols

/**
 * Removes grouping symbols when copying expression to clipboard.
 *
 * @property formatterSymbols Current [FormatterSymbols].
 * @property nativeClipboard [NativeClipboard] provided by system.
 */
@OptIn(ExperimentalComposeUiApi::class)
internal expect class ExpressionClipboardManager(
  formatterSymbols: FormatterSymbols,
  clipboard: NativeClipboard,
) : Clipboard {
  override val nativeClipboard: NativeClipboard

  override suspend fun getClipEntry(): ClipEntry?

  override suspend fun setClipEntry(clipEntry: ClipEntry?)
}
