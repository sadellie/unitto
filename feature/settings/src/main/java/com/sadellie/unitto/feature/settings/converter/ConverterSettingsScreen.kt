/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Rule
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.FormatterSymbols
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.EmptyScreen
import com.sadellie.unitto.core.ui.common.ListItem
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.ScaffoldWithLargeTopBar
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.UnitsListSorting
import com.sadellie.unitto.data.model.userprefs.ConverterPreferences
import com.sadellie.unitto.data.userprefs.ConverterPreferencesImpl
import com.sadellie.unitto.feature.settings.components.AlertDialogWithList

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
                updateUnitConverterSorting = viewModel::updateUnitConverterSorting
            )
        }
    }
}

@Composable
private fun ConverterSettingsScreen(
    prefs: ConverterPreferences,
    navigateUpAction: () -> Unit,
    navigateToUnitsGroup: () -> Unit,
    updateUnitConverterFormatTime: (Boolean) -> Unit,
    updateUnitConverterSorting: (UnitsListSorting) -> Unit,
) {
    var showDialog: Boolean by rememberSaveable { mutableStateOf(false) }

    ScaffoldWithLargeTopBar(
        title = stringResource(R.string.unit_converter_title),
        navigationIcon = { NavigateUpButton(navigateUpAction) }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            item("unit group") {
                ListItem(
                    icon = Icons.AutoMirrored.Filled.Rule,
                    headlineText = stringResource(R.string.settings_unit_groups_title),
                    supportingText = stringResource(R.string.settings_unit_groups_support),
                    modifier = Modifier.clickable { navigateToUnitsGroup() }
                )
            }

            item("units sorting") {
                ListItem(
                    icon = Icons.AutoMirrored.Filled.Sort,
                    headlineText = stringResource(R.string.settings_units_sorting),
                    supportingText = stringResource(R.string.settings_units_sorting_support),
                    modifier = Modifier.clickable { showDialog = true }
                )
            }

            item("format time") {
                ListItem(
                    icon = Icons.Default.Timer,
                    headlineText = stringResource(R.string.settings_format_time),
                    supportingText = stringResource(R.string.settings_format_time_support),
                    switchState = prefs.unitConverterFormatTime,
                    onSwitchChange = updateUnitConverterFormatTime
                )
            }
        }
    }

    if (showDialog) {
        AlertDialogWithList(
            title = stringResource(R.string.settings_units_sorting),
            listItems = mapOf(
                UnitsListSorting.USAGE to R.string.settings_sort_by_usage,
                UnitsListSorting.ALPHABETICAL to R.string.settings_sort_by_alphabetical,
                UnitsListSorting.SCALE_DESC to R.string.settings_sort_by_scale_desc,
                UnitsListSorting.SCALE_ASC to R.string.settings_sort_by_scale_asc,
            ),
            selectedItemIndex = prefs.unitConverterSorting,
            selectAction = updateUnitConverterSorting,
            dismissAction = { showDialog = false }
        )
    }
}

@Preview
@Composable
private fun PreviewConverterSettingsScreen() {
    ConverterSettingsScreen(
        prefs = ConverterPreferencesImpl(
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
        updateUnitConverterSorting = {}
    )
}
