/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
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

package com.sadellie.unitto.feature.timezone.components

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sadellie.unitto.core.ui.datetimepicker.TimePickerDialog
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_cancel
import unitto.core.common.generated.resources.common_next
import unitto.core.common.generated.resources.common_ok

// android only version of DateTimeDialogs

/** @param nextButton Show button to switch from time to date input (stepped input). */
@Composable
internal fun DateTimeDialogs(
  dialogState: DateTimeDialogStateTZ,
  updateDialogState: (DateTimeDialogStateTZ) -> Unit,
  date: ZonedDateTime,
  updateDate: (ZonedDateTime) -> Unit,
  nextButton: Boolean = true,
) {
  when (dialogState) {
    DateTimeDialogStateTZ.FROM_TIME ->
      TimePickerDialog(
        hour = date.hour,
        minute = date.minute,
        onCancel = { updateDialogState(DateTimeDialogStateTZ.NONE) },
        onConfirm = { hour, minute ->
          updateDate(date.withHour(hour).withMinute(minute))
          updateDialogState(
            if (nextButton) DateTimeDialogStateTZ.FROM_DATE else DateTimeDialogStateTZ.NONE
          )
        },
        confirmLabel =
          stringResource(if (nextButton) Res.string.common_next else Res.string.common_ok),
      )
    DateTimeDialogStateTZ.FROM_DATE ->
      DatePickerDialog(
        zonedDateTime = date,
        onDismiss = { updateDialogState(DateTimeDialogStateTZ.NONE) },
        onConfirm = {
          updateDate(it)
          updateDialogState(DateTimeDialogStateTZ.NONE)
        },
      )
    DateTimeDialogStateTZ.NONE -> Unit
  }
}

internal enum class DateTimeDialogStateTZ {
  NONE,
  FROM_TIME,
  FROM_DATE,
}

@Composable
private fun DatePickerDialog(
  modifier: Modifier = Modifier,
  zonedDateTime: ZonedDateTime,
  confirmLabel: String = stringResource(Res.string.common_ok),
  dismissLabel: String = stringResource(Res.string.common_cancel),
  onDismiss: () -> Unit = {},
  onConfirm: (ZonedDateTime) -> Unit,
) {
  val pickerState =
    rememberDatePickerState(
      initialSelectedDateMillis =
        zonedDateTime.withZoneSameLocal(ZoneOffset.UTC).toEpochSecond() * 1_000,
      yearRange = 0..9_999,
    )

  DatePickerDialog(
    modifier = modifier,
    onDismissRequest = onDismiss,
    confirmButton = {
      TextButton(
        onClick = {
          val millis = pickerState.selectedDateMillis ?: return@TextButton
          val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC)

          onConfirm(
            zonedDateTime
              .withYear(date.year)
              .withMonth(date.monthValue)
              .withDayOfMonth(date.dayOfMonth)
          )
        },
        shapes = ButtonDefaults.shapes(),
      ) {
        Text(confirmLabel)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss, shapes = ButtonDefaults.shapes()) { Text(dismissLabel) }
    },
    content = { DatePicker(pickerState) },
  )
}
