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
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.sadellie.unitto.core.designsystem.unittoComposable
import com.sadellie.unitto.core.designsystem.unittoNavigation
import com.sadellie.unitto.core.navigation.Route
import com.sadellie.unitto.core.navigation.TimeZoneGraphRoute
import com.sadellie.unitto.core.navigation.deepLink
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.feature.timezone.AddTimeZoneRoute
import com.sadellie.unitto.feature.timezone.TimeZoneRoute
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlinx.serialization.Serializable

fun NavGraphBuilder.timeZoneGraph(openDrawer: () -> Unit, navController: NavHostController) {
  unittoNavigation<TimeZoneGraphRoute>(
    startDestination = TimeZoneStartRoute::class,
    deepLinks = listOf(navDeepLink { uriPattern = deepLink(TimeZoneGraphRoute) }),
  ) {
    unittoComposable<TimeZoneStartRoute> {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        EmptyScreen()
        return@unittoComposable
      }

      TimeZoneRoute(
        openDrawer = openDrawer,
        navigateToAddTimeZone = { userTime ->
          navController.navigate(
            AddTimeZoneRoute(userTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME))
          )
        },
      )
    }

    unittoComposable<AddTimeZoneRoute> { stackEntry ->
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        EmptyScreen()
        return@unittoComposable
      }

      val userTime =
        ZonedDateTime.parse(
          stackEntry.toRoute<AddTimeZoneRoute>().userTimeIso,
          DateTimeFormatter.ISO_ZONED_DATE_TIME,
        )

      AddTimeZoneRoute(navigateUp = navController::navigateUp, userTime = userTime)
    }
  }
}

@Serializable
private data object TimeZoneStartRoute : Route {
  override val id = "time_zone_start"
}

@Serializable
internal data class AddTimeZoneRoute(val userTimeIso: String) : Route {
  override val id = "add_time_zone_route"
}
