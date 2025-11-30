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

package com.sadellie.unitto.feature.timezone.navigation

import com.sadellie.unitto.core.navigation.LocalNavigator
import com.sadellie.unitto.core.navigation.TimeZoneStartRoute
import com.sadellie.unitto.core.ui.AndroidExclusiveScreenMain
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.Module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
internal actual fun Module.timeZoneNavigation() {
  navigation<TimeZoneStartRoute> { AndroidExclusiveScreenMain(LocalNavigator.current::openDrawer) }
}
