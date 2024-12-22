package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.SettingsFill: ImageVector
  get() {
    if (_SettingsFill != null) {
      return _SettingsFill!!
    }
    _SettingsFill = ImageVector.Builder(
      name = "SettingsFill",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(433f, 880f)
        quadToRelative(-27f, 0f, -46.5f, -18f)
        reflectiveQuadTo(363f, 818f)
        lineToRelative(-9f, -66f)
        quadToRelative(-13f, -5f, -24.5f, -12f)
        reflectiveQuadTo(307f, 725f)
        lineToRelative(-62f, 26f)
        quadToRelative(-25f, 11f, -50f, 2f)
        reflectiveQuadToRelative(-39f, -32f)
        lineToRelative(-47f, -82f)
        quadToRelative(-14f, -23f, -8f, -49f)
        reflectiveQuadToRelative(27f, -43f)
        lineToRelative(53f, -40f)
        quadToRelative(-1f, -7f, -1f, -13.5f)
        verticalLineToRelative(-27f)
        quadToRelative(0f, -6.5f, 1f, -13.5f)
        lineToRelative(-53f, -40f)
        quadToRelative(-21f, -17f, -27f, -43f)
        reflectiveQuadToRelative(8f, -49f)
        lineToRelative(47f, -82f)
        quadToRelative(14f, -23f, 39f, -32f)
        reflectiveQuadToRelative(50f, 2f)
        lineToRelative(62f, 26f)
        quadToRelative(11f, -8f, 23f, -15f)
        reflectiveQuadToRelative(24f, -12f)
        lineToRelative(9f, -66f)
        quadToRelative(4f, -26f, 23.5f, -44f)
        reflectiveQuadToRelative(46.5f, -18f)
        horizontalLineToRelative(94f)
        quadToRelative(27f, 0f, 46.5f, 18f)
        reflectiveQuadToRelative(23.5f, 44f)
        lineToRelative(9f, 66f)
        quadToRelative(13f, 5f, 24.5f, 12f)
        reflectiveQuadToRelative(22.5f, 15f)
        lineToRelative(62f, -26f)
        quadToRelative(25f, -11f, 50f, -2f)
        reflectiveQuadToRelative(39f, 32f)
        lineToRelative(47f, 82f)
        quadToRelative(14f, 23f, 8f, 49f)
        reflectiveQuadToRelative(-27f, 43f)
        lineToRelative(-53f, 40f)
        quadToRelative(1f, 7f, 1f, 13.5f)
        verticalLineToRelative(27f)
        quadToRelative(0f, 6.5f, -2f, 13.5f)
        lineToRelative(53f, 40f)
        quadToRelative(21f, 17f, 27f, 43f)
        reflectiveQuadToRelative(-8f, 49f)
        lineToRelative(-48f, 82f)
        quadToRelative(-14f, 23f, -39f, 32f)
        reflectiveQuadToRelative(-50f, -2f)
        lineToRelative(-60f, -26f)
        quadToRelative(-11f, 8f, -23f, 15f)
        reflectiveQuadToRelative(-24f, 12f)
        lineToRelative(-9f, 66f)
        quadToRelative(-4f, 26f, -23.5f, 44f)
        reflectiveQuadTo(527f, 880f)
        horizontalLineToRelative(-94f)
        close()
        moveTo(482f, 620f)
        quadToRelative(58f, 0f, 99f, -41f)
        reflectiveQuadToRelative(41f, -99f)
        quadToRelative(0f, -58f, -41f, -99f)
        reflectiveQuadToRelative(-99f, -41f)
        quadToRelative(-59f, 0f, -99.5f, 41f)
        reflectiveQuadTo(342f, 480f)
        quadToRelative(0f, 58f, 40.5f, 99f)
        reflectiveQuadToRelative(99.5f, 41f)
        close()
      }
    }.build()

    return _SettingsFill!!
  }

@Suppress("ObjectPropertyName")
private var _SettingsFill: ImageVector? = null
