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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Copyright
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Translate
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
import com.sadellie.unitto.BuildConfig
import com.sadellie.unitto.R
import com.sadellie.unitto.data.NavRoutes.THIRD_PARTY_LICENSES_SCREEN
import com.sadellie.unitto.screens.common.UnittoLargeTopAppBar
import com.sadellie.unitto.screens.openLink
import com.sadellie.unitto.screens.setttings.components.AlertDialogWithList


@Composable
fun AboutScreen(
    navigateUpAction: () -> Unit,
    navControllerAction: (String) -> Unit
) {
    val mContext = LocalContext.current

    var showDialog: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    UnittoLargeTopAppBar(
        title = "About Unitto",
        navigateUpAction = navigateUpAction
    ) { padding ->
        LazyColumn(contentPadding = padding) {

            // CURRENCY RATE NOTE
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Help,
                            stringResource(R.string.currency_rates_note_setting),
                        )
                    },
                    headlineText = { Text(stringResource(R.string.currency_rates_note_setting)) },
                    modifier = Modifier.clickable { showDialog = true }
                )
            }

            // TERMS AND CONDITIONS
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.PrivacyTip,
                            stringResource(R.string.terms_and_conditions),
                        )
                    },
                    headlineText = { Text(stringResource(R.string.terms_and_conditions)) },
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
                            stringResource(R.string.privacy_policy),
                        )
                    },
                    headlineText = { Text(stringResource(R.string.privacy_policy)) },
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
                            stringResource(R.string.open_source),
                        )
                    },
                    headlineText = { Text(stringResource(R.string.open_source)) },
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
                            stringResource(R.string.translate_app),
                        )
                    },
                    headlineText = { Text(stringResource(R.string.translate_app)) },
                    supportingText = { Text(stringResource(R.string.translate_app_support)) },
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
                            stringResource(R.string.third_party_licenses),
                        )
                    },
                    headlineText = { Text(stringResource(R.string.third_party_licenses)) },
                    modifier = Modifier.clickable { navControllerAction(THIRD_PARTY_LICENSES_SCREEN) }
                )
            }

            // APP VERSION
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Info,
                            stringResource(R.string.app_version_name_setting),
                        )
                    },
                    headlineText = { Text(stringResource(R.string.app_version_name_setting)) },
                    supportingText = { Text("${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})") },
                    modifier = Modifier.clickable {}
                )
            }
        }
    }

    if (showDialog) {
        AlertDialogWithList(
            title = stringResource(R.string.currency_rates_note_title),
            dismissAction = { showDialog = false },
            supportText = stringResource(R.string.currency_rates_note_text),
            dismissButtonLabel = stringResource(R.string.ok_label)
        )
    }
}
