/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.feature.calculator

import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

internal enum class DragState { CLOSED, SMALL, OPEN }

@Composable
internal fun rememberDragState(
    initialValue: DragState = DragState.CLOSED,
    historyItem: Dp,
    max: Dp,
    enablePartialView: Boolean,
): AnchoredDraggableState<DragState> {
    val historyItemHeight = with(LocalDensity.current) { historyItem.toPx() }
    val maxHeight = with(LocalDensity.current) { max.toPx() }
    val anchors: DraggableAnchors<DragState> = if (enablePartialView) {
        DraggableAnchors {
            DragState.CLOSED at 0f
            DragState.SMALL at historyItemHeight
            DragState.OPEN at maxHeight
        }
    } else {
        DraggableAnchors {
            DragState.CLOSED at 0f
            DragState.OPEN at maxHeight
        }
    }

    return remember(historyItem, enablePartialView) {
        AnchoredDraggableState(
            initialValue = initialValue,
            anchors = anchors,
            positionalThreshold = { 0f },
            velocityThreshold = { 0f },
            animationSpec = tween()
        )
    }
}
