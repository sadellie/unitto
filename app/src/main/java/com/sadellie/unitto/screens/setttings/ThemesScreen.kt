/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022 Elshan Agaev
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

package com.sadellie.unitto.screens.setttings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sadellie.unitto.R
import com.sadellie.unitto.screens.common.UnittoLargeTopAppBar
import com.sadellie.unitto.screens.setttings.components.SettingsListItem
import io.github.sadellie.themmo.ThemingMode
import io.github.sadellie.themmo.ThemmoController

@Composable
fun ThemesScreen(
    navigateUpAction: () -> Unit = {},
    themmoController: ThemmoController,
    viewModel: SettingsViewModel
) {
    UnittoLargeTopAppBar(
        title = stringResource(R.string.theme_setting),
        navigateUpAction = navigateUpAction
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            item {
                SettingsListItem(
                    label = stringResource(R.string.color_theme),
                    allOptions = mapOf(
                        ThemingMode.AUTO to stringResource(R.string.force_auto_mode),
                        ThemingMode.FORCE_LIGHT to stringResource(R.string.force_light_mode),
                        ThemingMode.FORCE_DARK to stringResource(R.string.force_dark_mode)
                    ),
                    selected = themmoController.currentThemingMode,
                    onSelectedChange = {
                        themmoController.setThemingMode(it)
                        viewModel.updateThemingMode(it)
                    }
                )
            }

            item {
                SettingsListItem(
                    label = stringResource(R.string.enable_dynamic_colors),
                    supportText = stringResource(R.string.enable_dynamic_colors_support),
                    switchState = themmoController.isDynamicThemeEnabled,
                    onSwitchChange = {
                        themmoController.enableDynamicTheme(it)
                        viewModel.updateDynamicTheme(it)
                    }
                )
            }

            item {
                AnimatedVisibility(
                    visible = (themmoController.currentThemingMode != ThemingMode.FORCE_LIGHT),
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut(),
                ) {
                    SettingsListItem(
                        label = stringResource(R.string.force_amoled_mode),
                        supportText = stringResource(R.string.force_amoled_mode_support),
                        switchState = themmoController.isAmoledThemeEnabled,
                        onSwitchChange = {
                            themmoController.enableAmoledTheme(it)
                            viewModel.updateAmoledTheme(it)
                        }
                    )
                }
            }
        }
    }
}
