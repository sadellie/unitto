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
    primary = Color(0xFF4F6632),
    onPrimary = Color(0xFFEFFFD4),
    primaryContainer = Color(0xFFD0ECAB),
    onPrimaryContainer = Color(0xFF425826),
    secondary = Color(0xFF58634A),
    onSecondary = Color(0xFFF2FDDD),
    secondaryContainer = Color(0xFFDCE7C7),
    onSecondaryContainer = Color(0xFF4B553D),
    tertiary = Color(0xFF6A5F27),
    onTertiary = Color(0xFFFFF8EA),
    tertiaryContainer = Color(0xFFF6E6A0),
    onTertiaryContainer = Color(0xFF5E531D),
    error = Color(0xFFA73B21),
    onError = Color(0xFFFFF7F6),
    errorContainer = Color(0xFFFD795A),
    onErrorContainer = Color(0xFF6E1400),
    background = Color(0xFFFAFAF0),
    onBackground = Color(0xFF303429),
    surface = Color(0xFFFAFAF0),
    onSurface = Color(0xFF303429),
    surfaceVariant = Color(0xFFE1E4D4),
    onSurfaceVariant = Color(0xFF5C6154),
    outline = Color(0xFF787C6F),
    outlineVariant = Color(0xFFB0B4A5),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFF0D0F0A),
    inverseOnSurface = Color(0xFF9D9E95),
    inversePrimary = Color(0xFFE1FDBA),
    surfaceDim = Color(0xFFD8DCCC),
    surfaceBright = Color(0xFFFAFAF0),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF3F5E9),
    surfaceContainer = Color(0xFFEDEFE2),
    surfaceContainerHigh = Color(0xFFE7EADB),
    surfaceContainerHighest = Color(0xFFE1E4D4),
    surfaceTint = Color(0xFF4F6632),
    primaryFixed = Color(0xFFD0ECAB),
    primaryFixedDim = Color(0xFFC2DE9E),
    onPrimaryFixed = Color(0xFF304515),
    onPrimaryFixedVariant = Color(0xFF4C622F),
    secondaryFixed = Color(0xFFDCE7C7),
    secondaryFixedDim = Color(0xFFCED9BA),
    onSecondaryFixed = Color(0xFF39422C),
    onSecondaryFixedVariant = Color(0xFF555F47),
    tertiaryFixed = Color(0xFFF6E6A0),
    tertiaryFixedDim = Color(0xFFE7D793),
    onTertiaryFixed = Color(0xFF4B410B),
    onTertiaryFixedVariant = Color(0xFF695D26),
  )
}

val DarkThemeColors: ColorScheme by lazy {
  ColorScheme(
    primary = Color(0xFFB9CE9B),
    onPrimary = Color(0xFF34451F),
    primaryContainer = Color(0xFF465830),
    onPrimaryContainer = Color(0xFFD5EBB6),
    secondary = Color(0xFFC0CBAD),
    onSecondary = Color(0xFF3A432D),
    secondaryContainer = Color(0xFF353F28),
    onSecondaryContainer = Color(0xFFB8C3A6),
    tertiary = Color(0xFFFFF4CB),
    onTertiary = Color(0xFF675C24),
    tertiaryContainer = Color(0xFFF6E6A0),
    onTertiaryContainer = Color(0xFF5E531D),
    error = Color(0xFFF97758),
    onError = Color(0xFF450900),
    errorContainer = Color(0xFF85230A),
    onErrorContainer = Color(0xFFFF9B82),
    background = Color(0xFF0D0F0A),
    onBackground = Color(0xFFE4E7D7),
    surface = Color(0xFF0D0F0A),
    onSurface = Color(0xFFE4E7D7),
    surfaceVariant = Color(0xFF23271D),
    onSurfaceVariant = Color(0xFF919587),
    outline = Color(0xFF73776A),
    outlineVariant = Color(0xFF45493E),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFFFAFAF0),
    inverseOnSurface = Color(0xFF54564F),
    inversePrimary = Color(0xFF53653B),
    surfaceDim = Color(0xFF0D0F0A),
    surfaceBright = Color(0xFF292E23),
    surfaceContainerLowest = Color(0xFF000000),
    surfaceContainerLow = Color(0xFF12140E),
    surfaceContainer = Color(0xFF181B13),
    surfaceContainerHigh = Color(0xFF1D2118),
    surfaceContainerHighest = Color(0xFF23271D),
    surfaceTint = Color(0xFFB9CE9B),
    primaryFixed = Color(0xFFD5EAB6),
    primaryFixedDim = Color(0xFFC7DCA8),
    onPrimaryFixed = Color(0xFF34441F),
    onPrimaryFixedVariant = Color(0xFF4F6138),
    secondaryFixed = Color(0xFFDCE7C7),
    secondaryFixedDim = Color(0xFFCED9BA),
    onSecondaryFixed = Color(0xFF39422C),
    onSecondaryFixedVariant = Color(0xFF555F47),
    tertiaryFixed = Color(0xFFF6E6A0),
    tertiaryFixedDim = Color(0xFFE7D793),
    onTertiaryFixed = Color(0xFF4B410B),
    onTertiaryFixedVariant = Color(0xFF695D26),
  )
}

fun Color.isDark(): Boolean = luminance() < LIGHT_OR_DARK_LUMINANCE_THRESHOLD

private const val LIGHT_OR_DARK_LUMINANCE_THRESHOLD = 0.5f
