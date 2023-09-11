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

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Copyright
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.BuildConfig
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.UnittoScreenWithLargeTopBar
import com.sadellie.unitto.core.ui.openLink

@Composable
internal fun AboutScreen(
    navigateUpAction: () -> Unit,
    navigateToThirdParty: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val mContext = LocalContext.current
    val userPrefs = viewModel.userPrefs.collectAsStateWithLifecycle()
    var aboutItemClick: Int by rememberSaveable { mutableIntStateOf(0) }
    var showDialog: Boolean by rememberSaveable { mutableStateOf(false) }

    UnittoScreenWithLargeTopBar(
        title = stringResource(R.string.about_unitto),
        navigationIcon = { NavigateUpButton(navigateUpAction) }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            // CURRENCY RATE NOTE
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Help,
                            stringResource(R.string.currency_rates_note_setting)
                        )
                    },
                    headlineContent = { Text(stringResource(R.string.currency_rates_note_setting)) },
                    modifier = Modifier.clickable { showDialog = true }
                )
            }

            // TERMS AND CONDITIONS
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.PrivacyTip,
                            stringResource(R.string.terms_and_conditions)
                        )
                    },
                    headlineContent = { Text(stringResource(R.string.terms_and_conditions)) },
                    modifier = Modifier.clickable {
                        openLink(
                            mContext,
                            "https://sadellie.github.io/unitto/terms"
                        )
                    }
                )
            }

            // PRIVACY POLICY
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Policy,
                            stringResource(R.string.privacy_policy)
                        )
                    },
                    headlineContent = { Text(stringResource(R.string.privacy_policy)) },
                    modifier = Modifier.clickable {
                        openLink(
                            mContext,
                            "https://sadellie.github.io/unitto/privacy"
                        )
                    }
                )
            }

            // OPEN SOURCE
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Code,
                            stringResource(R.string.open_source)
                        )
                    },
                    headlineContent = { Text(stringResource(R.string.open_source)) },
                    modifier = Modifier.clickable {
                        openLink(
                            mContext,
                            "https://github.com/sadellie/unitto"
                        )
                    }
                )
            }

            // TRANSLATE
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Translate,
                            stringResource(R.string.translate_app)
                        )
                    },
                    headlineContent = { Text(stringResource(R.string.translate_app)) },
                    supportingContent = { Text(stringResource(R.string.translate_app_support)) },
                    modifier = Modifier.clickable {
                        openLink(
                            mContext,
                            "https://poeditor.com/join/project/T4zjmoq8dx"
                        )
                    }
                )
            }

            // THIRD PARTY
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Copyright,
                            stringResource(R.string.third_party_licenses)
                        )
                    },
                    headlineContent = { Text(stringResource(R.string.third_party_licenses)) },
                    modifier = Modifier.clickable { navigateToThirdParty() }
                )
            }

            // APP VERSION
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Info,
                            stringResource(R.string.app_version_name_setting)
                        )
                    },
                    headlineContent = { Text(stringResource(R.string.app_version_name_setting)) },
                    supportingContent = { Text("${BuildConfig.APP_NAME} (${BuildConfig.APP_CODE})") },
                    modifier = Modifier.combinedClickable {
                        if (userPrefs.value.enableToolsExperiment) {
                            Toast.makeText(mContext, "Experiments features are already enabled!", Toast.LENGTH_LONG).show()
                            return@combinedClickable
                        }

                        aboutItemClick++
                        if (aboutItemClick < 7) return@combinedClickable

                        viewModel.enableToolsExperiment()
                        Toast.makeText(mContext, "Experimental features enabled!", Toast.LENGTH_LONG).show()
                    }
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            title = {
                Text(stringResource(R.string.currency_rates_note_title))
            },
            text = {
                Text(stringResource(R.string.currency_rates_note_text))
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.ok_label))
                }
            },
            onDismissRequest = { showDialog = false }
        )
    }
}
