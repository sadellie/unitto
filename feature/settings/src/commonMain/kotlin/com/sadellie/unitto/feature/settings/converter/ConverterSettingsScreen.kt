/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.collectAsStateWithLifecycleKMP
import com.sadellie.unitto.core.datastore.ConverterPreferences
import com.sadellie.unitto.core.designsystem.icons.symbols.DrawAbstract
import com.sadellie.unitto.core.designsystem.icons.symbols.Rule
import com.sadellie.unitto.core.designsystem.icons.symbols.Sort
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.icons.symbols.Timer
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.ListItemExpressiveDefaults
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.converter_title
import unitto.core.common.generated.resources.settings_format_time
import unitto.core.common.generated.resources.settings_format_time_support
import unitto.core.common.generated.resources.settings_show_unit_group_icons
import unitto.core.common.generated.resources.settings_show_unit_group_icons_support
import unitto.core.common.generated.resources.settings_unit_groups_support
import unitto.core.common.generated.resources.settings_unit_groups_title
import unitto.core.common.generated.resources.settings_units_sorting
import unitto.core.common.generated.resources.settings_units_sorting_support

@Composable
internal fun ConverterSettingsRoute(
  navigateUpAction: () -> Unit,
  navigateToUnitsGroup: () -> Unit,
) {
  val viewModel: ConverterSettingsViewModel = koinViewModel()
  when (val prefs = viewModel.prefs.collectAsStateWithLifecycleKMP().value) {
    null -> EmptyScreen()
    else -> {
      ConverterSettingsScreen(
        prefs = prefs,
        navigateUpAction = navigateUpAction,
        navigateToUnitsGroup = navigateToUnitsGroup,
        updateUnitConverterFormatTime = viewModel::updateUnitConverterFormatTime,
        updateUnitConverterSorting = viewModel::updateUnitConverterSorting,
        updateUnitConverterShowIcons = viewModel::updateUnitConverterShowIcons,
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
  updateUnitConverterShowIcons: (Boolean) -> Unit,
) {
  ScaffoldWithLargeTopBar(
    title = stringResource(Res.string.converter_title),
    navigationIcon = { NavigateUpButton(navigateUpAction) },
  ) { padding ->
    Column(
      modifier =
        Modifier.padding(padding)
          .padding(start = Sizes.large, end = Sizes.large, bottom = Sizes.large),
      verticalArrangement = ListItemExpressiveDefaults.ListArrangement,
    ) {
      ListItemExpressive(
        icon = Symbols.Rule,
        headlineText = stringResource(Res.string.settings_unit_groups_title),
        supportingText = stringResource(Res.string.settings_unit_groups_support),
        onClick = { navigateToUnitsGroup() },
        shape = ListItemExpressiveDefaults.firstShape,
      )
      ListItemExpressive(
        shape = ListItemExpressiveDefaults.middleShape,
        icon = Symbols.Sort,
        headlineText = stringResource(Res.string.settings_units_sorting),
        supportingText = stringResource(Res.string.settings_units_sorting_support),
        secondaryContent = {
          UnitListSortingSetting(
            modifier = Modifier.fillMaxWidth(),
            onUpdate = updateUnitConverterSorting,
            currentSorting = prefs.sorting,
          )
        },
      )
      ListItemExpressive(
        icon = Symbols.DrawAbstract,
        headlineText = stringResource(Res.string.settings_show_unit_group_icons),
        supportingText = stringResource(Res.string.settings_show_unit_group_icons_support),
        switchState = prefs.showIcons,
        onSwitchChange = updateUnitConverterShowIcons,
        shape = ListItemExpressiveDefaults.middleShape,
      )
      ListItemExpressive(
        icon = Symbols.Timer,
        headlineText = stringResource(Res.string.settings_format_time),
        supportingText = stringResource(Res.string.settings_format_time_support),
        switchState = prefs.formatTime,
        onSwitchChange = updateUnitConverterFormatTime,
        shape = ListItemExpressiveDefaults.lastShape,
      )
    }
  }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun UnitListSortingSetting(
  modifier: Modifier,
  onUpdate: (UnitsListSorting) -> Unit,
  currentSorting: UnitsListSorting,
) {
  val sortings = remember { UnitsListSorting.entries }
  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()
  LaunchedEffect(Unit) {
    coroutineScope.launch {
      val index = sortings.indexOf(currentSorting)
      if (index >= 0) listState.scrollToItem(index)
    }
  }
  LazyRow(
    modifier = modifier,
    state = listState,
    horizontalArrangement = Arrangement.spacedBy(Sizes.small),
  ) {
    items(sortings) { sorting ->
      ToggleButton(
        checked = currentSorting == sorting,
        onCheckedChange = { onUpdate(sorting) },
        shapes = ToggleButtonDefaults.shapes(),
      ) {
        Text(stringResource(sorting.res))
      }
    }
  }
}

@Preview
@Composable
private fun PreviewConverterSettingsScreen() {
  var prefs by remember {
    mutableStateOf(
      ConverterPreferences(
        formatterSymbols = FormatterSymbols(Token.Space, Token.Period, false),
        middleZero = false,
        acButton = true,
        precision = 3,
        outputFormat = OutputFormat.PLAIN,
        formatTime = false,
        sorting = UnitsListSorting.USAGE,
        shownUnitGroups = UnitGroup.entries,
        favoritesOnly = false,
        latestLeftSideUnit = "kilometer",
        latestRightSideUnit = "mile",
        customApiUrl = "",
        showIcons = true,
      )
    )
  }
  ConverterSettingsScreen(
    prefs = prefs,
    navigateUpAction = {},
    navigateToUnitsGroup = {},
    updateUnitConverterFormatTime = { prefs = prefs.copy(formatTime = it) },
    updateUnitConverterSorting = { prefs = prefs.copy(sorting = it) },
    updateUnitConverterShowIcons = { prefs = prefs.copy(showIcons = it) },
  )
}
