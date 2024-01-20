/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.os.ConfigurationCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.ui.LocalLocale
import com.sadellie.unitto.core.ui.LocalWindowSize
import com.sadellie.unitto.core.ui.calculateWindowSizeClass
import com.sadellie.unitto.core.ui.theme.LocalNumberTypography
import com.sadellie.unitto.core.ui.theme.NumberTypographySystem
import com.sadellie.unitto.core.ui.theme.NumberTypographyUnitto
import com.sadellie.unitto.data.model.repository.UserPreferencesRepository
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
internal class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var userPrefsRepository: UserPreferencesRepository

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val configuration = LocalConfiguration.current
            val prefs = userPrefsRepository.appPrefs
                .collectAsStateWithLifecycle(null).value

            val locale = remember(configuration) {
                ConfigurationCompat.getLocales(configuration).get(0) ?: Locale.getDefault()
            }

            val numbersTypography = remember(prefs?.systemFont) {
                if (prefs?.systemFont == true) NumberTypographySystem else NumberTypographyUnitto
            }

            CompositionLocalProvider(
                LocalLocale provides locale,
                LocalWindowSize provides calculateWindowSizeClass(this@MainActivity),
                LocalNumberTypography provides numbersTypography
            ) {
                App(prefs)
            }
        }
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        val window = (parent?.context as? Activity)?.window
        if (window != null) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
        return super.onCreateView(parent, name, context, attrs)
    }
}
