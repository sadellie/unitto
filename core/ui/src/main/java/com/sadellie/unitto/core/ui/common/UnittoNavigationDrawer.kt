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

package com.sadellie.unitto.core.ui.common

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.model.DrawerItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

// Why do I have to do it myself?
@Composable
fun UnittoModalNavigationDrawer(
    drawer: @Composable () -> Unit,
    modifier: Modifier,
    state: AnchoredDraggableState<UnittoDrawerState>,
    gesturesEnabled: Boolean,
    scope: CoroutineScope,
    content: @Composable () -> Unit,
) {
    Box(modifier.fillMaxSize()) {
        content()

        Scrim(
            open = state.isOpen,
            onClose = { if (gesturesEnabled) scope.launch { state.close() } },
            fraction = {
                fraction(state.anchors.minAnchor(), state.anchors.maxAnchor(), state.offset)
            },
            color = DrawerDefaults.scrimColor
        )

        // Drawer
        Box(Modifier
            .offset {
                IntOffset(
                    x = state
                        .requireOffset()
                        .roundToInt(), y = 0
                )
            }
            .anchoredDraggable(
                state = state,
                orientation = Orientation.Horizontal,
                enabled = gesturesEnabled or state.isOpen,
            )
            .padding(end = 18.dp) // Draggable when closed
        ) {
            drawer()
        }

    }
}

@Composable
private fun Scrim(
    open: Boolean,
    onClose: () -> Unit,
    fraction: () -> Float,
    color: Color,
) {
    val dismissDrawer = if (open) {
        Modifier.pointerInput(onClose) { detectTapGestures { onClose() } }
    } else {
        Modifier
    }

    Canvas(
        Modifier
            .fillMaxSize()
            .then(dismissDrawer)
    ) {
        drawRect(color, alpha = fraction())
    }
}

enum class UnittoDrawerState { OPEN, CLOSED }

@Composable
fun rememberUnittoDrawerState(
    initialValue: UnittoDrawerState = UnittoDrawerState.CLOSED,
): AnchoredDraggableState<UnittoDrawerState> {
    val minValue = -with(LocalDensity.current) { 360.dp.toPx() }
    val positionalThreshold = -minValue * 0.5f
    val velocityThreshold = with(LocalDensity.current) { 400.dp.toPx() }

    return remember {
        AnchoredDraggableState(
            initialValue = initialValue,
            anchors = DraggableAnchors {
                UnittoDrawerState.OPEN at 0F
                UnittoDrawerState.CLOSED at minValue
            },
            positionalThreshold = { positionalThreshold },
            velocityThreshold = { velocityThreshold },
            animationSpec = tween()
        )
    }
}

val AnchoredDraggableState<UnittoDrawerState>.isOpen
    get() = this.currentValue == UnittoDrawerState.OPEN

suspend fun AnchoredDraggableState<UnittoDrawerState>.close() {
    this.animateTo(UnittoDrawerState.CLOSED)
}

suspend fun AnchoredDraggableState<UnittoDrawerState>.open() {
    this.animateTo(UnittoDrawerState.OPEN)
}

private fun fraction(a: Float, b: Float, pos: Float) =
    ((pos - a) / (b - a)).coerceIn(0f, 1f)

@Preview(backgroundColor = 0xFFC8F7D4, showBackground = true, showSystemUi = true)
@Composable
private fun PreviewUnittoModalNavigationDrawer() {
    val drawerState = rememberUnittoDrawerState(initialValue = UnittoDrawerState.OPEN)
    val corScope = rememberCoroutineScope()

    UnittoModalNavigationDrawer(
        drawer = {
            UnittoDrawerSheet(
                modifier = Modifier,
                tabs = listOf(
                    DrawerItems.Calculator,
                    DrawerItems.Calculator,
                    DrawerItems.Calculator,
                ),
                currentDestination = DrawerItems.Calculator.destination.start,
                onItemClick = {}
            )
        },
        modifier = Modifier,
        state = drawerState,
        gesturesEnabled = true,
        scope = corScope,
        content = {
            Column {
                Text(text = "Content")
                Button(
                    onClick = { corScope.launch { drawerState.open() } }
                ) {
                    Text(text = "BUTTON")
                }
            }
        }
    )
}
