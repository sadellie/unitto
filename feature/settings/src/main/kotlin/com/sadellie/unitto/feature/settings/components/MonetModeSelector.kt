/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

package com.sadellie.unitto.feature.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.designsystem.theme.DarkThemeColors
import com.sadellie.unitto.core.designsystem.theme.LightThemeColors
import com.sadellie.unitto.core.ui.BasicColoredCheckbox
import io.github.sadellie.themmo.commonProvideColorScheme
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun MonetModeSelector(
  modifier: Modifier = Modifier,
  selected: MonetMode,
  onItemClick: (MonetMode) -> Unit,
  customColor: Color,
  themingMode: ThemingMode,
  paddingValues: PaddingValues = PaddingValues(0.dp),
) {
  val listState = rememberLazyListState()
  val isSystemInDarkTheme = isSystemInDarkTheme()

  LaunchedEffect(Unit) {
    val index = MonetMode.entries.indexOf(selected)
    if (index >= 0) listState.scrollToItem(index)
  }

  LazyRow(
    modifier = modifier,
    state = listState,
    horizontalArrangement = Arrangement.spacedBy(Sizes.extraSmall),
    contentPadding = paddingValues,
  ) {
    items(MonetMode.entries) { monetMode ->
      val colorScheme =
        commonProvideColorScheme(
          isSystemDark = isSystemInDarkTheme,
          currentThemingMode = themingMode,
          isDynamicThemeEnabled = false,
          currentCustomColor = customColor,
          currentMonetMode = monetMode,
          lightColorScheme = LightThemeColors,
          darkColorScheme = DarkThemeColors,
          isAmoledThemeEnabled = false,
        )
      MonetModeCheckbox(
        selected = monetMode == selected,
        onClick = { onItemClick(monetMode) },
        colorScheme = colorScheme,
      )
    }
  }
}

@Composable
private fun MonetModeCheckbox(selected: Boolean, onClick: () -> Unit, colorScheme: ColorScheme) {
  BasicColoredCheckbox(selected = selected, onClick = onClick, color = colorScheme.secondary) {
    Box(Modifier.fillMaxSize(0.5f).background(colorScheme.primary).align(Alignment.BottomStart))
    Box(
      Modifier.fillMaxSize(0.5f)
        .background(colorScheme.secondaryContainer)
        .align(Alignment.BottomEnd)
    )
  }
}

@Composable
@Preview
private fun PreviewMMonetModeCheckbox() {
  MonetModeCheckbox(selected = true, onClick = {}, colorScheme = LightThemeColors)
}

@Composable
@Preview
private fun PreviewMonetModeSelector() {
  MonetModeSelector(
    modifier = Modifier,
    selected = MonetMode.TonalSpot,
    onItemClick = {},
    customColor = Color.Red,
    themingMode = ThemingMode.AUTO,
  )
}
