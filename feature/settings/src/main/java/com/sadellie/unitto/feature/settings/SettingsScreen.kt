/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled._123
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.BuildConfig
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.Header
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.UnittoListItem
import com.sadellie.unitto.core.ui.common.UnittoScreenWithLargeTopBar
import com.sadellie.unitto.core.ui.openLink
import com.sadellie.unitto.data.userprefs.GeneralPreferences
import com.sadellie.unitto.feature.settings.navigation.aboutRoute
import com.sadellie.unitto.feature.settings.navigation.calculatorSettingsRoute
import com.sadellie.unitto.feature.settings.navigation.converterSettingsRoute
import com.sadellie.unitto.feature.settings.navigation.displayRoute
import com.sadellie.unitto.feature.settings.navigation.formattingRoute
import com.sadellie.unitto.feature.settings.navigation.startingScreenRoute

@Composable
internal fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navControllerAction: (String) -> Unit,
) {
    val userPrefs = viewModel.userPrefs.collectAsStateWithLifecycle()

    SettingsScreen(
        userPrefs = userPrefs.value,
        navigateUp = navigateUp,
        navControllerAction = navControllerAction,
        updateVibrations = viewModel::updateVibrations,
    )
}

@Composable
private fun SettingsScreen(
    userPrefs: GeneralPreferences,
    navigateUp: () -> Unit,
    navControllerAction: (String) -> Unit,
    updateVibrations: (Boolean) -> Unit,
) {
    val mContext = LocalContext.current

    UnittoScreenWithLargeTopBar(
        title = stringResource(R.string.settings_screen),
        navigationIcon = { NavigateUpButton(navigateUp) }
    ) { padding ->
        LazyColumn(contentPadding = padding) {

            // THEME
            item {
                UnittoListItem(
                    icon = Icons.Default.Palette,
                    iconDescription = stringResource(R.string.display_settings),
                    headlineText = stringResource(R.string.display_settings),
                    supportingText = stringResource(R.string.theme_setting_support),
                    modifier = Modifier.clickable { navControllerAction(displayRoute) }
                )
            }

            // START SCREEN
            item {
                UnittoListItem(
                    icon = Icons.Default.Home,
                    iconDescription = stringResource(R.string.starting_screen_setting),
                    headlineText = stringResource(R.string.starting_screen_setting),
                    supportingText = stringResource(R.string.starting_screen_setting_support),
                    modifier = Modifier.clickable { navControllerAction(startingScreenRoute) }
                )
            }

            // FORMATTING
            item {
                UnittoListItem(
                    icon = Icons.Default._123,
                    iconDescription = stringResource(R.string.formatting_setting),
                    headlineText = stringResource(R.string.formatting_setting),
                    supportingText = stringResource(R.string.formatting_setting_support),
                    modifier = Modifier.clickable { navControllerAction(formattingRoute) }
                )
            }

            item {
                UnittoListItem(
                    icon = Icons.Default.Calculate,
                    iconDescription = stringResource(R.string.calculator),
                    headlineText = stringResource(R.string.calculator),
                    supportingText = stringResource(R.string.calculator_settings_support),
                    modifier = Modifier.clickable { navControllerAction(calculatorSettingsRoute) }
                )
            }

            item {
                UnittoListItem(
                    icon = Icons.Default.SwapHoriz,
                    iconDescription = stringResource(R.string.unit_converter),
                    headlineText = stringResource(R.string.unit_converter),
                    supportingText = stringResource(R.string.converter_settings_support),
                    modifier = Modifier.clickable { navControllerAction(converterSettingsRoute) }
                )
            }

            // ADDITIONAL GROUP
            item { Header(stringResource(R.string.additional_settings_group)) }

            // VIBRATIONS
            item {
                UnittoListItem(
                    icon = Icons.Default.Vibration,
                    iconDescription = stringResource(R.string.enable_vibrations),
                    headlineText = stringResource(R.string.enable_vibrations),
                    supportingText = stringResource(R.string.enable_vibrations_support),
                    modifier = Modifier.clickable { navControllerAction(converterSettingsRoute) },
                    switchState = userPrefs.enableVibrations,
                    onSwitchChange = updateVibrations
                )
            }

            // RATE THIS APP
            if (BuildConfig.STORE_LINK.isNotEmpty()) {
                item {
                    UnittoListItem(
                        icon = Icons.Default.RateReview,
                        iconDescription = stringResource(R.string.rate_this_app),
                        headlineText = stringResource(R.string.rate_this_app),
                        modifier = Modifier.clickable { openLink(mContext, BuildConfig.STORE_LINK) }
                    )
                }
            }

            // More settings
            item {
                UnittoListItem(
                    icon = Icons.Default.Info,
                    iconDescription = stringResource(R.string.about_unitto),
                    headlineText = stringResource(R.string.about_unitto),
                    supportingText = stringResource(R.string.about_unitto_support),
                    modifier = Modifier.clickable { navControllerAction(aboutRoute) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSettingsScreen() {
    SettingsScreen(
        userPrefs = GeneralPreferences(),
        navigateUp = {},
        navControllerAction = {},
        updateVibrations = {},
    )
}
