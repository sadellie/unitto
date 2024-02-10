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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.LocalWindowSize
import com.sadellie.unitto.core.ui.WindowWidthSizeClass
import com.sadellie.unitto.core.ui.model.DrawerItem
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class DrawerValue { Open, Closed }

class DrawerState(
    initialValue: DrawerValue = DrawerValue.Closed,
) {
    internal val anchoredDraggableState = AnchoredDraggableState(
        initialValue = initialValue,
        positionalThreshold = { distance -> distance * 0.5f },
        velocityThreshold = { with(requireNotNull(density)) { 400.dp.toPx() } },
        animationSpec = tween()
    )

    val isOpen: Boolean
        get() = currentValue == DrawerValue.Open

    internal var density: Density? by mutableStateOf(null)

    private val currentValue: DrawerValue
        get() = anchoredDraggableState.currentValue

    suspend fun open() = anchoredDraggableState.animateTo(DrawerValue.Open)

    suspend fun close() = anchoredDraggableState.animateTo(DrawerValue.Closed)

    companion object {
        internal fun Saver() =
            Saver<DrawerState, DrawerValue>(
                save = { it.currentValue },
                restore = { DrawerState(it) }
            )
    }
}

@Composable
fun rememberDrawerState(
    initialValue: DrawerValue = DrawerValue.Closed,
): DrawerState {
    return rememberSaveable(saver = DrawerState.Saver()) {
        DrawerState(initialValue)
    }
}

@Composable
fun NavigationDrawer(
    modifier: Modifier,
    gesturesEnabled: Boolean,
    state: DrawerState = rememberDrawerState(),
    mainTabs: List<DrawerItem>,
    additionalTabs: List<DrawerItem>,
    currentDestination: String?,
    onItemClick: (DrawerItem) -> Unit,
    content: @Composable () -> Unit,
) {
    if (LocalWindowSize.current.widthSizeClass == WindowWidthSizeClass.Expanded) {
        PermanentNavigationDrawer(
            modifier = modifier,
            drawerContent = {
                PermanentDrawerSheet(
                    modifier = Modifier
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                ) {
                    SheetContent(
                        mainTabs = mainTabs,
                        additionalTabs = additionalTabs,
                        currentDestination = currentDestination,
                        onItemClick = onItemClick
                    )
                }
            },
            content = content
        )
    } else {
        UnittoModalNavigationDrawer(
            modifier = modifier,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                ) {
                    SheetContent(
                        mainTabs = mainTabs,
                        additionalTabs = additionalTabs,
                        currentDestination = currentDestination,
                        onItemClick = onItemClick
                    )
                }
            },
            gesturesEnabled = gesturesEnabled,
            state = state,
            content = content
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun UnittoModalNavigationDrawer(
    modifier: Modifier,
    drawerContent: @Composable () -> Unit,
    gesturesEnabled: Boolean,
    state: DrawerState = rememberDrawerState(),
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val drawerScope = rememberCoroutineScope()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        val drawerWidth = 360.dp
        val drawerWidthPx = with(density) { drawerWidth.toPx() }
        val minValue = -drawerWidthPx
        val maxValue = 0f

        SideEffect {
            state.density = density
            state.anchoredDraggableState.updateAnchors(
                DraggableAnchors {
                    DrawerValue.Closed at minValue
                    DrawerValue.Open at maxValue
                }
            )
        }

        content()

        // Drag handle
        Box(
            modifier
                .width(18.dp)
                .fillMaxHeight()
                .pointerInteropFilter { false } // Do not consume
                .anchoredDraggable(
                    state = state.anchoredDraggableState,
                    orientation = Orientation.Horizontal,
                    enabled = gesturesEnabled or state.isOpen,
                )
        )

        Scrim(
            open = state.isOpen,
            onClose = { if (gesturesEnabled) drawerScope.launch { state.close() } },
            fraction = {
                fraction(
                    minValue, maxValue, state.anchoredDraggableState.requireOffset()
                )
            },
        )

        // Drawer box
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = state
                            .anchoredDraggableState
                            .requireOffset()
                            .roundToInt(),
                        y = 0
                    )
                }
                .anchoredDraggable(
                    state = state.anchoredDraggableState,
                    orientation = Orientation.Horizontal,
                    enabled = gesturesEnabled or state.isOpen,
                )
        ) {
            drawerContent()
        }
    }
}

@Composable
private fun Scrim(
    open: Boolean,
    onClose: () -> Unit,
    fraction: () -> Float,
    color: Color = DrawerDefaults.scrimColor,
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

private fun fraction(a: Float, b: Float, pos: Float) =
    ((pos - a) / (b - a)).coerceIn(0f, 1f)

@Preview(
    backgroundColor = 0xFFC8F7D4,
    showBackground = true,
    device = "spec:width=320dp,height=500dp,dpi=320"
)
@Preview(
    backgroundColor = 0xFFC8F7D4,
    showBackground = true,
    device = "spec:width=440dp,height=500dp,dpi=440"
)
@Composable
private fun PreviewUnittoModalNavigationDrawerClose() {
    val corScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Open)

    NavigationDrawer(
        modifier = Modifier,
        state = drawerState,
        gesturesEnabled = true,
        mainTabs = DrawerItem.main,
        additionalTabs = DrawerItem.additional,
        currentDestination = DrawerItem.Calculator.start,
        onItemClick = {},
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
