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

package com.sadellie.unitto.feature.converter

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.isEqualTo
import com.sadellie.unitto.core.common.isExpression
import com.sadellie.unitto.core.common.toFormattedString
import com.sadellie.unitto.core.data.converter.ConverterResult
import com.sadellie.unitto.core.data.converter.CurrencyRateUpdateState
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.designsystem.LocalLocale
import com.sadellie.unitto.core.designsystem.icons.symbols.SwapHoriz
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.ui.ColumnWithConstraints
import com.sadellie.unitto.core.ui.PortraitLandscape
import com.sadellie.unitto.core.ui.datetime.formatDateWeekDayMonthYear
import com.sadellie.unitto.core.ui.textfield.ExpressionTextField
import com.sadellie.unitto.core.ui.textfield.NumberBaseTextField
import com.sadellie.unitto.core.ui.textfield.SimpleTextField
import com.sadellie.unitto.core.ui.textfield.addBracket
import com.sadellie.unitto.core.ui.textfield.addTokens
import com.sadellie.unitto.core.ui.textfield.deleteTokens
import com.sadellie.unitto.feature.converter.components.DefaultKeyboard
import com.sadellie.unitto.feature.converter.components.NumberBaseKeyboard
import com.sadellie.unitto.feature.converter.components.UnitSelectionButton
import java.math.BigDecimal
import java.time.LocalDate
import java.util.Locale
import kotlinx.coroutines.delay

@Composable
internal fun ConverterDefault(
  modifier: Modifier,
  uiState: ConverterUIState.Default,
  navigateToLeftScreen: (unitFromId: String, group: UnitGroup) -> Unit,
  swapUnits: (String, String) -> Unit,
  navigateToRightScreen:
    (
      unitFromId: String, unitToId: String, group: UnitGroup, input1: String, input2: String,
    ) -> Unit,
  convert: () -> Unit,
) {
  var focusedOnInput1 by rememberSaveable { mutableStateOf(true) }
  val connection by connectivityState()

  LaunchedEffect(connection) {
    if (
      connection is ConnectionState.Available &&
        uiState.result is ConverterResult.Error &&
        uiState.unitFrom.group == UnitGroup.CURRENCY
    )
      delay(RETRY_CURRENCY_UPDATE_DEBOUNCE_MS)
    convert()
  }

  PortraitLandscape(
    modifier = modifier.fillMaxSize(),
    content1 = { contentModifier ->
      ColumnWithConstraints(modifier = contentModifier) { boxWithConstraintsScope ->
        val textFieldModifier = Modifier.fillMaxWidth().weight(2f)

        CurrencyUpdateStatusBar(
          modifier = Modifier.align(Alignment.CenterHorizontally),
          currencyRateUpdateState = uiState.currencyRateUpdateState,
          unitFromGroup = uiState.unitFrom.group,
        )

        if (uiState.unitFrom.id == UnitID.foot) {
          FootInchInputTextFields(
            textFieldModifier = textFieldModifier,
            input1 = uiState.input1,
            formatterSymbols = uiState.formatterSymbols,
            onFocusedOnInput1Changed = { focusedOnInput1 = it },
            input2 = uiState.input2,
          )
        } else {
          ExpressionTextField(
            modifier = textFieldModifier,
            state = uiState.input1,
            minRatio = 0.7f,
            formatterSymbols = uiState.formatterSymbols,
            placeholder = Token.Digit.DIGIT_0,
          )
          CalculationResultTextField(
            modifier = Modifier.weight(1f),
            input1 = uiState.input1.text,
            result = uiState.result,
            scale = uiState.scale,
            outputFormat = uiState.outputFormat,
            formatterSymbols = uiState.formatterSymbols,
          )
          AnimatedUnitShortName(stringResource(uiState.unitFrom.shortName))
        }

        ConverterResultTextField(
          modifier = textFieldModifier,
          result = uiState.result,
          scale = uiState.scale,
          outputFormat = uiState.outputFormat,
          formatterSymbols = uiState.formatterSymbols,
        )
        AnimatedUnitShortName(stringResource(uiState.unitTo.shortName))

        Spacer(modifier = Modifier.height(boxWithConstraintsScope.maxHeight * SPACER_HEIGHT_FACTOR))

        UnitSelectionButtons(
          unitFromLabel = stringResource(uiState.unitFrom.displayName),
          unitToLabel = stringResource(uiState.unitTo.displayName),
          swapUnits = { swapUnits(uiState.unitTo.id, uiState.unitFrom.id) },
          navigateToLeftScreen = {
            navigateToLeftScreen(uiState.unitFrom.id, uiState.unitFrom.group)
          },
          navigateToRightScreen = {
            navigateToRightScreen(
              uiState.unitFrom.id,
              uiState.unitTo.id,
              uiState.unitFrom.group,
              uiState.input1.text.toString(),
              uiState.input2.text.toString(),
            )
          },
        )
      }
    },
    content2 = { modifier2 ->
      DefaultKeyboard(
        modifier = modifier2,
        onAddTokenClick = {
          if (focusedOnInput1) uiState.input1.addTokens(it) else uiState.input2.addTokens(it)
        },
        onClearClick = {
          uiState.input1.clearText()
          uiState.input2.clearText()
        },
        onDeleteClick = {
          if (focusedOnInput1) uiState.input1.deleteTokens() else uiState.input2.deleteTokens()
        },
        onBracketsClick = {
          if (focusedOnInput1) uiState.input1.addBracket() else uiState.input2.addBracket()
        },
        fractional = uiState.formatterSymbols.fractional,
        middleZero = uiState.middleZero,
        acButton = uiState.acButton,
      )
    },
  )
}

@Composable
internal fun NumberBase(
  modifier: Modifier,
  uiState: ConverterUIState.NumberBase,
  navigateToLeftScreen: (unitFromId: String, group: UnitGroup) -> Unit,
  swapUnits: (String, String) -> Unit,
  navigateToRightScreen:
    (unitFromId: String, unitToId: String, group: UnitGroup, input1: String, input2: String) -> Unit,
) {
  PortraitLandscape(
    modifier = modifier.fillMaxSize(),
    content1 = { contentModifier ->
      ColumnWithConstraints(modifier = contentModifier) {
        val textFieldModifier = Modifier.weight(2f)

        NumberBaseTextField(
          modifier = textFieldModifier,
          minRatio = 0.7f,
          placeholder = Token.Digit.DIGIT_0,
          state = uiState.input,
        )
        AnimatedUnitShortName(stringResource(uiState.unitFrom.shortName))

        ConverterResultTextField(modifier = textFieldModifier, result = uiState.result)
        AnimatedUnitShortName(stringResource(uiState.unitTo.shortName))

        Spacer(modifier = Modifier.height(it.maxHeight * SPACER_HEIGHT_FACTOR))

        UnitSelectionButtons(
          unitFromLabel = stringResource(uiState.unitFrom.displayName),
          unitToLabel = stringResource(uiState.unitTo.displayName),
          swapUnits = { swapUnits(uiState.unitTo.id, uiState.unitFrom.id) },
          navigateToLeftScreen = {
            navigateToLeftScreen(uiState.unitFrom.id, uiState.unitFrom.group)
          },
          navigateToRightScreen = {
            navigateToRightScreen(
              uiState.unitFrom.id,
              uiState.unitTo.id,
              uiState.unitFrom.group,
              uiState.input.text.toString(),
              // Second input is used in foot/inch only
              "",
            )
          },
        )
      }
    },
    content2 = { modifier2 ->
      NumberBaseKeyboard(
        modifier = modifier2,
        onAddTokenClick = { uiState.input.addTokens(it) },
        onDeleteClick = { uiState.input.deleteTokens() },
        onClearClick = { uiState.input.clearText() },
      )
    },
  )
}

@Composable
private fun CalculationResultTextField(
  modifier: Modifier,
  input1: CharSequence,
  result: ConverterResult,
  scale: Int,
  outputFormat: Int,
  formatterSymbols: FormatterSymbols,
) {
  val showCalculation =
    remember(input1, result) {
      if (input1.toString().isExpression() && result is ConverterResult.Default) {
        return@remember !result.calculation.isEqualTo(BigDecimal.ZERO)
      }
      false
    }

  AnimatedVisibility(
    visible = showCalculation,
    modifier = modifier,
    enter = expandVertically(clip = false),
    exit = shrinkVertically(clip = false),
  ) {
    val calculationTextField =
      remember(result, scale, outputFormat) {
        val text =
          if (result is ConverterResult.Default) {
            result.calculation.toFormattedString(scale, outputFormat)
          } else {
            ""
          }
        TextFieldState(text)
      }
    ExpressionTextField(
      modifier = Modifier,
      state = calculationTextField,
      minRatio = 0.7f,
      textColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
      formatterSymbols = formatterSymbols,
      readOnly = true,
    )
  }
}

@Composable
private fun FootInchInputTextFields(
  textFieldModifier: Modifier,
  input1: TextFieldState,
  formatterSymbols: FormatterSymbols,
  onFocusedOnInput1Changed: (Boolean) -> Unit,
  input2: TextFieldState,
) {
  Row(modifier = textFieldModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
      ExpressionTextField(
        modifier = Modifier.fillMaxWidth().weight(1f),
        state = input1,
        minRatio = 0.7f,
        formatterSymbols = formatterSymbols,
        placeholder = Token.Digit.DIGIT_0,
      )
      AnimatedUnitShortName(stringResource(R.string.unit_foot_short))
    }

    VerticalDivider()

    Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
      ExpressionTextField(
        modifier =
          Modifier.fillMaxWidth().weight(1f).onFocusEvent { state ->
            onFocusedOnInput1Changed(!state.hasFocus)
          },
        state = input2,
        minRatio = 0.7f,
        formatterSymbols = formatterSymbols,
        placeholder = Token.Digit.DIGIT_0,
      )
      AnimatedUnitShortName(stringResource(R.string.unit_inch_short))
    }
  }
}

@Composable
private fun CurrencyUpdateStatusBar(
  modifier: Modifier,
  currencyRateUpdateState: CurrencyRateUpdateState,
  unitFromGroup: UnitGroup,
) {
  val locale: Locale = LocalLocale.current
  val lastUpdate by
    remember(currencyRateUpdateState, unitFromGroup) {
      derivedStateOf {
        if (
          currencyRateUpdateState is CurrencyRateUpdateState.Ready &&
            unitFromGroup == UnitGroup.CURRENCY
        ) {
          currencyRateUpdateState.date.formatDateWeekDayMonthYear(locale)
        } else {
          null
        }
      }
    }

  AnimatedVisibility(
    visible = lastUpdate != null,
    enter = expandVertically() + fadeIn(),
    exit = shrinkVertically() + fadeOut(),
  ) {
    Text(
      modifier =
        modifier
          .clip(RoundedCornerShape(16.dp))
          .background(MaterialTheme.colorScheme.inverseOnSurface)
          .fillMaxWidth(),
      text = lastUpdate.orEmpty(),
      minLines = 1,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      textAlign = TextAlign.Center,
    )
  }
}

@Composable
private fun ConverterResultTextField(
  modifier: Modifier,
  result: ConverterResult,
  scale: Int = 0,
  outputFormat: Int = OutputFormat.PLAIN,
  formatterSymbols: FormatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
) {
  when (result) {
    is ConverterResult.Default -> {
      val state =
        remember(result, scale, outputFormat) {
          TextFieldState(result.value.toFormattedString(scale, outputFormat))
        }
      ExpressionTextField(
        modifier = modifier,
        state = state,
        minRatio = 0.7f,
        formatterSymbols = formatterSymbols,
        readOnly = true,
      )
    }
    is ConverterResult.NumberBase -> {
      val state = remember(result) { TextFieldState(result.value.uppercase()) }
      NumberBaseTextField(modifier = modifier, state = state, minRatio = 0.7f, readOnly = true)
    }
    is ConverterResult.Time -> {
      val mContext = LocalContext.current
      val state =
        remember(result, formatterSymbols) {
          TextFieldState(result.format(mContext, formatterSymbols))
        }
      SimpleTextField(modifier = modifier, state = state, minRatio = 0.7f, readOnly = true)
    }
    is ConverterResult.FootInch -> {
      val mContext = LocalContext.current
      val state =
        remember(result, scale, outputFormat, formatterSymbols) {
          TextFieldState(result.format(mContext, scale, outputFormat, formatterSymbols))
        }
      SimpleTextField(modifier = modifier, state = state, minRatio = 0.7f, readOnly = true)
    }
    is ConverterResult.Loading ->
      SimpleTextField(
        modifier = modifier,
        state = TextFieldState(stringResource(R.string.common_loading)),
        minRatio = 0.7f,
        readOnly = true,
      )
    is ConverterResult.Error.DivideByZeroError ->
      SimpleTextField(
        modifier = modifier,
        state = TextFieldState(stringResource(R.string.calculator_divide_by_zero_error)),
        minRatio = 0.7f,
        readOnly = true,
        textColor = MaterialTheme.colorScheme.error,
      )
    is ConverterResult.Error.CurrencyError ->
      SimpleTextField(
        modifier = modifier,
        state = TextFieldState(stringResource(R.string.common_error)),
        minRatio = 0.7f,
        readOnly = true,
        textColor = MaterialTheme.colorScheme.error,
      )
  }
}

@Composable
private fun AnimatedUnitShortName(label: String = stringResource(R.string.common_loading)) {
  AnimatedContent(
    modifier = Modifier.fillMaxWidth(),
    targetState = label,
    transitionSpec = {
      // Enter animation
      (expandHorizontally(clip = false, expandFrom = Alignment.Start) + fadeIn() togetherWith
        fadeOut()) using SizeTransform(clip = false)
    },
    label = "Animated short name from",
  ) { value ->
    Text(text = value, style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.End))
  }
}

@Composable
private fun UnitSelectionButtons(
  unitFromLabel: String = stringResource(R.string.common_loading),
  unitToLabel: String = stringResource(R.string.common_loading),
  swapUnits: () -> Unit = {},
  navigateToLeftScreen: () -> Unit = {},
  navigateToRightScreen: () -> Unit = {},
) {
  var swapped by remember { mutableStateOf(false) }
  val swapButtonRotation: Float by
    animateFloatAsState(
      targetValue = if (swapped) 0f else 180f,
      animationSpec = tween(easing = FastOutSlowInEasing),
      label = "Swap button rotation",
    )

  Row(verticalAlignment = Alignment.CenterVertically) {
    UnitSelectionButton(
      modifier = Modifier.fillMaxWidth().weight(1f),
      label = unitFromLabel,
      onClick = navigateToLeftScreen,
    )
    IconButton(
      onClick = {
        swapUnits()
        swapped = !swapped
      }
    ) {
      Icon(
        modifier = Modifier.rotate(swapButtonRotation),
        imageVector = Symbols.SwapHoriz,
        contentDescription = stringResource(R.string.converter_swap_units_description),
      )
    }
    UnitSelectionButton(
      modifier = Modifier.fillMaxWidth().weight(1f),
      label = unitToLabel,
      onClick = navigateToRightScreen,
    )
  }
}

@Preview
@Composable
private fun PreviewCurrencyUpdateStatusBar() {
  CurrencyUpdateStatusBar(
    modifier = Modifier,
    currencyRateUpdateState = CurrencyRateUpdateState.Ready(LocalDate.now()),
    unitFromGroup = UnitGroup.CURRENCY,
  )
}

private const val SPACER_HEIGHT_FACTOR = 0.03f
private const val RETRY_CURRENCY_UPDATE_DEBOUNCE_MS = 1_000L
