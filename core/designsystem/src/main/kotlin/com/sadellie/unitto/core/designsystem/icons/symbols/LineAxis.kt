package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.LineAxis: ImageVector
  get() {
    if (_LineAxis != null) {
      return _LineAxis!!
    }
    _LineAxis = ImageVector.Builder(
      name = "LineAxis",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveToRelative(110f, 670f)
        lineToRelative(213f, -213f)
        quadToRelative(23f, -23f, 57f, -23f)
        reflectiveQuadToRelative(57f, 23f)
        lineToRelative(103f, 103f)
        lineToRelative(67f, -76f)
        lineToRelative(-224f, -207f)
        lineToRelative(-213f, 213f)
        quadToRelative(-13f, 13f, -30f, 13f)
        reflectiveQuadToRelative(-30f, -13f)
        quadToRelative(-13f, -13f, -13f, -30f)
        reflectiveQuadToRelative(13f, -30f)
        lineToRelative(216f, -216f)
        quadToRelative(23f, -23f, 54.5f, -23.5f)
        reflectiveQuadTo(436f, 212f)
        lineToRelative(228f, 209f)
        lineToRelative(132f, -150f)
        quadToRelative(11f, -13f, 28.5f, -13f)
        reflectiveQuadToRelative(29.5f, 12f)
        quadToRelative(11f, 11f, 11.5f, 26.5f)
        reflectiveQuadTo(855f, 324f)
        lineTo(722f, 474f)
        lineToRelative(126f, 116f)
        quadToRelative(14f, 12f, 14f, 30f)
        reflectiveQuadToRelative(-13f, 31f)
        quadToRelative(-12f, 12f, -29f, 12f)
        reflectiveQuadToRelative(-30f, -11f)
        lineTo(666f, 538f)
        lineToRelative(-70f, 78f)
        quadToRelative(-23f, 26f, -57f, 27.5f)
        reflectiveQuadTo(480f, 620f)
        lineTo(380f, 520f)
        lineTo(170f, 730f)
        quadToRelative(-13f, 13f, -30f, 13f)
        reflectiveQuadToRelative(-30f, -13f)
        quadToRelative(-13f, -13f, -13f, -30f)
        reflectiveQuadToRelative(13f, -30f)
        close()
      }
    }.build()

    return _LineAxis!!
  }

@Suppress("ObjectPropertyName")
private var _LineAxis: ImageVector? = null
