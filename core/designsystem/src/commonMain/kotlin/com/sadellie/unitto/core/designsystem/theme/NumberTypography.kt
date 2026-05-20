/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2026 Elshan Agaev
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

package com.sadellie.unitto.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import unitto.core.designsystem.generated.resources.Res
import unitto.core.designsystem.generated.resources.google_sans_for_unitto

val LocalNumberTypography = staticCompositionLocalOf {
  numberTypographyUnittoImpl(FontFamily.Default)
}

/**
 * Branded typography
 *
 * `fonttools varLib.mutator font.ttf wght=400 wdth=86 ROND=100 slnt=0 opsz=28`
 */
@Composable
fun numberTypographyUnitto(): Typography =
  numberTypographyUnittoImpl(
    FontFamily(Font(resource = Res.font.google_sans_for_unitto, weight = FontWeight.Normal))
  )

private fun numberTypographyUnittoImpl(fontFamily: FontFamily): Typography {
  return Typography(
    displayLarge =
      TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.0.sp,
        letterSpacing = (-0.2).sp,
      ),
    displayMedium =
      TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.0.sp,
        letterSpacing = 0.0.sp,
      ),
    displaySmall =
      TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.0.sp,
        letterSpacing = 0.0.sp,
      ),
  )
}
