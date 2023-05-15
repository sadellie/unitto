/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

package com.sadellie.unitto.feature.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled._123
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.BuildConfig
import com.sadellie.unitto.core.base.OUTPUT_FORMAT
import com.sadellie.unitto.core.base.PRECISIONS
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.SEPARATORS
import com.sadellie.unitto.core.base.TOP_LEVEL_DESTINATIONS
import com.sadellie.unitto.core.ui.common.Header
import com.sadellie.unitto.core.ui.common.MenuButton
import com.sadellie.unitto.core.ui.common.UnittoListItem
import com.sadellie.unitto.core.ui.common.UnittoScreenWithLargeTopBar
import com.sadellie.unitto.core.ui.openLink
import com.sadellie.unitto.data.model.UnitsListSorting
import com.sadellie.unitto.feature.settings.components.AlertDialogWithList
import com.sadellie.unitto.feature.settings.navigation.aboutRoute
import com.sadellie.unitto.feature.settings.navigation.themesRoute
import com.sadellie.unitto.feature.settings.navigation.unitsGroupRoute

@Composable
internal fun SettingsScreen(
    viewModel: SettingsViewModel,
    menuButtonClick: () -> Unit,
    navControllerAction: (String) -> Unit
) {
    val mContext = LocalContext.current
    val userPrefs = viewModel.userPrefs.collectAsStateWithLifecycle()
    var dialogState: DialogState by rememberSaveable {
        mutableStateOf(DialogState.NONE)
    }

    UnittoScreenWithLargeTopBar(
        title = stringResource(R.string.settings_screen),
        navigationIcon = { MenuButton(menuButtonClick) }
    ) { padding ->
        LazyColumn(contentPadding = padding) {

            // THEME
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Palette,
                            stringResource(R.string.theme_setting),
                        )
                    },
                    headlineContent = { Text(stringResource(R.string.theme_setting)) },
                    supportingContent = { Text(stringResource(R.string.theme_setting_support)) },
                    modifier = Modifier.clickable { navControllerAction(themesRoute) }
                )
            }

            // START SCREEN
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Home,
                            stringResource(R.string.starting_screen_setting),
                        )
                    },
                    headlineContent = { Text(stringResource(R.string.starting_screen_setting)) },
                    supportingContent = { Text(stringResource(R.string.starting_screen_setting_support)) },
                    modifier = Modifier.clickable { dialogState = DialogState.START_SCREEN }
                )
            }

            // GENERAL GROUP
            item { Header(stringResource(R.string.formatting_settings_group)) }

            // PRECISION
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default._123,
                            stringResource(R.string.precision_setting),
                        )
                    },
                    headlineContent = { Text(stringResource(R.string.precision_setting)) },
                    supportingContent = { Text(stringResource(R.string.precision_setting_support)) },
                    modifier = Modifier.clickable { dialogState = DialogState.PRECISION }
                )
            }

            // SEPARATOR
            item {
                ListItem(
                    headlineContent = { Text(stringResource(R.string.separator_setting)) },
                    supportingContent = { Text(stringResource(R.string.separator_setting_support)) },
                    modifier = Modifier
                        .clickable { dialogState = DialogState.SEPARATOR }
                        .padding(start = 40.dp)
                )
            }

            // OUTPUT FORMAT
            item {
                ListItem(
                    headlineContent = { Text(stringResource(R.string.output_format_setting)) },
                    supportingContent = { Text(stringResource(R.string.output_format_setting_support)) },
                    modifier = Modifier
                        .clickable { dialogState = DialogState.OUTPUT_FORMAT }
                        .padding(start = 40.dp)
                )
            }

            // UNIT CONVERTER GROUP
            item { Header(stringResource(R.string.unit_converter)) }

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
                    modifier = Modifier.clickable { navControllerAction(unitsGroupRoute) }
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
                    modifier = Modifier.clickable { dialogState = DialogState.UNIT_LIST_SORTING }
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
                    switchState = userPrefs.value.unitConverterFormatTime,
                    onSwitchChange = viewModel::updateUnitConverterFormatTime
                )
            }

            // ADDITIONAL GROUP
            item { Header(stringResource(R.string.additional_settings_group)) }

            // VIBRATIONS
            item {
                UnittoListItem(
                    label = stringResource(R.string.enable_vibrations),
                    leadingContent = {
                        Icon(
                            Icons.Default.Vibration,
                            stringResource(R.string.enable_vibrations)
                        )
                    },
                    supportContent = stringResource(R.string.enable_vibrations_support),
                    switchState = userPrefs.value.enableVibrations,
                    onSwitchChange = viewModel::updateVibrations
                )
            }

            // RATE THIS APP
            if (BuildConfig.STORE_LINK.isNotEmpty()) {
                item {
                    ListItem(
                        leadingContent = {
                            Icon(
                                Icons.Default.RateReview,
                                stringResource(R.string.rate_this_app),
                            )
                        },
                        headlineContent = { Text(stringResource(R.string.rate_this_app)) },
                        modifier = Modifier.clickable { openLink(mContext, BuildConfig.STORE_LINK) }
                    )
                }
            }

            // More settings
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Info,
                            stringResource(R.string.about_unitto),
                        )
                    },
                    headlineContent = { Text(stringResource(R.string.about_unitto)) },
                    supportingContent = { Text(stringResource(R.string.about_unitto_support)) },
                    modifier = Modifier.clickable { navControllerAction(aboutRoute) }
                )
            }
        }
    }

    /**
     * Function to reset currently displayed dialog.
     */
    fun resetDialog() {
        dialogState = DialogState.NONE
    }

    // Showing dialog
    when (dialogState) {
        DialogState.PRECISION -> {
            AlertDialogWithList(
                title = stringResource(R.string.precision_setting),
                listItems = PRECISIONS,
                selectedItemIndex = userPrefs.value.digitsPrecision,
                selectAction = viewModel::updatePrecision,
                dismissAction = { resetDialog() },
                supportText = stringResource(R.string.precision_setting_info)
            )
        }
        DialogState.SEPARATOR -> {
            AlertDialogWithList(
                title = stringResource(R.string.separator_setting),
                listItems = SEPARATORS,
                selectedItemIndex = userPrefs.value.separator,
                selectAction = viewModel::updateSeparator,
                dismissAction = { resetDialog() }
            )
        }
        DialogState.OUTPUT_FORMAT -> {
            AlertDialogWithList(
                title = stringResource(R.string.output_format_setting),
                listItems = OUTPUT_FORMAT,
                selectedItemIndex = userPrefs.value.outputFormat,
                selectAction = viewModel::updateOutputFormat,
                dismissAction = { resetDialog() },
                supportText = stringResource(R.string.output_format_setting_info)
            )
        }
        DialogState.START_SCREEN -> {
            AlertDialogWithList(
                title = stringResource(R.string.starting_screen_setting),
                selectedItemIndex = userPrefs.value.startingScreen,
                listItems = TOP_LEVEL_DESTINATIONS.mapKeys { it.key.route },
                selectAction = viewModel::updateStartingScreen,
                dismissAction = { resetDialog() }
            )
        }
        DialogState.UNIT_LIST_SORTING -> {
            AlertDialogWithList(
                title = stringResource(R.string.units_sorting),
                listItems = mapOf(
                    UnitsListSorting.USAGE to R.string.sort_by_usage,
                    UnitsListSorting.ALPHABETICAL to R.string.sort_by_alphabetical,
                    UnitsListSorting.SCALE_DESC to R.string.sort_by_scale_desc,
                    UnitsListSorting.SCALE_ASC to R.string.sort_by_scale_asc,
                ),
                selectedItemIndex = userPrefs.value.unitConverterSorting,
                selectAction = viewModel::updateUnitConverterSorting,
                dismissAction = { resetDialog() }
            )
        }
        // Dismissing alert dialog
        else -> {}
    }
}

/**
 * All possible states for alert dialog that opens when user clicks on settings.
 */
private enum class DialogState {
    NONE, PRECISION, SEPARATOR, OUTPUT_FORMAT, START_SCREEN, UNIT_LIST_SORTING
}
