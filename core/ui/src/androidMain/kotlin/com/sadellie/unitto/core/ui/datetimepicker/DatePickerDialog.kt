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

package com.sadellie.unitto.core.ui.datetimepicker

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_cancel
import unitto.core.common.generated.resources.common_ok

@Composable
fun DatePickerDialog(
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
        }
      ) {
        Text(confirmLabel)
      }
    },
    dismissButton = { TextButton(onDismiss) { Text(dismissLabel) } },
    content = { DatePicker(pickerState) },
  )
}
