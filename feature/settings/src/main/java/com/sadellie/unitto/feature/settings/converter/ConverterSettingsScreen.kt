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

package com.sadellie.unitto.feature.settings.converter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.UnittoListItem
import com.sadellie.unitto.core.ui.common.UnittoScreenWithLargeTopBar
import com.sadellie.unitto.data.model.UnitsListSorting
import com.sadellie.unitto.feature.settings.components.AlertDialogWithList

@Composable
internal fun ConverterSettingsScreen(
    viewModel: ConverterViewModel = hiltViewModel(),
    navigateUpAction: () -> Unit,
    navigateToUnitsGroup: () -> Unit,
) {
    val prefs = viewModel.prefs.collectAsStateWithLifecycle()
    var showDialog: Boolean by rememberSaveable { mutableStateOf(false) }

    UnittoScreenWithLargeTopBar(
        title = stringResource(R.string.unit_converter),
        navigationIcon = { NavigateUpButton(navigateUpAction) }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            // UNIT GROUPS
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Rule,
                            stringResource(R.string.disable_unit_group_description),
                        )
                    },
                    headlineContent = { Text(stringResource(R.string.unit_groups_setting)) },
                    supportingContent = { Text(stringResource(R.string.unit_groups_support)) },
                    modifier = Modifier.clickable { navigateToUnitsGroup() }
                )
            }

            // UNITS LIST SORTING
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Sort,
                            stringResource(R.string.units_sorting)
                        )
                    },
                    headlineContent = { Text(stringResource(R.string.units_sorting)) },
                    supportingContent = { Text(stringResource(R.string.units_sorting_support)) },
                    modifier = Modifier.clickable { showDialog = true }
                )
            }

            // FORMAT TIME
            item {
                UnittoListItem(
                    label = stringResource(R.string.format_time),
                    leadingContent = {
                        Icon(
                            Icons.Default.Timer,
                            stringResource(R.string.format_time)
                        )
                    },
                    supportContent = stringResource(R.string.format_time_support),
                    switchState = prefs.value.unitConverterFormatTime,
                    onSwitchChange = viewModel::updateUnitConverterFormatTime
                )
            }
        }
    }

    if (showDialog) {
        AlertDialogWithList(
            title = stringResource(R.string.units_sorting),
            listItems = mapOf(
                UnitsListSorting.USAGE to R.string.sort_by_usage,
                UnitsListSorting.ALPHABETICAL to R.string.sort_by_alphabetical,
                UnitsListSorting.SCALE_DESC to R.string.sort_by_scale_desc,
                UnitsListSorting.SCALE_ASC to R.string.sort_by_scale_asc,
            ),
            selectedItemIndex = prefs.value.unitConverterSorting,
            selectAction = viewModel::updateUnitConverterSorting,
            dismissAction = { showDialog = false }
        )
    }
}
