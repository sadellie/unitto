package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.CenterFocusWeak: ImageVector
  get() {
    if (_CenterFocusWeak != null) {
      return _CenterFocusWeak!!
    }
    _CenterFocusWeak = ImageVector.Builder(
      name = "CenterFocusWeak",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(200f, 840f)
        quadToRelative(-33f, 0f, -56.5f, -23.5f)
        reflectiveQuadTo(120f, 760f)
        verticalLineToRelative(-120f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(160f, 600f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(200f, 640f)
        verticalLineToRelative(120f)
        horizontalLineToRelative(120f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(360f, 800f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(320f, 840f)
        lineTo(200f, 840f)
        close()
        moveTo(760f, 840f)
        lineTo(640f, 840f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(600f, 800f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(640f, 760f)
        horizontalLineToRelative(120f)
        verticalLineToRelative(-120f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(800f, 600f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(840f, 640f)
        verticalLineToRelative(120f)
        quadToRelative(0f, 33f, -23.5f, 56.5f)
        reflectiveQuadTo(760f, 840f)
        close()
        moveTo(120f, 320f)
        verticalLineToRelative(-120f)
        quadToRelative(0f, -33f, 23.5f, -56.5f)
        reflectiveQuadTo(200f, 120f)
        horizontalLineToRelative(120f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(360f, 160f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(320f, 200f)
        lineTo(200f, 200f)
        verticalLineToRelative(120f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(160f, 360f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(120f, 320f)
        close()
        moveTo(760f, 320f)
        verticalLineToRelative(-120f)
        lineTo(640f, 200f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(600f, 160f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(640f, 120f)
        horizontalLineToRelative(120f)
        quadToRelative(33f, 0f, 56.5f, 23.5f)
        reflectiveQuadTo(840f, 200f)
        verticalLineToRelative(120f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(800f, 360f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(760f, 320f)
        close()
        moveTo(480f, 640f)
        quadToRelative(-66f, 0f, -113f, -47f)
        reflectiveQuadToRelative(-47f, -113f)
        quadToRelative(0f, -66f, 47f, -113f)
        reflectiveQuadToRelative(113f, -47f)
        quadToRelative(66f, 0f, 113f, 47f)
        reflectiveQuadToRelative(47f, 113f)
        quadToRelative(0f, 66f, -47f, 113f)
        reflectiveQuadToRelative(-113f, 47f)
        close()
        moveTo(480f, 560f)
        quadToRelative(33f, 0f, 56.5f, -23.5f)
        reflectiveQuadTo(560f, 480f)
        quadToRelative(0f, -33f, -23.5f, -56.5f)
        reflectiveQuadTo(480f, 400f)
        quadToRelative(-33f, 0f, -56.5f, 23.5f)
        reflectiveQuadTo(400f, 480f)
        quadToRelative(0f, 33f, 23.5f, 56.5f)
        reflectiveQuadTo(480f, 560f)
        close()
        moveTo(480f, 480f)
        close()
      }
    }.build()

    return _CenterFocusWeak!!
  }

@Suppress("ObjectPropertyName")
private var _CenterFocusWeak: ImageVector? = null
