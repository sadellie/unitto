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

package com.sadellie.unitto.feature.epoch.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopPart(
    modifier: Modifier,
    unixToDate: Boolean,
    swap: () -> Unit,
    dateField: @Composable() (LazyItemScope.() -> Unit),
    unixField: @Composable() (LazyItemScope.() -> Unit),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Bottom
        ) {
            if (unixToDate) {
                item("unix") { unixField() }
                item("date") { dateField() }
            } else {
                item("date") { dateField() }
                item("unix") { unixField() }
            }
        }
        SwapButton(swap)
    }
}
