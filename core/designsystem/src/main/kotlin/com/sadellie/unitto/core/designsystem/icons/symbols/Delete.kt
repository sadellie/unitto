package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.Delete: ImageVector
  get() {
    if (_Delete != null) {
      return _Delete!!
    }
    _Delete = ImageVector.Builder(
      name = "Delete",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(280f, 840f)
        quadToRelative(-33f, 0f, -56.5f, -23.5f)
        reflectiveQuadTo(200f, 760f)
        verticalLineToRelative(-520f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(160f, 200f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(200f, 160f)
        horizontalLineToRelative(160f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(400f, 120f)
        horizontalLineToRelative(160f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(600f, 160f)
        horizontalLineToRelative(160f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(800f, 200f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(760f, 240f)
        verticalLineToRelative(520f)
        quadToRelative(0f, 33f, -23.5f, 56.5f)
        reflectiveQuadTo(680f, 840f)
        lineTo(280f, 840f)
        close()
        moveTo(680f, 240f)
        lineTo(280f, 240f)
        verticalLineToRelative(520f)
        horizontalLineToRelative(400f)
        verticalLineToRelative(-520f)
        close()
        moveTo(400f, 680f)
        quadToRelative(17f, 0f, 28.5f, -11.5f)
        reflectiveQuadTo(440f, 640f)
        verticalLineToRelative(-280f)
        quadToRelative(0f, -17f, -11.5f, -28.5f)
        reflectiveQuadTo(400f, 320f)
        quadToRelative(-17f, 0f, -28.5f, 11.5f)
        reflectiveQuadTo(360f, 360f)
        verticalLineToRelative(280f)
        quadToRelative(0f, 17f, 11.5f, 28.5f)
        reflectiveQuadTo(400f, 680f)
        close()
        moveTo(560f, 680f)
        quadToRelative(17f, 0f, 28.5f, -11.5f)
        reflectiveQuadTo(600f, 640f)
        verticalLineToRelative(-280f)
        quadToRelative(0f, -17f, -11.5f, -28.5f)
        reflectiveQuadTo(560f, 320f)
        quadToRelative(-17f, 0f, -28.5f, 11.5f)
        reflectiveQuadTo(520f, 360f)
        verticalLineToRelative(280f)
        quadToRelative(0f, 17f, 11.5f, 28.5f)
        reflectiveQuadTo(560f, 680f)
        close()
        moveTo(280f, 240f)
        verticalLineToRelative(520f)
        verticalLineToRelative(-520f)
        close()
      }
    }.build()

    return _Delete!!
  }

@Suppress("ObjectPropertyName")
private var _Delete: ImageVector? = null
