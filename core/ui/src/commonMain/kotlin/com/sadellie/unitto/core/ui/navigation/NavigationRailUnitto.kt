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

package com.sadellie.unitto.core.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.navigation.CalculatorStartRoute
import com.sadellie.unitto.core.navigation.DrawerItem
import com.sadellie.unitto.core.navigation.TopLevelRoute
import com.sadellie.unitto.core.navigation.additionalDrawerItems
import com.sadellie.unitto.core.navigation.mainDrawerItems
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun NavigationRailUnitto(
  modifier: Modifier,
  mainTabs: List<DrawerItem>,
  additionalTabs: List<DrawerItem>,
  currentDestination: TopLevelRoute?,
  onItemClick: (DrawerItem) -> Unit,
) {
  NavigationRail(
    modifier = modifier,
    containerColor = MaterialTheme.colorScheme.surfaceContainer,
    contentColor = MaterialTheme.colorScheme.onSurface,
  ) {
    mainTabs.forEach { tab ->
      val isSelected = tab.topLevelRoute == currentDestination
      NavigationRailItem(
        selected = isSelected,
        onClick = { onItemClick(tab) },
        icon = {
          Icon(if (isSelected) tab.selectedIcon else tab.defaultIcon, stringResource(tab.name))
        },
        label = {
          Text(stringResource(tab.name))
        },
      )
    }
    additionalTabs.forEach { tab ->
      val isSelected = tab.topLevelRoute == currentDestination
      NavigationRailItem(
        selected = isSelected,
        onClick = { onItemClick(tab) },
        icon = {
          Icon(if (isSelected) tab.selectedIcon else tab.defaultIcon, stringResource(tab.name))
        },
        label = {
          Text(stringResource(tab.name))
        },
      )
    }
  }
}

@Preview
@Composable
private fun PreviewNavigationRailUnitto() {
  NavigationRailUnitto(
    modifier = Modifier,
    mainTabs = mainDrawerItems,
    additionalTabs = additionalDrawerItems,
    currentDestination = CalculatorStartRoute,
    onItemClick = {},
  )
}
