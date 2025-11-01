package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.HdrAuto: ImageVector
  get() {
    if (_HdrAuto != null) {
      return _HdrAuto!!
    }
    _HdrAuto = ImageVector.Builder(
      name = "HdrAuto",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(392f, 568f)
        horizontalLineToRelative(176f)
        lineToRelative(32f, 89f)
        quadToRelative(4f, 11f, 13f, 17f)
        reflectiveQuadToRelative(20f, 6f)
        quadToRelative(19f, 0f, 29.5f, -15.5f)
        reflectiveQuadTo(666f, 632f)
        lineTo(529f, 264f)
        quadToRelative(-4f, -11f, -13.5f, -17.5f)
        reflectiveQuadTo(494f, 240f)
        horizontalLineToRelative(-28f)
        quadToRelative(-12f, 0f, -21.5f, 6.5f)
        reflectiveQuadTo(431f, 264f)
        lineTo(294f, 633f)
        quadToRelative(-7f, 17f, 3.5f, 32f)
        reflectiveQuadToRelative(29.5f, 15f)
        quadToRelative(11f, 0f, 20f, -6.5f)
        reflectiveQuadToRelative(13f, -17.5f)
        lineToRelative(32f, -88f)
        close()
        moveTo(414f, 504f)
        lineTo(478f, 322f)
        horizontalLineToRelative(4f)
        lineToRelative(64f, 182f)
        lineTo(414f, 504f)
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
        moveTo(480f, 480f)
        close()
        moveTo(480f, 800f)
        quadToRelative(133f, 0f, 226.5f, -93.5f)
        reflectiveQuadTo(800f, 480f)
        quadToRelative(0f, -133f, -93.5f, -226.5f)
        reflectiveQuadTo(480f, 160f)
        quadToRelative(-133f, 0f, -226.5f, 93.5f)
        reflectiveQuadTo(160f, 480f)
        quadToRelative(0f, 133f, 93.5f, 226.5f)
        reflectiveQuadTo(480f, 800f)
        close()
      }
    }.build()

    return _HdrAuto!!
  }

@Suppress("ObjectPropertyName")
private var _HdrAuto: ImageVector? = null
