/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

package com.sadellie.unitto.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.designsystem.icons.iconpack.AcTan
import com.sadellie.unitto.core.designsystem.icons.iconpack.ArCos
import com.sadellie.unitto.core.designsystem.icons.iconpack.ArSin
import com.sadellie.unitto.core.designsystem.icons.iconpack.Backspace
import com.sadellie.unitto.core.designsystem.icons.iconpack.Brackets
import com.sadellie.unitto.core.designsystem.icons.iconpack.Clear
import com.sadellie.unitto.core.designsystem.icons.iconpack.Comma
import com.sadellie.unitto.core.designsystem.icons.iconpack.Cos
import com.sadellie.unitto.core.designsystem.icons.iconpack.Deg
import com.sadellie.unitto.core.designsystem.icons.iconpack.Divide
import com.sadellie.unitto.core.designsystem.icons.iconpack.Dot
import com.sadellie.unitto.core.designsystem.icons.iconpack.Equal
import com.sadellie.unitto.core.designsystem.icons.iconpack.Euler
import com.sadellie.unitto.core.designsystem.icons.iconpack.Ex
import com.sadellie.unitto.core.designsystem.icons.iconpack.Factorial
import com.sadellie.unitto.core.designsystem.icons.iconpack.IconPack
import com.sadellie.unitto.core.designsystem.icons.iconpack.Inv
import com.sadellie.unitto.core.designsystem.icons.iconpack.Key0
import com.sadellie.unitto.core.designsystem.icons.iconpack.Key1
import com.sadellie.unitto.core.designsystem.icons.iconpack.Key2
import com.sadellie.unitto.core.designsystem.icons.iconpack.Key3
import com.sadellie.unitto.core.designsystem.icons.iconpack.Key4
import com.sadellie.unitto.core.designsystem.icons.iconpack.Key5
import com.sadellie.unitto.core.designsystem.icons.iconpack.Key6
import com.sadellie.unitto.core.designsystem.icons.iconpack.Key7
import com.sadellie.unitto.core.designsystem.icons.iconpack.Key8
import com.sadellie.unitto.core.designsystem.icons.iconpack.Key9
import com.sadellie.unitto.core.designsystem.icons.iconpack.KeyA
import com.sadellie.unitto.core.designsystem.icons.iconpack.KeyB
import com.sadellie.unitto.core.designsystem.icons.iconpack.KeyC
import com.sadellie.unitto.core.designsystem.icons.iconpack.KeyD
import com.sadellie.unitto.core.designsystem.icons.iconpack.KeyE
import com.sadellie.unitto.core.designsystem.icons.iconpack.KeyF
import com.sadellie.unitto.core.designsystem.icons.iconpack.LeftBracket
import com.sadellie.unitto.core.designsystem.icons.iconpack.Ln
import com.sadellie.unitto.core.designsystem.icons.iconpack.Log
import com.sadellie.unitto.core.designsystem.icons.iconpack.Minus
import com.sadellie.unitto.core.designsystem.icons.iconpack.Modulo
import com.sadellie.unitto.core.designsystem.icons.iconpack.Multiply
import com.sadellie.unitto.core.designsystem.icons.iconpack.Percent
import com.sadellie.unitto.core.designsystem.icons.iconpack.Pi
import com.sadellie.unitto.core.designsystem.icons.iconpack.Plus
import com.sadellie.unitto.core.designsystem.icons.iconpack.Power
import com.sadellie.unitto.core.designsystem.icons.iconpack.Power10
import com.sadellie.unitto.core.designsystem.icons.iconpack.Rad
import com.sadellie.unitto.core.designsystem.icons.iconpack.RightBracket
import com.sadellie.unitto.core.designsystem.icons.iconpack.Root
import com.sadellie.unitto.core.designsystem.icons.iconpack.Sin
import com.sadellie.unitto.core.designsystem.icons.iconpack.Tan
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_clear
import unitto.core.common.generated.resources.common_comma
import unitto.core.common.generated.resources.common_delete
import unitto.core.common.generated.resources.keyboard_actan
import unitto.core.common.generated.resources.keyboard_arcos
import unitto.core.common.generated.resources.keyboard_arsin
import unitto.core.common.generated.resources.keyboard_brackets
import unitto.core.common.generated.resources.keyboard_cos
import unitto.core.common.generated.resources.keyboard_degree
import unitto.core.common.generated.resources.keyboard_divide
import unitto.core.common.generated.resources.keyboard_dot
import unitto.core.common.generated.resources.keyboard_equal
import unitto.core.common.generated.resources.keyboard_euler
import unitto.core.common.generated.resources.keyboard_exp
import unitto.core.common.generated.resources.keyboard_factorial
import unitto.core.common.generated.resources.keyboard_inverse
import unitto.core.common.generated.resources.keyboard_left_bracket
import unitto.core.common.generated.resources.keyboard_ln
import unitto.core.common.generated.resources.keyboard_log
import unitto.core.common.generated.resources.keyboard_minus
import unitto.core.common.generated.resources.keyboard_modulo
import unitto.core.common.generated.resources.keyboard_multiply
import unitto.core.common.generated.resources.keyboard_percent
import unitto.core.common.generated.resources.keyboard_pi
import unitto.core.common.generated.resources.keyboard_plus
import unitto.core.common.generated.resources.keyboard_power
import unitto.core.common.generated.resources.keyboard_power_10
import unitto.core.common.generated.resources.keyboard_radian
import unitto.core.common.generated.resources.keyboard_right_bracket
import unitto.core.common.generated.resources.keyboard_root
import unitto.core.common.generated.resources.keyboard_sin
import unitto.core.common.generated.resources.keyboard_tan

sealed interface KeypadButton {
  val icon: ImageVector
  val description: StringResource?

  data class KeypadButtonAdd(
    override val icon: ImageVector,
    override val description: StringResource?,
    val token: String,
  ) : KeypadButton

  data class KeypadButtonSimple(
    override val icon: ImageVector,
    override val description: StringResource?,
  ) : KeypadButton

  companion object {
    val Key0 = KeypadButtonAdd(IconPack.Key0, null, Token.Digit.DIGIT_0)
    val Key1 = KeypadButtonAdd(IconPack.Key1, null, Token.Digit.DIGIT_1)
    val Key2 = KeypadButtonAdd(IconPack.Key2, null, Token.Digit.DIGIT_2)
    val Key3 = KeypadButtonAdd(IconPack.Key3, null, Token.Digit.DIGIT_3)
    val Key4 = KeypadButtonAdd(IconPack.Key4, null, Token.Digit.DIGIT_4)
    val Key5 = KeypadButtonAdd(IconPack.Key5, null, Token.Digit.DIGIT_5)
    val Key6 = KeypadButtonAdd(IconPack.Key6, null, Token.Digit.DIGIT_6)
    val Key7 = KeypadButtonAdd(IconPack.Key7, null, Token.Digit.DIGIT_7)
    val Key8 = KeypadButtonAdd(IconPack.Key8, null, Token.Digit.DIGIT_8)
    val Key9 = KeypadButtonAdd(IconPack.Key9, null, Token.Digit.DIGIT_9)

    val KeyA = KeypadButtonAdd(IconPack.KeyA, null, Token.Letter.LETTER_A)
    val KeyB = KeypadButtonAdd(IconPack.KeyB, null, Token.Letter.LETTER_B)
    val KeyC = KeypadButtonAdd(IconPack.KeyC, null, Token.Letter.LETTER_C)
    val KeyD = KeypadButtonAdd(IconPack.KeyD, null, Token.Letter.LETTER_D)
    val KeyE = KeypadButtonAdd(IconPack.KeyE, null, Token.Letter.LETTER_E)
    val KeyF = KeypadButtonAdd(IconPack.KeyF, null, Token.Letter.LETTER_F)

    val AngleRadKey = KeypadButtonSimple(IconPack.Rad, Res.string.keyboard_radian)
    val AngleDegKey = KeypadButtonSimple(IconPack.Deg, Res.string.keyboard_degree)
    val BackspaceKey = KeypadButtonSimple(IconPack.Backspace, Res.string.common_delete)
    val BracketsKey = KeypadButtonSimple(IconPack.Brackets, Res.string.keyboard_brackets)
    val ClearKey = KeypadButtonSimple(IconPack.Clear, Res.string.common_clear)
    val EqualKey = KeypadButtonSimple(IconPack.Equal, Res.string.keyboard_equal)
    val InvKey = KeypadButtonSimple(IconPack.Inv, Res.string.keyboard_inverse)

    val AcTanKey =
      KeypadButtonAdd(IconPack.AcTan, Res.string.keyboard_actan, Token.Func.ACTAN_BRACKET)
    val ArCosKey =
      KeypadButtonAdd(IconPack.ArCos, Res.string.keyboard_arcos, Token.Func.ARCOS_BRACKET)
    val ArSinKey =
      KeypadButtonAdd(IconPack.ArSin, Res.string.keyboard_arsin, Token.Func.ARSIN_BRACKET)
    val CommaKey = KeypadButtonAdd(IconPack.Comma, Res.string.common_comma, Token.Digit.DOT)
    val CosKey = KeypadButtonAdd(IconPack.Cos, Res.string.keyboard_cos, Token.Func.COS_BRACKET)
    val DotKey = KeypadButtonAdd(IconPack.Dot, Res.string.keyboard_dot, Token.Digit.DOT)
    val DivideKey =
      KeypadButtonAdd(IconPack.Divide, Res.string.keyboard_divide, Token.Operator.DIVIDE)
    val EulerKey = KeypadButtonAdd(IconPack.Euler, Res.string.keyboard_euler, Token.Const.E)
    val ExKey = KeypadButtonAdd(IconPack.Ex, Res.string.keyboard_exp, Token.Func.EXP_BRACKET)
    val FactorialKey =
      KeypadButtonAdd(IconPack.Factorial, Res.string.keyboard_factorial, Token.Operator.FACTORIAL)
    val MinusKey = KeypadButtonAdd(IconPack.Minus, Res.string.keyboard_minus, Token.Operator.MINUS)
    val ModuloKey =
      KeypadButtonAdd(IconPack.Modulo, Res.string.keyboard_modulo, Token.Operator.MODULO)
    val MultiplyKey =
      KeypadButtonAdd(IconPack.Multiply, Res.string.keyboard_multiply, Token.Operator.MULTIPLY)
    val LeftBracketKey =
      KeypadButtonAdd(
        IconPack.LeftBracket,
        Res.string.keyboard_left_bracket,
        Token.Operator.LEFT_BRACKET,
      )
    val LnKey = KeypadButtonAdd(IconPack.Ln, Res.string.keyboard_ln, Token.Func.LN_BRACKET)
    val LogKey = KeypadButtonAdd(IconPack.Log, Res.string.keyboard_log, Token.Func.LOG_BRACKET)
    val PercentKey =
      KeypadButtonAdd(IconPack.Percent, Res.string.keyboard_percent, Token.Operator.PERCENT)
    val PlusKey = KeypadButtonAdd(IconPack.Plus, Res.string.keyboard_plus, Token.Operator.PLUS)
    val PiKey = KeypadButtonAdd(IconPack.Pi, Res.string.keyboard_pi, Token.Const.PI)
    val PowerKey = KeypadButtonAdd(IconPack.Power, Res.string.keyboard_power, Token.Operator.POWER)
    val Power10Key =
      KeypadButtonAdd(
        IconPack.Power10,
        Res.string.keyboard_power_10,
        Token.Digit.DIGIT_1 + Token.Digit.DIGIT_0 + Token.Operator.POWER,
      )
    val RootKey = KeypadButtonAdd(IconPack.Root, Res.string.keyboard_root, Token.Operator.SQRT)
    val RightBracketKey =
      KeypadButtonAdd(
        IconPack.RightBracket,
        Res.string.keyboard_right_bracket,
        Token.Operator.RIGHT_BRACKET,
      )
    val SinKey = KeypadButtonAdd(IconPack.Sin, Res.string.keyboard_sin, Token.Func.SIN_BRACKET)
    val TanKey = KeypadButtonAdd(IconPack.Tan, Res.string.keyboard_tan, Token.Func.TAN_BRACKET)
  }
}

@Composable
fun KeypadButtonTransparent(
  modifier: Modifier,
  button: KeypadButton.KeypadButtonAdd,
  height: Float,
  onClick: (String) -> Unit,
) =
  KeyboardButtonTransparent(
    modifier = modifier,
    icon = button.icon,
    contentDescription = button.description?.let { stringResource(it) },
    contentHeight = height,
  ) {
    onClick(button.token)
  }

@Composable
fun KeypadButtonTransparent(
  modifier: Modifier,
  button: KeypadButton.KeypadButtonSimple,
  height: Float,
  onClick: () -> Unit,
) =
  KeyboardButtonTransparent(
    modifier = modifier,
    icon = button.icon,
    contentDescription = button.description?.let { stringResource(it) },
    contentHeight = height,
    onClick = onClick,
  )

@Composable
fun KeypadButtonTransparent(
  modifier: Modifier,
  button: KeypadButton.KeypadButtonSimple,
  height: Float,
  onLongClick: () -> Unit,
  onClick: () -> Unit,
) =
  KeyboardButtonTransparent(
    modifier = modifier,
    icon = button.icon,
    contentDescription = button.description?.let { stringResource(it) },
    contentHeight = height,
    onLongClick = onLongClick,
    onClick = onClick,
  )

@Composable
fun KeypadButtonTertiary(
  modifier: Modifier,
  button: KeypadButton.KeypadButtonSimple,
  height: Float,
  onClick: () -> Unit,
) =
  KeyboardButtonTertiary(
    modifier = modifier,
    icon = button.icon,
    contentDescription = button.description?.let { stringResource(it) },
    contentHeight = height,
    onClick = onClick,
  )

@Composable
fun KeypadButtonFilled(
  modifier: Modifier,
  button: KeypadButton.KeypadButtonSimple,
  height: Float,
  onClick: () -> Unit,
) =
  KeyboardButtonFilled(
    modifier = modifier,
    icon = button.icon,
    contentDescription = button.description?.let { stringResource(it) },
    contentHeight = height,
    onClick = onClick,
  )

@Composable
fun KeypadButtonFilled(
  modifier: Modifier,
  button: KeypadButton.KeypadButtonAdd,
  height: Float,
  onClick: (String) -> Unit,
) =
  KeyboardButtonFilled(
    modifier = modifier,
    icon = button.icon,
    contentDescription = button.description?.let { stringResource(it) },
    contentHeight = height,
  ) {
    onClick(button.token)
  }

@Composable
fun KeypadButtonFilledPrimary(
  modifier: Modifier,
  button: KeypadButton.KeypadButtonSimple,
  height: Float,
  onClick: () -> Unit,
) =
  KeyboardButtonFilledPrimary(
    modifier = modifier,
    icon = button.icon,
    contentDescription = button.description?.let { stringResource(it) },
    contentHeight = height,
    onClick = onClick,
  )

@Composable
fun KeypadButtonFilledPrimary(
  modifier: Modifier,
  button: KeypadButton.KeypadButtonAdd,
  height: Float,
  onClick: (String) -> Unit,
) =
  KeyboardButtonFilledPrimary(
    modifier = modifier,
    icon = button.icon,
    contentDescription = button.description?.let { stringResource(it) },
    contentHeight = height,
  ) {
    onClick(button.token)
  }

@Composable
fun KeypadButtonLight(
  modifier: Modifier,
  button: KeypadButton.KeypadButtonAdd,
  height: Float,
  onClick: (String) -> Unit,
) =
  KeyboardButtonLight(
    modifier = modifier,
    icon = button.icon,
    contentDescription = button.description?.let { stringResource(it) },
    contentHeight = height,
  ) {
    onClick(button.token)
  }

@Composable
fun KeypadButtonLight(
  modifier: Modifier,
  button: KeypadButton.KeypadButtonSimple,
  height: Float,
  onLongClick: () -> Unit,
  onClick: () -> Unit,
) =
  KeyboardButtonLight(
    modifier = modifier,
    icon = button.icon,
    contentDescription = button.description?.let { stringResource(it) },
    contentHeight = height,
    onLongClick = onLongClick,
    onClick = onClick,
  )
