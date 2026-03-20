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

package com.sadellie.unitto.feature.calculator.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.designsystem.ExpressivePreview
import com.sadellie.unitto.core.designsystem.LocalWindowSize
import com.sadellie.unitto.core.designsystem.icons.symbols.KeyboardArrowUp
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.ui.ColumnWithConstraints
import com.sadellie.unitto.core.ui.KeyboardButtonToken
import com.sadellie.unitto.core.ui.KeypadButton
import com.sadellie.unitto.core.ui.KeypadButton.Companion.AcTanKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.AngleDegKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.AngleRadKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.ArCosKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.ArSinKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.BackspaceKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.BracketsKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.ClearKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.CommaKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.CosKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.DivideKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.DotKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.EqualKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.EulerKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.ExKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.FactorialKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.InvKey
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
import com.sadellie.unitto.core.ui.KeypadButton.Companion.LeftBracketKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.LnKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.LogKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.MinusKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.ModuloKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.MultiplyKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.PercentKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.PiKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.PlusKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Power10Key
import com.sadellie.unitto.core.ui.KeypadButton.Companion.PowerKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.RightBracketKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.RootKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.SinKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.TanKey
import com.sadellie.unitto.core.ui.KeypadFlow

@Composable
internal fun CalculatorKeyboard(
  modifier: Modifier,
  onAddTokenClick: (String) -> Unit,
  onBracketsClick: () -> Unit,
  onDeleteClick: () -> Unit,
  onClearClick: () -> Unit,
  onEqualClick: () -> Unit,
  radianMode: Boolean,
  onRadianModeClick: (Boolean) -> Unit,
  additionalButtons: Boolean,
  onAdditionalButtonsClick: (Boolean) -> Unit,
  inverseMode: Boolean,
  onInverseModeClick: (Boolean) -> Unit,
  showAcButton: Boolean,
  middleZero: Boolean,
  fractional: Token.Formatter,
) {
  if (LocalWindowSize.current.heightSizeClass < WindowHeightSizeClass.Medium) {
    CompactKeyboard(
      modifier = modifier,
      onAddTokenClick = onAddTokenClick,
      onBracketsClick = onBracketsClick,
      onDeleteClick = onDeleteClick,
      onClearClick = onClearClick,
      onEqualClick = onEqualClick,
      radianMode = radianMode,
      onRadianModeClick = onRadianModeClick,
      inverseMode = inverseMode,
      onInverseModeClick = onInverseModeClick,
      showAcButton = showAcButton,
      middleZero = middleZero,
      fractional = fractional,
    )
  } else {
    ExpandedKeyboard(
      modifier = modifier,
      onAddTokenClick = onAddTokenClick,
      onBracketsClick = onBracketsClick,
      onDeleteClick = onDeleteClick,
      onClearClick = onClearClick,
      onEqualClick = onEqualClick,
      radianMode = radianMode,
      onRadianModeClick = onRadianModeClick,
      additionalButtons = additionalButtons,
      onAdditionalButtonsClick = onAdditionalButtonsClick,
      inverseMode = inverseMode,
      onInverseModeClick = onInverseModeClick,
      showAcButton = showAcButton,
      middleZero = middleZero,
      fractional = fractional,
    )
  }
}

@Composable
private fun ExpandedKeyboard(
  modifier: Modifier,
  onAddTokenClick: (String) -> Unit,
  onBracketsClick: () -> Unit,
  onDeleteClick: () -> Unit,
  onClearClick: () -> Unit,
  onEqualClick: () -> Unit,
  radianMode: Boolean,
  onRadianModeClick: (Boolean) -> Unit,
  additionalButtons: Boolean,
  onAdditionalButtonsClick: (Boolean) -> Unit,
  inverseMode: Boolean,
  onInverseModeClick: (Boolean) -> Unit,
  showAcButton: Boolean,
  middleZero: Boolean,
  fractional: Token.Formatter,
) {
  ColumnWithConstraints(modifier = modifier) { constraints ->
    val spacerHeight = constraints.maxHeight * SPACER_HEIGHT_FACTOR
    val additionalButtonHeight = constraints.maxHeight * EXPANDED_ADDITIONAL_BUTTON_HEIGHT_FACTOR

    Spacer(modifier = Modifier.height(spacerHeight))

    ExpandedAdditionalKeyboard(
      expanded = additionalButtons,
      inverseMode = inverseMode,
      radianMode = radianMode,
      onRadianModeClick = onRadianModeClick,
      onInverseModeClick = onInverseModeClick,
      additionalButtonHeight = additionalButtonHeight,
      onAddTokenClick = onAddTokenClick,
      onAdditionalButtonsClick = onAdditionalButtonsClick,
    )

    Spacer(modifier = Modifier.height(spacerHeight))

    KeypadFlow(
      modifier = Modifier.weight(1f).fillMaxSize(),
      iconHeight = KeyboardButtonToken.ICON_HEIGHT_TALL,
    ) {
      KeypadRow {
        if (showAcButton) {
          ButtonTertiary(ClearKey, onClearClick)
          ButtonFilled(BracketsKey, onBracketsClick)
        } else {
          ButtonFilled(LeftBracketKey, onAddTokenClick)
          ButtonFilled(RightBracketKey, onAddTokenClick)
        }
        ButtonFilled(PercentKey, onAddTokenClick)
        ButtonFilled(DivideKey, onAddTokenClick)
      }

      KeypadRow {
        ButtonLight(Key7, onAddTokenClick)
        ButtonLight(Key8, onAddTokenClick)
        ButtonLight(Key9, onAddTokenClick)
        ButtonFilled(MultiplyKey, onAddTokenClick)
      }

      KeypadRow {
        ButtonLight(Key4, onAddTokenClick)
        ButtonLight(Key5, onAddTokenClick)
        ButtonLight(Key6, onAddTokenClick)
        ButtonFilled(MinusKey, onAddTokenClick)
      }

      KeypadRow {
        ButtonLight(Key1, onAddTokenClick)
        ButtonLight(Key2, onAddTokenClick)
        ButtonLight(Key3, onAddTokenClick)
        ButtonFilled(PlusKey, onAddTokenClick)
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
        ButtonFilledPrimary(EqualKey, onEqualClick)
      }
    }

    Spacer(modifier = Modifier.height(spacerHeight))
  }
}

@Composable
private fun ExpandedAdditionalKeyboard(
  expanded: Boolean,
  inverseMode: Boolean,
  radianMode: Boolean,
  onRadianModeClick: (Boolean) -> Unit,
  onInverseModeClick: (Boolean) -> Unit,
  additionalButtonHeight: Dp,
  onAddTokenClick: (String) -> Unit,
  onAdditionalButtonsClick: (Boolean) -> Unit,
) {
  Crossfade(
    targetState = inverseMode,
    modifier = Modifier.animateContentSize(),
    label = "Additional keyboard state changes",
  ) { isInverse ->
    val iconHeight = KeyboardButtonToken.ICON_HEIGHT_TALL_SECONDARY
    val rows = if (expanded) EXPANDED_ADDITIONAL_ROWS_COUNT else 1
    val angleKey = if (radianMode) AngleRadKey else AngleDegKey

    if (isInverse) {
      Row {
        KeypadFlow(
          modifier = Modifier.height(additionalButtonHeight * rows).fillMaxWidth().weight(1f),
          iconHeight = iconHeight,
        ) {
          KeypadRow {
            ButtonTransparent(ModuloKey, onAddTokenClick)
            ButtonTransparent(PiKey, onAddTokenClick)
            ButtonTransparent(PowerKey, onAddTokenClick)
            ButtonTransparent(FactorialKey, onAddTokenClick)
          }

          if (expanded) {
            KeypadRow {
              ButtonTransparent(angleKey, { onRadianModeClick(!radianMode) })
              ButtonTransparent(ArSinKey, onAddTokenClick)
              ButtonTransparent(ArCosKey, onAddTokenClick)
              ButtonTransparent(AcTanKey, onAddTokenClick)
            }

            KeypadRow {
              ButtonTransparent(InvKey, { onInverseModeClick(!inverseMode) })
              ButtonTransparent(EulerKey, onAddTokenClick)
              ButtonTransparent(ExKey, onAddTokenClick)
              ButtonTransparent(Power10Key, onAddTokenClick)
            }
          }
        }

        ToggleExpandedAdditionalKeysButton(
          modifier = Modifier.size(additionalButtonHeight),
          expanded = expanded,
          onClick = onAdditionalButtonsClick,
        )
      }
    } else {
      Row {
        KeypadFlow(
          modifier = Modifier.height(additionalButtonHeight * rows).fillMaxWidth().weight(1f),
          iconHeight = iconHeight,
        ) {
          KeypadRow {
            ButtonTransparent(RootKey, onAddTokenClick)
            ButtonTransparent(PiKey, onAddTokenClick)
            ButtonTransparent(PowerKey, onAddTokenClick)
            ButtonTransparent(FactorialKey, onAddTokenClick)
          }

          if (expanded) {
            KeypadRow {
              ButtonTransparent(angleKey, { onRadianModeClick(!radianMode) })
              ButtonTransparent(SinKey, onAddTokenClick)
              ButtonTransparent(CosKey, onAddTokenClick)
              ButtonTransparent(TanKey, onAddTokenClick)
            }

            KeypadRow {
              ButtonTransparent(InvKey, { onInverseModeClick(!inverseMode) })
              ButtonTransparent(EulerKey, onAddTokenClick)
              ButtonTransparent(LnKey, onAddTokenClick)
              ButtonTransparent(LogKey, onAddTokenClick)
            }
          }
        }

        ToggleExpandedAdditionalKeysButton(
          modifier = Modifier.size(additionalButtonHeight),
          expanded = expanded,
          onClick = onAdditionalButtonsClick,
        )
      }
    }
  }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ToggleExpandedAdditionalKeysButton(
  modifier: Modifier,
  expanded: Boolean,
  onClick: (Boolean) -> Unit,
) {
  val expandRotation: Float by
    animateFloatAsState(
      targetValue = if (expanded) 0f else 180f,
      animationSpec = MaterialTheme.motionScheme.defaultEffectsSpec(),
      label = "Rotate on expand",
    )
  Box(modifier = modifier, contentAlignment = Alignment.Center) {
    IconButton(
      onClick = { onClick(!expanded) },
      colors =
        IconButtonDefaults.iconButtonColors(
          containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
          contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
      shapes = IconButtonDefaults.shapes(),
      modifier =
        Modifier.size(
          IconButtonDefaults.smallContainerSize(IconButtonDefaults.IconButtonWidthOption.Uniform)
        ),
    ) {
      Icon(
        imageVector = Symbols.KeyboardArrowUp,
        contentDescription = null,
        modifier = Modifier.rotate(expandRotation).size(IconButtonDefaults.mediumIconSize),
      )
    }
  }
}

@Composable
private fun CompactKeyboard(
  modifier: Modifier,
  onAddTokenClick: (String) -> Unit,
  onBracketsClick: () -> Unit,
  onDeleteClick: () -> Unit,
  onClearClick: () -> Unit,
  onEqualClick: () -> Unit,
  radianMode: Boolean,
  onRadianModeClick: (Boolean) -> Unit,
  inverseMode: Boolean,
  onInverseModeClick: (Boolean) -> Unit,
  showAcButton: Boolean,
  middleZero: Boolean,
  fractional: Token.Formatter,
) {
  Crossfade(targetState = inverseMode, label = "Inverse switch", modifier = modifier) { inverse ->
    val angleKey = if (radianMode) AngleRadKey else AngleDegKey
    if (inverse) {
      CompactKeyboardInverse(
        modifier = Modifier.fillMaxSize(),
        onAddTokenClick = onAddTokenClick,
        onBracketsClick = onBracketsClick,
        onDeleteClick = onDeleteClick,
        onClearClick = onClearClick,
        onEqualClick = onEqualClick,
        radianMode = radianMode,
        onRadianModeClick = onRadianModeClick,
        inverseMode = inverseMode,
        onInverseModeClick = onInverseModeClick,
        showAcButton = showAcButton,
        middleZero = middleZero,
        fractional = fractional,
        angleKey = angleKey,
      )
    } else {
      CompactKeyboardDefault(
        modifier = Modifier.fillMaxSize(),
        onAddTokenClick = onAddTokenClick,
        onBracketsClick = onBracketsClick,
        onDeleteClick = onDeleteClick,
        onClearClick = onClearClick,
        onEqualClick = onEqualClick,
        radianMode = radianMode,
        onRadianModeClick = onRadianModeClick,
        inverseMode = inverseMode,
        onInverseModeClick = onInverseModeClick,
        showAcButton = showAcButton,
        middleZero = middleZero,
        fractional = fractional,
        angleKey = angleKey,
      )
    }
  }
}

@Composable
private fun CompactKeyboardInverse(
  modifier: Modifier,
  onAddTokenClick: (String) -> Unit,
  onBracketsClick: () -> Unit,
  onDeleteClick: () -> Unit,
  onClearClick: () -> Unit,
  onEqualClick: () -> Unit,
  radianMode: Boolean,
  onRadianModeClick: (Boolean) -> Unit,
  inverseMode: Boolean,
  onInverseModeClick: (Boolean) -> Unit,
  showAcButton: Boolean,
  middleZero: Boolean,
  fractional: Token.Formatter,
  angleKey: KeypadButton.KeypadButtonSimple,
) {
  KeypadFlow(modifier = modifier, iconHeight = KeyboardButtonToken.ICON_HEIGHT_SHORT) {
    val iconHeightSecondary = KeyboardButtonToken.ICON_HEIGHT_SHORT_SECONDARY

    KeypadRow {
      ButtonTransparent(angleKey, { onRadianModeClick(!radianMode) }, height = iconHeightSecondary)
      ButtonTransparent(ModuloKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonTransparent(PiKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonLight(Key7, onAddTokenClick)
      ButtonLight(Key8, onAddTokenClick)
      ButtonLight(Key9, onAddTokenClick)
      if (showAcButton) {
        ButtonTertiary(ClearKey, onClearClick)
        ButtonFilled(BracketsKey, onBracketsClick)
      } else {
        ButtonFilled(LeftBracketKey, onAddTokenClick)
        ButtonFilled(RightBracketKey, onAddTokenClick)
      }
    }

    KeypadRow {
      ButtonTransparent(InvKey, { onInverseModeClick(!inverseMode) }, height = iconHeightSecondary)
      ButtonTransparent(PowerKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonTransparent(FactorialKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonLight(Key4, onAddTokenClick)
      ButtonLight(Key5, onAddTokenClick)
      ButtonLight(Key6, onAddTokenClick)
      ButtonFilled(MultiplyKey, onAddTokenClick)
      ButtonFilled(DivideKey, onAddTokenClick)
    }

    KeypadRow {
      ButtonTransparent(ArSinKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonTransparent(ArCosKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonTransparent(AcTanKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonLight(Key1, onAddTokenClick)
      ButtonLight(Key2, onAddTokenClick)
      ButtonLight(Key3, onAddTokenClick)
      ButtonFilled(MinusKey, onAddTokenClick)
      ButtonFilled(PercentKey, onAddTokenClick)
    }

    KeypadRow {
      ButtonTransparent(EulerKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonTransparent(ExKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonTransparent(Power10Key, onAddTokenClick, height = iconHeightSecondary)
      val fractionalKey = if (fractional == Token.Period) DotKey else CommaKey
      if (middleZero) {
        ButtonLight(fractionalKey, onAddTokenClick)
        ButtonLight(Key0, onAddTokenClick)
      } else {
        ButtonLight(Key0, onAddTokenClick)
        ButtonLight(fractionalKey, onAddTokenClick)
      }
      ButtonLight(BackspaceKey, onClearClick, onDeleteClick)
      ButtonFilled(PlusKey, onAddTokenClick)
      ButtonFilledPrimary(EqualKey, onEqualClick)
    }
  }
}

@Composable
private fun CompactKeyboardDefault(
  modifier: Modifier,
  onAddTokenClick: (String) -> Unit,
  onBracketsClick: () -> Unit,
  onDeleteClick: () -> Unit,
  onClearClick: () -> Unit,
  onEqualClick: () -> Unit,
  radianMode: Boolean,
  onRadianModeClick: (Boolean) -> Unit,
  inverseMode: Boolean,
  onInverseModeClick: (Boolean) -> Unit,
  showAcButton: Boolean,
  middleZero: Boolean,
  fractional: Token.Formatter,
  angleKey: KeypadButton.KeypadButtonSimple,
) {
  KeypadFlow(modifier = modifier, iconHeight = KeyboardButtonToken.ICON_HEIGHT_SHORT) {
    val iconHeightSecondary = KeyboardButtonToken.ICON_HEIGHT_SHORT_SECONDARY

    KeypadRow {
      ButtonTransparent(angleKey, { onRadianModeClick(!radianMode) }, height = iconHeightSecondary)
      ButtonTransparent(RootKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonTransparent(PiKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonLight(Key7, onAddTokenClick)
      ButtonLight(Key8, onAddTokenClick)
      ButtonLight(Key9, onAddTokenClick)
      if (showAcButton) {
        ButtonTertiary(ClearKey, onClearClick)
        ButtonFilled(BracketsKey, onBracketsClick)
      } else {
        ButtonFilled(LeftBracketKey, onAddTokenClick)
        ButtonFilled(RightBracketKey, onAddTokenClick)
      }
    }

    KeypadRow {
      ButtonTransparent(InvKey, { onInverseModeClick(!inverseMode) }, height = iconHeightSecondary)
      ButtonTransparent(PowerKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonTransparent(FactorialKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonLight(Key4, onAddTokenClick)
      ButtonLight(Key5, onAddTokenClick)
      ButtonLight(Key6, onAddTokenClick)
      ButtonFilled(MultiplyKey, onAddTokenClick)
      ButtonFilled(DivideKey, onAddTokenClick)
    }

    KeypadRow {
      ButtonTransparent(SinKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonTransparent(CosKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonTransparent(TanKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonLight(Key1, onAddTokenClick)
      ButtonLight(Key2, onAddTokenClick)
      ButtonLight(Key3, onAddTokenClick)
      ButtonFilled(MinusKey, onAddTokenClick)
      ButtonFilled(PercentKey, onAddTokenClick)
    }

    KeypadRow {
      ButtonTransparent(EulerKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonTransparent(LnKey, onAddTokenClick, height = iconHeightSecondary)
      ButtonTransparent(LogKey, onAddTokenClick, height = iconHeightSecondary)
      val fractionalKey = if (fractional == Token.Period) DotKey else CommaKey
      if (middleZero) {
        ButtonLight(fractionalKey, onAddTokenClick)
        ButtonLight(Key0, onAddTokenClick)
      } else {
        ButtonLight(Key0, onAddTokenClick)
        ButtonLight(fractionalKey, onAddTokenClick)
      }
      ButtonLight(BackspaceKey, onClearClick, onDeleteClick)
      ButtonFilled(PlusKey, onAddTokenClick)
      ButtonFilledPrimary(EqualKey, onEqualClick)
    }
  }
}

private const val SPACER_HEIGHT_FACTOR = 0.025f
private const val EXPANDED_ADDITIONAL_BUTTON_HEIGHT_FACTOR = 0.09f
private const val EXPANDED_ADDITIONAL_ROWS_COUNT = 3

@Preview(
  device = "spec:width=400dp,height=600dp,dpi=440",
  showSystemUi = false,
  showBackground = false,
)
@Composable
private fun PreviewExpandedKeyboard() = ExpressivePreview {
  var additionalButtons by remember { mutableStateOf(false) }
  ExpandedKeyboard(
    modifier = Modifier.fillMaxHeight(),
    onAddTokenClick = {},
    onBracketsClick = {},
    onDeleteClick = {},
    onClearClick = {},
    onEqualClick = {},
    radianMode = true,
    onRadianModeClick = {},
    additionalButtons = additionalButtons,
    onAdditionalButtonsClick = { additionalButtons = it },
    inverseMode = false,
    onInverseModeClick = {},
    showAcButton = true,
    middleZero = false,
    fractional = Token.Period,
  )
}

@Preview(
  device = "spec:width=600dp,height=300dp,dpi=440",
  showSystemUi = false,
  showBackground = false,
)
@Composable
private fun PreviewCompactKeyboard() = ExpressivePreview {
  var inverseMode by remember { mutableStateOf(false) }
  CompactKeyboard(
    modifier = Modifier.fillMaxHeight(),
    onAddTokenClick = {},
    onBracketsClick = {},
    onDeleteClick = {},
    onClearClick = {},
    onEqualClick = {},
    radianMode = true,
    onRadianModeClick = {},
    inverseMode = inverseMode,
    onInverseModeClick = { inverseMode = it },
    showAcButton = true,
    middleZero = false,
    fractional = Token.Period,
  )
}
