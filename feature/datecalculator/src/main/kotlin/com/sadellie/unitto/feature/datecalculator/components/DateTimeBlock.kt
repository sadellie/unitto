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

package com.sadellie.unitto.feature.datecalculator.components

import android.text.format.DateFormat.is24HourFormat
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.designsystem.LocalLocale
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.ui.ListItemExpressiveDefaults
import com.sadellie.unitto.core.ui.datetime.formatDateWeekDayMonthYear
import com.sadellie.unitto.core.ui.datetime.formatTime
import com.sadellie.unitto.feature.datecalculator.ZonedDateTimeUtils
import java.time.ZonedDateTime

@Composable
internal fun DateTimeBlock(
  modifier: Modifier = Modifier,
  title: String,
  onTimeClick: () -> Unit = {},
  onDateClick: () -> Unit = {},
  onLongClick: () -> Unit = {},
  dateTime: ZonedDateTime,
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.Start,
    verticalArrangement = ListItemExpressiveDefaults.ListArrangement,
  ) {
    Column(
      modifier =
        Modifier.clip(ListItemExpressiveDefaults.firstShape)
          .combinedClickable(onClick = onTimeClick, onLongClick = onLongClick)
          .background(MaterialTheme.colorScheme.secondaryContainer)
          .padding(
            start = Sizes.large,
            end = Sizes.large,
            top = Sizes.large,
            bottom = Sizes.extraSmall,
          )
          .fillMaxWidth()
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.labelMedium,
        maxLines = 1,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
      )
      val is24Hour = is24HourFormat(LocalContext.current)
      val locale = LocalLocale.current
      val formattedTime = remember(is24Hour, dateTime) { dateTime.formatTime(locale, is24Hour) }
      AnimatedText(
        modifier = Modifier,
        targetState = formattedTime,
        style = MaterialTheme.typography.displaySmall,
      )
    }
    val locale1 = LocalLocale.current
    val formattedDate = remember(locale1) { dateTime.formatDateWeekDayMonthYear(locale1) }
    AnimatedText(
      modifier =
        Modifier.clip(ListItemExpressiveDefaults.lastShape)
          .combinedClickable(onClick = onDateClick, onLongClick = onLongClick)
          .background(MaterialTheme.colorScheme.secondaryContainer)
          .padding(Sizes.large)
          .fillMaxWidth(),
      targetState = formattedDate,
      style = MaterialTheme.typography.bodyLarge,
    )
  }
}

@Composable
private fun AnimatedText(modifier: Modifier, targetState: String, style: TextStyle) {
  AnimatedContent(
    modifier = modifier,
    targetState = targetState,
    transitionSpec = {
      slideInVertically { height -> height } + fadeIn() togetherWith
        slideOutVertically { height -> -height } + fadeOut() using
        SizeTransform()
    },
    label = "Animated date",
  ) { text ->
    Text(
      text = text,
      style = style,
      maxLines = 1,
      color = MaterialTheme.colorScheme.onSecondaryContainer,
    )
  }
}

@Preview
@Composable
fun DateTimeBlockPreview() {
  DateTimeBlock(
    modifier = Modifier.width(224.dp),
    title = "End",
    dateTime = ZonedDateTimeUtils.nowWithMinutes(),
  )
}
