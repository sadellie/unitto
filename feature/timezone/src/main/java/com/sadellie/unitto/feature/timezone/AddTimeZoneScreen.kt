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

package com.sadellie.unitto.feature.timezone

import android.icu.util.TimeZone
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.UnittoEmptyScreen
import com.sadellie.unitto.core.ui.common.UnittoListItem
import com.sadellie.unitto.core.ui.common.UnittoSearchBar
import com.sadellie.unitto.core.ui.datetime.formatLocal
import com.sadellie.unitto.core.ui.theme.numberHeadlineSmall
import com.sadellie.unitto.data.common.offset
import com.sadellie.unitto.data.common.region
import com.sadellie.unitto.data.model.timezone.SearchResultZone
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.N)
@Composable
internal fun AddTimeZoneRoute(
    viewModel: AddTimeZoneViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    userTime: ZonedDateTime,
) {
    when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
        AddTimeZoneUIState.Loading -> UnittoEmptyScreen()
        is AddTimeZoneUIState.Ready -> AddTimeZoneScreen(
            uiState = uiState,
            navigateUp = navigateUp,
            onQueryChange = viewModel::onQueryChange,
            addToFavorites = viewModel::addToFavorites,
            userTime = userTime
        )
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AddTimeZoneScreen(
    uiState: AddTimeZoneUIState.Ready,
    navigateUp: () -> Unit,
    onQueryChange: (TextFieldValue) -> Unit,
    addToFavorites: (TimeZone) -> Unit,
    userTime: ZonedDateTime,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            UnittoSearchBar(
                query = uiState.query,
                onQueryChange = onQueryChange,
                navigateUp = navigateUp,
                title = stringResource(R.string.time_zone_add_title),
                scrollBehavior = scrollBehavior
            )
        },
    ) { paddingValues ->
        Crossfade(targetState = uiState.list.isEmpty()) { empty ->
            if (empty) {
                UnittoEmptyScreen()
            } else {
                LazyColumn(contentPadding = paddingValues) {
                    items(uiState.list, { it.timeZone.id }) {
                        UnittoListItem(
                            modifier = Modifier
                                .animateItemPlacement()
                                .clickable {
                                    addToFavorites(it.timeZone)
                                    navigateUp()
                                },
                            headlineContent = { Text(it.timeZone.displayName) },
                            supportingContent = { Text(it.formattedLabel) },
                            trailingContent = {
                                Text(
                                    text = it.timeZone.offset(userTime).formatLocal(),
                                    style = MaterialTheme.typography.numberHeadlineSmall
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview
@Composable
fun PreviewAddTimeZoneScreen() {
    AddTimeZoneScreen(
        uiState = AddTimeZoneUIState.Ready(
            query = TextFieldValue(),
            list = listOf(
                "UTC",
                "Africa/Addis_Ababa",
                "ACT"
            ).map {
                val zone = TimeZone.getTimeZone(it)
                SearchResultZone(
                    timeZone = zone,
                    formattedLabel = zone.region
                )
            }
        ),
        navigateUp = {},
        onQueryChange = {},
        addToFavorites = {},
        userTime = ZonedDateTime.now()
    )
}
