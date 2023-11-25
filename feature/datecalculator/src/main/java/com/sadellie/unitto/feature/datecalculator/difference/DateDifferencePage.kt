/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.formatExpression
import com.sadellie.unitto.data.common.format
import com.sadellie.unitto.feature.datecalculator.ZonedDateTimeUtils
import com.sadellie.unitto.feature.datecalculator.components.DateTimeDialogs
import com.sadellie.unitto.feature.datecalculator.components.DateTimeResultBlock
import com.sadellie.unitto.feature.datecalculator.components.DateTimeSelectorBlock
import com.sadellie.unitto.feature.datecalculator.components.DialogState
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@Composable
internal fun DateDifferencePage(
    viewModel: DateDifferenceViewModel = hiltViewModel(),
) {
    when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
        DifferenceUIState.Loading -> Unit
        is DifferenceUIState.Ready -> DateDifferenceView(
            uiState = uiState,
            setStartDate = viewModel::setStartDate,
            setEndDate = viewModel::setEndDate
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
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FlowRow(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            maxItemsInEachRow = 2,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DateTimeSelectorBlock(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .weight(1f)
                    .fillMaxWidth(),
                title = stringResource(R.string.date_calculator_start),
                dateTime = uiState.start,
                onClick = { dialogState = DialogState.FROM },
                onLongClick = { setStartDate(ZonedDateTimeUtils.nowWithMinutes()) },
                onTimeClick = { dialogState = DialogState.FROM_TIME },
                onDateClick = { dialogState = DialogState.FROM_DATE },
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )

            DateTimeSelectorBlock(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .weight(1f)
                    .fillMaxWidth(),
                title = stringResource(R.string.date_calculator_end),
                dateTime = uiState.end,
                onClick = { dialogState = DialogState.TO },
                onLongClick = { setEndDate(ZonedDateTimeUtils.nowWithMinutes()) },
                onTimeClick = { dialogState = DialogState.TO_TIME },
                onDateClick = { dialogState = DialogState.TO_DATE },
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        }

        AnimatedContent(
            targetState = uiState.result,
            modifier = Modifier.fillMaxWidth()
        ) { result ->
            when (result) {
                is ZonedDateTimeDifference.Default -> {
                    DateTimeResultBlock(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        diff = result,
                        format = {
                            it.format(uiState.precision, uiState.outputFormat)
                                .formatExpression(uiState.formatterSymbols)
                        }
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
        bothState = DialogState.FROM,
        timeState = DialogState.FROM_TIME,
        dateState = DialogState.FROM_DATE,
    )

    DateTimeDialogs(
        dialogState = dialogState,
        updateDialogState = { dialogState = it },
        date = uiState.end,
        updateDate = setEndDate,
        bothState = DialogState.TO,
        timeState = DialogState.TO_TIME,
        dateState = DialogState.TO_DATE,
    )
}


@Preview
@Composable
fun DateDifferenceViewPreview() {
    DateDifferenceView(
        uiState = DifferenceUIState.Ready(
            start = ZonedDateTimeUtils.nowWithMinutes(),
            end = ZonedDateTimeUtils.nowWithMinutes().truncatedTo(ChronoUnit.MINUTES),
            result = ZonedDateTimeDifference.Default(
                years = 1,
                months = 2,
                days = 3,
                hours = 4,
                minutes = 5,
                sumYears = BigDecimal("0.083"),
                sumMonths = BigDecimal("1.000"),
                sumDays = BigDecimal("30.000"),
                sumHours = BigDecimal("720.000"),
                sumMinutes = BigDecimal("43200.000"),
            ),
            precision = 3,
            outputFormat = OutputFormat.PLAIN,
            formatterSymbols = FormatterSymbols.Spaces
        ),
        setStartDate = {},
        setEndDate = {},
    )
}
