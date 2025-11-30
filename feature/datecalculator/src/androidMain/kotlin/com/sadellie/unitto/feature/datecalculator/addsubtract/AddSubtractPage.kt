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

package com.sadellie.unitto.feature.datecalculator.addsubtract

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.designsystem.ExpressivePreview
import com.sadellie.unitto.core.designsystem.icons.symbols.Add
import com.sadellie.unitto.core.designsystem.icons.symbols.Event
import com.sadellie.unitto.core.designsystem.icons.symbols.Remove
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.TextFieldBox
import com.sadellie.unitto.core.ui.TextFieldRow
import com.sadellie.unitto.feature.datecalculator.ZonedDateTimeUtils
import com.sadellie.unitto.feature.datecalculator.components.DateTimeBlock
import com.sadellie.unitto.feature.datecalculator.components.DateTimeDialogs
import com.sadellie.unitto.feature.datecalculator.components.DialogState
import com.sadellie.unitto.feature.datecalculator.components.TimeUnitTextField
import java.time.ZonedDateTime
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.date_calculator_add
import unitto.core.common.generated.resources.date_calculator_create_event
import unitto.core.common.generated.resources.date_calculator_days
import unitto.core.common.generated.resources.date_calculator_end
import unitto.core.common.generated.resources.date_calculator_hours
import unitto.core.common.generated.resources.date_calculator_minutes
import unitto.core.common.generated.resources.date_calculator_months
import unitto.core.common.generated.resources.date_calculator_start
import unitto.core.common.generated.resources.date_calculator_subtract
import unitto.core.common.generated.resources.date_calculator_years

@Composable
internal fun AddSubtractPage(viewModel: AddSubtractViewModel = koinViewModel()) {
  LaunchedEffect(Unit) { viewModel.observeInput() }

  when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
    AddSubtractUIState.Loading -> EmptyScreen()
    is AddSubtractUIState.Ready ->
      AddSubtractView(
        uiState = uiState,
        updateStart = viewModel::updateStart,
        updateAddition = viewModel::updateAddition,
      )
  }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun AddSubtractView(
  uiState: AddSubtractUIState.Ready,
  updateStart: (ZonedDateTime) -> Unit,
  updateAddition: (Boolean) -> Unit,
) {
  val mContext = LocalContext.current
  var dialogState by remember { mutableStateOf(DialogState.NONE) }
  val showResult = remember(uiState.start, uiState.result) { uiState.start != uiState.result }

  Column(
    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(Sizes.large),
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    AnimatedContent(
      targetState = showResult,
      label = "Reveal result",
      transitionSpec = { fadeIn() togetherWith fadeOut() using SizeTransform() },
    ) { show ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Sizes.small),
      ) {
        DateTimeBlock(
          modifier = Modifier.weight(1f),
          title = stringResource(Res.string.date_calculator_start),
          onTimeClick = { dialogState = DialogState.FROM_TIME },
          onDateClick = { dialogState = DialogState.FROM_DATE },
          onLongClick = { updateStart(ZonedDateTimeUtils.nowWithMinutes()) },
          dateTime = uiState.start,
        )

        if (show) {
          DateTimeBlock(
            modifier = Modifier.weight(1f),
            title = stringResource(Res.string.date_calculator_end),
            dateTime = uiState.result,
          )
        }
      }
    }

    OperationSelector(
      modifier = Modifier.fillMaxWidth(),
      updateAddition = updateAddition,
      addition = uiState.addition,
    )

    InputTextFieldsBox(
      modifier = Modifier.fillMaxWidth(),
      formatterSymbols = uiState.formatterSymbols,
      years = uiState.years,
      months = uiState.months,
      days = uiState.days,
      hours = uiState.hours,
      minutes = uiState.minutes,
    )

    Button(
      onClick = { mContext.createEvent(uiState.start, uiState.result) },
      shapes = ButtonDefaults.shapes(),
      modifier = Modifier.fillMaxWidth().height(ButtonDefaults.MediumContainerHeight),
      contentPadding = ButtonDefaults.TextButtonWithIconContentPadding,
    ) {
      Icon(
        imageVector = Symbols.Event,
        contentDescription = stringResource(Res.string.date_calculator_create_event),
        modifier = Modifier.size(ButtonDefaults.MediumIconSize),
      )
      Spacer(Modifier.size(ButtonDefaults.MediumIconSpacing))
      Text(
        text = stringResource(Res.string.date_calculator_create_event),
        style = ButtonDefaults.textStyleFor(ButtonDefaults.MediumContainerHeight),
      )
    }
  }

  DateTimeDialogs(
    dialogState = dialogState,
    updateDialogState = { dialogState = it },
    date = uiState.start,
    updateDate = updateStart,
    timeState = DialogState.FROM_TIME,
    dateState = DialogState.FROM_DATE,
  )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun OperationSelector(
  modifier: Modifier,
  updateAddition: (Boolean) -> Unit,
  addition: Boolean,
) {
  Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(Sizes.large)) {
    ToggleButton(
      checked = addition,
      onCheckedChange = { updateAddition(true) },
      shapes = ToggleButtonDefaults.shapes(),
      modifier = Modifier.weight(1f),
    ) {
      Icon(Symbols.Add, stringResource(Res.string.date_calculator_add))
    }
    ToggleButton(
      checked = !addition,
      onCheckedChange = { updateAddition(false) },
      shapes = ToggleButtonDefaults.shapes(),
      modifier = Modifier.weight(1f),
    ) {
      Icon(Symbols.Remove, stringResource(Res.string.date_calculator_subtract))
    }
  }
}

@Composable
private fun InputTextFieldsBox(
  modifier: Modifier,
  formatterSymbols: FormatterSymbols,
  years: TextFieldState,
  months: TextFieldState,
  days: TextFieldState,
  hours: TextFieldState,
  minutes: TextFieldState,
) {
  TextFieldBox(modifier = modifier) {
    TextFieldRow {
      TimeUnitTextField(
        modifier = Modifier.fillMaxWidth(),
        state = years,
        label = stringResource(Res.string.date_calculator_years),
        maxValue = 9_999.0,
        formatterSymbols = formatterSymbols,
        allowFraction = false,
      )
    }

    TextFieldRow {
      TimeUnitTextField(
        modifier = Modifier.weight(1f),
        state = months,
        label = stringResource(Res.string.date_calculator_months),
        maxValue = 9_999.0,
        formatterSymbols = formatterSymbols,
        allowFraction = false,
      )

      TimeUnitTextField(
        modifier = Modifier.weight(1f),
        state = days,
        label = stringResource(Res.string.date_calculator_days),
        maxValue = 99_999.0,
        formatterSymbols = formatterSymbols,
        allowFraction = false,
      )
    }

    TextFieldRow {
      TimeUnitTextField(
        modifier = Modifier.weight(1f),
        state = hours,
        label = stringResource(Res.string.date_calculator_hours),
        maxValue = 9_999_999.0,
        formatterSymbols = formatterSymbols,
        allowFraction = false,
      )

      TimeUnitTextField(
        modifier = Modifier.weight(1f),
        state = minutes,
        label = stringResource(Res.string.date_calculator_minutes),
        formatterSymbols = formatterSymbols,
        maxValue = 99_999_999.0,
        imeAction = ImeAction.Done,
        allowFraction = false,
      )
    }
  }
}

private fun Context.createEvent(start: ZonedDateTime, end: ZonedDateTime) {
  val millisecondsInSecond = 1_000
  val startMillis: Long = start.toEpochSecond() * millisecondsInSecond
  val endMillis: Long = end.toEpochSecond() * millisecondsInSecond
  val intent =
    Intent(Intent.ACTION_INSERT)
      .setData(CalendarContract.Events.CONTENT_URI)
      .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
      .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
      .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
  try {
    startActivity(intent)
  } catch (e: Exception) {
    Logger.e(e, TAG) { "Failed to create event" }
  }
}

private const val TAG = "AddSubtractPage"

@Preview
@Composable
fun AddSubtractViewPreview() = ExpressivePreview {
  AddSubtractView(
    uiState =
      AddSubtractUIState.Ready(
        addition = true,
        start = ZonedDateTimeUtils.nowWithMinutes(),
        result = ZonedDateTimeUtils.nowWithMinutes().plusSeconds(1),
        years = remember { TextFieldState("12") },
        months = remember { TextFieldState("12") },
        days = remember { TextFieldState("12") },
        hours = remember { TextFieldState("12") },
        minutes = remember { TextFieldState("12") },
        formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
      ),
    updateStart = {},
    updateAddition = {},
  )
}
