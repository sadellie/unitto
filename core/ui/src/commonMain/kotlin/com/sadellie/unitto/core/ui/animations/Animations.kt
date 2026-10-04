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

package com.sadellie.unitto.core.ui.animations

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset

@Composable
context(scope: LazyItemScope)
fun Modifier.animateItemDefault(): Modifier =
  with(scope) {
    val fadeSpec = MaterialTheme.motionScheme.defaultEffectsSpec<Float>()
    val placementSpec = MaterialTheme.motionScheme.defaultSpatialSpec<IntOffset>()
    this@animateItemDefault.animateItem(
      fadeInSpec = fadeSpec,
      placementSpec = placementSpec,
      fadeOutSpec = fadeSpec,
    )
  }
