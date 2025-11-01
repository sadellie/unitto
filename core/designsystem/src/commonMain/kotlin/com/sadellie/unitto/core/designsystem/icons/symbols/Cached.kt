package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.Cached: ImageVector
  get() {
    if (_Cached != null) {
      return _Cached!!
    }
    _Cached = ImageVector.Builder(
      name = "Cached",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(482f, 800f)
        quadToRelative(-134f, 0f, -228f, -93f)
        reflectiveQuadToRelative(-94f, -227f)
        verticalLineToRelative(-7f)
        lineToRelative(-36f, 36f)
        quadToRelative(-11f, 11f, -28f, 11f)
        reflectiveQuadToRelative(-28f, -11f)
        quadToRelative(-11f, -11f, -11f, -28f)
        reflectiveQuadToRelative(11f, -28f)
        lineToRelative(104f, -104f)
        quadToRelative(12f, -12f, 28f, -12f)
        reflectiveQuadToRelative(28f, 12f)
        lineToRelative(104f, 104f)
        quadToRelative(11f, 11f, 11f, 28f)
        reflectiveQuadToRelative(-11f, 28f)
        quadToRelative(-11f, 11f, -28f, 11f)
        reflectiveQuadToRelative(-28f, -11f)
        lineToRelative(-36f, -36f)
        verticalLineToRelative(7f)
        quadToRelative(0f, 100f, 70.5f, 170f)
        reflectiveQuadTo(482f, 720f)
        quadToRelative(16f, 0f, 31.5f, -2f)
        reflectiveQuadToRelative(30.5f, -7f)
        quadToRelative(17f, -5f, 32f, 1f)
        reflectiveQuadToRelative(23f, 21f)
        quadToRelative(8f, 16f, 1.5f, 31.5f)
        reflectiveQuadTo(577f, 785f)
        quadToRelative(-23f, 8f, -47f, 11.5f)
        reflectiveQuadToRelative(-48f, 3.5f)
        close()
        moveTo(478f, 240f)
        quadToRelative(-16f, 0f, -31.5f, 2f)
        reflectiveQuadToRelative(-30.5f, 7f)
        quadToRelative(-17f, 5f, -32.5f, -1f)
        reflectiveQuadTo(360f, 227f)
        quadToRelative(-8f, -15f, -1.5f, -30.5f)
        reflectiveQuadTo(381f, 176f)
        quadToRelative(24f, -8f, 48f, -12f)
        reflectiveQuadToRelative(49f, -4f)
        quadToRelative(134f, 0f, 228f, 93f)
        reflectiveQuadToRelative(94f, 227f)
        verticalLineToRelative(7f)
        lineToRelative(36f, -36f)
        quadToRelative(11f, -11f, 28f, -11f)
        reflectiveQuadToRelative(28f, 11f)
        quadToRelative(11f, 11f, 11f, 28f)
        reflectiveQuadToRelative(-11f, 28f)
        lineTo(788f, 611f)
        quadToRelative(-12f, 12f, -28f, 12f)
        reflectiveQuadToRelative(-28f, -12f)
        lineTo(628f, 507f)
        quadToRelative(-11f, -11f, -11f, -28f)
        reflectiveQuadToRelative(11f, -28f)
        quadToRelative(11f, -11f, 28f, -11f)
        reflectiveQuadToRelative(28f, 11f)
        lineToRelative(36f, 36f)
        verticalLineToRelative(-7f)
        quadToRelative(0f, -100f, -70.5f, -170f)
        reflectiveQuadTo(478f, 240f)
        close()
      }
    }.build()

    return _Cached!!
  }

@Suppress("ObjectPropertyName")
private var _Cached: ImageVector? = null
