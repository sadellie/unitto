/*
 * Unitto is a unit converter for Android
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

package com.sadellie.unitto.feature.glance.glance

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.Action
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.color.ColorProviders
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.unit.ColorProvider

@Composable
internal fun IconButton(
    glanceModifier: GlanceModifier,
    containerColor: ColorProvider,
    @DrawableRes iconRes: Int,
    contentColor: ColorProvider = GlanceTheme.colors.contentColorFor(containerColor),
    onClickKey: String = iconRes.toString(),
    onClick: Action,
) {
    Box(
        modifier = glanceModifier
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = GlanceModifier
                .fillMaxWidth()
                .clickable(onClick)
                .cornerRadius(100.dp)
                .background(containerColor)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            provider = ImageProvider(iconRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(contentColor)
        )
    }
}

private fun ColorProviders.contentColorFor(backgroundColor: ColorProvider): ColorProvider =
    when (backgroundColor) {
        primary -> onPrimary
        primaryContainer -> onPrimaryContainer
        inverseOnSurface -> onSurfaceVariant
        tertiaryContainer -> onTertiaryContainer
        else -> onBackground
    }

// https://gist.github.com/rozPierog/1145af6e1f10c9199000828ab4bd6bad
// Kinda works, but corners parameter needs to be split
//@SuppressLint("RestrictedApi")
//fun GlanceModifier.cornerRadiusCompat(
//    cornerRadius: Int,
//    @ColorInt color: Int,
//    @FloatRange(from = 0.0, to = 1.0) backgroundAlpha: Float = 1f,
//): GlanceModifier {
//    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//        this.background(Color(color).copy(alpha = backgroundAlpha))
//            .cornerRadius(cornerRadius.dp)
//    } else {
//        val radii = FloatArray(8) { cornerRadius.toFloat() }
//        val shape = ShapeDrawable(RoundRectShape(radii, null, null))
//        shape.paint.color = ColorUtils.setAlphaComponent(color, (255 * backgroundAlpha).toInt())
//        val bitmap = shape.toBitmap(width = 150, height = 75)
//        this.background(BitmapImageProvider(bitmap))
//    }
//}
