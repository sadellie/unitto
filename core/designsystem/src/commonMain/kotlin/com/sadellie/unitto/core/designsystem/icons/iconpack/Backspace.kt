/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2026 Elshan Agaev
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
package com.sadellie.unitto.core.designsystem.icons.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconPack.Backspace: ImageVector
  get() {
    if (_Backspace != null) {
      return _Backspace!!
    }
    _Backspace =
      ImageVector.Builder(
          name = "Backspace",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(12.766f, 12.613f)
            lineTo(13.597f, 13.444f)
            curveTo(13.677f, 13.524f, 13.779f, 13.564f, 13.903f, 13.564f)
            curveTo(14.027f, 13.564f, 14.129f, 13.524f, 14.209f, 13.444f)
            curveTo(14.29f, 13.363f, 14.33f, 13.262f, 14.33f, 13.137f)
            curveTo(14.33f, 13.014f, 14.29f, 12.911f, 14.209f, 12.831f)
            lineTo(13.378f, 12f)
            lineTo(14.209f, 11.169f)
            curveTo(14.29f, 11.089f, 14.33f, 10.986f, 14.33f, 10.863f)
            curveTo(14.33f, 10.738f, 14.29f, 10.637f, 14.209f, 10.556f)
            curveTo(14.129f, 10.476f, 14.027f, 10.436f, 13.903f, 10.436f)
            curveTo(13.779f, 10.436f, 13.677f, 10.476f, 13.597f, 10.556f)
            lineTo(12.766f, 11.387f)
            lineTo(11.934f, 10.556f)
            curveTo(11.854f, 10.476f, 11.752f, 10.436f, 11.628f, 10.436f)
            curveTo(11.504f, 10.436f, 11.402f, 10.476f, 11.322f, 10.556f)
            curveTo(11.242f, 10.637f, 11.202f, 10.738f, 11.202f, 10.863f)
            curveTo(11.202f, 10.986f, 11.242f, 11.089f, 11.322f, 11.169f)
            lineTo(12.153f, 12f)
            lineTo(11.322f, 12.831f)
            curveTo(11.242f, 12.911f, 11.202f, 13.014f, 11.202f, 13.137f)
            curveTo(11.202f, 13.262f, 11.242f, 13.363f, 11.322f, 13.444f)
            curveTo(11.402f, 13.524f, 11.504f, 13.564f, 11.628f, 13.564f)
            curveTo(11.752f, 13.564f, 11.854f, 13.524f, 11.934f, 13.444f)
            lineTo(12.766f, 12.613f)
            close()
            moveTo(10.578f, 15.5f)
            curveTo(10.44f, 15.5f, 10.308f, 15.469f, 10.184f, 15.407f)
            curveTo(10.06f, 15.345f, 9.958f, 15.259f, 9.878f, 15.15f)
            lineTo(7.909f, 12.525f)
            curveTo(7.793f, 12.372f, 7.734f, 12.197f, 7.734f, 12f)
            curveTo(7.734f, 11.803f, 7.793f, 11.628f, 7.909f, 11.475f)
            lineTo(9.878f, 8.85f)
            curveTo(9.958f, 8.741f, 10.06f, 8.655f, 10.184f, 8.593f)
            curveTo(10.308f, 8.531f, 10.44f, 8.5f, 10.578f, 8.5f)
            horizontalLineTo(15.391f)
            curveTo(15.631f, 8.5f, 15.837f, 8.586f, 16.009f, 8.757f)
            curveTo(16.18f, 8.928f, 16.266f, 9.134f, 16.266f, 9.375f)
            verticalLineTo(14.625f)
            curveTo(16.266f, 14.866f, 16.18f, 15.072f, 16.009f, 15.243f)
            curveTo(15.837f, 15.414f, 15.631f, 15.5f, 15.391f, 15.5f)
            horizontalLineTo(10.578f)
            close()
          }
        }
        .build()

    return _Backspace!!
  }

@Suppress("ObjectPropertyName") private var _Backspace: ImageVector? = null
