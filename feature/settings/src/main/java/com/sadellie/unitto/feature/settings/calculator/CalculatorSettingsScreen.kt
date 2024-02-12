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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.FormatterSymbols
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.EmptyScreen
import com.sadellie.unitto.core.ui.common.ListItem
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.ScaffoldWithLargeTopBar
import com.sadellie.unitto.data.model.userprefs.CalculatorPreferences
import com.sadellie.unitto.data.userprefs.CalculatorPreferencesImpl

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
            )
        }
    }
}

@Composable
private fun CalculatorSettingsScreen(
    prefs: CalculatorPreferences,
    navigateUpAction: () -> Unit,
    updatePartialHistoryView: (Boolean) -> Unit,
) {
    ScaffoldWithLargeTopBar(
        title = stringResource(R.string.calculator_title),
        navigationIcon = { NavigateUpButton(navigateUpAction) }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            ListItem(
                headlineText = stringResource(R.string.settings_partial_history_view),
                icon = Icons.Default.Timer,
                supportingText = stringResource(R.string.settings_partial_history_view_support),
                switchState = prefs.partialHistoryView,
                onSwitchChange = updatePartialHistoryView
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCalculatorSettingsScreenStandard() {
    CalculatorSettingsScreen(
        prefs = CalculatorPreferencesImpl(
            radianMode = true,
            formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
            middleZero = false,
            acButton = false,
            partialHistoryView = false,
            precision = 3,
            outputFormat = OutputFormat.PLAIN
        ),
        navigateUpAction = {},
        updatePartialHistoryView = {},
    )
}
