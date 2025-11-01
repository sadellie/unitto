/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2025 Elshan Agaev
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.isEqualTo
import com.sadellie.unitto.core.common.openLink
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.ui.DrawerButton
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ScaffoldWithTopBar
import com.sadellie.unitto.core.ui.TextFieldBox
import com.sadellie.unitto.core.ui.TextFieldRow
import com.sadellie.unitto.feature.bodymass.components.BodyMassResult
import com.sadellie.unitto.feature.bodymass.components.BodyMassTextField
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.body_mass_height
import unitto.core.common.generated.resources.body_mass_imperial
import unitto.core.common.generated.resources.body_mass_metric
import unitto.core.common.generated.resources.body_mass_title
import unitto.core.common.generated.resources.body_mass_weight
import unitto.core.common.generated.resources.common_read_article
import unitto.core.common.generated.resources.unit_centimeter_short
import unitto.core.common.generated.resources.unit_foot_short
import unitto.core.common.generated.resources.unit_inch_short
import unitto.core.common.generated.resources.unit_kilogram_short
import unitto.core.common.generated.resources.unit_pound_short

@Composable
internal fun BodyMassRoute(openDrawer: () -> Unit, viewModel: BodyMassViewModel = koinViewModel()) {
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

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun BodyMassScreen(
  uiState: UIState.Ready,
  updateIsMetric: (Boolean) -> Unit,
  openDrawer: () -> Unit,
) {
  val context = LocalContext.current
  val weightShortLabel =
    stringResource(
      if (uiState.isMetric) Res.string.unit_kilogram_short else Res.string.unit_pound_short
    )

  ScaffoldWithTopBar(
    title = { Text(stringResource(Res.string.body_mass_title)) },
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
      BodyMassInputModeSelector(
        modifier = Modifier.fillMaxWidth(),
        updateIsMetric = updateIsMetric,
        isMetric = uiState.isMetric,
      )

      BodyMassInputBox(
        modifier = Modifier.fillMaxWidth(),
        uiState = uiState,
        weightShortLabel = weightShortLabel,
      )

      AnimatedContent(
        modifier = Modifier.fillMaxWidth(),
        targetState = uiState.result,
        transitionSpec = { (fadeIn() togetherWith fadeOut()) using SizeTransform(false) },
        label = "Body mass value visibility",
      ) { targetState ->
        if (targetState.isEqualTo(KBigDecimal.ZERO)) return@AnimatedContent

        BodyMassResult(
          value = targetState,
          range = uiState.normalWeightRange,
          rangeSuffix = weightShortLabel,
          formatterSymbols = uiState.formatterSymbols,
        )
      }

      OutlinedButton(
        onClick = { openLink(context, "https://sadellie.github.io/unitto/help#body-mass-index") },
        shapes = ButtonDefaults.shapes(),
        contentPadding = ButtonDefaults.SmallContentPadding,
        modifier = Modifier.height(ButtonDefaults.MinHeight),
      ) {
        Text(
          text = stringResource(Res.string.common_read_article),
          style = ButtonDefaults.textStyleFor(ButtonDefaults.MinHeight),
        )
      }
    }
  }
}

@Composable
private fun BodyMassInputBox(modifier: Modifier, uiState: UIState.Ready, weightShortLabel: String) {
  TextFieldBox(modifier = modifier) {
    Crossfade(targetState = uiState.isMetric, label = "Measurement system change") { isMetric ->
      if (isMetric) {
        TextFieldRow {
          BodyMassTextField(
            modifier = Modifier.fillMaxWidth(),
            state = uiState.height1,
            label =
              "${stringResource(Res.string.body_mass_height)}, ${stringResource(Res.string.unit_centimeter_short)}",
            formatterSymbols = uiState.formatterSymbols,
          )
        }
      } else {
        TextFieldRow {
          BodyMassTextField(
            modifier = Modifier.weight(1f),
            state = uiState.height1,
            label =
              "${stringResource(Res.string.body_mass_height)}, ${stringResource(Res.string.unit_foot_short)}",
            formatterSymbols = uiState.formatterSymbols,
          )
          BodyMassTextField(
            modifier = Modifier.weight(1f),
            state = uiState.height2,
            label =
              "${stringResource(Res.string.body_mass_height)}, ${stringResource(Res.string.unit_inch_short)}",
            formatterSymbols = uiState.formatterSymbols,
          )
        }
      }
    }

    TextFieldRow {
      BodyMassTextField(
        modifier = Modifier.fillMaxWidth(),
        state = uiState.weight,
        label = "${stringResource(Res.string.body_mass_weight)}, $weightShortLabel",
        formatterSymbols = uiState.formatterSymbols,
        imeAction = ImeAction.Done,
      )
    }
  }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun BodyMassInputModeSelector(
  modifier: Modifier,
  updateIsMetric: (Boolean) -> Unit,
  isMetric: Boolean,
) {
  Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(Sizes.large)) {
    ToggleButton(
      onCheckedChange = { updateIsMetric(true) },
      checked = isMetric,
      modifier = Modifier.weight(1f),
    ) {
      Text(stringResource(Res.string.body_mass_metric))
    }
    ToggleButton(
      onCheckedChange = { updateIsMetric(false) },
      checked = !isMetric,
      modifier = Modifier.weight(1f),
    ) {
      Text(stringResource(Res.string.body_mass_imperial))
    }
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
        normalWeightRange = KBigDecimal(30) to KBigDecimal(50),
        result = KBigDecimal(18.5),
        formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
      ),
    updateIsMetric = {},
    openDrawer = {},
  )
}
