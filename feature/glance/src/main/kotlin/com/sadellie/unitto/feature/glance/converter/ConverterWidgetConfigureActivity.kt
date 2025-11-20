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
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.rememberCoroutineScope
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.navigation3.runtime.rememberNavBackStack
import com.sadellie.unitto.core.designsystem.theme.DarkThemeColors
import com.sadellie.unitto.core.designsystem.theme.LightThemeColors
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.rememberThemmoController
import kotlinx.coroutines.launch

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
        val backStack = rememberNavBackStack(ConverterWidgetConfigureStartRoute(appWidgetId))
        val coroutineScope = rememberCoroutineScope()
        ConverterWidgetConfigureNavigation(
          backStack = backStack,
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
