package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.CalculateFill: ImageVector
  get() {
    if (_CalculateFill != null) {
      return _CalculateFill!!
    }
    _CalculateFill = ImageVector.Builder(
      name = "CalculateFill",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(320f, 640f)
        verticalLineToRelative(50f)
        quadToRelative(0f, 13f, 8.5f, 21.5f)
        reflectiveQuadTo(350f, 720f)
        quadToRelative(13f, 0f, 21.5f, -8.5f)
        reflectiveQuadTo(380f, 690f)
        verticalLineToRelative(-50f)
        horizontalLineToRelative(50f)
        quadToRelative(13f, 0f, 21.5f, -8.5f)
        reflectiveQuadTo(460f, 610f)
        quadToRelative(0f, -13f, -8.5f, -21.5f)
        reflectiveQuadTo(430f, 580f)
        horizontalLineToRelative(-50f)
        verticalLineToRelative(-50f)
        quadToRelative(0f, -13f, -8.5f, -21.5f)
        reflectiveQuadTo(350f, 500f)
        quadToRelative(-13f, 0f, -21.5f, 8.5f)
        reflectiveQuadTo(320f, 530f)
        verticalLineToRelative(50f)
        horizontalLineToRelative(-50f)
        quadToRelative(-13f, 0f, -21.5f, 8.5f)
        reflectiveQuadTo(240f, 610f)
        quadToRelative(0f, 13f, 8.5f, 21.5f)
        reflectiveQuadTo(270f, 640f)
        horizontalLineToRelative(50f)
        close()
        moveTo(550f, 690f)
        horizontalLineToRelative(140f)
        quadToRelative(13f, 0f, 21.5f, -8.5f)
        reflectiveQuadTo(720f, 660f)
        quadToRelative(0f, -13f, -8.5f, -21.5f)
        reflectiveQuadTo(690f, 630f)
        lineTo(550f, 630f)
        quadToRelative(-13f, 0f, -21.5f, 8.5f)
        reflectiveQuadTo(520f, 660f)
        quadToRelative(0f, 13f, 8.5f, 21.5f)
        reflectiveQuadTo(550f, 690f)
        close()
        moveTo(550f, 590f)
        horizontalLineToRelative(140f)
        quadToRelative(13f, 0f, 21.5f, -8.5f)
        reflectiveQuadTo(720f, 560f)
        quadToRelative(0f, -13f, -8.5f, -21.5f)
        reflectiveQuadTo(690f, 530f)
        lineTo(550f, 530f)
        quadToRelative(-13f, 0f, -21.5f, 8.5f)
        reflectiveQuadTo(520f, 560f)
        quadToRelative(0f, 13f, 8.5f, 21.5f)
        reflectiveQuadTo(550f, 590f)
        close()
        moveTo(280f, 368f)
        horizontalLineToRelative(140f)
        quadToRelative(13f, 0f, 21.5f, -8.5f)
        reflectiveQuadTo(450f, 338f)
        quadToRelative(0f, -13f, -8.5f, -21.5f)
        reflectiveQuadTo(420f, 308f)
        lineTo(280f, 308f)
        quadToRelative(-13f, 0f, -21.5f, 8.5f)
        reflectiveQuadTo(250f, 338f)
        quadToRelative(0f, 13f, 8.5f, 21.5f)
        reflectiveQuadTo(280f, 368f)
        close()
        moveTo(200f, 840f)
        quadToRelative(-33f, 0f, -56.5f, -23.5f)
        reflectiveQuadTo(120f, 760f)
        verticalLineToRelative(-560f)
        quadToRelative(0f, -33f, 23.5f, -56.5f)
        reflectiveQuadTo(200f, 120f)
        horizontalLineToRelative(560f)
        quadToRelative(33f, 0f, 56.5f, 23.5f)
        reflectiveQuadTo(840f, 200f)
        verticalLineToRelative(560f)
        quadToRelative(0f, 33f, -23.5f, 56.5f)
        reflectiveQuadTo(760f, 840f)
        lineTo(200f, 840f)
        close()
        moveTo(620f, 382f)
        lineTo(655f, 417f)
        quadToRelative(9f, 9f, 21f, 9f)
        reflectiveQuadToRelative(21f, -9f)
        quadToRelative(8f, -8f, 8.5f, -20.5f)
        reflectiveQuadTo(698f, 375f)
        lineToRelative(-36f, -37f)
        lineToRelative(35f, -35f)
        quadToRelative(9f, -9f, 9f, -21f)
        reflectiveQuadToRelative(-9f, -21f)
        quadToRelative(-9f, -9f, -21f, -9f)
        reflectiveQuadToRelative(-21f, 9f)
        lineToRelative(-35f, 35f)
        lineToRelative(-35f, -35f)
        quadToRelative(-9f, -9f, -21f, -9f)
        reflectiveQuadToRelative(-21f, 9f)
        quadToRelative(-9f, 9f, -9f, 21f)
        reflectiveQuadToRelative(9f, 21f)
        lineToRelative(35f, 35f)
        lineToRelative(-36f, 37f)
        quadToRelative(-8f, 9f, -8f, 21f)
        reflectiveQuadToRelative(9f, 21f)
        quadToRelative(9f, 9f, 21f, 9f)
        reflectiveQuadToRelative(21f, -9f)
        lineToRelative(35f, -35f)
        close()
      }
    }.build()

    return _CalculateFill!!
  }

@Suppress("ObjectPropertyName")
private var _CalculateFill: ImageVector? = null
