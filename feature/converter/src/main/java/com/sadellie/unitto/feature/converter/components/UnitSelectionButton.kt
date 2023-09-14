/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.common.UnittoButton

/**
 * Button to select a unit
 *
 * @param modifier Modifier that is applied to a [Button]
 * @param onClick Function to call when button is clicked (navigate to a unit selection screen)
 * @param label Text on button
 */
@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
internal fun UnitSelectionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    label: String,
    enabled: Boolean = true
) {
    UnittoButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp),
    ) {
        AnimatedContent(
            targetState = label,
            transitionSpec = {
                if (targetState > initialState) {
                    (slideInVertically { height -> height } + fadeIn()) togetherWith
                        slideOutVertically { height -> -height } + fadeOut()
                } else {
                    (slideInVertically { height -> -height } + fadeIn()) togetherWith
                        slideOutVertically { height -> height } + fadeOut()
                }.using(
                    SizeTransform(clip = false)
                )
            },
            label = "Unit change"
        ) {
            Text(
                text = it,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}
