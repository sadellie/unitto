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

package com.sadellie.unitto.feature.timezone.components

import android.text.format.DateFormat
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.designsystem.LocalLocale
import com.sadellie.unitto.core.designsystem.icons.symbols.History
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.ui.datetime.formatDateDayMonthYear
import com.sadellie.unitto.core.ui.datetime.formatTimeAmPm
import com.sadellie.unitto.core.ui.datetime.formatTimeHours
import com.sadellie.unitto.core.ui.datetime.formatTimeMinutes
import com.sadellie.unitto.core.ui.datetime.formatZone
import com.sadellie.unitto.core.ui.squashable
import java.time.ZonedDateTime

@Composable
internal fun UserTimeZone(
  modifier: Modifier,
  userTime: ZonedDateTime,
  onClick: () -> Unit,
  onResetClick: () -> Unit,
  showReset: Boolean,
) {
  val locale = LocalLocale.current
  val is24Hour = DateFormat.is24HourFormat(LocalContext.current)

  Row(
    modifier =
      modifier
        .squashable(
          onClick = onClick,
          onLongClick = onResetClick,
          cornerRadiusRange = 8.dp..32.dp,
          interactionSource = remember { MutableInteractionSource() },
        )
        .background(MaterialTheme.colorScheme.tertiaryContainer)
        .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    Column(Modifier.weight(1f)) {
      Text(
        text = userTime.formatZone(locale),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onTertiaryContainer,
      )

      Row(verticalAlignment = Alignment.Bottom) {
        SlidingText(userTime.formatTimeHours(locale, is24Hour))
        TimeSeparator()
        SlidingText(userTime.formatTimeMinutes(locale))
        Spacer(Modifier.padding(4.dp))
        if (!is24Hour) {
          SlidingText(userTime.formatTimeAmPm(locale))
        }
      }

      Text(
        text = userTime.formatDateDayMonthYear(locale),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onTertiaryContainer,
      )
    }
    AnimatedVisibility(
      visible = showReset,
      enter = scaleIn() + fadeIn(),
      exit = scaleOut() + fadeOut(),
    ) {
      IconButton(onResetClick) {
        Icon(
          imageVector = Symbols.History,
          contentDescription = stringResource(R.string.time_zone_reset),
          tint = MaterialTheme.colorScheme.onTertiaryContainer,
        )
      }
    }
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
    userTime = ZonedDateTime.now(),
    onClick = {},
    onResetClick = {},
    showReset = true,
  )
}
