package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.Energy: ImageVector
  get() {
    if (_Energy != null) {
      return _Energy!!
    }
    _Energy =
      ImageVector.Builder(
          name = "Energy",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(360f, 880f)
            verticalLineToRelative(-40f)
            quadToRelative(0f, -32f, 24f, -53.5f)
            reflectiveQuadToRelative(56f, -26.5f)
            verticalLineToRelative(-286f)
            quadToRelative(-22f, -8f, -38.5f, -23f)
            reflectiveQuadTo(374f, 416f)
            lineToRelative(-76f, 20f)
            quadToRelative(-36f, 8f, -68.5f, -6f)
            reflectiveQuadTo(188f, 382f)
            lineToRelative(174f, -44f)
            quadToRelative(8f, -41f, 38f, -68f)
            reflectiveQuadToRelative(72f, -30f)
            lineToRelative(24f, -90f)
            quadToRelative(10f, -35f, 38.5f, -55.5f)
            reflectiveQuadTo(598f, 82f)
            lineToRelative(-50f, 180f)
            quadToRelative(25f, 16f, 38.5f, 42f)
            reflectiveQuadToRelative(13.5f, 56f)
            quadToRelative(0f, 13f, -3f, 25.5f)
            reflectiveQuadToRelative(-7f, 24.5f)
            lineToRelative(54f, 56f)
            quadToRelative(25f, 26f, 29.5f, 61f)
            reflectiveQuadTo(654f, 586f)
            lineTo(534f, 466f)
            quadToRelative(-3f, 3f, -6.5f, 4.5f)
            reflectiveQuadTo(520f, 474f)
            verticalLineToRelative(286f)
            quadToRelative(32f, 5f, 56f, 26.5f)
            reflectiveQuadToRelative(24f, 53.5f)
            verticalLineToRelative(40f)
            lineTo(360f, 880f)
            close()
            moveTo(522.5f, 402.5f)
            quadTo(540f, 385f, 540f, 360f)
            reflectiveQuadToRelative(-17.5f, -42.5f)
            quadTo(505f, 300f, 480f, 300f)
            reflectiveQuadToRelative(-42.5f, 17.5f)
            quadTo(420f, 335f, 420f, 360f)
            reflectiveQuadToRelative(17.5f, 42.5f)
            quadTo(455f, 420f, 480f, 420f)
            reflectiveQuadToRelative(42.5f, -17.5f)
            close()
          }
        }
        .build()

    return _Energy!!
  }

@Suppress("ObjectPropertyName") private var _Energy: ImageVector? = null
