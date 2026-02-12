package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.AddRowBelow: ImageVector
  get() {
    if (_AddRowBelow != null) {
      return _AddRowBelow!!
    }
    _AddRowBelow =
      ImageVector.Builder(
          name = "AddRowBelow",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(760f, 400f)
            verticalLineToRelative(-240f)
            lineTo(200f, 160f)
            verticalLineToRelative(240f)
            horizontalLineToRelative(560f)
            close()
            moveTo(200f, 800f)
            quadToRelative(-33f, 0f, -56.5f, -23.5f)
            reflectiveQuadTo(120f, 720f)
            verticalLineToRelative(-560f)
            quadToRelative(0f, -33f, 23.5f, -56.5f)
            reflectiveQuadTo(200f, 80f)
            horizontalLineToRelative(560f)
            quadToRelative(33f, 0f, 56.5f, 23.5f)
            reflectiveQuadTo(840f, 160f)
            verticalLineToRelative(560f)
            quadToRelative(0f, 33f, -23.5f, 56.5f)
            reflectiveQuadTo(760f, 800f)
            horizontalLineToRelative(-40f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(680f, 760f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(720f, 720f)
            horizontalLineToRelative(40f)
            verticalLineToRelative(-240f)
            lineTo(200f, 480f)
            verticalLineToRelative(240f)
            horizontalLineToRelative(40f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(280f, 760f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(240f, 800f)
            horizontalLineToRelative(-40f)
            close()
            moveTo(451.5f, 868.5f)
            quadTo(440f, 857f, 440f, 840f)
            verticalLineToRelative(-40f)
            horizontalLineToRelative(-40f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(360f, 760f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(400f, 720f)
            horizontalLineToRelative(40f)
            verticalLineToRelative(-40f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(480f, 640f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(520f, 680f)
            verticalLineToRelative(40f)
            horizontalLineToRelative(40f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(600f, 760f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(560f, 800f)
            horizontalLineToRelative(-40f)
            verticalLineToRelative(40f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(480f, 880f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            close()
            moveTo(480f, 480f)
            close()
            moveTo(480f, 400f)
            verticalLineToRelative(80f)
            verticalLineToRelative(-80f)
            close()
            moveTo(480f, 400f)
            close()
          }
        }
        .build()

    return _AddRowBelow!!
  }

@Suppress("ObjectPropertyName") private var _AddRowBelow: ImageVector? = null
