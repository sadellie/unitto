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

package com.sadellie.unitto.core.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

/**
 * @see NavGraphBuilder.composable
 */
fun NavGraphBuilder.unittoComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (
        @JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
    )? = { unittoFadeIn() },
    exitTransition: (
        @JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
    )? = { unittoFadeOut() },
    popEnterTransition: (
        @JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
    )? = enterTransition,
    popExitTransition: (
        @JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
    )? = exitTransition,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
): Unit = composable(
    route = route,
    arguments = arguments,
    deepLinks = deepLinks,
    enterTransition = enterTransition,
    exitTransition = exitTransition,
    popEnterTransition = popEnterTransition,
    popExitTransition = popExitTransition,
    content = content,
)

fun NavGraphBuilder.unittoStackedComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = {
            slideInHorizontally(
                animationSpec = unittoEnterTween(),
                initialOffsetX = { (it * 0.2f).toInt() },
            ) + unittoFadeIn()
        },
        exitTransition = {
            slideOutHorizontally(
                animationSpec = unittoExitTween(),
                targetOffsetX = { -(it * 0.2f).toInt() },
            ) + unittoFadeOut()
        },
        popEnterTransition = {
            slideInHorizontally(
                animationSpec = unittoEnterTween(),
                initialOffsetX = { -(it * 0.2f).toInt() },
            ) + unittoFadeIn()
        },
        popExitTransition = {
            slideOutHorizontally(
                animationSpec = unittoExitTween(),
                targetOffsetX = { (it * 0.2f).toInt() },
            ) + unittoFadeOut()
        },
        content = content,
    )
}

/**
 * @see NavGraphBuilder.navigation
 */
fun NavGraphBuilder.unittoNavigation(
    startDestination: String,
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        null,
    popEnterTransition: (
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
    )? = enterTransition,
    popExitTransition: (
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
    )? = exitTransition,
    builder: NavGraphBuilder.() -> Unit,
): Unit = navigation(
    startDestination = startDestination,
    route = route,
    arguments = arguments,
    deepLinks = deepLinks,
    enterTransition = enterTransition,
    exitTransition = exitTransition,
    popEnterTransition = popEnterTransition,
    popExitTransition = popExitTransition,
    builder = builder,
)

private const val ENTER_DURATION = 350
private const val EXIT_DURATION = 200

private fun unittoFadeIn(): EnterTransition = fadeIn(tween(ENTER_DURATION))
private fun unittoFadeOut(): ExitTransition = fadeOut(tween(EXIT_DURATION))
private fun unittoEnterTween(): FiniteAnimationSpec<IntOffset> = tween(ENTER_DURATION)
private fun unittoExitTween(): FiniteAnimationSpec<IntOffset> = tween(EXIT_DURATION)
