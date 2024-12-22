/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BasicKeyboardButton(
  modifier: Modifier,
  contentHeight: Float,
  onClick: () -> Unit,
  onLongClick: (() -> Unit)?,
  containerColor: Color,
  icon: ImageVector,
  contentDescription: String?,
  iconColor: Color,
) {
  Box(
    modifier =
      modifier
        .squashable(
          interactionSource = remember { MutableInteractionSource() },
          onClick = { onClick() },
          onLongClick =
            if (onLongClick != null) {
              { onLongClick() }
            } else {
              null
            },
          cornerRadiusRange = 30..50,
          animationSpec = tween(KeyboardButtonToken.SQUASH_ANIMATION_DURATION_MS),
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
      tint = iconColor,
    )
  }
}

@Composable
fun KeyboardButtonLight(
  modifier: Modifier,
  icon: ImageVector,
  contentDescription: String?,
  contentHeight: Float,
  onLongClick: (() -> Unit)? = null,
  onClick: () -> Unit,
) {
  BasicKeyboardButton(
    modifier = modifier,
    contentHeight = contentHeight,
    onClick = onClick,
    onLongClick = onLongClick,
    containerColor = MaterialTheme.colorScheme.inverseOnSurface,
    icon = icon,
    contentDescription = contentDescription,
    iconColor = MaterialTheme.colorScheme.onSurfaceVariant,
  )
}

@Composable
fun KeyboardButtonFilled(
  modifier: Modifier,
  icon: ImageVector,
  contentDescription: String?,
  contentHeight: Float,
  onLongClick: (() -> Unit)? = null,
  onClick: () -> Unit,
) {
  BasicKeyboardButton(
    modifier = modifier,
    contentHeight = contentHeight,
    onClick = onClick,
    onLongClick = onLongClick,
    containerColor = MaterialTheme.colorScheme.secondaryContainer,
    icon = icon,
    contentDescription = contentDescription,
    iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
  )
}

@Composable
fun KeyboardButtonFilledPrimary(
  modifier: Modifier,
  icon: ImageVector,
  contentDescription: String?,
  contentHeight: Float,
  onLongClick: (() -> Unit)? = null,
  onClick: () -> Unit,
) {
  BasicKeyboardButton(
    modifier = modifier,
    contentHeight = contentHeight,
    onClick = onClick,
    onLongClick = onLongClick,
    containerColor = MaterialTheme.colorScheme.primaryContainer,
    icon = icon,
    contentDescription = contentDescription,
    iconColor = MaterialTheme.colorScheme.onPrimaryContainer,
  )
}

@Composable
fun KeyboardButtonTransparent(
  modifier: Modifier,
  icon: ImageVector,
  contentDescription: String?,
  contentHeight: Float,
  onLongClick: (() -> Unit)? = null,
  onClick: () -> Unit,
) {
  BasicKeyboardButton(
    modifier = modifier,
    contentHeight = contentHeight,
    onClick = onClick,
    onLongClick = onLongClick,
    containerColor = Color.Transparent,
    icon = icon,
    contentDescription = contentDescription,
    iconColor = MaterialTheme.colorScheme.onSurfaceVariant,
  )
}

@Composable
fun KeyboardButtonTertiary(
  modifier: Modifier,
  icon: ImageVector,
  contentDescription: String?,
  contentHeight: Float,
  onLongClick: (() -> Unit)? = null,
  onClick: () -> Unit,
) {
  BasicKeyboardButton(
    modifier = modifier,
    contentHeight = contentHeight,
    onClick = onClick,
    onLongClick = onLongClick,
    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
    icon = icon,
    contentDescription = contentDescription,
    iconColor = MaterialTheme.colorScheme.onTertiaryContainer,
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

  const val SQUASH_ANIMATION_DURATION_MS = 200
}
