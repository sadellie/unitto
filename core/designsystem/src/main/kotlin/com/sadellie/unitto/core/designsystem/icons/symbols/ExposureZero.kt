package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.ExposureZero: ImageVector
  get() {
    if (_ExposureZero != null) {
      return _ExposureZero!!
    }
    _ExposureZero = ImageVector.Builder(
      name = "ExposureZero",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(480f, 760f)
        quadToRelative(-100f, 0f, -160f, -79.5f)
        reflectiveQuadTo(260f, 480f)
        quadToRelative(0f, -121f, 60f, -200.5f)
        reflectiveQuadTo(480f, 200f)
        quadToRelative(100f, 0f, 160f, 79.5f)
        reflectiveQuadTo(700f, 480f)
        quadToRelative(0f, 121f, -60f, 200.5f)
        reflectiveQuadTo(480f, 760f)
        close()
        moveTo(480f, 678f)
        quadToRelative(66f, 0f, 99f, -60f)
        reflectiveQuadToRelative(33f, -138f)
        quadToRelative(0f, -78f, -33f, -138f)
        reflectiveQuadToRelative(-99f, -60f)
        quadToRelative(-66f, 0f, -99f, 60f)
        reflectiveQuadToRelative(-33f, 138f)
        quadToRelative(0f, 78f, 33f, 138f)
        reflectiveQuadToRelative(99f, 60f)
        close()
      }
    }.build()

    return _ExposureZero!!
  }

@Suppress("ObjectPropertyName")
private var _ExposureZero: ImageVector? = null
