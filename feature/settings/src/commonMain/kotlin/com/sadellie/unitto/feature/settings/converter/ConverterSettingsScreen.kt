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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.collectAsStateWithLifecycleKMP
import com.sadellie.unitto.core.datastore.ConverterPreferences
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
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.core.ThemingMode
import io.github.sadellie.themmo.rememberThemmoController
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.converter_title
import unitto.core.common.generated.resources.settings_format_time
import unitto.core.common.generated.resources.settings_format_time_support
import unitto.core.common.generated.resources.settings_unit_groups_support
import unitto.core.common.generated.resources.settings_unit_groups_title
import unitto.core.common.generated.resources.settings_units_sorting
import unitto.core.common.generated.resources.settings_units_sorting_support

@Composable
internal fun ConverterSettingsRoute(
  viewModel: ConverterSettingsViewModel = koinViewModel(),
  navigateUpAction: () -> Unit,
  navigateToUnitsGroup: () -> Unit,
) {
  when (val prefs = viewModel.prefs.collectAsStateWithLifecycleKMP().value) {
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
            currentSorting = prefs.unitConverterSorting,
          )
        },
      )
      ListItemExpressive(
        icon = Symbols.Timer,
        headlineText = stringResource(Res.string.settings_format_time),
        supportingText = stringResource(Res.string.settings_format_time_support),
        switchState = prefs.unitConverterFormatTime,
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
      ToggleButton(checked = currentSorting == sorting, onCheckedChange = { onUpdate(sorting) }) {
        Text(stringResource(sorting.res))
      }
    }
  }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
private fun PreviewConverterSettingsScreen() =
  Themmo(
    themmoController =
      rememberThemmoController(amoledThemeEnabled = true, themingMode = ThemingMode.FORCE_DARK)
  ) {
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
