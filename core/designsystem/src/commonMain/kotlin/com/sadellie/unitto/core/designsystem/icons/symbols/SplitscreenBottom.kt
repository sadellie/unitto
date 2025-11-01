package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.SplitscreenBottom: ImageVector
  get() {
    if (_SplitscreenBottom != null) {
      return _SplitscreenBottom!!
    }
    _SplitscreenBottom =
      ImageVector.Builder(
          name = "SplitscreenBottom",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(200f, 840f)
            quadToRelative(-33f, 0f, -56.5f, -23.5f)
            reflectiveQuadTo(120f, 760f)
            verticalLineToRelative(-160f)
            quadToRelative(0f, -33f, 23.5f, -56.5f)
            reflectiveQuadTo(200f, 520f)
            horizontalLineToRelative(560f)
            quadToRelative(33f, 0f, 56.5f, 23.5f)
            reflectiveQuadTo(840f, 600f)
            verticalLineToRelative(160f)
            quadToRelative(0f, 33f, -23.5f, 56.5f)
            reflectiveQuadTo(760f, 840f)
            lineTo(200f, 840f)
            close()
            moveTo(200f, 440f)
            quadToRelative(-33f, 0f, -56.5f, -23.5f)
            reflectiveQuadTo(120f, 360f)
            verticalLineToRelative(-160f)
            quadToRelative(0f, -33f, 23.5f, -56.5f)
            reflectiveQuadTo(200f, 120f)
            horizontalLineToRelative(560f)
            quadToRelative(33f, 0f, 56.5f, 23.5f)
            reflectiveQuadTo(840f, 200f)
            verticalLineToRelative(160f)
            quadToRelative(0f, 33f, -23.5f, 56.5f)
            reflectiveQuadTo(760f, 440f)
            lineTo(200f, 440f)
            close()
            moveTo(760f, 200f)
            lineTo(200f, 200f)
            verticalLineToRelative(160f)
            horizontalLineToRelative(560f)
            verticalLineToRelative(-160f)
            close()
          }
        }
        .build()

    return _SplitscreenBottom!!
  }

@Suppress("ObjectPropertyName") private var _SplitscreenBottom: ImageVector? = null
