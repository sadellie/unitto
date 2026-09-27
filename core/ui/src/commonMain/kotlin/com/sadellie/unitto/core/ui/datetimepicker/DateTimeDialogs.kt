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
import kotlin.time.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_next
import unitto.core.common.generated.resources.common_ok

/** @param nextButton Show button to switch from time to date input (stepped input). */
@Composable
fun DateTimeDialogs(
  dialogState: DateTimeDialogState,
  updateDialogState: (DateTimeDialogState) -> Unit,
  date: Instant,
  updateDate: (Instant) -> Unit,
  nextButton: Boolean = true,
) {
  val currentLocal = date.toLocalDateTime(TimeZone.currentSystemDefault())

  when (dialogState) {
    DateTimeDialogState.FROM_TIME ->
      TimePickerDialog(
        hour = currentLocal.hour,
        minute = currentLocal.minute,
        onCancel = { updateDialogState(DateTimeDialogState.NONE) },
        onConfirm = { hour, minute ->
          val newLocal =
            LocalDateTime(
              year = currentLocal.year,
              month = currentLocal.month.number,
              day = currentLocal.day,
              hour = hour,
              minute = minute,
              second = currentLocal.second,
              nanosecond = currentLocal.nanosecond,
            )
          updateDate(newLocal.toInstant(TimeZone.currentSystemDefault()))
          updateDialogState(
            if (nextButton) DateTimeDialogState.FROM_DATE else DateTimeDialogState.NONE
          )
        },
        confirmLabel =
          stringResource(if (nextButton) Res.string.common_next else Res.string.common_ok),
      )
    DateTimeDialogState.FROM_DATE ->
      DatePickerDialog(
        instant = date,
        onDismiss = { updateDialogState(DateTimeDialogState.NONE) },
        onConfirm = {
          updateDate(it)
          updateDialogState(DateTimeDialogState.NONE)
        },
      )
    DateTimeDialogState.NONE -> Unit
  }
}

enum class DateTimeDialogState {
  NONE,
  FROM_TIME,
  FROM_DATE,
}
