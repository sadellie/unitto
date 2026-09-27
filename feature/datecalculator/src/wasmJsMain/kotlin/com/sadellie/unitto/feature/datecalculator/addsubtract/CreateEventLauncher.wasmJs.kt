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

package com.sadellie.unitto.feature.datecalculator.addsubtract

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.sadellie.unitto.core.ui.AndroidExclusiveDialog
import kotlin.time.Instant

@Composable
actual fun rememberCreateEventLauncher(): CreateEventLauncher {
  var showAndroidExclusive by rememberSaveable { mutableStateOf(false) }
  if (showAndroidExclusive) {
    AndroidExclusiveDialog(onDismissRequest = { showAndroidExclusive = false })
  }

  return remember {
    object : CreateEventLauncher {
      override fun launch(start: Instant, end: Instant) {
        showAndroidExclusive = true
      }
    }
  }
}
