/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2025 Elshan Agaev
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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.common.openLink
import com.sadellie.unitto.core.designsystem.icons.symbols.Code
import com.sadellie.unitto.core.designsystem.icons.symbols.Copyright
import com.sadellie.unitto.core.designsystem.icons.symbols.Help
import com.sadellie.unitto.core.designsystem.icons.symbols.Info
import com.sadellie.unitto.core.designsystem.icons.symbols.Policy
import com.sadellie.unitto.core.designsystem.icons.symbols.PrivacyTip
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.ListItemExpressiveDefaults
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import com.sadellie.unitto.feature.settings.BuildConfig

@Composable
internal fun AboutRoute(
  navigateUpAction: () -> Unit,
  navigateToThirdParty: () -> Unit,
  navigateToEasterEgg: () -> Unit,
) {
  AboutScreen(
    navigateUpAction = navigateUpAction,
    navigateToThirdParty = navigateToThirdParty,
    navigateToEasterEgg = navigateToEasterEgg,
  )
}

@Composable
private fun AboutScreen(
  navigateUpAction: () -> Unit,
  navigateToThirdParty: () -> Unit,
  navigateToEasterEgg: () -> Unit,
) {
  val mContext = LocalContext.current
  var aboutItemClick: Int by rememberSaveable { mutableIntStateOf(0) }
  var showDialog: Boolean by rememberSaveable { mutableStateOf(false) }

  ScaffoldWithLargeTopBar(
    title = stringResource(R.string.settings_about_unitto),
    navigationIcon = { NavigateUpButton(navigateUpAction) },
  ) { padding ->
    Column(
      modifier =
        Modifier.fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(padding)
          .padding(start = Sizes.large, end = Sizes.large, bottom = Sizes.large),
      verticalArrangement = ListItemExpressiveDefaults.ListArrangement,
    ) {
      ListItemExpressive(
        icon = Symbols.Help,
        onClick = { showDialog = true },
        headlineText = stringResource(R.string.settings_currency_rates_note_title),
        shape = ListItemExpressiveDefaults.firstShape,
      )
      ListItemExpressive(
        icon = Symbols.PrivacyTip,
        headlineText = stringResource(R.string.settings_terms_and_conditions),
        onClick = { openLink(mContext, "https://sadellie.github.io/unitto/terms") },
        shape = ListItemExpressiveDefaults.middleShape,
      )
      ListItemExpressive(
        icon = Symbols.Policy,
        headlineText = stringResource(R.string.settings_privacy_policy),
        onClick = { openLink(mContext, "https://sadellie.github.io/unitto/privacy") },
        shape = ListItemExpressiveDefaults.middleShape,
      )
      ListItemExpressive(
        icon = Symbols.Code,
        headlineText = stringResource(R.string.settings_view_source_code),
        onClick = { openLink(mContext, "https://github.com/sadellie/unitto") },
        shape = ListItemExpressiveDefaults.middleShape,
      )
      ListItemExpressive(
        icon = Symbols.Copyright,
        headlineText = stringResource(R.string.settings_third_party_licenses),
        onClick = { navigateToThirdParty() },
        shape = ListItemExpressiveDefaults.middleShape,
      )
      ListItemExpressive(
        icon = Symbols.Info,
        headlineText = stringResource(R.string.settings_version_name),
        supportingText =
          "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE}) ${BuildConfig.BUILD_TYPE}",
        onClick = {
          val clicksToOpenEasterEgg = 5
          aboutItemClick++
          if (aboutItemClick > clicksToOpenEasterEgg) {
            aboutItemClick = 0
            navigateToEasterEgg()
          }
        },
        shape = ListItemExpressiveDefaults.lastShape,
      )
    }
  }

  if (showDialog) {
    AlertDialog(
      title = { Text(stringResource(R.string.settings_note)) },
      text = { Text(stringResource(R.string.settings_currency_rates_note_text)) },
      confirmButton = {
        TextButton(onClick = { showDialog = false }) { Text(stringResource(R.string.common_ok)) }
      },
      onDismissRequest = { showDialog = false },
    )
  }
}

@Preview
@Composable
fun PreviewAboutScreen() {
  AboutScreen(navigateUpAction = {}, navigateToThirdParty = {}, navigateToEasterEgg = {})
}
