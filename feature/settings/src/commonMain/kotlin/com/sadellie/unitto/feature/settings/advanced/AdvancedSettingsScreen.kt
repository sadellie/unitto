/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
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

package com.sadellie.unitto.feature.settings.advanced

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.Token2
import com.sadellie.unitto.core.common.collectAsStateWithLifecycleKMP
import com.sadellie.unitto.core.datastore.ConverterPreferences
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.ListItemExpressiveDefaults
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_cancel

@Composable
internal fun AdvancedSettingsRoute(navigateUpAction: () -> Unit) {
  val viewModel: AdvancedSettingsViewModel = koinViewModel()
  when (val prefs = viewModel.prefs.collectAsStateWithLifecycleKMP().value) {
    null -> EmptyScreen()
    else ->
      AdvancedSettingsScreen(
        navigateUpAction = navigateUpAction,
        updateCustomApiUrl = viewModel::updateCustomApiUrl,
        prefs = prefs,
      )
  }
}

@Composable
private fun AdvancedSettingsScreen(
  navigateUpAction: () -> Unit,
  updateCustomApiUrl: (String) -> Unit,
  prefs: ConverterPreferences,
) {
  ScaffoldWithLargeTopBar(
    title = "Advanced",
    navigationIcon = { NavigateUpButton(navigateUpAction) },
  ) { paddingValues ->
    Column(
      modifier =
        Modifier.fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(paddingValues)
          .padding(start = Sizes.large, end = Sizes.large, bottom = Sizes.large)
    ) {
      var showCustomApiUrlDialog by rememberSaveable { mutableStateOf(false) }
      ListItemExpressive(
        headlineContent = { Text("Currency exchange rates API") },
        supportingContent = { Text("Leave empty for default") },
        shape = ListItemExpressiveDefaults.singleShape,
        onClick = { showCustomApiUrlDialog = true },
      )
      if (showCustomApiUrlDialog) {
        val textState = rememberTextFieldState(prefs.customApiUrl)
        AlertDialog(
          title = { Text("Currency exchange rates API") },
          text = {
            OutlinedTextField(
              state = textState,
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier.fillMaxWidth(),
              lineLimits = TextFieldLineLimits.SingleLine,
            )
          },
          dismissButton = {
            TextButton(
              onClick = { showCustomApiUrlDialog = false },
              shapes = ButtonDefaults.shapes(),
            ) {
              Text(stringResource(Res.string.common_cancel))
            }
          },
          confirmButton = {
            Button(
              onClick = {
                updateCustomApiUrl(textState.text.toString())
                showCustomApiUrlDialog = false
              },
              shapes = ButtonDefaults.shapes(),
            ) {
              Text("Save")
            }
          },
          onDismissRequest = { showCustomApiUrlDialog = false },
        )
      }
    }
  }
}

@Preview
@Composable
private fun PreviewAdvancedSettingsScreen() {
  AdvancedSettingsScreen(
    navigateUpAction = {},
    updateCustomApiUrl = {},
    prefs =
      ConverterPreferences(
        formatterSymbols = FormatterSymbols(Token2.Space, Token2.Period, false),
        middleZero = false,
        acButton = true,
        precision = 3,
        outputFormat = OutputFormat.PLAIN,
        formatTime = false,
        sorting = UnitsListSorting.USAGE,
        shownUnitGroups = UnitGroup.entries,
        favoritesOnly = false,
        latestLeftSideUnit = "kilometer",
        latestRightSideUnit = "mile",
        customApiUrl = "",
      ),
  )
}
