/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
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

package com.sadellie.unitto.feature.programmer

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.designsystem.ExpressivePreview
import com.sadellie.unitto.core.designsystem.LocalWindowSize
import com.sadellie.unitto.core.designsystem.icons.iconpack.And
import com.sadellie.unitto.core.designsystem.icons.iconpack.Base
import com.sadellie.unitto.core.designsystem.icons.iconpack.IconPack
import com.sadellie.unitto.core.designsystem.icons.iconpack.Lsh
import com.sadellie.unitto.core.designsystem.icons.iconpack.Mod
import com.sadellie.unitto.core.designsystem.icons.iconpack.Nand
import com.sadellie.unitto.core.designsystem.icons.iconpack.Nor
import com.sadellie.unitto.core.designsystem.icons.iconpack.Not
import com.sadellie.unitto.core.designsystem.icons.iconpack.Or
import com.sadellie.unitto.core.designsystem.icons.iconpack.RoL
import com.sadellie.unitto.core.designsystem.icons.iconpack.RoR
import com.sadellie.unitto.core.designsystem.icons.iconpack.Rsh
import com.sadellie.unitto.core.designsystem.icons.iconpack.Shift
import com.sadellie.unitto.core.designsystem.icons.iconpack.Size
import com.sadellie.unitto.core.designsystem.icons.iconpack.Xor
import com.sadellie.unitto.core.model.programmer.ShiftType
import com.sadellie.unitto.core.ui.KeyboardButtonToken
import com.sadellie.unitto.core.ui.KeypadButton
import com.sadellie.unitto.core.ui.KeypadButton.Companion.BackspaceKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.BracketsKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.ClearKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.DivideKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.EqualKey
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
import com.sadellie.unitto.core.ui.KeypadButton.Companion.RightBracketKey
import com.sadellie.unitto.core.ui.KeypadFlow

@Composable
internal fun ProgrammerKeyboard(
  modifier: Modifier,
  showAcButton: Boolean,
  onClearClick: () -> Unit,
  onBracketsClick: () -> Unit,
  onAddTokenClick: (String) -> Unit,
  onDeleteClick: () -> Unit,
  onEqualClick: () -> Unit,
  middleZero: Boolean,
  toggleSize: () -> Unit,
  toggleBase: () -> Unit,
  toggleShiftType: () -> Unit,
  base: Int,
  shiftType: ShiftType,
) {
  if (LocalWindowSize.current.widthSizeClass == WindowWidthSizeClass.Compact) {
    ProgrammerKeyboardCompact(
      modifier = modifier,
      showAcButton = showAcButton,
      onClearClick = onClearClick,
      onBracketsClick = onBracketsClick,
      onAddTokenClick = onAddTokenClick,
      onDeleteClick = onDeleteClick,
      onEqualClick = onEqualClick,
      middleZero = middleZero,
      toggleSize = toggleSize,
      toggleBase = toggleBase,
      toggleShiftType = toggleShiftType,
      base = base,
      shiftType = shiftType,
    )
  } else {
    ProgrammerKeyboardExpanded(
      modifier = modifier,
      showAcButton = showAcButton,
      onClearClick = onClearClick,
      onBracketsClick = onBracketsClick,
      onAddTokenClick = onAddTokenClick,
      onDeleteClick = onDeleteClick,
      onEqualClick = onEqualClick,
      middleZero = middleZero,
      toggleSize = toggleSize,
      toggleBase = toggleBase,
      toggleShiftType = toggleShiftType,
      base = base,
      shiftType = shiftType,
    )
  }
}

@Composable
private fun ProgrammerKeyboardCompact(
  modifier: Modifier,
  showAcButton: Boolean,
  onClearClick: () -> Unit,
  onBracketsClick: () -> Unit,
  onAddTokenClick: (String) -> Unit,
  onDeleteClick: () -> Unit,
  onEqualClick: () -> Unit,
  middleZero: Boolean,
  toggleSize: () -> Unit,
  toggleBase: () -> Unit,
  toggleShiftType: () -> Unit,
  base: Int,
  shiftType: ShiftType,
) {
  KeypadFlow(modifier = modifier, iconHeight = KeyboardButtonToken.ICON_HEIGHT_TALL) {
    KeypadRow {
      ButtonTransparent(KeyOr, onAddTokenClick)
      ButtonTransparent(KeyAnd, onAddTokenClick)
      ButtonTransparent(KeyNot, onAddTokenClick)
      ButtonTransparent(KeyMod, onAddTokenClick)
    }

    KeypadRow {
      ButtonTransparent(KeyNor, onAddTokenClick)
      ButtonTransparent(KeyNand, onAddTokenClick)
      ButtonTransparent(KeyXor, onAddTokenClick)
      ButtonTransparent(KeySize, toggleSize)
    }

    KeypadRow {
      if (showAcButton) {
        ButtonTertiary(ClearKey, onClearClick)
        ButtonFilled(BracketsKey, onBracketsClick)
      } else {
        ButtonFilled(LeftBracketKey, onAddTokenClick)
        ButtonFilled(RightBracketKey, onAddTokenClick)
      }
      when (shiftType) {
        ShiftType.SHIFT -> {
          ButtonFilled(KeyLsh, onAddTokenClick)
          ButtonFilled(KeyRsh, onAddTokenClick)
        }
        ShiftType.ROTATE -> {
          ButtonFilled(KeyRoL, onAddTokenClick)
          ButtonFilled(KeyRoR, onAddTokenClick)
        }
      }
    }

    KeypadRow {
      ButtonLight(KeyD, onAddTokenClick, base >= 14)
      ButtonLight(KeyE, onAddTokenClick, base >= 15)
      ButtonLight(KeyF, onAddTokenClick, base >= 16)
      ButtonFilled(KeyShiftType, toggleShiftType)
    }

    KeypadRow {
      ButtonLight(KeyA, onAddTokenClick, base >= 11)
      ButtonLight(KeyB, onAddTokenClick, base >= 12)
      ButtonLight(KeyC, onAddTokenClick, base >= 13)
      ButtonFilled(DivideKey, onAddTokenClick)
    }

    KeypadRow {
      ButtonLight(Key7, onAddTokenClick, base >= 8)
      ButtonLight(Key8, onAddTokenClick, base >= 9)
      ButtonLight(Key9, onAddTokenClick, base >= 10)
      ButtonFilled(MultiplyKey, onAddTokenClick)
    }

    KeypadRow {
      ButtonLight(Key4, onAddTokenClick, base >= 5)
      ButtonLight(Key5, onAddTokenClick, base >= 6)
      ButtonLight(Key6, onAddTokenClick, base >= 7)
      ButtonFilled(MinusKey, onAddTokenClick)
    }

    KeypadRow {
      ButtonLight(Key1, onAddTokenClick, base >= 2)
      ButtonLight(Key2, onAddTokenClick, base >= 3)
      ButtonLight(Key3, onAddTokenClick, base >= 4)
      ButtonFilled(PlusKey, onAddTokenClick)
    }

    KeypadRow {
      if (middleZero) {
        ButtonLight(KeyBaseSwitch, null, toggleBase)
        ButtonLight(Key0, onAddTokenClick)
      } else {
        ButtonLight(Key0, onAddTokenClick)
        ButtonLight(KeyBaseSwitch, null, toggleBase)
      }
      ButtonLight(BackspaceKey, onClearClick, onDeleteClick)
      ButtonFilledPrimary(EqualKey, onEqualClick)
    }
  }
}

@Composable
private fun ProgrammerKeyboardExpanded(
  modifier: Modifier,
  showAcButton: Boolean,
  onClearClick: () -> Unit,
  onBracketsClick: () -> Unit,
  onAddTokenClick: (String) -> Unit,
  onDeleteClick: () -> Unit,
  onEqualClick: () -> Unit,
  middleZero: Boolean,
  toggleSize: () -> Unit,
  toggleBase: () -> Unit,
  toggleShiftType: () -> Unit,
  base: Int,
  shiftType: ShiftType,
) {
  KeypadFlow(modifier = modifier) {
    KeypadRow {
      ButtonTransparent(KeyNot, onAddTokenClick)
      ButtonLight(KeyD, onAddTokenClick, base >= 14)
      ButtonLight(KeyE, onAddTokenClick, base >= 15)
      ButtonLight(KeyF, onAddTokenClick, base >= 16)
      if (showAcButton) {
        ButtonTertiary(ClearKey, onClearClick)
        ButtonFilled(BracketsKey, onBracketsClick)
      } else {
        ButtonFilled(LeftBracketKey, onAddTokenClick)
        ButtonFilled(RightBracketKey, onAddTokenClick)
      }
    }

    KeypadRow {
      ButtonTransparent(KeyNand, onAddTokenClick)
      ButtonLight(KeyA, onAddTokenClick, base >= 11)
      ButtonLight(KeyB, onAddTokenClick, base >= 12)
      ButtonLight(KeyC, onAddTokenClick, base >= 13)
      ButtonFilled(KeyMod, onAddTokenClick)
      ButtonFilled(KeySize, toggleSize)
    }

    KeypadRow {
      ButtonTransparent(KeyAnd, onAddTokenClick)
      ButtonLight(Key7, onAddTokenClick, base >= 8)
      ButtonLight(Key8, onAddTokenClick, base >= 9)
      ButtonLight(Key9, onAddTokenClick, base >= 10)
      when (shiftType) {
        ShiftType.SHIFT -> {
          ButtonFilled(KeyLsh, onAddTokenClick)
          ButtonFilled(KeyRsh, onAddTokenClick)
        }
        ShiftType.ROTATE -> {
          ButtonFilled(KeyRoL, onAddTokenClick)
          ButtonFilled(KeyRoR, onAddTokenClick)
        }
      }
    }

    KeypadRow {
      ButtonTransparent(KeyNor, onAddTokenClick)
      ButtonLight(Key4, onAddTokenClick, base >= 5)
      ButtonLight(Key5, onAddTokenClick, base >= 6)
      ButtonLight(Key6, onAddTokenClick, base >= 7)
      ButtonFilled(MultiplyKey, onAddTokenClick)
      ButtonFilled(KeyShiftType, toggleShiftType)
    }
    KeypadRow {
      ButtonTransparent(KeyXor, onAddTokenClick)
      ButtonLight(Key1, onAddTokenClick, base >= 2)
      ButtonLight(Key2, onAddTokenClick, base >= 3)
      ButtonLight(Key3, onAddTokenClick, base >= 4)
      ButtonFilled(MinusKey, onAddTokenClick)
      ButtonFilled(DivideKey, onAddTokenClick)
    }

    KeypadRow {
      ButtonTransparent(KeyOr, onAddTokenClick)
      if (middleZero) {
        ButtonLight(KeyBaseSwitch, null, toggleBase)
        ButtonLight(Key0, onAddTokenClick)
      } else {
        ButtonLight(Key0, onAddTokenClick)
        ButtonLight(KeyBaseSwitch, null, toggleBase)
      }
      ButtonLight(BackspaceKey, onClearClick, onDeleteClick)
      ButtonFilled(PlusKey, onAddTokenClick)
      ButtonFilledPrimary(EqualKey, onEqualClick)
    }
  }
}

// TODO image descriptions
private val KeyOr = KeypadButton.KeypadButtonAdd(IconPack.Or, null, Token.Or.symbol)
private val KeyAnd = KeypadButton.KeypadButtonAdd(IconPack.And, null, Token.And.symbol)
private val KeyNot = KeypadButton.KeypadButtonAdd(IconPack.Not, null, Token.Not.symbol)
private val KeyNand = KeypadButton.KeypadButtonAdd(IconPack.Nand, null, Token.Nand.symbol)
private val KeyNor = KeypadButton.KeypadButtonAdd(IconPack.Nor, null, Token.Nor.symbol)
private val KeyXor = KeypadButton.KeypadButtonAdd(IconPack.Xor, null, Token.Xor.symbol)
private val KeyMod = KeypadButton.KeypadButtonAdd(IconPack.Mod, null, Token.Mod.symbol)
private val KeyLsh = KeypadButton.KeypadButtonAdd(IconPack.Lsh, null, Token.Lsh.symbol)
private val KeyRsh = KeypadButton.KeypadButtonAdd(IconPack.Rsh, null, Token.Rsh.symbol)
private val KeyRoL = KeypadButton.KeypadButtonAdd(IconPack.RoL, null, Token.RoL.symbol)
private val KeyRoR = KeypadButton.KeypadButtonAdd(IconPack.RoR, null, Token.RoR.symbol)
private val KeyBaseSwitch = KeypadButton.KeypadButtonSimple(IconPack.Base, null)
private val KeyShiftType = KeypadButton.KeypadButtonSimple(IconPack.Shift, null)
private val KeySize = KeypadButton.KeypadButtonSimple(IconPack.Size, null)

@Composable
@Preview
private fun PreviewProgrammerKeyboardCompact() = ExpressivePreview {
  ProgrammerKeyboardCompact(
    modifier = Modifier.aspectRatio(0.5f).width(400.dp),
    showAcButton = true,
    onClearClick = {},
    onBracketsClick = {},
    onAddTokenClick = {},
    onDeleteClick = {},
    onEqualClick = {},
    middleZero = false,
    toggleSize = {},
    toggleBase = {},
    toggleShiftType = {},
    base = 16,
    shiftType = ShiftType.SHIFT,
  )
}

@Composable
@Preview
private fun PreviewProgrammerKeyboardExpanded() = ExpressivePreview {
  ProgrammerKeyboardExpanded(
    modifier = Modifier.aspectRatio(2f).width(400.dp),
    showAcButton = true,
    onClearClick = {},
    onBracketsClick = {},
    onAddTokenClick = {},
    onDeleteClick = {},
    onEqualClick = {},
    middleZero = false,
    toggleSize = {},
    toggleBase = {},
    toggleShiftType = {},
    base = 16,
    shiftType = ShiftType.SHIFT,
  )
}
