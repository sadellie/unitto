/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

package com.sadellie.unitto.core.ui.common

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

/**
 * Provide style for content.
 *
 * @param color Content color.
 * @param textStyle Text style.
 * @param content Content that will get styled
 */
@Composable
fun ProvideStyle(
    color: Color,
    textStyle: TextStyle,
    content: @Composable () -> Unit,
) = CompositionLocalProvider(LocalContentColor provides color) {
    ProvideTextStyle(textStyle, content)
}

/**
 * Provide color for content.
 *
 * @param color Content color.
 * @param content Content that will get styled
 */
@Composable
fun ProvideColor(
    color: Color,
    content: @Composable () -> Unit,
) = CompositionLocalProvider(LocalContentColor provides color) {
    content()
}
