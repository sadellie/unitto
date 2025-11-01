/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2025 Elshan Agaev
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

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import kotlin.getValue

val LightThemeColors: ColorScheme by lazy {
  ColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
    surfaceTint = surfaceTintLight,
    primaryFixed = primaryFixed,
    primaryFixedDim = primaryFixedDim,
    onPrimaryFixed = onPrimaryFixed,
    onPrimaryFixedVariant = onPrimaryFixedVariant,
    secondaryFixed = secondaryFixed,
    secondaryFixedDim = secondaryFixedDim,
    onSecondaryFixed = onSecondaryFixed,
    onSecondaryFixedVariant = onSecondaryFixedVariant,
    tertiaryFixed = tertiaryFixed,
    tertiaryFixedDim = tertiaryFixedDim,
    onTertiaryFixed = onTertiaryFixed,
    onTertiaryFixedVariant = onTertiaryFixedVariant,
  )
}

val DarkThemeColors: ColorScheme by lazy {
  ColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
    surfaceTint = surfaceTintDark,
    primaryFixed = primaryFixed,
    primaryFixedDim = primaryFixedDim,
    onPrimaryFixed = onPrimaryFixed,
    onPrimaryFixedVariant = onPrimaryFixedVariant,
    secondaryFixed = secondaryFixed,
    secondaryFixedDim = secondaryFixedDim,
    onSecondaryFixed = onSecondaryFixed,
    onSecondaryFixedVariant = onSecondaryFixedVariant,
    tertiaryFixed = tertiaryFixed,
    tertiaryFixedDim = tertiaryFixedDim,
    onTertiaryFixed = onTertiaryFixed,
    onTertiaryFixedVariant = onTertiaryFixedVariant,
  )
}

fun Color.isDark(): Boolean = luminance() < LIGHT_OR_DARK_LUMINANCE_THRESHOLD

private val primaryLight = Color(0xFF1A6D32)
private val onPrimaryLight = Color(0xFFE9FFE6)
private val primaryContainerLight = Color(0xFFA0F3A9)
private val onPrimaryContainerLight = Color(0xFF005D25)
private val inversePrimaryLight = Color(0xFFA0F3A9)
private val secondaryLight = Color(0xFF526451)
private val onSecondaryLight = Color(0xFFEAFFE7)
private val secondaryContainerLight = Color(0xFFD4E8D1)
private val onSecondaryContainerLight = Color(0xFF445645)
private val tertiaryLight = Color(0xFF5C6330)
private val onTertiaryLight = Color(0xFFF7FEBC)
private val tertiaryContainerLight = Color(0xFFF4FCBA)
private val onTertiaryContainerLight = Color(0xFF5A612E)
private val backgroundLight = Color(0xFFF8FAF3)
private val onBackgroundLight = Color(0xFF2D342C)
private val surfaceLight = Color(0xFFF8FAF3)
private val onSurfaceLight = Color(0xFF2D342C)
private val surfaceVariantLight = Color(0xFFDDE5D9)
private val onSurfaceVariantLight = Color(0xFF596158)
private val surfaceTintLight = Color(0xFF1A6D32)
private val inverseSurfaceLight = Color(0xFF0C0F0B)
private val inverseOnSurfaceLight = Color(0xFF9B9E98)
private val errorLight = Color(0xFFA73B21)
private val onErrorLight = Color(0xFFFFF7F6)
private val errorContainerLight = Color(0xFFFD795A)
private val onErrorContainerLight = Color(0xFF6E1400)
private val outlineLight = Color(0xFF757D73)
private val outlineVariantLight = Color(0xFFACB4A9)
private val scrimLight = Color(0xFF000000)
private val surfaceBrightLight = Color(0xFFF8FAF3)
private val surfaceContainerLight = Color(0xFFEAF0E6)
private val surfaceContainerHighLight = Color(0xFFE4EADF)
private val surfaceContainerHighestLight = Color(0xFFDDE5D9)
private val surfaceContainerLowLight = Color(0xFFF1F5EC)
private val surfaceContainerLowestLight = Color(0xFFFFFFFF)
private val surfaceDimLight = Color(0xFFD4DCD1)

private val primaryDark = Color(0xFF88D991)
private val onPrimaryDark = Color(0xFF004B1C)
private val primaryContainerDark = Color(0xFF036027)
private val onPrimaryContainerDark = Color(0xFFA4F6AC)
private val inversePrimaryDark = Color(0xFF1A6D32)
private val secondaryDark = Color(0xFFB8CCB6)
private val onSecondaryDark = Color(0xFF334434)
private val secondaryContainerDark = Color(0xFF2F402F)
private val onSecondaryContainerDark = Color(0xFFB1C5AF)
private val tertiaryDark = Color(0xFFF7FFBD)
private val onTertiaryDark = Color(0xFF5C6330)
private val tertiaryContainerDark = Color(0xFFE9F0AF)
private val onTertiaryContainerDark = Color(0xFF545B28)
private val backgroundDark = Color(0xFF0C0F0B)
private val onBackgroundDark = Color(0xFFE0E8DC)
private val surfaceDark = Color(0xFF0C0F0B)
private val onSurfaceDark = Color(0xFFE0E8DC)
private val surfaceVariantDark = Color(0xFF202820)
private val onSurfaceVariantDark = Color(0xFF8E968B)
private val surfaceTintDark = Color(0xFF88D991)
private val inverseSurfaceDark = Color(0xFFF8FAF3)
private val inverseOnSurfaceDark = Color(0xFF525651)
private val errorDark = Color(0xFFF97758)
private val onErrorDark = Color(0xFF450900)
private val errorContainerDark = Color(0xFF85230A)
private val onErrorContainerDark = Color(0xFFFF9B82)
private val outlineDark = Color(0xFF70786E)
private val outlineVariantDark = Color(0xFF424A41)
private val scrimDark = Color(0xFF000000)
private val surfaceBrightDark = Color(0xFF272E26)
private val surfaceContainerDark = Color(0xFF151B15)
private val surfaceContainerHighDark = Color(0xFF1B211B)
private val surfaceContainerHighestDark = Color(0xFF202820)
private val surfaceContainerLowDark = Color(0xFF101510)
private val surfaceContainerLowestDark = Color(0xFF000000)
private val surfaceDimDark = Color(0xFF0C0F0B)

private val primaryFixed = Color(0xFFA0F3A9)
private val primaryFixedDim = Color(0xFF92E49C)
private val onPrimaryFixed = Color(0xFF00481B)
private val onPrimaryFixedVariant = Color(0xFF12672E)
private val secondaryFixed = Color(0xFFD4E8D1)
private val secondaryFixedDim = Color(0xFFC6DAC3)
private val onSecondaryFixed = Color(0xFF324433)
private val onSecondaryFixedVariant = Color(0xFF4E604E)
private val tertiaryFixed = Color(0xFFF4FCBA)
private val tertiaryFixedDim = Color(0xFFE6EEAD)
private val onTertiaryFixed = Color(0xFF484F1E)
private val onTertiaryFixedVariant = Color(0xFF656C38)

private const val LIGHT_OR_DARK_LUMINANCE_THRESHOLD = 0.5f
