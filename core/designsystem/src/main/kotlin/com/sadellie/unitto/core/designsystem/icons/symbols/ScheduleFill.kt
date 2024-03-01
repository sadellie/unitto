package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.ScheduleFill: ImageVector
  get() {
    if (_ScheduleFill != null) {
      return _ScheduleFill!!
    }
    _ScheduleFill = ImageVector.Builder(
      name = "ScheduleFill",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(520f, 464f)
        verticalLineToRelative(-144f)
        quadToRelative(0f, -17f, -11.5f, -28.5f)
        reflectiveQuadTo(480f, 280f)
        quadToRelative(-17f, 0f, -28.5f, 11.5f)
        reflectiveQuadTo(440f, 320f)
        verticalLineToRelative(159f)
        quadToRelative(0f, 8f, 3f, 15.5f)
        reflectiveQuadToRelative(9f, 13.5f)
        lineToRelative(132f, 132f)
        quadToRelative(11f, 11f, 28f, 11f)
        reflectiveQuadToRelative(28f, -11f)
        quadToRelative(11f, -11f, 11f, -28f)
        reflectiveQuadToRelative(-11f, -28f)
        lineTo(520f, 464f)
        close()
        moveTo(480f, 880f)
        quadToRelative(-83f, 0f, -156f, -31.5f)
        reflectiveQuadTo(197f, 763f)
        quadToRelative(-54f, -54f, -85.5f, -127f)
        reflectiveQuadTo(80f, 480f)
        quadToRelative(0f, -83f, 31.5f, -156f)
        reflectiveQuadTo(197f, 197f)
        quadToRelative(54f, -54f, 127f, -85.5f)
        reflectiveQuadTo(480f, 80f)
        quadToRelative(83f, 0f, 156f, 31.5f)
        reflectiveQuadTo(763f, 197f)
        quadToRelative(54f, 54f, 85.5f, 127f)
        reflectiveQuadTo(880f, 480f)
        quadToRelative(0f, 83f, -31.5f, 156f)
        reflectiveQuadTo(763f, 763f)
        quadToRelative(-54f, 54f, -127f, 85.5f)
        reflectiveQuadTo(480f, 880f)
        close()
      }
    }.build()

    return _ScheduleFill!!
  }

@Suppress("ObjectPropertyName")
private var _ScheduleFill: ImageVector? = null
