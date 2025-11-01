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

import android.content.ClipData
import android.content.Context
import androidx.compose.ui.platform.toClipEntry
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ExpressionClipboardManagerTest {
  private val context = RuntimeEnvironment.getApplication().applicationContext
  private val formatterSymbols = FormatterSymbols(Token.PERIOD, Token.COMMA)
  private val systemClipboardManager =
    context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
  private val clipboardManager =
    ExpressionClipboardManager(formatterSymbols, systemClipboardManager)

  @Test
  fun setTextGetText_test() = runBlocking {
    // will be formatted to 123.456,789
    val textToCopy = "123456.789".formatExpression(formatterSymbols)
    clipboardManager.setClipEntry(ClipData.newPlainText("label", textToCopy).toClipEntry())

    // when copying grouping symbol should be deleted
    assertEquals("123456,789", clipboardManager.getClipEntry()?.clipData?.getItemAt(0)?.text)
  }
}
