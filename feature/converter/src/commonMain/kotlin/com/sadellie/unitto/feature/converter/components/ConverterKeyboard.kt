/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2025 Elshan Agaev
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

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
import com.sadellie.unitto.core.ui.KeypadButtonFilled
import com.sadellie.unitto.core.ui.KeypadButtonLight
import com.sadellie.unitto.core.ui.KeypadButtonTertiary
import com.sadellie.unitto.core.ui.KeypadFlow
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.designsystem.LocalWindowSize

@Composable
internal fun DefaultKeyboard(
  modifier: Modifier,
  onAddTokenClick: (String) -> Unit,
  onClearClick: () -> Unit,
  onDeleteClick: () -> Unit,
  onBracketsClick: () -> Unit,
  fractional: String,
  middleZero: Boolean,
  acButton: Boolean,
) {
  val iconHeight: Float =
    if (LocalWindowSize.current.heightSizeClass < WindowHeightSizeClass.Medium)
      KeyboardButtonToken.ICON_HEIGHT_SHORT
    else KeyboardButtonToken.ICON_HEIGHT_TALL

  KeypadFlow(modifier = modifier, rows = 5, columns = 4) { width, height ->
    val bModifier = Modifier.fillMaxWidth(width).fillMaxHeight(height)

    if (acButton) {
      KeypadButtonTertiary(bModifier, ClearKey, iconHeight, onClearClick)
      KeypadButtonFilled(bModifier, BracketsKey, iconHeight, onBracketsClick)
    } else {
      KeypadButtonFilled(bModifier, LeftBracketKey, iconHeight, onAddTokenClick)
      KeypadButtonFilled(bModifier, RightBracketKey, iconHeight, onAddTokenClick)
    }
    KeypadButtonFilled(bModifier, PowerKey, iconHeight, onAddTokenClick)
    KeypadButtonFilled(bModifier, RootKey, iconHeight, onAddTokenClick)

    KeypadButtonLight(bModifier, Key7, iconHeight, onAddTokenClick)
    KeypadButtonLight(bModifier, Key8, iconHeight, onAddTokenClick)
    KeypadButtonLight(bModifier, Key9, iconHeight, onAddTokenClick)
    KeypadButtonFilled(bModifier, DivideKey, iconHeight, onAddTokenClick)

    KeypadButtonLight(bModifier, Key4, iconHeight, onAddTokenClick)
    KeypadButtonLight(bModifier, Key5, iconHeight, onAddTokenClick)
    KeypadButtonLight(bModifier, Key6, iconHeight, onAddTokenClick)
    KeypadButtonFilled(bModifier, MultiplyKey, iconHeight, onAddTokenClick)

    KeypadButtonLight(bModifier, Key1, iconHeight, onAddTokenClick)
    KeypadButtonLight(bModifier, Key2, iconHeight, onAddTokenClick)
    KeypadButtonLight(bModifier, Key3, iconHeight, onAddTokenClick)
    KeypadButtonFilled(bModifier, MinusKey, iconHeight, onAddTokenClick)

    val fractionalKey =
      remember(fractional) { if (fractional == Token.PERIOD) DotKey else CommaKey }
    if (middleZero) {
      KeypadButtonLight(bModifier, fractionalKey, iconHeight, onAddTokenClick)
      KeypadButtonLight(bModifier, Key0, iconHeight, onAddTokenClick)
    } else {
      KeypadButtonLight(bModifier, Key0, iconHeight, onAddTokenClick)
      KeypadButtonLight(bModifier, fractionalKey, iconHeight, onAddTokenClick)
    }
    KeypadButtonLight(bModifier, BackspaceKey, iconHeight, onClearClick, onDeleteClick)
    KeypadButtonFilled(bModifier, PlusKey, iconHeight, onAddTokenClick)
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

  KeypadFlow(modifier = modifier, rows = 6, columns = 3) { width, height ->
    val bModifier = Modifier.fillMaxWidth(width).fillMaxHeight(height)
    val wideButtonModifier = Modifier.fillMaxHeight(height).fillMaxWidth(width * 2)

    KeypadButtonFilled(bModifier, KeyA, iconHeight, onAddTokenClick)
    KeypadButtonFilled(bModifier, KeyB, iconHeight, onAddTokenClick)
    KeypadButtonFilled(bModifier, KeyC, iconHeight, onAddTokenClick)

    KeypadButtonFilled(bModifier, KeyD, iconHeight, onAddTokenClick)
    KeypadButtonFilled(bModifier, KeyE, iconHeight, onAddTokenClick)
    KeypadButtonFilled(bModifier, KeyF, iconHeight, onAddTokenClick)

    KeypadButtonLight(bModifier, Key7, iconHeight, onAddTokenClick)
    KeypadButtonLight(bModifier, Key8, iconHeight, onAddTokenClick)
    KeypadButtonLight(bModifier, Key9, iconHeight, onAddTokenClick)

    KeypadButtonLight(bModifier, Key4, iconHeight, onAddTokenClick)
    KeypadButtonLight(bModifier, Key5, iconHeight, onAddTokenClick)
    KeypadButtonLight(bModifier, Key6, iconHeight, onAddTokenClick)

    KeypadButtonLight(bModifier, Key1, iconHeight, onAddTokenClick)
    KeypadButtonLight(bModifier, Key2, iconHeight, onAddTokenClick)
    KeypadButtonLight(bModifier, Key3, iconHeight, onAddTokenClick)

    // TODO Should be a separate o use custom widthFillFactors and heightFillFactors
    KeypadButtonLight(bModifier, Key0, iconHeight, onAddTokenClick)
    KeypadButtonLight(wideButtonModifier, BackspaceKey, iconHeight, onClearClick, onDeleteClick)
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
    fractional = Token.PERIOD,
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
