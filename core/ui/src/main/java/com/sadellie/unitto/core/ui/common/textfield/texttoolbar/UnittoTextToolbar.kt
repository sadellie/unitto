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

package com.sadellie.unitto.core.ui.common.textfield.texttoolbar

import android.os.Build
import android.view.ActionMode
import android.view.View
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus

class UnittoTextToolbar(
    private val view: View,
    private val copyCallback: () -> Unit,
    private val pasteCallback: (() -> Unit)? = null,
    private val cutCallback: (() -> Unit)? = null
) : TextToolbar {

    private var actionMode: ActionMode? = null
    private val textActionModeCallback: UnittoActionModeCallback = UnittoActionModeCallback()
    override var status: TextToolbarStatus = TextToolbarStatus.Hidden
        private set

    override fun showMenu(
        rect: Rect,
        onCopyRequested: (() -> Unit)?,
        onPasteRequested: (() -> Unit)?,
        onCutRequested: (() -> Unit)?,
        onSelectAllRequested: (() -> Unit)?
    ) {
        textActionModeCallback.rect = rect
        textActionModeCallback.onCopyRequested = copyCallback
        textActionModeCallback.onCutRequested = cutCallback
        textActionModeCallback.onPasteRequested = pasteCallback
        textActionModeCallback.onSelectAllRequested = onSelectAllRequested
        if (actionMode == null) {
            status = TextToolbarStatus.Shown
            actionMode = startActionMode(view, textActionModeCallback)
        } else {
            actionMode?.invalidate()
        }
    }

    override fun hide() {
        status = TextToolbarStatus.Hidden
        actionMode?.finish()
        actionMode = null
    }
}

private fun startActionMode(
    view: View,
    textActionModeCallback: UnittoActionModeCallback
): ActionMode {
    return if (Build.VERSION.SDK_INT >= 23) {
        view.startActionMode(
            FloatingTextActionModeCallback(textActionModeCallback),
            ActionMode.TYPE_FLOATING
        )
    } else {
        // Old devices use toolbar instead of a floating menu
        view.startActionMode(
            UnittoPrimaryTextActionModeCallback(textActionModeCallback)
        )
    }
}
