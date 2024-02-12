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

import android.content.ClipData
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import com.sadellie.unitto.core.base.FormatterSymbols
import com.sadellie.unitto.core.base.Token

/**
 * Unformatted values are expected. Basically what BigDecimal and Expression parser use.
 *
 * @property formatterSymbols Current [FormatterSymbols].
 * @property clipboardManager [android.content.ClipboardManager] provided by system.
 */
internal class ExpressionClipboardManager(
    private val formatterSymbols: FormatterSymbols,
    private val clipboardManager: android.content.ClipboardManager
): ClipboardManager {
    override fun setText(annotatedString: AnnotatedString) = clipboardManager.setPrimaryClip(
        ClipData.newPlainText(
            PLAIN_TEXT_LABEL,
            annotatedString
                .text
                .replace(Token.Digit.dot, formatterSymbols.fractional)
        )
    )

    override fun getText(): AnnotatedString? = clipboardManager.primaryClip?.let { primaryClip ->
        if (primaryClip.itemCount > 0) {
            val clipText = primaryClip.getItemAt(0)?.text ?:return@let null

            clipText
                .toString()
                .toAnnotatedString()
        } else {
            null
        }
    }

    override fun hasText() =
        clipboardManager.primaryClipDescription?.hasMimeType("text/*") ?: false
}

internal const val PLAIN_TEXT_LABEL = "plain text"

private fun CharSequence.toAnnotatedString(): AnnotatedString = AnnotatedString(this.toString())
