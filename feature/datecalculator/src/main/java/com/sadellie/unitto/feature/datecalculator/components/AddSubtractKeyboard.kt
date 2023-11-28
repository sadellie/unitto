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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import com.sadellie.unitto.core.ui.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.LocalWindowSize
import com.sadellie.unitto.core.ui.common.KeyboardButtonContentHeightTall
import com.sadellie.unitto.core.ui.common.KeyboardButtonFilled
import com.sadellie.unitto.core.ui.common.KeyboardButtonLight
import com.sadellie.unitto.core.ui.common.KeypadFlow
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
    Row(
        modifier = modifier
    ) {
        KeypadFlow(
            modifier = Modifier
                .fillMaxHeight()
                .weight(3f),
            rows = 4,
            columns = 3
        ) { width, height ->
            val buttonModifier = Modifier
                .fillMaxWidth(width)
                .fillMaxHeight(height)

            KeyboardButtonLight(buttonModifier, UnittoIcons.Key7, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._7) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key8, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._8) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key9, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._9) }

            KeyboardButtonLight(buttonModifier, UnittoIcons.Key4, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._4) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key5, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._5) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key6, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._6) }

            KeyboardButtonLight(buttonModifier, UnittoIcons.Key1, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._1) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key2, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._2) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key3, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._3) }

            Spacer(buttonModifier)
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key0, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._0) }
            Spacer(buttonModifier)
        }

        KeypadFlow(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            rows = 2,
            columns = 1,
            // In digits keypad there are 4 rows with verticalPadding set to 10
            // In this keypad we have 2 times less rows, we use 2 times smaller verticalPadding -> 5
            verticalPadding = 5
        ) { width, height ->
            val mainButtonModifier = Modifier
                .fillMaxWidth(width)
                .fillMaxHeight(height)
            val actionIconHeight = if (LocalWindowSize.current.heightSizeClass > WindowHeightSizeClass.Compact) 0.396f else 0.68f

            Crossfade(
                targetState = imeAction == ImeAction.Next,
                modifier = mainButtonModifier,
                label = "Primary button animation"
            ) { showNext ->
                if (showNext) {
                    KeyboardButtonFilled(Modifier.fillMaxSize(), UnittoIcons.Tab, allowVibration, actionIconHeight) { focusManager.moveFocus(FocusDirection.Next) }
                } else {
                    KeyboardButtonFilled(Modifier.fillMaxSize(), UnittoIcons.Check, allowVibration, actionIconHeight) { onConfirm() }
                }
            }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Backspace, allowVibration, actionIconHeight) { deleteSymbol() }
        }

    }
}

@Preview
@Composable
fun PreviewAddSubtractKeyboardNew() {
    AddSubtractKeyboard(
        modifier = Modifier
            .fillMaxSize(),
        addSymbol = {},
        deleteSymbol = {},
        onConfirm = {},
        allowVibration = true,
        imeAction = ImeAction.Next
    )
}
