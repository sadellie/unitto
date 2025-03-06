/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.feature.graphing.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navDeepLink
import com.sadellie.unitto.core.designsystem.unittoComposable
import com.sadellie.unitto.core.designsystem.unittoNavigation
import com.sadellie.unitto.core.navigation.GraphingGraphRoute
import com.sadellie.unitto.core.navigation.Route
import com.sadellie.unitto.core.navigation.deepLink
import com.sadellie.unitto.feature.graphing.FunctionCreatorRoute
import com.sadellie.unitto.feature.graphing.FunctionEditorRoute
import com.sadellie.unitto.feature.graphing.GraphFunction
import com.sadellie.unitto.feature.graphing.GraphingRoute
import com.sadellie.unitto.feature.graphing.GraphingViewModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

fun NavGraphBuilder.graphingGraph(openDrawer: () -> Unit, navController: NavHostController) {
  unittoNavigation<GraphingGraphRoute>(
    startDestination = GraphingStartRoute::class,
    deepLinks = listOf(navDeepLink { uriPattern = deepLink(GraphingGraphRoute) }),
  ) {
    unittoComposable<GraphingStartRoute> { backStackEntry ->
      val parentEntry =
        remember(backStackEntry) { navController.getBackStackEntry(GraphingGraphRoute) }
      val viewModel = hiltViewModel<GraphingViewModel>(parentEntry)

      // listen for result from function creator and editor screens
      val creatorResult =
        backStackEntry.savedStateHandle
          .getStateFlow<String?>(FUNCTION_CREATOR_RESULT, null)
          .collectAsStateWithLifecycle()
      val editorResult =
        backStackEntry.savedStateHandle
          .getStateFlow<String?>(FUNCTION_EDITOR_RESULT, null)
          .collectAsStateWithLifecycle()

      LaunchedEffect(creatorResult.value) {
        val newFunction =
          Json.decodeFromString<GraphFunction>(creatorResult.value ?: return@LaunchedEffect)
        viewModel.onAddFunction(newFunction)
        backStackEntry.savedStateHandle.remove<GraphFunction>(FUNCTION_CREATOR_RESULT)
      }
      LaunchedEffect(editorResult.value) {
        val updatedFunction =
          Json.decodeFromString<GraphFunction>(editorResult.value ?: return@LaunchedEffect)
        viewModel.onEditFunction(updatedFunction)
        backStackEntry.savedStateHandle.remove<GraphFunction>(FUNCTION_EDITOR_RESULT)
      }

      GraphingRoute(
        openDrawer = openDrawer,
        viewModel = viewModel,
        navigateToEditor = { function ->
          navController.navigate(
            FunctionEditorRoute(function.id, function.expression, function.color.value.toLong())
          )
        },
        navigateToCreator = { navController.navigate(FunctionCreatorRoute) },
      )
    }

    unittoComposable<FunctionCreatorRoute> {
      FunctionCreatorRoute(
        navigateUp = navController::navigateUp,
        viewModel = hiltViewModel(),
        onConfirm = { newFunction ->
          navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set(FUNCTION_CREATOR_RESULT, Json.encodeToString(newFunction))
          navController.navigateUp()
        },
      )
    }

    unittoComposable<FunctionEditorRoute> {
      FunctionEditorRoute(
        navigateUp = navController::navigateUp,
        viewModel = hiltViewModel(),
        onConfirm = { updatedFunction ->
          navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set(FUNCTION_EDITOR_RESULT, Json.encodeToString(updatedFunction))
          navController.navigateUp()
        },
      )
    }
  }
}

@Serializable
private data object GraphingStartRoute : Route {
  override val id = "graphing_start"
}

@Serializable
private data object FunctionCreatorRoute : Route {
  override val id = "function_creator"
}

@Serializable
internal data class FunctionEditorRoute(
  // Passing complex data between screens is a crime in compost team (typeMaps is pain)
  val functionId: Int,
  val functionExpression: String,
  val functionColor: Long,
) : Route {
  override val id = "function_editor"
}

private const val FUNCTION_CREATOR_RESULT = "functionCreatorResult"
private const val FUNCTION_EDITOR_RESULT = "functionEditorResult"
