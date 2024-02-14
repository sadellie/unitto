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

package com.sadellie.unitto.feature.calculator.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.LocalWindowSize
import com.sadellie.unitto.core.ui.WindowHeightSizeClass
import com.sadellie.unitto.core.ui.common.ColumnWithConstraints
import com.sadellie.unitto.core.ui.common.KeyboardButtonAdditional
import com.sadellie.unitto.core.ui.common.KeyboardButtonContentHeightShort
import com.sadellie.unitto.core.ui.common.KeyboardButtonContentHeightShortAdditional
import com.sadellie.unitto.core.ui.common.KeyboardButtonContentHeightTall
import com.sadellie.unitto.core.ui.common.KeyboardButtonContentHeightTallAdditional
import com.sadellie.unitto.core.ui.common.KeyboardButtonFilled
import com.sadellie.unitto.core.ui.common.KeyboardButtonLight
import com.sadellie.unitto.core.ui.common.KeyboardButtonTertiary
import com.sadellie.unitto.core.ui.common.KeypadFlow
import com.sadellie.unitto.core.ui.common.icons.IconPack
import com.sadellie.unitto.core.ui.common.icons.iconpack.AcTan
import com.sadellie.unitto.core.ui.common.icons.iconpack.ArCos
import com.sadellie.unitto.core.ui.common.icons.iconpack.ArSin
import com.sadellie.unitto.core.ui.common.icons.iconpack.Backspace
import com.sadellie.unitto.core.ui.common.icons.iconpack.Brackets
import com.sadellie.unitto.core.ui.common.icons.iconpack.Clear
import com.sadellie.unitto.core.ui.common.icons.iconpack.Comma
import com.sadellie.unitto.core.ui.common.icons.iconpack.Cos
import com.sadellie.unitto.core.ui.common.icons.iconpack.Deg
import com.sadellie.unitto.core.ui.common.icons.iconpack.Divide
import com.sadellie.unitto.core.ui.common.icons.iconpack.Dot
import com.sadellie.unitto.core.ui.common.icons.iconpack.Equal
import com.sadellie.unitto.core.ui.common.icons.iconpack.Euler
import com.sadellie.unitto.core.ui.common.icons.iconpack.Ex
import com.sadellie.unitto.core.ui.common.icons.iconpack.Factorial
import com.sadellie.unitto.core.ui.common.icons.iconpack.Inv
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key0
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key1
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key2
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key3
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key4
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key5
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key6
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key7
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key8
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key9
import com.sadellie.unitto.core.ui.common.icons.iconpack.LeftBracket
import com.sadellie.unitto.core.ui.common.icons.iconpack.Ln
import com.sadellie.unitto.core.ui.common.icons.iconpack.Log
import com.sadellie.unitto.core.ui.common.icons.iconpack.Minus
import com.sadellie.unitto.core.ui.common.icons.iconpack.Modulo
import com.sadellie.unitto.core.ui.common.icons.iconpack.Multiply
import com.sadellie.unitto.core.ui.common.icons.iconpack.Percent
import com.sadellie.unitto.core.ui.common.icons.iconpack.Pi
import com.sadellie.unitto.core.ui.common.icons.iconpack.Plus
import com.sadellie.unitto.core.ui.common.icons.iconpack.Power
import com.sadellie.unitto.core.ui.common.icons.iconpack.Power10
import com.sadellie.unitto.core.ui.common.icons.iconpack.Rad
import com.sadellie.unitto.core.ui.common.icons.iconpack.RightBracket
import com.sadellie.unitto.core.ui.common.icons.iconpack.Root
import com.sadellie.unitto.core.ui.common.icons.iconpack.Sin
import com.sadellie.unitto.core.ui.common.icons.iconpack.Tan

@Composable
internal fun CalculatorKeyboard(
    modifier: Modifier,
    onAddTokenClick: (String) -> Unit,
    onBracketsClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onClearClick: () -> Unit,
    onEqualClick: () -> Unit,
    onAngleClick: (Boolean) -> Unit,
    radianMode: Boolean,
    showAcButton: Boolean,
    middleZero: Boolean,
    fractional: String,
) {
    var showInvButtons: Boolean by remember { mutableStateOf(false) }

    if (LocalWindowSize.current.heightSizeClass < WindowHeightSizeClass.Medium) {
        LandscapeKeyboard(
            modifier = modifier,
            onAddTokenClick = onAddTokenClick,
            onBracketsClick = onBracketsClick,
            onDeleteClick = onDeleteClick,
            onClearClick = onClearClick,
            onEqualClick = onEqualClick,
            onInvClick = { showInvButtons = !showInvButtons },
            onAngleClick = onAngleClick,
            showInvButtons = showInvButtons,
            radianMode = radianMode,
            showAcButton = showAcButton,
            middleZero = middleZero,
            fractional = fractional,
        )
    } else {
        PortraitKeyboard(
            modifier = modifier,
            onAddTokenClick = onAddTokenClick,
            onBracketsClick = onBracketsClick,
            onDeleteClick = onDeleteClick,
            onClearClick = onClearClick,
            onEqualClick = onEqualClick,
            onInvClick = { showInvButtons = !showInvButtons },
            onAngleClick = onAngleClick,
            showInvButtons = showInvButtons,
            radianMode = radianMode,
            showAcButton = showAcButton,
            middleZero = middleZero,
            fractional = fractional,
        )
    }
}

@Composable
private fun PortraitKeyboard(
    modifier: Modifier,
    onAddTokenClick: (String) -> Unit,
    onBracketsClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onClearClick: () -> Unit,
    onEqualClick: () -> Unit,
    onInvClick: () -> Unit,
    onAngleClick: (Boolean) -> Unit,
    showInvButtons: Boolean,
    radianMode: Boolean,
    showAcButton: Boolean,
    middleZero: Boolean,
    fractional: String,
) {
    val angleIcon = remember(radianMode) { if (radianMode) IconPack.Rad else IconPack.Deg }
    val angleIconDescription = remember(radianMode) { if (radianMode) R.string.keyboard_radian else R.string.keyboard_degree }

    val fractionalIcon = remember(fractional) { if (fractional == Token.PERIOD) IconPack.Dot else IconPack.Comma }
    val fractionalIconDescription = remember(fractional) { if (fractional == Token.PERIOD) R.string.keyboard_dot else R.string.comma }

    var showAdditional: Boolean by remember { mutableStateOf(false) }
    val expandRotation: Float by animateFloatAsState(
        targetValue = if (showAdditional) 180f else 0f,
        animationSpec = tween(easing = FastOutSlowInEasing),
        label = "Rotate on expand"
    )

    ColumnWithConstraints(
        modifier = modifier,
    ) { constraints ->
        val spacerHeight = constraints.maxHeight * 0.025f
        val additionalButtonHeight = constraints.maxHeight * 0.09f

        Spacer(modifier = Modifier.height(spacerHeight))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Crossfade(
                targetState = showInvButtons,
                label = "Inverse switch",
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
                            KeyboardButtonAdditional(buttonModifier, IconPack.Modulo, stringResource(R.string.keyboard_modulo), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Operator.modulo) }
                            KeyboardButtonAdditional(buttonModifier, IconPack.Pi, stringResource(R.string.keyboard_pi), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Const.pi) }
                            KeyboardButtonAdditional(buttonModifier, IconPack.Power, stringResource(R.string.keyboard_power), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Operator.power) }
                            KeyboardButtonAdditional(buttonModifier, IconPack.Factorial, stringResource(R.string.keyboard_factorial), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Operator.factorial) }
                        },
                        content2 = { buttonModifier ->
                            KeyboardButtonAdditional(buttonModifier, angleIcon, stringResource(angleIconDescription), KeyboardButtonContentHeightTallAdditional) { onAngleClick(!radianMode) }
                            KeyboardButtonAdditional(buttonModifier, IconPack.ArSin, stringResource(R.string.keyboard_arsin), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Func.arsinBracket) }
                            KeyboardButtonAdditional(buttonModifier, IconPack.ArCos, stringResource(R.string.keyboard_arcos), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Func.arcosBracket) }
                            KeyboardButtonAdditional(buttonModifier, IconPack.AcTan, stringResource(R.string.keyboard_actan), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Func.actanBracket) }

                            KeyboardButtonAdditional(buttonModifier, IconPack.Inv, stringResource(R.string.keyboard_inverse), KeyboardButtonContentHeightTallAdditional) { onInvClick() }
                            KeyboardButtonAdditional(buttonModifier, IconPack.Euler, stringResource(R.string.keyboard_euler), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Const.e) }
                            KeyboardButtonAdditional(buttonModifier, IconPack.Ex, stringResource(R.string.keyboard_exp), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Func.expBracket) }
                            KeyboardButtonAdditional(buttonModifier, IconPack.Power10, stringResource(R.string.keyboard_power_10), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Digit._1 + Token.Digit._0 + Token.Operator.power) }
                        }
                    )
                } else {
                    AdditionalPortrait(
                        modifier = Modifier.fillMaxWidth(),
                        showAdditional = showAdditional,
                        buttonHeight = additionalButtonHeight,
                        content1 = { buttonModifier ->
                            KeyboardButtonAdditional(buttonModifier, IconPack.Root, stringResource(R.string.keyboard_root), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Operator.sqrt) }
                            KeyboardButtonAdditional(buttonModifier, IconPack.Pi, stringResource(R.string.keyboard_pi), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Const.pi) }
                            KeyboardButtonAdditional(buttonModifier, IconPack.Power, stringResource(R.string.keyboard_power), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Operator.power) }
                            KeyboardButtonAdditional(buttonModifier, IconPack.Factorial, stringResource(R.string.keyboard_factorial), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Operator.factorial) }
                        },
                        content2 = { buttonModifier ->
                            KeyboardButtonAdditional(buttonModifier, angleIcon, stringResource(angleIconDescription), KeyboardButtonContentHeightTallAdditional) { onAngleClick(!radianMode) }
                            KeyboardButtonAdditional(buttonModifier, IconPack.Sin, stringResource(R.string.keyboard_sin), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Func.sinBracket) }
                            KeyboardButtonAdditional(buttonModifier, IconPack.Cos, stringResource(R.string.keyboard_cos), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Func.cosBracket) }
                            KeyboardButtonAdditional(buttonModifier, IconPack.Tan, stringResource(R.string.keyboard_tan), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Func.tanBracket) }

                            KeyboardButtonAdditional(buttonModifier, IconPack.Inv, stringResource(R.string.keyboard_inverse), KeyboardButtonContentHeightTallAdditional) { onInvClick() }
                            KeyboardButtonAdditional(buttonModifier, IconPack.Euler, stringResource(R.string.keyboard_exp), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Const.e) }
                            KeyboardButtonAdditional(buttonModifier, IconPack.Ln, stringResource(R.string.keyboard_ln), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Func.lnBracket) }
                            KeyboardButtonAdditional(buttonModifier, IconPack.Log, stringResource(R.string.keyboard_log), KeyboardButtonContentHeightTallAdditional) { onAddTokenClick(Token.Func.logBracket) }
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

            if (showAcButton) {
                KeyboardButtonTertiary(mainButtonModifier, IconPack.Clear, stringResource(R.string.clear_label), KeyboardButtonContentHeightTall) { onClearClick() }
                KeyboardButtonFilled(mainButtonModifier, IconPack.Brackets, stringResource(R.string.keyboard_brackets), KeyboardButtonContentHeightTall) { onBracketsClick() }
            } else {
                KeyboardButtonFilled(mainButtonModifier, IconPack.LeftBracket, stringResource(R.string.keyboard_left_bracket), KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Operator.leftBracket) }
                KeyboardButtonFilled(mainButtonModifier, IconPack.RightBracket, stringResource(R.string.keyboard_right_bracket), KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Operator.rightBracket) }
            }
            KeyboardButtonFilled(mainButtonModifier, IconPack.Percent, stringResource(R.string.keyboard_percent), KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Operator.percent) }
            KeyboardButtonFilled(mainButtonModifier, IconPack.Divide, stringResource(R.string.keyboard_divide), KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Operator.divide) }

            KeyboardButtonLight(mainButtonModifier, IconPack.Key7, Token.Digit._7, KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Digit._7) }
            KeyboardButtonLight(mainButtonModifier, IconPack.Key8, Token.Digit._8, KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Digit._8) }
            KeyboardButtonLight(mainButtonModifier, IconPack.Key9, Token.Digit._9, KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Digit._9) }
            KeyboardButtonFilled(mainButtonModifier, IconPack.Multiply, stringResource(R.string.keyboard_multiply), KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Operator.multiply) }

            KeyboardButtonLight(mainButtonModifier, IconPack.Key4, Token.Digit._4, KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Digit._4) }
            KeyboardButtonLight(mainButtonModifier, IconPack.Key5, Token.Digit._5, KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Digit._5) }
            KeyboardButtonLight(mainButtonModifier, IconPack.Key6, Token.Digit._6, KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Digit._6) }
            KeyboardButtonFilled(mainButtonModifier, IconPack.Minus, stringResource(R.string.keyboard_minus), KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Operator.minus) }

            KeyboardButtonLight(mainButtonModifier, IconPack.Key1, Token.Digit._1, KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Digit._1) }
            KeyboardButtonLight(mainButtonModifier, IconPack.Key2, Token.Digit._2, KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Digit._2) }
            KeyboardButtonLight(mainButtonModifier, IconPack.Key3, Token.Digit._3, KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Digit._3) }
            KeyboardButtonFilled(mainButtonModifier, IconPack.Plus, stringResource(R.string.keyboard_plus), KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Operator.plus) }

            if (middleZero) {
                KeyboardButtonLight(mainButtonModifier, fractionalIcon, stringResource(fractionalIconDescription), KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Digit.dot) }
                KeyboardButtonLight(mainButtonModifier, IconPack.Key0, Token.Digit._0, KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Digit._0) }
            } else {
                KeyboardButtonLight(mainButtonModifier, IconPack.Key0, Token.Digit._0, KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Digit._0) }
                KeyboardButtonLight(mainButtonModifier, fractionalIcon, stringResource(fractionalIconDescription), KeyboardButtonContentHeightTall) { onAddTokenClick(Token.Digit.dot) }
            }
            KeyboardButtonLight(mainButtonModifier, IconPack.Backspace, stringResource(R.string.delete_label), KeyboardButtonContentHeightTall, onClearClick) { onDeleteClick() }
            KeyboardButtonFilled(mainButtonModifier, IconPack.Equal, stringResource(R.string.keyboard_equal), KeyboardButtonContentHeightTall) { onEqualClick() }
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
        label = "Additional buttons reveal",
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
    onAddTokenClick: (String) -> Unit,
    onBracketsClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onClearClick: () -> Unit,
    onEqualClick: () -> Unit,
    onInvClick: () -> Unit,
    onAngleClick: (Boolean) -> Unit,
    showInvButtons: Boolean,
    radianMode: Boolean,
    showAcButton: Boolean,
    middleZero: Boolean,
    fractional: String,
) {
    val angleIcon = remember(radianMode) { if (radianMode) IconPack.Rad else IconPack.Deg }
    val angleIconDescription = remember(radianMode) { if (radianMode) R.string.keyboard_radian else R.string.keyboard_degree }

    val fractionalIcon = remember(fractional) { if (fractional == Token.PERIOD) IconPack.Dot else IconPack.Comma }
    val fractionalIconDescription = remember(fractional) { if (fractional == Token.PERIOD) R.string.keyboard_dot else R.string.comma }

    Crossfade(
        targetState = showInvButtons,
        label = "Inverse switch",
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

                KeyboardButtonAdditional(buttonModifier, angleIcon, stringResource(angleIconDescription), KeyboardButtonContentHeightShortAdditional) { onAngleClick(!radianMode) }
                KeyboardButtonAdditional(buttonModifier, IconPack.Modulo, stringResource(R.string.keyboard_modulo), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Operator.modulo) }
                KeyboardButtonAdditional(buttonModifier, IconPack.Pi, stringResource(R.string.keyboard_pi), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Const.pi) }
                KeyboardButtonLight(buttonModifier, IconPack.Key7, Token.Digit._7, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._7) }
                KeyboardButtonLight(buttonModifier, IconPack.Key8, Token.Digit._8, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._8) }
                KeyboardButtonLight(buttonModifier, IconPack.Key9, Token.Digit._9, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._9) }
                if (showAcButton) {
                    KeyboardButtonTertiary(buttonModifier, IconPack.Clear, stringResource(R.string.clear_label), KeyboardButtonContentHeightShort) { onClearClick() }
                    KeyboardButtonFilled(buttonModifier, IconPack.Brackets, stringResource(R.string.keyboard_brackets), KeyboardButtonContentHeightShort) { onBracketsClick() }
                } else {
                    KeyboardButtonFilled(buttonModifier, IconPack.LeftBracket, stringResource(R.string.keyboard_left_bracket), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Operator.leftBracket) }
                    KeyboardButtonFilled(buttonModifier, IconPack.RightBracket, stringResource(R.string.keyboard_right_bracket), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Operator.rightBracket) }
                }

                KeyboardButtonAdditional(buttonModifier, IconPack.Inv, stringResource(R.string.keyboard_inverse), KeyboardButtonContentHeightShortAdditional) { onInvClick() }
                KeyboardButtonAdditional(buttonModifier, IconPack.Power, stringResource(R.string.keyboard_power), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Operator.power) }
                KeyboardButtonAdditional(buttonModifier, IconPack.Factorial, stringResource(R.string.keyboard_factorial), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Operator.factorial) }
                KeyboardButtonLight(buttonModifier, IconPack.Key4, Token.Digit._4, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._4) }
                KeyboardButtonLight(buttonModifier, IconPack.Key5, Token.Digit._5, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._5) }
                KeyboardButtonLight(buttonModifier, IconPack.Key6, Token.Digit._6, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._6) }
                KeyboardButtonFilled(buttonModifier, IconPack.Multiply, stringResource(R.string.keyboard_multiply), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Operator.multiply) }
                KeyboardButtonFilled(buttonModifier, IconPack.Divide, stringResource(R.string.keyboard_divide), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Operator.divide) }

                KeyboardButtonAdditional(buttonModifier, IconPack.ArSin, stringResource(R.string.keyboard_arsin), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Func.arsinBracket) }
                KeyboardButtonAdditional(buttonModifier, IconPack.ArCos, stringResource(R.string.keyboard_arcos), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Func.arcosBracket) }
                KeyboardButtonAdditional(buttonModifier, IconPack.AcTan, stringResource(R.string.keyboard_actan), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Func.actanBracket) }
                KeyboardButtonLight(buttonModifier, IconPack.Key1, Token.Digit._1, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._1) }
                KeyboardButtonLight(buttonModifier, IconPack.Key2, Token.Digit._2, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._2) }
                KeyboardButtonLight(buttonModifier, IconPack.Key3, Token.Digit._3, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._3) }
                KeyboardButtonFilled(buttonModifier, IconPack.Minus, stringResource(R.string.keyboard_minus), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Operator.minus) }
                KeyboardButtonFilled(buttonModifier, IconPack.Percent, stringResource(R.string.keyboard_percent), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Operator.percent) }

                KeyboardButtonAdditional(buttonModifier, IconPack.Euler, stringResource(R.string.keyboard_euler), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Const.e) }
                KeyboardButtonAdditional(buttonModifier, IconPack.Ex, stringResource(R.string.keyboard_exp), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Func.expBracket) }
                KeyboardButtonAdditional(buttonModifier, IconPack.Power10, stringResource(R.string.keyboard_power_10), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Digit._1 + Token.Digit._0 + Token.Operator.power) }
                if (middleZero) {
                    KeyboardButtonLight(buttonModifier, fractionalIcon, stringResource(fractionalIconDescription), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit.dot) }
                    KeyboardButtonLight(buttonModifier, IconPack.Key0, Token.Digit._0, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._0) }
                } else {
                    KeyboardButtonLight(buttonModifier, IconPack.Key0, Token.Digit._0, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._0) }
                    KeyboardButtonLight(buttonModifier, fractionalIcon, stringResource(fractionalIconDescription), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit.dot) }
                }
                KeyboardButtonLight(buttonModifier, IconPack.Backspace, stringResource(R.string.delete_label), KeyboardButtonContentHeightShort, onClearClick) { onDeleteClick() }
                KeyboardButtonFilled(buttonModifier, IconPack.Plus, stringResource(R.string.keyboard_plus), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Operator.plus) }
                KeyboardButtonFilled(buttonModifier, IconPack.Equal, stringResource(R.string.keyboard_equal), KeyboardButtonContentHeightShort) { onEqualClick() }
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

                KeyboardButtonAdditional(buttonModifier, angleIcon, stringResource(angleIconDescription), KeyboardButtonContentHeightShortAdditional) { onAngleClick(!radianMode) }
                KeyboardButtonAdditional(buttonModifier, IconPack.Root, stringResource(R.string.keyboard_root), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Operator.sqrt) }
                KeyboardButtonAdditional(buttonModifier, IconPack.Pi, stringResource(R.string.keyboard_pi), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Const.pi) }
                KeyboardButtonLight(buttonModifier, IconPack.Key7, Token.Digit._7, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._7) }
                KeyboardButtonLight(buttonModifier, IconPack.Key8, Token.Digit._8, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._8) }
                KeyboardButtonLight(buttonModifier, IconPack.Key9, Token.Digit._9, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._9) }
                if (showAcButton) {
                    KeyboardButtonTertiary(buttonModifier, IconPack.Clear, stringResource(R.string.clear_label), KeyboardButtonContentHeightShort) { onClearClick() }
                    KeyboardButtonFilled(buttonModifier, IconPack.Brackets, stringResource(R.string.keyboard_brackets), KeyboardButtonContentHeightShort) { onBracketsClick() }
                } else {
                    KeyboardButtonFilled(buttonModifier, IconPack.LeftBracket, stringResource(R.string.keyboard_left_bracket), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Operator.leftBracket) }
                    KeyboardButtonFilled(buttonModifier, IconPack.RightBracket, stringResource(R.string.keyboard_right_bracket), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Operator.rightBracket) }
                }

                KeyboardButtonAdditional(buttonModifier, IconPack.Inv, stringResource(R.string.keyboard_inverse), KeyboardButtonContentHeightShortAdditional) { onInvClick() }
                KeyboardButtonAdditional(buttonModifier, IconPack.Power, stringResource(R.string.keyboard_power), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Operator.power) }
                KeyboardButtonAdditional(buttonModifier, IconPack.Factorial, stringResource(R.string.keyboard_factorial), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Operator.factorial) }
                KeyboardButtonLight(buttonModifier, IconPack.Key4, Token.Digit._4, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._4) }
                KeyboardButtonLight(buttonModifier, IconPack.Key5, Token.Digit._5, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._5) }
                KeyboardButtonLight(buttonModifier, IconPack.Key6, Token.Digit._6, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._6) }
                KeyboardButtonFilled(buttonModifier, IconPack.Multiply, stringResource(R.string.keyboard_multiply), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Operator.multiply) }
                KeyboardButtonFilled(buttonModifier, IconPack.Divide, stringResource(R.string.keyboard_divide), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Operator.divide) }

                KeyboardButtonAdditional(buttonModifier, IconPack.Sin, stringResource(R.string.keyboard_sin), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Func.sinBracket) }
                KeyboardButtonAdditional(buttonModifier, IconPack.Cos, stringResource(R.string.keyboard_cos), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Func.cosBracket) }
                KeyboardButtonAdditional(buttonModifier, IconPack.Tan, stringResource(R.string.keyboard_tan), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Func.tanBracket) }
                KeyboardButtonLight(buttonModifier, IconPack.Key1, Token.Digit._1, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._1) }
                KeyboardButtonLight(buttonModifier, IconPack.Key2, Token.Digit._2, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._2) }
                KeyboardButtonLight(buttonModifier, IconPack.Key3, Token.Digit._3, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._3) }
                KeyboardButtonFilled(buttonModifier, IconPack.Minus, stringResource(R.string.keyboard_minus), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Operator.minus) }
                KeyboardButtonFilled(buttonModifier, IconPack.Percent, stringResource(R.string.keyboard_percent), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Operator.percent) }

                KeyboardButtonAdditional(buttonModifier, IconPack.Euler, stringResource(R.string.keyboard_euler), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Const.e) }
                KeyboardButtonAdditional(buttonModifier, IconPack.Ln, stringResource(R.string.keyboard_ln), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Func.lnBracket) }
                KeyboardButtonAdditional(buttonModifier, IconPack.Log, stringResource(R.string.keyboard_log), KeyboardButtonContentHeightShortAdditional) { onAddTokenClick(Token.Func.logBracket) }
                if (middleZero) {
                    KeyboardButtonLight(buttonModifier, fractionalIcon, stringResource(fractionalIconDescription), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit.dot) }
                    KeyboardButtonLight(buttonModifier, IconPack.Key0, Token.Digit._0, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._0) }
                } else {
                    KeyboardButtonLight(buttonModifier, IconPack.Key0, Token.Digit._0, KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit._0) }
                    KeyboardButtonLight(buttonModifier, fractionalIcon, stringResource(fractionalIconDescription), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Digit.dot) }
                }
                KeyboardButtonLight(buttonModifier, IconPack.Backspace, stringResource(R.string.delete_label), KeyboardButtonContentHeightShort, onClearClick) { onDeleteClick() }
                KeyboardButtonFilled(buttonModifier, IconPack.Plus, stringResource(R.string.keyboard_plus), KeyboardButtonContentHeightShort) { onAddTokenClick(Token.Operator.plus) }
                KeyboardButtonFilled(buttonModifier, IconPack.Equal, stringResource(R.string.keyboard_equal), KeyboardButtonContentHeightShort) { onEqualClick() }
            }
        }
    }
}

@Preview(device = "spec:width=400dp,height=600dp,dpi=440")
@Composable
private fun PreviewPortraitKeyboard() {
    PortraitKeyboard(
        modifier = Modifier.fillMaxHeight(),
        onAddTokenClick = {},
        onBracketsClick = {},
        onDeleteClick = {},
        onClearClick = {},
        onEqualClick = {},
        onInvClick = {},
        onAngleClick = {},
        showInvButtons = false,
        radianMode = true,
        showAcButton = true,
        middleZero = false,
        fractional = Token.PERIOD,
    )
}

@Preview(device = "spec:width=600dp,height=300dp,dpi=440")
@Composable
private fun PreviewLandscapeKeyboard() {
    LandscapeKeyboard(
        modifier = Modifier.fillMaxHeight(),
        onAddTokenClick = {},
        onBracketsClick = {},
        onDeleteClick = {},
        onClearClick = {},
        onEqualClick = {},
        onInvClick = {},
        onAngleClick = {},
        showInvButtons = false,
        radianMode = true,
        showAcButton = true,
        middleZero = false,
        fractional = Token.PERIOD,
    )
}
