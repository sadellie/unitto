/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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

import android.content.ClipData
import android.os.Build
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.NativeClipboard
import androidx.compose.ui.platform.toClipEntry
import com.sadellie.unitto.core.common.FormatterSymbols

internal actual class ExpressionClipboardManager
actual constructor(private val formatterSymbols: FormatterSymbols, clipboard: NativeClipboard) :
  Clipboard {
  actual override val nativeClipboard: NativeClipboard = clipboard

  actual override suspend fun getClipEntry(): ClipEntry? =
    nativeClipboard.primaryClip?.toClipEntry()

  actual override suspend fun setClipEntry(clipEntry: ClipEntry?) {
    if (clipEntry == null) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        nativeClipboard.clearPrimaryClip()
      } else {
        nativeClipboard.setPrimaryClip(ClipData.newPlainText("", ""))
      }
      return
    }
    if (clipEntry.clipData.itemCount < 1) return

    // Always only 1 item
    val firstClipDataItem = clipEntry.clipData.getItemAt(0)
    val firstClipDataItemText = firstClipDataItem.text.toString()
    val clearedClipDataItem = firstClipDataItemText.replace(formatterSymbols.grouping, "")

    nativeClipboard.setPrimaryClip(ClipData.newPlainText(PLAIN_TEXT_LABEL, clearedClipDataItem))
  }
}

private const val PLAIN_TEXT_LABEL = "Expression"
