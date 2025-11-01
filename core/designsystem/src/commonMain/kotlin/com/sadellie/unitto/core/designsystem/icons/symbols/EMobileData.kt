package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.EMobileData: ImageVector
  get() {
    if (_EMobileData != null) {
      return _EMobileData!!
    }
    _EMobileData = ImageVector.Builder(
      name = "EMobileData",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(360f, 680f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(320f, 640f)
        verticalLineToRelative(-320f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(360f, 280f)
        horizontalLineToRelative(240f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(640f, 320f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(600f, 360f)
        lineTo(400f, 360f)
        verticalLineToRelative(80f)
        horizontalLineToRelative(200f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(640f, 480f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(600f, 520f)
        lineTo(400f, 520f)
        verticalLineToRelative(80f)
        horizontalLineToRelative(200f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(640f, 640f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(600f, 680f)
        lineTo(360f, 680f)
        close()
      }
    }.build()

    return _EMobileData!!
  }

@Suppress("ObjectPropertyName")
private var _EMobileData: ImageVector? = null
