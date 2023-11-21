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
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.ColumnWithConstraints
import com.sadellie.unitto.core.ui.common.KeyboardButtonAdditional
import com.sadellie.unitto.core.ui.common.KeyboardButtonContentHeightShort
import com.sadellie.unitto.core.ui.common.KeyboardButtonContentHeightTall
import com.sadellie.unitto.core.ui.common.KeyboardButtonContentHeightWide
import com.sadellie.unitto.core.ui.common.KeyboardButtonFilled
import com.sadellie.unitto.core.ui.common.KeyboardButtonLight
import com.sadellie.unitto.core.ui.common.KeyboardButtonTertiary
import com.sadellie.unitto.core.ui.common.KeypadFlow
import com.sadellie.unitto.core.ui.common.key.UnittoIcons
import com.sadellie.unitto.core.ui.common.key.unittoicons.AcTan
import com.sadellie.unitto.core.ui.common.key.unittoicons.ArCos
import com.sadellie.unitto.core.ui.common.key.unittoicons.ArSin
import com.sadellie.unitto.core.ui.common.key.unittoicons.Backspace
import com.sadellie.unitto.core.ui.common.key.unittoicons.Brackets
import com.sadellie.unitto.core.ui.common.key.unittoicons.Clear
import com.sadellie.unitto.core.ui.common.key.unittoicons.Comma
import com.sadellie.unitto.core.ui.common.key.unittoicons.Cos
import com.sadellie.unitto.core.ui.common.key.unittoicons.Deg
import com.sadellie.unitto.core.ui.common.key.unittoicons.Divide
import com.sadellie.unitto.core.ui.common.key.unittoicons.Dot
import com.sadellie.unitto.core.ui.common.key.unittoicons.E
import com.sadellie.unitto.core.ui.common.key.unittoicons.Equal
import com.sadellie.unitto.core.ui.common.key.unittoicons.Ex
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
import com.sadellie.unitto.core.ui.common.key.unittoicons.PowerWide
import com.sadellie.unitto.core.ui.common.key.unittoicons.Rad
import com.sadellie.unitto.core.ui.common.key.unittoicons.RightBracket
import com.sadellie.unitto.core.ui.common.key.unittoicons.RootWide
import com.sadellie.unitto.core.ui.common.key.unittoicons.Sin
import com.sadellie.unitto.core.ui.common.key.unittoicons.Tan

@Composable
internal fun CalculatorKeyboard(
    modifier: Modifier,
    radianMode: Boolean,
    fractional: String,
    allowVibration: Boolean,
    middleZero: Boolean,
    acButton: Boolean,
    addSymbol: (String) -> Unit,
    addBracket: () -> Unit,
    clearSymbols: () -> Unit,
    deleteSymbol: () -> Unit,
    toggleAngleMode: () -> Unit,
    evaluate: () -> Unit
) {
    val fractionalIcon = remember(fractional) { if (fractional == Token.Digit.dot) UnittoIcons.Dot else UnittoIcons.Comma }
    val angleIcon = remember(radianMode) { if (radianMode) UnittoIcons.Rad else UnittoIcons.Deg }
    var invMode: Boolean by remember { mutableStateOf(false) }

    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitKeyboard(
            modifier = modifier,
            angleIcon = angleIcon,
            fractionalIcon = fractionalIcon,
            allowVibration = allowVibration,
            middleZero = middleZero,
            addSymbol = addSymbol,
            toggleAngleMode = toggleAngleMode,
            deleteSymbol = deleteSymbol,
            clearSymbols = clearSymbols,
            evaluate = evaluate,
            acButton = acButton,
            addBracket = addBracket,
            invMode = invMode,
            toggleInvMode = { invMode = !invMode },
        )
    } else {
        LandscapeKeyboard(
            modifier = modifier,
            angleIcon = angleIcon,
            fractionalIcon = fractionalIcon,
            allowVibration = allowVibration,
            middleZero = middleZero,
            addSymbol = addSymbol,
            toggleAngleMode = toggleAngleMode,
            deleteSymbol = deleteSymbol,
            clearSymbols = clearSymbols,
            evaluate = evaluate,
            acButton = acButton,
            addBracket = addBracket,
            invMode = invMode,
            toggleInvMode = { invMode = !invMode },
        )
    }
}

@Composable
private fun PortraitKeyboard(
    modifier: Modifier,
    angleIcon: ImageVector,
    fractionalIcon: ImageVector,
    allowVibration: Boolean,
    middleZero: Boolean,
    addSymbol: (String) -> Unit,
    toggleAngleMode: () -> Unit,
    deleteSymbol: () -> Unit,
    clearSymbols: () -> Unit,
    evaluate: () -> Unit,
    acButton: Boolean,
    addBracket: () -> Unit,
    invMode: Boolean,
    toggleInvMode: () -> Unit,
) {
    var showAdditional: Boolean by remember { mutableStateOf(false) }
    val expandRotation: Float by animateFloatAsState(
        targetValue = if (showAdditional) 180f else 0f,
        animationSpec = tween(easing = FastOutSlowInEasing),
        label = "Rotate on expand"
    )

    ColumnWithConstraints(
        modifier = modifier,
    ) { constraints ->
        val spacerHeight = remember { constraints.maxHeight * 0.025f }
        val additionalButtonHeight = remember { constraints.maxHeight * 0.09f }

        Spacer(modifier = Modifier.height(spacerHeight))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Crossfade(
                targetState = invMode,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { inverse ->
                if (inverse) {
                    AdditionalPortrait(
                        modifier = Modifier.fillMaxWidth(),
                        showAdditional = showAdditional,
                        buttonHeight = additionalButtonHeight,
                        content1 = { buttonModifier ->
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Modulo, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Operator.modulo) }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Pi, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Const.pi) }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.PowerWide, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Operator.power) }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Factorial, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Operator.factorial) }
                        },
                        content2 = { buttonModifier ->
                            KeyboardButtonAdditional(buttonModifier, angleIcon, allowVibration, KeyboardButtonContentHeightWide) { toggleAngleMode() }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.ArSin, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Func.arsinBracket) }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.ArCos, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Func.arcosBracket) }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.AcTan, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Func.actanBracket) }

                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Inv, allowVibration, KeyboardButtonContentHeightWide) { toggleInvMode() }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.E, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Const.e) }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Ex, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Func.expBracket) }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Power10, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Digit._1 + Token.Digit._0 + Token.Operator.power) }
                        }
                    )
                } else {
                    AdditionalPortrait(
                        modifier = Modifier.fillMaxWidth(),
                        showAdditional = showAdditional,
                        buttonHeight = additionalButtonHeight,
                        content1 = { buttonModifier ->
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.RootWide, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Operator.sqrt) }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Pi, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Const.pi) }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.PowerWide, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Operator.power) }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Factorial, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Operator.factorial) }
                        },
                        content2 = { buttonModifier ->
                            KeyboardButtonAdditional(buttonModifier, angleIcon, allowVibration, KeyboardButtonContentHeightWide) { toggleAngleMode() }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Sin, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Func.sinBracket) }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Cos, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Func.cosBracket) }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Tan, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Func.tanBracket) }

                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Inv, allowVibration, KeyboardButtonContentHeightWide) { toggleInvMode() }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.E, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Const.e) }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Ln, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Func.lnBracket) }
                            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Log, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Func.logBracket) }
                        }
                    )
                }
            }

            Box(
                modifier = Modifier.size(additionalButtonHeight),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { showAdditional = !showAdditional },
                    colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
                ) {
                    Icon(Icons.Default.ExpandMore, null, Modifier.rotate(expandRotation))
                }
            }
        }

        Spacer(modifier = Modifier.height(spacerHeight))

        KeypadFlow(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            rows = 5,
            columns = 4,
        ) { width, height ->
            val mainButtonModifier = Modifier
                .fillMaxWidth(width)
                .fillMaxHeight(height)

            if (acButton) {
                KeyboardButtonTertiary(mainButtonModifier, UnittoIcons.Clear, allowVibration, KeyboardButtonContentHeightTall) { clearSymbols() }
                KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Brackets, allowVibration, KeyboardButtonContentHeightTall) { addBracket() }
            } else {
                KeyboardButtonFilled(mainButtonModifier, UnittoIcons.LeftBracket, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Operator.leftBracket) }
                KeyboardButtonFilled(mainButtonModifier, UnittoIcons.RightBracket, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Operator.rightBracket) }
            }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Percent, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Operator.percent) }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Divide, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Operator.divide) }

            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key7, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._7) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key8, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._8) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key9, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._9) }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Multiply, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Operator.multiply) }

            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key4, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._4) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key5, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._5) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key6, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._6) }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Minus, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Operator.minus) }

            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key1, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._1) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key2, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._2) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key3, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._3) }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Plus, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Operator.plus) }

            if (middleZero) {
                KeyboardButtonLight(mainButtonModifier, fractionalIcon, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit.dot) }
                KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key0, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._0) }
            } else {
                KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key0, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit._0) }
                KeyboardButtonLight(mainButtonModifier, fractionalIcon, allowVibration, KeyboardButtonContentHeightTall) { addSymbol(Token.Digit.dot) }
            }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Backspace, allowVibration, KeyboardButtonContentHeightTall, clearSymbols) { deleteSymbol() }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Equal, allowVibration, KeyboardButtonContentHeightTall) { evaluate() }
        }

        Spacer(modifier = Modifier.height(spacerHeight))
    }
}

/**
 * Additional buttons.
 *
 * Width: 4 buttons
 *
 * Height: 3 buttons
 *
 * @param showAdditional When `true` reveals [content2] with animation.
 * @param buttonHeight Button height in [Dp].
 * @param content1 First row of buttons.
 * @param content2 Second and third rows of buttons.
 */
@Composable
private fun AdditionalPortrait(
    modifier: Modifier,
    showAdditional: Boolean,
    buttonHeight: Dp,
    content1: @Composable (buttonModifier: Modifier) -> Unit,
    content2: @Composable (buttonModifier: Modifier) -> Unit
) {
    AnimatedContent(
        targetState = showAdditional,
        modifier = modifier,
        transitionSpec = {
            expandVertically(expandFrom = Alignment.Top) togetherWith
                    shrinkVertically(shrinkTowards = Alignment.Top) using (SizeTransform())
        }
    ) { show ->
        if (show) {
            KeypadFlow(
                modifier = Modifier
                    .height(buttonHeight * 3)
                    .fillMaxWidth(),
                rows = 3,
                columns = 4,
                horizontalPadding = 0,
                verticalPadding = 0,
            ) { _, _ ->
                // Can't use provided width and height due to rounding errors. It's ok since weight
                // achieves same result (no padding and equally distributed).
                val buttonModifier = Modifier
                    .weight(1f)
                    .height(buttonHeight)

                content1(buttonModifier)
                content2(buttonModifier)
            }
        } else {
            KeypadFlow(
                modifier = Modifier
                    .height(buttonHeight * 1)
                    .fillMaxWidth(),
                rows = 1,
                columns = 4,
                horizontalPadding = 0,
                verticalPadding = 0,
            ) { _, _ ->
                val buttonModifier = Modifier
                    .weight(1f)
                    .height(buttonHeight)

                content1(buttonModifier)
            }
        }
    }
}

@Composable
private fun LandscapeKeyboard(
    modifier: Modifier,
    angleIcon: ImageVector,
    fractionalIcon: ImageVector,
    allowVibration: Boolean,
    middleZero: Boolean,
    addSymbol: (String) -> Unit,
    toggleAngleMode: () -> Unit,
    deleteSymbol: () -> Unit,
    clearSymbols: () -> Unit,
    evaluate: () -> Unit,
    acButton: Boolean,
    addBracket: () -> Unit,
    invMode: Boolean,
    toggleInvMode: () -> Unit,
) {
    Crossfade(
        targetState = invMode,
        modifier = modifier
    ) { inverse ->
        if (inverse) {
            KeypadFlow(
                modifier = Modifier.fillMaxSize(),
                rows = 4,
                columns = 8
            ) { width, height ->
                val buttonModifier = Modifier
                    .fillMaxWidth(width)
                    .fillMaxHeight(height)

                KeyboardButtonAdditional(buttonModifier, angleIcon, allowVibration, KeyboardButtonContentHeightShort) { toggleAngleMode() }
                KeyboardButtonAdditional(buttonModifier, UnittoIcons.Modulo, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Operator.modulo) }
                KeyboardButtonAdditional(buttonModifier, UnittoIcons.Pi, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Const.pi) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key7, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._7) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key8, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._8) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key9, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._9) }
                if (acButton) {
                    KeyboardButtonTertiary(buttonModifier, UnittoIcons.Clear, allowVibration, KeyboardButtonContentHeightShort) { clearSymbols() }
                    KeyboardButtonFilled(buttonModifier, UnittoIcons.Brackets, allowVibration, KeyboardButtonContentHeightShort) { addBracket() }
                } else {
                    KeyboardButtonFilled(buttonModifier, UnittoIcons.LeftBracket, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.leftBracket) }
                    KeyboardButtonFilled(buttonModifier, UnittoIcons.RightBracket, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.rightBracket) }
                }

                KeyboardButtonAdditional(buttonModifier, UnittoIcons.Inv, allowVibration, KeyboardButtonContentHeightShort) { toggleInvMode() }
                KeyboardButtonAdditional(buttonModifier, UnittoIcons.PowerWide, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.power) }
                KeyboardButtonAdditional(buttonModifier, UnittoIcons.Factorial, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.factorial) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key4, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._4) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key5, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._5) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key6, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._6) }
                KeyboardButtonFilled(buttonModifier, UnittoIcons.Multiply, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.multiply) }
                KeyboardButtonFilled(buttonModifier, UnittoIcons.Divide, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.divide) }

                KeyboardButtonAdditional(buttonModifier, UnittoIcons.ArSin, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Func.arsinBracket) }
                KeyboardButtonAdditional(buttonModifier, UnittoIcons.ArCos, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Func.arcosBracket) }
                KeyboardButtonAdditional(buttonModifier, UnittoIcons.AcTan, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Func.actanBracket) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key1, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._1) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key2, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._2) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key3, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._3) }
                KeyboardButtonFilled(buttonModifier, UnittoIcons.Minus, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.minus) }
                KeyboardButtonFilled(buttonModifier, UnittoIcons.Percent, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.percent) }

                KeyboardButtonAdditional(buttonModifier, UnittoIcons.E, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Const.e) }
                KeyboardButtonAdditional(buttonModifier, UnittoIcons.Ex, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Func.expBracket) }
                KeyboardButtonAdditional(buttonModifier, UnittoIcons.Power10, allowVibration, KeyboardButtonContentHeightWide) { addSymbol(Token.Digit._1 + Token.Digit._0 + Token.Operator.power) }
                if (middleZero) {
                    KeyboardButtonLight(buttonModifier, fractionalIcon, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit.dot) }
                    KeyboardButtonLight(buttonModifier, UnittoIcons.Key0, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._0) }
                } else {
                    KeyboardButtonLight(buttonModifier, UnittoIcons.Key0, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._0) }
                    KeyboardButtonLight(buttonModifier, fractionalIcon, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit.dot) }
                }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Backspace, allowVibration, KeyboardButtonContentHeightShort, clearSymbols) { deleteSymbol() }
                KeyboardButtonFilled(buttonModifier, UnittoIcons.Plus, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.plus) }
                KeyboardButtonFilled(buttonModifier, UnittoIcons.Equal, allowVibration, KeyboardButtonContentHeightShort) { evaluate() }
            }
        } else {
            KeypadFlow(
                modifier = Modifier.fillMaxSize(),
                rows = 4,
                columns = 8
            ) { width, height ->
                val buttonModifier = Modifier
                    .fillMaxWidth(width)
                    .fillMaxHeight(height)

                KeyboardButtonAdditional(buttonModifier, angleIcon, allowVibration, KeyboardButtonContentHeightShort) { toggleAngleMode() }
                KeyboardButtonAdditional(buttonModifier, UnittoIcons.RootWide, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.sqrt) }
                KeyboardButtonAdditional(buttonModifier, UnittoIcons.Pi, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Const.pi) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key7, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._7) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key8, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._8) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key9, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._9) }
                if (acButton) {
                    KeyboardButtonTertiary(buttonModifier, UnittoIcons.Clear, allowVibration, KeyboardButtonContentHeightShort) { clearSymbols() }
                    KeyboardButtonFilled(buttonModifier, UnittoIcons.Brackets, allowVibration, KeyboardButtonContentHeightShort) { addBracket() }
                } else {
                    KeyboardButtonFilled(buttonModifier, UnittoIcons.LeftBracket, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.leftBracket) }
                    KeyboardButtonFilled(buttonModifier, UnittoIcons.RightBracket, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.rightBracket) }
                }

                KeyboardButtonAdditional(buttonModifier, UnittoIcons.Inv, allowVibration, KeyboardButtonContentHeightShort) { toggleInvMode() }
                KeyboardButtonAdditional(buttonModifier, UnittoIcons.PowerWide, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.power) }
                KeyboardButtonAdditional(buttonModifier, UnittoIcons.Factorial, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.factorial) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key4, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._4) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key5, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._5) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key6, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._6) }
                KeyboardButtonFilled(buttonModifier, UnittoIcons.Multiply, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.multiply) }
                KeyboardButtonFilled(buttonModifier, UnittoIcons.Divide, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.divide) }

                KeyboardButtonAdditional(buttonModifier, UnittoIcons.Sin, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Func.sinBracket) }
                KeyboardButtonAdditional(buttonModifier, UnittoIcons.Cos, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Func.cosBracket) }
                KeyboardButtonAdditional(buttonModifier, UnittoIcons.Tan, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Func.tanBracket) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key1, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._1) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key2, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._2) }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Key3, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._3) }
                KeyboardButtonFilled(buttonModifier, UnittoIcons.Minus, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.minus) }
                KeyboardButtonFilled(buttonModifier, UnittoIcons.Percent, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.percent) }

                KeyboardButtonAdditional(buttonModifier, UnittoIcons.E, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Const.e) }
                KeyboardButtonAdditional(buttonModifier, UnittoIcons.Ln, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Func.lnBracket) }
                KeyboardButtonAdditional(buttonModifier, UnittoIcons.Log, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Func.logBracket) }
                if (middleZero) {
                    KeyboardButtonLight(buttonModifier, fractionalIcon, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit.dot) }
                    KeyboardButtonLight(buttonModifier, UnittoIcons.Key0, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._0) }
                } else {
                    KeyboardButtonLight(buttonModifier, UnittoIcons.Key0, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit._0) }
                    KeyboardButtonLight(buttonModifier, fractionalIcon, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Digit.dot) }
                }
                KeyboardButtonLight(buttonModifier, UnittoIcons.Backspace, allowVibration, KeyboardButtonContentHeightShort, clearSymbols) { deleteSymbol() }
                KeyboardButtonFilled(buttonModifier, UnittoIcons.Plus, allowVibration, KeyboardButtonContentHeightShort) { addSymbol(Token.Operator.plus) }
                KeyboardButtonFilled(buttonModifier, UnittoIcons.Equal, allowVibration, KeyboardButtonContentHeightShort) { evaluate() }
            }
        }
    }
}

@Preview(device = "spec:parent=pixel_5,orientation=portrait")
@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun PreviewCalculatorKeyboard() {
    CalculatorKeyboard(
        modifier = Modifier.fillMaxHeight(0.75f),
        radianMode = true,
        fractional = ".",
        addSymbol = {},
        clearSymbols = {},
        deleteSymbol = {},
        toggleAngleMode = {},
        evaluate = {},
        allowVibration = false,
        middleZero = false,
        acButton = true,
        addBracket = {}
    )
}
