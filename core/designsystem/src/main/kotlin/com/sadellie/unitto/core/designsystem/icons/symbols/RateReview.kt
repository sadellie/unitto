package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.RateReview: ImageVector
  get() {
    if (_RateReview != null) {
      return _RateReview!!
    }
    _RateReview = ImageVector.Builder(
      name = "RateReview",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveTo(280f, 560f)
        horizontalLineToRelative(65f)
        quadToRelative(8f, 0f, 15.5f, -3f)
        reflectiveQuadToRelative(13.5f, -9f)
        lineToRelative(188f, -188f)
        quadToRelative(9f, -9f, 13.5f, -20.5f)
        reflectiveQuadTo(580f, 317f)
        quadToRelative(0f, -11f, -5f, -21.5f)
        reflectiveQuadTo(562f, 276f)
        lineToRelative(-36f, -38f)
        quadToRelative(-9f, -9f, -20f, -13.5f)
        reflectiveQuadToRelative(-23f, -4.5f)
        quadToRelative(-11f, 0f, -22.5f, 4.5f)
        reflectiveQuadTo(440f, 238f)
        lineTo(252f, 426f)
        quadToRelative(-6f, 6f, -9f, 13.5f)
        reflectiveQuadToRelative(-3f, 15.5f)
        verticalLineToRelative(65f)
        quadToRelative(0f, 17f, 11.5f, 28.5f)
        reflectiveQuadTo(280f, 560f)
        close()
        moveTo(520f, 317f)
        lineTo(483f, 280f)
        lineTo(520f, 317f)
        close()
        moveTo(300f, 500f)
        verticalLineToRelative(-38f)
        lineToRelative(101f, -101f)
        lineToRelative(20f, 18f)
        lineToRelative(18f, 20f)
        lineToRelative(-101f, 101f)
        horizontalLineToRelative(-38f)
        close()
        moveTo(421f, 379f)
        lineTo(439f, 399f)
        lineTo(401f, 361f)
        lineTo(421f, 379f)
        close()
        moveTo(447f, 560f)
        horizontalLineToRelative(233f)
        quadToRelative(17f, 0f, 28.5f, -11.5f)
        reflectiveQuadTo(720f, 520f)
        quadToRelative(0f, -17f, -11.5f, -28.5f)
        reflectiveQuadTo(680f, 480f)
        lineTo(527f, 480f)
        lineToRelative(-80f, 80f)
        close()
        moveTo(240f, 720f)
        lineToRelative(-92f, 92f)
        quadToRelative(-19f, 19f, -43.5f, 8.5f)
        reflectiveQuadTo(80f, 783f)
        verticalLineToRelative(-623f)
        quadToRelative(0f, -33f, 23.5f, -56.5f)
        reflectiveQuadTo(160f, 80f)
        horizontalLineToRelative(640f)
        quadToRelative(33f, 0f, 56.5f, 23.5f)
        reflectiveQuadTo(880f, 160f)
        verticalLineToRelative(480f)
        quadToRelative(0f, 33f, -23.5f, 56.5f)
        reflectiveQuadTo(800f, 720f)
        lineTo(240f, 720f)
        close()
        moveTo(206f, 640f)
        horizontalLineToRelative(594f)
        verticalLineToRelative(-480f)
        lineTo(160f, 160f)
        verticalLineToRelative(525f)
        lineToRelative(46f, -45f)
        close()
        moveTo(160f, 640f)
        verticalLineToRelative(-480f)
        verticalLineToRelative(480f)
        close()
      }
    }.build()

    return _RateReview!!
  }

@Suppress("ObjectPropertyName")
private var _RateReview: ImageVector? = null
