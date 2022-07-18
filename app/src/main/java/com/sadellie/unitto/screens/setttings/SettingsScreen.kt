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

import android.os.Build
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.sadellie.unitto.BuildConfig
import com.sadellie.unitto.R
import com.sadellie.unitto.data.NavRoutes.ABOUT_SCREEN
import com.sadellie.unitto.data.NavRoutes.THEMES_SCREEN
import com.sadellie.unitto.data.preferences.APP_THEMES
import com.sadellie.unitto.data.preferences.OUTPUT_FORMAT
import com.sadellie.unitto.data.preferences.PRECISIONS
import com.sadellie.unitto.data.preferences.SEPARATORS
import com.sadellie.unitto.screens.MainViewModel
import com.sadellie.unitto.screens.common.UnittoLargeTopAppBar
import com.sadellie.unitto.screens.openLink
import com.sadellie.unitto.screens.setttings.components.AlertDialogWithList
import com.sadellie.unitto.screens.setttings.components.SettingsHeader
import com.sadellie.unitto.screens.setttings.components.SettingsListItem

@Composable
fun SettingsScreen(
    mainViewModel: MainViewModel,
    navigateUpAction: () -> Unit,
    navControllerAction: (String) -> Unit
) {
    val mContext = LocalContext.current
    var dialogState: DialogState by rememberSaveable {
        mutableStateOf(DialogState.NONE)
    }

    UnittoLargeTopAppBar(
        title = stringResource(id = R.string.settings_screen),
        navigateUpAction = navigateUpAction
    ) { padding ->
        LazyColumn(contentPadding = padding) {

            // GENERAL GROUP
            item { SettingsHeader(stringResource(R.string.general_settings_group)) }

            // PRECISION
            item {
                SettingsListItem(
                    stringResource(R.string.precision_setting),
                    stringResource(R.string.precision_setting_support)
                ) { dialogState = DialogState.PRECISION }
            }

            // SEPARATOR
            item {
                SettingsListItem(
                    stringResource(R.string.separator_setting),
                    stringResource(R.string.separator_setting_support)
                ) { dialogState = DialogState.SEPARATOR }
            }

            // OUTPUT FORMAT
            item {
                SettingsListItem(
                    stringResource(R.string.output_format_setting),
                    stringResource(R.string.output_format_setting_support)
                ) { dialogState = DialogState.OUTPUT_FORMAT }
            }

            // THEME
            item {
                SettingsListItem(
                    stringResource(R.string.theme_setting),
                    stringResource(R.string.theme_setting_support)
                ) { dialogState = DialogState.THEME }
            }

            // CURRENCY RATE NOTE
            item {
                SettingsListItem(
                    stringResource(R.string.currency_rates_note_setting)
                ) { dialogState = DialogState.CURRENCY_RATE }
            }

            // ADDITIONAL GROUP
            item { SettingsHeader(stringResource(R.string.additional_settings_group)) }

            // TERMS AND CONDITIONS
            item {
                SettingsListItem(
                    stringResource(R.string.terms_and_conditions)
                ) { openLink(mContext, "http://sadellie.github.io/unitto/terms-app.html") }
            }

            // PRIVACY POLICY
            item {
                SettingsListItem(
                    stringResource(R.string.privacy_policy)
                ) { openLink(mContext, "http://sadellie.github.io/unitto/privacy-app.html") }
            }

            // ANALYTICS
            item {
                SettingsListItem(
                    stringResource(R.string.send_usage_statistics),
                    stringResource(R.string.send_usage_statistics_support),
                    mainViewModel.enableAnalytics
                ) { mainViewModel.updateEnableAnalytics(!it) }
            }

            // THIRD PARTY
            item {
                SettingsListItem(
                    stringResource(R.string.third_party_licenses)
                ) { navControllerAction(ABOUT_SCREEN) }
            }

            // RATE THIS APP
            if (BuildConfig.StoreLink.isNotEmpty()) {
                item {
                    SettingsListItem(
                        stringResource(R.string.rate_this_app)
                    ) { openLink(mContext, BuildConfig.StoreLink) }
                }
            }

            // APP VERSION
            item {
                SettingsListItem(
                    label = stringResource(id = R.string.app_version_name_setting),
                    supportText = BuildConfig.VERSION_NAME
                ) {}
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
                title = stringResource(id = R.string.precision_setting),
                listItems = PRECISIONS,
                selectedItemIndex = mainViewModel.precision,
                selectAction = { mainViewModel.updatePrecision(it) },
                dismissAction = { resetDialog() },
                supportText = stringResource(id = R.string.precision_setting_info)
            )
        }
        DialogState.SEPARATOR -> {
            AlertDialogWithList(
                title = stringResource(id = R.string.separator_setting),
                listItems = SEPARATORS,
                selectedItemIndex = mainViewModel.separator,
                selectAction = { mainViewModel.updateSeparator(it) },
                dismissAction = { resetDialog() }
            )
        }
        DialogState.OUTPUT_FORMAT -> {
            AlertDialogWithList(
                title = stringResource(id = R.string.output_format_setting),
                listItems = OUTPUT_FORMAT,
                selectedItemIndex = mainViewModel.outputFormat,
                selectAction = { mainViewModel.updateOutputFormat(it) },
                dismissAction = { resetDialog() },
                supportText = stringResource(id = R.string.output_format_setting_info)
            )
        }
        DialogState.THEME -> {
            AlertDialogWithList(
                title = stringResource(id = R.string.theme_setting),
                listItems = APP_THEMES,
                selectedItemIndex = mainViewModel.currentTheme,
                selectAction = { mainViewModel.updateCurrentAppTheme(it) },
                dismissAction = { resetDialog() },
                // Show note for users with devices that support custom Dynamic theming
                supportText = if (Build.VERSION.SDK_INT in (Build.VERSION_CODES.O_MR1..Build.VERSION_CODES.R)) stringResource(
                    id = R.string.theme_setting_info
                ) else null
            )
        }
        DialogState.CURRENCY_RATE -> {
            AlertDialogWithList(
                title = stringResource(id = R.string.currency_rates_note_title),
                dismissAction = { resetDialog() },
                supportText = stringResource(id = R.string.currency_rates_note_text),
                dismissButtonLabel = stringResource(id = R.string.ok_label)
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
    NONE, PRECISION, SEPARATOR, OUTPUT_FORMAT, THEME, CURRENCY_RATE,
}
