/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.data.converter.UnitSearchResultItem
import com.sadellie.unitto.core.data.converter.UnitStats
import com.sadellie.unitto.core.designsystem.icons.symbols.Favorite
import com.sadellie.unitto.core.designsystem.icons.symbols.FavoriteFill
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import com.sadellie.unitto.core.ui.Header
import com.sadellie.unitto.core.ui.SearchPlaceholder
import java.math.BigDecimal

@Composable
internal fun UnitsList(
  modifier: Modifier,
  searchResult: Map<UnitGroup, List<UnitSearchResultItem>>,
  navigateToUnitGroups: () -> Unit,
  selectedUnitId: String,
  supportLabel: (UnitSearchResultItem) -> String,
  onClick: (UnitSearchResultItem) -> Unit,
  favoriteUnit: (UnitSearchResultItem) -> Unit,
) {
  val isSearchEmpty = remember(searchResult) { searchResult.isEmpty() }
  Crossfade(
    modifier = modifier,
    targetState = isSearchEmpty,
    label = "Units list",
    animationSpec = tween(UNIT_LIST_CROSS_FADE_DURATION_MS),
  ) { isEmpty ->
    if (isEmpty) {
      SearchPlaceholder(
        onButtonClick = navigateToUnitGroups,
        supportText = stringResource(R.string.converter_no_results_support),
        buttonLabel = stringResource(R.string.common_open_settings),
      )
    } else {
      LazyColumn(modifier = Modifier.fillMaxSize()) {
        searchResult.forEach { (group, units) ->
          item(group.name) { UnitGroupHeader(Modifier.animateItem(), group) }

          items(units, { it.basicUnit.id }) {
            BasicUnitListItem(
              modifier = Modifier.animateItem(),
              name = stringResource(it.basicUnit.displayName),
              supportLabel = supportLabel(it),
              isFavorite = it.stats.isFavorite,
              isSelected = it.basicUnit.id == selectedUnitId,
              onClick = { onClick(it) },
              favoriteUnit = { favoriteUnit(it) },
            )
          }
        }
      }
    }
  }
}

@Composable
private fun UnitGroupHeader(modifier: Modifier, unitGroup: UnitGroup) {
  Header(
    modifier = modifier,
    text = stringResource(unitGroup.res),
    paddingValues = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 12.dp),
  )
}

@Composable
private fun BasicUnitListItem(
  modifier: Modifier,
  name: String,
  supportLabel: String,
  isFavorite: Boolean,
  isSelected: Boolean,
  onClick: () -> Unit,
  favoriteUnit: () -> Unit,
) {
  val itemColor =
    if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
    else MaterialTheme.colorScheme.onSurfaceVariant

  Box(
    modifier =
      modifier
        .background(MaterialTheme.colorScheme.surface)
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = ripple(),
          onClick = onClick,
        )
        .padding(horizontal = 12.dp)
  ) {
    Row(
      modifier =
        Modifier.background(
            if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
            RoundedCornerShape(24.dp),
          )
          .padding(paddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp))
          .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      Column(Modifier.weight(1f)) {
        Text(
          modifier = Modifier.fillMaxWidth(),
          text = name,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          color = itemColor,
        )
        Text(
          modifier = Modifier.fillMaxWidth(),
          text = supportLabel,
          style = MaterialTheme.typography.bodySmall,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          color = itemColor,
        )
      }
      AnimatedContent(
        modifier =
          Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(false),
            onClick = favoriteUnit,
          ),
        targetState = isFavorite,
        transitionSpec = { (scaleIn() togetherWith scaleOut()).using(SizeTransform(clip = false)) },
        label = "Favorite unit",
      ) {
        Icon(
          imageVector = if (it) Symbols.FavoriteFill else Symbols.Favorite,
          contentDescription = stringResource(R.string.converter_favorite_button_description),
          tint = itemColor,
        )
      }
    }
  }
}

private const val UNIT_LIST_CROSS_FADE_DURATION_MS = 150

@Preview
@Composable
private fun PreviewUnitsList() {
  val resources = LocalContext.current.resources
  val units: Map<UnitGroup, List<UnitSearchResultItem>> =
    mapOf(
      UnitGroup.LENGTH to
        listOf(
            NormalUnit(
              UnitID.meter,
              BigDecimal("1000000000000000000"),
              UnitGroup.LENGTH,
              R.string.unit_meter,
              R.string.unit_meter_short,
            ),
            NormalUnit(
              UnitID.kilometer,
              BigDecimal("1000000000000000000000"),
              UnitGroup.LENGTH,
              R.string.unit_kilometer,
              R.string.unit_kilometer_short,
            ),
            NormalUnit(
              UnitID.nautical_mile,
              BigDecimal("1852000000000000000000"),
              UnitGroup.LENGTH,
              R.string.unit_nautical_mile,
              R.string.unit_nautical_mile_short,
            ),
            NormalUnit(
              UnitID.inch,
              BigDecimal("25400000000000000"),
              UnitGroup.LENGTH,
              R.string.unit_inch,
              R.string.unit_inch_short,
            ),
            NormalUnit(
              UnitID.foot,
              BigDecimal("304800000000000000"),
              UnitGroup.LENGTH,
              R.string.unit_foot,
              R.string.unit_foot_short,
            ),
            NormalUnit(
              UnitID.yard,
              BigDecimal("914400000000000000"),
              UnitGroup.LENGTH,
              R.string.unit_yard,
              R.string.unit_yard_short,
            ),
            NormalUnit(
              UnitID.mile,
              BigDecimal("1609344000000000000000"),
              UnitGroup.LENGTH,
              R.string.unit_mile,
              R.string.unit_mile_short,
            ),
          )
          .map { UnitSearchResultItem(it, UnitStats(it.id), null) }
    )

  UnitsList(
    modifier = Modifier.fillMaxSize(),
    searchResult = units,
    navigateToUnitGroups = {},
    selectedUnitId = UnitID.mile,
    supportLabel = { resources.getString(it.basicUnit.shortName) },
    onClick = {},
    favoriteUnit = {},
  )
}