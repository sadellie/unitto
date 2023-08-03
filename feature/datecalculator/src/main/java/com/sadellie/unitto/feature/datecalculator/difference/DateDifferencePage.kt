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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.feature.datecalculator.components.DateTimeDialogs
import com.sadellie.unitto.feature.datecalculator.components.DateTimeResultBlock
import com.sadellie.unitto.feature.datecalculator.components.DateTimeSelectorBlock
import com.sadellie.unitto.feature.datecalculator.components.DialogState
import java.time.ZonedDateTime

@Composable
internal fun DateDifferencePage(
    viewModel: DateDifferenceViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    DateDifferenceView(
        uiState = uiState.value,
        setStartDate = viewModel::setStartDate,
        setEndDate = viewModel::setEndDate
    )
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun DateDifferenceView(
    uiState: DifferenceUIState,
    setStartDate: (ZonedDateTime) -> Unit,
    setEndDate: (ZonedDateTime) -> Unit,
) {
    var dialogState by remember { mutableStateOf(DialogState.NONE) }

    FlowRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        maxItemsInEachRow = 2,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DateTimeSelectorBlock(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            title = stringResource(R.string.date_difference_start),
            dateTime = uiState.start,
            onClick = { dialogState = DialogState.FROM },
            onLongClick = { setStartDate(ZonedDateTime.now()) },
            onTimeClick = { dialogState = DialogState.FROM_TIME },
            onDateClick = { dialogState = DialogState.FROM_DATE },
        )

        DateTimeSelectorBlock(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            title = stringResource(R.string.date_difference_end),
            dateTime = uiState.end,
            onClick = { dialogState = DialogState.TO },
            onLongClick = { setStartDate(ZonedDateTime.now()) },
            onTimeClick = { dialogState = DialogState.TO_TIME },
            onDateClick = { dialogState = DialogState.TO_DATE },
        )

        AnimatedVisibility(
            visible = uiState.result is ZonedDateTimeDifference.Default,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            DateTimeResultBlock(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth(),
                zonedDateTimeDifference = uiState.result,
            )
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
        uiState = DifferenceUIState(),
        setStartDate = {},
        setEndDate = {},
    )
}
