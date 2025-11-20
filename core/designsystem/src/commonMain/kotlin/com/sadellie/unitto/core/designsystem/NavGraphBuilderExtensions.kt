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

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.navigation3.ui.NavDisplay

fun unittoFadeIn(): EnterTransition = fadeIn(tween(ENTER_DURATION))

fun unittoFadeOut(): ExitTransition = fadeOut(tween(EXIT_DURATION))

@Suppress("UnusedReceiverParameter")
fun NavDisplay.stackedTransition(): Map<String, Any> {
  val screenFraction = 4
  val enterTransition =
    slideInHorizontally(initialOffsetX = { it / screenFraction }) + unittoFadeIn()
  val exitTransition =
    slideOutHorizontally(targetOffsetX = { -it / screenFraction }) + unittoFadeOut()
  val popEnterTransition =
    slideInHorizontally(initialOffsetX = { -it / screenFraction }) + unittoFadeIn()
  val popExitTransition =
    slideOutHorizontally(targetOffsetX = { it / screenFraction }) + unittoFadeOut()

  return NavDisplay.transitionSpec { enterTransition togetherWith exitTransition } +
    NavDisplay.popTransitionSpec { popEnterTransition togetherWith popExitTransition } +
    NavDisplay.predictivePopTransitionSpec { popEnterTransition togetherWith popExitTransition }
}

private const val ENTER_DURATION = 350
private const val EXIT_DURATION = 200
