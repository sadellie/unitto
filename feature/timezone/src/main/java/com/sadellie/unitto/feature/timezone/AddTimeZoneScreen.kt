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

import android.icu.text.LocaleDisplayNames
import android.icu.text.TimeZoneNames
import android.icu.util.TimeZone
import android.icu.util.ULocale
import android.os.Build
import android.text.format.DateFormat.is24HourFormat
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.LocalLocale
import com.sadellie.unitto.core.ui.common.SearchPlaceholder
import com.sadellie.unitto.core.ui.common.UnittoEmptyScreen
import com.sadellie.unitto.core.ui.common.UnittoListItem
import com.sadellie.unitto.core.ui.common.UnittoSearchBar
import com.sadellie.unitto.core.ui.datetime.formatTime
import com.sadellie.unitto.core.ui.openLink
import com.sadellie.unitto.data.common.displayName
import com.sadellie.unitto.data.common.offset
import com.sadellie.unitto.data.common.regionName
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
    val mContext = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val locale = LocalLocale.current
    val is24Hour = is24HourFormat(LocalContext.current)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            UnittoSearchBar(
                query = uiState.query,
                onQueryChange = onQueryChange,
                navigateUp = navigateUp,
                scrollBehavior = scrollBehavior,
            )
        },
    ) { paddingValues ->
        Crossfade(
            modifier = Modifier.padding(paddingValues),
            targetState = uiState.list.isEmpty() and uiState.query.text.isNotEmpty(),
            label = "Placeholder"
        ) { empty ->
            if (empty) {
                SearchPlaceholder(
                    onButtonClick = { openLink(mContext, "https://sadellie.github.io/unitto/faq#add-time-zones") },
                    supportText = stringResource(R.string.time_zone_no_results_support),
                    buttonLabel = stringResource(R.string.time_zone_no_results_button),
                )
            } else {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(uiState.list, { it.timeZone.id }) {
                        UnittoListItem(
                            modifier = Modifier
                                .animateItemPlacement()
                                .clickable {
                                    addToFavorites(it.timeZone)
                                    navigateUp()
                                },
                            headlineContent = { Text(it.name) },
                            supportingContent = { Text(it.region) },
                            trailingContent = {
                                Text(
                                    text = it.timeZone
                                        .offset(userTime)
                                        .formatTime(locale, is24Hour),
                                    style = MaterialTheme.typography.headlineSmall
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
    val locale = ULocale.getDefault()
    val timeZoneNames = remember(locale) { TimeZoneNames.getInstance(locale) }
    val localeDisplayNames = remember(locale) { LocaleDisplayNames.getInstance(locale) }

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
                    region = zone.regionName(
                        timeZoneNames = timeZoneNames,
                        localeDisplayNames = localeDisplayNames
                    ),
                    name = zone.displayName(locale),
                    rank = 0
                )
            }
        ),
        navigateUp = {},
        onQueryChange = {},
        addToFavorites = {},
        userTime = ZonedDateTime.now()
    )
}
