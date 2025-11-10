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

package com.sadellie.unitto.feature.glance.converter

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sadellie.unitto.core.designsystem.theme.DarkThemeColors
import com.sadellie.unitto.core.designsystem.theme.LightThemeColors
import com.sadellie.unitto.core.designsystem.unittoComposable
import com.sadellie.unitto.core.designsystem.unittoNavigation
import com.sadellie.unitto.core.designsystem.unittoStackedComposable
import com.sadellie.unitto.core.navigation.Route
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.rememberThemmoController
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

internal class ConverterWidgetConfigureActivity : AppCompatActivity() {
  private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

  @OptIn(ExperimentalMaterial3ExpressiveApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)

    setCancellationResult()
    appWidgetId = extractAppWidgetId()
    if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
      finish()
      return
    }

    setContent {
      val themmoController =
        rememberThemmoController(
          lightColorScheme = LightThemeColors,
          darkColorScheme = DarkThemeColors,
          dynamicThemeEnabled = true,
        )

      Themmo(themmoController) {
        val navController = rememberNavController()
        val coroutineScope = rememberCoroutineScope()
        ConverterWidgetConfigureNavigation(
          navController = navController,
          appWidgetId = appWidgetId,
          onDone = {
            val context = this
            val manager = GlanceAppWidgetManager(this)
            val glanceId = manager.getGlanceIdBy(appWidgetId)
            val widget = ConverterWidget()
            coroutineScope.launch { widget.update(context, glanceId) }
            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(RESULT_OK, resultValue)
            finish()
          },
        )
      }
    }
  }

  private fun setCancellationResult() {
    // Cancel widget placement in case user leaves this activity
    val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    setResult(RESULT_CANCELED, resultValue)
  }

  private fun extractAppWidgetId(): Int {
    return intent
      ?.extras
      ?.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
      ?: AppWidgetManager.INVALID_APPWIDGET_ID
  }
}

@Composable
private fun ConverterWidgetConfigureNavigation(
  navController: NavHostController,
  appWidgetId: Int,
  onDone: () -> Unit,
) {
  NavHost(
    navController = navController,
    startDestination = ConverterWidgetConfigureGraphRoute::class,
    modifier = Modifier.background(MaterialTheme.colorScheme.background),
  ) {
    unittoNavigation<ConverterWidgetConfigureGraphRoute>(
      startDestination = ConverterWidgetConfigureStartRoute::class
    ) {
      unittoComposable<ConverterWidgetConfigureStartRoute> { backStackEntry ->
        val parentEntry =
          remember(backStackEntry) {
            navController.getBackStackEntry(ConverterWidgetConfigureGraphRoute)
          }
        val viewModel =
          koinViewModel<ConverterWidgetConfigureViewModel>(viewModelStoreOwner = parentEntry) {
            parametersOf(appWidgetId)
          }

        // listen for result when adding unit pair
        val selectUnitPairResult =
          backStackEntry.savedStateHandle
            .getStateFlow<String?>(SELECT_UNIT_ADD_PAIR_RESULT, null)
            .collectAsStateWithLifecycle()

        LaunchedEffect(selectUnitPairResult.value) {
          val result = selectUnitPairResult.value ?: return@LaunchedEffect
          val newPair = Json.decodeFromString<AddUnitPairResult>(result)
          viewModel.addNewPair(newPair.unitFromId, newPair.unitToId)
          backStackEntry.savedStateHandle.remove<AddUnitPairResult>(SELECT_UNIT_ADD_PAIR_RESULT)
        }

        val submitProgress = viewModel.submitProgress.collectAsStateWithLifecycle()
        LaunchedEffect(submitProgress.value) {
          if (submitProgress.value == ConverterWidgetConfigureSubmitProgress.FINISHED) {
            onDone()
          }
        }

        ConverterWidgetConfigureRoute(
          viewModel = viewModel,
          navigateToSelectFrom = {
            navController.navigate(ConverterWidgetConfigureCreateNewRoute) {
              this.launchSingleTop = true
              this.restoreState = true
            }
          },
          onDone = viewModel::submitNewPairs,
        )
      }

      unittoStackedComposable<ConverterWidgetConfigureCreateNewRoute> { backStackEntry ->
        val viewModel = koinViewModel<ConverterWidgetConfigureCreateNewViewModel>()

        val selectFromResult =
          backStackEntry.savedStateHandle
            .getStateFlow<String?>(SELECT_UNIT_FROM_RESULT, null)
            .collectAsStateWithLifecycle()

        val selectToResult =
          backStackEntry.savedStateHandle
            .getStateFlow<String?>(SELECT_UNIT_TO_RESULT, null)
            .collectAsStateWithLifecycle()

        LaunchedEffect(selectFromResult.value) {
          val result = selectFromResult.value ?: return@LaunchedEffect
          viewModel.selectUnitFromById(result)
          backStackEntry.savedStateHandle.remove<String?>(SELECT_UNIT_FROM_RESULT)
        }

        LaunchedEffect(selectToResult.value) {
          val result = selectToResult.value ?: return@LaunchedEffect
          viewModel.selectUnitToById(result)
          backStackEntry.savedStateHandle.remove<String?>(SELECT_UNIT_TO_RESULT)
        }

        ConverterWidgetConfigureCreateNewRoute(
          viewModel = viewModel,
          navigateUp = navController::navigateUp,
          navigateToSelectFrom = {
            navController.navigate(ConverterWidgetConfigureSelectorRoute(null))
          },
          navigateToSelectTo = { unitFromId ->
            navController.navigate(ConverterWidgetConfigureSelectorRoute(unitFromId))
          },
          onAdd = { unitFromId, unitToId ->
            val result = AddUnitPairResult(unitFromId, unitToId)
            navController
              .getBackStackEntry<ConverterWidgetConfigureStartRoute>()
              .savedStateHandle[SELECT_UNIT_ADD_PAIR_RESULT] = Json.encodeToString(result)
            navController.navigateUp()
          },
        )
      }

      unittoStackedComposable<ConverterWidgetConfigureSelectorRoute> {
        ConverterWidgetConfigureSelectorRoute(
          navigateUp = navController::navigateUp,
          onClick = { unitId ->
            val unitFromIdFromArgs = it.toRoute<ConverterWidgetConfigureSelectorRoute>().unitFromId
            val isSelectFrom = unitFromIdFromArgs == null
            val key = if (isSelectFrom) SELECT_UNIT_FROM_RESULT else SELECT_UNIT_TO_RESULT

            navController
              .getBackStackEntry<ConverterWidgetConfigureCreateNewRoute>()
              .savedStateHandle[key] = unitId
            navController.navigateUp()
          },
        )
      }
    }
  }
}

private const val SELECT_UNIT_ADD_PAIR_RESULT = "SELECT_UNIT_ADD_PAIR_RESULT"
private const val SELECT_UNIT_FROM_RESULT = "SELECT_UNIT_FROM_RESULT"
private const val SELECT_UNIT_TO_RESULT = "SELECT_UNIT_TO_RESULT"

@Serializable internal data class AddUnitPairResult(val unitFromId: String, val unitToId: String)

@Serializable
private data object ConverterWidgetConfigureGraphRoute : Route {
  override val id = "converter_widget_configure_route"
}

@Serializable
private data object ConverterWidgetConfigureStartRoute : Route {
  override val id = "converter_widget_configure_start_route"
}

@Serializable
private data object ConverterWidgetConfigureCreateNewRoute : Route {
  override val id = "converter_widget_configure_create_new_route"
}

@Serializable
internal data class ConverterWidgetConfigureSelectorRoute(val unitFromId: String?) : Route {
  override val id = "converter_widget_configure_selector_route"
}
