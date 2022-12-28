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

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.R
import com.sadellie.unitto.data.NavRoutes.LEFT_LIST_SCREEN
import com.sadellie.unitto.data.NavRoutes.RIGHT_LIST_SCREEN
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.screens.Formatter

/**
 * Top of the main screen. Contains input and output TextFields, and unit selection row of buttons.
 * It's a separate composable, so that we support album orientation (this element will be on the left)
 *
 * @param modifier Modifier that is applied to Column
 * @param inputValue Current input value (like big decimal)
 * @param calculatedValue Current calculated value (like big decimal), will be shown under input when it
 * has an expression in it.
 * @param outputValue Current output value (like big decimal)
 * @param unitFrom [AbstractUnit] on the left
 * @param unitTo [AbstractUnit] on the right
 * @param loadingDatabase Are we still loading units usage stats from database? Disables unit
 * selection buttons.
 * @param loadingNetwork Are we loading data from network? Shows loading text in TextFields
 * @param networkError Did we got errors while trying to get data from network
 * @param onUnitSelectionClick Function that is called when clicking unit selection buttons
 * @param swapUnits Method to swap units
 */
@Composable
fun TopScreenPart(
    modifier: Modifier,
    inputValue: String,
    calculatedValue: String?,
    outputValue: String,
    unitFrom: AbstractUnit,
    unitTo: AbstractUnit,
    loadingDatabase: Boolean,
    loadingNetwork: Boolean,
    networkError: Boolean,
    onUnitSelectionClick: (String) -> Unit,
    swapUnits: () -> Unit
) {
    var swapped by remember { mutableStateOf(false) }
    val swapButtonRotation: Float by animateFloatAsState(
        targetValue = if (swapped) 0f else 180f,
        animationSpec = tween(easing = FastOutSlowInEasing)
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MyTextField(
            modifier = Modifier.fillMaxWidth(),
            primaryText = {
                when {
                    loadingDatabase || loadingNetwork -> stringResource(R.string.loading_label)
                    networkError -> stringResource(R.string.error_label)
                    else -> Formatter.format(inputValue)
                }
            },
            secondaryText = calculatedValue?.let { Formatter.format(it) },
            helperText = stringResource(if (loadingDatabase) R.string.loading_label else unitFrom.shortName),
            textToCopy = calculatedValue ?: inputValue,
        )
        MyTextField(
            modifier = Modifier.fillMaxWidth(),
            primaryText = {
                when {
                    loadingDatabase || loadingNetwork -> stringResource(R.string.loading_label)
                    networkError -> stringResource(R.string.error_label)
                    else -> Formatter.format(outputValue)
                }
            },
            secondaryText = null,
            helperText = stringResource(if (loadingDatabase) R.string.loading_label else unitTo.shortName),
            textToCopy = outputValue,
        )
        // Unit selection buttons
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            UnitSelectionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = { onUnitSelectionClick(LEFT_LIST_SCREEN) },
                label = unitFrom.displayName,
                isLoading = loadingDatabase
            )
            IconButton(
                onClick = {
                    swapUnits()
                    swapped = !swapped
                },
                enabled = !loadingDatabase
            ) {
                Icon(
                    modifier = Modifier.rotate(swapButtonRotation),
                    imageVector = Icons.Outlined.SwapHoriz,
                    contentDescription = stringResource(R.string.swap_units_description)
                )
            }
            UnitSelectionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = { onUnitSelectionClick(RIGHT_LIST_SCREEN) },
                label = unitTo.displayName,
                isLoading = loadingDatabase
            )
        }
    }
}
