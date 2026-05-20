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
      ButtonLight(Key7, onAddTokenClick)
      ButtonLight(Key8, onAddTokenClick)
      ButtonLight(Key9, onAddTokenClick)
      ButtonFilled(DivideKey, onAddTokenClick)
    }

    KeypadRow {
      ButtonLight(Key4, onAddTokenClick)
      ButtonLight(Key5, onAddTokenClick)
      ButtonLight(Key6, onAddTokenClick)
      ButtonFilled(MultiplyKey, onAddTokenClick)
    }

    KeypadRow {
      ButtonLight(Key1, onAddTokenClick)
      ButtonLight(Key2, onAddTokenClick)
      ButtonLight(Key3, onAddTokenClick)
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
) {
  val iconHeight: Float =
    if (LocalWindowSize.current.heightSizeClass < WindowHeightSizeClass.Medium)
      KeyboardButtonToken.ICON_HEIGHT_SHORT
    else KeyboardButtonToken.ICON_HEIGHT_TALL

  KeypadFlow(modifier = modifier, iconHeight = iconHeight) {
    KeypadRow {
      ButtonFilled(KeyA, onAddTokenClick)
      ButtonFilled(KeyB, onAddTokenClick)
      ButtonFilled(KeyC, onAddTokenClick)
    }

    KeypadRow {
      ButtonFilled(KeyD, onAddTokenClick)
      ButtonFilled(KeyE, onAddTokenClick)
      ButtonFilled(KeyF, onAddTokenClick)
    }

    KeypadRow {
      ButtonLight(Key7, onAddTokenClick)
      ButtonLight(Key8, onAddTokenClick)
      ButtonLight(Key9, onAddTokenClick)
    }

    KeypadRow {
      ButtonLight(Key4, onAddTokenClick)
      ButtonLight(Key5, onAddTokenClick)
      ButtonLight(Key6, onAddTokenClick)
    }

    KeypadRow {
      ButtonLight(Key1, onAddTokenClick)
      ButtonLight(Key2, onAddTokenClick)
      ButtonLight(Key3, onAddTokenClick)
    }

    KeypadRow {
      ButtonLight(Key0, onAddTokenClick)
      ButtonLight(BackspaceKey, onClearClick, onDeleteClick, weight = 2f)
    }
  }
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
  )
}
