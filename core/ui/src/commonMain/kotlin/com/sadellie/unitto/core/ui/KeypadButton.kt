/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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
    val Key0 = KeypadButtonAdd(IconPack.Key0, null, Token.Digit0.symbol)
    val Key1 = KeypadButtonAdd(IconPack.Key1, null, Token.Digit1.symbol)
    val Key2 = KeypadButtonAdd(IconPack.Key2, null, Token.Digit2.symbol)
    val Key3 = KeypadButtonAdd(IconPack.Key3, null, Token.Digit3.symbol)
    val Key4 = KeypadButtonAdd(IconPack.Key4, null, Token.Digit4.symbol)
    val Key5 = KeypadButtonAdd(IconPack.Key5, null, Token.Digit5.symbol)
    val Key6 = KeypadButtonAdd(IconPack.Key6, null, Token.Digit6.symbol)
    val Key7 = KeypadButtonAdd(IconPack.Key7, null, Token.Digit7.symbol)
    val Key8 = KeypadButtonAdd(IconPack.Key8, null, Token.Digit8.symbol)
    val Key9 = KeypadButtonAdd(IconPack.Key9, null, Token.Digit9.symbol)

    val KeyA = KeypadButtonAdd(IconPack.KeyA, null, Token.LetterA.symbol)
    val KeyB = KeypadButtonAdd(IconPack.KeyB, null, Token.LetterB.symbol)
    val KeyC = KeypadButtonAdd(IconPack.KeyC, null, Token.LetterC.symbol)
    val KeyD = KeypadButtonAdd(IconPack.KeyD, null, Token.LetterD.symbol)
    val KeyE = KeypadButtonAdd(IconPack.KeyE, null, Token.LetterE.symbol)
    val KeyF = KeypadButtonAdd(IconPack.KeyF, null, Token.LetterF.symbol)

    val AngleRadKey = KeypadButtonSimple(IconPack.Rad, Res.string.keyboard_radian)
    val AngleDegKey = KeypadButtonSimple(IconPack.Deg, Res.string.keyboard_degree)
    val BackspaceKey = KeypadButtonSimple(IconPack.Backspace, Res.string.common_delete)
    val BracketsKey = KeypadButtonSimple(IconPack.Brackets, Res.string.keyboard_brackets)
    val ClearKey = KeypadButtonSimple(IconPack.Clear, Res.string.common_clear)
    val EqualKey = KeypadButtonSimple(IconPack.Equal, Res.string.keyboard_equal)
    val InvKey = KeypadButtonSimple(IconPack.Inv, Res.string.keyboard_inverse)

    val AcTanKey =
      KeypadButtonAdd(IconPack.AcTan, Res.string.keyboard_actan, Token.ArTan.WithBracket.symbol)
    val ArCosKey =
      KeypadButtonAdd(IconPack.ArCos, Res.string.keyboard_arcos, Token.ArCos.WithBracket.symbol)
    val ArSinKey =
      KeypadButtonAdd(IconPack.ArSin, Res.string.keyboard_arsin, Token.ArSin.WithBracket.symbol)
    val CommaKey = KeypadButtonAdd(IconPack.Comma, Res.string.common_comma, Token.Dot.symbol)
    val CosKey =
      KeypadButtonAdd(IconPack.Cos, Res.string.keyboard_cos, Token.Cos.WithBracket.symbol)
    val DotKey = KeypadButtonAdd(IconPack.Dot, Res.string.keyboard_dot, Token.Dot.symbol)
    val DivideKey =
      KeypadButtonAdd(IconPack.Divide, Res.string.keyboard_divide, Token.Divide.symbol)
    val EulerKey = KeypadButtonAdd(IconPack.Euler, Res.string.keyboard_euler, Token.E.symbol)
    val ExKey = KeypadButtonAdd(IconPack.Ex, Res.string.keyboard_exp, Token.Exp.WithBracket.symbol)
    val FactorialKey =
      KeypadButtonAdd(IconPack.Factorial, Res.string.keyboard_factorial, Token.Factorial.symbol)
    val MinusKey = KeypadButtonAdd(IconPack.Minus, Res.string.keyboard_minus, Token.Minus.symbol)
    val ModuloKey =
      KeypadButtonAdd(IconPack.Modulo, Res.string.keyboard_modulo, Token.Modulo.symbol)
    val MultiplyKey =
      KeypadButtonAdd(IconPack.Multiply, Res.string.keyboard_multiply, Token.Multiply.symbol)
    val LeftBracketKey =
      KeypadButtonAdd(
        IconPack.LeftBracket,
        Res.string.keyboard_left_bracket,
        Token.LeftBracket.symbol,
      )
    val LnKey = KeypadButtonAdd(IconPack.Ln, Res.string.keyboard_ln, Token.Ln.WithBracket.symbol)
    val LogKey =
      KeypadButtonAdd(IconPack.Log, Res.string.keyboard_log, Token.Log.WithBracket.symbol)
    val PercentKey =
      KeypadButtonAdd(IconPack.Percent, Res.string.keyboard_percent, Token.Percent.symbol)
    val PlusKey = KeypadButtonAdd(IconPack.Plus, Res.string.keyboard_plus, Token.Plus.symbol)
    val PiKey = KeypadButtonAdd(IconPack.Pi, Res.string.keyboard_pi, Token.Pi.symbol)
    val PowerKey = KeypadButtonAdd(IconPack.Power, Res.string.keyboard_power, Token.Power.symbol)
    val Power10Key =
      KeypadButtonAdd(
        IconPack.Power10,
        Res.string.keyboard_power_10,
        Token.Digit1.symbol + Token.Digit0.symbol + Token.Power.symbol,
      )
    val RootKey = KeypadButtonAdd(IconPack.Root, Res.string.keyboard_root, Token.Sqrt.symbol)
    val RightBracketKey =
      KeypadButtonAdd(
        IconPack.RightBracket,
        Res.string.keyboard_right_bracket,
        Token.RightBracket.symbol,
      )
    val SinKey =
      KeypadButtonAdd(IconPack.Sin, Res.string.keyboard_sin, Token.Sin.WithBracket.symbol)
    val TanKey =
      KeypadButtonAdd(IconPack.Tan, Res.string.keyboard_tan, Token.Tan.WithBracket.symbol)
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
