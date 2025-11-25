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
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.common.Config
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
import com.sadellie.unitto.core.ui.rememberLinkOpener
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_ok
import unitto.core.common.generated.resources.settings_about_unitto
import unitto.core.common.generated.resources.settings_currency_rates_note_text
import unitto.core.common.generated.resources.settings_currency_rates_note_title
import unitto.core.common.generated.resources.settings_note
import unitto.core.common.generated.resources.settings_privacy_policy
import unitto.core.common.generated.resources.settings_terms_and_conditions
import unitto.core.common.generated.resources.settings_third_party_licenses
import unitto.core.common.generated.resources.settings_version_name
import unitto.core.common.generated.resources.settings_view_source_code

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
  var aboutItemClick: Int by rememberSaveable { mutableIntStateOf(0) }
  var showDialog: Boolean by rememberSaveable { mutableStateOf(false) }

  ScaffoldWithLargeTopBar(
    title = stringResource(Res.string.settings_about_unitto),
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
      val linkOpener = rememberLinkOpener()
      ListItemExpressive(
        icon = Symbols.Help,
        onClick = { showDialog = true },
        headlineText = stringResource(Res.string.settings_currency_rates_note_title),
        shape = ListItemExpressiveDefaults.firstShape,
      )
      ListItemExpressive(
        icon = Symbols.PrivacyTip,
        headlineText = stringResource(Res.string.settings_terms_and_conditions),
        onClick = { linkOpener.launch("https://sadellie.github.io/unitto/terms") },
        shape = ListItemExpressiveDefaults.middleShape,
      )
      ListItemExpressive(
        icon = Symbols.Policy,
        headlineText = stringResource(Res.string.settings_privacy_policy),
        onClick = { linkOpener.launch("https://sadellie.github.io/unitto/privacy") },
        shape = ListItemExpressiveDefaults.middleShape,
      )
      ListItemExpressive(
        icon = Symbols.Code,
        headlineText = stringResource(Res.string.settings_view_source_code),
        onClick = { linkOpener.launch("https://github.com/sadellie/unitto") },
        shape = ListItemExpressiveDefaults.middleShape,
      )
      ListItemExpressive(
        icon = Symbols.Copyright,
        headlineText = stringResource(Res.string.settings_third_party_licenses),
        onClick = { navigateToThirdParty() },
        shape = ListItemExpressiveDefaults.middleShape,
      )
      ListItemExpressive(
        icon = Symbols.Info,
        headlineText = stringResource(Res.string.settings_version_name),
        supportingText =
          "${Config.VERSION_NAME} (${Config.VERSION_CODE}) ${Config.BUILD_TYPE}",
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
      title = { Text(stringResource(Res.string.settings_note)) },
      text = { Text(stringResource(Res.string.settings_currency_rates_note_text)) },
      confirmButton = {
        TextButton(onClick = { showDialog = false }) { Text(stringResource(Res.string.common_ok)) }
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
