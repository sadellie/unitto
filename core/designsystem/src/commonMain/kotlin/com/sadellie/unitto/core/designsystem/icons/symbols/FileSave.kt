package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.FileSave: ImageVector
  get() {
    if (_FileSave != null) {
      return _FileSave!!
    }
    _FileSave = ImageVector.Builder(
      name = "FileSave",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveToRelative(720f, 840f)
        lineToRelative(160f, -160f)
        lineToRelative(-56f, -56f)
        lineToRelative(-64f, 64f)
        verticalLineToRelative(-167f)
        horizontalLineToRelative(-80f)
        verticalLineToRelative(167f)
        lineToRelative(-64f, -64f)
        lineToRelative(-56f, 56f)
        lineToRelative(160f, 160f)
        close()
        moveTo(560f, 960f)
        verticalLineToRelative(-80f)
        horizontalLineToRelative(320f)
        lineTo(880f, 960f)
        lineTo(560f, 960f)
        close()
        moveTo(240f, 800f)
        quadToRelative(-33f, 0f, -56.5f, -23.5f)
        reflectiveQuadTo(160f, 720f)
        verticalLineToRelative(-560f)
        quadToRelative(0f, -33f, 23.5f, -56.5f)
        reflectiveQuadTo(240f, 80f)
        horizontalLineToRelative(280f)
        lineToRelative(240f, 240f)
        verticalLineToRelative(121f)
        horizontalLineToRelative(-80f)
        verticalLineToRelative(-81f)
        lineTo(480f, 360f)
        verticalLineToRelative(-200f)
        lineTo(240f, 160f)
        verticalLineToRelative(560f)
        horizontalLineToRelative(240f)
        verticalLineToRelative(80f)
        lineTo(240f, 800f)
        close()
        moveTo(240f, 720f)
        verticalLineToRelative(-560f)
        verticalLineToRelative(560f)
        close()
      }
    }.build()

    return _FileSave!!
  }

@Suppress("ObjectPropertyName")
private var _FileSave: ImageVector? = null
