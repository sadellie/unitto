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

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import co.touchlab.kermit.Logger
import kotlin.time.Instant

@Composable
actual fun rememberCreateEventLauncher(): CreateEventLauncher {
  val context = LocalContext.current
  return remember(context) { CreateEventLauncherImpl(context) }
}

private class CreateEventLauncherImpl(private val context: Context) : CreateEventLauncher {
  override fun launch(start: Instant, end: Instant) {
    val startMillis: Long = start.toEpochMilliseconds()
    val endMillis: Long = end.toEpochMilliseconds()
    val intent =
      Intent(Intent.ACTION_INSERT)
        .setData(CalendarContract.Events.CONTENT_URI)
        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)

    try {
      context.startActivity(intent)
    } catch (e: Exception) {
      Logger.e(e, TAG) { "Failed to create event" }
    }
  }
}

private const val TAG = "CreateEventLauncherImpl"
