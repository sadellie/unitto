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

package com.sadellie.unitto.feature.datecalculator.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.KeyboardButtonFilled
import com.sadellie.unitto.core.ui.common.KeyboardButtonLight
import com.sadellie.unitto.core.ui.common.key.UnittoIcons
import com.sadellie.unitto.core.ui.common.key.unittoicons.Backspace
import com.sadellie.unitto.core.ui.common.key.unittoicons.Check
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key0
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key1
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key2
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key3
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key4
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key5
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key6
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key7
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key8
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key9
import com.sadellie.unitto.core.ui.common.key.unittoicons.Tab
import com.sadellie.unitto.core.ui.isPortrait

@Composable
internal fun AddSubtractKeyboard(
    modifier: Modifier,
    addSymbol: (String) -> Unit,
    deleteSymbol: () -> Unit,
    onConfirm: () -> Unit,
    allowVibration: Boolean,
    imeAction: ImeAction,
    focusManager: FocusManager = LocalFocusManager.current
) {
    Row(modifier) {
        val weightModifier = Modifier.weight(1f)
        val mainButtonModifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(4.dp)
        val actionIconHeight = if (isPortrait()) 0.35f else 0.6f

        fun keyboardAction() {
            when (imeAction) {
                ImeAction.Next -> focusManager.moveFocus(FocusDirection.Next)
                else -> onConfirm()
            }
        }

        Column(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key7, allowVibration) {
                addSymbol(Token.Digit._7)
            }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key4, allowVibration) {
                addSymbol(Token.Digit._4
                )
            }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key1, allowVibration) {
                addSymbol(Token.Digit._1)
            }
            Spacer(mainButtonModifier)
        }

        Column(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key8, allowVibration) {
                addSymbol(Token.Digit._8)
            }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key5, allowVibration) {
                addSymbol(Token.Digit._5)
            }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key2, allowVibration) {
                addSymbol(Token.Digit._2)
            }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key0, allowVibration) {
                addSymbol(Token.Digit._0)
            }
        }

        Column(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key9, allowVibration) {
                addSymbol(Token.Digit._9)
            }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key6, allowVibration) {
                addSymbol(Token.Digit._6)
            }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key3, allowVibration) {
                addSymbol(Token.Digit._3)
            }
            Spacer(mainButtonModifier)
        }

        Column(weightModifier) {
            Crossfade(
                targetState = imeAction,
                modifier = mainButtonModifier,
                label = "Primary button animation"
            ) {
                when (it) {
                    ImeAction.Next -> KeyboardButtonFilled(
                        Modifier.fillMaxSize(),
                        UnittoIcons.Tab,
                        allowVibration,
                        actionIconHeight
                    ) { keyboardAction() }
                    else -> KeyboardButtonFilled(
                        Modifier.fillMaxSize(),
                        UnittoIcons.Check,
                        allowVibration,
                        actionIconHeight
                    ) { keyboardAction() }
                }
            }
            KeyboardButtonLight(
                mainButtonModifier,
                UnittoIcons.Backspace,
                allowVibration,
                actionIconHeight
            ) { deleteSymbol() }
        }
    }
}
