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

package com.sadellie.unitto.screens.main.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.ui.theme.NumbersTextStyleTitleLarge


/**
 * Button for keyboard
 *
 * @param modifier Modifier that is applied to a [Button] component
 * @param digit Symbol to show on button
 * @param enabled Current state of this button
 * @param onClick Action to perform when clicking this button
 */
@Composable
fun KeyboardButton(
    modifier: Modifier = Modifier,
    digit: String,
    enabled: Boolean = true,
    onClick: (String) -> Unit = {},
) {
    Button(
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        onClick = { onClick(digit) },
        enabled = enabled,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = digit,
            style = NumbersTextStyleTitleLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}
