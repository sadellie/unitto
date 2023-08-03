/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

/**
 * Represents one item in list on Settings screen.
 *
 * @param label Main text.
 * @param supportContent Text that is located below label.
 * @param switchState Current switch state.
 * @param onSwitchChange Action to perform when user clicks on this component or just switch. Gives
 * you new value.
 */
@Composable
fun UnittoListItem(
    label: String,
    leadingContent: @Composable (() -> Unit)?,
    supportContent: String? = null,
    switchState: Boolean,
    onSwitchChange: (Boolean) -> Unit
) {
    ListItem(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = { onSwitchChange(!switchState) }
            ),
        headlineContent = { Text(label) },
        supportingContent = { supportContent?.let { Text(text = it) } },
        leadingContent = leadingContent,
        trailingContent = {
            Switch(
                checked = switchState,
                onCheckedChange = { onSwitchChange(it) }
            )
        }
    )
}
