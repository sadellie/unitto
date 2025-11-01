package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.Home: ImageVector
  get() {
    if (_Home != null) {
      return _Home!!
    }
    _Home = ImageVector.Builder(
      name = "Home",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(240f, 760f)
        horizontalLineToRelative(120f)
        verticalLineToRelative(-200f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(400f, 520f)
        horizontalLineToRelative(160f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(600f, 560f)
        verticalLineToRelative(200f)
        horizontalLineToRelative(120f)
        verticalLineToRelative(-360f)
        lineTo(480f, 220f)
        lineTo(240f, 400f)
        verticalLineToRelative(360f)
        close()
        moveTo(160f, 760f)
        verticalLineToRelative(-360f)
        quadToRelative(0f, -19f, 8.5f, -36f)
        reflectiveQuadToRelative(23.5f, -28f)
        lineToRelative(240f, -180f)
        quadToRelative(21f, -16f, 48f, -16f)
        reflectiveQuadToRelative(48f, 16f)
        lineToRelative(240f, 180f)
        quadToRelative(15f, 11f, 23.5f, 28f)
        reflectiveQuadToRelative(8.5f, 36f)
        verticalLineToRelative(360f)
        quadToRelative(0f, 33f, -23.5f, 56.5f)
        reflectiveQuadTo(720f, 840f)
        lineTo(560f, 840f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(520f, 800f)
        verticalLineToRelative(-200f)
        horizontalLineToRelative(-80f)
        verticalLineToRelative(200f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(400f, 840f)
        lineTo(240f, 840f)
        quadToRelative(-33f, 0f, -56.5f, -23.5f)
        reflectiveQuadTo(160f, 760f)
        close()
        moveTo(480f, 490f)
        close()
      }
    }.build()

    return _Home!!
  }

@Suppress("ObjectPropertyName")
private var _Home: ImageVector? = null
