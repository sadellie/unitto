/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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

package com.sadellie.unitto.core.navigation

import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.deeplink.DeepLinkRequest
import androidx.navigation3.runtime.deeplink.DeepLinkUri
import androidx.navigation3.runtime.deeplink.UriDeepLinkMatcher
import com.sadellie.unitto.core.navigation.CalculatorStartRoute.serializer
import kotlinx.serialization.Serializable

interface Route : NavKey {
  /** Don't touch, users have "..._route" in their settings as initial route and in shortcuts */
  val routeId: String

  companion object {
    /**
     * Extract [Route] from [uriString]
     *
     * @return matched [Route] (using [DeepLinkRequest]) or null if no matches
     * @author https://github.com/android/nav3-recipes
     */
    fun extractRouteFromDeeplink(uriString: String): Route? {
      val deepLinkRequest = DeepLinkRequest(uriString)
      val uriDeepLinkMatchers: List<UriDeepLinkMatcher<Route>> =
        listOf(
          UriDeepLinkMatcher(DeepLinkUri(deepLink(CalculatorStartRoute)), serializer()),
          UriDeepLinkMatcher(
            DeepLinkUri(deepLink(ConverterStartRoute())),
            ConverterStartRoute.serializer(),
          ),
          UriDeepLinkMatcher(
            DeepLinkUri(ConverterStartRoute.DEEP_LINK_PATTERN),
            ConverterStartRoute.serializer(),
          ),
          UriDeepLinkMatcher(
            DeepLinkUri(deepLink(DateCalculatorStartRoute)),
            DateCalculatorStartRoute.serializer(),
          ),
          UriDeepLinkMatcher(
            DeepLinkUri(deepLink(TimeZoneStartRoute)),
            TimeZoneStartRoute.serializer(),
          ),
          UriDeepLinkMatcher(
            DeepLinkUri(deepLink(BodyMassStartRoute)),
            BodyMassStartRoute.serializer(),
          ),
          UriDeepLinkMatcher(
            DeepLinkUri(deepLink(BodyMassStartRoute)),
            ProgrammerStartRoute.serializer(),
          ),
        )
      val matches = uriDeepLinkMatchers.mapNotNull {
        it.match(deepLinkRequest)
      }
      val bestMatch = matches.maxOrNull()
      return bestMatch?.key
    }
  }
}

/** Special marker for top level routes */
interface TopLevelRoute : Route

@Serializable
data object CalculatorStartRoute : TopLevelRoute {
  override val routeId = "calculator_route"
}

@Serializable
data class ConverterStartRoute(val unitFromId: String = "", val unitToId: String = "") :
  TopLevelRoute {
  companion object {
    private const val ROUTE_ID = "converter_route"

    // parameter names must match with constructor
    fun generateRoute(unitFromId: String?, unitToId: String?) =
      "$NAVIGATION_BASE_URI/$ROUTE_ID?unitFromId=$unitFromId&unitToId=$unitToId"

    val DEEP_LINK_PATTERN =
      "$NAVIGATION_BASE_URI/$ROUTE_ID" +
        "?${ConverterStartRoute::unitFromId.name}={${ConverterStartRoute::unitFromId.name}}" +
        "&${ConverterStartRoute::unitToId.name}={${ConverterStartRoute::unitToId.name}}"
  }

  override val routeId = ROUTE_ID
}

@Serializable
data object DateCalculatorStartRoute : TopLevelRoute {
  override val routeId = "date_calculator_route"
}

@Serializable
data object TimeZoneStartRoute : TopLevelRoute {
  override val routeId = "time_zone_route"
}

@Serializable
data object BodyMassStartRoute : TopLevelRoute {
  override val routeId = "body_mass_route"
}

@Serializable
data object SettingsStartRoute : TopLevelRoute {
  override val routeId = "settings_route"
}

@Serializable
data object ProgrammerStartRoute : TopLevelRoute {
  override val routeId = "programmer_route"
}

val graphRoutes = (mainDrawerItems + additionalDrawerItems).map { it.topLevelRoute }

fun deepLink(graphRoute: TopLevelRoute): String = "$NAVIGATION_BASE_URI/${graphRoute.routeId}"

private const val NAVIGATION_BASE_URI = "app://com.sadellie.unitto"
