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

package com.sadellie.unitto.core.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.designsystem.icons.symbols.Power
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols

@Composable
fun KeyboardButtonLight(
  modifier: Modifier,
  icon: ImageVector,
  contentDescription: String?,
  contentHeight: Float,
  enabled: Boolean,
  onLongClick: (() -> Unit)? = null,
  onClick: () -> Unit,
) {
  BasicKeyboardButton(
    modifier = modifier,
    contentHeight = contentHeight,
    enabled = enabled,
    onClick = onClick,
    onLongClick = onLongClick,
    colors =
      ButtonDefaults.filledTonalButtonColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
      ),
    icon = icon,
    contentDescription = contentDescription,
  )
}

@Composable
fun KeyboardButtonFilled(
  modifier: Modifier,
  icon: ImageVector,
  contentDescription: String?,
  contentHeight: Float,
  enabled: Boolean,
  onLongClick: (() -> Unit)? = null,
  onClick: () -> Unit,
) {
  BasicKeyboardButton(
    modifier = modifier,
    contentHeight = contentHeight,
    enabled = enabled,
    onClick = onClick,
    onLongClick = onLongClick,
    colors =
      ButtonDefaults.filledTonalButtonColors(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
      ),
    icon = icon,
    contentDescription = contentDescription,
  )
}

@Composable
fun KeyboardButtonFilledPrimary(
  modifier: Modifier,
  icon: ImageVector,
  contentDescription: String?,
  contentHeight: Float,
  enabled: Boolean,
  onLongClick: (() -> Unit)? = null,
  onClick: () -> Unit,
) {
  BasicKeyboardButton(
    modifier = modifier,
    contentHeight = contentHeight,
    enabled = enabled,
    onClick = onClick,
    onLongClick = onLongClick,
    colors =
      ButtonDefaults.filledTonalButtonColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
      ),
    icon = icon,
    contentDescription = contentDescription,
  )
}

@Composable
fun KeyboardButtonTransparent(
  modifier: Modifier,
  icon: ImageVector,
  contentDescription: String?,
  contentHeight: Float,
  enabled: Boolean,
  onLongClick: (() -> Unit)? = null,
  onClick: () -> Unit,
) {
  BasicKeyboardButton(
    modifier = modifier,
    contentHeight = contentHeight,
    enabled = enabled,
    onClick = onClick,
    onLongClick = onLongClick,
    colors =
      ButtonDefaults.textButtonColors(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
      ),
    icon = icon,
    contentDescription = contentDescription,
  )
}

@Composable
fun KeyboardButtonTertiary(
  modifier: Modifier,
  icon: ImageVector,
  contentDescription: String?,
  contentHeight: Float,
  enabled: Boolean,
  onLongClick: (() -> Unit)? = null,
  onClick: () -> Unit,
) {
  BasicKeyboardButton(
    modifier = modifier,
    contentHeight = contentHeight,
    enabled = enabled,
    onClick = onClick,
    onLongClick = onLongClick,
    colors =
      ButtonDefaults.filledTonalButtonColors(
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
      ),
    icon = icon,
    contentDescription = contentDescription,
  )
}

@Composable
private fun BasicKeyboardButton(
  modifier: Modifier,
  contentHeight: Float,
  enabled: Boolean,
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
  Box(
    modifier =
      modifier
        .squashable(
          interactionSource = remember { MutableInteractionSource() },
          onClick = { onClick() },
          onLongClick = onLongClick,
          cornerRadiusRange = 30..50,
          animationSpec = tween(KeyboardButtonToken.SQUASH_ANIMATION_DURATION_MS),
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

object KeyboardButtonToken {

  /** Height factor for icons. Mostly for main button in portrait mode. */
  const val ICON_HEIGHT_TALL = 1.1f

  /** Height factor for icons. Mostly for additional button in portrait mode */
  const val ICON_HEIGHT_TALL_SECONDARY = 1.6f

  /** Height factor for icons. Mostly for main button in landscape mode. */
  const val ICON_HEIGHT_SHORT = 1.3f

  /** Height factor for icons. Mostly for additional button in landscape mode. */
  const val ICON_HEIGHT_SHORT_SECONDARY = 1.1f

  const val SQUASH_ANIMATION_DURATION_MS = 200
}

@Composable
@Preview
private fun PreviewBasicKeyboardButton() {
  FlowRow {
    val modifier = Modifier.size(46.dp)
    KeyboardButtonLight(
      modifier = modifier,
      icon = Symbols.Power,
      contentDescription = null,
      contentHeight = 1f,
      onLongClick = null,
      enabled = true,
      onClick = {},
    )
    KeyboardButtonLight(
      modifier = modifier,
      icon = Symbols.Power,
      contentDescription = null,
      contentHeight = 1f,
      onLongClick = null,
      enabled = false,
      onClick = {},
    )

    KeyboardButtonFilled(
      modifier = modifier,
      icon = Symbols.Power,
      contentDescription = null,
      contentHeight = 1f,
      onLongClick = null,
      enabled = true,
      onClick = {},
    )
    KeyboardButtonFilled(
      modifier = modifier,
      icon = Symbols.Power,
      contentDescription = null,
      contentHeight = 1f,
      onLongClick = null,
      enabled = false,
      onClick = {},
    )

    KeyboardButtonFilledPrimary(
      modifier = modifier,
      icon = Symbols.Power,
      contentDescription = null,
      contentHeight = 1f,
      onLongClick = null,
      enabled = true,
      onClick = {},
    )
    KeyboardButtonFilledPrimary(
      modifier = modifier,
      icon = Symbols.Power,
      contentDescription = null,
      contentHeight = 1f,
      onLongClick = null,
      enabled = false,
      onClick = {},
    )

    KeyboardButtonTransparent(
      modifier = modifier,
      icon = Symbols.Power,
      contentDescription = null,
      contentHeight = 1f,
      onLongClick = null,
      enabled = true,
      onClick = {},
    )
    KeyboardButtonTransparent(
      modifier = modifier,
      icon = Symbols.Power,
      contentDescription = null,
      contentHeight = 1f,
      onLongClick = null,
      enabled = false,
      onClick = {},
    )

    KeyboardButtonTertiary(
      modifier = modifier,
      icon = Symbols.Power,
      contentDescription = null,
      contentHeight = 1f,
      onLongClick = null,
      enabled = true,
      onClick = {},
    )
    KeyboardButtonTertiary(
      modifier = modifier,
      icon = Symbols.Power,
      contentDescription = null,
      contentHeight = 1f,
      onLongClick = null,
      enabled = false,
      onClick = {},
    )
  }
}
