package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.AppShortcut: ImageVector
  get() {
    if (_AppShortcut != null) {
      return _AppShortcut!!
    }
    _AppShortcut = ImageVector.Builder(
      name = "AppShortcut",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(280f, 800f)
        verticalLineToRelative(40f)
        horizontalLineToRelative(400f)
        verticalLineToRelative(-40f)
        lineTo(280f, 800f)
        close()
        moveTo(280f, 160f)
        horizontalLineToRelative(400f)
        verticalLineToRelative(-40f)
        lineTo(280f, 120f)
        verticalLineToRelative(40f)
        close()
        moveTo(280f, 160f)
        verticalLineToRelative(-40f)
        verticalLineToRelative(40f)
        close()
        moveTo(280f, 800f)
        verticalLineToRelative(40f)
        verticalLineToRelative(-40f)
        close()
        moveTo(686f, 520f)
        lineTo(480f, 520f)
        verticalLineToRelative(80f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(440f, 640f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(400f, 600f)
        verticalLineToRelative(-80f)
        quadToRelative(0f, -33f, 23.5f, -56.5f)
        reflectiveQuadTo(480f, 440f)
        horizontalLineToRelative(206f)
        lineToRelative(-35f, -36f)
        quadToRelative(-11f, -11f, -11f, -27.5f)
        reflectiveQuadToRelative(12f, -28.5f)
        quadToRelative(11f, -11f, 28f, -11f)
        reflectiveQuadToRelative(28f, 11f)
        lineToRelative(104f, 104f)
        quadToRelative(12f, 12f, 12f, 28f)
        reflectiveQuadToRelative(-12f, 28f)
        lineTo(708f, 612f)
        quadToRelative(-11f, 11f, -27.5f, 11.5f)
        reflectiveQuadTo(652f, 612f)
        quadToRelative(-11f, -11f, -11.5f, -27.5f)
        reflectiveQuadTo(651f, 556f)
        lineToRelative(35f, -36f)
        close()
        moveTo(280f, 920f)
        quadToRelative(-33f, 0f, -56.5f, -23.5f)
        reflectiveQuadTo(200f, 840f)
        verticalLineToRelative(-720f)
        quadToRelative(0f, -33f, 23.5f, -56.5f)
        reflectiveQuadTo(280f, 40f)
        horizontalLineToRelative(400f)
        quadToRelative(33f, 0f, 56.5f, 23.5f)
        reflectiveQuadTo(760f, 120f)
        verticalLineToRelative(120f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(720f, 280f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(680f, 240f)
        lineTo(280f, 240f)
        verticalLineToRelative(480f)
        horizontalLineToRelative(400f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(720f, 680f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(760f, 720f)
        verticalLineToRelative(120f)
        quadToRelative(0f, 33f, -23.5f, 56.5f)
        reflectiveQuadTo(680f, 920f)
        lineTo(280f, 920f)
        close()
      }
    }.build()

    return _AppShortcut!!
  }

@Suppress("ObjectPropertyName")
private var _AppShortcut: ImageVector? = null
