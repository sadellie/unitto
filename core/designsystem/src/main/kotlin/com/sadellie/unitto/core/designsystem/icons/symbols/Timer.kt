package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.Timer: ImageVector
  get() {
    if (_Timer != null) {
      return _Timer!!
    }
    _Timer = ImageVector.Builder(
      name = "Timer",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(400f, 120f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(360f, 80f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(400f, 40f)
        horizontalLineToRelative(160f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(600f, 80f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(560f, 120f)
        lineTo(400f, 120f)
        close()
        moveTo(480f, 560f)
        quadToRelative(17f, 0f, 28.5f, -11.5f)
        reflectiveQuadTo(520f, 520f)
        verticalLineToRelative(-160f)
        quadToRelative(0f, -17f, -11.5f, -28.5f)
        reflectiveQuadTo(480f, 320f)
        quadToRelative(-17f, 0f, -28.5f, 11.5f)
        reflectiveQuadTo(440f, 360f)
        verticalLineToRelative(160f)
        quadToRelative(0f, 17f, 11.5f, 28.5f)
        reflectiveQuadTo(480f, 560f)
        close()
        moveTo(480f, 880f)
        quadToRelative(-74f, 0f, -139.5f, -28.5f)
        reflectiveQuadTo(226f, 774f)
        quadToRelative(-49f, -49f, -77.5f, -114.5f)
        reflectiveQuadTo(120f, 520f)
        quadToRelative(0f, -74f, 28.5f, -139.5f)
        reflectiveQuadTo(226f, 266f)
        quadToRelative(49f, -49f, 114.5f, -77.5f)
        reflectiveQuadTo(480f, 160f)
        quadToRelative(62f, 0f, 119f, 20f)
        reflectiveQuadToRelative(107f, 58f)
        lineToRelative(28f, -28f)
        quadToRelative(11f, -11f, 28f, -11f)
        reflectiveQuadToRelative(28f, 11f)
        quadToRelative(11f, 11f, 11f, 28f)
        reflectiveQuadToRelative(-11f, 28f)
        lineToRelative(-28f, 28f)
        quadToRelative(38f, 50f, 58f, 107f)
        reflectiveQuadToRelative(20f, 119f)
        quadToRelative(0f, 74f, -28.5f, 139.5f)
        reflectiveQuadTo(734f, 774f)
        quadToRelative(-49f, 49f, -114.5f, 77.5f)
        reflectiveQuadTo(480f, 880f)
        close()
        moveTo(480f, 800f)
        quadToRelative(116f, 0f, 198f, -82f)
        reflectiveQuadToRelative(82f, -198f)
        quadToRelative(0f, -116f, -82f, -198f)
        reflectiveQuadToRelative(-198f, -82f)
        quadToRelative(-116f, 0f, -198f, 82f)
        reflectiveQuadToRelative(-82f, 198f)
        quadToRelative(0f, 116f, 82f, 198f)
        reflectiveQuadToRelative(198f, 82f)
        close()
        moveTo(480f, 520f)
        close()
      }
    }.build()

    return _Timer!!
  }

@Suppress("ObjectPropertyName")
private var _Timer: ImageVector? = null
