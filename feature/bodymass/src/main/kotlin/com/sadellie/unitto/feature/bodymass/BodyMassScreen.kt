/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.feature.bodymass

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.isEqualTo
import com.sadellie.unitto.core.common.openLink
import com.sadellie.unitto.core.designsystem.shapes.M3Shapes
import com.sadellie.unitto.core.ui.DrawerButton
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ScaffoldWithTopBar
import com.sadellie.unitto.core.ui.SegmentedButton
import com.sadellie.unitto.core.ui.SegmentedButtonsRow
import com.sadellie.unitto.feature.bodymass.components.BodyMassResult
import com.sadellie.unitto.feature.bodymass.components.BodyMassTextField
import java.math.BigDecimal

@Composable
internal fun BodyMassRoute(openDrawer: () -> Unit, viewModel: BodyMassViewModel = hiltViewModel()) {
  LaunchedEffect(Unit) { viewModel.observeInput() }

  when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
    UIState.Loading -> EmptyScreen()
    is UIState.Ready ->
      BodyMassScreen(
        uiState = uiState,
        updateIsMetric = viewModel::updateIsMetric,
        openDrawer = openDrawer,
      )
  }
}

@Composable
private fun BodyMassScreen(
  uiState: UIState.Ready,
  updateIsMetric: (Boolean) -> Unit,
  openDrawer: () -> Unit,
) {
  val mContext = LocalContext.current
  val weightShortLabel =
    remember(uiState.isMetric) {
      mContext.resources.getString(
        if (uiState.isMetric) R.string.unit_kilogram_short else R.string.unit_pound_short
      )
    }

  ScaffoldWithTopBar(
    title = { Text(stringResource(R.string.body_mass_title)) },
    navigationIcon = { DrawerButton(openDrawer) },
  ) { paddingValues ->
    Column(
      modifier =
        Modifier.verticalScroll(rememberScrollState())
          .padding(paddingValues)
          .padding(16.dp)
          .fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      BodyMassInputModeSelector(updateIsMetric, uiState.isMetric)

      BodyMassInputBox(uiState, weightShortLabel)

      AnimatedContent(
        modifier = Modifier.fillMaxWidth(),
        targetState = uiState.result,
        transitionSpec = { (fadeIn() togetherWith fadeOut()) using SizeTransform(false) },
        label = "Body mass value visibility",
      ) { targetState ->
        if (targetState.isEqualTo(BigDecimal.ZERO)) return@AnimatedContent

        BodyMassResult(
          value = targetState,
          range = uiState.normalWeightRange,
          rangeSuffix = weightShortLabel,
          formatterSymbols = uiState.formatterSymbols,
        )
      }

      ElevatedButton(
        onClick = { openLink(mContext, "https://sadellie.github.io/unitto/help#body-mass-index") }
      ) {
        Text(text = stringResource(R.string.common_read_article))
      }
    }
  }
}

@Composable
private fun BodyMassInputBox(uiState: UIState.Ready, weightShortLabel: String) {
  Column(
    modifier =
      Modifier.fillMaxWidth()
        .background(MaterialTheme.colorScheme.secondaryContainer, M3Shapes.ExtraLarge)
        .padding(M3Shapes.large, 24.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Crossfade(targetState = uiState.isMetric, label = "Measurement system change") { isMetric ->
      if (isMetric) {
        BodyMassTextField(
          modifier = Modifier.fillMaxWidth(),
          state = uiState.height1,
          label =
            "${stringResource(R.string.body_mass_height)}, ${stringResource(R.string.unit_centimeter_short)}",
          formatterSymbols = uiState.formatterSymbols,
        )
      } else {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          BodyMassTextField(
            modifier = Modifier.weight(1f),
            state = uiState.height1,
            label =
              "${stringResource(R.string.body_mass_height)}, ${stringResource(R.string.unit_foot_short)}",
            formatterSymbols = uiState.formatterSymbols,
          )
          BodyMassTextField(
            modifier = Modifier.weight(1f),
            state = uiState.height2,
            label =
              "${stringResource(R.string.body_mass_height)}, ${stringResource(R.string.unit_inch_short)}",
            formatterSymbols = uiState.formatterSymbols,
          )
        }
      }
    }
    BodyMassTextField(
      modifier = Modifier.fillMaxWidth(),
      state = uiState.weight,
      label = "${stringResource(R.string.body_mass_weight)}, $weightShortLabel",
      formatterSymbols = uiState.formatterSymbols,
      imeAction = ImeAction.Done,
    )
  }
}

@Composable
private fun BodyMassInputModeSelector(updateIsMetric: (Boolean) -> Unit, isMetric: Boolean) {
  SegmentedButtonsRow(modifier = Modifier.fillMaxWidth()) {
    SegmentedButton(
      label = stringResource(R.string.body_mass_metric),
      onClick = { updateIsMetric(true) },
      selected = isMetric,
      modifier = Modifier.weight(1f),
    )
    SegmentedButton(
      label = stringResource(R.string.body_mass_imperial),
      onClick = { updateIsMetric(false) },
      selected = !isMetric,
      modifier = Modifier.weight(1f),
    )
  }
}

@Preview
@Composable
fun PreviewBodyMassScreen() {
  BodyMassScreen(
    uiState =
      UIState.Ready(
        isMetric = false,
        height1 = remember { TextFieldState() },
        height2 = remember { TextFieldState() },
        weight = remember { TextFieldState() },
        normalWeightRange = BigDecimal(30) to BigDecimal(50),
        result = BigDecimal(18.5),
        formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
      ),
    updateIsMetric = {},
    openDrawer = {},
  )
}
