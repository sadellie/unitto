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
import androidx.compose.foundation.lazy.LazyColumn
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
    var dialogState: DialogState by rememberSaveable {
        mutableStateOf(DialogState.NONE)
    }

    UnittoLargeTopAppBar(
        title = stringResource(R.string.settings_screen),
        navigateUpAction = navigateUpAction
    ) { padding ->
        LazyColumn(contentPadding = padding) {

            // GENERAL GROUP
            item { Header(stringResource(R.string.general_settings_group)) }

            // THEME
            item {
                ListItem(
                    headlineText = { Text(stringResource(R.string.unit_groups_setting)) },
                    modifier = Modifier.clickable { navControllerAction(UNIT_GROUPS_SCREEN) }
                )
            }

            // PRECISION
            item {
                ListItem(
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
                    modifier = Modifier.clickable { dialogState = DialogState.SEPARATOR }
                )
            }

            // OUTPUT FORMAT
            item {
                ListItem(
                    headlineText = { Text(stringResource(R.string.output_format_setting)) },
                    supportingText = { Text(stringResource(R.string.output_format_setting_support)) },
                    modifier = Modifier.clickable { dialogState = DialogState.OUTPUT_FORMAT }
                )
            }

            // THEME
            item {
                ListItem(
                    headlineText = { Text(stringResource(R.string.theme_setting)) },
                    supportingText = { Text(stringResource(R.string.theme_setting_support)) },
                    modifier = Modifier.clickable { navControllerAction(THEMES_SCREEN) }
                )
            }

            // CURRENCY RATE NOTE
            item {
                ListItem(
                    headlineText = { Text(stringResource(R.string.currency_rates_note_setting)) },
                    modifier = Modifier.clickable { dialogState = DialogState.CURRENCY_RATE }
                )
            }

            // ADDITIONAL GROUP
            item { Header(stringResource(R.string.additional_settings_group)) }

            // TERMS AND CONDITIONS
            item {
                ListItem(
                    headlineText = { Text(stringResource(R.string.terms_and_conditions)) },
                    modifier = Modifier.clickable {
                        openLink(
                            mContext,
                            "http://sadellie.github.io/unitto/terms-app.html"
                        )
                    }
                )
            }

            // PRIVACY POLICY
            item {
                ListItem(
                    headlineText = { Text(stringResource(R.string.privacy_policy)) },
                    modifier = Modifier.clickable {
                        openLink(
                            mContext,
                            "http://sadellie.github.io/unitto/privacy-app.html"
                        )
                    }
                )
            }

            // ANALYTICS
            if (BuildConfig.ANALYTICS) {
                item {
                    UnittoListItem(
                        label = stringResource(R.string.send_usage_statistics),
                        supportText = stringResource(R.string.send_usage_statistics_support),
                        switchState = viewModel.userPrefs.enableAnalytics
                    ) { viewModel.updateEnableAnalytics(it) }
                }
            }

            // THIRD PARTY
            item {
                ListItem(
                    headlineText = { Text(stringResource(R.string.third_party_licenses)) },
                    modifier = Modifier.clickable { navControllerAction(ABOUT_SCREEN) }
                )
            }

            // RATE THIS APP
            if (BuildConfig.STORE_LINK.isNotEmpty()) {
                item {
                    ListItem(
                        headlineText = { Text(stringResource(R.string.rate_this_app)) },
                        modifier = Modifier.clickable { openLink(mContext, BuildConfig.STORE_LINK) }
                    )
                }
            }

            // APP VERSION
            item {
                ListItem(
                    headlineText = { Text(stringResource(R.string.app_version_name_setting)) },
                    supportingText = { Text("${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})") },
                    modifier = Modifier.clickable {}
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
                selectedItemIndex = viewModel.userPrefs.digitsPrecision,
                selectAction = { viewModel.updatePrecision(it) },
                dismissAction = { resetDialog() },
                supportText = stringResource(R.string.precision_setting_info)
            )
        }
        DialogState.SEPARATOR -> {
            AlertDialogWithList(
                title = stringResource(R.string.separator_setting),
                listItems = SEPARATORS,
                selectedItemIndex = viewModel.userPrefs.separator,
                selectAction = { viewModel.updateSeparator(it) },
                dismissAction = { resetDialog() }
            )
        }
        DialogState.OUTPUT_FORMAT -> {
            AlertDialogWithList(
                title = stringResource(R.string.output_format_setting),
                listItems = OUTPUT_FORMAT,
                selectedItemIndex = viewModel.userPrefs.outputFormat,
                selectAction = { viewModel.updateOutputFormat(it) },
                dismissAction = { resetDialog() },
                supportText = stringResource(R.string.output_format_setting_info)
            )
        }
        DialogState.CURRENCY_RATE -> {
            AlertDialogWithList(
                title = stringResource(R.string.currency_rates_note_title),
                dismissAction = { resetDialog() },
                supportText = stringResource(R.string.currency_rates_note_text),
                dismissButtonLabel = stringResource(R.string.ok_label)
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
    NONE, PRECISION, SEPARATOR, OUTPUT_FORMAT, CURRENCY_RATE,
}
