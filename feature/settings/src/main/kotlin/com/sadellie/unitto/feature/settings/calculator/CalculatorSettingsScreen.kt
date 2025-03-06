/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

package com.sadellie.unitto.feature.settings.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.datastore.CalculatorPreferences
import com.sadellie.unitto.core.designsystem.icons.iconpack.Fraction
import com.sadellie.unitto.core.designsystem.icons.iconpack.IconPack
import com.sadellie.unitto.core.designsystem.icons.symbols.History
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.icons.symbols.Timer
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListItem
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar

@Composable
internal fun CalculatorSettingsRoute(
  viewModel: CalculatorSettingsViewModel = hiltViewModel(),
  navigateUpAction: () -> Unit,
) {
  when (val prefs = viewModel.prefs.collectAsStateWithLifecycle().value) {
    null -> EmptyScreen()
    else -> {
      CalculatorSettingsScreen(
        prefs = prefs,
        navigateUpAction = navigateUpAction,
        updatePartialHistoryView = viewModel::updatePartialHistoryView,
        updateFractionalOutput = viewModel::updateFractionalOutput,
        updateOpenHistoryViewButton = viewModel::updateOpenHistoryViewButton,
      )
    }
  }
}

@Composable
private fun CalculatorSettingsScreen(
  prefs: CalculatorPreferences,
  navigateUpAction: () -> Unit,
  updatePartialHistoryView: (Boolean) -> Unit,
  updateOpenHistoryViewButton: (Boolean) -> Unit,
  updateFractionalOutput: (Boolean) -> Unit,
) {
  ScaffoldWithLargeTopBar(
    title = stringResource(R.string.calculator_title),
    navigationIcon = { NavigateUpButton(navigateUpAction) },
  ) { padding ->
    Column(Modifier.padding(padding)) {
      ListItem(
        headlineText = stringResource(R.string.settings_history_view_button),
        icon = Symbols.History,
        supportingText = stringResource(R.string.settings_history_view_button_support),
        switchState = prefs.openHistoryViewButton,
        onSwitchChange = updateOpenHistoryViewButton,
      )

      ListItem(
        headlineText = stringResource(R.string.settings_fractional_output),
        icon = IconPack.Fraction,
        supportingText = stringResource(R.string.settings_fractional_output_support),
        switchState = prefs.fractionalOutput,
        onSwitchChange = updateFractionalOutput,
      )

      ListItem(
        headlineText = stringResource(R.string.settings_partial_history_view),
        icon = Symbols.Timer,
        supportingText = stringResource(R.string.settings_partial_history_view_support),
        switchState = prefs.partialHistoryView,
        onSwitchChange = updatePartialHistoryView,
      )
    }
  }
}

@Preview
@Composable
private fun PreviewCalculatorSettingsScreenStandard() {
  CalculatorSettingsScreen(
    prefs =
      CalculatorPreferences(
        radianMode = true,
        formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
        middleZero = false,
        acButton = false,
        additionalButtons = false,
        inverseMode = false,
        partialHistoryView = false,
        initialPartialHistoryView = false,
        openHistoryViewButton = false,
        precision = 3,
        outputFormat = OutputFormat.PLAIN,
        fractionalOutput = true,
      ),
    navigateUpAction = {},
    updatePartialHistoryView = {},
    updateFractionalOutput = {},
    updateOpenHistoryViewButton = {},
  )
}
