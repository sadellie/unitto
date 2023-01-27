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

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.R

/**
 * Represents one item in list on Settings screen.
 *
 * @param label Main text.
 * @param supportText Text that is located below label.
 * @param switchState Current switch state.
 * @param onSwitchChange Action to perform when user clicks on this component or just switch. Gives
 * you new value.
 */
@Composable
fun UnittoListItem(
    label: String,
    leadingContent: @Composable (() -> Unit)?,
    supportText: String? = null,
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
        headlineText = { Text(label) },
        supportingText = { supportText?.let { Text(text = it) } },
        leadingContent = leadingContent,
        trailingContent = {
            Switch(
                checked = switchState,
                onCheckedChange = { onSwitchChange(it) }
            )
        }
    )
}

/**
 * Represents one item in list on Settings screen with drop-down menu.
 *
 * @param label Main text.
 * @param supportText Text that is located below label.
 * @param allOptions Options in drop-down menu. Key is option itself and value is the string that
 * will be shown.
 * @param selected Selected option.
 * @param onSelectedChange Action to perform when drop-down menu item is selected.
 */
@Composable
fun <T> UnittoListItem(
    label: String,
    supportText: String? = null,
    allOptions: Map<T, String>,
    leadingContent: @Composable (() -> Unit)?,
    selected: T,
    onSelectedChange: (T) -> Unit
) {
    var dropDownExpanded by rememberSaveable { mutableStateOf(false) }
    var currentOption by rememberSaveable { mutableStateOf(selected) }
    val dropDownRotation: Float by animateFloatAsState(
        targetValue = if (dropDownExpanded) 180f else 0f,
        animationSpec = tween(easing = FastOutSlowInEasing)
    )

    ListItem(
        headlineText = { Text(label) },
        supportingText = { supportText?.let { Text(text = it) } },
        leadingContent = leadingContent,
        trailingContent = {
            ExposedDropdownMenuBox(
                modifier = Modifier,
                expanded = dropDownExpanded,
                onExpandedChange = { dropDownExpanded = it }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor()
                        .widthIn(1.dp),
                    value = allOptions[currentOption] ?: selected.toString(),
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    enabled = false,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.ArrowDropDown,
                            modifier = Modifier.rotate(dropDownRotation),
                            contentDescription = stringResource(R.string.drop_down_description)
                        )
                    }
                )
                ExposedDropdownMenu(
                    modifier = Modifier.exposedDropdownSize(),
                    expanded = dropDownExpanded,
                    onDismissRequest = { dropDownExpanded = false }
                ) {
                    allOptions.forEach {
                        DropdownMenuItem(
                            text = { Text(it.value) },
                            onClick = {
                                currentOption = it.key
                                onSelectedChange(it.key)
                                dropDownExpanded = false
                            }
                        )
                    }
                }
            }
        }
    )
}
