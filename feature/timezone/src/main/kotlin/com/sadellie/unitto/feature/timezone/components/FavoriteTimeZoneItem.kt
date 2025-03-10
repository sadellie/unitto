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

import android.icu.text.LocaleDisplayNames
import android.icu.text.TimeZoneNames
import android.icu.util.TimeZone
import android.icu.util.ULocale
import android.os.Build
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.common.offset
import com.sadellie.unitto.core.common.regionName
import com.sadellie.unitto.core.designsystem.LocalLocale
import com.sadellie.unitto.core.designsystem.icons.symbols.Delete
import com.sadellie.unitto.core.designsystem.icons.symbols.Edit
import com.sadellie.unitto.core.designsystem.icons.symbols.Schedule
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.shapes.Shapes
import com.sadellie.unitto.core.model.timezone.FavoriteZone
import com.sadellie.unitto.core.ui.ProvideColor
import com.sadellie.unitto.core.ui.ProvideStyle
import com.sadellie.unitto.core.ui.datetime.formatOffset
import com.sadellie.unitto.core.ui.datetime.formatTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.N)
@Composable
internal fun FavoriteTimeZoneItem(
  modifier: Modifier,
  item: FavoriteZone,
  fromTime: ZonedDateTime,
  isDragging: Boolean,
  expanded: Boolean,
  onClick: () -> Unit,
  onDelete: () -> Unit,
  onLabelClick: () -> Unit,
  onPrimaryClick: (ZonedDateTime) -> Unit,
  timeZoneNames: TimeZoneNames,
  localeDisplayNames: LocaleDisplayNames,
) {
  val context = LocalContext.current
  val locale = LocalLocale.current
  val is24Hour = DateFormat.is24HourFormat(context)
  var deleteAnimationRunning by remember { mutableStateOf(false) }
  val animatedAlpha by
    animateFloatAsState(
      label = "delete animation",
      targetValue = if (deleteAnimationRunning) 0f else 1f,
      finishedListener = { if (it == 0f) onDelete() },
    )

  val regionName =
    remember(timeZoneNames, localeDisplayNames) {
      item.timeZone.regionName(timeZoneNames, localeDisplayNames)
    }

  val offsetTime = remember(fromTime) { item.timeZone.offset(fromTime) }
  val offsetTimeFormatted = offsetTime.formatOffset(context, fromTime)

  Column(
    modifier =
      Modifier.graphicsLayer(alpha = animatedAlpha)
        .then(modifier)
        .clickable(enabled = !isDragging) { onClick() }
        .padding(vertical = 16.dp, horizontal = 12.dp)
  ) {
    TimeZoneLabel(label = item.label, expanded = expanded, onLabelClick = onLabelClick)
    Row(
      modifier = Modifier.padding().heightIn(min = 56.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      Column(
        modifier = Modifier.weight(1f).padding(2.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
      ) {
        Text(
          text = regionName,
          style = MaterialTheme.typography.bodyLarge,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis,
          color = MaterialTheme.colorScheme.onSurface,
        )
        AnimatedVisibility(visible = offsetTimeFormatted != null, label = "Nullable offset") {
          Text(
            text = offsetTimeFormatted ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }
      AnimatedContent(
        targetState = offsetTime.formatTime(locale, is24Hour),
        label = "Time change",
        transitionSpec = { fadeIn() togetherWith fadeOut() using (SizeTransform(clip = false)) },
      ) { time ->
        Text(
          text = time,
          style = MaterialTheme.typography.headlineMedium,
          color = MaterialTheme.colorScheme.onSurface,
        )
      }
    }

    AnimatedVisibility(visible = expanded) {
      Column {
        TimeZoneOption(
          title = stringResource(R.string.common_select_time),
          icon = Symbols.Schedule,
          contentDescription = stringResource(R.string.common_select_time),
          onClick = { onPrimaryClick(offsetTime) },
        )
        TimeZoneOption(
          title = stringResource(R.string.common_delete),
          icon = Symbols.Delete,
          contentDescription = stringResource(R.string.common_delete),
          onClick = { deleteAnimationRunning = true },
        )
      }
    }
  }
}

@Composable
private fun TimeZoneOption(
  title: String,
  icon: ImageVector,
  contentDescription: String,
  onClick: () -> Unit,
) =
  ProvideColor(MaterialTheme.colorScheme.onSurfaceVariant) {
    Row(
      modifier =
        Modifier.clip(Shapes.Small)
          .clickable { onClick() }
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Icon(imageVector = icon, contentDescription = contentDescription)
      Text(text = title)
    }
  }

@Composable
private fun TimeZoneLabel(label: String, expanded: Boolean, onLabelClick: () -> Unit) =
  ProvideStyle(
    color = MaterialTheme.colorScheme.onSurfaceVariant,
    textStyle = MaterialTheme.typography.bodyMedium,
  ) {
    AnimatedContent(
      label = "Expand animation",
      targetState = label.isBlank(),
      modifier = if (expanded) Modifier.clickable { onLabelClick() } else Modifier,
    ) { blank ->
      if (blank) {
        AnimatedVisibility(expanded) {
          Row(modifier = Modifier.height(24.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Symbols.Edit,
              // Not required because there is text next to it
              contentDescription = null,
              modifier = Modifier.padding(end = 8.dp),
            )
            Text(text = stringResource(R.string.common_add))
          }
        }
      } else {
        Row(modifier = Modifier.height(24.dp), verticalAlignment = Alignment.CenterVertically) {
          AnimatedVisibility(visible = expanded) {
            Icon(
              imageVector = Symbols.Edit,
              // Not required because there is text next to it
              contentDescription = null,
              modifier = Modifier.padding(end = 8.dp),
            )
          }
          Text(text = label)
        }
      }
    }
  }

private data class FavoriteTimeZoneItemParameter(val expanded: Boolean, val tz: FavoriteZone)

@RequiresApi(Build.VERSION_CODES.N)
private class FavoriteTimeZoneItemParameterProvider :
  PreviewParameterProvider<FavoriteTimeZoneItemParameter> {
  override val values: Sequence<FavoriteTimeZoneItemParameter>
    get() =
      sequenceOf(
        FavoriteTimeZoneItemParameter(
          expanded = false,
          tz = FavoriteZone(timeZone = TimeZone.getDefault(), position = 1, label = ""),
        ),
        FavoriteTimeZoneItemParameter(
          expanded = false,
          tz = FavoriteZone(timeZone = TimeZone.getDefault(), position = 1, label = "Some text"),
        ),
        FavoriteTimeZoneItemParameter(
          expanded = true,
          tz = FavoriteZone(timeZone = TimeZone.getDefault(), position = 1, label = ""),
        ),
        FavoriteTimeZoneItemParameter(
          expanded = true,
          tz = FavoriteZone(timeZone = TimeZone.getDefault(), position = 1, label = "Some text"),
        ),
      )
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview(showBackground = true, backgroundColor = 0xFFC1C9FF)
@Composable
private fun PreviewFavoriteTimeZones(
  @PreviewParameter(FavoriteTimeZoneItemParameterProvider::class) tz: FavoriteTimeZoneItemParameter
) {
  var expanded by remember { mutableStateOf(tz.expanded) }
  val locale = ULocale.getDefault()
  val timeZoneNames = remember(locale) { TimeZoneNames.getInstance(locale) }
  val localeDisplayNames = remember(locale) { LocaleDisplayNames.getInstance(locale) }

  FavoriteTimeZoneItem(
    modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer),
    item = tz.tz,
    fromTime =
      ZonedDateTime.parse(
        "2023-05-01T14:00+03:00[Africa/Addis_Ababa]",
        DateTimeFormatter.ISO_ZONED_DATE_TIME,
      ),
    expanded = expanded,
    onClick = { expanded = !expanded },
    onDelete = {},
    onPrimaryClick = {},
    onLabelClick = {},
    isDragging = false,
    timeZoneNames = timeZoneNames,
    localeDisplayNames = localeDisplayNames,
  )
}
