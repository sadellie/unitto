package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.HardDrive2: ImageVector
  get() {
    if (_HardDrive2 != null) {
      return _HardDrive2!!
    }
    _HardDrive2 =
      ImageVector.Builder(
          name = "HardDrive2",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(560f, 760f)
            quadToRelative(17f, 0f, 28.5f, -11.5f)
            reflectiveQuadTo(600f, 720f)
            quadToRelative(0f, -17f, -11.5f, -28.5f)
            reflectiveQuadTo(560f, 680f)
            quadToRelative(-17f, 0f, -28.5f, 11.5f)
            reflectiveQuadTo(520f, 720f)
            quadToRelative(0f, 17f, 11.5f, 28.5f)
            reflectiveQuadTo(560f, 760f)
            close()
            moveTo(680f, 760f)
            quadToRelative(17f, 0f, 28.5f, -11.5f)
            reflectiveQuadTo(720f, 720f)
            quadToRelative(0f, -17f, -11.5f, -28.5f)
            reflectiveQuadTo(680f, 680f)
            quadToRelative(-17f, 0f, -28.5f, 11.5f)
            reflectiveQuadTo(640f, 720f)
            quadToRelative(0f, 17f, 11.5f, 28.5f)
            reflectiveQuadTo(680f, 760f)
            close()
            moveTo(120f, 520f)
            verticalLineToRelative(-360f)
            quadToRelative(0f, -33f, 23.5f, -56.5f)
            reflectiveQuadTo(200f, 80f)
            horizontalLineToRelative(560f)
            quadToRelative(33f, 0f, 56.5f, 23.5f)
            reflectiveQuadTo(840f, 160f)
            verticalLineToRelative(360f)
            horizontalLineToRelative(-80f)
            verticalLineToRelative(-360f)
            lineTo(200f, 160f)
            verticalLineToRelative(360f)
            horizontalLineToRelative(-80f)
            close()
            moveTo(200f, 600f)
            verticalLineToRelative(200f)
            horizontalLineToRelative(560f)
            verticalLineToRelative(-200f)
            lineTo(200f, 600f)
            close()
            moveTo(200f, 880f)
            quadToRelative(-33f, 0f, -56.5f, -23.5f)
            reflectiveQuadTo(120f, 800f)
            verticalLineToRelative(-280f)
            horizontalLineToRelative(720f)
            verticalLineToRelative(280f)
            quadToRelative(0f, 33f, -23.5f, 56.5f)
            reflectiveQuadTo(760f, 880f)
            lineTo(200f, 880f)
            close()
            moveTo(200f, 520f)
            horizontalLineToRelative(560f)
            horizontalLineToRelative(-560f)
            close()
            moveTo(200f, 600f)
            horizontalLineToRelative(560f)
            horizontalLineToRelative(-560f)
            close()
          }
        }
        .build()

    return _HardDrive2!!
  }

@Suppress("ObjectPropertyName") private var _HardDrive2: ImageVector? = null
