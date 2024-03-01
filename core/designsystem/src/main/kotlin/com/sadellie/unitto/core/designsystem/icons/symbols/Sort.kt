package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.Sort: ImageVector
  get() {
    if (_Sort != null) {
      return _Sort!!
    }
    _Sort = ImageVector.Builder(
      name = "Sort",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(160f, 720f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(120f, 680f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(160f, 640f)
        horizontalLineToRelative(160f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(360f, 680f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(320f, 720f)
        lineTo(160f, 720f)
        close()
        moveTo(160f, 520f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(120f, 480f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(160f, 440f)
        horizontalLineToRelative(400f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(600f, 480f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(560f, 520f)
        lineTo(160f, 520f)
        close()
        moveTo(160f, 320f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(120f, 280f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(160f, 240f)
        horizontalLineToRelative(640f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(840f, 280f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(800f, 320f)
        lineTo(160f, 320f)
        close()
      }
    }.build()

    return _Sort!!
  }

@Suppress("ObjectPropertyName")
private var _Sort: ImageVector? = null
