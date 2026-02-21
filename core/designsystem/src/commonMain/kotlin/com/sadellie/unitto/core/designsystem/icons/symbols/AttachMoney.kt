package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.AttachMoney: ImageVector
  get() {
    if (_AttachMoney != null) {
      return _AttachMoney!!
    }
    _AttachMoney =
      ImageVector.Builder(
          name = "AttachMoney",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(481f, 840f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(441f, 800f)
            verticalLineToRelative(-46f)
            quadToRelative(-45f, -10f, -79f, -35f)
            reflectiveQuadToRelative(-55f, -70f)
            quadToRelative(-7f, -14f, -0.5f, -29.5f)
            reflectiveQuadTo(330f, 597f)
            quadToRelative(14f, -6f, 29f, 0.5f)
            reflectiveQuadToRelative(23f, 21.5f)
            quadToRelative(17f, 30f, 43f, 45.5f)
            reflectiveQuadToRelative(64f, 15.5f)
            quadToRelative(41f, 0f, 69.5f, -18.5f)
            reflectiveQuadTo(587f, 604f)
            quadToRelative(0f, -35f, -22f, -55.5f)
            reflectiveQuadTo(463f, 502f)
            quadToRelative(-86f, -27f, -118f, -64.5f)
            reflectiveQuadTo(313f, 346f)
            quadToRelative(0f, -65f, 42f, -101f)
            reflectiveQuadToRelative(86f, -41f)
            verticalLineToRelative(-44f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(481f, 120f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(521f, 160f)
            verticalLineToRelative(44f)
            quadToRelative(38f, 6f, 66f, 24.5f)
            reflectiveQuadToRelative(46f, 45.5f)
            quadToRelative(9f, 13f, 3.5f, 29f)
            reflectiveQuadTo(614f, 326f)
            quadToRelative(-14f, 6f, -29f, 0.5f)
            reflectiveQuadTo(557f, 307f)
            quadToRelative(-13f, -14f, -30.5f, -21.5f)
            reflectiveQuadTo(483f, 278f)
            quadToRelative(-44f, 0f, -67f, 19.5f)
            reflectiveQuadTo(393f, 346f)
            quadToRelative(0f, 33f, 30f, 52f)
            reflectiveQuadToRelative(104f, 40f)
            quadToRelative(69f, 20f, 104.5f, 63.5f)
            reflectiveQuadTo(667f, 602f)
            quadToRelative(0f, 71f, -42f, 108f)
            reflectiveQuadToRelative(-104f, 46f)
            verticalLineToRelative(44f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(481f, 840f)
            close()
          }
        }
        .build()

    return _AttachMoney!!
  }

@Suppress("ObjectPropertyName") private var _AttachMoney: ImageVector? = null
