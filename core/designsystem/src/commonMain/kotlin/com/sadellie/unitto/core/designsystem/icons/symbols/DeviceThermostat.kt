package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.DeviceThermostat: ImageVector
  get() {
    if (_DeviceThermostat != null) {
      return _DeviceThermostat!!
    }
    _DeviceThermostat =
      ImageVector.Builder(
          name = "DeviceThermostat",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(338.5f, 821.5f)
            quadTo(280f, 763f, 280f, 680f)
            quadToRelative(0f, -48f, 21f, -89.5f)
            reflectiveQuadToRelative(59f, -70.5f)
            verticalLineToRelative(-320f)
            quadToRelative(0f, -50f, 35f, -85f)
            reflectiveQuadToRelative(85f, -35f)
            quadToRelative(50f, 0f, 85f, 35f)
            reflectiveQuadToRelative(35f, 85f)
            verticalLineToRelative(320f)
            quadToRelative(38f, 29f, 59f, 70.5f)
            reflectiveQuadToRelative(21f, 89.5f)
            quadToRelative(0f, 83f, -58.5f, 141.5f)
            reflectiveQuadTo(480f, 880f)
            quadToRelative(-83f, 0f, -141.5f, -58.5f)
            close()
            moveTo(440f, 440f)
            horizontalLineToRelative(80f)
            verticalLineToRelative(-40f)
            horizontalLineToRelative(-40f)
            verticalLineToRelative(-40f)
            horizontalLineToRelative(40f)
            verticalLineToRelative(-80f)
            horizontalLineToRelative(-40f)
            verticalLineToRelative(-40f)
            horizontalLineToRelative(40f)
            verticalLineToRelative(-40f)
            quadToRelative(0f, -17f, -11.5f, -28.5f)
            reflectiveQuadTo(480f, 160f)
            quadToRelative(-17f, 0f, -28.5f, 11.5f)
            reflectiveQuadTo(440f, 200f)
            verticalLineToRelative(240f)
            close()
          }
        }
        .build()

    return _DeviceThermostat!!
  }

@Suppress("ObjectPropertyName") private var _DeviceThermostat: ImageVector? = null
