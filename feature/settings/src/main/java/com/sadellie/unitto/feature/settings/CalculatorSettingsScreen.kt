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

package com.sadellie.unitto.feature.settings

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.UnittoListItem
import com.sadellie.unitto.core.ui.common.UnittoScreenWithLargeTopBar

@Composable
internal fun CalculatorSettingsScreen(
    viewModel: SettingsViewModel,
    navigateUpAction: () -> Unit,
) {
    val userPrefs = viewModel.userPrefs.collectAsStateWithLifecycle()

    UnittoScreenWithLargeTopBar(
        title = stringResource(R.string.calculator),
        navigationIcon = { NavigateUpButton(navigateUpAction) }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            item {
                UnittoListItem(
                    label = stringResource(R.string.partial_history_view_setting),
                    leadingContent = {
                        Icon(
                            Icons.Default.Timer,
                            stringResource(R.string.partial_history_view_setting)
                        )
                    },
                    supportContent = stringResource(R.string.partial_history_view_setting_support),
                    switchState = userPrefs.value.partialHistoryView,
                    onSwitchChange = viewModel::updatePartialHistoryView
                )
            }
        }
    }
}
