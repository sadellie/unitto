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

package com.sadellie.unitto.feature.converter.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.designsystem.icons.symbols.Favorite
import com.sadellie.unitto.core.designsystem.icons.symbols.FavoriteFill
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols

@Composable
internal fun FavoritesButton(state: Boolean, onClick: () -> Unit) {
  IconButton(onClick = onClick) {
    val animationSpec = MaterialTheme.motionScheme.defaultEffectsSpec<Float>()
    AnimatedContent(
      targetState = state,
      transitionSpec = {
        (scaleIn(animationSpec) togetherWith scaleOut(animationSpec)).using(
          SizeTransform(clip = false)
        )
      },
      label = "Animated click",
    ) {
      Icon(
        if (it) Symbols.FavoriteFill else Symbols.Favorite,
        contentDescription = stringResource(R.string.converter_favorite_button_description),
      )
    }
  }
}
