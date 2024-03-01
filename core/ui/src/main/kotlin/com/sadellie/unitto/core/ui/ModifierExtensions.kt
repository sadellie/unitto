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

import android.os.Build
import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import com.sadellie.unitto.core.designsystem.LocalHapticPreference
import com.sadellie.unitto.core.designsystem.vibrate

fun Modifier.squashable(
  onClick: () -> Unit = {},
  onLongClick: (() -> Unit)? = null,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource,
  cornerRadiusRange: IntRange,
  role: Role = Role.Button,
  animationSpec: AnimationSpec<Int> = tween(),
) = composed {
  val isPressed by interactionSource.collectIsPressedAsState()
  val cornerRadius: Int by
    animateIntAsState(
      targetValue = if (isPressed) cornerRadiusRange.first else cornerRadiusRange.last,
      animationSpec = animationSpec,
      label = "Squashed animation",
    )

  this.clip(RoundedCornerShape(cornerRadius))
    .haptics(isPressed)
    .combinedClickable(
      onClick = onClick,
      onLongClick = onLongClick,
      interactionSource = interactionSource,
      indication = ripple(),
      role = role,
      enabled = enabled,
    )
}

fun Modifier.squashable(
  onClick: () -> Unit = {},
  onLongClick: (() -> Unit)? = null,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource,
  cornerRadiusRange: ClosedRange<Dp>,
  role: Role = Role.Button,
  animationSpec: AnimationSpec<Dp> = tween(),
) = composed {
  val view = LocalView.current
  val coroutineScope = rememberCoroutineScope()
  val allowVibration = LocalHapticPreference.current
  val isPressed by interactionSource.collectIsPressedAsState()
  val cornerRadius: Dp by
    animateDpAsState(
      targetValue = if (isPressed) cornerRadiusRange.start else cornerRadiusRange.endInclusive,
      animationSpec = animationSpec,
      label = "Squashed animation",
    )

  this.clip(RoundedCornerShape(cornerRadius))
    .haptics(isPressed)
    .pointerInput(Unit) {
      detectTapGestures(
        onLongPress = {
          vibrate(view, coroutineScope, allowVibration, HapticFeedbackConstants.LONG_PRESS)
        }
      )
    }
    .combinedClickable(
      onClick = onClick,
      onLongClick = onLongClick,
      interactionSource = interactionSource,
      indication = ripple(),
      role = role,
      enabled = enabled,
    )
}

fun Modifier.haptics(isPressed: Boolean) = composed {
  val view = LocalView.current
  val coroutineScope = rememberCoroutineScope()
  val allowVibration = LocalHapticPreference.current

  LaunchedEffect(isPressed, view, coroutineScope, allowVibration) {
    if (isPressed) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        vibrate(view, coroutineScope, allowVibration, HapticFeedbackConstants.KEYBOARD_PRESS)
      }
    }
  }

  this.pointerInput(view, allowVibration) {
    detectTapGestures(
      onLongPress = {
        vibrate(view, coroutineScope, allowVibration, HapticFeedbackConstants.LONG_PRESS)
      }
    )
  }
}
