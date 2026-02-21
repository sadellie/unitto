package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.ElectricBoltFill: ImageVector
  get() {
    if (_ElectricBoltFill != null) {
      return _ElectricBoltFill!!
    }
    _ElectricBoltFill =
      ImageVector.Builder(
          name = "ElectricBoltFill",
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
          }
        }
        .build()

    return _ElectricBoltFill!!
  }

@Suppress("ObjectPropertyName") private var _ElectricBoltFill: ImageVector? = null
