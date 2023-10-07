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

import android.icu.util.TimeZone
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.MenuButton
import com.sadellie.unitto.core.ui.common.SettingsButton
import com.sadellie.unitto.core.ui.common.TimePickerDialog
import com.sadellie.unitto.core.ui.common.UnittoEmptyScreen
import com.sadellie.unitto.core.ui.common.UnittoScreenWithTopBar
import com.sadellie.unitto.data.model.timezone.FavoriteZone
import com.sadellie.unitto.feature.timezone.components.FavoriteTimeZoneItem
import com.sadellie.unitto.feature.timezone.components.UserTimeZone
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.N)
@Composable
internal fun TimeZoneRoute(
    viewModel: TimeZoneViewModel = hiltViewModel(),
    openMenu: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToAddTimeZone: (ZonedDateTime) -> Unit,
) {
    when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
        TimeZoneUIState.Loading -> UnittoEmptyScreen()
        is TimeZoneUIState.Ready -> {
            TimeZoneScreen(
                uiState = uiState,
                openMenu = openMenu,
                navigateToSettings = navigateToSettings,
                navigateToAddTimeZone = navigateToAddTimeZone,
                setCurrentTime = viewModel::setCurrentTime,
                setSelectedTime = viewModel::setSelectedTime,
                onDragEnd = viewModel::onDragEnd,
                delete = viewModel::delete,
                updateLabel = viewModel::updateLabel,
                selectTimeZone = viewModel::selectTimeZone,
                setDialogState = viewModel::setDialogState
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
private fun TimeZoneScreen(
    uiState: TimeZoneUIState.Ready,
    openMenu: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToAddTimeZone: (ZonedDateTime) -> Unit,
    setCurrentTime: () -> Unit,
    setSelectedTime: (ZonedDateTime) -> Unit,
    onDragEnd: (id: FavoriteZone, target: Int) -> Unit,
    delete: (FavoriteZone) -> Unit,
    updateLabel: (FavoriteZone, String) -> Unit,
    selectTimeZone: (FavoriteZone?) -> Unit,
    setDialogState: (TimeZoneDialogState) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var currentUserTime by remember(uiState.customUserTime) {
        mutableStateOf(
            uiState.customUserTime ?: uiState.userTimeZone.timeNow()
        )
    }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(uiState.customUserTime) {
        while ((uiState.customUserTime == null) and isActive) {
            currentUserTime = uiState.userTimeZone.timeNow()
            delay(1000)
        }
    }

    val copiedList = rememberUpdatedState(newValue = uiState.favorites) as MutableState
    val state = rememberReorderableLazyListState(
        onMove = { from, to ->
            // -1 because we use fake item. It fixes animation for the first item in list
            copiedList.value = copiedList.value
                .toMutableList()
                .apply {
                    add(to.index - 1, removeAt(from.index - 1))
                }
        },
        canDragOver = { draggedOver, _ ->
            // Don't allow dragging over fake item
            draggedOver.index > 0
        },
        onDragEnd = onDragEnd@{ from, to ->
            if (from == to) return@onDragEnd
            // There is some logic going on. I have no idea what I did here but it works
            val tz = copiedList.value.getOrNull(to - 1) ?: return@onDragEnd
            val targetInOldTz = uiState.favorites.getOrNull(to - 1) ?: return@onDragEnd

            onDragEnd(tz, targetInOldTz.position)
        }
    )

    UnittoScreenWithTopBar(
        title = { Text(stringResource(R.string.time_zone_title)) },
        navigationIcon = { MenuButton(openMenu) },
        actions = { SettingsButton(navigateToSettings) },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {
                    navigateToAddTimeZone(currentUserTime)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize),
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        scrollBehavior = scrollBehavior,
    ) { padding ->
        LazyColumn(
            state = state.listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .reorderable(state),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 124.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item("user time") {
                UserTimeZone(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    userTime = currentUserTime,
                    onClick = { setDialogState(TimeZoneDialogState.UserTimePicker(currentUserTime)) },
                    onResetClick = setCurrentTime,
                    showReset = uiState.customUserTime != null
                )
            }

            items(copiedList.value, { it.timeZone.id }) { item ->
                ReorderableItem(
                    reorderableState = state,
                    key = item.timeZone.id,
                ) { isDragging ->
                    val isSelected = uiState.selectedTimeZone == item

                    val transition = updateTransition(isDragging, label = "draggedTransition")

                    val itemPadding by transition.animateDp(label = "itemPadding") {
                        if (it) 8.dp else 0.dp
                    }

                    val elevation by transition.animateDp(label = "elevation") {
                        if (it) 8.dp else 2.dp
                    }

                    val cornerRadius by transition.animateInt(label = "cornerRadius") {
                        if (it) 25 else 15
                    }

                    FavoriteTimeZoneItem(
                        modifier = Modifier
                            .padding(itemPadding)
                            .clip(RoundedCornerShape(cornerRadius))
                            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(elevation))
                            .detectReorderAfterLongPress(state),
                        item = item,
                        fromTime = currentUserTime,
                        expanded = isSelected,
                        onClick = {
                            selectTimeZone(if (isSelected) null else item)
                        },
                        onDelete = { delete(item) },
                        onLabelClick = { setDialogState(TimeZoneDialogState.LabelEditPicker(item)) },
                        onPrimaryClick = { offsetTime ->
                            setDialogState(TimeZoneDialogState.FavoriteTimePicker(item, offsetTime))
                        },
                        isDragging = isDragging
                    )
                }
            }
        }
    }

    when (uiState.dialogState) {
        is TimeZoneDialogState.UserTimePicker -> {
            TimePickerDialog(
                hour = currentUserTime.hour,
                minute = currentUserTime.minute,
                onConfirm = { hour, minute ->
                    setSelectedTime(
                        currentUserTime
                            .withHour(hour)
                            .withMinute(minute)
                    )
                    setDialogState(TimeZoneDialogState.Nothing)
                },
                onCancel = { setDialogState(TimeZoneDialogState.Nothing) }
            )
        }

        is TimeZoneDialogState.FavoriteTimePicker -> {
            TimePickerDialog(
                hour = uiState.dialogState.time.hour,
                minute = uiState.dialogState.time.minute,
                onConfirm = { hour, minute ->
                    setSelectedTime(
                        uiState.dialogState.time
                            .withHour(hour)
                            .withMinute(minute)
                            .minusSeconds(uiState.dialogState.timeZone.timeZone.offsetSeconds)
                            .plusSeconds(uiState.userTimeZone.offsetSeconds)

                    )
                    setDialogState(TimeZoneDialogState.Nothing)
                },
                onCancel = { setDialogState(TimeZoneDialogState.Nothing) }
            )
        }

        is TimeZoneDialogState.LabelEditPicker -> {
            var tfv by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue(
                        text = uiState.dialogState.timeZone.label,
                        selection = TextRange(uiState.dialogState.timeZone.label.length)
                    )
                )
            }
            AlertDialog(
                title = { Text(text = stringResource(R.string.label_label)) },
                text = {
                    OutlinedTextField(
                        value = tfv,
                        onValueChange = { tfv = it },
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                    LaunchedEffect(Unit) {
                        awaitFrame()
                        focusRequester.requestFocus()
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            setDialogState(TimeZoneDialogState.Nothing)
                            updateLabel(uiState.dialogState.timeZone, tfv.text)
                        },
                        content = { Text(text = stringResource(R.string.ok_label)) }
                    )
                },
                dismissButton = {
                    TextButton(
                        onClick = { setDialogState(TimeZoneDialogState.Nothing) },
                        content = { Text(text = stringResource(R.string.cancel_label)) }
                    )
                },
                onDismissRequest = { setDialogState(TimeZoneDialogState.Nothing) },
            )
        }

        TimeZoneDialogState.Nothing -> Unit
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview
@Composable
fun PreviewTimeZoneScreen() {
    TimeZoneScreen(
        uiState = TimeZoneUIState.Ready(
            favorites = TimeZone
                .getAvailableIDs()
                .mapIndexed { index, tz ->
                    FavoriteZone(
                        timeZone = TimeZone.getTimeZone(tz),
                        position = index,
                        label = if (tz == "ACT") "label text" else ""
                    )
                },
            customUserTime = null,
            userTimeZone = TimeZone.getTimeZone("Africa/Addis_Ababa"),
            selectedTimeZone = null,
            dialogState = TimeZoneDialogState.Nothing
        ),
        openMenu = {},
        navigateToSettings = {},
        navigateToAddTimeZone = {},
        setCurrentTime = {},
        setSelectedTime = {},
        onDragEnd = { _, _ -> },
        delete = {},
        updateLabel = { _, _ -> },
        selectTimeZone = {},
        setDialogState = {}
    )
}
