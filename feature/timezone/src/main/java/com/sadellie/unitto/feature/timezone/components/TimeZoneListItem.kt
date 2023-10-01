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

package com.sadellie.unitto.feature.timezone.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.datetime.formatLocal
import com.sadellie.unitto.core.ui.datetime.formatOffset
import com.sadellie.unitto.data.model.UnittoTimeZone
import java.time.ZonedDateTime
import kotlin.math.roundToInt

@Composable
fun TimeZoneListItem(
    modifier: Modifier,
    timeZone: UnittoTimeZone,
    currentTime: ZonedDateTime,
    onClick: () -> Unit = {},
    onDelete: (UnittoTimeZone) -> Unit = {},
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    onSwipe: (UnittoTimeZone) -> Unit = {},
    draggableState: AnchoredDraggableState<Boolean>,
) {
    val offsetTime by remember(currentTime) { mutableStateOf(timeZone.offsetFrom(currentTime)) }
    val offsetTimeFormatted = offsetTime.formatOffset(currentTime)

    // TODO Animate deleting
    Box(
        modifier = modifier
            .heightIn(72.dp)
            .clip(RoundedCornerShape(25))
            .anchoredDraggable(
                state = draggableState,
                orientation = Orientation.Horizontal,
            ),
        contentAlignment = Alignment.Center,
    ) {
        // TODO Reveal animation
        Box(
            modifier = Modifier
                .clickable { onDelete(timeZone) }
                .background(MaterialTheme.colorScheme.errorContainer)
                .matchParentSize()
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(horizontal = 24.dp)
                    .size(32.dp),
                imageVector = Icons.Outlined.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
        }

        Row(
            modifier = Modifier
                .offset {
                    IntOffset(
                        y = 0,
                        x = draggableState
                            .requireOffset()
                            .roundToInt()
                    )
                }
                .clickable {}
                .background(color)
                .matchParentSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                // TODO Name
                Text(
                    text = timeZone.nameRes,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                AnimatedVisibility(
                    visible = offsetTimeFormatted != null,
                    label = "Nullable offset"
                ) {
                    Text(offsetTimeFormatted ?: "", style = MaterialTheme.typography.bodyMedium)
                }
            }
            Column {
                // Time
                AnimatedContent(
                    targetState = offsetTime.formatLocal(),
                    label = "Time change",
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut() using (SizeTransform(clip = false))
                    }
                ) { time ->
                    Text(time, style = MaterialTheme.typography.headlineMedium)
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewTimeZoneListItem() {

    val maxDrag = -with(LocalDensity.current) { 80.dp.toPx() }
    val draggableState = remember {
        AnchoredDraggableState(
            initialValue = false,
            anchors = DraggableAnchors {
                false at 0f
                true at maxDrag
            },
            positionalThreshold = { 0f },
            velocityThreshold = { 0f },
            animationSpec = tween(),
            confirmValueChange = { true }
        )
    }

    TimeZoneListItem(
        modifier = Modifier,
        timeZone = UnittoTimeZone(
            id = "timezone",
            offsetSeconds = -10800,
            nameRes = "Time zone"
        ),
        currentTime = ZonedDateTime.now(),
        draggableState = draggableState
    )
}
