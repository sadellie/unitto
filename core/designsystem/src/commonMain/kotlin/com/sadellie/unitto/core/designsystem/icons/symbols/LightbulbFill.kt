package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.LightbulbFill: ImageVector
  get() {
    if (_LightbulbFill != null) {
      return _LightbulbFill!!
    }
    _LightbulbFill =
      ImageVector.Builder(
          name = "LightbulbFill",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(423.5f, 856.5f)
            quadTo(400f, 833f, 400f, 800f)
            horizontalLineToRelative(160f)
            quadToRelative(0f, 33f, -23.5f, 56.5f)
            reflectiveQuadTo(480f, 880f)
            quadToRelative(-33f, 0f, -56.5f, -23.5f)
            close()
            moveTo(360f, 760f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(320f, 720f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(360f, 680f)
            horizontalLineToRelative(240f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(640f, 720f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(600f, 760f)
            lineTo(360f, 760f)
            close()
            moveTo(330f, 640f)
            quadToRelative(-69f, -41f, -109.5f, -110f)
            reflectiveQuadTo(180f, 380f)
            quadToRelative(0f, -125f, 87.5f, -212.5f)
            reflectiveQuadTo(480f, 80f)
            quadToRelative(125f, 0f, 212.5f, 87.5f)
            reflectiveQuadTo(780f, 380f)
            quadToRelative(0f, 81f, -40.5f, 150f)
            reflectiveQuadTo(630f, 640f)
            lineTo(330f, 640f)
            close()
          }
        }
        .build()

    return _LightbulbFill!!
  }

@Suppress("ObjectPropertyName") private var _LightbulbFill: ImageVector? = null
