/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2025 Elshan Agaev
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

package com.sadellie.unitto.core.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ListItemElevation
import androidx.compose.material3.ListItemShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sadellie.unitto.core.designsystem.icons.symbols.Check
import com.sadellie.unitto.core.designsystem.icons.symbols.Close
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.shapes.Sizes

@Composable
fun ListItemExpressive(
  headlineContent: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  supportingContent: @Composable (() -> Unit)? = null,
  leadingContent: @Composable (() -> Unit)? = null,
  trailingContent: @Composable (() -> Unit)? = null,
  secondaryContent: @Composable (() -> Unit),
  secondaryContentPadding: PaddingValues =
    PaddingValues(start = 56.dp, end = Sizes.large, bottom = Sizes.small),
  interactionSource: MutableInteractionSource? = null,
  colors: ListItemColors =
    ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceBright),
  shapes: ListItemShapes,
) {
  Column(modifier = modifier.clip(shapes.shape).background(colors.containerColor)) {
    ListItemExpressive(
      modifier = Modifier,
      content = headlineContent,
      supportingContent = supportingContent,
      leadingContent = leadingContent,
      trailingContent = trailingContent,
      colors = colors,
      onClick = {},
      interactionSource = interactionSource,
      shapes = ListItemDefaults.middleShapes,
    )

    Box(modifier = Modifier.fillMaxWidth().padding(secondaryContentPadding)) { secondaryContent() }
  }
}

@Composable
fun ListItemExpressive(
  modifier: Modifier = Modifier,
  headlineText: String,
  supportingText: String? = null,
  icon: ImageVector,
  iconDescription: String = headlineText,
  trailingContent: @Composable (() -> Unit)? = null,
  secondaryContent: @Composable (() -> Unit),
  secondaryContentPadding: PaddingValues =
    PaddingValues(start = 56.dp, end = Sizes.large, bottom = Sizes.small),
  interactionSource: MutableInteractionSource? = null,
  colors: ListItemColors =
    ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceBright),
  shapes: ListItemShapes,
) {
  Column(modifier = modifier.clip(shapes.shape).background(colors.containerColor)) {
    ListItemExpressive(
      modifier = Modifier,
      headlineText = headlineText,
      supportingText = supportingText,
      icon = icon,
      iconDescription = iconDescription,
      trailingContent = trailingContent,
      colors = colors,
      onClick = {},
      interactionSource = interactionSource,
      shapes = ListItemDefaults.middleShapes,
    )

    Box(modifier = Modifier.fillMaxWidth().padding(secondaryContentPadding)) { secondaryContent() }
  }
}

@Composable
fun ListItemExpressive(
  modifier: Modifier = Modifier,
  headlineText: String,
  supportingText: String? = null,
  icon: ImageVector,
  iconDescription: String = headlineText,
  trailingContent: @Composable (() -> Unit)? = null,
  onClick: (() -> Unit) = {},
  interactionSource: MutableInteractionSource? = null,
  colors: ListItemColors =
    ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceBright),
  shapes: ListItemShapes,
) =
  ListItemExpressive(
    onClick = onClick,
    shapes = shapes,
    modifier = modifier,
    leadingContent = {
      Icon(
        imageVector = icon,
        contentDescription = iconDescription,
        modifier = Modifier.size(24.dp),
      )
    },
    trailingContent = trailingContent,
    supportingContent = supportingText?.let { { Text(it) } },
    content = { Text(headlineText) },
    interactionSource = interactionSource,
    colors = colors,
  )

@Composable
fun ListItemExpressive(
  modifier: Modifier = Modifier,
  headlineText: String,
  icon: ImageVector,
  iconDescription: String = headlineText,
  supportingText: String? = null,
  switchState: Boolean,
  onSwitchChange: (Boolean) -> Unit,
  colors: ListItemColors =
    ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceBright),
  shapes: ListItemShapes,
) {
  val interactionSource = remember { MutableInteractionSource() }
  ListItemExpressive(
    modifier = modifier,
    onClick = { onSwitchChange(!switchState) },
    interactionSource = interactionSource,
    content = { Text(headlineText) },
    shapes = shapes,
    supportingContent = supportingText?.let { { Text(supportingText) } },
    leadingContent = { Icon(icon, contentDescription = iconDescription) },
    verticalAlignment = Alignment.CenterVertically,
    trailingContent = {
      Switch(
        checked = switchState,
        onCheckedChange = { onSwitchChange(it) },
        interactionSource = interactionSource,
        thumbContent = {
          val icon = remember(switchState) { if (switchState) Symbols.Check else Symbols.Close }
          AnimatedContent(icon) { currentIcon ->
            Icon(
              imageVector = currentIcon,
              contentDescription = null,
              modifier = Modifier.size(SwitchDefaults.IconSize),
            )
          }
        },
      )
    },
    colors = colors,
  )
}

private object UnittoListItemDefaults {
  val headlineTextStyle: TextStyle
    @Stable
    @Composable
    get() =
      MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight(HEADLINE_TEXT_FONT_WEIGHT))

  val supportingTextStyle: TextStyle
    @Stable @Composable get() = MaterialTheme.typography.bodyMedium.copy(lineHeight = 16.sp)

  private const val HEADLINE_TEXT_FONT_WEIGHT = 450
}

@Composable
fun ListItemExpressive(
  onClick: () -> Unit,
  shapes: ListItemShapes,
  modifier: Modifier = Modifier,
  selected: Boolean = false,
  enabled: Boolean = true,
  leadingContent: @Composable (() -> Unit)? = null,
  trailingContent: @Composable (() -> Unit)? = null,
  overlineContent: @Composable (() -> Unit)? = null,
  supportingContent: @Composable (() -> Unit)? = null,
  verticalAlignment: Alignment.Vertical = ListItemDefaults.verticalAlignment(),
  onLongClick: (() -> Unit)? = null,
  onLongClickLabel: String? = null,
  colors: ListItemColors =
    ListItemDefaults.segmentedColors(containerColor = MaterialTheme.colorScheme.surfaceBright),
  elevation: ListItemElevation = ListItemDefaults.elevation(),
  contentPadding: PaddingValues = ListItemDefaults.ContentPadding,
  interactionSource: MutableInteractionSource? = null,
  content: @Composable () -> Unit,
) {
  SegmentedListItem(
    selected = selected,
    onClick = onClick,
    shapes = shapes,
    modifier = modifier,
    enabled = enabled,
    leadingContent = leadingContent,
    trailingContent = trailingContent,
    overlineContent = overlineContent,
    supportingContent =
      supportingContent?.let {
        {
          ProvideStyle(
            color = colors.supportingContentColor,
            textStyle = UnittoListItemDefaults.supportingTextStyle,
            content = it,
          )
        }
      },
    verticalAlignment = verticalAlignment,
    onLongClick = onLongClick,
    onLongClickLabel = onLongClickLabel,
    colors = colors,
    elevation = elevation,
    contentPadding = contentPadding,
    interactionSource = interactionSource,
    content = {
      ProvideStyle(
        color = colors.contentColor,
        textStyle = UnittoListItemDefaults.headlineTextStyle,
        content = content,
      )
    },
  )
}

@Composable
fun ListItemDefaults.listedShapes(indexInList: Int, listSize: Int): ListItemShapes {
  return segmentedShapes(
    index = indexInList,
    count = listSize,
    defaultShapes = shapes(if (listSize == 1) singleShape else middleShape),
  )
}

@Stable
val ListItemDefaults.firstShapes: ListItemShapes
  @Composable get() = shapes(ListItemDefaults.firstShape)

@Stable
val ListItemDefaults.middleShapes: ListItemShapes
  @Composable get() = shapes(ListItemDefaults.middleShape)

@Stable
val ListItemDefaults.lastShapes: ListItemShapes
  @Composable get() = shapes(ListItemDefaults.lastShape)

@Stable
val ListItemDefaults.singleShapes: ListItemShapes
  @Composable get() = shapes(ListItemDefaults.singleShape)

@Suppress("UnusedReceiverParameter")
@Stable
val ListItemDefaults.rectangleShapes: ListItemShapes
  get() =
    ListItemShapes(
      RectangleShape,
      RectangleShape,
      RectangleShape,
      RectangleShape,
      RectangleShape,
      RectangleShape,
    )

@Suppress("UnusedReceiverParameter")
@Stable
val ListItemDefaults.firstShape: Shape
  get() = RoundedCornerShape(Sizes.large, Sizes.large, Sizes.extraSmall, Sizes.extraSmall)

@Suppress("UnusedReceiverParameter")
@Stable
val ListItemDefaults.middleShape: Shape
  get() = RoundedCornerShape(Sizes.extraSmall)

@Suppress("UnusedReceiverParameter")
@Stable
val ListItemDefaults.lastShape: Shape
  get() = RoundedCornerShape(Sizes.extraSmall, Sizes.extraSmall, Sizes.large, Sizes.large)

@Suppress("UnusedReceiverParameter")
@Stable
val ListItemDefaults.singleShape: Shape
  get() = RoundedCornerShape(Sizes.large)

@Suppress("UnusedReceiverParameter")
@Stable
val ListItemDefaults.ListArrangement
  get() = Arrangement.spacedBy(2.dp)

@Composable
@Preview
private fun PreviewListItem2() {
  Column(verticalArrangement = ListItemDefaults.ListArrangement) {
    ListItemExpressive(
      shapes = ListItemDefaults.firstShapes,
      content = { Text("List item") },
      supportingContent = { Text("List item") },
      onClick = {},
    )
  }
}
