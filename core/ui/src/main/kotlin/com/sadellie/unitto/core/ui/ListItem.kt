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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.designsystem.icons.symbols.Check
import com.sadellie.unitto.core.designsystem.icons.symbols.Close
import com.sadellie.unitto.core.designsystem.icons.symbols.Help
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
  colors: ListItemColors =
    ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceBright),
  shape: Shape,
) {
  Column(modifier = modifier.clip(shape).background(colors.containerColor)) {
    ListItemExpressive(
      modifier = Modifier,
      headlineContent = headlineContent,
      supportingContent = supportingContent,
      leadingContent = leadingContent,
      trailingContent = trailingContent,
      colors = colors,
      shape = RectangleShape,
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
  colors: ListItemColors =
    ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceBright),
  shape: Shape,
) =
  ListItemExpressive(
    modifier = modifier,
    headlineContent = { Text(headlineText) },
    supportingContent = supportingText?.let { { Text(it) } },
    leadingContent = {
      Icon(
        imageVector = icon,
        contentDescription = iconDescription,
        modifier = Modifier.size(24.dp),
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    },
    trailingContent = trailingContent,
    shape = shape,
    colors = colors,
  )

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
  colors: ListItemColors =
    ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceBright),
  shape: Shape,
) {
  Column(modifier = modifier.clip(shape).background(colors.containerColor)) {
    ListItemExpressive(
      modifier = Modifier,
      headlineText = headlineText,
      supportingText = supportingText,
      icon = icon,
      iconDescription = iconDescription,
      trailingContent = trailingContent,
      colors = colors,
      shape = RectangleShape,
    )
    Box(modifier = Modifier.fillMaxWidth().padding(secondaryContentPadding)) { secondaryContent() }
  }
}

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
  shape: Shape,
) {
  val interactionSource = remember { MutableInteractionSource() }
  ListItemExpressive(
    modifier =
      modifier.clickable(
        onClick = { onSwitchChange(!switchState) },
        interactionSource = interactionSource,
        role = Role.Switch,
      ),
    headlineContent = { Text(headlineText) },
    supportingContent = supportingText?.let { { Text(supportingText) } },
    leadingContent = { Icon(icon, contentDescription = iconDescription) },
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
    shape = shape,
    colors = colors,
  )
}

@Composable
fun ListItemExpressive(
  headlineContent: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  supportingContent: @Composable (() -> Unit)? = null,
  leadingContent: @Composable (() -> Unit)? = null,
  trailingContent: @Composable (() -> Unit)? = null,
  colors: ListItemColors =
    ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceBright),
  shape: Shape,
) {
  Row(
    modifier =
      Modifier.clip(shape)
        .then(modifier)
        .background(colors.containerColor)
        .padding(horizontal = 16.dp)
        .heightIn(min = if (supportingContent == null) 56.dp else 72.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    leadingContent?.let { ProvideColor(color = colors.leadingIconColor, content = it) }

    Column(Modifier.weight(1f).padding(vertical = 8.dp)) {
      ProvideStyle(
        color = colors.headlineColor,
        textStyle = MaterialTheme.typography.bodyLarge,
        content = headlineContent,
      )
      supportingContent?.let {
        ProvideStyle(
          color = colors.supportingTextColor,
          textStyle = MaterialTheme.typography.bodyMedium,
          content = it,
        )
      }
    }
    trailingContent?.let { ProvideColor(color = colors.trailingIconColor, content = it) }
  }
}

fun ListItemDefaults.listedShaped(indexInList: Int, listSize: Int): Shape {
  val isFirst = indexInList == 0
  val isLast = indexInList == listSize - 1
  return when {
    isFirst && isLast -> singleShape
    isFirst -> firstShape
    isLast -> lastShape
    else -> middleShape
  }
}

val ListArrangement = Arrangement.spacedBy(2.dp)

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

@Preview
@Composable
fun PreviewListItem1() {
  Column {
    ListItemExpressive(
      modifier = Modifier,
      headlineContent = { Text("Headline") },
      supportingContent = { Text("Support") },
      leadingContent = { Icon(imageVector = Symbols.Help, contentDescription = null) },
      shape = ListItemDefaults.firstShape,
    )

    var radioState by rememberSaveable { mutableStateOf(false) }
    ListItemExpressive(
      modifier = Modifier,
      headlineContent = { Text("Headline") },
      leadingContent = {
        RadioButton(selected = radioState, onClick = { radioState = !radioState })
      },
      shape = ListItemDefaults.middleShape,
    )

    ListItemExpressive(
      icon = Symbols.Help,
      headlineText = "Text text",
      supportingText = "Support text support text support text support text",
      modifier = Modifier,
      trailingContent = {},
      iconDescription = "",
      shape = ListItemDefaults.middleShape,
    )

    var switchState by rememberSaveable { mutableStateOf(false) }
    ListItemExpressive(
      icon = Symbols.Help,
      headlineText = "Text text",
      supportingText = "Support text support text support text support text",
      modifier = Modifier,
      onSwitchChange = { switchState = !switchState },
      switchState = switchState,
      shape = ListItemDefaults.lastShape,
    )
  }
}
