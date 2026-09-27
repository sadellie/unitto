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

package com.sadellie.unitto.core.data

import com.sadellie.unitto.core.data.timezone.TimeZonesRepository
import com.sadellie.unitto.core.data.timezone.TimeZonesRepositoryImpl
import com.sadellie.unitto.core.database.DatabaseBindings
import com.sadellie.unitto.core.database.TimeZoneDao
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@BindingContainer(includes = [DatabaseBindings::class])
object TimeZoneDataBindings {
  @Provides
  @SingleIn(AppScope::class)
  fun provideTimeZonesRepository(timeZoneDao: TimeZoneDao): TimeZonesRepository =
    TimeZonesRepositoryImpl(dao = timeZoneDao)
}
