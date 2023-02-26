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

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.base.TopLevelDestinations
import com.sadellie.unitto.core.ui.R

@Composable
fun UnittoDrawerSheet(
    modifier: Modifier,
    mainTabs: List<Pair<TopLevelDestinations, ImageVector>>,
    additionalTabs: List<Pair<TopLevelDestinations, ImageVector>>,
    currentDestination: TopLevelDestinations?,
    onItemClick: (String) -> Unit
) {
    ModalDrawerSheet(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.app_name).uppercase(),
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.primary
        )

        mainTabs.forEach { (destination, icon) ->
            UnittoDrawerItem(
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                destination = destination,
                icon = icon,
                selected = destination == currentDestination,
                onClick = onItemClick
            )
        }

        Divider(Modifier.padding(28.dp, 16.dp))

        additionalTabs.forEach { (destination, icon) ->
            UnittoDrawerItem(
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                destination = destination,
                icon = icon,
                selected = destination == currentDestination,
                onClick = onItemClick
            )
        }
    }
}

@Preview
@Composable
private fun PreviewUnittoDrawerSheet() {
    UnittoDrawerSheet(
        modifier = Modifier,
        mainTabs = listOf(
            TopLevelDestinations.Calculator to Icons.Default.Calculate,
            TopLevelDestinations.Calculator to Icons.Default.Calculate,
            TopLevelDestinations.Settings to Icons.Default.Calculate
        ),
        additionalTabs = listOf(
            TopLevelDestinations.Calculator to Icons.Default.Calculate,
            TopLevelDestinations.Calculator to Icons.Default.Calculate,
            TopLevelDestinations.Calculator to Icons.Default.Calculate
        ),
        currentDestination = TopLevelDestinations.Settings,
        onItemClick = {}
    )
}
