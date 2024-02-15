/*
 * Unitto is a calculator for Android
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

package com.sadellie.unitto.core.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sadellie.unitto.core.ui.LocalWindowSize
import com.sadellie.unitto.core.ui.WindowHeightSizeClass
import com.sadellie.unitto.core.ui.WindowWidthSizeClass

/**
 * Template screen. Uses [Scaffold] and [CenterAlignedTopAppBar]
 *
 * @param modifier See [Scaffold]
 * @param title See [CenterAlignedTopAppBar]
 * @param navigationIcon See [CenterAlignedTopAppBar]
 * @param actions See [CenterAlignedTopAppBar]
 * @param colors See [CenterAlignedTopAppBar]
 * @param floatingActionButton See [Scaffold]
 * @param scrollBehavior See [CenterAlignedTopAppBar]
 * @param content See [Scaffold]
 */
@Composable
fun ScaffoldWithTopBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AnimatedVisibility(
                visible = LocalWindowSize.current.heightSizeClass > WindowHeightSizeClass.Compact,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut(),
            ) {
                CenterAlignedTopAppBar(
                    title = title,
                    navigationIcon = {
                        if (LocalWindowSize.current.widthSizeClass != WindowWidthSizeClass.Expanded) {
                            navigationIcon()
                        }
                    },
                    actions = actions,
                    colors = colors,
                    scrollBehavior = scrollBehavior,
                )
            }
        },
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        content = content,
    )
}
