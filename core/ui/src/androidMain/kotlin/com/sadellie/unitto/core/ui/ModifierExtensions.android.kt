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

package com.sadellie.unitto.core.ui

import android.os.Build
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import com.sadellie.unitto.core.designsystem.LocalHapticFeedbackManager

internal actual fun Modifier.haptics(isPressed: Boolean) = composed {
  val coroutineScope = rememberCoroutineScope()
  val hapticFeedbackManager = LocalHapticFeedbackManager.current

  LaunchedEffect(isPressed, coroutineScope) {
    if (isPressed) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        hapticFeedbackManager.vibrateKeyboardPress(coroutineScope)
      }
    }
  }

  this.pointerInput(Unit) {
    detectTapGestures(onLongPress = { hapticFeedbackManager.vibrateLongPress(coroutineScope) })
  }
}
