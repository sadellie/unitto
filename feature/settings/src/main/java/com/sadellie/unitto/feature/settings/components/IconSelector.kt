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

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.data.model.LauncherIcon


@Composable
internal fun IconsSelector(
    modifier: Modifier,
    selectedColor: Color,
    icons: List<LauncherIcon>,
    selectedIcon: LauncherIcon,
    onIconChange: (LauncherIcon) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(icons) {
            IconCheckbox(
                iconRes = it.foregroundDrawable,
                foregroundColor = Color(it.foregroundColor),
                backGroundColor = selectedColor,
                selected = it == selectedIcon,
                onClick = { onIconChange(it) },
                label = it.labelString
            )
        }
    }
}

@Composable
private fun IconCheckbox(
    @DrawableRes iconRes: Int,
    foregroundColor: Color,
    backGroundColor: Color,
    @StringRes label: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(72.dp)
            .clip(RoundedCornerShape(25))
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
            .padding(bottom = 8.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .padding(8.dp)
                .clip(CircleShape)
                .background(backGroundColor)
                .border(1.dp, Color.Black.copy(0.5f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = foregroundColor,
                modifier = Modifier.scale(1.5f)
            )

            // bug in language, need to call it like this
            androidx.compose.animation.AnimatedVisibility(
                visible = selected,
                enter = fadeIn(tween(250)) + scaleIn(tween(150)),
                exit = fadeOut(tween(250)) + scaleOut(tween(150)),
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.scrim.copy(0.5f))
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = if (backGroundColor.luminance() > 0.5) Color.Black else Color.White,
                    )
                }
            }
        }

        Text(stringResource(label), style = MaterialTheme.typography.labelSmall)
    }
}
