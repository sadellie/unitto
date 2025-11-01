/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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

package io.github.sadellie.themmo

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * This composable lets you change colors with simple api calls.
 *
 * @param themmoController [ThemmoController] that is used to provide current theming state and
 *   methods to change theming states.
 * @param typography Your app's typography.
 * @param content Provided composables will be colored according to [ThemmoController] states.
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Themmo(
  themmoController: ThemmoController = rememberThemmoController(),
  motionScheme: MotionScheme = MotionScheme.expressive(),
  shapes: Shapes = Shapes(),
  typography: Typography = Typography(),
  content: @Composable (ThemmoController) -> Unit,
) {
  val generatedColors: ColorScheme =
    themmoController.provideColorScheme(isSystemDark = isSystemInDarkTheme())

  MaterialExpressiveTheme(
    colorScheme = generatedColors.animateAllColors(motionScheme.fastEffectsSpec()),
    typography = typography,
    motionScheme = motionScheme,
    shapes = shapes,
    content = { content(themmoController) },
  )
}

/**
 * This function animates colors when current color scheme changes.
 *
 * @param animationSpec Animation that will be applied when theming option changes.
 * @return [ColorScheme] with animated colors.
 */
@Composable
private fun ColorScheme.animateAllColors(animationSpec: AnimationSpec<Color>): ColorScheme {

  /**
   * Wraps color into [animateColorAsState].
   *
   * @return Animated [Color].
   */
  @Composable
  fun Color.animateColor() =
    animateColorAsState(targetValue = this, animationSpec = animationSpec, label = "Color change")
      .value

  return ColorScheme(
    primary = primary.animateColor(),
    onPrimary = onPrimary.animateColor(),
    primaryContainer = primaryContainer.animateColor(),
    onPrimaryContainer = onPrimaryContainer.animateColor(),
    inversePrimary = inversePrimary.animateColor(),
    secondary = secondary.animateColor(),
    onSecondary = onSecondary.animateColor(),
    secondaryContainer = secondaryContainer.animateColor(),
    onSecondaryContainer = onSecondaryContainer.animateColor(),
    tertiary = tertiary.animateColor(),
    onTertiary = onTertiary.animateColor(),
    tertiaryContainer = tertiaryContainer.animateColor(),
    onTertiaryContainer = onTertiaryContainer.animateColor(),
    background = background.animateColor(),
    onBackground = onBackground.animateColor(),
    surface = surface.animateColor(),
    onSurface = onSurface.animateColor(),
    surfaceVariant = surfaceVariant.animateColor(),
    onSurfaceVariant = onSurfaceVariant.animateColor(),
    surfaceTint = surfaceTint.animateColor(),
    inverseSurface = inverseSurface.animateColor(),
    inverseOnSurface = inverseOnSurface.animateColor(),
    error = error.animateColor(),
    onError = onError.animateColor(),
    errorContainer = errorContainer.animateColor(),
    onErrorContainer = onErrorContainer.animateColor(),
    outline = outline.animateColor(),
    outlineVariant = outlineVariant.animateColor(),
    scrim = scrim.animateColor(),
    surfaceBright = surfaceBright.animateColor(),
    surfaceDim = surfaceDim.animateColor(),
    surfaceContainer = surfaceContainer.animateColor(),
    surfaceContainerHigh = surfaceContainerHigh.animateColor(),
    surfaceContainerHighest = surfaceContainerHighest.animateColor(),
    surfaceContainerLow = surfaceContainerLow.animateColor(),
    surfaceContainerLowest = surfaceContainerLowest.animateColor(),
    primaryFixed = primaryFixed.animateColor(),
    primaryFixedDim = primaryFixedDim.animateColor(),
    onPrimaryFixed = onPrimaryFixed.animateColor(),
    onPrimaryFixedVariant = onPrimaryFixedVariant.animateColor(),
    secondaryFixed = secondaryFixed.animateColor(),
    secondaryFixedDim = secondaryFixedDim.animateColor(),
    onSecondaryFixed = onSecondaryFixed.animateColor(),
    onSecondaryFixedVariant = onSecondaryFixedVariant.animateColor(),
    tertiaryFixed = tertiaryFixed.animateColor(),
    tertiaryFixedDim = tertiaryFixedDim.animateColor(),
    onTertiaryFixed = onTertiaryFixed.animateColor(),
    onTertiaryFixedVariant = onTertiaryFixedVariant.animateColor(),
  )
}
