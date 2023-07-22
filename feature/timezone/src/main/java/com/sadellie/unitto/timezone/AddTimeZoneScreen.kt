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

package com.sadellie.unitto.timezone

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.data.model.UnittoTimeZone
import com.sadellie.unitto.timezone.components.SelectableTimeZone
import com.sadellie.unitto.timezone.components.UnittoSearchBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.time.ZonedDateTime

@Composable
internal fun AddTimeZoneRoute(
    viewModel: AddTimeZoneViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    userTime: ZonedDateTime? = null
) {
    val uiState = viewModel.addTimeZoneUIState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (userTime == null) {
            while (isActive) {
                viewModel.setTime(ZonedDateTime.now())
                delay(1000)
            }
        } else {
            viewModel.setTime(userTime)
        }
    }

    AddTimeZoneScreen(
        uiState = uiState.value,
        navigateUp = navigateUp,
        onQueryChange = viewModel::onQueryChange,
        addToFavorites = viewModel::addToFavorites,
    )
}

@Composable
fun AddTimeZoneScreen(
    uiState: AddTimeZoneUIState,
    navigateUp: () -> Unit,
    onQueryChange: (String) -> Unit,
    addToFavorites: (UnittoTimeZone) -> Unit,
) {
    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        rememberTopAppBarState()
    )
    val elevatedColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
    val needToTint by remember {
        derivedStateOf { scrollBehavior.state.overlappedFraction > 0.01f }
    }

    val searchBarBackground = animateColorAsState(
        targetValue = if (needToTint) elevatedColor else MaterialTheme.colorScheme.surface,
        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing),
        label = "Search bar background"
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            UnittoSearchBar(
                modifier = Modifier,
                query = uiState.query,
                onQueryChange = onQueryChange,
                navigateUp = navigateUp,
                title = stringResource(R.string.add_time_zone_title),
                placeholder = stringResource(R.string.search_text_field_placeholder),
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = searchBarBackground.value
                )
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            state = listState
        ) {
            items(uiState.list) {
                SelectableTimeZone(
                    timeZone = it,
                    modifier = Modifier
                        .clickable { addToFavorites(it); navigateUp() }
                        .fillMaxWidth(),
                    currentTime = uiState.userTime
                )
                Divider()
            }
        }
    }
}

@Preview
@Composable
fun PreviewAddTimeZoneScreen() {
    AddTimeZoneScreen(
        navigateUp = {},
        uiState = AddTimeZoneUIState(
            list = List(50) {
                UnittoTimeZone(
                    id = "timezone $it",
                    nameRes = "Time zone $it",
                )
            }
        ),
        onQueryChange = {},
        addToFavorites = {},
    )
}
