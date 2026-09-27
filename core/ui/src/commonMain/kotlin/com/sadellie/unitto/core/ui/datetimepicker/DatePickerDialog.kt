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

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlin.time.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_cancel
import unitto.core.common.generated.resources.common_ok

@Composable
fun DatePickerDialog(
  modifier: Modifier = Modifier,
  instant: Instant,
  confirmLabel: String = stringResource(Res.string.common_ok),
  dismissLabel: String = stringResource(Res.string.common_cancel),
  onDismiss: () -> Unit = {},
  onConfirm: (Instant) -> Unit,
) {
  val pickerState =
    rememberDatePickerState(
      initialSelectedDateMillis = instant.toEpochMilliseconds(),
      yearRange = 0..9_999,
    )

  DatePickerDialog(
    modifier = modifier,
    onDismissRequest = onDismiss,
    confirmButton = {
      TextButton(
        onClick = {
          val millis = pickerState.selectedDateMillis ?: return@TextButton
          val targetDate = Instant.fromEpochMilliseconds(millis).toLocalDateTime(TimeZone.UTC)
          val currentLocal = instant.toLocalDateTime(TimeZone.currentSystemDefault())

          val newLocal =
            LocalDateTime(
              year = targetDate.year,
              month = targetDate.month.number,
              day = targetDate.day,
              hour = currentLocal.hour,
              minute = currentLocal.minute,
              second = currentLocal.second,
              nanosecond = currentLocal.nanosecond,
            )

          onConfirm(newLocal.toInstant(TimeZone.currentSystemDefault()))
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
