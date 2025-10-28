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

package com.sadellie.unitto.feature.settings.converter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.datastore.ConverterPreferences
import com.sadellie.unitto.core.designsystem.icons.symbols.Rule
import com.sadellie.unitto.core.designsystem.icons.symbols.Sort
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.icons.symbols.Timer
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListArrangement
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import com.sadellie.unitto.core.ui.firstShape
import com.sadellie.unitto.core.ui.lastShape
import com.sadellie.unitto.core.ui.middleShape

@Composable
internal fun ConverterSettingsRoute(
  viewModel: ConverterSettingsViewModel = hiltViewModel(),
  navigateUpAction: () -> Unit,
  navigateToUnitsGroup: () -> Unit,
) {
  when (val prefs = viewModel.prefs.collectAsStateWithLifecycle().value) {
    null -> EmptyScreen()
    else -> {
      ConverterSettingsScreen(
        prefs = prefs,
        navigateUpAction = navigateUpAction,
        navigateToUnitsGroup = navigateToUnitsGroup,
        updateUnitConverterFormatTime = viewModel::updateUnitConverterFormatTime,
        updateUnitConverterSorting = viewModel::updateUnitConverterSorting,
      )
    }
  }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ConverterSettingsScreen(
  prefs: ConverterPreferences,
  navigateUpAction: () -> Unit,
  navigateToUnitsGroup: () -> Unit,
  updateUnitConverterFormatTime: (Boolean) -> Unit,
  updateUnitConverterSorting: (UnitsListSorting) -> Unit,
) {
  ScaffoldWithLargeTopBar(
    title = stringResource(R.string.converter_title),
    navigationIcon = { NavigateUpButton(navigateUpAction) },
  ) { padding ->
    Column(
      modifier =
        Modifier.padding(padding)
          .padding(start = Sizes.large, end = Sizes.large, bottom = Sizes.large),
      verticalArrangement = ListArrangement,
    ) {
      ListItemExpressive(
        icon = Symbols.Rule,
        headlineText = stringResource(R.string.settings_unit_groups_title),
        supportingText = stringResource(R.string.settings_unit_groups_support),
        modifier = Modifier.clickable { navigateToUnitsGroup() },
        shape = ListItemDefaults.firstShape,
      )
      ListItemExpressive(
        shape = ListItemDefaults.middleShape,
        icon = Symbols.Sort,
        headlineText = stringResource(R.string.settings_units_sorting),
        supportingText = stringResource(R.string.settings_units_sorting_support),
        secondaryContent = {
          Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(Sizes.small),
          ) {
            ToggleButton(
              checked = prefs.unitConverterSorting == UnitsListSorting.USAGE,
              onCheckedChange = { updateUnitConverterSorting(UnitsListSorting.USAGE) },
            ) {
              Text(stringResource(R.string.settings_sort_by_usage))
            }
            ToggleButton(
              checked = prefs.unitConverterSorting == UnitsListSorting.ALPHABETICAL,
              onCheckedChange = { updateUnitConverterSorting(UnitsListSorting.ALPHABETICAL) },
            ) {
              Text(stringResource(R.string.settings_sort_by_alphabetical))
            }
            ToggleButton(
              checked = prefs.unitConverterSorting == UnitsListSorting.SCALE_DESC,
              onCheckedChange = { updateUnitConverterSorting(UnitsListSorting.SCALE_DESC) },
            ) {
              Text(stringResource(R.string.settings_sort_by_scale_desc))
            }
            ToggleButton(
              checked = prefs.unitConverterSorting == UnitsListSorting.SCALE_ASC,
              onCheckedChange = { updateUnitConverterSorting(UnitsListSorting.SCALE_ASC) },
            ) {
              Text(stringResource(R.string.settings_sort_by_scale_asc))
            }
          }
        },
      )
      ListItemExpressive(
        icon = Symbols.Timer,
        headlineText = stringResource(R.string.settings_format_time),
        supportingText = stringResource(R.string.settings_format_time_support),
        switchState = prefs.unitConverterFormatTime,
        onSwitchChange = updateUnitConverterFormatTime,
        shape = ListItemDefaults.lastShape,
      )
    }
  }
}

@Preview
@Composable
private fun PreviewConverterSettingsScreen() {
  ConverterSettingsScreen(
    prefs =
      ConverterPreferences(
        formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
        middleZero = false,
        precision = 3,
        outputFormat = OutputFormat.PLAIN,
        unitConverterFormatTime = false,
        unitConverterSorting = UnitsListSorting.USAGE,
        shownUnitGroups = UnitGroup.entries,
        unitConverterFavoritesOnly = false,
        enableToolsExperiment = false,
        latestLeftSideUnit = "kilometer",
        latestRightSideUnit = "mile",
        acButton = true,
      ),
    navigateUpAction = {},
    navigateToUnitsGroup = {},
    updateUnitConverterFormatTime = {},
    updateUnitConverterSorting = {},
  )
}
