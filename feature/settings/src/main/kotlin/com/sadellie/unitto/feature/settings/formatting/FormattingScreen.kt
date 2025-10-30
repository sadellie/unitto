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

package com.sadellie.unitto.feature.settings.formatting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.MAX_SCALE
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.toFormattedString
import com.sadellie.unitto.core.designsystem.icons.symbols.Architecture
import com.sadellie.unitto.core.designsystem.icons.symbols.EMobileData
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.icons.symbols._123
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.designsystem.theme.NumberTypographyUnitto
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.ListItemExpressiveDefaults
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.PagedIsland
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import com.sadellie.unitto.core.ui.Slider
import com.sadellie.unitto.core.ui.textfield.formatExpression
import kotlin.math.ceil
import kotlin.math.roundToInt

@Composable
fun FormattingRoute(
  viewModel: FormattingViewModel = hiltViewModel(),
  navigateUpAction: () -> Unit,
) {
  when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
    null -> EmptyScreen()
    else -> {
      FormattingScreen(
        navigateUpAction = navigateUpAction,
        uiState = uiState,
        onPrecisionChange = viewModel::updatePrecision,
        updateFormatterSymbols = viewModel::updateFormatterSymbols,
        onOutputFormatChange = viewModel::updateOutputFormat,
      )
    }
  }
}

@Composable
fun FormattingScreen(
  navigateUpAction: () -> Unit,
  uiState: FormattingUIState,
  onPrecisionChange: (Int) -> Unit,
  updateFormatterSymbols: (grouping: String, fractional: String) -> Unit,
  onOutputFormatChange: (Int) -> Unit,
) {
  ScaffoldWithLargeTopBar(
    title = stringResource(R.string.settings_formatting),
    navigationIcon = { NavigateUpButton(navigateUpAction) },
  ) { paddingValues ->
    Column(
      modifier =
        Modifier.verticalScroll(rememberScrollState())
          .padding(paddingValues)
          .padding(horizontal = Sizes.large),
      verticalArrangement = ListItemExpressiveDefaults.ListArrangement,
    ) {
      val precisions: ClosedFloatingPointRange<Float> = 0f..MAX_SCALE_ALIAS
      val resources = LocalResources.current
      var scale by rememberSaveable(uiState.precision) { mutableIntStateOf(uiState.precision) }
      val precisionText: String by
        remember(scale, uiState.formatterSymbols) {
          derivedStateOf {
            return@derivedStateOf if (scale >= precisions.endInclusive) {
              resources.getString(
                R.string.settings_precision_max,
                MAX_SCALE.toString().formatExpression(uiState.formatterSymbols),
              )
            } else {
              scale.toString()
            }
          }
        }

      PreviewBox(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        scale = scale,
        outputFormat = uiState.outputFormat,
        formatterSymbols = uiState.formatterSymbols,
      )

      val supportingItemModifier = Modifier.fillMaxWidth()

      // Precision
      ListItemExpressive(
        shape = ListItemExpressiveDefaults.firstShape,
        leadingContent = {
          Icon(Symbols.Architecture, stringResource(R.string.settings_precision))
        },
        headlineContent = {
          Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
          ) {
            Text(stringResource(R.string.settings_precision))
            Text(precisionText)
          }
        },
        supportingContent = { Text(stringResource(R.string.settings_precision_support)) },
        secondaryContent = {
          Slider(
            modifier = supportingItemModifier,
            value = scale.toFloat(),
            valueRange = precisions,
            onValueChange = { scale = it.roundToInt() },
            onValueChangeFinished = { onPrecisionChange(scale) },
            steps = SCALE_STEPS,
          )
        },
      )

      // Thousands separator
      ListItemExpressive(
        shape = ListItemExpressiveDefaults.middleShape,
        leadingContent = {
          Icon(Symbols._123, stringResource(R.string.settings_thousands_separator))
        },
        headlineContent = { Text(stringResource(R.string.settings_thousands_separator)) },
        secondaryContent = {
          GroupingSymbolSelector(
            modifier = supportingItemModifier,
            updateFormatterSymbols = updateFormatterSymbols,
            formatterSymbols = uiState.formatterSymbols,
          )
        },
      )

      // Decimal separator
      AnimatedVisibility(
        visible = uiState.formatterSymbols.grouping == Token.SPACE,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
      ) {
        ListItemExpressive(
          shape = ListItemExpressiveDefaults.middleShape,
          leadingContent = { Spacer(Modifier.size(24.dp)) }, // empty icon spacing
          headlineContent = { Text(stringResource(R.string.settings_decimal_separator)) },
          secondaryContent = {
            FractionalSymbolSelector(
              modifier = supportingItemModifier,
              updateFormatterSymbols = updateFormatterSymbols,
              formatterSymbols = uiState.formatterSymbols,
            )
          },
        )
      }

      // Output format
      ListItemExpressive(
        shape = ListItemExpressiveDefaults.lastShape,
        leadingContent = {
          Icon(Symbols.EMobileData, stringResource(R.string.settings_exponential_notation))
        },
        headlineContent = { Text(stringResource(R.string.settings_exponential_notation)) },
        supportingContent = {
          Text(stringResource(R.string.settings_exponential_notation_support))
        },
        secondaryContent = {
          OutputFormatSelector(
            modifier = supportingItemModifier,
            onOutputFormatChange = onOutputFormatChange,
            outputFormat = uiState.outputFormat,
          )
        },
      )
    }
  }
}

@Composable
private fun PreviewBox(
  modifier: Modifier,
  scale: Int,
  outputFormat: Int,
  formatterSymbols: FormatterSymbols,
) {
  PagedIsland(modifier = modifier, pageCount = 2) { currentPage ->
    val preview =
      when (currentPage) {
          0 -> "123456.${"789123456".repeat(ceil(scale.toDouble() / 9.0).toInt())}"
          else -> "0.${"1".padStart(scale, '0')}"
        }
        .toBigDecimalOrNull()
        ?.toFormattedString(scale, outputFormat)
        ?.formatExpression(formatterSymbols) ?: ""

    Text(
      text = preview,
      style = NumberTypographyUnitto.displayMedium,
      maxLines = 1,
      modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
      textAlign = TextAlign.End,
      color = MaterialTheme.colorScheme.onSecondaryContainer,
    )
  }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun FractionalSymbolSelector(
  modifier: Modifier,
  updateFormatterSymbols: (grouping: String, fractional: String) -> Unit,
  formatterSymbols: FormatterSymbols,
) {
  Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(Sizes.small)) {
    ToggleButton(
      checked = formatterSymbols.fractional == Token.PERIOD,
      onCheckedChange = { updateFormatterSymbols(Token.SPACE, Token.PERIOD) },
      modifier = Modifier.weight(1f),
    ) {
      Text(stringResource(R.string.settings_period))
    }
    ToggleButton(
      checked = formatterSymbols.fractional == Token.COMMA,
      onCheckedChange = { updateFormatterSymbols(Token.SPACE, Token.COMMA) },
      modifier = Modifier.weight(1f),
    ) {
      Text(stringResource(R.string.common_comma))
    }
  }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun GroupingSymbolSelector(
  modifier: Modifier,
  updateFormatterSymbols: (grouping: String, fractional: String) -> Unit,
  formatterSymbols: FormatterSymbols,
) {
  Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(Sizes.small)) {
    ToggleButton(
      checked = formatterSymbols.grouping == Token.SPACE,
      onCheckedChange = { updateFormatterSymbols(Token.SPACE, formatterSymbols.fractional) },
      modifier = Modifier.weight(1f),
    ) {
      Text(stringResource(R.string.settings_space))
    }
    ToggleButton(
      checked = formatterSymbols.grouping == Token.PERIOD,
      onCheckedChange = { updateFormatterSymbols(Token.PERIOD, Token.COMMA) },
      modifier = Modifier.weight(1f),
    ) {
      Text(stringResource(R.string.settings_period))
    }
    ToggleButton(
      checked = formatterSymbols.grouping == Token.COMMA,
      onCheckedChange = { updateFormatterSymbols(Token.COMMA, Token.PERIOD) },
      modifier = Modifier.weight(1f),
    ) {
      Text(stringResource(R.string.common_comma))
    }
  }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun OutputFormatSelector(
  modifier: Modifier,
  onOutputFormatChange: (Int) -> Unit,
  outputFormat: Int,
) {
  Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(Sizes.small)) {
    ToggleButton(
      onCheckedChange = { onOutputFormatChange(OutputFormat.ALLOW_ENGINEERING) },
      checked = OutputFormat.ALLOW_ENGINEERING == outputFormat,
      modifier = Modifier.weight(1f),
    ) {
      Text(stringResource(R.string.settings_auto))
    }
    ToggleButton(
      onCheckedChange = { onOutputFormatChange(OutputFormat.FORCE_ENGINEERING) },
      checked = OutputFormat.FORCE_ENGINEERING == outputFormat,
      modifier = Modifier.weight(1f),
    ) {
      Text(stringResource(R.string.common_enabled))
    }
    ToggleButton(
      onCheckedChange = { onOutputFormatChange(OutputFormat.PLAIN) },
      checked = OutputFormat.PLAIN == outputFormat,
      modifier = Modifier.weight(1f),
    ) {
      Text(stringResource(R.string.common_disabled))
    }
  }
}

// When setting scale to this number it will be treated as MAX_SCALE
internal const val MAX_SCALE_ALIAS = 16f
private const val SCALE_STEPS = MAX_SCALE_ALIAS.toInt() - 1

@Preview
@Composable
private fun PreviewFormattingScreen() {
  var currentPrecision by remember { mutableIntStateOf(6) }
  var currentFormatterSymbols by remember {
    mutableStateOf(FormatterSymbols(Token.SPACE, Token.PERIOD))
  }
  var currentOutputFormat by remember { mutableIntStateOf(OutputFormat.PLAIN) }

  FormattingScreen(
    uiState =
      FormattingUIState(
        precision = currentPrecision,
        outputFormat = currentOutputFormat,
        formatterSymbols = currentFormatterSymbols,
      ),
    onPrecisionChange = { currentPrecision = it },
    updateFormatterSymbols = { grouping, fractional ->
      currentFormatterSymbols = FormatterSymbols(grouping, fractional)
    },
    onOutputFormatChange = { currentOutputFormat = it },
    navigateUpAction = {},
  )
}
