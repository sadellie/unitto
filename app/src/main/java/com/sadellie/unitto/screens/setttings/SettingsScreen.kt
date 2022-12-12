/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

package com.sadellie.unitto.screens.setttings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Rule
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
import com.sadellie.unitto.BuildConfig
import com.sadellie.unitto.R
import com.sadellie.unitto.data.NavRoutes.ABOUT_SCREEN
import com.sadellie.unitto.data.NavRoutes.THEMES_SCREEN
import com.sadellie.unitto.data.NavRoutes.UNIT_GROUPS_SCREEN
import com.sadellie.unitto.data.preferences.OUTPUT_FORMAT
import com.sadellie.unitto.data.preferences.PRECISIONS
import com.sadellie.unitto.data.preferences.SEPARATORS
import com.sadellie.unitto.screens.common.Header
import com.sadellie.unitto.screens.common.UnittoLargeTopAppBar
import com.sadellie.unitto.screens.common.UnittoListItem
import com.sadellie.unitto.screens.openLink
import com.sadellie.unitto.screens.setttings.components.AlertDialogWithList

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navigateUpAction: () -> Unit,
    navControllerAction: (String) -> Unit
) {
    val mContext = LocalContext.current
    val userPrefs = viewModel.userPrefs.collectAsStateWithLifecycle()
    var dialogState: DialogState by rememberSaveable {
        mutableStateOf(DialogState.NONE)
    }

    UnittoLargeTopAppBar(
        title = stringResource(R.string.settings_screen),
        navigateUpAction = navigateUpAction
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
                    headlineText = { Text(stringResource(R.string.unit_groups_setting)) },
                    supportingText = { Text(stringResource(R.string.unit_groups_support)) },
                    modifier = Modifier.clickable { navControllerAction(UNIT_GROUPS_SCREEN) }
                )
            }

            // THEME
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Palette,
                            stringResource(R.string.theme_setting),
                        )
                    },
                    headlineText = { Text(stringResource(R.string.theme_setting)) },
                    supportingText = { Text(stringResource(R.string.theme_setting_support)) },
                    modifier = Modifier.clickable { navControllerAction(THEMES_SCREEN) }
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
                    headlineText = { Text(stringResource(R.string.precision_setting)) },
                    supportingText = { Text(stringResource(R.string.precision_setting_support)) },
                    modifier = Modifier.clickable { dialogState = DialogState.PRECISION }
                )
            }

            // SEPARATOR
            item {
                ListItem(
                    headlineText = { Text(stringResource(R.string.separator_setting)) },
                    supportingText = { Text(stringResource(R.string.separator_setting_support)) },
                    modifier = Modifier
                        .clickable { dialogState = DialogState.SEPARATOR }
                        .padding(start = 40.dp)
                )
            }

            // OUTPUT FORMAT
            item {
                ListItem(
                    headlineText = { Text(stringResource(R.string.output_format_setting)) },
                    supportingText = { Text(stringResource(R.string.output_format_setting_support)) },
                    modifier = Modifier
                        .clickable { dialogState = DialogState.OUTPUT_FORMAT }
                        .padding(start = 40.dp)
                )
            }

            // ADDITIONAL GROUP
            item { Header(stringResource(R.string.additional_settings_group)) }

            // ANALYTICS
            if (BuildConfig.ANALYTICS) {
                item {
                    UnittoListItem(
                        leadingContent = {
                            Icon(
                                Icons.Default.Insights,
                                stringResource(R.string.send_usage_statistics),
                            )
                        },
                        label = stringResource(R.string.send_usage_statistics),
                        supportText = stringResource(R.string.send_usage_statistics_support),
                        switchState = userPrefs.value.enableAnalytics
                    ) { viewModel.updateEnableAnalytics(it) }
                }
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
                        headlineText = { Text(stringResource(R.string.rate_this_app)) },
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
                    headlineText = { Text(stringResource(R.string.about_unitto)) },
                    supportingText = { Text(stringResource(R.string.about_unitto_support)) },
                    modifier = Modifier.clickable { navControllerAction(ABOUT_SCREEN) }
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
                selectAction = { viewModel.updatePrecision(it) },
                dismissAction = { resetDialog() },
                supportText = stringResource(R.string.precision_setting_info)
            )
        }
        DialogState.SEPARATOR -> {
            AlertDialogWithList(
                title = stringResource(R.string.separator_setting),
                listItems = SEPARATORS,
                selectedItemIndex = userPrefs.value.separator,
                selectAction = { viewModel.updateSeparator(it) },
                dismissAction = { resetDialog() }
            )
        }
        DialogState.OUTPUT_FORMAT -> {
            AlertDialogWithList(
                title = stringResource(R.string.output_format_setting),
                listItems = OUTPUT_FORMAT,
                selectedItemIndex = userPrefs.value.outputFormat,
                selectAction = { viewModel.updateOutputFormat(it) },
                dismissAction = { resetDialog() },
                supportText = stringResource(R.string.output_format_setting_info)
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
    NONE, PRECISION, SEPARATOR, OUTPUT_FORMAT,
}
