/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sadellie.unitto.core.designsystem.icons.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconPack.Base: ImageVector
  get() {
    if (_Base != null) {
      return _Base!!
    }
    _Base =
      ImageVector.Builder(
          name = "Base",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(fill = SolidColor(Color.White)) {
            moveTo(4.036f, 15.835f)
            curveTo(3.61f, 15.835f, 3.238f, 15.748f, 2.921f, 15.575f)
            curveTo(2.608f, 15.398f, 2.358f, 15.182f, 2.171f, 14.925f)
            horizontalLineTo(2.141f)
            verticalLineTo(15.24f)
            curveTo(2.141f, 15.373f, 2.095f, 15.487f, 2.001f, 15.58f)
            curveTo(1.908f, 15.673f, 1.795f, 15.72f, 1.661f, 15.72f)
            curveTo(1.531f, 15.72f, 1.418f, 15.673f, 1.321f, 15.58f)
            curveTo(1.228f, 15.487f, 1.181f, 15.373f, 1.181f, 15.24f)
            verticalLineTo(8.735f)
            curveTo(1.181f, 8.598f, 1.23f, 8.482f, 1.326f, 8.385f)
            curveTo(1.426f, 8.285f, 1.543f, 8.235f, 1.676f, 8.235f)
            curveTo(1.813f, 8.235f, 1.93f, 8.285f, 2.026f, 8.385f)
            curveTo(2.123f, 8.482f, 2.171f, 8.598f, 2.171f, 8.735f)
            verticalLineTo(11.19f)
            horizontalLineTo(2.201f)
            curveTo(2.361f, 10.917f, 2.606f, 10.682f, 2.936f, 10.485f)
            curveTo(3.27f, 10.285f, 3.665f, 10.185f, 4.121f, 10.185f)
            curveTo(4.825f, 10.185f, 5.418f, 10.44f, 5.901f, 10.95f)
            curveTo(6.388f, 11.46f, 6.631f, 12.15f, 6.631f, 13.02f)
            curveTo(6.631f, 13.86f, 6.383f, 14.54f, 5.886f, 15.06f)
            curveTo(5.393f, 15.577f, 4.776f, 15.835f, 4.036f, 15.835f)
            close()
            moveTo(3.871f, 15.005f)
            curveTo(4.388f, 15.005f, 4.81f, 14.825f, 5.136f, 14.465f)
            curveTo(5.463f, 14.102f, 5.626f, 13.62f, 5.626f, 13.02f)
            curveTo(5.626f, 12.417f, 5.461f, 11.932f, 5.131f, 11.565f)
            curveTo(4.805f, 11.198f, 4.385f, 11.015f, 3.871f, 11.015f)
            curveTo(3.388f, 11.015f, 2.976f, 11.2f, 2.636f, 11.57f)
            curveTo(2.296f, 11.937f, 2.126f, 12.425f, 2.126f, 13.035f)
            curveTo(2.126f, 13.635f, 2.291f, 14.113f, 2.621f, 14.47f)
            curveTo(2.951f, 14.827f, 3.368f, 15.005f, 3.871f, 15.005f)
            close()
            moveTo(9.285f, 15.835f)
            curveTo(8.712f, 15.835f, 8.238f, 15.675f, 7.865f, 15.355f)
            curveTo(7.495f, 15.035f, 7.31f, 14.588f, 7.31f, 14.015f)
            curveTo(7.31f, 13.445f, 7.512f, 12.997f, 7.915f, 12.67f)
            curveTo(8.318f, 12.34f, 8.843f, 12.175f, 9.49f, 12.175f)
            curveTo(9.817f, 12.175f, 10.113f, 12.213f, 10.38f, 12.29f)
            curveTo(10.647f, 12.363f, 10.884f, 12.465f, 11.09f, 12.595f)
            verticalLineTo(12.24f)
            curveTo(11.09f, 11.843f, 10.96f, 11.545f, 10.7f, 11.345f)
            curveTo(10.443f, 11.145f, 10.113f, 11.045f, 9.71f, 11.045f)
            curveTo(9.517f, 11.045f, 9.337f, 11.067f, 9.17f, 11.11f)
            curveTo(9.003f, 11.153f, 8.857f, 11.21f, 8.73f, 11.28f)
            curveTo(8.603f, 11.35f, 8.492f, 11.423f, 8.395f, 11.5f)
            curveTo(8.302f, 11.577f, 8.197f, 11.608f, 8.08f, 11.595f)
            curveTo(7.967f, 11.582f, 7.872f, 11.533f, 7.795f, 11.45f)
            curveTo(7.722f, 11.363f, 7.69f, 11.258f, 7.7f, 11.135f)
            curveTo(7.713f, 11.012f, 7.767f, 10.908f, 7.86f, 10.825f)
            curveTo(7.953f, 10.742f, 8.102f, 10.642f, 8.305f, 10.525f)
            curveTo(8.512f, 10.408f, 8.733f, 10.322f, 8.97f, 10.265f)
            curveTo(9.207f, 10.205f, 9.48f, 10.175f, 9.79f, 10.175f)
            curveTo(10.46f, 10.175f, 11.007f, 10.358f, 11.43f, 10.725f)
            curveTo(11.854f, 11.088f, 12.065f, 11.592f, 12.065f, 12.235f)
            verticalLineTo(15.25f)
            curveTo(12.065f, 15.38f, 12.019f, 15.492f, 11.925f, 15.585f)
            curveTo(11.835f, 15.675f, 11.725f, 15.72f, 11.595f, 15.72f)
            curveTo(11.469f, 15.72f, 11.358f, 15.675f, 11.265f, 15.585f)
            curveTo(11.175f, 15.492f, 11.13f, 15.38f, 11.13f, 15.25f)
            verticalLineTo(14.955f)
            horizontalLineTo(11.1f)
            curveTo(10.947f, 15.192f, 10.714f, 15.398f, 10.4f, 15.575f)
            curveTo(10.087f, 15.748f, 9.715f, 15.835f, 9.285f, 15.835f)
            close()
            moveTo(9.5f, 15.035f)
            curveTo(9.937f, 15.035f, 10.314f, 14.878f, 10.63f, 14.565f)
            curveTo(10.947f, 14.248f, 11.107f, 13.88f, 11.11f, 13.46f)
            verticalLineTo(13.425f)
            curveTo(10.937f, 13.265f, 10.72f, 13.142f, 10.46f, 13.055f)
            curveTo(10.203f, 12.965f, 9.94f, 12.92f, 9.67f, 12.92f)
            curveTo(9.247f, 12.92f, 8.915f, 13.015f, 8.675f, 13.205f)
            curveTo(8.435f, 13.392f, 8.315f, 13.652f, 8.315f, 13.985f)
            curveTo(8.315f, 14.312f, 8.418f, 14.568f, 8.625f, 14.755f)
            curveTo(8.835f, 14.942f, 9.127f, 15.035f, 9.5f, 15.035f)
            close()
            moveTo(15.178f, 15.845f)
            curveTo(14.811f, 15.845f, 14.495f, 15.795f, 14.228f, 15.695f)
            curveTo(13.961f, 15.595f, 13.728f, 15.457f, 13.528f, 15.28f)
            curveTo(13.328f, 15.1f, 13.195f, 14.943f, 13.128f, 14.81f)
            curveTo(13.065f, 14.677f, 13.051f, 14.557f, 13.088f, 14.45f)
            curveTo(13.125f, 14.343f, 13.191f, 14.263f, 13.288f, 14.21f)
            curveTo(13.385f, 14.157f, 13.486f, 14.138f, 13.593f, 14.155f)
            curveTo(13.703f, 14.172f, 13.788f, 14.228f, 13.848f, 14.325f)
            curveTo(13.911f, 14.422f, 14.008f, 14.53f, 14.138f, 14.65f)
            curveTo(14.268f, 14.767f, 14.42f, 14.857f, 14.593f, 14.92f)
            curveTo(14.766f, 14.983f, 14.968f, 15.015f, 15.198f, 15.015f)
            curveTo(15.531f, 15.015f, 15.8f, 14.947f, 16.003f, 14.81f)
            curveTo(16.21f, 14.67f, 16.313f, 14.483f, 16.313f, 14.25f)
            curveTo(16.313f, 14.01f, 16.218f, 13.825f, 16.028f, 13.695f)
            curveTo(15.838f, 13.565f, 15.513f, 13.443f, 15.053f, 13.33f)
            curveTo(14.34f, 13.15f, 13.85f, 12.935f, 13.583f, 12.685f)
            curveTo(13.32f, 12.435f, 13.188f, 12.098f, 13.188f, 11.675f)
            curveTo(13.188f, 11.235f, 13.37f, 10.875f, 13.733f, 10.595f)
            curveTo(14.1f, 10.315f, 14.561f, 10.175f, 15.118f, 10.175f)
            curveTo(15.415f, 10.175f, 15.673f, 10.208f, 15.893f, 10.275f)
            curveTo(16.116f, 10.342f, 16.321f, 10.438f, 16.508f, 10.565f)
            curveTo(16.695f, 10.692f, 16.816f, 10.805f, 16.873f, 10.905f)
            curveTo(16.93f, 11.002f, 16.948f, 11.102f, 16.928f, 11.205f)
            curveTo(16.908f, 11.308f, 16.851f, 11.392f, 16.758f, 11.455f)
            curveTo(16.668f, 11.515f, 16.575f, 11.542f, 16.478f, 11.535f)
            curveTo(16.385f, 11.528f, 16.308f, 11.493f, 16.248f, 11.43f)
            curveTo(16.188f, 11.367f, 16.1f, 11.298f, 15.983f, 11.225f)
            curveTo(15.866f, 11.152f, 15.74f, 11.097f, 15.603f, 11.06f)
            curveTo(15.47f, 11.023f, 15.311f, 11.005f, 15.128f, 11.005f)
            curveTo(14.811f, 11.005f, 14.561f, 11.068f, 14.378f, 11.195f)
            curveTo(14.198f, 11.318f, 14.108f, 11.472f, 14.108f, 11.655f)
            curveTo(14.108f, 11.808f, 14.17f, 11.947f, 14.293f, 12.07f)
            curveTo(14.42f, 12.193f, 14.813f, 12.345f, 15.473f, 12.525f)
            curveTo(16.113f, 12.698f, 16.57f, 12.915f, 16.843f, 13.175f)
            curveTo(17.116f, 13.432f, 17.253f, 13.767f, 17.253f, 14.18f)
            curveTo(17.253f, 14.683f, 17.058f, 15.087f, 16.668f, 15.39f)
            curveTo(16.278f, 15.693f, 15.781f, 15.845f, 15.178f, 15.845f)
            close()
            moveTo(20.657f, 15.835f)
            curveTo(19.83f, 15.835f, 19.162f, 15.575f, 18.652f, 15.055f)
            curveTo(18.142f, 14.535f, 17.887f, 13.855f, 17.887f, 13.015f)
            curveTo(17.887f, 12.225f, 18.134f, 11.557f, 18.627f, 11.01f)
            curveTo(19.121f, 10.46f, 19.782f, 10.185f, 20.612f, 10.185f)
            curveTo(21.382f, 10.185f, 21.997f, 10.42f, 22.457f, 10.89f)
            curveTo(22.917f, 11.357f, 23.147f, 11.948f, 23.147f, 12.665f)
            verticalLineTo(12.725f)
            curveTo(23.147f, 12.878f, 23.092f, 13.01f, 22.982f, 13.12f)
            curveTo(22.875f, 13.227f, 22.746f, 13.28f, 22.592f, 13.28f)
            horizontalLineTo(18.447f)
            verticalLineTo(12.515f)
            horizontalLineTo(22.177f)
            verticalLineTo(12.505f)
            curveTo(22.157f, 12.098f, 22.012f, 11.753f, 21.742f, 11.47f)
            curveTo(21.472f, 11.183f, 21.096f, 11.04f, 20.612f, 11.04f)
            curveTo(20.109f, 11.04f, 19.694f, 11.217f, 19.367f, 11.57f)
            curveTo(19.044f, 11.92f, 18.882f, 12.402f, 18.882f, 13.015f)
            curveTo(18.882f, 13.655f, 19.052f, 14.14f, 19.392f, 14.47f)
            curveTo(19.732f, 14.8f, 20.169f, 14.965f, 20.702f, 14.965f)
            curveTo(20.929f, 14.965f, 21.129f, 14.94f, 21.302f, 14.89f)
            curveTo(21.479f, 14.837f, 21.64f, 14.762f, 21.787f, 14.665f)
            curveTo(21.937f, 14.568f, 22.054f, 14.482f, 22.137f, 14.405f)
            curveTo(22.221f, 14.325f, 22.314f, 14.283f, 22.417f, 14.28f)
            curveTo(22.524f, 14.277f, 22.622f, 14.312f, 22.712f, 14.385f)
            curveTo(22.802f, 14.455f, 22.854f, 14.545f, 22.867f, 14.655f)
            curveTo(22.884f, 14.765f, 22.847f, 14.878f, 22.757f, 14.995f)
            curveTo(22.667f, 15.112f, 22.515f, 15.243f, 22.302f, 15.39f)
            curveTo(22.089f, 15.537f, 21.855f, 15.647f, 21.602f, 15.72f)
            curveTo(21.352f, 15.797f, 21.037f, 15.835f, 20.657f, 15.835f)
            close()
          }
        }
        .build()

    return _Base!!
  }

@Suppress("ObjectPropertyName") private var _Base: ImageVector? = null
