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

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.TopLevelDestinations
import com.sadellie.unitto.core.ui.model.DrawerItems
import kotlinx.coroutines.delay

@Composable
fun UnittoDrawerSheet(
    modifier: Modifier,
    tabs: List<DrawerItems>,
    currentDestination: String?,
    onItemClick: (TopLevelDestinations) -> Unit,
) {
    var showHello by remember { mutableStateOf(false) }
    val interactionSource = remember {
        MutableInteractionSource()
    }

    LaunchedEffect(showHello) {
        if (showHello) {
            delay(2000)
            showHello = false
        }
    }

    ModalDrawerSheet(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        AnimatedContent(
            targetState = showHello,
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { showHello = true }
            )
        ) { hello ->
            Text(
                text = if (hello) stringResource(R.string.hello_label) else stringResource(R.string.app_name),
                modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        tabs.forEach { drawerItem ->
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
        tabs = listOf(
            DrawerItems.Calculator,
            DrawerItems.Calculator,
            DrawerItems.Calculator,
        ),
        currentDestination = DrawerItems.Calculator.destination.start,
        onItemClick = {}
    )
}
