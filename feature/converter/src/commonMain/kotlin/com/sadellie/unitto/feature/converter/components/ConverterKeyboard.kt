/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2026 Elshan Agaev
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

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.designsystem.LocalWindowSize
import com.sadellie.unitto.core.ui.KeyboardButtonToken
import com.sadellie.unitto.core.ui.KeypadButton.Companion.BackspaceKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.BracketsKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.ClearKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.CommaKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.DivideKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.DotKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key0
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key1
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key2
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key3
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key4
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key5
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key6
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key7
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key8
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key9
import com.sadellie.unitto.core.ui.KeypadButton.Companion.KeyA
import com.sadellie.unitto.core.ui.KeypadButton.Companion.KeyB
import com.sadellie.unitto.core.ui.KeypadButton.Companion.KeyC
import com.sadellie.unitto.core.ui.KeypadButton.Companion.KeyD
import com.sadellie.unitto.core.ui.KeypadButton.Companion.KeyE
import com.sadellie.unitto.core.ui.KeypadButton.Companion.KeyF
import com.sadellie.unitto.core.ui.KeypadButton.Companion.LeftBracketKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.MinusKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.MultiplyKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.PlusKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.PowerKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.RightBracketKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.RootKey
import com.sadellie.unitto.core.ui.KeypadFlow
import com.sadellie.unitto.core.ui.KeypadFlowScope

@Composable
internal fun DefaultKeyboard(
  modifier: Modifier,
  onAddTokenClick: (String) -> Unit,
  onClearClick: () -> Unit,
  onDeleteClick: () -> Unit,
  onBracketsClick: () -> Unit,
  fractional: Token.Formatter,
  middleZero: Boolean,
  acButton: Boolean,
  topToBottom: Boolean,
) {
  val iconHeight: Float =
    if (LocalWindowSize.current.heightSizeClass < WindowHeightSizeClass.Medium)
      KeyboardButtonToken.ICON_HEIGHT_SHORT
    else KeyboardButtonToken.ICON_HEIGHT_TALL

  KeypadFlow(modifier = modifier, iconHeight = iconHeight) {
    KeypadRow {
      if (acButton) {
        ButtonTertiary(ClearKey, onClearClick)
        ButtonFilled(BracketsKey, onBracketsClick)
      } else {
        ButtonFilled(LeftBracketKey, onAddTokenClick)
        ButtonFilled(RightBracketKey, onAddTokenClick)
      }
      ButtonFilled(PowerKey, onAddTokenClick)
      ButtonFilled(RootKey, onAddTokenClick)
    }

    KeypadRow {
      if (topToBottom) Numbers123(onAddTokenClick, this) else Numbers789(onAddTokenClick, this)
      ButtonFilled(DivideKey, onAddTokenClick)
    }

    KeypadRow {
      Numbers456(onAddTokenClick, this)
      ButtonFilled(MultiplyKey, onAddTokenClick)
    }

    KeypadRow {
      if (topToBottom) Numbers789(onAddTokenClick, this) else Numbers123(onAddTokenClick, this)
      ButtonFilled(MinusKey, onAddTokenClick)
    }

    KeypadRow {
      val fractionalKey =
        remember(fractional) { if (fractional == Token.Period) DotKey else CommaKey }
      if (middleZero) {
        ButtonLight(fractionalKey, onAddTokenClick)
        ButtonLight(Key0, onAddTokenClick)
      } else {
        ButtonLight(Key0, onAddTokenClick)
        ButtonLight(fractionalKey, onAddTokenClick)
      }
      ButtonLight(BackspaceKey, onClearClick, onDeleteClick)
      ButtonFilled(PlusKey, onAddTokenClick)
    }
  }
}

@Composable
internal fun NumberBaseKeyboard(
  modifier: Modifier,
  onAddTokenClick: (String) -> Unit,
  onClearClick: () -> Unit,
  onDeleteClick: () -> Unit,
  topToBottom: Boolean,
) {
  val iconHeight: Float =
    if (LocalWindowSize.current.heightSizeClass < WindowHeightSizeClass.Medium)
      KeyboardButtonToken.ICON_HEIGHT_SHORT
    else KeyboardButtonToken.ICON_HEIGHT_TALL

  KeypadFlow(modifier = modifier, iconHeight = iconHeight) {
    KeypadRow {
      NumbersABC(onAddTokenClick, this)
    }

    KeypadRow {
      NumbersDEF(onAddTokenClick, this)
    }

    KeypadRow {
      if (topToBottom) Numbers123(onAddTokenClick, this) else Numbers789(onAddTokenClick, this)
    }

    KeypadRow {
      Numbers456(onAddTokenClick, this)
    }

    KeypadRow {
      if (topToBottom) Numbers789(onAddTokenClick, this) else Numbers123(onAddTokenClick, this)
    }

    KeypadRow {
      ButtonLight(Key0, onAddTokenClick)
      ButtonLight(BackspaceKey, onClearClick, onDeleteClick, weight = 2f)
    }
  }
}

@Composable
private fun KeypadFlowScope.Numbers123(onAddTokenClick: (String) -> Unit, rowScope: RowScope) =
  with(rowScope) {
    ButtonLight(Key1, onAddTokenClick)
    ButtonLight(Key2, onAddTokenClick)
    ButtonLight(Key3, onAddTokenClick)
  }

@Composable
private fun KeypadFlowScope.Numbers456(onAddTokenClick: (String) -> Unit, rowScope: RowScope) =
  with(rowScope) {
    ButtonLight(Key4, onAddTokenClick)
    ButtonLight(Key5, onAddTokenClick)
    ButtonLight(Key6, onAddTokenClick)
  }

@Composable
private fun KeypadFlowScope.Numbers789(onAddTokenClick: (String) -> Unit, rowScope: RowScope) =
  with(rowScope) {
    ButtonLight(Key7, onAddTokenClick)
    ButtonLight(Key8, onAddTokenClick)
    ButtonLight(Key9, onAddTokenClick)
  }

@Composable
private fun KeypadFlowScope.NumbersABC(onAddTokenClick: (String) -> Unit, rowScope: RowScope) =
  with(rowScope) {
    ButtonLight(KeyA, onAddTokenClick)
    ButtonLight(KeyB, onAddTokenClick)
    ButtonLight(KeyC, onAddTokenClick)
  }

@Composable
private fun KeypadFlowScope.NumbersDEF(onAddTokenClick: (String) -> Unit, rowScope: RowScope) =
  with(rowScope) {
    ButtonLight(KeyD, onAddTokenClick)
    ButtonLight(KeyE, onAddTokenClick)
    ButtonLight(KeyF, onAddTokenClick)
  }

@Preview
@Composable
private fun PreviewConverterKeyboard() {
  DefaultKeyboard(
    modifier = Modifier.fillMaxSize(),
    onAddTokenClick = {},
    onClearClick = {},
    onDeleteClick = {},
    onBracketsClick = {},
    fractional = Token.Period,
    middleZero = false,
    acButton = true,
    topToBottom = false,
  )
}

@Preview
@Composable
private fun PreviewConverterKeyboardNumberBase() {
  NumberBaseKeyboard(
    modifier = Modifier.fillMaxSize(),
    onAddTokenClick = {},
    onClearClick = {},
    onDeleteClick = {},
    topToBottom = false,
  )
}
