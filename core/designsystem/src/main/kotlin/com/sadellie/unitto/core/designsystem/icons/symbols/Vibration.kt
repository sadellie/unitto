package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.Vibration: ImageVector
  get() {
    if (_Vibration != null) {
      return _Vibration!!
    }
    _Vibration = ImageVector.Builder(
      name = "Vibration",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(40f, 360f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(80f, 400f)
        verticalLineToRelative(160f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(40f, 600f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(0f, 560f)
        verticalLineToRelative(-160f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(40f, 360f)
        close()
        moveTo(160f, 280f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(200f, 320f)
        verticalLineToRelative(320f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(160f, 680f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(120f, 640f)
        verticalLineToRelative(-320f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(160f, 280f)
        close()
        moveTo(920f, 360f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(960f, 400f)
        verticalLineToRelative(160f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(920f, 600f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(880f, 560f)
        verticalLineToRelative(-160f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(920f, 360f)
        close()
        moveTo(800f, 280f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(840f, 320f)
        verticalLineToRelative(320f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(800f, 680f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(760f, 640f)
        verticalLineToRelative(-320f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(800f, 280f)
        close()
        moveTo(320f, 840f)
        quadToRelative(-33f, 0f, -56.5f, -23.5f)
        reflectiveQuadTo(240f, 760f)
        verticalLineToRelative(-560f)
        quadToRelative(0f, -33f, 23.5f, -56.5f)
        reflectiveQuadTo(320f, 120f)
        horizontalLineToRelative(320f)
        quadToRelative(33f, 0f, 56.5f, 23.5f)
        reflectiveQuadTo(720f, 200f)
        verticalLineToRelative(560f)
        quadToRelative(0f, 33f, -23.5f, 56.5f)
        reflectiveQuadTo(640f, 840f)
        lineTo(320f, 840f)
        close()
        moveTo(320f, 760f)
        horizontalLineToRelative(320f)
        verticalLineToRelative(-560f)
        lineTo(320f, 200f)
        verticalLineToRelative(560f)
        close()
        moveTo(320f, 760f)
        verticalLineToRelative(-560f)
        verticalLineToRelative(560f)
        close()
      }
    }.build()

    return _Vibration!!
  }

@Suppress("ObjectPropertyName")
private var _Vibration: ImageVector? = null
