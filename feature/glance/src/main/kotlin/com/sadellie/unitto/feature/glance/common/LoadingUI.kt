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

package com.sadellie.unitto.feature.glance.common

import androidx.compose.runtime.Composable
import androidx.glance.GlanceModifier
import androidx.glance.action.Action
import androidx.glance.action.action
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import com.sadellie.unitto.feature.glance.R

@Composable
internal fun LoadingUI(onClick: Action) {
  Box(
    modifier =
      GlanceModifier.appWidgetBackground()
        .background(UnittoGlanceTheme.colors.surfaceContainer)
        .fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    IconButton(
      glanceModifier = GlanceModifier,
      containerColor = UnittoGlanceTheme.colors.primary,
      iconRes = R.drawable.refresh,
      onClick = onClick,
    )
  }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Composable
@Preview
private fun PreviewLoadingUI() {
  LoadingUI(action {})
}
