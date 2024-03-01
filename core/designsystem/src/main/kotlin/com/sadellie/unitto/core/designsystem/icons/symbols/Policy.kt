package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.Policy: ImageVector
  get() {
    if (_Policy != null) {
      return _Policy!!
    }
    _Policy = ImageVector.Builder(
      name = "Policy",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
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
        quadToRelative(0f, 75f, -23.5f, 146.5f)
        reflectiveQuadTo(709f, 720f)
        quadToRelative(-8f, 11f, -21.5f, 11.5f)
        reflectiveQuadTo(664f, 722f)
        lineTo(560f, 618f)
        quadToRelative(-18f, 11f, -38.5f, 16.5f)
        reflectiveQuadTo(480f, 640f)
        quadToRelative(-66f, 0f, -113f, -47f)
        reflectiveQuadToRelative(-47f, -113f)
        quadToRelative(0f, -66f, 47f, -113f)
        reflectiveQuadToRelative(113f, -47f)
        quadToRelative(66f, 0f, 113f, 47f)
        reflectiveQuadToRelative(47f, 113f)
        quadToRelative(0f, 22f, -5.5f, 42.5f)
        reflectiveQuadTo(618f, 562f)
        lineToRelative(60f, 60f)
        quadToRelative(20f, -41f, 31f, -86f)
        reflectiveQuadToRelative(11f, -92f)
        verticalLineToRelative(-189f)
        lineToRelative(-240f, -90f)
        lineToRelative(-240f, 90f)
        verticalLineToRelative(189f)
        quadToRelative(0f, 121f, 68f, 220f)
        reflectiveQuadToRelative(172f, 132f)
        quadToRelative(16f, -5f, 31.5f, -12f)
        reflectiveQuadToRelative(30.5f, -16f)
        quadToRelative(14f, -8f, 30.5f, -6f)
        reflectiveQuadToRelative(26.5f, 16f)
        quadToRelative(10f, 14f, 6.5f, 30f)
        reflectiveQuadTo(588f, 833f)
        quadToRelative(-20f, 12f, -40f, 21.5f)
        reflectiveQuadTo(505f, 872f)
        quadToRelative(-6f, 2f, -12f, 3f)
        reflectiveQuadToRelative(-13f, 1f)
        close()
        moveTo(480f, 560f)
        quadToRelative(33f, 0f, 56.5f, -23.5f)
        reflectiveQuadTo(560f, 480f)
        quadToRelative(0f, -33f, -23.5f, -56.5f)
        reflectiveQuadTo(480f, 400f)
        quadToRelative(-33f, 0f, -56.5f, 23.5f)
        reflectiveQuadTo(400f, 480f)
        quadToRelative(0f, 33f, 23.5f, 56.5f)
        reflectiveQuadTo(480f, 560f)
        close()
        moveTo(488f, 483f)
        close()
      }
    }.build()

    return _Policy!!
  }

@Suppress("ObjectPropertyName")
private var _Policy: ImageVector? = null
