package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.MoveSelectionDown: ImageVector
  get() {
    if (_MoveSelectionDown != null) {
      return _MoveSelectionDown!!
    }
    _MoveSelectionDown =
      ImageVector.Builder(
          name = "MoveSelectionDown",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(320f, 880f)
            quadToRelative(-33f, 0f, -56.5f, -23.5f)
            reflectiveQuadTo(240f, 800f)
            verticalLineToRelative(-320f)
            quadToRelative(0f, -33f, 23.5f, -56.5f)
            reflectiveQuadTo(320f, 400f)
            horizontalLineToRelative(320f)
            quadToRelative(33f, 0f, 56.5f, 23.5f)
            reflectiveQuadTo(720f, 480f)
            verticalLineToRelative(320f)
            quadToRelative(0f, 33f, -23.5f, 56.5f)
            reflectiveQuadTo(640f, 880f)
            lineTo(320f, 880f)
            close()
            moveTo(640f, 480f)
            lineTo(320f, 480f)
            verticalLineToRelative(320f)
            horizontalLineToRelative(320f)
            verticalLineToRelative(-320f)
            close()
            moveTo(680f, 320f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(640f, 280f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(680f, 240f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(720f, 280f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(680f, 320f)
            close()
            moveTo(680f, 160f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(640f, 120f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(680f, 80f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(720f, 120f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(680f, 160f)
            close()
            moveTo(480f, 160f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(440f, 120f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(480f, 80f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(520f, 120f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(480f, 160f)
            close()
            moveTo(280f, 160f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(240f, 120f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(280f, 80f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(320f, 120f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(280f, 160f)
            close()
            moveTo(280f, 320f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(240f, 280f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(280f, 240f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(320f, 280f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(280f, 320f)
            close()
            moveTo(480f, 640f)
            close()
          }
        }
        .build()

    return _MoveSelectionDown!!
  }

@Suppress("ObjectPropertyName") private var _MoveSelectionDown: ImageVector? = null
