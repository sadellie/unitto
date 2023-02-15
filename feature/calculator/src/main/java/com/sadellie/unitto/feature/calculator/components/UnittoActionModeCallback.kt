/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.feature.calculator.components

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.compose.ui.geometry.Rect

private const val MENU_ITEM_COPY = 0
private const val MENU_ITEM_PASTE = 1
private const val MENU_ITEM_CUT = 2
private const val MENU_ITEM_SELECT_ALL = 3

internal class UnittoActionModeCallback(
    var rect: Rect = Rect.Zero,
    var onCopyRequested: (() -> Unit)? = null,
    var onPasteRequested: (() -> Unit)? = null,
    var onCutRequested: (() -> Unit)? = null,
    var onSelectAllRequested: (() -> Unit)? = null
) {
    fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        requireNotNull(menu)
        requireNotNull(mode)

        onCopyRequested?.let {
            menu.add(0, MENU_ITEM_COPY, 0, android.R.string.copy)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }

        onPasteRequested?.let {
            menu.add(0, MENU_ITEM_PASTE, 1, android.R.string.paste)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }

        onCutRequested?.let {
            menu.add(0, MENU_ITEM_CUT, 2, android.R.string.cut)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }

        onSelectAllRequested?.let {
            menu.add(0, MENU_ITEM_SELECT_ALL, 3, android.R.string.selectAll)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }
        return true
    }

    fun onPrepareActionMode(): Boolean {
        return false
    }

    fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when (item!!.itemId) {
            MENU_ITEM_COPY -> onCopyRequested?.invoke()
            MENU_ITEM_PASTE -> onPasteRequested?.invoke()
            MENU_ITEM_CUT -> onCutRequested?.invoke()
            MENU_ITEM_SELECT_ALL -> onSelectAllRequested?.invoke()
            else -> return false
        }
        mode?.finish()
        return true
    }

    fun onDestroyActionMode() {}
}
