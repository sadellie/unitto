/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.BasicColoredCheckbox
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.ThemmoController
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode

@Composable
internal fun MonetModeSelector(
  modifier: Modifier = Modifier,
  selected: MonetMode,
  onItemClick: (MonetMode) -> Unit,
  customColor: Color,
  themingMode: ThemingMode,
) {
  val listState = rememberLazyListState()

  LaunchedEffect(Unit) {
    val index = MonetMode.entries.indexOf(selected)
    if (index >= 0) listState.scrollToItem(index)
  }

  LazyRow(
    modifier = modifier,
    state = listState,
    horizontalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    items(MonetMode.entries) { monetMode ->
      Themmo(
        themmoController =
          remember(customColor, themingMode) {
            ThemmoController(
              lightColorScheme = lightColorScheme(),
              darkColorScheme = darkColorScheme(),
              themingMode = themingMode,
              dynamicThemeEnabled = false,
              amoledThemeEnabled = false,
              customColor = customColor,
              monetMode = monetMode,
            )
          }
      ) {
        MonetModeCheckbox(selected = monetMode == selected, onClick = { onItemClick(monetMode) })
      }
    }
  }
}

@Composable
private fun MonetModeCheckbox(selected: Boolean, onClick: () -> Unit) {
  BasicColoredCheckbox(
    selected = selected,
    onClick = onClick,
    color = MaterialTheme.colorScheme.secondary,
    checkIconColor = MaterialTheme.colorScheme.inverseOnSurface,
    checkBackgroundColor = MaterialTheme.colorScheme.inverseSurface,
  ) {
    Box(
      Modifier.fillMaxSize(0.5f)
        .background(MaterialTheme.colorScheme.primary)
        .align(Alignment.BottomStart)
    )
    Box(
      Modifier.fillMaxSize(0.5f)
        .background(MaterialTheme.colorScheme.secondaryContainer)
        .align(Alignment.BottomEnd)
    )
  }
}

@Composable
@Preview
private fun PreviewMMonetModeCheckbox() {
  MonetModeCheckbox(selected = true, onClick = {})
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
