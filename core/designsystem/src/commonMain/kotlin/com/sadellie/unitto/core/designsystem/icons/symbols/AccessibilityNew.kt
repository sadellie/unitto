package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.AccessibilityNew: ImageVector
  get() {
    if (_AccessibilityNew != null) {
      return _AccessibilityNew!!
    }
    _AccessibilityNew = ImageVector.Builder(
      name = "AccessibilityNew",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(480f, 240f)
        quadToRelative(-33f, 0f, -56.5f, -23.5f)
        reflectiveQuadTo(400f, 160f)
        quadToRelative(0f, -33f, 23.5f, -56.5f)
        reflectiveQuadTo(480f, 80f)
        quadToRelative(33f, 0f, 56.5f, 23.5f)
        reflectiveQuadTo(560f, 160f)
        quadToRelative(0f, 33f, -23.5f, 56.5f)
        reflectiveQuadTo(480f, 240f)
        close()
        moveTo(360f, 840f)
        verticalLineToRelative(-480f)
        quadToRelative(-49f, -4f, -99f, -11f)
        reflectiveQuadToRelative(-98f, -18f)
        quadToRelative(-17f, -4f, -27.5f, -19f)
        reflectiveQuadToRelative(-5.5f, -32f)
        quadToRelative(5f, -17f, 21f, -25f)
        reflectiveQuadToRelative(34f, -4f)
        quadToRelative(70f, 15f, 145.5f, 22f)
        reflectiveQuadToRelative(149.5f, 7f)
        quadToRelative(74f, 0f, 149.5f, -7f)
        reflectiveQuadTo(775f, 251f)
        quadToRelative(18f, -4f, 34f, 4f)
        reflectiveQuadToRelative(21f, 25f)
        quadToRelative(5f, 17f, -5.5f, 32f)
        reflectiveQuadTo(797f, 331f)
        quadToRelative(-48f, 11f, -98f, 18f)
        reflectiveQuadToRelative(-99f, 11f)
        verticalLineToRelative(480f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(560f, 880f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(520f, 840f)
        verticalLineToRelative(-200f)
        horizontalLineToRelative(-80f)
        verticalLineToRelative(200f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(400f, 880f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(360f, 840f)
        close()
      }
    }.build()

    return _AccessibilityNew!!
  }

@Suppress("ObjectPropertyName")
private var _AccessibilityNew: ImageVector? = null
