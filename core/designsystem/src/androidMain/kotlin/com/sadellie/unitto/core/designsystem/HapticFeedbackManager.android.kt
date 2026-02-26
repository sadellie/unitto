/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2025 Elshan Agaev
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

import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HapticFeedbackManagerImpl(private val view: View, private val enabled: Boolean) :
  HapticFeedbackManager {
  override fun vibrateGestureThresholdActivate(scope: CoroutineScope) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) return
    vibrate(scope, HapticFeedbackConstants.GESTURE_THRESHOLD_ACTIVATE)
  }

  override fun vibrateKeyboardPress(scope: CoroutineScope) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O_MR1) return
    vibrate(scope, HapticFeedbackConstants.KEYBOARD_PRESS)
  }

  override fun vibrateLongPress(scope: CoroutineScope) =
    vibrate(scope, HapticFeedbackConstants.LONG_PRESS)

  private fun vibrate(scope: CoroutineScope, feedbackConstant: Int) {
    // skip creating a job at all if disabled
    if (!enabled) return
    scope.launch {
      try {
        view.performHapticFeedback(feedbackConstant)
      } catch (_: SecurityException) {
        // missing android.permission.VIBRATE
      }
    }
  }
}
