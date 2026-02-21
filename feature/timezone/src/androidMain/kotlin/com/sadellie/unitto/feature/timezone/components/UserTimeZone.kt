/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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

package com.sadellie.unitto.feature.timezone.components

import android.text.format.DateFormat
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.designsystem.LocalLocale
import com.sadellie.unitto.core.designsystem.icons.symbols.History
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.ui.ListItemExpressiveDefaults
import com.sadellie.unitto.core.ui.datetime.formatDateDayMonthYear
import com.sadellie.unitto.core.ui.datetime.formatTimeAmPm
import com.sadellie.unitto.core.ui.datetime.formatTimeHours
import com.sadellie.unitto.core.ui.datetime.formatTimeMinutes
import com.sadellie.unitto.core.ui.datetime.formatZone
import com.sadellie.unitto.core.ui.datetimepicker.DateTimeDialogState
import com.sadellie.unitto.core.ui.datetimepicker.DateTimeDialogs
import java.time.ZonedDateTime
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.time_zone_reset

@Composable
internal fun UserTimeZone(
  modifier: Modifier,
  time: ZonedDateTime,
  onUpdateTime: (ZonedDateTime) -> Unit,
  onResetClick: () -> Unit,
  showReset: Boolean,
) {
  Row(
    modifier = modifier.height(IntrinsicSize.Min),
    horizontalArrangement = ListItemExpressiveDefaults.ListArrangement,
  ) {
    Column(
      modifier = Modifier.weight(1f).height(IntrinsicSize.Min),
      verticalArrangement = ListItemExpressiveDefaults.ListArrangement,
    ) {
      var dialogState by remember { mutableStateOf(DateTimeDialogState.NONE) }
      Column(
        Modifier.fillMaxWidth()
          .clip(ListItemExpressiveDefaults.firstShape)
          .clickable { dialogState = DateTimeDialogState.FROM_TIME }
          .background(MaterialTheme.colorScheme.tertiaryContainer)
          .padding(horizontal = 16.dp, vertical = 12.dp)
      ) {
        TimeZoneWithOffset(time)
        Time(time)
      }
      Box(
        Modifier.fillMaxWidth()
          .clip(ListItemExpressiveDefaults.lastShape)
          .clickable { dialogState = DateTimeDialogState.FROM_DATE }
          .background(MaterialTheme.colorScheme.tertiaryContainer)
          .padding(horizontal = 16.dp, vertical = 12.dp)
      ) {
        Date(time)
      }
      DateTimeDialogs(
        dialogState = dialogState,
        updateDialogState = { dialogState = it },
        date = time,
        updateDate = onUpdateTime,
        timeState = DateTimeDialogState.FROM_TIME,
      )
    }

    AnimatedVisibility(modifier = Modifier.fillMaxHeight(), visible = showReset) {
      ResetButton(modifier = Modifier.animateEnterExit().fillMaxHeight(), onClick = onResetClick)
    }
  }
}

@Composable
private fun TimeZoneWithOffset(time: ZonedDateTime) {
  Text(
    text = time.formatZone(LocalLocale.current),
    style = MaterialTheme.typography.bodyLarge,
    color = MaterialTheme.colorScheme.onTertiaryContainer,
  )
}

@Composable
private fun Time(time: ZonedDateTime) {
  Row(verticalAlignment = Alignment.Bottom) {
    val is24Hour = DateFormat.is24HourFormat(LocalContext.current)
    val locale = LocalLocale.current
    SlidingText(time.formatTimeHours(locale, is24Hour))
    TimeSeparator()
    SlidingText(time.formatTimeMinutes(locale))
    if (!is24Hour) {
      Spacer(Modifier.padding(4.dp))
      SlidingText(time.formatTimeAmPm(locale))
    }
  }
}

@Composable
private fun Date(time: ZonedDateTime) {
  Text(
    text = time.formatDateDayMonthYear(LocalLocale.current),
    style = MaterialTheme.typography.headlineMedium,
    color = MaterialTheme.colorScheme.onTertiaryContainer,
  )
}

@Composable
private fun ResetButton(modifier: Modifier, onClick: () -> Unit) {
  IconButton(
    modifier =
      modifier.width(
        IconButtonDefaults.mediumContainerSize(IconButtonDefaults.IconButtonWidthOption.Wide).width
      ),
    onClick = onClick,
    shapes = IconButtonDefaults.shapes(),
    colors =
      IconButtonDefaults.iconButtonColors(
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
      ),
  ) {
    Icon(
      imageVector = Symbols.History,
      contentDescription = stringResource(Res.string.time_zone_reset),
      modifier = Modifier.size(IconButtonDefaults.mediumIconSize),
    )
  }
}

@Composable
private fun SlidingText(text: String) {
  AnimatedContent(
    targetState = text,
    label = "user time change",
    transitionSpec = {
      slideInVertically { height -> height } + fadeIn() togetherWith
        slideOutVertically { height -> -height } + fadeOut() using
        SizeTransform()
    },
  ) { target ->
    Text(
      text = target,
      style = MaterialTheme.typography.displayLarge,
      color = MaterialTheme.colorScheme.onTertiaryContainer,
      overflow = TextOverflow.Visible,
      maxLines = 1,
    )
  }
}

@Composable
private fun TimeSeparator() {
  Text(
    text = ":",
    style = MaterialTheme.typography.displayLarge,
    color = MaterialTheme.colorScheme.onTertiaryContainer,
    overflow = TextOverflow.Visible,
    maxLines = 1,
  )
}

@Preview
@Composable
private fun PreviewUserTimeZone() {
  UserTimeZone(
    modifier = Modifier,
    time = ZonedDateTime.now(),
    onUpdateTime = {},
    onResetClick = {},
    showReset = true,
  )
}
