package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.PowerFill: ImageVector
  get() {
    if (_PowerFill != null) {
      return _PowerFill!!
    }
    _PowerFill =
      ImageVector.Builder(
          name = "PowerFill",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(380f, 800f)
            verticalLineToRelative(-80f)
            lineTo(263f, 603f)
            quadToRelative(-11f, -11f, -17f, -25.5f)
            reflectiveQuadToRelative(-6f, -30.5f)
            verticalLineToRelative(-187f)
            quadToRelative(0f, -33f, 23.5f, -56.5f)
            reflectiveQuadTo(320f, 280f)
            horizontalLineToRelative(40f)
            lineToRelative(-40f, 40f)
            verticalLineToRelative(-160f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(360f, 120f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(400f, 160f)
            verticalLineToRelative(120f)
            horizontalLineToRelative(160f)
            verticalLineToRelative(-120f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(600f, 120f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(640f, 160f)
            verticalLineToRelative(160f)
            lineToRelative(-40f, -40f)
            horizontalLineToRelative(40f)
            quadToRelative(33f, 0f, 56.5f, 23.5f)
            reflectiveQuadTo(720f, 360f)
            verticalLineToRelative(187f)
            quadToRelative(0f, 16f, -6f, 30.5f)
            reflectiveQuadTo(697f, 603f)
            lineTo(580f, 720f)
            verticalLineToRelative(80f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(540f, 840f)
            lineTo(420f, 840f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(380f, 800f)
            close()
          }
        }
        .build()

    return _PowerFill!!
  }

@Suppress("ObjectPropertyName") private var _PowerFill: ImageVector? = null
