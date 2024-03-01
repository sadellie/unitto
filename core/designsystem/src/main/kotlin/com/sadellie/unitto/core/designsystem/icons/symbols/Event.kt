package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.Event: ImageVector
  get() {
    if (_Event != null) {
      return _Event!!
    }
    _Event = ImageVector.Builder(
      name = "Event",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(580f, 720f)
        quadToRelative(-42f, 0f, -71f, -29f)
        reflectiveQuadToRelative(-29f, -71f)
        quadToRelative(0f, -42f, 29f, -71f)
        reflectiveQuadToRelative(71f, -29f)
        quadToRelative(42f, 0f, 71f, 29f)
        reflectiveQuadToRelative(29f, 71f)
        quadToRelative(0f, 42f, -29f, 71f)
        reflectiveQuadToRelative(-71f, 29f)
        close()
        moveTo(200f, 880f)
        quadToRelative(-33f, 0f, -56.5f, -23.5f)
        reflectiveQuadTo(120f, 800f)
        verticalLineToRelative(-560f)
        quadToRelative(0f, -33f, 23.5f, -56.5f)
        reflectiveQuadTo(200f, 160f)
        horizontalLineToRelative(40f)
        verticalLineToRelative(-40f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(280f, 80f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(320f, 120f)
        verticalLineToRelative(40f)
        horizontalLineToRelative(320f)
        verticalLineToRelative(-40f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(680f, 80f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(720f, 120f)
        verticalLineToRelative(40f)
        horizontalLineToRelative(40f)
        quadToRelative(33f, 0f, 56.5f, 23.5f)
        reflectiveQuadTo(840f, 240f)
        verticalLineToRelative(560f)
        quadToRelative(0f, 33f, -23.5f, 56.5f)
        reflectiveQuadTo(760f, 880f)
        lineTo(200f, 880f)
        close()
        moveTo(200f, 800f)
        horizontalLineToRelative(560f)
        verticalLineToRelative(-400f)
        lineTo(200f, 400f)
        verticalLineToRelative(400f)
        close()
        moveTo(200f, 320f)
        horizontalLineToRelative(560f)
        verticalLineToRelative(-80f)
        lineTo(200f, 240f)
        verticalLineToRelative(80f)
        close()
        moveTo(200f, 320f)
        verticalLineToRelative(-80f)
        verticalLineToRelative(80f)
        close()
      }
    }.build()

    return _Event!!
  }

@Suppress("ObjectPropertyName")
private var _Event: ImageVector? = null
