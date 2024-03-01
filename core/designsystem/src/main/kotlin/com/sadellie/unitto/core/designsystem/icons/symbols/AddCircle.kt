package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.AddCircle: ImageVector
  get() {
    if (_AddCircle != null) {
      return _AddCircle!!
    }
    _AddCircle = ImageVector.Builder(
      name = "AddCircle",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(440f, 520f)
        verticalLineToRelative(120f)
        quadToRelative(0f, 17f, 11.5f, 28.5f)
        reflectiveQuadTo(480f, 680f)
        quadToRelative(17f, 0f, 28.5f, -11.5f)
        reflectiveQuadTo(520f, 640f)
        verticalLineToRelative(-120f)
        horizontalLineToRelative(120f)
        quadToRelative(17f, 0f, 28.5f, -11.5f)
        reflectiveQuadTo(680f, 480f)
        quadToRelative(0f, -17f, -11.5f, -28.5f)
        reflectiveQuadTo(640f, 440f)
        lineTo(520f, 440f)
        verticalLineToRelative(-120f)
        quadToRelative(0f, -17f, -11.5f, -28.5f)
        reflectiveQuadTo(480f, 280f)
        quadToRelative(-17f, 0f, -28.5f, 11.5f)
        reflectiveQuadTo(440f, 320f)
        verticalLineToRelative(120f)
        lineTo(320f, 440f)
        quadToRelative(-17f, 0f, -28.5f, 11.5f)
        reflectiveQuadTo(280f, 480f)
        quadToRelative(0f, 17f, 11.5f, 28.5f)
        reflectiveQuadTo(320f, 520f)
        horizontalLineToRelative(120f)
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
        moveTo(480f, 800f)
        quadToRelative(134f, 0f, 227f, -93f)
        reflectiveQuadToRelative(93f, -227f)
        quadToRelative(0f, -134f, -93f, -227f)
        reflectiveQuadToRelative(-227f, -93f)
        quadToRelative(-134f, 0f, -227f, 93f)
        reflectiveQuadToRelative(-93f, 227f)
        quadToRelative(0f, 134f, 93f, 227f)
        reflectiveQuadToRelative(227f, 93f)
        close()
        moveTo(480f, 480f)
        close()
      }
    }.build()

    return _AddCircle!!
  }

@Suppress("ObjectPropertyName")
private var _AddCircle: ImageVector? = null
