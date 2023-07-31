/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

package com.sadellie.unitto.feature.unitslist.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sadellie.unitto.core.base.R

@Composable
internal fun FavoritesButton(
    favoritesOnly: Boolean,
    favoriteAction: () -> Unit
) {
    IconButton(onClick = favoriteAction) {
        AnimatedContent(
            targetState = favoritesOnly,
            transitionSpec = {
                (scaleIn() togetherWith scaleOut()).using(SizeTransform(clip = false))
            }
        ) {
            Icon(
                if (it) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = stringResource(R.string.favorite_button_description)
            )
        }
    }
}
