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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled._123
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.sadellie.unitto.core.ui.common.UnittoEmptyScreen
import com.sadellie.unitto.core.ui.common.UnittoListItem
import com.sadellie.unitto.core.ui.common.UnittoScreenWithLargeTopBar
import com.sadellie.unitto.core.ui.openLink
import com.sadellie.unitto.core.ui.showToast
import com.sadellie.unitto.data.model.userprefs.GeneralPreferences
import com.sadellie.unitto.data.userprefs.GeneralPreferencesImpl
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
    val userPrefs = viewModel.userPrefs.collectAsStateWithLifecycle().value
    val cachePercentage = viewModel.cachePercentage.collectAsStateWithLifecycle()

    when (userPrefs) {
        null -> UnittoEmptyScreen()
        else -> {
            SettingsScreen(
                userPrefs = userPrefs,
                navigateUp = navigateUp,
                navControllerAction = navControllerAction,
                updateVibrations = viewModel::updateVibrations,
                cachePercentage = cachePercentage.value,
                clearCache = viewModel::clearCache,
            )
        }
    }
}

@Composable
private fun SettingsScreen(
    userPrefs: GeneralPreferences,
    navigateUp: () -> Unit,
    navControllerAction: (String) -> Unit,
    updateVibrations: (Boolean) -> Unit,
    cachePercentage: Float,
    clearCache: () -> Unit,
) {
    val mContext = LocalContext.current

    UnittoScreenWithLargeTopBar(
        title = stringResource(R.string.settings_title),
        navigationIcon = { NavigateUpButton(navigateUp) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            UnittoListItem(
                icon = Icons.Default.Palette,
                headlineText = stringResource(R.string.settings_display),
                supportingText = stringResource(R.string.settings_display_support),
                modifier = Modifier.clickable { navControllerAction(displayRoute) }
            )

            UnittoListItem(
                icon = Icons.Default.Home,
                headlineText = stringResource(R.string.settings_starting_screen),
                supportingText = stringResource(R.string.settings_starting_screen_support),
                modifier = Modifier.clickable { navControllerAction(startingScreenRoute) }
            )

            UnittoListItem(
                icon = Icons.Default._123,
                headlineText = stringResource(R.string.settings_formatting),
                supportingText = stringResource(R.string.settings_formatting_support),
                modifier = Modifier.clickable { navControllerAction(formattingRoute) }
            )

            UnittoListItem(
                icon = Icons.Default.Calculate,
                headlineText = stringResource(R.string.calculator_title),
                supportingText = stringResource(R.string.settings_calculator_support),
                modifier = Modifier.clickable { navControllerAction(calculatorSettingsRoute) }
            )

            UnittoListItem(
                icon = Icons.Default.SwapHoriz,
                headlineText = stringResource(R.string.unit_converter_title),
                supportingText = stringResource(R.string.settings_converter_support),
                modifier = Modifier.clickable { navControllerAction(converterSettingsRoute) }
            )

            Header(stringResource(R.string.settings_additional))

            UnittoListItem(
                icon = Icons.Default.Vibration,
                headlineText = stringResource(R.string.settings_vibrations),
                supportingText = stringResource(R.string.settings_vibrations_support),
                modifier = Modifier.clickable { navControllerAction(converterSettingsRoute) },
                switchState = userPrefs.enableVibrations,
                onSwitchChange = updateVibrations
            )

            AnimatedVisibility(
                visible = cachePercentage > 0,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),
            ) {
                UnittoListItem(
                    headlineText = stringResource(R.string.settings_clear_cache),
                    icon = Icons.Default.Cached,
                    modifier = Modifier.clickable { clearCache(); showToast(mContext, "👌") },
                )
            }

            if (BuildConfig.STORE_LINK.isNotEmpty()) {
                UnittoListItem(
                    icon = Icons.Default.RateReview,
                    headlineText = stringResource(R.string.settings_rate_this_app),
                    modifier = Modifier.clickable { openLink(mContext, BuildConfig.STORE_LINK) }
                )
            }

            UnittoListItem(
                icon = Icons.Default.Info,
                headlineText = stringResource(R.string.settings_about_unitto),
                supportingText = stringResource(R.string.settings_about_unitto_support),
                modifier = Modifier.clickable { navControllerAction(aboutRoute) }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSettingsScreen() {
    var cacheSize by remember { mutableFloatStateOf(0.9f) }

    SettingsScreen(
        userPrefs = GeneralPreferencesImpl(
            enableVibrations = true
        ),
        navigateUp = {},
        navControllerAction = {},
        updateVibrations = {},
        cachePercentage = cacheSize,
        clearCache = { cacheSize = 0f }
    )
}
