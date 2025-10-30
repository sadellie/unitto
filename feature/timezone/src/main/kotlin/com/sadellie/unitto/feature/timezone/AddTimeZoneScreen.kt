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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.common.displayName
import com.sadellie.unitto.core.common.offset
import com.sadellie.unitto.core.common.openLink
import com.sadellie.unitto.core.common.regionName
import com.sadellie.unitto.core.designsystem.LocalLocale
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.model.timezone.SearchResultZone
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.ListItemExpressiveDefaults
import com.sadellie.unitto.core.ui.SearchBar
import com.sadellie.unitto.core.ui.SearchPlaceholder
import com.sadellie.unitto.core.ui.datetime.formatTime
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.N)
@Composable
internal fun AddTimeZoneRoute(
  viewModel: AddTimeZoneViewModel = hiltViewModel(),
  navigateUp: () -> Unit,
  userTime: ZonedDateTime,
) {
  LaunchedEffect(Unit) { viewModel.observeSearchFilters() }

  when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
    AddTimeZoneUIState.Loading -> EmptyScreen()
    is AddTimeZoneUIState.Ready ->
      AddTimeZoneScreen(
        uiState = uiState,
        navigateUp = navigateUp,
        addToFavorites = viewModel::addToFavorites,
        userTime = userTime,
      )
  }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AddTimeZoneScreen(
  uiState: AddTimeZoneUIState.Ready,
  navigateUp: () -> Unit,
  addToFavorites: (TimeZone) -> Unit,
  userTime: ZonedDateTime,
) {
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    containerColor = MaterialTheme.colorScheme.surfaceContainer,
    topBar = {
      SearchBar(state = uiState.query, navigateUp = navigateUp, scrollBehavior = scrollBehavior)
    },
  ) { paddingValues ->
    val mContext = LocalContext.current
    Crossfade(
      modifier = Modifier.padding(paddingValues),
      targetState = uiState.searchResults.isEmpty() and uiState.query.text.isNotEmpty(),
      label = "Placeholder",
    ) { empty ->
      if (empty) {
        SearchPlaceholder(
          onButtonClick = {
            openLink(mContext, "https://sadellie.github.io/unitto/help#add-time-zones")
          },
          supportText = stringResource(R.string.time_zone_no_results_support),
          buttonLabel = stringResource(R.string.common_read_article),
        )
      } else {
        val locale = LocalLocale.current
        val is24Hour = is24HourFormat(mContext)

        LazyColumn(
          modifier = Modifier.fillMaxSize(),
          contentPadding =
            PaddingValues(start = Sizes.large, end = Sizes.large, bottom = Sizes.large),
          verticalArrangement = ListItemExpressiveDefaults.ListArrangement,
        ) {
          itemsIndexed(uiState.searchResults, { index, item -> item.timeZone.id }) { index, item ->
            ListItemExpressive(
              shape = ListItemExpressiveDefaults.listedShaped(index, uiState.searchResults.size),
              modifier =
                Modifier.animateItem().clickable {
                  addToFavorites(item.timeZone)
                  navigateUp()
                },
              headlineContent = { Text(item.name) },
              supportingContent = { Text(item.region) },
              trailingContent = {
                Text(
                  text = item.timeZone.offset(userTime).formatTime(locale, is24Hour),
                  style = MaterialTheme.typography.headlineSmall,
                )
              },
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
    uiState =
      AddTimeZoneUIState.Ready(
        query = TextFieldState(),
        searchResults =
          listOf("UTC", "Africa/Addis_Ababa", "ACT").map {
            val zone = TimeZone.getTimeZone(it)
            SearchResultZone(
              timeZone = zone,
              region =
                zone.regionName(
                  timeZoneNames = timeZoneNames,
                  localeDisplayNames = localeDisplayNames,
                ),
              name = zone.displayName(locale),
              rank = 0,
            )
          },
      ),
    navigateUp = {},
    addToFavorites = {},
    userTime = ZonedDateTime.now(),
  )
}
