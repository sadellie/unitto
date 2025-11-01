package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.FontDownload: ImageVector
  get() {
    if (_FontDownload != null) {
      return _FontDownload!!
    }
    _FontDownload = ImageVector.Builder(
      name = "FontDownload",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(384f, 598f)
        horizontalLineToRelative(192f)
        lineToRelative(35f, 97f)
        quadToRelative(4f, 11f, 14f, 18f)
        reflectiveQuadToRelative(22f, 7f)
        quadToRelative(20f, 0f, 32.5f, -16.5f)
        reflectiveQuadTo(684f, 667f)
        lineTo(532f, 265f)
        quadToRelative(-5f, -11f, -15f, -18f)
        reflectiveQuadToRelative(-22f, -7f)
        horizontalLineToRelative(-30f)
        quadToRelative(-12f, 0f, -22f, 7f)
        reflectiveQuadToRelative(-15f, 18f)
        lineTo(276f, 667f)
        quadToRelative(-8f, 19f, 4f, 36f)
        reflectiveQuadToRelative(32f, 17f)
        quadToRelative(13f, 0f, 22.5f, -7f)
        reflectiveQuadToRelative(14.5f, -19f)
        lineToRelative(35f, -96f)
        close()
        moveTo(408f, 528f)
        lineTo(478f, 330f)
        horizontalLineToRelative(4f)
        lineToRelative(70f, 198f)
        lineTo(408f, 528f)
        close()
        moveTo(160f, 880f)
        quadToRelative(-33f, 0f, -56.5f, -23.5f)
        reflectiveQuadTo(80f, 800f)
        verticalLineToRelative(-640f)
        quadToRelative(0f, -33f, 23.5f, -56.5f)
        reflectiveQuadTo(160f, 80f)
        horizontalLineToRelative(640f)
        quadToRelative(33f, 0f, 56.5f, 23.5f)
        reflectiveQuadTo(880f, 160f)
        verticalLineToRelative(640f)
        quadToRelative(0f, 33f, -23.5f, 56.5f)
        reflectiveQuadTo(800f, 880f)
        lineTo(160f, 880f)
        close()
        moveTo(160f, 800f)
        horizontalLineToRelative(640f)
        verticalLineToRelative(-640f)
        lineTo(160f, 160f)
        verticalLineToRelative(640f)
        close()
        moveTo(160f, 160f)
        verticalLineToRelative(640f)
        verticalLineToRelative(-640f)
        close()
      }
    }.build()

    return _FontDownload!!
  }

@Suppress("ObjectPropertyName")
private var _FontDownload: ImageVector? = null
