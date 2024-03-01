package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.Copyright: ImageVector
  get() {
    if (_Copyright != null) {
      return _Copyright!!
    }
    _Copyright = ImageVector.Builder(
      name = "Copyright",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
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
        moveTo(400f, 640f)
        horizontalLineToRelative(160f)
        quadToRelative(17f, 0f, 28.5f, -11.5f)
        reflectiveQuadTo(600f, 600f)
        verticalLineToRelative(-40f)
        quadToRelative(0f, -17f, -11.5f, -28.5f)
        reflectiveQuadTo(560f, 520f)
        quadToRelative(-17f, 0f, -28.5f, 11.5f)
        reflectiveQuadTo(520f, 560f)
        horizontalLineToRelative(-80f)
        verticalLineToRelative(-160f)
        horizontalLineToRelative(80f)
        quadToRelative(0f, 17f, 11.5f, 28.5f)
        reflectiveQuadTo(560f, 440f)
        quadToRelative(17f, 0f, 28.5f, -11.5f)
        reflectiveQuadTo(600f, 400f)
        verticalLineToRelative(-40f)
        quadToRelative(0f, -17f, -11.5f, -28.5f)
        reflectiveQuadTo(560f, 320f)
        lineTo(400f, 320f)
        quadToRelative(-17f, 0f, -28.5f, 11.5f)
        reflectiveQuadTo(360f, 360f)
        verticalLineToRelative(240f)
        quadToRelative(0f, 17f, 11.5f, 28.5f)
        reflectiveQuadTo(400f, 640f)
        close()
      }
    }.build()

    return _Copyright!!
  }

@Suppress("ObjectPropertyName")
private var _Copyright: ImageVector? = null
