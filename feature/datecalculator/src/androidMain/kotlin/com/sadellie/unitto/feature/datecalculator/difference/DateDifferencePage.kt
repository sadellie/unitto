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

package com.sadellie.unitto.feature.datecalculator.difference

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.designsystem.ExpressivePreview
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.feature.datecalculator.ZonedDateTimeUtils
import com.sadellie.unitto.feature.datecalculator.components.DateTimeBlock
import com.sadellie.unitto.feature.datecalculator.components.DateTimeDialogs
import com.sadellie.unitto.feature.datecalculator.components.DateTimeResultBlock
import com.sadellie.unitto.feature.datecalculator.components.DialogState
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.date_calculator_end
import unitto.core.common.generated.resources.date_calculator_start

@Composable
internal fun DateDifferencePage() {
  val viewModel: DateDifferenceViewModel = koinViewModel()
  when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
    DifferenceUIState.Loading -> Unit
    is DifferenceUIState.Ready ->
      DateDifferenceView(
        uiState = uiState,
        setStartDate = viewModel::setStartDate,
        setEndDate = viewModel::setEndDate,
      )
  }
}

@Composable
private fun DateDifferenceView(
  uiState: DifferenceUIState.Ready,
  setStartDate: (ZonedDateTime) -> Unit,
  setEndDate: (ZonedDateTime) -> Unit,
) {
  var dialogState by remember { mutableStateOf(DialogState.NONE) }

  Column(
    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(Sizes.small),
  ) {
    Spacer(modifier = Modifier.height(Sizes.large))

    Row(
      modifier = Modifier.padding(horizontal = Sizes.large).fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(Sizes.small),
    ) {
      DateTimeBlock(
        modifier = Modifier.weight(1f).fillMaxWidth(),
        title = stringResource(Res.string.date_calculator_start),
        onTimeClick = { dialogState = DialogState.FROM_TIME },
        onDateClick = { dialogState = DialogState.FROM_DATE },
        onLongClick = { setStartDate(ZonedDateTimeUtils.nowWithMinutes()) },
        dateTime = uiState.start,
      )

      DateTimeBlock(
        modifier = Modifier.weight(1f).fillMaxWidth(),
        title = stringResource(Res.string.date_calculator_end),
        onTimeClick = { dialogState = DialogState.TO_TIME },
        onDateClick = { dialogState = DialogState.TO_DATE },
        onLongClick = { setEndDate(ZonedDateTimeUtils.nowWithMinutes()) },
        dateTime = uiState.end,
      )
    }

    AnimatedContent(
      targetState = uiState.result,
      label = "Result reveal",
      modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
    ) { result ->
      when (result) {
        is ZonedDateTimeDifference.Default -> {
          DateTimeResultBlock(
            diff = result,
            precision = uiState.precision,
            outputFormat = uiState.outputFormat,
            formatterSymbols = uiState.formatterSymbols,
          )
        }
        ZonedDateTimeDifference.Zero -> Unit
      }
    }
  }

  DateTimeDialogs(
    dialogState = dialogState,
    updateDialogState = { dialogState = it },
    date = uiState.start,
    updateDate = setStartDate,
    timeState = DialogState.FROM_TIME,
    dateState = DialogState.FROM_DATE,
  )

  DateTimeDialogs(
    dialogState = dialogState,
    updateDialogState = { dialogState = it },
    date = uiState.end,
    updateDate = setEndDate,
    timeState = DialogState.TO_TIME,
    dateState = DialogState.TO_DATE,
  )
}

@Preview
@Composable
fun DateDifferenceViewPreview() = ExpressivePreview {
  DateDifferenceView(
    uiState =
      DifferenceUIState.Ready(
        start = ZonedDateTimeUtils.nowWithMinutes(),
        end = ZonedDateTimeUtils.nowWithMinutes().truncatedTo(ChronoUnit.MINUTES),
        result =
          ZonedDateTimeDifference.Default(
            years = 1,
            months = 2,
            days = 3,
            hours = 4,
            minutes = 5,
            sumYears = KBigDecimal("0.083"),
            sumMonths = KBigDecimal("1.000"),
            sumDays = KBigDecimal("30.000"),
            sumHours = KBigDecimal("720.000"),
            sumMinutes = KBigDecimal("43200.000"),
          ),
        precision = 3,
        outputFormat = OutputFormat.PLAIN,
        formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
      ),
    setStartDate = {},
    setEndDate = {},
  )
}
