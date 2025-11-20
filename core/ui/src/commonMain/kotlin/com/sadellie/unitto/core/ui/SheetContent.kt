/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

package com.sadellie.unitto.core.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.designsystem.ExpressivePreview
import com.sadellie.unitto.core.navigation.CalculatorStartRoute
import com.sadellie.unitto.core.navigation.DrawerItem
import com.sadellie.unitto.core.navigation.TopLevelRoute
import com.sadellie.unitto.core.navigation.additionalDrawerItems
import com.sadellie.unitto.core.navigation.mainDrawerItems
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.app_name
import unitto.core.common.generated.resources.common_hello

@Suppress("UnusedReceiverParameter")
@Composable
internal fun ColumnScope.SheetContent(
  mainTabs: List<DrawerItem>,
  additionalTabs: List<DrawerItem>,
  currentDestination: TopLevelRoute?,
  onItemClick: (DrawerItem) -> Unit,
) {
  var showHello by remember { mutableStateOf(false) }
  val interactionSource = remember { MutableInteractionSource() }
  LaunchedEffect(showHello) {
    if (showHello) {
      delay(HELLO_DURATION_MS)
      showHello = false
    }
  }

  AnimatedContent(
    targetState = showHello,
    label = "Hello reveal",
    modifier =
      Modifier.clickable(
        interactionSource = interactionSource,
        indication = null,
        onClick = { showHello = true },
      ),
  ) { hello ->
    Text(
      text =
        if (hello) stringResource(Res.string.common_hello) else stringResource(Res.string.app_name),
      modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp),
      style = MaterialTheme.typography.titleLarge,
      color = MaterialTheme.colorScheme.primary,
    )
  }

  val navigationDrawerItemColors =
    NavigationDrawerItemDefaults.colors(
      unselectedIconColor = MaterialTheme.colorScheme.onSurface,
      unselectedTextColor = MaterialTheme.colorScheme.onSurface,
    )
  mainTabs.forEach { drawerItem ->
    val selected = drawerItem.topLevelRoute == currentDestination
    DrawerItem(
      modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
      destination = drawerItem,
      icon = if (selected) drawerItem.selectedIcon else drawerItem.defaultIcon,
      selected = selected,
      onClick = { onItemClick(drawerItem) },
      colors = navigationDrawerItemColors,
    )
  }

  HorizontalDivider(Modifier.padding(horizontal = 12.dp, vertical = 8.dp))

  additionalTabs.forEach { drawerItem ->
    val selected = drawerItem.topLevelRoute == currentDestination
    DrawerItem(
      modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
      destination = drawerItem,
      icon = if (selected) drawerItem.selectedIcon else drawerItem.defaultIcon,
      selected = selected,
      onClick = { onItemClick(drawerItem) },
      colors = navigationDrawerItemColors,
    )
  }
}

@Composable
private fun DrawerItem(
  modifier: Modifier = Modifier,
  destination: DrawerItem,
  icon: ImageVector,
  selected: Boolean,
  onClick: () -> Unit,
  colors: NavigationDrawerItemColors,
) {
  NavigationDrawerItem(
    modifier = modifier,
    label = { Text(stringResource(destination.name)) },
    icon = { Icon(icon, stringResource(destination.name)) },
    selected = selected,
    onClick = onClick,
    colors = colors,
  )
}

private const val HELLO_DURATION_MS = 2_000L

@Preview
@Composable
private fun PreviewDrawerSheet() = ExpressivePreview {
  Column(Modifier.background(MaterialTheme.colorScheme.surfaceContainer)) {
    SheetContent(
      mainTabs = mainDrawerItems,
      additionalTabs = additionalDrawerItems,
      currentDestination = CalculatorStartRoute,
      onItemClick = {},
    )
  }
}
