package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.LocalGasStationFill: ImageVector
  get() {
    if (_LocalGasStationFill != null) {
      return _LocalGasStationFill!!
    }
    _LocalGasStationFill =
      ImageVector.Builder(
          name = "LocalGasStationFill",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(160f, 800f)
            verticalLineToRelative(-600f)
            quadToRelative(0f, -33f, 23.5f, -56.5f)
            reflectiveQuadTo(240f, 120f)
            horizontalLineToRelative(240f)
            quadToRelative(33f, 0f, 56.5f, 23.5f)
            reflectiveQuadTo(560f, 200f)
            verticalLineToRelative(280f)
            horizontalLineToRelative(40f)
            quadToRelative(33f, 0f, 56.5f, 23.5f)
            reflectiveQuadTo(680f, 560f)
            verticalLineToRelative(180f)
            quadToRelative(0f, 17f, 11.5f, 28.5f)
            reflectiveQuadTo(720f, 780f)
            quadToRelative(17f, 0f, 28.5f, -11.5f)
            reflectiveQuadTo(760f, 740f)
            verticalLineToRelative(-288f)
            quadToRelative(-9f, 5f, -19f, 6.5f)
            reflectiveQuadToRelative(-21f, 1.5f)
            quadToRelative(-42f, 0f, -71f, -29f)
            reflectiveQuadToRelative(-29f, -71f)
            quadToRelative(0f, -32f, 17.5f, -57.5f)
            reflectiveQuadTo(684f, 266f)
            lineToRelative(-63f, -63f)
            quadToRelative(-9f, -9f, -9f, -21f)
            reflectiveQuadToRelative(9f, -21f)
            quadToRelative(8f, -8f, 20.5f, -8.5f)
            reflectiveQuadTo(663f, 160f)
            lineToRelative(127f, 124f)
            quadToRelative(15f, 15f, 22.5f, 35f)
            reflectiveQuadToRelative(7.5f, 41f)
            verticalLineToRelative(380f)
            quadToRelative(0f, 42f, -29f, 71f)
            reflectiveQuadToRelative(-71f, 29f)
            quadToRelative(-42f, 0f, -71f, -29f)
            reflectiveQuadToRelative(-29f, -71f)
            verticalLineToRelative(-200f)
            horizontalLineToRelative(-60f)
            verticalLineToRelative(260f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(520f, 840f)
            lineTo(200f, 840f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(160f, 800f)
            close()
            moveTo(240f, 400f)
            horizontalLineToRelative(240f)
            verticalLineToRelative(-200f)
            lineTo(240f, 200f)
            verticalLineToRelative(200f)
            close()
            moveTo(720f, 400f)
            quadToRelative(17f, 0f, 28.5f, -11.5f)
            reflectiveQuadTo(760f, 360f)
            quadToRelative(0f, -17f, -11.5f, -28.5f)
            reflectiveQuadTo(720f, 320f)
            quadToRelative(-17f, 0f, -28.5f, 11.5f)
            reflectiveQuadTo(680f, 360f)
            quadToRelative(0f, 17f, 11.5f, 28.5f)
            reflectiveQuadTo(720f, 400f)
            close()
          }
        }
        .build()

    return _LocalGasStationFill!!
  }

@Suppress("ObjectPropertyName") private var _LocalGasStationFill: ImageVector? = null
