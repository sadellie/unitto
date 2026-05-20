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

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.ui.platform.AndroidClipboard
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.nativeClipboardManager
import com.sadellie.unitto.core.common.FormatterSymbols

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
@SuppressLint("VisibleForTests")
internal class AndroidExpressionClipboard(
  private val formatterSymbols: FormatterSymbols,
  private val systemClipboard: Clipboard,
) : ExpressionClipboardManager, AndroidClipboard, Clipboard by systemClipboard {
  override suspend fun setClipEntry(clipEntry: ClipEntry?) {
    if (clipEntry == null) {
      systemClipboard.setClipEntry(null)
      return
    }
    if (clipEntry.clipData.itemCount < 1) return
    val firstClipDataItem = clipEntry.clipData.getItemAt(0)
    val firstClipDataItemText = firstClipDataItem.text.toString()
    val clearedClipDataItem = firstClipDataItemText.replace(formatterSymbols.grouping.symbol, "")
    systemClipboard.setClipEntry(
      ClipEntry(ClipData.newPlainText(PLAIN_TEXT_LABEL, clearedClipDataItem))
    )
  }

  override val clipboardManager: ClipboardManager
    get() = systemClipboard.nativeClipboardManager
}

private const val PLAIN_TEXT_LABEL = "Expression"

internal actual fun createExpressionClipboard(
  formatterSymbols: FormatterSymbols,
  systemClipboard: Clipboard,
): ExpressionClipboardManager = AndroidExpressionClipboard(formatterSymbols, systemClipboard)
