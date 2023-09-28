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

package com.sadellie.unitto.feature.settings.about

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Copyright
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.BuildConfig
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.UnittoListItem
import com.sadellie.unitto.core.ui.common.UnittoScreenWithLargeTopBar
import com.sadellie.unitto.core.ui.openLink
import com.sadellie.unitto.data.userprefs.AboutPreferences

@Composable
internal fun AboutRoute(
    viewModel: AboutViewModel = hiltViewModel(),
    navigateUpAction: () -> Unit,
    navigateToThirdParty: () -> Unit,
) {
    val prefs = viewModel.prefs.collectAsStateWithLifecycle()

    AboutScreen(
        prefs = prefs.value,
        navigateUpAction = navigateUpAction,
        navigateToThirdParty = navigateToThirdParty,
        enableToolsExperiment = viewModel::enableToolsExperiment
    )
}

@Composable
private fun AboutScreen(
    prefs: AboutPreferences,
    navigateUpAction: () -> Unit,
    navigateToThirdParty: () -> Unit,
    enableToolsExperiment: () -> Unit,
) {
    val mContext = LocalContext.current
    var aboutItemClick: Int by rememberSaveable { mutableIntStateOf(0) }
    var showDialog: Boolean by rememberSaveable { mutableStateOf(false) }

    UnittoScreenWithLargeTopBar(
        title = stringResource(R.string.about_unitto),
        navigationIcon = { NavigateUpButton(navigateUpAction) }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            // CURRENCY RATE NOTE
            item {
                UnittoListItem(
                    icon = Icons.AutoMirrored.Filled.Help,
                    iconDescription = stringResource(R.string.currency_rates_note_setting),
                    headlineText = stringResource(R.string.currency_rates_note_setting),
                    modifier = Modifier.clickable { showDialog = true }
                )
            }

            // TERMS AND CONDITIONS
            item {
                UnittoListItem(
                    icon = Icons.Default.PrivacyTip,
                    iconDescription = stringResource(R.string.terms_and_conditions),
                    headlineText = stringResource(R.string.terms_and_conditions),
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
                UnittoListItem(
                    icon = Icons.Default.Policy,
                    iconDescription = stringResource(R.string.privacy_policy),
                    headlineText = stringResource(R.string.privacy_policy),
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
                UnittoListItem(
                    icon = Icons.Default.Code,
                    iconDescription = stringResource(R.string.open_source),
                    headlineText = stringResource(R.string.open_source),
                    modifier = Modifier.clickable {
                        openLink(
                            mContext,
                            "https://github.com/sadellie/unitto"
                        )
                    }
                )
            }

            // THIRD PARTY
            item {
                UnittoListItem(
                    icon = Icons.Default.Copyright,
                    iconDescription = stringResource(R.string.third_party_licenses),
                    headlineText = stringResource(R.string.third_party_licenses),
                    modifier = Modifier.clickable { navigateToThirdParty() }
                )
            }

            // APP VERSION
            item {
                UnittoListItem(
                    icon = Icons.Default.Info,
                    iconDescription = stringResource(R.string.app_version_name_setting),
                    headlineText = stringResource(R.string.app_version_name_setting),
                    supportingText = "${BuildConfig.APP_NAME} (${BuildConfig.APP_CODE})",
                    modifier = Modifier.combinedClickable {
                        if (prefs.enableToolsExperiment) {
                            Toast.makeText(mContext, "Experiments features are already enabled!", Toast.LENGTH_LONG).show()
                            return@combinedClickable
                        }

                        aboutItemClick++
                        if (aboutItemClick < 7) return@combinedClickable

                        enableToolsExperiment()
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

@Preview
@Composable
fun PreviewAboutScreen() {
    AboutScreen(
        prefs = AboutPreferences(),
        navigateUpAction = {},
        navigateToThirdParty = {},
        enableToolsExperiment = {}
    )
}
