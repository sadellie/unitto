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

package com.sadellie.unitto.core.ui.common

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.sadellie.unitto.core.base.TopLevelDestinations

@Composable
internal fun UnittoDrawerItem(
    modifier: Modifier = Modifier,
    destination: TopLevelDestinations,
    icon: ImageVector,
    selected: Boolean,
    onClick: (TopLevelDestinations) -> Unit
) {
    NavigationDrawerItem(
        modifier = modifier,
        label = { Text(stringResource(destination.name)) },
        icon = { Icon(icon, stringResource(destination.name)) },
        selected = selected,
        onClick = { onClick(destination) }
    )
}
