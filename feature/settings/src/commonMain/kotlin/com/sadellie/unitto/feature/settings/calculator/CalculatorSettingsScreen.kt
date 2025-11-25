/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.collectAsStateWithLifecycleKMP
import com.sadellie.unitto.core.datastore.CalculatorPreferences
import com.sadellie.unitto.core.designsystem.icons.iconpack.Fraction
import com.sadellie.unitto.core.designsystem.icons.iconpack.IconPack
import com.sadellie.unitto.core.designsystem.icons.symbols.History
import com.sadellie.unitto.core.designsystem.icons.symbols.MoveSelectionDown
import com.sadellie.unitto.core.designsystem.icons.symbols.SplitscreenBottom
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.ListItemExpressiveDefaults
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.calculator_title
import unitto.core.common.generated.resources.settings_fractional_output
import unitto.core.common.generated.resources.settings_fractional_output_support
import unitto.core.common.generated.resources.settings_history_view_button
import unitto.core.common.generated.resources.settings_history_view_button_support
import unitto.core.common.generated.resources.settings_partial_history_view
import unitto.core.common.generated.resources.settings_partial_history_view_support
import unitto.core.common.generated.resources.settings_stepped_drag_gesture
import unitto.core.common.generated.resources.settings_stepped_drag_gesture_support

@Composable
internal fun CalculatorSettingsRoute(
  viewModel: CalculatorSettingsViewModel = koinViewModel(),
  navigateUpAction: () -> Unit,
) {
  when (val prefs = viewModel.prefs.collectAsStateWithLifecycleKMP().value) {
    null -> EmptyScreen()
    else -> {
      CalculatorSettingsScreen(
        prefs = prefs,
        navigateUpAction = navigateUpAction,
        updatePartialHistoryView = viewModel::updatePartialHistoryView,
        updateSteppedPartialHistoryView = viewModel::updateSteppedPartialHistoryView,
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
  updateSteppedPartialHistoryView: (Boolean) -> Unit,
  updateOpenHistoryViewButton: (Boolean) -> Unit,
  updateFractionalOutput: (Boolean) -> Unit,
) {
  ScaffoldWithLargeTopBar(
    title = stringResource(Res.string.calculator_title),
    navigationIcon = { NavigateUpButton(navigateUpAction) },
  ) { padding ->
    Column(
      modifier =
        Modifier.padding(padding)
          .padding(start = Sizes.large, end = Sizes.large, bottom = Sizes.large),
      verticalArrangement = ListItemExpressiveDefaults.ListArrangement,
    ) {
      ListItemExpressive(
        headlineText = stringResource(Res.string.settings_history_view_button),
        icon = Symbols.History,
        supportingText = stringResource(Res.string.settings_history_view_button_support),
        switchState = prefs.openHistoryViewButton,
        onSwitchChange = updateOpenHistoryViewButton,
        shape = ListItemExpressiveDefaults.firstShape,
      )

      ListItemExpressive(
        headlineText = stringResource(Res.string.settings_fractional_output),
        icon = IconPack.Fraction,
        supportingText = stringResource(Res.string.settings_fractional_output_support),
        switchState = prefs.fractionalOutput,
        onSwitchChange = updateFractionalOutput,
        shape = ListItemExpressiveDefaults.middleShape,
      )

      ListItemExpressive(
        headlineText = stringResource(Res.string.settings_partial_history_view),
        icon = Symbols.SplitscreenBottom,
        supportingText = stringResource(Res.string.settings_partial_history_view_support),
        switchState = prefs.partialHistoryView,
        onSwitchChange = updatePartialHistoryView,
        shape =
          if (prefs.partialHistoryView) ListItemExpressiveDefaults.middleShape
          else ListItemExpressiveDefaults.lastShape,
      )

      AnimatedVisibility(
        visible = prefs.partialHistoryView,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
        modifier = Modifier.fillMaxWidth(),
      ) {
        ListItemExpressive(
          headlineText = stringResource(Res.string.settings_stepped_drag_gesture),
          icon = Symbols.MoveSelectionDown,
          supportingText = stringResource(Res.string.settings_stepped_drag_gesture_support),
          switchState = prefs.steppedPartialHistoryView,
          onSwitchChange = updateSteppedPartialHistoryView,
          shape = ListItemExpressiveDefaults.lastShape,
        )
      }
    }
  }
}

@Preview
@Composable
private fun PreviewCalculatorSettingsScreenStandard() {
  var prefs by remember {
    mutableStateOf(
      CalculatorPreferences(
        radianMode = true,
        formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
        middleZero = false,
        acButton = false,
        additionalButtons = false,
        inverseMode = false,
        partialHistoryView = false,
        steppedPartialHistoryView = false,
        initialPartialHistoryView = false,
        openHistoryViewButton = false,
        precision = 3,
        outputFormat = OutputFormat.PLAIN,
        fractionalOutput = true,
      )
    )
  }
  CalculatorSettingsScreen(
    prefs = prefs,
    navigateUpAction = {},
    updatePartialHistoryView = { prefs = prefs.copy(partialHistoryView = it) },
    updateSteppedPartialHistoryView = { prefs = prefs.copy(steppedPartialHistoryView = it) },
    updateFractionalOutput = { prefs = prefs.copy(fractionalOutput = it) },
    updateOpenHistoryViewButton = { prefs = prefs.copy(openHistoryViewButton = it) },
  )
}
