package com.sadellie.unitto.core.designsystem.icons.symbols

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Symbols.Dialpad: ImageVector
  get() {
    val current = _dialpad
    if (current != null) return current

    return ImageVector.Builder(
      name = "Dialpad",
      defaultWidth = 24.0.dp,
      defaultHeight = 24.0.dp,
      viewportWidth = 960.0f,
      viewportHeight = 960.0f,
    ).apply {
      // M480 -40 q-33 0 -56.5 -23.5 T400 -120 q0 -33 23.5 -56.5 T480 -200 q33 0 56.5 23.5 T560 -120 q0 33 -23.5 56.5 T480 -40Z M240 -760 q-33 0 -56.5 -23.5 T160 -840 q0 -33 23.5 -56.5 T240 -920 q33 0 56.5 23.5 T320 -840 q0 33 -23.5 56.5 T240 -760Z m0 240 q-33 0 -56.5 -23.5 T160 -600 q0 -33 23.5 -56.5 T240 -680 q33 0 56.5 23.5 T320 -600 q0 33 -23.5 56.5 T240 -520Z m0 240 q-33 0 -56.5 -23.5 T160 -360 q0 -33 23.5 -56.5 T240 -440 q33 0 56.5 23.5 T320 -360 q0 33 -23.5 56.5 T240 -280Z m480 -480 q-33 0 -56.5 -23.5 T640 -840 q0 -33 23.5 -56.5 T720 -920 q33 0 56.5 23.5 T800 -840 q0 33 -23.5 56.5 T720 -760Z M480 -280 q-33 0 -56.5 -23.5 T400 -360 q0 -33 23.5 -56.5 T480 -440 q33 0 56.5 23.5 T560 -360 q0 33 -23.5 56.5 T480 -280Z m240 0 q-33 0 -56.5 -23.5 T640 -360 q0 -33 23.5 -56.5 T720 -440 q33 0 56.5 23.5 T800 -360 q0 33 -23.5 56.5 T720 -280Z m0 -240 q-33 0 -56.5 -23.5 T640 -600 q0 -33 23.5 -56.5 T720 -680 q33 0 56.5 23.5 T800 -600 q0 33 -23.5 56.5 T720 -520Z m-240 0 q-33 0 -56.5 -23.5 T400 -600 q0 -33 23.5 -56.5 T480 -680 q33 0 56.5 23.5 T560 -600 q0 33 -23.5 56.5 T480 -520Z m0 -240 q-33 0 -56.5 -23.5 T400 -840 q0 -33 23.5 -56.5 T480 -920 q33 0 56.5 23.5 T560 -840 q0 33 -23.5 56.5 T480 -760Z
      path(
        fill = SolidColor(Color(0xFFE3E3E3)),
      ) {
        // M 480 920
        moveTo(x = 480.0f, y = 920.0f)
        // q -33 0 -56.5 -23.5
        quadToRelative(
          dx1 = -33.0f,
          dy1 = 0.0f,
          dx2 = -56.5f,
          dy2 = -23.5f,
        )
        // T 400 840
        reflectiveQuadTo(
          x1 = 400.0f,
          y1 = 840.0f,
        )
        // q 0 -33 23.5 -56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = -33.0f,
          dx2 = 23.5f,
          dy2 = -56.5f,
        )
        // T 480 760
        reflectiveQuadTo(
          x1 = 480.0f,
          y1 = 760.0f,
        )
        // q 33 0 56.5 23.5
        quadToRelative(
          dx1 = 33.0f,
          dy1 = 0.0f,
          dx2 = 56.5f,
          dy2 = 23.5f,
        )
        // T 560 840
        reflectiveQuadTo(
          x1 = 560.0f,
          y1 = 840.0f,
        )
        // q 0 33 -23.5 56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = 33.0f,
          dx2 = -23.5f,
          dy2 = 56.5f,
        )
        // T 480 920z
        reflectiveQuadTo(
          x1 = 480.0f,
          y1 = 920.0f,
        )
        close()
        // M 240 200
        moveTo(x = 240.0f, y = 200.0f)
        // q -33 0 -56.5 -23.5
        quadToRelative(
          dx1 = -33.0f,
          dy1 = 0.0f,
          dx2 = -56.5f,
          dy2 = -23.5f,
        )
        // T 160 120
        reflectiveQuadTo(
          x1 = 160.0f,
          y1 = 120.0f,
        )
        // q 0 -33 23.5 -56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = -33.0f,
          dx2 = 23.5f,
          dy2 = -56.5f,
        )
        // T 240 40
        reflectiveQuadTo(
          x1 = 240.0f,
          y1 = 40.0f,
        )
        // q 33 0 56.5 23.5
        quadToRelative(
          dx1 = 33.0f,
          dy1 = 0.0f,
          dx2 = 56.5f,
          dy2 = 23.5f,
        )
        // T 320 120
        reflectiveQuadTo(
          x1 = 320.0f,
          y1 = 120.0f,
        )
        // q 0 33 -23.5 56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = 33.0f,
          dx2 = -23.5f,
          dy2 = 56.5f,
        )
        // T 240 200z
        reflectiveQuadTo(
          x1 = 240.0f,
          y1 = 200.0f,
        )
        close()
        // m 0 240
        moveToRelative(dx = 0.0f, dy = 240.0f)
        // q -33 0 -56.5 -23.5
        quadToRelative(
          dx1 = -33.0f,
          dy1 = 0.0f,
          dx2 = -56.5f,
          dy2 = -23.5f,
        )
        // T 160 360
        reflectiveQuadTo(
          x1 = 160.0f,
          y1 = 360.0f,
        )
        // q 0 -33 23.5 -56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = -33.0f,
          dx2 = 23.5f,
          dy2 = -56.5f,
        )
        // T 240 280
        reflectiveQuadTo(
          x1 = 240.0f,
          y1 = 280.0f,
        )
        // q 33 0 56.5 23.5
        quadToRelative(
          dx1 = 33.0f,
          dy1 = 0.0f,
          dx2 = 56.5f,
          dy2 = 23.5f,
        )
        // T 320 360
        reflectiveQuadTo(
          x1 = 320.0f,
          y1 = 360.0f,
        )
        // q 0 33 -23.5 56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = 33.0f,
          dx2 = -23.5f,
          dy2 = 56.5f,
        )
        // T 240 440z
        reflectiveQuadTo(
          x1 = 240.0f,
          y1 = 440.0f,
        )
        close()
        // m 0 240
        moveToRelative(dx = 0.0f, dy = 240.0f)
        // q -33 0 -56.5 -23.5
        quadToRelative(
          dx1 = -33.0f,
          dy1 = 0.0f,
          dx2 = -56.5f,
          dy2 = -23.5f,
        )
        // T 160 600
        reflectiveQuadTo(
          x1 = 160.0f,
          y1 = 600.0f,
        )
        // q 0 -33 23.5 -56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = -33.0f,
          dx2 = 23.5f,
          dy2 = -56.5f,
        )
        // T 240 520
        reflectiveQuadTo(
          x1 = 240.0f,
          y1 = 520.0f,
        )
        // q 33 0 56.5 23.5
        quadToRelative(
          dx1 = 33.0f,
          dy1 = 0.0f,
          dx2 = 56.5f,
          dy2 = 23.5f,
        )
        // T 320 600
        reflectiveQuadTo(
          x1 = 320.0f,
          y1 = 600.0f,
        )
        // q 0 33 -23.5 56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = 33.0f,
          dx2 = -23.5f,
          dy2 = 56.5f,
        )
        // T 240 680z
        reflectiveQuadTo(
          x1 = 240.0f,
          y1 = 680.0f,
        )
        close()
        // m 480 -480
        moveToRelative(dx = 480.0f, dy = -480.0f)
        // q -33 0 -56.5 -23.5
        quadToRelative(
          dx1 = -33.0f,
          dy1 = 0.0f,
          dx2 = -56.5f,
          dy2 = -23.5f,
        )
        // T 640 120
        reflectiveQuadTo(
          x1 = 640.0f,
          y1 = 120.0f,
        )
        // q 0 -33 23.5 -56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = -33.0f,
          dx2 = 23.5f,
          dy2 = -56.5f,
        )
        // T 720 40
        reflectiveQuadTo(
          x1 = 720.0f,
          y1 = 40.0f,
        )
        // q 33 0 56.5 23.5
        quadToRelative(
          dx1 = 33.0f,
          dy1 = 0.0f,
          dx2 = 56.5f,
          dy2 = 23.5f,
        )
        // T 800 120
        reflectiveQuadTo(
          x1 = 800.0f,
          y1 = 120.0f,
        )
        // q 0 33 -23.5 56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = 33.0f,
          dx2 = -23.5f,
          dy2 = 56.5f,
        )
        // T 720 200z
        reflectiveQuadTo(
          x1 = 720.0f,
          y1 = 200.0f,
        )
        close()
        // M 480 680
        moveTo(x = 480.0f, y = 680.0f)
        // q -33 0 -56.5 -23.5
        quadToRelative(
          dx1 = -33.0f,
          dy1 = 0.0f,
          dx2 = -56.5f,
          dy2 = -23.5f,
        )
        // T 400 600
        reflectiveQuadTo(
          x1 = 400.0f,
          y1 = 600.0f,
        )
        // q 0 -33 23.5 -56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = -33.0f,
          dx2 = 23.5f,
          dy2 = -56.5f,
        )
        // T 480 520
        reflectiveQuadTo(
          x1 = 480.0f,
          y1 = 520.0f,
        )
        // q 33 0 56.5 23.5
        quadToRelative(
          dx1 = 33.0f,
          dy1 = 0.0f,
          dx2 = 56.5f,
          dy2 = 23.5f,
        )
        // T 560 600
        reflectiveQuadTo(
          x1 = 560.0f,
          y1 = 600.0f,
        )
        // q 0 33 -23.5 56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = 33.0f,
          dx2 = -23.5f,
          dy2 = 56.5f,
        )
        // T 480 680z
        reflectiveQuadTo(
          x1 = 480.0f,
          y1 = 680.0f,
        )
        close()
        // m 240 0
        moveToRelative(dx = 240.0f, dy = 0.0f)
        // q -33 0 -56.5 -23.5
        quadToRelative(
          dx1 = -33.0f,
          dy1 = 0.0f,
          dx2 = -56.5f,
          dy2 = -23.5f,
        )
        // T 640 600
        reflectiveQuadTo(
          x1 = 640.0f,
          y1 = 600.0f,
        )
        // q 0 -33 23.5 -56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = -33.0f,
          dx2 = 23.5f,
          dy2 = -56.5f,
        )
        // T 720 520
        reflectiveQuadTo(
          x1 = 720.0f,
          y1 = 520.0f,
        )
        // q 33 0 56.5 23.5
        quadToRelative(
          dx1 = 33.0f,
          dy1 = 0.0f,
          dx2 = 56.5f,
          dy2 = 23.5f,
        )
        // T 800 600
        reflectiveQuadTo(
          x1 = 800.0f,
          y1 = 600.0f,
        )
        // q 0 33 -23.5 56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = 33.0f,
          dx2 = -23.5f,
          dy2 = 56.5f,
        )
        // T 720 680z
        reflectiveQuadTo(
          x1 = 720.0f,
          y1 = 680.0f,
        )
        close()
        // m 0 -240
        moveToRelative(dx = 0.0f, dy = -240.0f)
        // q -33 0 -56.5 -23.5
        quadToRelative(
          dx1 = -33.0f,
          dy1 = 0.0f,
          dx2 = -56.5f,
          dy2 = -23.5f,
        )
        // T 640 360
        reflectiveQuadTo(
          x1 = 640.0f,
          y1 = 360.0f,
        )
        // q 0 -33 23.5 -56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = -33.0f,
          dx2 = 23.5f,
          dy2 = -56.5f,
        )
        // T 720 280
        reflectiveQuadTo(
          x1 = 720.0f,
          y1 = 280.0f,
        )
        // q 33 0 56.5 23.5
        quadToRelative(
          dx1 = 33.0f,
          dy1 = 0.0f,
          dx2 = 56.5f,
          dy2 = 23.5f,
        )
        // T 800 360
        reflectiveQuadTo(
          x1 = 800.0f,
          y1 = 360.0f,
        )
        // q 0 33 -23.5 56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = 33.0f,
          dx2 = -23.5f,
          dy2 = 56.5f,
        )
        // T 720 440z
        reflectiveQuadTo(
          x1 = 720.0f,
          y1 = 440.0f,
        )
        close()
        // m -240 0
        moveToRelative(dx = -240.0f, dy = 0.0f)
        // q -33 0 -56.5 -23.5
        quadToRelative(
          dx1 = -33.0f,
          dy1 = 0.0f,
          dx2 = -56.5f,
          dy2 = -23.5f,
        )
        // T 400 360
        reflectiveQuadTo(
          x1 = 400.0f,
          y1 = 360.0f,
        )
        // q 0 -33 23.5 -56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = -33.0f,
          dx2 = 23.5f,
          dy2 = -56.5f,
        )
        // T 480 280
        reflectiveQuadTo(
          x1 = 480.0f,
          y1 = 280.0f,
        )
        // q 33 0 56.5 23.5
        quadToRelative(
          dx1 = 33.0f,
          dy1 = 0.0f,
          dx2 = 56.5f,
          dy2 = 23.5f,
        )
        // T 560 360
        reflectiveQuadTo(
          x1 = 560.0f,
          y1 = 360.0f,
        )
        // q 0 33 -23.5 56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = 33.0f,
          dx2 = -23.5f,
          dy2 = 56.5f,
        )
        // T 480 440z
        reflectiveQuadTo(
          x1 = 480.0f,
          y1 = 440.0f,
        )
        close()
        // m 0 -240
        moveToRelative(dx = 0.0f, dy = -240.0f)
        // q -33 0 -56.5 -23.5
        quadToRelative(
          dx1 = -33.0f,
          dy1 = 0.0f,
          dx2 = -56.5f,
          dy2 = -23.5f,
        )
        // T 400 120
        reflectiveQuadTo(
          x1 = 400.0f,
          y1 = 120.0f,
        )
        // q 0 -33 23.5 -56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = -33.0f,
          dx2 = 23.5f,
          dy2 = -56.5f,
        )
        // T 480 40
        reflectiveQuadTo(
          x1 = 480.0f,
          y1 = 40.0f,
        )
        // q 33 0 56.5 23.5
        quadToRelative(
          dx1 = 33.0f,
          dy1 = 0.0f,
          dx2 = 56.5f,
          dy2 = 23.5f,
        )
        // T 560 120
        reflectiveQuadTo(
          x1 = 560.0f,
          y1 = 120.0f,
        )
        // q 0 33 -23.5 56.5
        quadToRelative(
          dx1 = 0.0f,
          dy1 = 33.0f,
          dx2 = -23.5f,
          dy2 = 56.5f,
        )
        // T 480 200z
        reflectiveQuadTo(
          x1 = 480.0f,
          y1 = 200.0f,
        )
        close()
      }
    }.build().also { _dialpad = it }
  }

@Suppress("ObjectPropertyName")
private var _dialpad: ImageVector? = null
