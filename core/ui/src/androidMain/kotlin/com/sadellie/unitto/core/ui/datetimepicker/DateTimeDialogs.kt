/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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

package com.sadellie.unitto.core.ui.datetimepicker

import androidx.compose.runtime.Composable
import java.time.ZonedDateTime
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_next
import unitto.core.common.generated.resources.common_ok

@Composable
fun DateTimeDialogs(
  dialogState: DateTimeDialogState,
  updateDialogState: (DateTimeDialogState) -> Unit,
  date: ZonedDateTime,
  updateDate: (ZonedDateTime) -> Unit,
  timeState: DateTimeDialogState = DateTimeDialogState.FROM_TIME_AND_DATE,
  dateState: DateTimeDialogState = DateTimeDialogState.FROM_DATE,
) {
  when (dialogState) {
    timeState ->
      TimePickerDialog(
        hour = date.hour,
        minute = date.minute,
        onCancel = { updateDialogState(DateTimeDialogState.NONE) },
        onConfirm = { hour, minute ->
          updateDate(date.withHour(hour).withMinute(minute))
          updateDialogState(if (dateState.nextButton) dateState else DateTimeDialogState.NONE)
        },
        confirmLabel =
          stringResource(if (dateState.nextButton) Res.string.common_next else Res.string.common_ok),
      )
    dateState ->
      DatePickerDialog(
        zonedDateTime = date,
        onDismiss = { updateDialogState(DateTimeDialogState.NONE) },
        onConfirm = {
          updateDate(it)
          updateDialogState(DateTimeDialogState.NONE)
        },
      )
    else -> Unit
  }
}

enum class DateTimeDialogState(internal val nextButton: Boolean = false) {
  NONE,
  FROM_TIME,
  FROM_TIME_AND_DATE(true),
  FROM_DATE,
  TO_TIME_AND_DATE(true),
  TO_DATE,
}
