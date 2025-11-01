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

package com.sadellie.unitto.feature.timezone

import android.icu.text.LocaleDisplayNames
import android.icu.text.TimeZoneNames
import android.icu.util.TimeZone
import android.icu.util.ULocale
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.designsystem.icons.symbols.Add
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.model.timezone.FavoriteZone
import com.sadellie.unitto.core.ui.DrawerButton
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListItemExpressiveDefaults
import com.sadellie.unitto.core.ui.ScaffoldWithTopBar
import com.sadellie.unitto.core.ui.datetimepicker.TimePickerDialog
import com.sadellie.unitto.core.ui.plus
import com.sadellie.unitto.feature.timezone.components.FavoriteTimeZoneItem
import com.sadellie.unitto.feature.timezone.components.UserTimeZone
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_cancel
import unitto.core.common.generated.resources.common_label
import unitto.core.common.generated.resources.common_ok
import unitto.core.common.generated.resources.time_zone_add_title
import unitto.core.common.generated.resources.time_zone_title
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.N)
@Composable
internal fun TimeZoneRoute(
  viewModel: TimeZoneViewModel = koinViewModel(),
  openDrawer: () -> Unit,
  navigateToAddTimeZone: (ZonedDateTime) -> Unit,
) {
  when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
    TimeZoneUIState.Loading -> EmptyScreen()
    is TimeZoneUIState.Ready -> {
      TimeZoneScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        navigateToAddTimeZone = navigateToAddTimeZone,
        setCurrentTime = viewModel::setCurrentTime,
        setSelectedTime = viewModel::setSelectedTime,
        onDragEnd = viewModel::updatePosition,
        delete = viewModel::delete,
        updateLabel = viewModel::updateLabel,
        selectTimeZone = viewModel::selectTimeZone,
        setDialogState = viewModel::setDialogState,
      )
    }
  }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
private fun TimeZoneScreen(
  uiState: TimeZoneUIState.Ready,
  openDrawer: () -> Unit,
  navigateToAddTimeZone: (ZonedDateTime) -> Unit,
  setCurrentTime: () -> Unit,
  setSelectedTime: (ZonedDateTime) -> Unit,
  onDragEnd: (updatedList: List<FavoriteZone>, tz: FavoriteZone) -> Unit,
  delete: (FavoriteZone) -> Unit,
  updateLabel: (FavoriteZone, String) -> Unit,
  selectTimeZone: (FavoriteZone?) -> Unit,
  setDialogState: (TimeZoneDialogState) -> Unit,
) {
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
  var currentUserTime by
    remember(uiState.customUserTime) {
      mutableStateOf(uiState.customUserTime ?: uiState.userTimeZone.timeNow())
    }

  val locale = ULocale.forLanguageTag(AppCompatDelegate.getApplicationLocales().toLanguageTags())

  val timeZoneNames = remember(locale) { TimeZoneNames.getInstance(locale) }
  val localeDisplayNames = remember(locale) { LocaleDisplayNames.getInstance(locale) }

  LaunchedEffect(uiState.customUserTime) {
    while ((uiState.customUserTime == null) and isActive) {
      currentUserTime = uiState.userTimeZone.timeNow()
      // update time every 5 seconds
      delay(USER_TIME_UPDATE_FREQUENCY_MS)
    }
  }

  var mutableFavorites by remember(uiState.favorites) { mutableStateOf(uiState.favorites) }
  val lazyListState = rememberLazyListState()
  val reorderableLazyListState =
    rememberReorderableLazyListState(
      lazyListState = lazyListState,
      onMove = { from, to ->
        // -1 because list header
        mutableFavorites =
          mutableFavorites.toMutableList().apply { add(to.index - 1, removeAt(from.index - 1)) }
      },
    )

  ScaffoldWithTopBar(
    title = { Text(stringResource(Res.string.time_zone_title)) },
    navigationIcon = { DrawerButton(openDrawer) },
    floatingActionButton = {
      LargeFloatingActionButton(onClick = { navigateToAddTimeZone(currentUserTime) }) {
        Icon(
          imageVector = Symbols.Add,
          contentDescription = stringResource(Res.string.time_zone_add_title),
          modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize),
        )
      }
    },
    floatingActionButtonPosition = FabPosition.Center,
    scrollBehavior = scrollBehavior,
  ) { padding ->
    LazyColumn(
      state = lazyListState,
      modifier = Modifier.fillMaxSize(),
      contentPadding =
        padding + PaddingValues(start = Sizes.large, end = Sizes.large, bottom = 124.dp),
      verticalArrangement = ListItemExpressiveDefaults.ListArrangement,
    ) {
      item(key = "user time", contentType = ContentType.USER_TIME) {
        UserTimeZone(
          modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
          userTime = currentUserTime,
          onClick = { setDialogState(TimeZoneDialogState.UserTimePicker(currentUserTime)) },
          onResetClick = setCurrentTime,
          showReset = uiState.customUserTime != null,
        )
      }

      itemsIndexed(
        items = mutableFavorites,
        key = { index, item -> item.timeZone.id },
        contentType = { _, _ -> ContentType.ITEM },
      ) { index, item ->
        ReorderableItem(reorderableLazyListState, item.timeZone.id) { isDragging ->
          val isSelected = uiState.selectedTimeZone == item
          val transition = updateTransition(isDragging, label = "draggedTransition")
          val itemPadding by transition.animateDp(label = "itemPadding") { if (it) 8.dp else 0.dp }
          val background by
            transition.animateColor(label = "background") {
              if (it) MaterialTheme.colorScheme.surfaceContainerHighest
              else MaterialTheme.colorScheme.surfaceBright
            }
          val cornerRadius by
            transition.animateDp(label = "cornerRadius") { if (it) Sizes.large else 0.dp }

          FavoriteTimeZoneItem(
            modifier =
              Modifier.padding(itemPadding)
                .clip(RoundedCornerShape(cornerRadius))
                .clip(ListItemExpressiveDefaults.listedShaped(index, mutableFavorites.size))
                .background(background)
                .longPressDraggableHandle(onDragStopped = { onDragEnd(mutableFavorites, item) }),
            item = item,
            fromTime = currentUserTime,
            expanded = isSelected,
            onClick = { selectTimeZone(if (isSelected) null else item) },
            onDelete = { delete(item) },
            onLabelClick = { setDialogState(TimeZoneDialogState.LabelEditPicker(item)) },
            onPrimaryClick = { offsetTime ->
              setDialogState(TimeZoneDialogState.FavoriteTimePicker(item, offsetTime))
            },
            isDragging = isDragging,
            timeZoneNames = timeZoneNames,
            localeDisplayNames = localeDisplayNames,
          )
        }
      }
    }
  }

  TimeZoneDialog(
    dialogState = uiState.dialogState,
    currentUserTime = currentUserTime,
    setSelectedTime = setSelectedTime,
    setDialogState = setDialogState,
    userTimeZone = uiState.userTimeZone,
    updateLabel = updateLabel,
  )
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
private fun TimeZoneDialog(
  dialogState: TimeZoneDialogState,
  currentUserTime: ZonedDateTime,
  setSelectedTime: (ZonedDateTime) -> Unit,
  setDialogState: (TimeZoneDialogState) -> Unit,
  userTimeZone: TimeZone,
  updateLabel: (FavoriteZone, String) -> Unit,
) {
  when (dialogState) {
    is TimeZoneDialogState.UserTimePicker -> {
      TimePickerDialog(
        hour = currentUserTime.hour,
        minute = currentUserTime.minute,
        onConfirm = { hour, minute ->
          setSelectedTime(currentUserTime.withHour(hour).withMinute(minute))
          setDialogState(TimeZoneDialogState.Nothing)
        },
        onCancel = { setDialogState(TimeZoneDialogState.Nothing) },
      )
    }

    is TimeZoneDialogState.FavoriteTimePicker -> {
      TimePickerDialog(
        hour = dialogState.time.hour,
        minute = dialogState.time.minute,
        onConfirm = { hour, minute ->
          setSelectedTime(
            dialogState.time
              .withHour(hour)
              .withMinute(minute)
              .minusSeconds(dialogState.timeZone.timeZone.offsetSeconds)
              .plusSeconds(userTimeZone.offsetSeconds)
          )
          setDialogState(TimeZoneDialogState.Nothing)
        },
        onCancel = { setDialogState(TimeZoneDialogState.Nothing) },
      )
    }

    is TimeZoneDialogState.LabelEditPicker -> {
      val focusRequester = remember { FocusRequester() }
      val tfv by
        rememberSaveable(dialogState, stateSaver = TextFieldState.Saver) {
          mutableStateOf(
            TextFieldState(dialogState.timeZone.label, TextRange(dialogState.timeZone.label.length))
          )
        }
      AlertDialog(
        title = { Text(text = stringResource(Res.string.common_label)) },
        text = {
          OutlinedTextField(state = tfv, modifier = Modifier.focusRequester(focusRequester))
          LaunchedEffect(Unit) {
            awaitFrame()
            focusRequester.requestFocus()
          }
        },
        confirmButton = {
          TextButton(
            onClick = {
              setDialogState(TimeZoneDialogState.Nothing)
              updateLabel(dialogState.timeZone, tfv.text.toString())
            },
            content = { Text(text = stringResource(Res.string.common_ok)) },
          )
        },
        dismissButton = {
          TextButton(
            onClick = { setDialogState(TimeZoneDialogState.Nothing) },
            content = { Text(text = stringResource(Res.string.common_cancel)) },
          )
        },
        onDismissRequest = { setDialogState(TimeZoneDialogState.Nothing) },
      )
    }

    TimeZoneDialogState.Nothing -> Unit
  }
}

private enum class ContentType {
  USER_TIME,
  ITEM,
}

private const val USER_TIME_UPDATE_FREQUENCY_MS = 5_000L

@RequiresApi(Build.VERSION_CODES.N)
@Preview
@Composable
private fun PreviewTimeZoneScreen() {
  TimeZoneScreen(
    uiState =
      TimeZoneUIState.Ready(
        favorites =
          TimeZone.getAvailableIDs().mapIndexed { index, tz ->
            FavoriteZone(
              timeZone = TimeZone.getTimeZone(tz),
              position = index,
              label = if (tz == "ACT") "label text" else "",
            )
          },
        customUserTime = null,
        userTimeZone = TimeZone.getTimeZone("Africa/Addis_Ababa"),
        selectedTimeZone = null,
        dialogState = TimeZoneDialogState.Nothing,
      ),
    openDrawer = {},
    navigateToAddTimeZone = {},
    setCurrentTime = {},
    setSelectedTime = {},
    onDragEnd = { _, _ -> },
    delete = {},
    updateLabel = { _, _ -> },
    selectTimeZone = {},
    setDialogState = {},
  )
}
