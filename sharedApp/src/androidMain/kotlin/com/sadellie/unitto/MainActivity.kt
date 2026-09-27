/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
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

package com.sadellie.unitto

import android.os.Bundle
import android.text.format.DateFormat
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.designsystem.HapticFeedbackManagerImpl
import com.sadellie.unitto.core.designsystem.LocalHapticFeedbackManager
import com.sadellie.unitto.core.designsystem.LocalWindowSize
import com.sadellie.unitto.core.designsystem.theme.LocalNumberTypography
import com.sadellie.unitto.core.designsystem.theme.numberTypographyUnitto
import com.sadellie.unitto.core.navigation.Route
import com.sadellie.unitto.core.ui.datetime.LocalPlatformDateFormatSettings
import com.sadellie.unitto.core.ui.datetime.PlatformDateFormatSettings
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory
import kotlinx.datetime.TimeZone

internal class MainActivity : AppCompatActivity() {

  @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
    val uri = this.intent.data
    val deepLinkRoute = uri?.let { Route.extractRouteFromDeeplink(uri.toString()) }
    val appGraph = getApplicationGraph(this.application)
    val appPrefs = appGraph.appPrefsRepository.prefs
    val themePrefs = appGraph.themePrefsRepository.prefs
    val platformDateFormatSettings =
      PlatformDateFormatSettings(
        is24Hour = DateFormat.is24HourFormat(this),
        timeZone = TimeZone.currentSystemDefault(),
        locale = Locale.current,
      )

    setContent {
      CompositionLocalProvider(LocalMetroViewModelFactory provides appGraph.metroViewModelFactory) {
        val appPrefsValue =
          appPrefs.collectAsStateWithLifecycle(null).value ?: return@CompositionLocalProvider
        val themePrefsValue =
          themePrefs.collectAsStateWithLifecycle(null).value ?: return@CompositionLocalProvider

        LaunchedEffect(appPrefsValue.enableKeepScreenOn) {
          val enableKeepScreenOn = appPrefsValue.enableKeepScreenOn
          if (enableKeepScreenOn) {
            this@MainActivity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
          } else {
            this@MainActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
          }
        }
        val view = LocalView.current
        val hapticFeedbackManager =
          remember(appPrefsValue.enableVibrations) {
            HapticFeedbackManagerImpl(view, appPrefsValue.enableVibrations)
          }

        val numberTypography = numberTypographyUnitto()
        CompositionLocalProvider(
          LocalNumberTypography provides numberTypography,
          LocalWindowSize provides calculateWindowSizeClass(this@MainActivity),
          LocalHapticFeedbackManager provides hapticFeedbackManager,
          LocalPlatformDateFormatSettings provides platformDateFormatSettings,
        ) {
          App(deepLinkRoute, appPrefsValue, themePrefsValue)
        }
      }
    }
  }
}
