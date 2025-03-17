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

package com.sadellie.unitto.feature.datecalculator.addsubtract

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.sadellie.unitto.core.common.showToast
import com.sadellie.unitto.core.designsystem.icons.symbols.Add
import com.sadellie.unitto.core.designsystem.icons.symbols.Event
import com.sadellie.unitto.core.designsystem.icons.symbols.Remove
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.shapes.Shapes
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.TextFieldBox
import com.sadellie.unitto.core.ui.TextFieldBoxDefaults
import com.sadellie.unitto.core.ui.TextFieldRow
import com.sadellie.unitto.feature.datecalculator.ZonedDateTimeUtils
import com.sadellie.unitto.feature.datecalculator.components.DateTimeBlock
import com.sadellie.unitto.feature.datecalculator.components.DateTimeDialogs
import com.sadellie.unitto.feature.datecalculator.components.DialogState
import com.sadellie.unitto.feature.datecalculator.components.TimeUnitTextField
import java.time.ZonedDateTime

@Composable
internal fun AddSubtractPage(viewModel: AddSubtractViewModel = hiltViewModel()) {
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

@Composable
private fun AddSubtractView(
  uiState: AddSubtractUIState.Ready,
  updateStart: (ZonedDateTime) -> Unit,
  updateAddition: (Boolean) -> Unit,
) {
  val mContext = LocalContext.current
  var dialogState by remember { mutableStateOf(DialogState.NONE) }

  val showResult = remember(uiState.start, uiState.result) { uiState.start != uiState.result }

  Column(Modifier.fillMaxSize()) {
    Scaffold(
      modifier = Modifier.fillMaxHeight().weight(1f),
      floatingActionButton = {
        FloatingActionButton(onClick = { mContext.createEvent(uiState.start, uiState.result) }) {
          Icon(
            imageVector = Symbols.Event,
            contentDescription = stringResource(R.string.date_calculator_create_event),
          )
        }
      },
    ) { paddingValues ->
      Column(
        modifier =
          Modifier.padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(Sizes.large),
        verticalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        AnimatedContent(
          targetState = showResult,
          label = "Reveal result",
          transitionSpec = { fadeIn() togetherWith fadeOut() using SizeTransform() },
          modifier = Modifier.clip(Shapes.ExtraLarge),
        ) { show ->
          FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            DateTimeBlock(
              modifier = Modifier.weight(1f),
              containerColor = MaterialTheme.colorScheme.secondaryContainer,
              title = stringResource(R.string.date_calculator_start),
              dateTime = uiState.start,
              onLongClick = { updateStart(ZonedDateTimeUtils.nowWithMinutes()) },
              onClick = { dialogState = DialogState.FROM },
              onTimeClick = { dialogState = DialogState.FROM_TIME },
              onDateClick = { dialogState = DialogState.FROM_DATE },
            )

            if (show) {
              DateTimeBlock(
                modifier = Modifier.weight(1f),
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                title = stringResource(R.string.date_calculator_end),
                dateTime = uiState.result,
              )
            }
          }
        }

        OperationSelector(uiState.addition, updateAddition)

        InputTextFieldsBox(
          formatterSymbols = uiState.formatterSymbols,
          years = uiState.years,
          months = uiState.months,
          days = uiState.days,
          hours = uiState.hours,
          minutes = uiState.minutes,
        )
      }
    }
  }

  DateTimeDialogs(
    dialogState = dialogState,
    updateDialogState = { dialogState = it },
    date = uiState.start,
    updateDate = updateStart,
    bothState = DialogState.FROM,
    timeState = DialogState.FROM_TIME,
    dateState = DialogState.FROM_DATE,
  )
}

@Composable
private fun OperationSelector(addition: Boolean, updateAddition: (Boolean) -> Unit) {
  SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
    SegmentedButton(
      selected = addition,
      onClick = { updateAddition(true) },
      shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
      icon = {},
    ) {
      Icon(Symbols.Add, stringResource(R.string.date_calculator_add))
    }
    SegmentedButton(
      selected = !addition,
      onClick = { updateAddition(false) },
      shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
      icon = {},
    ) {
      Icon(Symbols.Remove, stringResource(R.string.date_calculator_subtract))
    }
  }
}

@Composable
private fun InputTextFieldsBox(
  formatterSymbols: FormatterSymbols,
  years: TextFieldState,
  months: TextFieldState,
  days: TextFieldState,
  hours: TextFieldState,
  minutes: TextFieldState,
) {
  TextFieldBox(
    modifier =
      Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
        .padding(TextFieldBoxDefaults.Padding)
        .fillMaxWidth()
  ) {
    TextFieldRow {
      TimeUnitTextField(
        modifier = Modifier.fillMaxWidth(),
        state = years,
        label = stringResource(R.string.date_calculator_years),
        maxValue = 9_999.0,
        formatterSymbols = formatterSymbols,
      )
    }

    TextFieldRow {
      TimeUnitTextField(
        modifier = Modifier.weight(1f),
        state = months,
        label = stringResource(R.string.date_calculator_months),
        maxValue = 9_999.0,
        formatterSymbols = formatterSymbols,
      )

      TimeUnitTextField(
        modifier = Modifier.weight(1f),
        state = days,
        label = stringResource(R.string.date_calculator_days),
        maxValue = 99_999.0,
        formatterSymbols = formatterSymbols,
      )
    }

    TextFieldRow {
      TimeUnitTextField(
        modifier = Modifier.weight(1f),
        state = hours,
        label = stringResource(R.string.date_calculator_hours),
        maxValue = 9_999_999.0,
        formatterSymbols = formatterSymbols,
      )

      TimeUnitTextField(
        modifier = Modifier.weight(1f),
        state = minutes,
        label = stringResource(R.string.date_calculator_minutes),
        formatterSymbols = formatterSymbols,
        maxValue = 99_999_999.0,
        imeAction = ImeAction.Done,
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
  } catch (e: ActivityNotFoundException) {
    showToast(this, this.getString(R.string.common_error))
  }
}

@Preview
@Composable
fun AddSubtractViewPreview() {
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
