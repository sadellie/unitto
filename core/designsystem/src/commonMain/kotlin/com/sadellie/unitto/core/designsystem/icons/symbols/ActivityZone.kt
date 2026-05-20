package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.ActivityZone: ImageVector
  get() {
    if (_ActivityZone != null) {
      return _ActivityZone!!
    }
    _ActivityZone =
      ImageVector.Builder(
          name = "ActivityZone",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 960f,
          viewportHeight = 960f,
        )
        .apply {
          path(fill = SolidColor(Color(0xFFE3E3E3))) {
            moveTo(200f, 880f)
            quadToRelative(-50f, 0f, -85f, -35f)
            reflectiveQuadToRelative(-35f, -85f)
            quadToRelative(0f, -39f, 22.5f, -69.5f)
            reflectiveQuadTo(160f, 647f)
            verticalLineToRelative(-334f)
            quadToRelative(-35f, -13f, -57.5f, -43.5f)
            reflectiveQuadTo(80f, 200f)
            quadToRelative(0f, -50f, 35f, -85f)
            reflectiveQuadToRelative(85f, -35f)
            quadToRelative(39f, 0f, 69.5f, 22.5f)
            reflectiveQuadTo(313f, 160f)
            horizontalLineToRelative(334f)
            quadToRelative(12f, -35f, 42.5f, -57.5f)
            reflectiveQuadTo(760f, 80f)
            quadToRelative(50f, 0f, 85f, 35f)
            reflectiveQuadToRelative(35f, 85f)
            quadToRelative(0f, 40f, -22.5f, 70.5f)
            reflectiveQuadTo(800f, 313f)
            verticalLineToRelative(334f)
            quadToRelative(35f, 13f, 57.5f, 43.5f)
            reflectiveQuadTo(880f, 760f)
            quadToRelative(0f, 50f, -35f, 85f)
            reflectiveQuadToRelative(-85f, 35f)
            quadToRelative(-39f, 0f, -69.5f, -22.5f)
            reflectiveQuadTo(647f, 800f)
            lineTo(313f, 800f)
            quadToRelative(-13f, 35f, -43.5f, 57.5f)
            reflectiveQuadTo(200f, 880f)
            close()
            moveTo(200f, 240f)
            quadToRelative(17f, 0f, 28.5f, -11.5f)
            reflectiveQuadTo(240f, 200f)
            quadToRelative(0f, -17f, -11.5f, -28.5f)
            reflectiveQuadTo(200f, 160f)
            quadToRelative(-17f, 0f, -28.5f, 11.5f)
            reflectiveQuadTo(160f, 200f)
            quadToRelative(0f, 17f, 11.5f, 28.5f)
            reflectiveQuadTo(200f, 240f)
            close()
            moveTo(760f, 240f)
            quadToRelative(17f, 0f, 28.5f, -11.5f)
            reflectiveQuadTo(800f, 200f)
            quadToRelative(0f, -17f, -11.5f, -28.5f)
            reflectiveQuadTo(760f, 160f)
            quadToRelative(-17f, 0f, -28.5f, 11.5f)
            reflectiveQuadTo(720f, 200f)
            quadToRelative(0f, 17f, 11.5f, 28.5f)
            reflectiveQuadTo(760f, 240f)
            close()
            moveTo(313f, 720f)
            horizontalLineToRelative(334f)
            quadToRelative(9f, -26f, 28f, -45f)
            reflectiveQuadToRelative(45f, -28f)
            verticalLineToRelative(-334f)
            quadToRelative(-26f, -9f, -45f, -28f)
            reflectiveQuadToRelative(-28f, -45f)
            lineTo(313f, 240f)
            quadToRelative(-9f, 26f, -28f, 45f)
            reflectiveQuadToRelative(-45f, 28f)
            verticalLineToRelative(334f)
            quadToRelative(26f, 9f, 45f, 28f)
            reflectiveQuadToRelative(28f, 45f)
            close()
            moveTo(760f, 800f)
            quadToRelative(17f, 0f, 28.5f, -11.5f)
            reflectiveQuadTo(800f, 760f)
            quadToRelative(0f, -17f, -11.5f, -28.5f)
            reflectiveQuadTo(760f, 720f)
            quadToRelative(-17f, 0f, -28.5f, 11.5f)
            reflectiveQuadTo(720f, 760f)
            quadToRelative(0f, 17f, 11.5f, 28.5f)
            reflectiveQuadTo(760f, 800f)
            close()
            moveTo(200f, 800f)
            quadToRelative(17f, 0f, 28.5f, -11.5f)
            reflectiveQuadTo(240f, 760f)
            quadToRelative(0f, -17f, -11.5f, -28.5f)
            reflectiveQuadTo(200f, 720f)
            quadToRelative(-17f, 0f, -28.5f, 11.5f)
            reflectiveQuadTo(160f, 760f)
            quadToRelative(0f, 17f, 11.5f, 28.5f)
            reflectiveQuadTo(200f, 800f)
            close()
            moveTo(200f, 200f)
            close()
            moveTo(760f, 200f)
            close()
            moveTo(760f, 760f)
            close()
            moveTo(200f, 760f)
            close()
          }
        }
        .build()

    return _ActivityZone!!
  }

@Suppress("ObjectPropertyName") private var _ActivityZone: ImageVector? = null
