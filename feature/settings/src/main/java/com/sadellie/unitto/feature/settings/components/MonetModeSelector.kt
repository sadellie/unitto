/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.ThemmoController
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode

@Composable
internal fun MonetModeSelector(
    modifier: Modifier = Modifier,
    selected: MonetMode,
    onItemClick: (MonetMode) -> Unit,
    monetModes: List<MonetMode>,
    customColor: Color,
    themingMode: ThemingMode,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        val index = monetModes.indexOf(selected)
        if (index >= 0) listState.scrollToItem(index)
    }

    LazyRow(
        modifier = modifier,
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(monetModes) { monetMode ->
            Themmo(
                themmoController = remember(customColor, themingMode) {
                    ThemmoController(
                        lightColorScheme = lightColorScheme(),
                        darkColorScheme = darkColorScheme(),
                        themingMode = themingMode,
                        dynamicThemeEnabled = false,
                        amoledThemeEnabled = false,
                        customColor = customColor,
                        monetMode = monetMode
                    )
                }
            ) {
                MonetModeCheckbox(
                    selected = monetMode == selected,
                    onClick = { onItemClick(monetMode) }
                )
            }
        }
    }
}

@Composable
private fun MonetModeCheckbox(
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(72.dp)
            .clip(RoundedCornerShape(25))
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .padding(8.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary)
                .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape),
            contentAlignment = Alignment.BottomStart
        ) {
            // Is this bad? Yes. Does it work? Also yes.
            Box(modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary))
            Box(modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(0.5f)
                .background(MaterialTheme.colorScheme.secondaryContainer))
        }
        AnimatedVisibility(
            visible = selected,
            enter = fadeIn(tween(250)) + scaleIn(tween(150)),
            exit = fadeOut(tween(250)) + scaleOut(tween(150)),
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.inverseSurface, CircleShape)
                    .padding(4.dp)
            )
        }
    }
}
