/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

package com.sadellie.unitto.core.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    headlineContent: @Composable () -> Unit,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(start = 16.dp, end = 24.dp)
            .heightIn(min = if (supportingContent == null) 56.dp else 72.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leadingContent?.let {
            ProvideColor(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                content = it,
            )
        }

        Column(Modifier.weight(1f).padding(vertical = 8.dp)) {
            ProvideStyle(
                color = MaterialTheme.colorScheme.onSurface,
                textStyle = MaterialTheme.typography.bodyLarge,
                content = headlineContent,
            )
            supportingContent?.let {
                ProvideStyle(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    content = it,
                )
            }
        }
        trailingContent?.let {
            ProvideColor(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                content = it,
            )
        }
    }
}

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    headlineText: String,
    supportingText: String? = null,
    icon: ImageVector,
    iconDescription: String = headlineText,
    trailing: @Composable (() -> Unit)? = null,
) = ListItem(
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
    trailingContent = trailing,
)

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    headlineText: String,
    icon: ImageVector,
    iconDescription: String = headlineText,
    supportingText: String? = null,
    switchState: Boolean,
    onSwitchChange: (Boolean) -> Unit,
) = ListItem(
    modifier = modifier
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(),
            onClick = { onSwitchChange(!switchState) },
            role = Role.Switch,
        ),
    headlineText = headlineText,
    supportingText = supportingText,
    icon = icon,
    iconDescription = iconDescription,
    trailing = {
        Switch(
            checked = switchState,
            onCheckedChange = { onSwitchChange(it) },
        )
    },
)

@Preview
@Composable
fun PreviewListItem1() {
    Column {
        ListItem(
            modifier = Modifier,
            headlineContent = { Text("Headline") },
            supportingContent = { Text("Support") },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                )
            },
        )

        ListItem(
            modifier = Modifier,
            headlineContent = { Text("Headline") },
            leadingContent = {
                RadioButton(selected = false, onClick = {})
            },
        )

        ListItem(
            icon = Icons.Default.Home,
            headlineText = "Text text",
            supportingText = "Support text support text support text support text",
            modifier = Modifier,
            trailing = {},
            iconDescription = "",
        )

        ListItem(
            icon = Icons.Default.Home,
            headlineText = "Text text",
            supportingText = "Support text support text support text support text",
            modifier = Modifier,
            onSwitchChange = {},
            switchState = true,
        )
    }
}
