package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.EditOff: ImageVector
  get() {
    if (_EditOff != null) {
      return _EditOff!!
    }
    _EditOff = ImageVector.Builder(
      name = "EditOff",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(817f, 313f)
        lineTo(650f, 479f)
        quadToRelative(-11f, 11f, -27.5f, 11.5f)
        reflectiveQuadTo(594f, 479f)
        quadToRelative(-11f, -11f, -11f, -28f)
        reflectiveQuadToRelative(11f, -28f)
        lineToRelative(54f, -54f)
        lineToRelative(-57f, -57f)
        lineToRelative(-54f, 54f)
        quadToRelative(-11f, 11f, -28f, 11f)
        reflectiveQuadToRelative(-28f, -11f)
        quadToRelative(-11f, -11f, -11f, -28f)
        reflectiveQuadToRelative(11f, -28f)
        lineToRelative(167f, -167f)
        quadToRelative(12f, -12f, 26.5f, -17.5f)
        reflectiveQuadTo(705f, 120f)
        quadToRelative(16f, 0f, 31f, 6f)
        reflectiveQuadToRelative(26f, 18f)
        lineToRelative(55f, 56f)
        quadToRelative(12f, 11f, 17.5f, 26f)
        reflectiveQuadToRelative(5.5f, 30f)
        quadToRelative(0f, 16f, -5.5f, 30.5f)
        reflectiveQuadTo(817f, 313f)
        close()
        moveTo(200f, 760f)
        horizontalLineToRelative(57f)
        lineToRelative(195f, -195f)
        lineToRelative(-28f, -29f)
        lineToRelative(-29f, -28f)
        lineToRelative(-195f, 195f)
        verticalLineToRelative(57f)
        close()
        moveTo(764f, 876f)
        lineTo(509f, 622f)
        lineTo(313f, 817f)
        quadToRelative(-11f, 11f, -25.5f, 17f)
        reflectiveQuadToRelative(-30.5f, 6f)
        horizontalLineToRelative(-97f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(120f, 800f)
        verticalLineToRelative(-96f)
        quadToRelative(0f, -16f, 6f, -30.5f)
        reflectiveQuadToRelative(17f, -25.5f)
        lineToRelative(196f, -196f)
        lineTo(84f, 196f)
        quadToRelative(-12f, -12f, -11.5f, -28f)
        reflectiveQuadTo(85f, 140f)
        quadToRelative(12f, -12f, 28.5f, -12f)
        reflectiveQuadToRelative(28.5f, 12f)
        lineToRelative(679f, 679f)
        quadToRelative(12f, 12f, 11.5f, 28.5f)
        reflectiveQuadTo(820f, 876f)
        quadToRelative(-12f, 12f, -28f, 12f)
        reflectiveQuadToRelative(-28f, -12f)
        close()
        moveTo(760f, 256f)
        lineTo(704f, 200f)
        lineTo(760f, 256f)
        close()
        moveTo(591f, 312f)
        lineTo(648f, 369f)
        lineTo(591f, 312f)
        close()
        moveTo(424f, 536f)
        lineToRelative(-29f, -28f)
        lineToRelative(57f, 57f)
        lineToRelative(-28f, -29f)
        close()
      }
    }.build()

    return _EditOff!!
  }

@Suppress("ObjectPropertyName")
private var _EditOff: ImageVector? = null
