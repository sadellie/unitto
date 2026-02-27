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

val IconPack.Ln: ImageVector
  get() {
    if (_Ln != null) {
      return _Ln!!
    }
    _Ln =
      ImageVector.Builder(
          name = "Ln",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(8.937f, 15.555f)
            curveTo(8.804f, 15.555f, 8.687f, 15.507f, 8.587f, 15.41f)
            curveTo(8.49f, 15.31f, 8.442f, 15.192f, 8.442f, 15.055f)
            verticalLineTo(8.565f)
            curveTo(8.442f, 8.428f, 8.49f, 8.312f, 8.587f, 8.215f)
            curveTo(8.687f, 8.115f, 8.804f, 8.065f, 8.937f, 8.065f)
            curveTo(9.074f, 8.065f, 9.19f, 8.115f, 9.287f, 8.215f)
            curveTo(9.384f, 8.312f, 9.432f, 8.428f, 9.432f, 8.565f)
            verticalLineTo(15.055f)
            curveTo(9.432f, 15.192f, 9.384f, 15.31f, 9.287f, 15.41f)
            curveTo(9.19f, 15.507f, 9.074f, 15.555f, 8.937f, 15.555f)
            close()
            moveTo(11.335f, 15.555f)
            curveTo(11.201f, 15.555f, 11.085f, 15.507f, 10.985f, 15.41f)
            curveTo(10.888f, 15.31f, 10.84f, 15.192f, 10.84f, 15.055f)
            verticalLineTo(10.605f)
            curveTo(10.84f, 10.475f, 10.886f, 10.363f, 10.98f, 10.27f)
            curveTo(11.073f, 10.177f, 11.185f, 10.13f, 11.315f, 10.13f)
            curveTo(11.441f, 10.13f, 11.551f, 10.177f, 11.645f, 10.27f)
            curveTo(11.738f, 10.363f, 11.785f, 10.475f, 11.785f, 10.605f)
            verticalLineTo(10.925f)
            horizontalLineTo(11.815f)
            curveTo(11.985f, 10.658f, 12.231f, 10.44f, 12.555f, 10.27f)
            curveTo(12.878f, 10.1f, 13.246f, 10.015f, 13.66f, 10.015f)
            curveTo(14.29f, 10.015f, 14.773f, 10.182f, 15.11f, 10.515f)
            curveTo(15.45f, 10.848f, 15.62f, 11.312f, 15.62f, 11.905f)
            verticalLineTo(15.055f)
            curveTo(15.62f, 15.192f, 15.571f, 15.31f, 15.475f, 15.41f)
            curveTo(15.378f, 15.507f, 15.261f, 15.555f, 15.125f, 15.555f)
            curveTo(14.991f, 15.555f, 14.875f, 15.507f, 14.775f, 15.41f)
            curveTo(14.678f, 15.31f, 14.63f, 15.192f, 14.63f, 15.055f)
            verticalLineTo(12.125f)
            curveTo(14.63f, 11.722f, 14.531f, 11.412f, 14.335f, 11.195f)
            curveTo(14.141f, 10.975f, 13.821f, 10.865f, 13.375f, 10.865f)
            curveTo(12.928f, 10.865f, 12.558f, 11.008f, 12.265f, 11.295f)
            curveTo(11.975f, 11.578f, 11.83f, 11.938f, 11.83f, 12.375f)
            verticalLineTo(15.055f)
            curveTo(11.83f, 15.192f, 11.781f, 15.31f, 11.685f, 15.41f)
            curveTo(11.588f, 15.507f, 11.471f, 15.555f, 11.335f, 15.555f)
            close()
          }
        }
        .build()

    return _Ln!!
  }

@Suppress("ObjectPropertyName") private var _Ln: ImageVector? = null
