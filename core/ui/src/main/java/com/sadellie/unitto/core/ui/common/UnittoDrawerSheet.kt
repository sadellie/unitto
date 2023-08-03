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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.TopLevelDestinations
import com.sadellie.unitto.core.ui.model.DrawerItems

@Composable
fun UnittoDrawerSheet(
    modifier: Modifier,
    mainTabs: List<DrawerItems>,
    additionalTabs: List<DrawerItems>,
    currentDestination: String?,
    onItemClick: (TopLevelDestinations) -> Unit
) {
    ModalDrawerSheet(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.app_name).uppercase(),
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium, letterSpacing = 2.sp),
            color = MaterialTheme.colorScheme.primary
        )

        mainTabs.forEach { drawerItem ->
            val selected = drawerItem.destination.start == currentDestination
            UnittoDrawerItem(
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                destination = drawerItem.destination,
                icon = if (selected) drawerItem.selectedIcon else drawerItem.defaultIcon,
                selected = selected,
                onClick = onItemClick
            )
        }

        Divider(Modifier.padding(28.dp, 16.dp))

        additionalTabs.forEach { drawerItem ->
            val selected = drawerItem.destination.start == currentDestination
            UnittoDrawerItem(
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                destination = drawerItem.destination,
                icon = if (selected) drawerItem.selectedIcon else drawerItem.defaultIcon,
                selected = selected,
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
            DrawerItems.Calculator,
            DrawerItems.Calculator,
            DrawerItems.Calculator,
        ),
        additionalTabs = listOf(
            DrawerItems.Calculator,
            DrawerItems.Calculator,
            DrawerItems.Calculator,
        ),
        currentDestination = DrawerItems.Calculator.destination.start,
        onItemClick = {}
    )
}
