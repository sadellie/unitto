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

package com.sadellie.unitto.feature.converter.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sadellie.unitto.feature.converter.MainScreen
import com.sadellie.unitto.feature.converter.MainViewModel

const val converterRoute = "converter_route"

fun NavGraphBuilder.converterScreen(
    navigateToLeftScreen: () -> Unit,
    navigateToRightScreen: () -> Unit,
    navigateToSettings: () -> Unit,
    viewModel: MainViewModel
) {
    composable(converterRoute) {
        MainScreen(
            navigateToLeftScreen = navigateToLeftScreen,
            navigateToRightScreen = navigateToRightScreen,
            navigateToSettings = navigateToSettings,
            viewModel = viewModel,
        )
    }
}
