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

package com.sadellie.unitto.core.designsystem.icons.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconPack.Or: ImageVector
  get() {
    if (_Or != null) {
      return _Or!!
    }
    _Or =
      ImageVector.Builder(
          name = "Or",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(9.98f, 15.835f)
            curveTo(9.13f, 15.835f, 8.443f, 15.568f, 7.92f, 15.035f)
            curveTo(7.397f, 14.502f, 7.135f, 13.83f, 7.135f, 13.02f)
            curveTo(7.135f, 12.197f, 7.4f, 11.518f, 7.93f, 10.985f)
            curveTo(8.463f, 10.448f, 9.147f, 10.18f, 9.98f, 10.18f)
            curveTo(10.813f, 10.18f, 11.495f, 10.453f, 12.025f, 11f)
            curveTo(12.558f, 11.543f, 12.825f, 12.217f, 12.825f, 13.02f)
            curveTo(12.825f, 13.817f, 12.563f, 14.485f, 12.04f, 15.025f)
            curveTo(11.52f, 15.565f, 10.833f, 15.835f, 9.98f, 15.835f)
            close()
            moveTo(9.98f, 14.945f)
            curveTo(10.513f, 14.945f, 10.953f, 14.768f, 11.3f, 14.415f)
            curveTo(11.646f, 14.058f, 11.82f, 13.593f, 11.82f, 13.02f)
            curveTo(11.82f, 12.453f, 11.646f, 11.988f, 11.3f, 11.625f)
            curveTo(10.953f, 11.258f, 10.513f, 11.075f, 9.98f, 11.075f)
            curveTo(9.443f, 11.075f, 9.002f, 11.255f, 8.655f, 11.615f)
            curveTo(8.312f, 11.975f, 8.14f, 12.443f, 8.14f, 13.02f)
            curveTo(8.14f, 13.597f, 8.312f, 14.062f, 8.655f, 14.415f)
            curveTo(9.002f, 14.768f, 9.443f, 14.945f, 9.98f, 14.945f)
            close()
            moveTo(14.372f, 15.725f)
            curveTo(14.238f, 15.725f, 14.122f, 15.677f, 14.022f, 15.58f)
            curveTo(13.925f, 15.48f, 13.877f, 15.362f, 13.877f, 15.225f)
            verticalLineTo(10.77f)
            curveTo(13.877f, 10.64f, 13.922f, 10.53f, 14.012f, 10.44f)
            curveTo(14.105f, 10.347f, 14.215f, 10.3f, 14.342f, 10.3f)
            curveTo(14.469f, 10.3f, 14.577f, 10.347f, 14.667f, 10.44f)
            curveTo(14.76f, 10.53f, 14.807f, 10.64f, 14.807f, 10.77f)
            verticalLineTo(11.165f)
            horizontalLineTo(14.837f)
            curveTo(14.947f, 10.878f, 15.139f, 10.642f, 15.412f, 10.455f)
            curveTo(15.685f, 10.268f, 15.987f, 10.175f, 16.317f, 10.175f)
            curveTo(16.42f, 10.175f, 16.53f, 10.185f, 16.647f, 10.205f)
            curveTo(16.764f, 10.222f, 16.858f, 10.252f, 16.932f, 10.295f)
            curveTo(17.008f, 10.338f, 17.069f, 10.407f, 17.112f, 10.5f)
            curveTo(17.159f, 10.593f, 17.169f, 10.697f, 17.142f, 10.81f)
            curveTo(17.115f, 10.92f, 17.062f, 11.003f, 16.982f, 11.06f)
            curveTo(16.905f, 11.117f, 16.829f, 11.15f, 16.752f, 11.16f)
            curveTo(16.678f, 11.17f, 16.6f, 11.163f, 16.517f, 11.14f)
            curveTo(16.434f, 11.117f, 16.344f, 11.105f, 16.247f, 11.105f)
            curveTo(15.857f, 11.105f, 15.528f, 11.253f, 15.262f, 11.55f)
            curveTo(14.998f, 11.847f, 14.867f, 12.268f, 14.867f, 12.815f)
            verticalLineTo(15.225f)
            curveTo(14.867f, 15.362f, 14.818f, 15.48f, 14.722f, 15.58f)
            curveTo(14.625f, 15.677f, 14.509f, 15.725f, 14.372f, 15.725f)
            close()
          }
        }
        .build()

    return _Or!!
  }

@Suppress("ObjectPropertyName") private var _Or: ImageVector? = null
