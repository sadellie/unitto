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

package com.sadellie.unitto.feature.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.KeyboardButtonAdditional
import com.sadellie.unitto.core.ui.common.KeyboardButtonFilled
import com.sadellie.unitto.core.ui.common.KeyboardButtonLight
import com.sadellie.unitto.core.ui.common.KeyboardButtonTertiary
import com.sadellie.unitto.core.ui.common.key.UnittoIcons
import com.sadellie.unitto.core.ui.common.key.unittoicons.Backspace
import com.sadellie.unitto.core.ui.common.key.unittoicons.Clear
import com.sadellie.unitto.core.ui.common.key.unittoicons.Comma
import com.sadellie.unitto.core.ui.common.key.unittoicons.Divide
import com.sadellie.unitto.core.ui.common.key.unittoicons.Dot
import com.sadellie.unitto.core.ui.common.key.unittoicons.Down
import com.sadellie.unitto.core.ui.common.key.unittoicons.Enter
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
import com.sadellie.unitto.core.ui.common.key.unittoicons.Minus
import com.sadellie.unitto.core.ui.common.key.unittoicons.Multiply
import com.sadellie.unitto.core.ui.common.key.unittoicons.Percent
import com.sadellie.unitto.core.ui.common.key.unittoicons.Plus
import com.sadellie.unitto.core.ui.common.key.unittoicons.Pop
import com.sadellie.unitto.core.ui.common.key.unittoicons.Swap
import com.sadellie.unitto.core.ui.common.key.unittoicons.Unary
import com.sadellie.unitto.core.ui.common.key.unittoicons.Up
import io.github.sadellie.evaluatto.RPNCalculation

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun RPNCalculatorKeyboard(
    modifier: Modifier,
    fractional: String,
    middleZero: Boolean,
    allowVibration: Boolean,
    onCalculationClick: (RPNCalculation) -> Unit,
    onInputEditClick: (RPNInputEdit) -> Unit,
) {
    val fractionalIcon = remember(fractional) { if (fractional == Token.Digit.dot) UnittoIcons.Dot else UnittoIcons.Comma }

    val columns = 4
    val mainButtonRows = 5f
    val additionalButtonRows = 1f
    val additionalButtonRowFactor = 0.7f // How much smaller are the additional buttons than the main buttons
    val additionalButtonIconHeight = 0.65f
    val fillFactor = 0.92f

    val rows = remember { mainButtonRows + additionalButtonRows * additionalButtonRowFactor }
    val height = remember { fillFactor / rows }
    val width = remember { fillFactor / columns }

    FlowRow(
        maxItemsInEachRow = columns,
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        val aModifier = Modifier
            .fillMaxHeight(height * additionalButtonRowFactor)
            .fillMaxWidth(width)

        val bModifier = Modifier
            .fillMaxHeight(height)
            .fillMaxWidth(width)

        KeyboardButtonAdditional(modifier = aModifier, icon = UnittoIcons.Swap, allowVibration = allowVibration, contentHeight = additionalButtonIconHeight) { onCalculationClick(RPNCalculation.Swap) }
        KeyboardButtonAdditional(modifier = aModifier, icon = UnittoIcons.Up, allowVibration = allowVibration, contentHeight = additionalButtonIconHeight) { onCalculationClick(RPNCalculation.RotateUp) }
        KeyboardButtonAdditional(modifier = aModifier, icon = UnittoIcons.Down, allowVibration = allowVibration, contentHeight = additionalButtonIconHeight) { onCalculationClick(RPNCalculation.RotateDown) }
        KeyboardButtonAdditional(modifier = aModifier, icon = UnittoIcons.Pop, allowVibration = allowVibration, contentHeight = additionalButtonIconHeight) { onCalculationClick(RPNCalculation.Pop) }

        KeyboardButtonTertiary(modifier = bModifier, icon = UnittoIcons.Clear, allowVibration = allowVibration) { onCalculationClick(RPNCalculation.Clear) }
        KeyboardButtonFilled(modifier = bModifier, icon = UnittoIcons.Unary, allowVibration = allowVibration) { onCalculationClick(RPNCalculation.Negate) }
        KeyboardButtonFilled(modifier = bModifier, icon = UnittoIcons.Percent, allowVibration = allowVibration) { onCalculationClick(RPNCalculation.Percent) }
        KeyboardButtonFilled(modifier = bModifier, icon = UnittoIcons.Divide, allowVibration = allowVibration) { onCalculationClick(RPNCalculation.Divide) }

        KeyboardButtonLight(modifier = bModifier, icon = UnittoIcons.Key7, allowVibration = allowVibration) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._7)) }
        KeyboardButtonLight(modifier = bModifier, icon = UnittoIcons.Key8, allowVibration = allowVibration) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._8)) }
        KeyboardButtonLight(modifier = bModifier, icon = UnittoIcons.Key9, allowVibration = allowVibration) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._9)) }
        KeyboardButtonFilled(modifier = bModifier, icon = UnittoIcons.Multiply, allowVibration = allowVibration) { onCalculationClick(RPNCalculation.Multiply) }

        KeyboardButtonLight(modifier = bModifier, icon = UnittoIcons.Key4, allowVibration = allowVibration) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._4)) }
        KeyboardButtonLight(modifier = bModifier, icon = UnittoIcons.Key5, allowVibration = allowVibration) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._5)) }
        KeyboardButtonLight(modifier = bModifier, icon = UnittoIcons.Key6, allowVibration = allowVibration) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._6)) }
        KeyboardButtonFilled(modifier = bModifier, icon = UnittoIcons.Minus, allowVibration = allowVibration) { onCalculationClick(RPNCalculation.Minus) }

        KeyboardButtonLight(modifier = bModifier, icon = UnittoIcons.Key1, allowVibration = allowVibration) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._1)) }
        KeyboardButtonLight(modifier = bModifier, icon = UnittoIcons.Key2, allowVibration = allowVibration) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._2)) }
        KeyboardButtonLight(modifier = bModifier, icon = UnittoIcons.Key3, allowVibration = allowVibration) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._3)) }
        KeyboardButtonFilled(modifier = bModifier, icon = UnittoIcons.Plus, allowVibration = allowVibration) { onCalculationClick(RPNCalculation.Plus) }

        if (middleZero) {
            KeyboardButtonLight(modifier = bModifier, icon = UnittoIcons.Key0, allowVibration = allowVibration) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._0)) }
            KeyboardButtonLight(modifier = bModifier, icon = fractionalIcon, allowVibration = allowVibration) { onInputEditClick(RPNInputEdit.Dot) }
        } else {
            KeyboardButtonLight(modifier = bModifier, icon = fractionalIcon, allowVibration = allowVibration) { onInputEditClick(RPNInputEdit.Dot) }
            KeyboardButtonLight(modifier = bModifier, icon = UnittoIcons.Key0, allowVibration = allowVibration) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._0)) }
        }
        KeyboardButtonLight(modifier = bModifier, icon = UnittoIcons.Backspace, allowVibration = allowVibration) { onInputEditClick(RPNInputEdit.Delete) }
        KeyboardButtonFilled(modifier = bModifier, icon = UnittoIcons.Enter, allowVibration = allowVibration) { onCalculationClick(RPNCalculation.Enter) }
    }
}

@Preview
@Composable
private fun PreviewKeyboard() {
    RPNCalculatorKeyboard(
        modifier = Modifier.fillMaxSize(),
        fractional = Token.Digit.dot,
        middleZero = false,
        allowVibration = false,
        onCalculationClick = {},
        onInputEditClick = {}
    )
}
