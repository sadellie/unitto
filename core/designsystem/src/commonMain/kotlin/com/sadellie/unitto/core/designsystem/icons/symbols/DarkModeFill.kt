package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.DarkModeFill: ImageVector
  get() {
    if (_DarkModeFill != null) {
      return _DarkModeFill!!
    }
    _DarkModeFill = ImageVector.Builder(
      name = "DarkModeFill",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(480f, 840f)
        quadToRelative(-151f, 0f, -255.5f, -104.5f)
        reflectiveQuadTo(120f, 480f)
        quadToRelative(0f, -138f, 90f, -239.5f)
        reflectiveQuadTo(440f, 122f)
        quadToRelative(13f, -2f, 23f, 3.5f)
        reflectiveQuadToRelative(16f, 14.5f)
        quadToRelative(6f, 9f, 6.5f, 21f)
        reflectiveQuadToRelative(-7.5f, 23f)
        quadToRelative(-17f, 26f, -25.5f, 55f)
        reflectiveQuadToRelative(-8.5f, 61f)
        quadToRelative(0f, 90f, 63f, 153f)
        reflectiveQuadToRelative(153f, 63f)
        quadToRelative(31f, 0f, 61.5f, -9f)
        reflectiveQuadToRelative(54.5f, -25f)
        quadToRelative(11f, -7f, 22.5f, -6.5f)
        reflectiveQuadTo(819f, 481f)
        quadToRelative(10f, 5f, 15.5f, 15f)
        reflectiveQuadToRelative(3.5f, 24f)
        quadToRelative(-14f, 138f, -117.5f, 229f)
        reflectiveQuadTo(480f, 840f)
        close()
      }
    }.build()

    return _DarkModeFill!!
  }

@Suppress("ObjectPropertyName")
private var _DarkModeFill: ImageVector? = null
