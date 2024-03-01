/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.sadellie.unitto.core.designsystem.R

@Immutable
data class NumberTypography(
  val displayLarge: TextStyle,
  val displayMedium: TextStyle,
  val displaySmall: TextStyle,
)

/** Default typography using system font. */
val NumberTypographySystem by lazy {
  val typography = Typography()
  NumberTypography(
    displayLarge = typography.displayLarge,
    displayMedium = typography.displayMedium,
    displaySmall = typography.displaySmall,
  )
}

/** Branded typography using Lato font. */
val NumberTypographyUnitto by lazy {
  NumberTypography(
    displayLarge =
      TextStyle(
        fontFamily = FontFamily.lato,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.0.sp,
        letterSpacing = (-0.2).sp,
      ),
    displayMedium =
      TextStyle(
        fontFamily = FontFamily.lato,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.0.sp,
        letterSpacing = 0.0.sp,
      ),
    displaySmall =
      TextStyle(
        fontFamily = FontFamily.lato,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.0.sp,
        letterSpacing = 0.0.sp,
      ),
  )
}

/** Typography for big text fields with numbers. */
val LocalNumberTypography = staticCompositionLocalOf { NumberTypographySystem }

private val FontFamily.Companion.lato: FontFamily
  get() = FontFamily(Font(R.font.lato_regular, weight = FontWeight.Normal))

@Preview(widthDp = 480)
@Composable
private fun PreviewNumberTypography() {
  CompositionLocalProvider(LocalNumberTypography provides NumberTypographyUnitto) {
    val textStyles =
      mapOf(
        "displayLarge" to LocalNumberTypography.current.displayLarge,
        "displayMedium" to LocalNumberTypography.current.displayMedium,
        "displaySmall" to LocalNumberTypography.current.displaySmall,
      )

    LazyColumn(Modifier.background(MaterialTheme.colorScheme.background)) {
      textStyles.forEach { (label, style) ->
        item {
          Text(
            text = "$label 123 Error 7 1⁄2",
            style = style,
            color = MaterialTheme.colorScheme.onBackground,
          )
        }
      }
    }
  }
}
