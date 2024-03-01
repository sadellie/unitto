/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.feature.glance

import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.IconCompat
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.Action
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.color.ColorProviders
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.unit.ColorProvider

@Composable
internal fun IconButton(
  glanceModifier: GlanceModifier,
  containerColor: ColorProvider,
  @DrawableRes iconRes: Int,
  contentColor: ColorProvider = GlanceTheme.colors.contentColorFor(containerColor),
  onClick: Action,
) {
  Box(modifier = glanceModifier.height(48.dp).padding(4.dp), contentAlignment = Alignment.Center) {
    Image(
      modifier =
        GlanceModifier.fillMaxWidth()
          .clickable(onClick)
          .cornerRadius(
            context = LocalContext.current,
            cornerRadius = 24.dp,
            color = containerColor,
          )
          .padding(horizontal = 16.dp, vertical = 8.dp),
      provider = ImageProvider(iconRes),
      contentDescription = null,
      colorFilter = ColorFilter.tint(contentColor),
    )
  }
}

private fun ColorProviders.contentColorFor(backgroundColor: ColorProvider): ColorProvider =
  when (backgroundColor) {
    primary -> onPrimary
    primaryContainer -> onPrimaryContainer
    secondaryContainer -> onSecondaryContainer
    inverseOnSurface -> onSurfaceVariant
    tertiaryContainer -> onTertiaryContainer
    else -> onBackground
  }

fun GlanceModifier.cornerRadius(
  context: Context,
  cornerRadius: Dp,
  color: ColorProvider,
): GlanceModifier =
  when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
      this.background(color).cornerRadius(cornerRadius)
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ->
      this.background(
        // This
        ImageProvider(
          // is
          IconCompat
            // so
            .createWithResource(context, R.drawable.rounded_corners_rectangle_shape)
            // fucking
            .toIcon(context)
            // stupid
            .setTint(color.getColor(context).toArgb())
        )
      )
    else -> this.background(color)
  }
