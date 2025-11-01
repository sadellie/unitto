package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.NewReleases: ImageVector
  get() {
    if (_NewReleases != null) {
      return _NewReleases!!
    }
    _NewReleases = ImageVector.Builder(
      name = "NewReleases",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 960f,
      viewportHeight = 960f
    ).apply {
      path(fill = SolidColor(Color(0xFFE8EAED))) {
        moveToRelative(438f, 508f)
        lineToRelative(-58f, -57f)
        quadToRelative(-11f, -11f, -27.5f, -11f)
        reflectiveQuadTo(324f, 452f)
        quadToRelative(-11f, 11f, -11f, 28f)
        reflectiveQuadToRelative(11f, 28f)
        lineToRelative(86f, 86f)
        quadToRelative(12f, 12f, 28f, 12f)
        reflectiveQuadToRelative(28f, -12f)
        lineToRelative(170f, -170f)
        quadToRelative(12f, -12f, 11.5f, -28f)
        reflectiveQuadTo(636f, 368f)
        quadToRelative(-12f, -12f, -28.5f, -12.5f)
        reflectiveQuadTo(579f, 367f)
        lineTo(438f, 508f)
        close()
        moveTo(326f, 870f)
        lineToRelative(-58f, -98f)
        lineToRelative(-110f, -24f)
        quadToRelative(-15f, -3f, -24f, -15.5f)
        reflectiveQuadToRelative(-7f, -27.5f)
        lineToRelative(11f, -113f)
        lineToRelative(-75f, -86f)
        quadToRelative(-10f, -11f, -10f, -26f)
        reflectiveQuadToRelative(10f, -26f)
        lineToRelative(75f, -86f)
        lineToRelative(-11f, -113f)
        quadToRelative(-2f, -15f, 7f, -27.5f)
        reflectiveQuadToRelative(24f, -15.5f)
        lineToRelative(110f, -24f)
        lineToRelative(58f, -98f)
        quadToRelative(8f, -13f, 22f, -17.5f)
        reflectiveQuadToRelative(28f, 1.5f)
        lineToRelative(104f, 44f)
        lineToRelative(104f, -44f)
        quadToRelative(14f, -6f, 28f, -1.5f)
        reflectiveQuadToRelative(22f, 17.5f)
        lineToRelative(58f, 98f)
        lineToRelative(110f, 24f)
        quadToRelative(15f, 3f, 24f, 15.5f)
        reflectiveQuadToRelative(7f, 27.5f)
        lineToRelative(-11f, 113f)
        lineToRelative(75f, 86f)
        quadToRelative(10f, 11f, 10f, 26f)
        reflectiveQuadToRelative(-10f, 26f)
        lineToRelative(-75f, 86f)
        lineToRelative(11f, 113f)
        quadToRelative(2f, 15f, -7f, 27.5f)
        reflectiveQuadTo(802f, 748f)
        lineToRelative(-110f, 24f)
        lineToRelative(-58f, 98f)
        quadToRelative(-8f, 13f, -22f, 17.5f)
        reflectiveQuadTo(584f, 886f)
        lineToRelative(-104f, -44f)
        lineToRelative(-104f, 44f)
        quadToRelative(-14f, 6f, -28f, 1.5f)
        reflectiveQuadTo(326f, 870f)
        close()
        moveTo(378f, 798f)
        lineTo(480f, 754f)
        lineTo(584f, 798f)
        lineTo(640f, 702f)
        lineTo(750f, 676f)
        lineTo(740f, 564f)
        lineTo(814f, 480f)
        lineTo(740f, 394f)
        lineTo(750f, 282f)
        lineTo(640f, 258f)
        lineTo(582f, 162f)
        lineTo(480f, 206f)
        lineTo(376f, 162f)
        lineTo(320f, 258f)
        lineTo(210f, 282f)
        lineTo(220f, 394f)
        lineTo(146f, 480f)
        lineTo(220f, 564f)
        lineTo(210f, 678f)
        lineTo(320f, 702f)
        lineTo(378f, 798f)
        close()
        moveTo(480f, 480f)
        close()
      }
    }.build()

    return _NewReleases!!
  }

@Suppress("ObjectPropertyName")
private var _NewReleases: ImageVector? = null
