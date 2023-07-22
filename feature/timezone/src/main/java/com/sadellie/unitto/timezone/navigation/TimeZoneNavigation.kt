/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.timezone.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.sadellie.unitto.core.base.TopLevelDestinations
import com.sadellie.unitto.timezone.AddTimeZoneRoute
import com.sadellie.unitto.timezone.TimeZoneRoute
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

private val timeZoneGraph: String by lazy { TopLevelDestinations.TimeZone.route }
internal const val timeZoneRoute = "time_zone_route"
internal const val addTimeZoneRoute = "add_time_zone_route"
internal const val userTimeArg = "userTime"

fun NavController.navigateToAddTimeZone(
    userTime: ZonedDateTime?
) {
    val formattedTime = userTime
        ?.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
        ?.replace("/", "|") // this is so wrong

    navigate("$addTimeZoneRoute/$formattedTime")
}

fun NavGraphBuilder.timeZoneScreen(
    navigateToMenu: () -> Unit,
    navigateToSettings: () -> Unit,
    navController: NavHostController,
) {
    navigation(
        startDestination = timeZoneRoute,
        route = timeZoneGraph,
        deepLinks = listOf(navDeepLink { uriPattern = "app://com.sadellie.unitto/$timeZoneRoute" })
    ) {
        composable(timeZoneRoute) {
            TimeZoneRoute(
                navigateToMenu = navigateToMenu,
                navigateToSettings = navigateToSettings,
                navigateToAddTimeZone = navController::navigateToAddTimeZone
            )
        }

        composable(
            route = "$addTimeZoneRoute/{$userTimeArg}",
            arguments = listOf(
                navArgument(userTimeArg) {
                    defaultValue = null
                    nullable = true
                    type = NavType.StringType
                }
            )
        ) { stackEntry ->
            val userTime = stackEntry.arguments
                ?.getString(userTimeArg)
                ?.replace("|", "/") // war crime, don't look
                ?.let { ZonedDateTime.parse(it, DateTimeFormatter.ISO_ZONED_DATE_TIME) }

            AddTimeZoneRoute(
                navigateUp = navController::navigateUp,
                userTime = userTime
            )
        }
    }
}
