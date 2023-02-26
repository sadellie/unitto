/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

package com.sadellie.unitto.feature.converter.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.Formatter
import com.sadellie.unitto.core.ui.common.KeyboardButtonFilled
import com.sadellie.unitto.core.ui.common.KeyboardButtonLight
import com.sadellie.unitto.core.ui.common.key.UnittoIcons
import com.sadellie.unitto.core.ui.common.key.unittoicons.Comma
import com.sadellie.unitto.core.ui.common.key.unittoicons.Delete
import com.sadellie.unitto.core.ui.common.key.unittoicons.Divide
import com.sadellie.unitto.core.ui.common.key.unittoicons.Dot
import com.sadellie.unitto.core.ui.common.key.unittoicons.Exponent
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
import com.sadellie.unitto.core.ui.common.key.unittoicons.KeyA
import com.sadellie.unitto.core.ui.common.key.unittoicons.KeyB
import com.sadellie.unitto.core.ui.common.key.unittoicons.KeyC
import com.sadellie.unitto.core.ui.common.key.unittoicons.KeyD
import com.sadellie.unitto.core.ui.common.key.unittoicons.KeyE
import com.sadellie.unitto.core.ui.common.key.unittoicons.KeyF
import com.sadellie.unitto.core.ui.common.key.unittoicons.LeftBracket
import com.sadellie.unitto.core.ui.common.key.unittoicons.Minus
import com.sadellie.unitto.core.ui.common.key.unittoicons.Multiply
import com.sadellie.unitto.core.ui.common.key.unittoicons.Plus
import com.sadellie.unitto.core.ui.common.key.unittoicons.RightBracket
import com.sadellie.unitto.core.ui.common.key.unittoicons.SquareRoot
import com.sadellie.unitto.feature.converter.ConverterMode

/**
 * Keyboard with button that looks like a calculator
 *
 * @param modifier Modifier that is applied to [Row]
 * @param addDigit Function that is called when clicking number and dot buttons
 * @param deleteDigit Function that is called when clicking delete "<" button
 * @param clearInput Function that is called when clicking clear "AC" button
 * @param converterMode
 */
@Composable
internal fun Keyboard(
    modifier: Modifier = Modifier,
    addDigit: (String) -> Unit = {},
    deleteDigit: () -> Unit = {},
    clearInput: () -> Unit = {},
    converterMode: ConverterMode,
    allowVibration: Boolean
) {
    Crossfade(converterMode, modifier = modifier) {
        when (it) {
            ConverterMode.DEFAULT -> DefaultKeyboard(addDigit, clearInput, deleteDigit, allowVibration)
            ConverterMode.BASE -> BaseKeyboard(addDigit, clearInput, deleteDigit, allowVibration)
        }
    }

}

@Composable
private fun DefaultKeyboard(
    addDigit: (String) -> Unit,
    clearInput: () -> Unit,
    deleteDigit: () -> Unit,
    allowVibration: Boolean
) {
    val fractionalIcon = remember { if (Formatter.fractional == Token.dot) UnittoIcons.Dot else UnittoIcons.Comma }
    Column {
        // Button modifier
        val bModifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(4.dp)
        // Column modifier
        val cModifier = Modifier.weight(1f)
        Row(cModifier) {
            KeyboardButtonFilled(bModifier, UnittoIcons.LeftBracket, allowVibration) { addDigit(Token.leftBracket) }
            KeyboardButtonFilled(bModifier, UnittoIcons.RightBracket, allowVibration) { addDigit(Token.rightBracket) }
            KeyboardButtonFilled(bModifier, UnittoIcons.Exponent, allowVibration) { addDigit(Token.exponent) }
            KeyboardButtonFilled(bModifier, UnittoIcons.SquareRoot, allowVibration) { addDigit(Token.sqrt) }
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key7, allowVibration) { addDigit(Token._7) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key8, allowVibration) { addDigit(Token._8) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key9, allowVibration) { addDigit(Token._9) }
            KeyboardButtonFilled(bModifier, UnittoIcons.Divide, allowVibration) { addDigit(Token.divide) }
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key4, allowVibration) { addDigit(Token._4) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key5, allowVibration) { addDigit(Token._5) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key6, allowVibration) { addDigit(Token._6) }
            KeyboardButtonFilled(bModifier, UnittoIcons.Multiply, allowVibration) { addDigit(Token.multiply) }
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key1, allowVibration) { addDigit(Token._1) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key2, allowVibration) { addDigit(Token._2) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key3, allowVibration) { addDigit(Token._3) }
            KeyboardButtonFilled(bModifier, UnittoIcons.Minus, allowVibration) { addDigit(Token.minus) }
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key0, allowVibration) { addDigit(Token._0) }
            KeyboardButtonLight(bModifier, fractionalIcon, allowVibration) { addDigit(Token.dot) }
            KeyboardButtonLight(bModifier, UnittoIcons.Delete, allowVibration, clearInput) { deleteDigit() }
            KeyboardButtonFilled(bModifier, UnittoIcons.Plus, allowVibration) { addDigit(Token.plus) }
        }
    }
}

@Composable
private fun BaseKeyboard(
    addDigit: (String) -> Unit,
    clearInput: () -> Unit,
    deleteDigit: () -> Unit,
    allowVibration: Boolean
) {
    Column {
        // Button modifier
        val bModifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(4.dp)
        // Column modifier
        val cModifier = Modifier.weight(1f)

        Row(cModifier) {
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyA, allowVibration) { addDigit(Token.baseA) }
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyB, allowVibration) { addDigit(Token.baseB) }
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyC, allowVibration) { addDigit(Token.baseC) }
        }
        Row(cModifier) {
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyD, allowVibration) { addDigit(Token.baseD) }
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyE, allowVibration) { addDigit(Token.baseE) }
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyF, allowVibration) { addDigit(Token.baseF) }
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key7, allowVibration) { addDigit(Token._7) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key8, allowVibration) { addDigit(Token._8) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key9, allowVibration) { addDigit(Token._9) }
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key4, allowVibration) { addDigit(Token._4) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key5, allowVibration) { addDigit(Token._5) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key6, allowVibration) { addDigit(Token._6) }
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key1, allowVibration) { addDigit(Token._1) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key2, allowVibration) { addDigit(Token._2) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key3, allowVibration) { addDigit(Token._3) }
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key0, allowVibration) { addDigit(Token._0) }
            KeyboardButtonLight(Modifier.fillMaxSize().weight(2f).padding(4.dp), UnittoIcons.Delete, allowVibration, clearInput) { deleteDigit() }
        }
    }
}
