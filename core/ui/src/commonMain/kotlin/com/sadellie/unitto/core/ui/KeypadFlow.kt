/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2025 Elshan Agaev
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

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import org.jetbrains.compose.resources.stringResource

/** @param paddingFraction Button padding calculated from container size */
@Composable
fun KeypadFlow(
  modifier: Modifier,
  iconHeight: Float = 1f,
  paddingFraction: (width: Dp, height: Dp) -> PaddingValues = { width, height ->
    PaddingValues(width * DEFAULT_PADDING_FRACTION, height * DEFAULT_PADDING_FRACTION)
  },
  content: @Composable KeypadFlowScope.() -> Unit,
) {
  ColumnWithConstraints(modifier = modifier) {
    val scope =
      remember(it.maxWidth, it.maxHeight) {
        KeypadFlowScopeImpl(
          rowModifier = Modifier.weight(1f),
          buttonModifier =
            Modifier.fillMaxHeight().padding(paddingFraction(it.maxWidth, it.maxHeight)),
          iconHeight = iconHeight,
        )
      }
    scope.content()
  }
}

internal data class KeypadFlowScopeImpl(
  val rowModifier: Modifier,
  val buttonModifier: Modifier,
  override val iconHeight: Float,
) : KeypadFlowScope {
  @Composable
  override fun KeypadRow(content: @Composable (RowScope.() -> Unit)) {
    Row(modifier = rowModifier, content = content)
  }

  @Composable
  override fun RowScope.ButtonTransparent(
    button: KeypadButton.KeypadButtonAdd,
    onClick: (String) -> Unit,
    enabled: Boolean,
    height: Float,
    weight: Float,
  ) =
    BasicKeyboardButton(
      modifier = buttonModifier,
      contentHeight = height,
      enabled = enabled,
      defaultWeight = weight,
      onClick = { onClick(button.token) },
      onLongClick = null,
      colors = buttonTransparentColors(),
      icon = button.icon,
      contentDescription = button.description?.let { stringResource(it) },
    )

  @Composable
  override fun RowScope.ButtonTransparent(
    button: KeypadButton.KeypadButtonSimple,
    onClick: () -> Unit,
    enabled: Boolean,
    height: Float,
    weight: Float,
  ) =
    BasicKeyboardButton(
      modifier = buttonModifier,
      contentHeight = height,
      enabled = enabled,
      defaultWeight = weight,
      onClick = onClick,
      onLongClick = null,
      colors = buttonTransparentColors(),
      icon = button.icon,
      contentDescription = button.description?.let { stringResource(it) },
    )

  @Composable
  override fun RowScope.ButtonTertiary(
    button: KeypadButton.KeypadButtonSimple,
    onClick: () -> Unit,
    enabled: Boolean,
    height: Float,
    weight: Float,
  ) =
    BasicKeyboardButton(
      modifier = buttonModifier,
      contentHeight = height,
      enabled = enabled,
      defaultWeight = weight,
      onClick = onClick,
      onLongClick = null,
      colors = buttonTertiaryColors(),
      icon = button.icon,
      contentDescription = button.description?.let { stringResource(it) },
    )

  @Composable
  override fun RowScope.ButtonFilled(
    button: KeypadButton.KeypadButtonSimple,
    onClick: () -> Unit,
    enabled: Boolean,
    height: Float,
    weight: Float,
  ) =
    BasicKeyboardButton(
      modifier = buttonModifier,
      contentHeight = height,
      enabled = enabled,
      defaultWeight = weight,
      onClick = onClick,
      onLongClick = null,
      colors = buttonFilledColors(),
      icon = button.icon,
      contentDescription = button.description?.let { stringResource(it) },
    )

  @Composable
  override fun RowScope.ButtonFilled(
    button: KeypadButton.KeypadButtonAdd,
    onClick: (String) -> Unit,
    enabled: Boolean,
    height: Float,
    weight: Float,
  ) =
    BasicKeyboardButton(
      modifier = buttonModifier,
      contentHeight = height,
      enabled = enabled,
      defaultWeight = weight,
      onClick = { onClick(button.token) },
      onLongClick = null,
      colors = buttonFilledColors(),
      icon = button.icon,
      contentDescription = button.description?.let { stringResource(it) },
    )

  @Composable
  override fun RowScope.ButtonLight(
    button: KeypadButton.KeypadButtonAdd,
    onClick: (String) -> Unit,
    enabled: Boolean,
    height: Float,
    weight: Float,
  ) =
    BasicKeyboardButton(
      modifier = buttonModifier,
      contentHeight = height,
      enabled = enabled,
      defaultWeight = weight,
      onClick = { onClick(button.token) },
      onLongClick = null,
      colors = buttonLightColors(),
      icon = button.icon,
      contentDescription = button.description?.let { stringResource(it) },
    )

  @Composable
  override fun RowScope.ButtonLight(
    button: KeypadButton.KeypadButtonSimple,
    onLongClick: (() -> Unit)?,
    onClick: () -> Unit,
    enabled: Boolean,
    height: Float,
    weight: Float,
  ) =
    BasicKeyboardButton(
      modifier = buttonModifier,
      contentHeight = height,
      enabled = enabled,
      defaultWeight = weight,
      onClick = onClick,
      onLongClick = onLongClick,
      colors = buttonLightColors(),
      icon = button.icon,
      contentDescription = button.description?.let { stringResource(it) },
    )

  @Composable
  override fun RowScope.ButtonFilledPrimary(
    button: KeypadButton.KeypadButtonSimple,
    onClick: () -> Unit,
    enabled: Boolean,
    height: Float,
    weight: Float,
  ) =
    BasicKeyboardButton(
      modifier = buttonModifier,
      contentHeight = height,
      enabled = enabled,
      defaultWeight = weight,
      onClick = onClick,
      onLongClick = null,
      colors = buttonFilledPrimaryColors(),
      icon = button.icon,
      contentDescription = button.description?.let { stringResource(it) },
    )
}

@Composable
private fun buttonLightColors() =
  ButtonDefaults.filledTonalButtonColors(
    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
  )

@Composable
private fun buttonFilledColors() =
  ButtonDefaults.filledTonalButtonColors(
    containerColor = MaterialTheme.colorScheme.secondaryContainer,
    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
  )

@Composable
private fun buttonFilledPrimaryColors() =
  ButtonDefaults.filledTonalButtonColors(
    containerColor = MaterialTheme.colorScheme.primaryContainer,
    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
  )

@Composable
private fun buttonTertiaryColors() =
  ButtonDefaults.filledTonalButtonColors(
    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
  )

@Composable
private fun buttonTransparentColors() =
  ButtonDefaults.textButtonColors(
    containerColor = Color.Transparent,
    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
  )

@Composable
private fun RowScope.BasicKeyboardButton(
  modifier: Modifier,
  contentHeight: Float,
  enabled: Boolean,
  defaultWeight: Float,
  onClick: () -> Unit,
  onLongClick: (() -> Unit)?,
  colors: ButtonColors,
  icon: ImageVector,
  contentDescription: String?,
) {
  val containerColor =
    remember(colors, enabled) {
      if (enabled) colors.containerColor else colors.disabledContainerColor
    }
  val contentColor =
    remember(colors, enabled) { if (enabled) colors.contentColor else colors.disabledContentColor }

  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()
  val weight: Float by
    animateFloatAsState(
      targetValue = if (isPressed) 1.5f * defaultWeight else defaultWeight,
      animationSpec = MaterialTheme.motionScheme.fastSpatialSpec(),
    )

  Box(
    modifier =
      modifier
        .weight(weight)
        .squashable(
          interactionSource = interactionSource,
          onClick = { onClick() },
          onLongClick = onLongClick,
          cornerRadiusRange = 30..50,
          animationSpec = MaterialTheme.motionScheme.fastSpatialSpec(),
          enabled = enabled,
        )
        .background(containerColor),
    contentAlignment = Alignment.Center,
  ) {
    Icon(
      imageVector = icon,
      contentDescription = contentDescription,
      modifier =
        Modifier.aspectRatio(1f) // All icons are square (24x24)
          .fillMaxWidth()
          .graphicsLayer {
            scaleX = contentHeight
            scaleY = contentHeight
          },
      tint = contentColor,
    )
  }
}

interface KeypadFlowScope {
  val iconHeight: Float

  @Composable fun KeypadRow(content: @Composable RowScope.() -> Unit)

  @Composable
  fun RowScope.ButtonTransparent(
    button: KeypadButton.KeypadButtonAdd,
    onClick: (String) -> Unit,
    enabled: Boolean = true,
    height: Float = iconHeight,
    weight: Float = 1f,
  )

  @Composable
  fun RowScope.ButtonTransparent(
    button: KeypadButton.KeypadButtonSimple,
    onClick: () -> Unit,
    enabled: Boolean = true,
    height: Float = iconHeight,
    weight: Float = 1f,
  )

  @Composable
  fun RowScope.ButtonTertiary(
    button: KeypadButton.KeypadButtonSimple,
    onClick: () -> Unit,
    enabled: Boolean = true,
    height: Float = iconHeight,
    weight: Float = 1f,
  )

  @Composable
  fun RowScope.ButtonFilled(
    button: KeypadButton.KeypadButtonSimple,
    onClick: () -> Unit,
    enabled: Boolean = true,
    height: Float = iconHeight,
    weight: Float = 1f,
  )

  @Composable
  fun RowScope.ButtonFilled(
    button: KeypadButton.KeypadButtonAdd,
    onClick: (String) -> Unit,
    enabled: Boolean = true,
    height: Float = iconHeight,
    weight: Float = 1f,
  )

  @Composable
  fun RowScope.ButtonLight(
    button: KeypadButton.KeypadButtonAdd,
    onClick: (String) -> Unit,
    enabled: Boolean = true,
    height: Float = iconHeight,
    weight: Float = 1f,
  )

  @Composable
  fun RowScope.ButtonLight(
    button: KeypadButton.KeypadButtonSimple,
    onLongClick: (() -> Unit)?,
    onClick: () -> Unit,
    enabled: Boolean = true,
    height: Float = iconHeight,
    weight: Float = 1f,
  )

  @Composable
  fun RowScope.ButtonFilledPrimary(
    button: KeypadButton.KeypadButtonSimple,
    onClick: () -> Unit,
    enabled: Boolean = true,
    height: Float = iconHeight,
    weight: Float = 1f,
  )
}

object KeyboardButtonToken {

  /** Height factor for icons. Mostly for main button in portrait mode. */
  const val ICON_HEIGHT_TALL = 1.1f

  /** Height factor for icons. Mostly for additional button in portrait mode */
  const val ICON_HEIGHT_TALL_SECONDARY = 1.6f

  /** Height factor for icons. Mostly for main button in landscape mode. */
  const val ICON_HEIGHT_SHORT = 1.3f

  /** Height factor for icons. Mostly for additional button in landscape mode. */
  const val ICON_HEIGHT_SHORT_SECONDARY = 1.1f
}

private const val DEFAULT_PADDING_FRACTION = 0.01f
