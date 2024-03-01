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

package com.sadellie.unitto.core.ui.datetimepicker

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.designsystem.icons.symbols.Keyboard
import com.sadellie.unitto.core.designsystem.icons.symbols.Schedule
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols

// https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/material3/samples/TimePickerSamples.kt
@Composable
fun TimePickerDialog(
  hour: Int,
  minute: Int,
  onCancel: () -> Unit,
  onConfirm: (hour: Int, minute: Int) -> Unit,
  confirmLabel: String = stringResource(R.string.common_ok),
) {
  val pickerState =
    rememberTimePickerState(
      initialHour = hour,
      initialMinute = minute,
      is24Hour = DateFormat.is24HourFormat(LocalContext.current),
    )
  val configuration = LocalConfiguration.current
  var showingPicker by rememberSaveable { mutableStateOf(true) }

  BasicAlertDialog(
    onDismissRequest = onCancel,
    properties = DialogProperties(usePlatformDefaultWidth = false),
  ) {
    Surface(
      shape = MaterialTheme.shapes.extraLarge,
      tonalElevation = 6.dp,
      modifier =
        Modifier.width(IntrinsicSize.Min)
          .height(IntrinsicSize.Min)
          .background(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surface,
          ),
    ) {
      if (configuration.screenHeightDp > EXPANDED_HEIGHT_DP) {
        // Make this take the entire viewport. This will guarantee that Screen readers
        // focus the toggle first.
        Box(Modifier.fillMaxSize().semantics { isTraversalGroup = true }) {
          IconButton(
            modifier =
              Modifier
                // This is a workaround so that the Icon comes up first
                // in the talkback traversal order. So that users of a11y
                // services can use the text input. When talkback traversal
                // order is customizable we can remove this.
                .size(64.dp, 72.dp)
                .align(Alignment.BottomStart)
                .zIndex(TOGGLE_BUTTON_Z_INDEX),
            onClick = { showingPicker = !showingPicker },
          ) {
            val icon =
              if (showingPicker) {
                Symbols.Keyboard
              } else {
                Symbols.Schedule
              }
            Icon(
              imageVector = icon,
              contentDescription = stringResource(R.string.common_select_time),
            )
          }
        }
      }
      Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Text(
          modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
          text = stringResource(R.string.common_select_time),
          style = MaterialTheme.typography.labelMedium,
        )
        if (showingPicker && configuration.screenHeightDp > EXPANDED_HEIGHT_DP) {
          TimePicker(state = pickerState)
        } else {
          TimeInput(state = pickerState)
        }
        Row(modifier = Modifier.height(40.dp).fillMaxWidth()) {
          Spacer(modifier = Modifier.weight(1f))
          TextButton(onClick = onCancel) { Text(stringResource(R.string.common_cancel)) }
          TextButton(onClick = { onConfirm(pickerState.hour, pickerState.minute) }) {
            Text(confirmLabel)
          }
        }
      }
    }
  }
}

private const val EXPANDED_HEIGHT_DP = 400
private const val TOGGLE_BUTTON_Z_INDEX = 5f
