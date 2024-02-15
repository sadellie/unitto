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

package com.sadellie.unitto.feature.glance.glance

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.glance.GlanceTheme
import androidx.glance.material3.ColorProviders
import com.sadellie.unitto.core.ui.theme.DarkThemeColors
import com.sadellie.unitto.core.ui.theme.LightThemeColors

@Composable
internal fun WidgetTheme(content: @Composable () -> Unit) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        GlanceTheme {
            content()
        }
    } else {
        GlanceTheme(
            colors = ColorProviders(light = LightThemeColors, dark = DarkThemeColors),
        ) {
            content()
        }
    }
