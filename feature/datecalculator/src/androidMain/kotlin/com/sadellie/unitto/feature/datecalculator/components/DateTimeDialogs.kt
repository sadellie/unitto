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

package com.sadellie.unitto.feature.datecalculator.components

import androidx.compose.runtime.Composable
import com.sadellie.unitto.core.ui.datetimepicker.DatePickerDialog
import com.sadellie.unitto.core.ui.datetimepicker.TimePickerDialog
import java.time.ZonedDateTime
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_next

@Composable
internal fun DateTimeDialogs(
  dialogState: DialogState,
  updateDialogState: (DialogState) -> Unit,
  date: ZonedDateTime,
  updateDate: (ZonedDateTime) -> Unit,
  timeState: DialogState,
  dateState: DialogState,
) {
  when (dialogState) {
    timeState ->
      TimePickerDialog(
        hour = date.hour,
        minute = date.minute,
        onCancel = { updateDialogState(DialogState.NONE) },
        onConfirm = { hour, minute ->
          updateDate(date.withHour(hour).withMinute(minute))
          updateDialogState(dateState)
        },
        confirmLabel = stringResource(Res.string.common_next),
      )
    dateState ->
      DatePickerDialog(
        zonedDateTime = date,
        onDismiss = { updateDialogState(DialogState.NONE) },
        onConfirm = {
          updateDate(it)
          updateDialogState(DialogState.NONE)
        },
      )
    else -> Unit
  }
}

internal enum class DialogState {
  NONE,
  FROM_TIME,
  FROM_DATE,
  TO_TIME,
  TO_DATE,
}
