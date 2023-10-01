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

package com.sadellie.unitto.feature.timezone

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.MenuButton
import com.sadellie.unitto.core.ui.common.SettingsButton
import com.sadellie.unitto.core.ui.common.TimePickerDialog
import com.sadellie.unitto.core.ui.common.UnittoScreenWithTopBar
import com.sadellie.unitto.core.ui.common.squashable
import com.sadellie.unitto.core.ui.datetime.UnittoDateTimeFormatter
import com.sadellie.unitto.core.ui.datetime.formatLocal
import com.sadellie.unitto.core.ui.theme.TypographyUnitto
import com.sadellie.unitto.core.ui.theme.DarkThemeColors
import com.sadellie.unitto.core.ui.theme.LightThemeColors
import com.sadellie.unitto.data.model.UnittoTimeZone
import com.sadellie.unitto.feature.timezone.components.TimeZoneListItem
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.rememberThemmoController
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import java.time.ZonedDateTime

@Composable
internal fun TimeZoneRoute(
    viewModel: TimeZoneViewModel = hiltViewModel(),
    navigateToMenu: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToAddTimeZone: (ZonedDateTime?) -> Unit,
) {
    val uiState = viewModel.timeZoneUIState.collectAsStateWithLifecycle()

    TimeZoneScreen(
        uiState = uiState.value,
        navigateToMenu = navigateToMenu,
        navigateToSettings = navigateToSettings,
        navigateToAddTimeZone = {
            navigateToAddTimeZone(
                if (uiState.value.updateTime) null
                else uiState.value.userTime
            )
        },
        onDragEnd = viewModel::onDragEnd,
        onDelete = viewModel::onDelete,
        setSelectedTime = viewModel::setCustomTime,
        setCurrentTime = viewModel::setCurrentTime,
        resetTime = viewModel::resetTime,
    )
}

@Composable
private fun TimeZoneScreen(
    uiState: TimeZoneUIState,
    navigateToMenu: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToAddTimeZone: () -> Unit,
    onDragEnd: (String, String) -> Unit,
    onDelete: (UnittoTimeZone) -> Unit,
    setSelectedTime: (ZonedDateTime) -> Unit,
    setCurrentTime: () -> Unit,
    resetTime: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(uiState.updateTime) {
        while (uiState.updateTime and isActive) {
            setCurrentTime()
            delay(1000)
        }
    }

    val copiedList = rememberUpdatedState(newValue = uiState.list) as MutableState
    val state = rememberReorderableLazyListState(
        onMove = onMove@{ from, to ->
            // -1 because we use fake item. It fixes animation for the first item in list
            copiedList.value = copiedList.value
                .toMutableList()
                .apply {
                    add(to.index - 1, removeAt(from.index - 1))
                }
            onDragEnd(from.key as String, to.key as String)
        },
        canDragOver = { draggedOver, _ ->
            // Don't allow dragging over fake item
            draggedOver.index > 0
        }
    )
    // TODO Unswipe on dragging
    var swiped by remember<MutableState<UnittoTimeZone?>> { mutableStateOf(null) }
    var showTimeSelector by rememberSaveable { mutableStateOf(false) }
    val maxDrag = -with(LocalDensity.current) { 80.dp.toPx() }

    UnittoScreenWithTopBar(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        title = { Text(stringResource(R.string.time_zone_title)) },
        navigationIcon = { MenuButton(navigateToMenu) },
        actions = { SettingsButton(navigateToSettings) },
        floatingActionButton = {
            LargeFloatingActionButton(navigateToAddTimeZone) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize),
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        scrollBehavior = scrollBehavior,

        ) { paddingValues ->

        LazyColumn(
            state = state.listState,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxHeight()
                .reorderable(state)
                .detectReorderAfterLongPress(state),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 124.dp)
        ) {
            // This is a fake item. First item in list can not animated, so we do this magic fuckery
            item {
                UserTimeZone(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    userTime = uiState.userTime,
                    onClick = { showTimeSelector = true },
                    onResetClick = resetTime,
                    showReset = !uiState.updateTime,
                )
            }
            items(copiedList.value, { it.id }) { item ->
                ReorderableItem(state, key = item.id) { isDragging ->
                    val transition = updateTransition(isDragging, label = "draggedTransition")
                    val background by transition.animateColor(label = "background") {
                        if (it) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
                    }
                    val itemPadding by transition.animateDp(label = "itemPadding") {
                        if (it) 32.dp else 16.dp
                    }

                    val scope = rememberCoroutineScope()
                    val draggableState = rememberDraggableTimeZone(maxDrag) { swiped = item }

                    LaunchedEffect(swiped) {
                        if (swiped != item) scope.launch {
                            draggableState.animateTo(false)
                        }
                    }

                    TimeZoneListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = itemPadding),
                        timeZone = item,
                        currentTime = uiState.userTime,
                        onDelete = onDelete,
                        color = background,
                        onSwipe = {},
                        draggableState = draggableState,
                    )
                }
            }
        }
    }

    if (showTimeSelector) {
        TimePickerDialog(
            hour = uiState.userTime.hour,
            minute = uiState.userTime.minute,
            onConfirm = { hour, minute ->
                setSelectedTime(
                    uiState.userTime
                        .withHour(hour)
                        .withMinute(minute)
                )
                showTimeSelector = false
            },
            onDismiss = { showTimeSelector = false }
        )
    }
}

@Composable
private fun UserTimeZone(
    modifier: Modifier,
    userTime: ZonedDateTime,
    onClick: () -> Unit,
    onResetClick: () -> Unit,
    showReset: Boolean,
) {

    Row(
        modifier = modifier
            .squashable(
                onClick = onClick,
                onLongClick = onResetClick,
                cornerRadiusRange = 8.dp..32.dp,
                interactionSource = remember { MutableInteractionSource() }
            )
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = userTime.format(UnittoDateTimeFormatter.zoneFormatPattern),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            AnimatedContent(
                targetState = userTime.formatLocal(),
                label = "user time change",
                transitionSpec = {
                    slideInVertically { height -> height } + fadeIn() togetherWith
                            slideOutVertically { height -> -height } + fadeOut() using
                            SizeTransform()
                }
            ) { time ->
                Text(
                    text = time,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
            Text(
                text = userTime.format(UnittoDateTimeFormatter.dayMonthYear),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
        AnimatedVisibility(
            visible = showReset,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
        ) {
            IconButton(onResetClick) {
                Icon(
                    imageVector = Icons.Outlined.History,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                )
            }
        }
    }
}

@Composable
private fun rememberDraggableTimeZone(
    maxDrag: Float,
    onSwipe: () -> Unit,
) = remember {
    AnchoredDraggableState(
        initialValue = false,
        anchors = DraggableAnchors {
            false at 0f
            true at maxDrag
        },
        positionalThreshold = { it * 0.5f },
        velocityThreshold = { maxDrag },
        animationSpec = tween(),
        confirmValueChange = {
            onSwipe()
            true
        }
    )
}

@Preview
@Composable
fun PreviewTimeZoneScreen() {
    Themmo(
        themmoController = rememberThemmoController(
            lightColorScheme = LightThemeColors,
            darkColorScheme = DarkThemeColors,
        ),
        typography = TypographyUnitto,
    ) {
        TimeZoneScreen(
            uiState = TimeZoneUIState(
                list = List(50) {
                    UnittoTimeZone(
                        id = "timezone $it",
                        nameRes = "Time zone $it",
                    )
                }
            ),
            navigateToMenu = {},
            navigateToSettings = {},
            navigateToAddTimeZone = {},
            onDragEnd = { _, _ -> },
            onDelete = {},
            setSelectedTime = {},
            setCurrentTime = {},
            resetTime = {},
        )
    }
}
