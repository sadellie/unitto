/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

package com.sadellie.unitto.core.designsystem

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlin.reflect.KClass
import kotlin.reflect.KType

/** @see NavGraphBuilder.composable */
inline fun <reified T : Any> NavGraphBuilder.unittoComposable(
  typeMap: Map<KType, NavType<*>> = emptyMap(),
  deepLinks: List<NavDeepLink> = emptyList(),
  noinline enterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
    {
      unittoFadeIn()
    },
  noinline exitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
    {
      unittoFadeOut()
    },
  noinline popEnterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
    enterTransition,
  noinline popExitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
    exitTransition,
  noinline sizeTransform:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> SizeTransform?)? =
    null,
  noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
): Unit =
  composable<T>(
    typeMap = typeMap,
    deepLinks = deepLinks,
    enterTransition = enterTransition,
    exitTransition = exitTransition,
    popEnterTransition = popEnterTransition,
    popExitTransition = popExitTransition,
    sizeTransform = sizeTransform,
    content = content,
  )

inline fun <reified T : Any> NavGraphBuilder.unittoStackedComposable(
  typeMap: Map<KType, NavType<*>> = emptyMap(),
  deepLinks: List<NavDeepLink> = emptyList(),
  noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) =
  unittoComposable<T>(
    typeMap,
    deepLinks,
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

/** @see NavGraphBuilder.navigation */
inline fun <reified T : Any> NavGraphBuilder.unittoNavigation(
  startDestination: KClass<*>,
  typeMap: Map<KType, NavType<*>> = emptyMap(),
  deepLinks: List<NavDeepLink> = emptyList(),
  noinline enterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
    null,
  noinline exitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
    null,
  noinline popEnterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
    enterTransition,
  noinline popExitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
    exitTransition,
  noinline sizeTransform:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> SizeTransform?)? =
    null,
  noinline builder: NavGraphBuilder.() -> Unit,
): Unit =
  navigation<T>(
    startDestination = startDestination,
    typeMap = typeMap,
    deepLinks = deepLinks,
    enterTransition = enterTransition,
    exitTransition = exitTransition,
    popEnterTransition = popEnterTransition,
    popExitTransition = popExitTransition,
    sizeTransform = sizeTransform,
    builder = builder,
  )

private const val ENTER_DURATION = 350
private const val EXIT_DURATION = 200

fun unittoFadeIn(): EnterTransition = fadeIn(tween(ENTER_DURATION))

fun unittoFadeOut(): ExitTransition = fadeOut(tween(EXIT_DURATION))

fun unittoEnterTween(): FiniteAnimationSpec<IntOffset> = tween(ENTER_DURATION)

fun unittoExitTween(): FiniteAnimationSpec<IntOffset> = tween(EXIT_DURATION)
