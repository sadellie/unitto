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

package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.MoreHoriz: ImageVector
  get() {
    val current = _moreHoriz
    if (current != null) return current

    return ImageVector.Builder(
        name = "MoreHoriz",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 960.0f,
        viewportHeight = 960.0f,
      )
      .apply {
        // M240 -400 q-33 0 -56.5 -23.5 T160 -480 q0 -33 23.5 -56.5 T240 -560 q33 0 56.5 23.5 T320
        // -480 q0 33 -23.5 56.5 T240 -400Z m240 0 q-33 0 -56.5 -23.5 T400 -480 q0 -33 23.5 -56.5
        // T480 -560 q33 0 56.5 23.5 T560 -480 q0 33 -23.5 56.5 T480 -400Z m240 0 q-33 0 -56.5 -23.5
        // T640 -480 q0 -33 23.5 -56.5 T720 -560 q33 0 56.5 23.5 T800 -480 q0 33 -23.5 56.5 T720
        // -400Z
        path(fill = SolidColor(Color(0xFFE3E3E3))) {
          // M 240 560
          moveTo(x = 240.0f, y = 560.0f)
          // q -33 0 -56.5 -23.5
          quadToRelative(
            dx1 = -33.0f,
            dy1 = 0.0f,
            dx2 = -56.5f,
            dy2 = -23.5f,
          )
          // T 160 480
          reflectiveQuadTo(
            x1 = 160.0f,
            y1 = 480.0f,
          )
          // q 0 -33 23.5 -56.5
          quadToRelative(
            dx1 = 0.0f,
            dy1 = -33.0f,
            dx2 = 23.5f,
            dy2 = -56.5f,
          )
          // T 240 400
          reflectiveQuadTo(
            x1 = 240.0f,
            y1 = 400.0f,
          )
          // q 33 0 56.5 23.5
          quadToRelative(
            dx1 = 33.0f,
            dy1 = 0.0f,
            dx2 = 56.5f,
            dy2 = 23.5f,
          )
          // T 320 480
          reflectiveQuadTo(
            x1 = 320.0f,
            y1 = 480.0f,
          )
          // q 0 33 -23.5 56.5
          quadToRelative(
            dx1 = 0.0f,
            dy1 = 33.0f,
            dx2 = -23.5f,
            dy2 = 56.5f,
          )
          // T 240 560z
          reflectiveQuadTo(
            x1 = 240.0f,
            y1 = 560.0f,
          )
          close()
          // m 240 0
          moveToRelative(dx = 240.0f, dy = 0.0f)
          // q -33 0 -56.5 -23.5
          quadToRelative(
            dx1 = -33.0f,
            dy1 = 0.0f,
            dx2 = -56.5f,
            dy2 = -23.5f,
          )
          // T 400 480
          reflectiveQuadTo(
            x1 = 400.0f,
            y1 = 480.0f,
          )
          // q 0 -33 23.5 -56.5
          quadToRelative(
            dx1 = 0.0f,
            dy1 = -33.0f,
            dx2 = 23.5f,
            dy2 = -56.5f,
          )
          // T 480 400
          reflectiveQuadTo(
            x1 = 480.0f,
            y1 = 400.0f,
          )
          // q 33 0 56.5 23.5
          quadToRelative(
            dx1 = 33.0f,
            dy1 = 0.0f,
            dx2 = 56.5f,
            dy2 = 23.5f,
          )
          // T 560 480
          reflectiveQuadTo(
            x1 = 560.0f,
            y1 = 480.0f,
          )
          // q 0 33 -23.5 56.5
          quadToRelative(
            dx1 = 0.0f,
            dy1 = 33.0f,
            dx2 = -23.5f,
            dy2 = 56.5f,
          )
          // T 480 560z
          reflectiveQuadTo(
            x1 = 480.0f,
            y1 = 560.0f,
          )
          close()
          // m 240 0
          moveToRelative(dx = 240.0f, dy = 0.0f)
          // q -33 0 -56.5 -23.5
          quadToRelative(
            dx1 = -33.0f,
            dy1 = 0.0f,
            dx2 = -56.5f,
            dy2 = -23.5f,
          )
          // T 640 480
          reflectiveQuadTo(
            x1 = 640.0f,
            y1 = 480.0f,
          )
          // q 0 -33 23.5 -56.5
          quadToRelative(
            dx1 = 0.0f,
            dy1 = -33.0f,
            dx2 = 23.5f,
            dy2 = -56.5f,
          )
          // T 720 400
          reflectiveQuadTo(
            x1 = 720.0f,
            y1 = 400.0f,
          )
          // q 33 0 56.5 23.5
          quadToRelative(
            dx1 = 33.0f,
            dy1 = 0.0f,
            dx2 = 56.5f,
            dy2 = 23.5f,
          )
          // T 800 480
          reflectiveQuadTo(
            x1 = 800.0f,
            y1 = 480.0f,
          )
          // q 0 33 -23.5 56.5
          quadToRelative(
            dx1 = 0.0f,
            dy1 = 33.0f,
            dx2 = -23.5f,
            dy2 = 56.5f,
          )
          // T 720 560z
          reflectiveQuadTo(
            x1 = 720.0f,
            y1 = 560.0f,
          )
          close()
        }
      }
      .build()
      .also { _moreHoriz = it }
  }

@Suppress("ObjectPropertyName") private var _moreHoriz: ImageVector? = null
