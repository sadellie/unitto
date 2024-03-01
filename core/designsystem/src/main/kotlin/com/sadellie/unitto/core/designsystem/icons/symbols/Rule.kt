package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.Rule: ImageVector
  get() {
    if (_Rule != null) {
      return _Rule!!
    }
    _Rule = ImageVector.Builder(
      name = "Rule",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveToRelative(680f, 696f)
        lineToRelative(-76f, 76f)
        quadToRelative(-11f, 11f, -28f, 11f)
        reflectiveQuadToRelative(-28f, -11f)
        quadToRelative(-11f, -11f, -11f, -28f)
        reflectiveQuadToRelative(11f, -28f)
        lineToRelative(76f, -76f)
        lineToRelative(-76f, -76f)
        quadToRelative(-11f, -11f, -11f, -28f)
        reflectiveQuadToRelative(11f, -28f)
        quadToRelative(11f, -11f, 28f, -11f)
        reflectiveQuadToRelative(28f, 11f)
        lineToRelative(76f, 76f)
        lineToRelative(76f, -76f)
        quadToRelative(11f, -11f, 28f, -11f)
        reflectiveQuadToRelative(28f, 11f)
        quadToRelative(11f, 11f, 11f, 28f)
        reflectiveQuadToRelative(-11f, 28f)
        lineToRelative(-76f, 76f)
        lineToRelative(76f, 76f)
        quadToRelative(11f, 11f, 11f, 28f)
        reflectiveQuadToRelative(-11f, 28f)
        quadToRelative(-11f, 11f, -28f, 11f)
        reflectiveQuadToRelative(-28f, -11f)
        lineToRelative(-76f, -76f)
        close()
        moveTo(654f, 327f)
        lineTo(796f, 185f)
        quadToRelative(12f, -12f, 28f, -11.5f)
        reflectiveQuadToRelative(28f, 12.5f)
        quadToRelative(11f, 12f, 11f, 28f)
        reflectiveQuadToRelative(-11f, 28f)
        lineTo(683f, 412f)
        quadToRelative(-12f, 12f, -28f, 12f)
        reflectiveQuadToRelative(-28f, -12f)
        lineToRelative(-86f, -86f)
        quadToRelative(-11f, -11f, -11f, -28f)
        reflectiveQuadToRelative(11f, -28f)
        quadToRelative(11f, -11f, 28f, -11f)
        reflectiveQuadToRelative(28f, 11f)
        lineToRelative(57f, 57f)
        close()
        moveTo(120f, 600f)
        horizontalLineToRelative(280f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(440f, 640f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(400f, 680f)
        lineTo(120f, 680f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(80f, 640f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(120f, 600f)
        close()
        moveTo(120f, 280f)
        horizontalLineToRelative(280f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(440f, 320f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(400f, 360f)
        lineTo(120f, 360f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(80f, 320f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(120f, 280f)
        close()
      }
    }.build()

    return _Rule!!
  }

@Suppress("ObjectPropertyName")
private var _Rule: ImageVector? = null
