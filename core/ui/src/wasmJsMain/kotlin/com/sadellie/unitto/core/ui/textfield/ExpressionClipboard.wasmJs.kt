/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025-2026 Elshan Agaev
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
import com.sadellie.unitto.core.common.FormatterSymbols

internal actual fun createExpressionClipboard(
  formatterSymbols: FormatterSymbols,
  systemClipboard: Clipboard,
): ExpressionClipboardManager = WasmJsExpressionClipboard(formatterSymbols, systemClipboard)

private class WasmJsExpressionClipboard(
  private val formatterSymbols: FormatterSymbols,
  private val systemClipboard: Clipboard,
) : ExpressionClipboardManager, Clipboard by systemClipboard {
  @OptIn(ExperimentalComposeUiApi::class, ExperimentalWasmJsInterop::class)
  override suspend fun setClipEntry(clipEntry: ClipEntry?) {
    if (clipEntry == null) {
      setClipEntry(ClipEntry.withPlainText(""))
      return
    }
    if (clipEntry.clipboardItems.length < 1) return
    val firstClipDataItem = clipEntry.clipboardItems[0]
    val firstClipDataItemText = firstClipDataItem.toString()
    val clearedClipDataItem = firstClipDataItemText.replace(formatterSymbols.grouping.symbol, "")
    systemClipboard.setClipEntry(ClipEntry.withPlainText(clearedClipDataItem))
  }
}
