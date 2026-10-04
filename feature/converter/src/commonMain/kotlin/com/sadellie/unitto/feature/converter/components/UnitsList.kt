/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2026 Elshan Agaev
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

package com.sadellie.unitto.feature.converter.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ListItemShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.data.converter.UnitSearchResultItem
import com.sadellie.unitto.core.data.converter.UnitStats
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitID
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import com.sadellie.unitto.core.ui.ListArrangement
import com.sadellie.unitto.core.ui.ListHeader
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.SearchPlaceholder
import com.sadellie.unitto.core.ui.animations.animateItemDefault
import com.sadellie.unitto.core.ui.listedShapes
import com.sadellie.unitto.feature.converter.UnitSearchState
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_open_settings
import unitto.core.common.generated.resources.converter_no_results_support
import unitto.core.common.generated.resources.unit_foot
import unitto.core.common.generated.resources.unit_foot_short
import unitto.core.common.generated.resources.unit_inch
import unitto.core.common.generated.resources.unit_inch_short
import unitto.core.common.generated.resources.unit_kilometer
import unitto.core.common.generated.resources.unit_kilometer_short
import unitto.core.common.generated.resources.unit_meter
import unitto.core.common.generated.resources.unit_meter_short
import unitto.core.common.generated.resources.unit_mile
import unitto.core.common.generated.resources.unit_mile_short
import unitto.core.common.generated.resources.unit_nautical_mile
import unitto.core.common.generated.resources.unit_nautical_mile_short
import unitto.core.common.generated.resources.unit_yard
import unitto.core.common.generated.resources.unit_yard_short

@Composable
internal fun UnitsList(
  modifier: Modifier,
  searchState: UnitSearchState,
  navigateToUnitGroups: () -> Unit,
  selectedUnitId: String,
  supportLabel: @Composable (UnitSearchResultItem) -> String,
  onClick: (UnitSearchResultItem) -> Unit,
  favoriteUnit: (UnitSearchResultItem) -> Unit,
  contentPadding: PaddingValues,
) {
  val showPlaceholder =
    remember(searchState.units.size, searchState.isLoading) {
      searchState.units.isEmpty() && !searchState.isLoading
    }
  val fadeAnimationSpec = MaterialTheme.motionScheme.fastEffectsSpec<Float>()
  Crossfade(
    modifier = modifier.fillMaxSize(),
    targetState = showPlaceholder,
    label = "Units list",
    animationSpec = fadeAnimationSpec,
  ) { placeholder ->
    if (placeholder) {
      SearchPlaceholder(
        modifier = Modifier.padding(contentPadding),
        onButtonClick = navigateToUnitGroups,
        supportText = stringResource(Res.string.converter_no_results_support),
        buttonLabel = stringResource(Res.string.common_open_settings),
      )
      return@Crossfade
    }

    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = ListItemDefaults.ListArrangement,
      contentPadding = contentPadding,
    ) {
      searchState.units.forEach { (group, units) ->
        item(key = group.name, contentType = ContentType.HEADER) {
          ListHeader(
            modifier =
              Modifier.animateItemDefault()
                .padding(
                  start = Sizes.large,
                  end = Sizes.large,
                  top = Sizes.large,
                  bottom = Sizes.small,
                ),
            text = stringResource(group.res),
          )
        }

        itemsIndexed(
          items = units,
          key = { _, item -> item.basicUnit.id },
          contentType = { _, _ -> ContentType.ITEM },
        ) { index, item ->
          UnitListItem(
            modifier =
              Modifier.animateItemDefault().padding(horizontal = Sizes.medium).fillMaxWidth(),
            shapes = ListItemDefaults.listedShapes(index, units.size),
            name = stringResource(item.basicUnit.displayName),
            supportLabel = supportLabel(item),
            isFavorite = item.stats.isFavorite,
            isSelected = item.basicUnit.id == selectedUnitId,
            onClick = { onClick(item) },
            favoriteUnit = { favoriteUnit(item) },
          )
        }
      }
    }
  }
  // todo debounce
  AnimatedVisibility(
    visible = searchState.isLoading,
    modifier = modifier.fillMaxSize().padding(contentPadding),
    enter = fadeIn(fadeAnimationSpec),
    exit = fadeOut(fadeAnimationSpec),
  ) {
    Box(
      modifier =
        Modifier.fillMaxSize()
          .background(MaterialTheme.colorScheme.scrim.copy(alpha = PROGRESS_INDICATOR_SCRIM_ALPHA)),
      contentAlignment = Alignment.Center,
    ) {
      CircularWavyProgressIndicator()
    }
  }
}

@Composable
private fun UnitListItem(
  modifier: Modifier,
  name: String,
  supportLabel: String,
  isFavorite: Boolean,
  isSelected: Boolean,
  shapes: ListItemShapes,
  onClick: () -> Unit,
  favoriteUnit: () -> Unit,
) {
  ListItemExpressive(
    selected = isSelected,
    modifier = modifier,
    onClick = onClick,
    shapes = shapes,
    content = {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = name,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    },
    supportingContent = {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = supportLabel,
        style = MaterialTheme.typography.bodySmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    },
    trailingContent = { FavoritesButton(state = isFavorite, onClick = favoriteUnit) },
  )
}

private enum class ContentType {
  HEADER,
  ITEM,
}

// from m3 drawer
private const val PROGRESS_INDICATOR_SCRIM_ALPHA = 0.32f

@Preview
@Composable
private fun PreviewUnitsList() {
  val searchState = remember {
    UnitSearchState(
      units =
        mapOf(
          UnitGroup.LENGTH to
            listOf(
                NormalUnit(
                  UnitID.meter,
                  KBigDecimal("1000000000000000000"),
                  UnitGroup.LENGTH,
                  Res.string.unit_meter,
                  Res.string.unit_meter_short,
                ),
                NormalUnit(
                  UnitID.kilometer,
                  KBigDecimal("1000000000000000000000"),
                  UnitGroup.LENGTH,
                  Res.string.unit_kilometer,
                  Res.string.unit_kilometer_short,
                ),
                NormalUnit(
                  UnitID.nautical_mile,
                  KBigDecimal("1852000000000000000000"),
                  UnitGroup.LENGTH,
                  Res.string.unit_nautical_mile,
                  Res.string.unit_nautical_mile_short,
                ),
                NormalUnit(
                  UnitID.inch,
                  KBigDecimal("25400000000000000"),
                  UnitGroup.LENGTH,
                  Res.string.unit_inch,
                  Res.string.unit_inch_short,
                ),
                NormalUnit(
                  UnitID.foot,
                  KBigDecimal("304800000000000000"),
                  UnitGroup.LENGTH,
                  Res.string.unit_foot,
                  Res.string.unit_foot_short,
                ),
                NormalUnit(
                  UnitID.yard,
                  KBigDecimal("914400000000000000"),
                  UnitGroup.LENGTH,
                  Res.string.unit_yard,
                  Res.string.unit_yard_short,
                ),
                NormalUnit(
                  UnitID.mile,
                  KBigDecimal("1609344000000000000000"),
                  UnitGroup.LENGTH,
                  Res.string.unit_mile,
                  Res.string.unit_mile_short,
                ),
              )
              .map { UnitSearchResultItem(it, UnitStats(it.id), null) }
        ),
      isLoading = false,
    )
  }

  UnitsList(
    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceContainer),
    searchState = searchState,
    navigateToUnitGroups = {},
    selectedUnitId = UnitID.yard,
    supportLabel = { stringResource(it.basicUnit.shortName) },
    onClick = {},
    favoriteUnit = {},
    contentPadding = PaddingValues(0.dp),
  )
}
