package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.Colorize: ImageVector
  get() {
    if (_Colorize != null) {
      return _Colorize!!
    }
    _Colorize = ImageVector.Builder(
      name = "Colorize",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(120f, 800f)
        verticalLineToRelative(-117f)
        quadToRelative(0f, -16f, 6f, -30.5f)
        reflectiveQuadToRelative(17f, -25.5f)
        lineToRelative(335f, -335f)
        lineToRelative(-58f, -56f)
        lineToRelative(58f, -56f)
        lineToRelative(76f, 76f)
        lineToRelative(124f, -124f)
        quadToRelative(5f, -5f, 12.5f, -8f)
        reflectiveQuadToRelative(15.5f, -3f)
        quadToRelative(8f, 0f, 15f, 3f)
        reflectiveQuadToRelative(13f, 8f)
        lineToRelative(94f, 94f)
        quadToRelative(5f, 6f, 8f, 13f)
        reflectiveQuadToRelative(3f, 15f)
        quadToRelative(0f, 8f, -3f, 15.5f)
        reflectiveQuadToRelative(-8f, 12.5f)
        lineTo(705f, 405f)
        lineToRelative(76f, 78f)
        lineToRelative(-57f, 57f)
        lineToRelative(-56f, -58f)
        lineToRelative(-335f, 335f)
        quadToRelative(-11f, 11f, -25.5f, 17f)
        reflectiveQuadToRelative(-30.5f, 6f)
        lineTo(160f, 840f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(120f, 800f)
        close()
        moveTo(200f, 760f)
        horizontalLineToRelative(78f)
        lineToRelative(332f, -334f)
        lineToRelative(-76f, -76f)
        lineToRelative(-334f, 332f)
        verticalLineToRelative(78f)
        close()
        moveTo(647f, 350f)
        lineTo(743f, 254f)
        lineTo(706f, 217f)
        lineTo(610f, 313f)
        lineTo(647f, 350f)
        close()
        moveTo(647f, 350f)
        lineTo(610f, 313f)
        lineTo(647f, 350f)
        close()
      }
    }.build()

    return _Colorize!!
  }

@Suppress("ObjectPropertyName")
private var _Colorize: ImageVector? = null
