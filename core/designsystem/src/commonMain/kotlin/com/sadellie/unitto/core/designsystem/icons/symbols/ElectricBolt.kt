package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.ElectricBolt: ImageVector
  get() {
    if (_ElectricBolt != null) {
      return _ElectricBolt!!
    }
    _ElectricBolt =
      ImageVector.Builder(
          name = "ElectricBolt",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveToRelative(440f, 580f)
            lineToRelative(-237f, -30f)
            quadToRelative(-25f, -3f, -32.5f, -27f)
            reflectiveQuadToRelative(10.5f, -41f)
            lineToRelative(409f, -392f)
            quadToRelative(5f, -5f, 12f, -7.5f)
            reflectiveQuadToRelative(19f, -2.5f)
            quadToRelative(20f, 0f, 30.5f, 17f)
            reflectiveQuadToRelative(0.5f, 35f)
            lineTo(520f, 380f)
            lineToRelative(237f, 30f)
            quadToRelative(25f, 3f, 32.5f, 27f)
            reflectiveQuadTo(779f, 478f)
            lineTo(370f, 870f)
            quadToRelative(-5f, 5f, -12f, 7.5f)
            reflectiveQuadTo(339f, 880f)
            quadToRelative(-20f, 0f, -30.5f, -17f)
            reflectiveQuadToRelative(-0.5f, -35f)
            lineToRelative(132f, -248f)
            close()
            moveTo(502f, 633f)
            lineTo(663f, 479f)
            lineTo(394f, 445f)
            lineTo(457f, 328f)
            lineTo(297f, 482f)
            lineTo(565f, 515f)
            lineTo(502f, 633f)
            close()
            moveTo(480f, 480f)
            close()
          }
        }
        .build()

    return _ElectricBolt!!
  }

@Suppress("ObjectPropertyName") private var _ElectricBolt: ImageVector? = null
