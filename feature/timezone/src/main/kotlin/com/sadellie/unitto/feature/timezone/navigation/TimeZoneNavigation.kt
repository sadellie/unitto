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

package com.sadellie.unitto.feature.timezone.navigation

import android.os.Build
import com.sadellie.unitto.core.navigation.LocalNavigator
import com.sadellie.unitto.core.navigation.Route
import com.sadellie.unitto.core.navigation.TimeZoneStartRoute
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.feature.timezone.AddTimeZoneRoute
import com.sadellie.unitto.feature.timezone.TimeZoneRoute
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlinx.serialization.Serializable
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.Module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
fun Module.timeZoneNavigation() {
  navigation<TimeZoneStartRoute> {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
      EmptyScreen()
      return@navigation
    }

    val navigator = LocalNavigator.current
    TimeZoneRoute(
      openDrawer = navigator::openDrawer,
      navigateToAddTimeZone = { userTime ->
        navigator.goTo(AddTimeZoneRoute(userTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)))
      },
    )
  }
  navigation<AddTimeZoneRoute> { route ->
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
      EmptyScreen()
      return@navigation
    }
    val navigator = LocalNavigator.current
    val userTime = ZonedDateTime.parse(route.userTimeIso, DateTimeFormatter.ISO_ZONED_DATE_TIME)
    AddTimeZoneRoute(navigateUp = navigator::goBack, userTime = userTime)
  }
}

@Serializable
internal data class AddTimeZoneRoute(val userTimeIso: String) : Route {
  override val routeId = "add_time_zone_route"
}
