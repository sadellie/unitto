package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.PrivacyTip: ImageVector
  get() {
    if (_PrivacyTip != null) {
      return _PrivacyTip!!
    }
    _PrivacyTip = ImageVector.Builder(
      name = "PrivacyTip",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(480f, 680f)
        quadToRelative(17f, 0f, 28.5f, -11.5f)
        reflectiveQuadTo(520f, 640f)
        verticalLineToRelative(-160f)
        quadToRelative(0f, -17f, -11.5f, -28.5f)
        reflectiveQuadTo(480f, 440f)
        quadToRelative(-17f, 0f, -28.5f, 11.5f)
        reflectiveQuadTo(440f, 480f)
        verticalLineToRelative(160f)
        quadToRelative(0f, 17f, 11.5f, 28.5f)
        reflectiveQuadTo(480f, 680f)
        close()
        moveTo(480f, 360f)
        quadToRelative(17f, 0f, 28.5f, -11.5f)
        reflectiveQuadTo(520f, 320f)
        quadToRelative(0f, -17f, -11.5f, -28.5f)
        reflectiveQuadTo(480f, 280f)
        quadToRelative(-17f, 0f, -28.5f, 11.5f)
        reflectiveQuadTo(440f, 320f)
        quadToRelative(0f, 17f, 11.5f, 28.5f)
        reflectiveQuadTo(480f, 360f)
        close()
        moveTo(480f, 876f)
        quadToRelative(-7f, 0f, -13f, -1f)
        reflectiveQuadToRelative(-12f, -3f)
        quadToRelative(-135f, -45f, -215f, -166.5f)
        reflectiveQuadTo(160f, 444f)
        verticalLineToRelative(-189f)
        quadToRelative(0f, -25f, 14.5f, -45f)
        reflectiveQuadToRelative(37.5f, -29f)
        lineToRelative(240f, -90f)
        quadToRelative(14f, -5f, 28f, -5f)
        reflectiveQuadToRelative(28f, 5f)
        lineToRelative(240f, 90f)
        quadToRelative(23f, 9f, 37.5f, 29f)
        reflectiveQuadToRelative(14.5f, 45f)
        verticalLineToRelative(189f)
        quadToRelative(0f, 140f, -80f, 261.5f)
        reflectiveQuadTo(505f, 872f)
        quadToRelative(-6f, 2f, -12f, 3f)
        reflectiveQuadToRelative(-13f, 1f)
        close()
        moveTo(480f, 796f)
        quadToRelative(104f, -33f, 172f, -132f)
        reflectiveQuadToRelative(68f, -220f)
        verticalLineToRelative(-189f)
        lineToRelative(-240f, -90f)
        lineToRelative(-240f, 90f)
        verticalLineToRelative(189f)
        quadToRelative(0f, 121f, 68f, 220f)
        reflectiveQuadToRelative(172f, 132f)
        close()
        moveTo(480f, 480f)
        close()
      }
    }.build()

    return _PrivacyTip!!
  }

@Suppress("ObjectPropertyName")
private var _PrivacyTip: ImageVector? = null
