/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.datastore.CalculatorPreferences
import com.sadellie.unitto.core.designsystem.icons.iconpack.Fraction
import com.sadellie.unitto.core.designsystem.icons.iconpack.IconPack
import com.sadellie.unitto.core.designsystem.icons.symbols.AddRowBelow
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListArrangement
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import com.sadellie.unitto.core.ui.lastShapes
import com.sadellie.unitto.core.ui.middleShapes
import dev.zacsweers.metrox.viewmodel.metroViewModel
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.calculator_title
import unitto.core.common.generated.resources.settings_constant_calculation
import unitto.core.common.generated.resources.settings_constant_calculation_support
import unitto.core.common.generated.resources.settings_fractional_output
import unitto.core.common.generated.resources.settings_fractional_output_support

@Composable
internal fun CalculatorSettingsRoute(navigateUpAction: () -> Unit) {
  val viewModel: CalculatorSettingsViewModel = metroViewModel()
  when (val prefs = viewModel.prefs.collectAsStateWithLifecycle().value) {
    null -> EmptyScreen()
    else -> {
      CalculatorSettingsScreen(
        prefs = prefs,
        navigateUpAction = navigateUpAction,
        updateFractionalOutput = viewModel::updateFractionalOutput,
        updateConstantCalculation = viewModel::updateConstantCalculation,
      )
    }
  }
}

@Composable
private fun CalculatorSettingsScreen(
  prefs: CalculatorPreferences,
  navigateUpAction: () -> Unit,
  updateFractionalOutput: (Boolean) -> Unit,
  updateConstantCalculation: (Boolean) -> Unit,
) {
  ScaffoldWithLargeTopBar(
    title = stringResource(Res.string.calculator_title),
    navigationIcon = { NavigateUpButton(navigateUpAction) },
  ) { padding ->
    Column(
      modifier =
        Modifier.padding(padding)
          .padding(start = Sizes.large, end = Sizes.large, bottom = Sizes.large),
      verticalArrangement = ListItemDefaults.ListArrangement,
    ) {
      ListItemExpressive(
        headlineText = stringResource(Res.string.settings_fractional_output),
        icon = IconPack.Fraction,
        supportingText = stringResource(Res.string.settings_fractional_output_support),
        switchState = prefs.fractionalOutput,
        onSwitchChange = updateFractionalOutput,
        shapes = ListItemDefaults.middleShapes,
      )

      ListItemExpressive(
        headlineText = stringResource(Res.string.settings_constant_calculation),
        icon = Symbols.AddRowBelow,
        supportingText = stringResource(Res.string.settings_constant_calculation_support),
        switchState = prefs.constantCalculation,
        onSwitchChange = updateConstantCalculation,
        shapes = ListItemDefaults.lastShapes,
      )
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
        additionalButtons = false,
        inverseMode = false,
        initialPartialHistoryView = false,
        fractionalOutput = true,
        constantCalculation = false,
      )
    )
  }
  CalculatorSettingsScreen(
    prefs = prefs,
    navigateUpAction = {},
    updateFractionalOutput = { prefs = prefs.copy(fractionalOutput = it) },
    updateConstantCalculation = { prefs = prefs.copy(constantCalculation = it) },
  )
}
