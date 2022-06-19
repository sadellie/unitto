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
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.BuildConfig
import com.sadellie.unitto.R
import com.sadellie.unitto.data.ABOUT_SCREEN
import com.sadellie.unitto.data.preferences.APP_THEMES
import com.sadellie.unitto.data.preferences.OUTPUT_FORMAT
import com.sadellie.unitto.data.preferences.PRECISIONS
import com.sadellie.unitto.data.preferences.SEPARATORS
import com.sadellie.unitto.screens.MainViewModel
import com.sadellie.unitto.screens.openLink


@Composable
fun SettingsScreen(
    mainViewModel: MainViewModel,
    navigateUpAction: () -> Unit,
    navControllerAction: (String) -> Unit
) {
    val mContext = LocalContext.current
    // Scrollable
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberSplineBasedDecay(), rememberTopAppBarScrollState())

    var currentDialogState: Int by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.settings_screen))
                },
                navigationIcon = {
                    IconButton(onClick = navigateUpAction) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_up_description)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            LazyColumn(contentPadding = padding) {
                item {
                    Column {
                        // Group header
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 12.dp),
                            text = stringResource(id = R.string.general_settings_group),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        SettingsListItem(
                            label = stringResource(R.string.precision_setting),
                            supportText = stringResource(R.string.precision_setting_support),
                            onClick = { currentDialogState = 1 }
                        )
                        SettingsListItem(
                            label = stringResource(R.string.separator_setting),
                            supportText = stringResource(R.string.separator_setting_support),
                            onClick = { currentDialogState = 2 }
                        )
                        SettingsListItem(
                            label = stringResource(R.string.output_format_setting),
                            supportText = stringResource(id = R.string.output_format_setting_support),
                            onClick = { currentDialogState = 3 }
                        )
                        SettingsListItem(
                            label = stringResource(R.string.theme_setting),
                            supportText = stringResource(R.string.theme_setting_support),
                            onClick = { currentDialogState = 4 }
                        )
                        SettingsListItem(
                            label = stringResource(id = R.string.currency_rates_note_setting),
                            onClick = { currentDialogState = 5 }
                        )

                        // Group header
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 12.dp),
                            text = stringResource(id = R.string.additional_settings_group),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        SettingsListItem(
                            label = stringResource(R.string.terms_and_conditions),
                            onClick = {
                                openLink(
                                    mContext,
                                    "http://sadellie.github.io/unitto/terms-app.html"
                                )
                            }
                        )
                        SettingsListItem(
                            label = stringResource(R.string.privacy_policy),
                            onClick = {
                                openLink(
                                    mContext,
                                    "http://sadellie.github.io/unitto/privacy-app.html"
                                )
                            }
                        )
                        SettingsListItem(
                            label = stringResource(id = R.string.send_usage_statistics),
                            supportText = stringResource(id = R.string.send_usage_statistics_support),
                            switchState = mainViewModel.enableAnalytics,
                            onSwitchChange = { mainViewModel.updateEnableAnalytics(!it) })
                        SettingsListItem(
                            label = stringResource(R.string.third_party_licenses),
                            onClick = { navControllerAction(ABOUT_SCREEN) }
                        )

                        BuildConfig.StoreLink.let {
                            SettingsListItem(
                                label = stringResource(R.string.rate_this_app),
                                onClick = {
                                    openLink(
                                        mContext,
                                        it
                                    )
                                }
                            )
                        }
                    }
                }
            }
        },
    )

    // Showing dialog
    when (currentDialogState) {
        1 -> {
            AlertDialogWithList(
                title = stringResource(id = R.string.precision_setting),
                listItems = PRECISIONS,
                selectedItemIndex = mainViewModel.precision,
                selectAction = { mainViewModel.updatePrecision(it) },
                dismissAction = { currentDialogState = 0 },
                supportText = stringResource(id = R.string.precision_setting_info)
            )
        }
        2 -> {
            AlertDialogWithList(
                title = stringResource(id = R.string.separator_setting),
                listItems = SEPARATORS,
                selectedItemIndex = mainViewModel.separator,
                selectAction = { mainViewModel.updateSeparator(it) },
                dismissAction = { currentDialogState = 0 }
            )
        }
        3 -> {
            AlertDialogWithList(
                title = stringResource(id = R.string.output_format_setting),
                listItems = OUTPUT_FORMAT,
                selectedItemIndex = mainViewModel.outputFormat,
                selectAction = { mainViewModel.updateOutputFormat(it) },
                dismissAction = { currentDialogState = 0 },
                supportText = stringResource(id = R.string.output_format_setting_info)
            )
        }
        4 -> {
            AlertDialogWithList(
                title = stringResource(id = R.string.theme_setting),
                listItems = APP_THEMES,
                selectedItemIndex = mainViewModel.currentTheme,
                selectAction = { mainViewModel.updateCurrentAppTheme(it) },
                dismissAction = { currentDialogState = 0 },
                // Show note for users with devices that support custom Dynamic theming
                supportText = if (Build.VERSION.SDK_INT in (Build.VERSION_CODES.O_MR1..Build.VERSION_CODES.R)) stringResource(
                    id = R.string.theme_setting_info
                ) else null
            )
        }
        5 -> {
            AlertDialogWithList(
                title = stringResource(id = R.string.currency_rates_note_title),
                dismissAction = { currentDialogState = 0 },
                supportText = stringResource(id = R.string.currency_rates_note_text),
                dismissButtonLabel = stringResource(id = R.string.ok_label)
            )
        }
        // Dismissing alert dialog
        else -> {}
    }
}
