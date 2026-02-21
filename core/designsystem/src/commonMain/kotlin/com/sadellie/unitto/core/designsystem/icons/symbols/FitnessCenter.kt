package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.FitnessCenter: ImageVector
  get() {
    if (_FitnessCenter != null) {
      return _FitnessCenter!!
    }
    _FitnessCenter =
      ImageVector.Builder(
          name = "FitnessCenter",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(282f, 338f)
            lineTo(168f, 452f)
            quadToRelative(-11f, 11f, -27.5f, 11.5f)
            reflectiveQuadTo(112f, 452f)
            quadToRelative(-11f, -11f, -11.5f, -27.5f)
            reflectiveQuadTo(111f, 396f)
            lineToRelative(29f, -30f)
            lineToRelative(-28f, -28f)
            quadToRelative(-12f, -12f, -12f, -28f)
            reflectiveQuadToRelative(12f, -28f)
            lineToRelative(56f, -56f)
            lineToRelative(-29f, -30f)
            quadToRelative(-11f, -11f, -11f, -27.5f)
            reflectiveQuadToRelative(12f, -28.5f)
            quadToRelative(11f, -11f, 27.5f, -11.5f)
            reflectiveQuadTo(196f, 139f)
            lineToRelative(30f, 29f)
            lineToRelative(56f, -56f)
            quadToRelative(12f, -12f, 28f, -12f)
            reflectiveQuadToRelative(28f, 12f)
            lineToRelative(28f, 28f)
            lineToRelative(30f, -29f)
            quadToRelative(11f, -11f, 27.5f, -11f)
            reflectiveQuadToRelative(28.5f, 12f)
            quadToRelative(11f, 11f, 11f, 28f)
            reflectiveQuadToRelative(-11f, 28f)
            lineTo(338f, 282f)
            lineToRelative(340f, 340f)
            lineToRelative(114f, -114f)
            quadToRelative(11f, -11f, 27.5f, -11.5f)
            reflectiveQuadTo(848f, 508f)
            quadToRelative(11f, 11f, 11.5f, 27.5f)
            reflectiveQuadTo(849f, 564f)
            lineToRelative(-29f, 30f)
            lineToRelative(28f, 28f)
            quadToRelative(12f, 12f, 12f, 28f)
            reflectiveQuadToRelative(-12f, 28f)
            lineToRelative(-56f, 56f)
            lineToRelative(29f, 30f)
            quadToRelative(11f, 11f, 11f, 27.5f)
            reflectiveQuadTo(820f, 820f)
            quadToRelative(-11f, 11f, -27.5f, 11.5f)
            reflectiveQuadTo(764f, 821f)
            lineToRelative(-30f, -29f)
            lineToRelative(-56f, 56f)
            quadToRelative(-12f, 12f, -28f, 12f)
            reflectiveQuadToRelative(-28f, -12f)
            lineToRelative(-28f, -28f)
            lineToRelative(-30f, 29f)
            quadToRelative(-11f, 11f, -27.5f, 11f)
            reflectiveQuadTo(508f, 848f)
            quadToRelative(-11f, -11f, -11f, -28f)
            reflectiveQuadToRelative(11f, -28f)
            lineToRelative(114f, -114f)
            lineToRelative(-340f, -340f)
            close()
          }
        }
        .build()

    return _FitnessCenter!!
  }

@Suppress("ObjectPropertyName") private var _FitnessCenter: ImageVector? = null
