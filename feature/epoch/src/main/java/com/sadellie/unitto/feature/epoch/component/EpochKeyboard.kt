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

package com.sadellie.unitto.feature.epoch.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.base.KEY_0
import com.sadellie.unitto.core.base.KEY_1
import com.sadellie.unitto.core.base.KEY_2
import com.sadellie.unitto.core.base.KEY_3
import com.sadellie.unitto.core.base.KEY_4
import com.sadellie.unitto.core.base.KEY_5
import com.sadellie.unitto.core.base.KEY_6
import com.sadellie.unitto.core.base.KEY_7
import com.sadellie.unitto.core.base.KEY_8
import com.sadellie.unitto.core.base.KEY_9
import com.sadellie.unitto.core.base.KEY_CLEAR
import com.sadellie.unitto.core.ui.common.KeyboardButton

@Composable
internal fun EpochKeyboard(
    modifier: Modifier,
    addSymbol: (String) -> Unit,
    clearSymbols: () -> Unit,
    deleteSymbol: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        // Button modifier
        val bModifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(4.dp)
        // Column modifier
        val cModifier = Modifier.weight(1f)
        val dModifier = Modifier
            .fillMaxSize()
            .weight(2f)
            .padding(4.dp)
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_7, allowVibration = false, onClick = addSymbol)
            KeyboardButton(bModifier, KEY_8, allowVibration = false, onClick = addSymbol)
            KeyboardButton(bModifier, KEY_9, allowVibration = false, onClick = addSymbol)
        }
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_4, allowVibration = false, onClick = addSymbol)
            KeyboardButton(bModifier, KEY_5, allowVibration = false, onClick = addSymbol)
            KeyboardButton(bModifier, KEY_6, allowVibration = false, onClick = addSymbol)
        }
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_1, allowVibration = false, onClick = addSymbol)
            KeyboardButton(bModifier, KEY_2, allowVibration = false, onClick = addSymbol)
            KeyboardButton(bModifier, KEY_3, allowVibration = false, onClick = addSymbol)
        }
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_0, allowVibration = false, onClick = addSymbol)
            KeyboardButton(
                dModifier,
                KEY_CLEAR,
                allowVibration = false,
                onLongClick = clearSymbols
            ) { deleteSymbol() }
        }
    }
}