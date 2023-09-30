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

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.ColumnWithConstraints
import com.sadellie.unitto.core.ui.common.KeyboardButtonAdditional
import com.sadellie.unitto.core.ui.common.KeyboardButtonFilled
import com.sadellie.unitto.core.ui.common.KeyboardButtonLight
import com.sadellie.unitto.core.ui.common.RowWithConstraints
import com.sadellie.unitto.core.ui.common.key.UnittoIcons
import com.sadellie.unitto.core.ui.common.key.unittoicons.AcTan
import com.sadellie.unitto.core.ui.common.key.unittoicons.ArCos
import com.sadellie.unitto.core.ui.common.key.unittoicons.ArSin
import com.sadellie.unitto.core.ui.common.key.unittoicons.Backspace
import com.sadellie.unitto.core.ui.common.key.unittoicons.Comma
import com.sadellie.unitto.core.ui.common.key.unittoicons.Cos
import com.sadellie.unitto.core.ui.common.key.unittoicons.Deg
import com.sadellie.unitto.core.ui.common.key.unittoicons.Divide
import com.sadellie.unitto.core.ui.common.key.unittoicons.Dot
import com.sadellie.unitto.core.ui.common.key.unittoicons.E
import com.sadellie.unitto.core.ui.common.key.unittoicons.Equal
import com.sadellie.unitto.core.ui.common.key.unittoicons.Exp
import com.sadellie.unitto.core.ui.common.key.unittoicons.ExponentWide
import com.sadellie.unitto.core.ui.common.key.unittoicons.Factorial
import com.sadellie.unitto.core.ui.common.key.unittoicons.Inv
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
import com.sadellie.unitto.core.ui.common.key.unittoicons.LeftBracket
import com.sadellie.unitto.core.ui.common.key.unittoicons.Ln
import com.sadellie.unitto.core.ui.common.key.unittoicons.Log
import com.sadellie.unitto.core.ui.common.key.unittoicons.Minus
import com.sadellie.unitto.core.ui.common.key.unittoicons.Modulo
import com.sadellie.unitto.core.ui.common.key.unittoicons.Multiply
import com.sadellie.unitto.core.ui.common.key.unittoicons.Percent
import com.sadellie.unitto.core.ui.common.key.unittoicons.Pi
import com.sadellie.unitto.core.ui.common.key.unittoicons.Plus
import com.sadellie.unitto.core.ui.common.key.unittoicons.Power10
import com.sadellie.unitto.core.ui.common.key.unittoicons.Rad
import com.sadellie.unitto.core.ui.common.key.unittoicons.RightBracket
import com.sadellie.unitto.core.ui.common.key.unittoicons.Sin
import com.sadellie.unitto.core.ui.common.key.unittoicons.SquareRootWide
import com.sadellie.unitto.core.ui.common.key.unittoicons.Tan

@Composable
internal fun CalculatorKeyboard(
    modifier: Modifier,
    radianMode: Boolean,
    fractional: String,
    allowVibration: Boolean,
    middleZero: Boolean,
    addSymbol: (String) -> Unit,
    clearSymbols: () -> Unit,
    deleteSymbol: () -> Unit,
    toggleAngleMode: () -> Unit,
    evaluate: () -> Unit
) {
    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitKeyboard(
            modifier = modifier,
            radianMode = radianMode,
            fractional = fractional,
            allowVibration = allowVibration,
            middleZero = middleZero,
            addSymbol = addSymbol,
            toggleAngleMode = toggleAngleMode,
            deleteSymbol = deleteSymbol,
            clearSymbols = clearSymbols,
            evaluate = evaluate
        )
    } else {
        LandscapeKeyboard(
            modifier = modifier,
            radianMode = radianMode,
            fractional = fractional,
            allowVibration = allowVibration,
            middleZero = middleZero,
            addSymbol = addSymbol,
            toggleAngleMode = toggleAngleMode,
            deleteSymbol = deleteSymbol,
            clearSymbols = clearSymbols,
            evaluate = evaluate
        )
    }
}

@Composable
internal fun CalculatorKeyboardLoading(
    modifier: Modifier
) {
    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitKeyboardLoading(modifier)
    } else {
        LandscapeKeyboardLoading(modifier)
    }
}

@Composable
private fun PortraitKeyboardLoading(
    modifier: Modifier
) {
    ColumnWithConstraints(
        modifier = modifier
    ) { constraints ->

        val additionalButtonHeight by remember {
            mutableStateOf(constraints.maxHeight * 0.09f)
        }

        val spacerHeight by remember {
            mutableStateOf(constraints.maxHeight * 0.025f)
        }

        val additionalRowSpacedBy by remember {
            mutableStateOf(constraints.maxWidth * 0.03f)
        }

        val weightModifier = Modifier.weight(1f)
        val additionalButtonModifier = Modifier
            .weight(1f)
            .height(additionalButtonHeight)

        Spacer(modifier = Modifier.height(spacerHeight))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(additionalRowSpacedBy)
        ) {
            // Additional buttons
            Box(weightModifier) {
                AdditionalButtonsPortrait(
                    modifier = additionalButtonModifier,
                    allowVibration = false,
                    addSymbol = {},
                    showAdditional = false,
                    radianMode = false,
                    toggleAngleMode = {},
                    toggleInvMode = {}
                )
            }

            Box(
                modifier = Modifier.size(additionalButtonHeight),
                contentAlignment = Alignment.Center
            ) {
                // Expand/Collapse
                IconButton(
                    onClick = {  },
                    colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
                ) {
                    Icon(Icons.Default.ExpandMore, null)
                }
            }
        }

        Spacer(modifier = Modifier.height(spacerHeight))

        Box(
            modifier = weightModifier
                .clip(RoundedCornerShape(32.dp))
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .fillMaxSize()
        )

        Spacer(modifier = Modifier.height(spacerHeight))
    }
}

@Composable
private fun LandscapeKeyboardLoading(
    modifier: Modifier
) {
    RowWithConstraints(modifier) { constraints ->
        val buttonModifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(constraints.maxWidth * 0.005f, constraints.maxHeight * 0.02f)

        AdditionalButtonsLandscape(
            modifier = Modifier.weight(1f),
            buttonModifier = buttonModifier,
            allowVibration = false,
            radianMode = false,
            addSymbol = {},
            toggleAngleMode = {},
            toggleInvMode = {}
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .weight(5f)
                .fillMaxSize()
        )
    }
}

@Composable
private fun PortraitKeyboard(
    modifier: Modifier,
    radianMode: Boolean,
    fractional: String,
    allowVibration: Boolean,
    middleZero: Boolean,
    addSymbol: (String) -> Unit,
    toggleAngleMode: () -> Unit,
    deleteSymbol: () -> Unit,
    clearSymbols: () -> Unit,
    evaluate: () -> Unit
) {
    val fractionalIcon = remember { if (fractional == Token.Digit.dot) UnittoIcons.Dot else UnittoIcons.Comma }
    var showAdditional: Boolean by remember { mutableStateOf(false) }
    var invMode: Boolean by remember { mutableStateOf(false) }
    val expandRotation: Float by animateFloatAsState(
        targetValue = if (showAdditional) 180f else 0f,
        animationSpec = tween(easing = FastOutSlowInEasing),
        label = "Rotate on expand"
    )

    ColumnWithConstraints(
        modifier = modifier
    ) { constraints ->
        val mainButtonHorizontalPadding by remember {
            derivedStateOf { (constraints.maxWidth * 0.01f) }
        }

        val additionalButtonHeight by remember {
            mutableStateOf(constraints.maxHeight * 0.09f)
        }

        val spacerHeight by remember {
            mutableStateOf(constraints.maxHeight * 0.025f)
        }

        val additionalRowSpacedBy by remember {
            mutableStateOf(constraints.maxWidth * 0.03f)
        }

        val weightModifier = Modifier.weight(1f)
        val mainButtonModifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(mainButtonHorizontalPadding)
        val additionalButtonModifier = Modifier
            .weight(1f)
            .height(additionalButtonHeight)

        Spacer(modifier = Modifier.height(spacerHeight))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(additionalRowSpacedBy)
        ) {
            // Additional buttons
            Crossfade(invMode, weightModifier, label = "Additional button") {
                if (it) {
                    AdditionalButtonsPortraitInverse(
                        modifier = additionalButtonModifier,
                        allowVibration = allowVibration,
                        addSymbol = addSymbol,
                        showAdditional = showAdditional,
                        radianMode = radianMode,
                        toggleAngleMode = toggleAngleMode,
                        toggleInvMode = { invMode = !invMode }
                    )
                } else {
                    AdditionalButtonsPortrait(
                        modifier = additionalButtonModifier,
                        allowVibration = allowVibration,
                        addSymbol = addSymbol,
                        showAdditional = showAdditional,
                        radianMode = radianMode,
                        toggleAngleMode = toggleAngleMode,
                        toggleInvMode = { invMode = !invMode }
                    )
                }
            }

            Box(
                modifier = Modifier.size(additionalButtonHeight),
                contentAlignment = Alignment.Center
            ) {
                // Expand/Collapse
                IconButton(
                    onClick = { showAdditional = !showAdditional },
                    colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
                ) {
                    Icon(Icons.Default.ExpandMore, null, Modifier.rotate(expandRotation))
                }
            }
        }

        Spacer(modifier = Modifier.height(spacerHeight))

        Row(weightModifier) {
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.LeftBracket, allowVibration) { addSymbol(Token.Operator.leftBracket) }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.RightBracket, allowVibration) { addSymbol(Token.Operator.rightBracket) }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Percent, allowVibration) { addSymbol(Token.Operator.percent) }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Divide, allowVibration) { addSymbol(Token.Operator.divide) }
        }
        Row(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key7, allowVibration) { addSymbol(Token.Digit._7) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key8, allowVibration) { addSymbol(Token.Digit._8) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key9, allowVibration) { addSymbol(Token.Digit._9) }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Multiply, allowVibration) { addSymbol(Token.Operator.multiply) }
        }
        Row(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key4, allowVibration) { addSymbol(Token.Digit._4) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key5, allowVibration) { addSymbol(Token.Digit._5) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key6, allowVibration) { addSymbol(Token.Digit._6) }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Minus, allowVibration) { addSymbol(Token.Operator.minus) }
        }
        Row(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key1, allowVibration) { addSymbol(Token.Digit._1) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key2, allowVibration) { addSymbol(Token.Digit._2) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key3, allowVibration) { addSymbol(Token.Digit._3) }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Plus, allowVibration) { addSymbol(Token.Operator.plus) }
        }
        Row(weightModifier) {
            if (middleZero) {
                KeyboardButtonLight(mainButtonModifier, fractionalIcon, allowVibration) { addSymbol(Token.Digit.dot) }
                KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key0, allowVibration) { addSymbol(Token.Digit._0) }
            } else {
                KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key0, allowVibration) { addSymbol(Token.Digit._0) }
                KeyboardButtonLight(mainButtonModifier, fractionalIcon, allowVibration) { addSymbol(Token.Digit.dot) }
            }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Backspace, allowVibration, onLongClick = clearSymbols) { deleteSymbol() }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Equal, allowVibration) { evaluate() }
        }

        Spacer(modifier = Modifier.height(spacerHeight))
    }
}

@Composable
private fun AdditionalButtonsPortrait(
    modifier: Modifier,
    allowVibration: Boolean,
    addSymbol: (String) -> Unit,
    showAdditional: Boolean,
    radianMode: Boolean,
    toggleAngleMode: () -> Unit,
    toggleInvMode: () -> Unit
) {
    Column {
        Row {
            KeyboardButtonAdditional(modifier, UnittoIcons.SquareRootWide, allowVibration) { addSymbol(Token.Operator.sqrt) }
            KeyboardButtonAdditional(modifier, UnittoIcons.Pi, allowVibration) { addSymbol(Token.Const.pi) }
            KeyboardButtonAdditional(modifier, UnittoIcons.ExponentWide, allowVibration) { addSymbol(Token.Operator.power) }
            KeyboardButtonAdditional(modifier, UnittoIcons.Factorial, allowVibration) { addSymbol(Token.Operator.factorial) }
        }
        AnimatedVisibility(showAdditional) {
            Column {
                Row {
                    KeyboardButtonAdditional(modifier, if (radianMode) UnittoIcons.Rad else UnittoIcons.Deg, allowVibration) { toggleAngleMode() }
                    KeyboardButtonAdditional(modifier, UnittoIcons.Sin, allowVibration) { addSymbol(Token.Func.sinBracket) }
                    KeyboardButtonAdditional(modifier, UnittoIcons.Cos, allowVibration) { addSymbol(Token.Func.cosBracket) }
                    KeyboardButtonAdditional(modifier, UnittoIcons.Tan, allowVibration) { addSymbol(Token.Func.tanBracket) }
                }
                Row {
                    KeyboardButtonAdditional(modifier, UnittoIcons.Inv, allowVibration) { toggleInvMode() }
                    KeyboardButtonAdditional(modifier, UnittoIcons.E, allowVibration) { addSymbol(Token.Const.e) }
                    KeyboardButtonAdditional(modifier, UnittoIcons.Ln, allowVibration) { addSymbol(Token.Func.lnBracket) }
                    KeyboardButtonAdditional(modifier, UnittoIcons.Log, allowVibration) { addSymbol(Token.Func.logBracket) }
                }
            }
        }
    }
}

@Composable
private fun AdditionalButtonsPortraitInverse(
    modifier: Modifier,
    allowVibration: Boolean,
    addSymbol: (String) -> Unit,
    showAdditional: Boolean,
    radianMode: Boolean,
    toggleAngleMode: () -> Unit,
    toggleInvMode: () -> Unit
) {
    Column {
        Row {
            KeyboardButtonAdditional(modifier, UnittoIcons.Modulo, allowVibration) { addSymbol(Token.Operator.modulo) }
            KeyboardButtonAdditional(modifier, UnittoIcons.Pi, allowVibration) { addSymbol(Token.Const.pi) }
            KeyboardButtonAdditional(modifier, UnittoIcons.ExponentWide, allowVibration) { addSymbol(Token.Operator.power) }
            KeyboardButtonAdditional(modifier, UnittoIcons.Factorial, allowVibration) { addSymbol(Token.Operator.factorial) }
        }
        AnimatedVisibility(showAdditional) {
            Column {
                Row {
                    KeyboardButtonAdditional(modifier, if (radianMode) UnittoIcons.Rad else UnittoIcons.Deg, allowVibration) { toggleAngleMode() }
                    KeyboardButtonAdditional(modifier, UnittoIcons.ArSin, allowVibration) { addSymbol(Token.Func.arsinBracket) }
                    KeyboardButtonAdditional(modifier, UnittoIcons.ArCos, allowVibration) { addSymbol(Token.Func.arcosBracket) }
                    KeyboardButtonAdditional(modifier, UnittoIcons.AcTan, allowVibration) { addSymbol(Token.Func.actanBracket) }
                }
                Row {
                    KeyboardButtonAdditional(modifier, UnittoIcons.Inv, allowVibration) { toggleInvMode() }
                    KeyboardButtonAdditional(modifier, UnittoIcons.E, allowVibration) { addSymbol(Token.Const.e) }
                    KeyboardButtonAdditional(modifier, UnittoIcons.Exp, allowVibration) { addSymbol(Token.Func.expBracket) }
                    KeyboardButtonAdditional(modifier, UnittoIcons.Power10, allowVibration) { addSymbol(Token.Digit._1 + Token.Digit._0 + Token.Operator.power) }
                }
            }
        }
    }
}

@Composable
private fun LandscapeKeyboard(
    modifier: Modifier,
    radianMode: Boolean,
    fractional: String,
    allowVibration: Boolean,
    middleZero: Boolean,
    addSymbol: (String) -> Unit,
    toggleAngleMode: () -> Unit,
    deleteSymbol: () -> Unit,
    clearSymbols: () -> Unit,
    evaluate: () -> Unit
) {
    val fractionalIcon = remember { if (fractional == Token.Digit.dot) UnittoIcons.Dot else UnittoIcons.Comma }
    var invMode: Boolean by remember { mutableStateOf(false) }

    RowWithConstraints(modifier) { constraints ->
        val buttonModifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(constraints.maxWidth * 0.005f, constraints.maxHeight * 0.02f)

        Crossfade(invMode, Modifier.weight(3f), label = "Additional button") {
            Row {
                if (it) {
                    AdditionalButtonsLandscapeInverse(
                        modifier = Modifier.weight(1f),
                        buttonModifier = buttonModifier,
                        allowVibration = allowVibration,
                        radianMode = radianMode,
                        addSymbol = addSymbol,
                        toggleAngleMode = toggleAngleMode,
                        toggleInvMode = { invMode = !invMode }
                    )
                } else {
                    AdditionalButtonsLandscape(
                        modifier = Modifier.weight(1f),
                        buttonModifier = buttonModifier,
                        allowVibration = allowVibration,
                        radianMode = radianMode,
                        addSymbol = addSymbol,
                        toggleAngleMode = toggleAngleMode,
                        toggleInvMode = { invMode = !invMode }
                    )
                }
            }
        }

        Column(Modifier.weight(1f)) {
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key7, allowVibration) { addSymbol(Token.Digit._7) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key4, allowVibration) { addSymbol(Token.Digit._4) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key1, allowVibration) { addSymbol(Token.Digit._1) }
            if (middleZero) {
                KeyboardButtonLight(buttonModifier, fractionalIcon, allowVibration) { addSymbol(Token.Digit.dot) }
            } else {
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key0, allowVibration) { addSymbol(Token.Digit._0) }
            }
        }
        Column(Modifier.weight(1f)) {
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key8, allowVibration) { addSymbol(Token.Digit._8) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key5, allowVibration) { addSymbol(Token.Digit._5) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key2, allowVibration) { addSymbol(Token.Digit._2) }
            if (middleZero) {
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key0, allowVibration) { addSymbol(Token.Digit._0) }
            } else {
                KeyboardButtonLight(buttonModifier, fractionalIcon, allowVibration) { addSymbol(Token.Digit.dot) }
            }
        }
        Column(Modifier.weight(1f)) {
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key9, allowVibration) { addSymbol(Token.Digit._9) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key6, allowVibration) { addSymbol(Token.Digit._6) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key3, allowVibration) { addSymbol(Token.Digit._3) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Backspace, allowVibration, onLongClick = clearSymbols) { deleteSymbol() }
        }

        Column(Modifier.weight(1f)) {
            KeyboardButtonFilled(buttonModifier, UnittoIcons.LeftBracket, allowVibration) { addSymbol(Token.Operator.leftBracket) }
            KeyboardButtonFilled(buttonModifier, UnittoIcons.Multiply, allowVibration) { addSymbol(Token.Operator.multiply) }
            KeyboardButtonFilled(buttonModifier, UnittoIcons.Minus, allowVibration) { addSymbol(Token.Operator.minus) }
            KeyboardButtonFilled(buttonModifier, UnittoIcons.Plus, allowVibration) { addSymbol(Token.Operator.plus) }
        }
        Column(Modifier.weight(1f)) {
            KeyboardButtonFilled(buttonModifier, UnittoIcons.RightBracket, allowVibration) { addSymbol(Token.Operator.rightBracket) }
            KeyboardButtonFilled(buttonModifier, UnittoIcons.Divide, allowVibration) { addSymbol(Token.Operator.divide) }
            KeyboardButtonFilled(buttonModifier, UnittoIcons.Percent, allowVibration) { addSymbol(Token.Operator.percent) }
            KeyboardButtonFilled(buttonModifier, UnittoIcons.Equal, allowVibration) { evaluate() }
        }
    }
}

@Composable
private fun AdditionalButtonsLandscape(
    modifier: Modifier,
    buttonModifier: Modifier,
    allowVibration: Boolean,
    radianMode: Boolean,
    addSymbol: (String) -> Unit,
    toggleAngleMode: () -> Unit,
    toggleInvMode: () -> Unit
) {
    Column(modifier) {
        KeyboardButtonAdditional(buttonModifier, if (radianMode) UnittoIcons.Rad else UnittoIcons.Deg, allowVibration) { toggleAngleMode() }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.Inv, allowVibration) { toggleInvMode() }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.Sin, allowVibration) { addSymbol(Token.Func.sinBracket) }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.E, allowVibration) { addSymbol(Token.Const.e) }
    }

    Column(modifier) {
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.SquareRootWide, allowVibration) { addSymbol(Token.Operator.sqrt) }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.ExponentWide, allowVibration) { addSymbol(Token.Operator.power) }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.Cos, allowVibration) { addSymbol(Token.Func.cosBracket) }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.Ln, allowVibration) { addSymbol(Token.Func.lnBracket) }
    }

    Column(modifier) {
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.Pi, allowVibration) { addSymbol(Token.Const.pi) }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.Factorial, allowVibration) { addSymbol(Token.Operator.factorial) }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.Tan, allowVibration) { addSymbol(Token.Func.tanBracket) }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.Log, allowVibration) { addSymbol(Token.Func.logBracket) }
    }
}

@Composable
private fun AdditionalButtonsLandscapeInverse(
    modifier: Modifier,
    buttonModifier: Modifier,
    allowVibration: Boolean,
    radianMode: Boolean,
    addSymbol: (String) -> Unit,
    toggleAngleMode: () -> Unit,
    toggleInvMode: () -> Unit
) {
    Column(modifier) {
        KeyboardButtonAdditional(buttonModifier, if (radianMode) UnittoIcons.Rad else UnittoIcons.Deg, allowVibration) { toggleAngleMode() }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.Inv, allowVibration) { toggleInvMode() }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.ArSin, allowVibration) { addSymbol(Token.Func.arsinBracket) }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.E, allowVibration) { addSymbol(Token.Const.e) }
    }

    Column(modifier) {
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.Modulo, allowVibration) { addSymbol(Token.Operator.modulo) }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.ExponentWide, allowVibration) { addSymbol(Token.Operator.power) }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.ArCos, allowVibration) { addSymbol(Token.Func.arcosBracket) }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.Exp, allowVibration) { addSymbol(Token.Func.expBracket) }
    }

    Column(modifier) {
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.Pi, allowVibration) { addSymbol(Token.Const.pi) }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.Factorial, allowVibration) { addSymbol(Token.Operator.factorial) }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.AcTan, allowVibration) { addSymbol(Token.Func.actanBracket) }
        KeyboardButtonAdditional(buttonModifier, UnittoIcons.Power10, allowVibration) { addSymbol(Token.Digit._1 + Token.Digit._0 + Token.Operator.power) }
    }
}

@Preview
@Composable
private fun PreviewCalculatorKeyboard() {
    CalculatorKeyboard(
        modifier = Modifier,
        radianMode = true,
        fractional = ".",
        addSymbol = {},
        clearSymbols = {},
        deleteSymbol = {},
        toggleAngleMode = {},
        evaluate = {},
        allowVibration = false,
        middleZero = false,
    )
}
