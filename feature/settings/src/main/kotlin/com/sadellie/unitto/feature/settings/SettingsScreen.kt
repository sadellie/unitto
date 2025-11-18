/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2025 Elshan Agaev
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.common.Config
import com.sadellie.unitto.core.designsystem.LocalWindowSize
import com.sadellie.unitto.core.designsystem.icons.symbols.BacklightHigh
import com.sadellie.unitto.core.designsystem.icons.symbols.Cached
import com.sadellie.unitto.core.designsystem.icons.symbols.Calculate
import com.sadellie.unitto.core.designsystem.icons.symbols.Home
import com.sadellie.unitto.core.designsystem.icons.symbols.Info
import com.sadellie.unitto.core.designsystem.icons.symbols.NewReleases
import com.sadellie.unitto.core.designsystem.icons.symbols.Palette
import com.sadellie.unitto.core.designsystem.icons.symbols.RateReview
import com.sadellie.unitto.core.designsystem.icons.symbols.RestorePage
import com.sadellie.unitto.core.designsystem.icons.symbols.SwapHoriz
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.icons.symbols.Vibration
import com.sadellie.unitto.core.designsystem.icons.symbols._123
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.navigation.Route
import com.sadellie.unitto.core.ui.DrawerButton
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListHeader
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.ListItemExpressiveDefaults
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import com.sadellie.unitto.core.ui.rememberLinkOpener
import com.sadellie.unitto.feature.settings.components.AnnoyingBox
import com.sadellie.unitto.feature.settings.navigation.AboutRoute
import com.sadellie.unitto.feature.settings.navigation.BackupRoute
import com.sadellie.unitto.feature.settings.navigation.CalculatorSettingsRoute
import com.sadellie.unitto.feature.settings.navigation.ConverterSettingsRoute
import com.sadellie.unitto.feature.settings.navigation.DisplayRoute
import com.sadellie.unitto.feature.settings.navigation.FormattingRoute
import com.sadellie.unitto.feature.settings.navigation.StartingScreenRoute
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.app_name
import unitto.core.common.generated.resources.calculator_title
import unitto.core.common.generated.resources.converter_title
import unitto.core.common.generated.resources.settings_about_unitto
import unitto.core.common.generated.resources.settings_about_unitto_support
import unitto.core.common.generated.resources.settings_additional
import unitto.core.common.generated.resources.settings_backup
import unitto.core.common.generated.resources.settings_backup_support
import unitto.core.common.generated.resources.settings_calculator_support
import unitto.core.common.generated.resources.settings_clear_cache
import unitto.core.common.generated.resources.settings_converter_support
import unitto.core.common.generated.resources.settings_display
import unitto.core.common.generated.resources.settings_display_support
import unitto.core.common.generated.resources.settings_formatting
import unitto.core.common.generated.resources.settings_formatting_support
import unitto.core.common.generated.resources.settings_keep_screen_on
import unitto.core.common.generated.resources.settings_keep_screen_on_support
import unitto.core.common.generated.resources.settings_rate_this_app
import unitto.core.common.generated.resources.settings_starting_screen
import unitto.core.common.generated.resources.settings_starting_screen_support
import unitto.core.common.generated.resources.settings_title
import unitto.core.common.generated.resources.settings_updated
import unitto.core.common.generated.resources.settings_updated_support
import unitto.core.common.generated.resources.settings_vibrations
import unitto.core.common.generated.resources.settings_vibrations_support

@Composable
internal fun SettingsRoute(
  viewModel: SettingsViewModel = koinViewModel(),
  openDrawer: () -> Unit,
  navControllerAction: (route: Route) -> Unit,
) {
  when (val uiState: SettingsUIState = viewModel.uiState.collectAsStateWithLifecycle().value) {
    SettingsUIState.Loading -> EmptyScreen()

    is SettingsUIState.Ready ->
      SettingsScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        navControllerAction = navControllerAction,
        updateLastReadChangelog = viewModel::updateLastReadChangelog,
        updateVibrations = viewModel::updateVibrations,
        updateKeepScreenOn = viewModel::updateEnableKeepScreenOn,
        clearCache = viewModel::clearCache,
      )
  }
}

@Composable
private fun SettingsScreen(
  uiState: SettingsUIState.Ready,
  openDrawer: () -> Unit,
  navControllerAction: (Route) -> Unit,
  updateLastReadChangelog: (String) -> Unit,
  updateVibrations: (Boolean) -> Unit,
  updateKeepScreenOn: (Boolean) -> Unit,
  clearCache: () -> Unit,
) {
  ScaffoldWithLargeTopBar(
    title = stringResource(Res.string.settings_title),
    navigationIcon = {
      if (LocalWindowSize.current.widthSizeClass != WindowWidthSizeClass.Expanded) {
        DrawerButton(openDrawer)
      }
    },
  ) { padding ->
    Column(
      modifier =
        Modifier.fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(padding)
          .padding(start = Sizes.large, end = Sizes.large, bottom = Sizes.large),
      verticalArrangement = ListItemExpressiveDefaults.ListArrangement,
    ) {
      val linkOpener = rememberLinkOpener()
      AnimatedVisibility(
        modifier = Modifier.fillMaxWidth(),
        visible = uiState.showUpdateChangelog,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
      ) {
        val title = stringResource(Res.string.settings_updated, stringResource(Res.string.app_name))
        AnnoyingBox(
          modifier = Modifier.padding(vertical = Sizes.small).fillMaxWidth(),
          onClick = {
            linkOpener.launch("https://github.com/sadellie/unitto/releases/latest")
            updateLastReadChangelog(BuildConfig.VERSION_CODE)
          },
          imageVector = Symbols.NewReleases,
          imageVectorContentDescription = title,
          title = title,
          support = stringResource(Res.string.settings_updated_support, BuildConfig.VERSION_NAME),
        )
      }
      ListItemExpressive(
        icon = Symbols.Palette,
        headlineText = stringResource(Res.string.settings_display),
        supportingText = stringResource(Res.string.settings_display_support),
        onClick = { navControllerAction(DisplayRoute) },
        shape = ListItemExpressiveDefaults.firstShape,
      )
      ListItemExpressive(
        icon = Symbols.Home,
        headlineText = stringResource(Res.string.settings_starting_screen),
        supportingText = stringResource(Res.string.settings_starting_screen_support),
        onClick = { navControllerAction(StartingScreenRoute) },
        shape = ListItemExpressiveDefaults.middleShape,
      )
      ListItemExpressive(
        icon = Symbols._123,
        headlineText = stringResource(Res.string.settings_formatting),
        supportingText = stringResource(Res.string.settings_formatting_support),
        onClick = { navControllerAction(FormattingRoute) },
        shape = ListItemExpressiveDefaults.middleShape,
      )
      ListItemExpressive(
        icon = Symbols.Calculate,
        headlineText = stringResource(Res.string.calculator_title),
        supportingText = stringResource(Res.string.settings_calculator_support),
        onClick = { navControllerAction(CalculatorSettingsRoute) },
        shape = ListItemExpressiveDefaults.middleShape,
      )
      ListItemExpressive(
        icon = Symbols.SwapHoriz,
        headlineText = stringResource(Res.string.converter_title),
        supportingText = stringResource(Res.string.settings_converter_support),
        onClick = { navControllerAction(ConverterSettingsRoute) },
        shape = ListItemExpressiveDefaults.lastShape,
      )
      ListHeader(stringResource(Res.string.settings_additional))
      ListItemExpressive(
        icon = Symbols.Vibration,
        headlineText = stringResource(Res.string.settings_vibrations),
        supportingText = stringResource(Res.string.settings_vibrations_support),
        switchState = uiState.enableVibrations,
        onSwitchChange = updateVibrations,
        shape = ListItemExpressiveDefaults.firstShape,
      )
      ListItemExpressive(
        icon = Symbols.BacklightHigh,
        headlineText = stringResource(Res.string.settings_keep_screen_on),
        supportingText = stringResource(Res.string.settings_keep_screen_on_support),
        switchState = uiState.enableKeepScreenOn,
        onSwitchChange = updateKeepScreenOn,
        shape = ListItemExpressiveDefaults.middleShape,
      )
      ListItemExpressive(
        icon = Symbols.RestorePage,
        headlineText = stringResource(Res.string.settings_backup),
        supportingText = stringResource(Res.string.settings_backup_support),
        onClick = { navControllerAction(BackupRoute) },
        shape = ListItemExpressiveDefaults.middleShape,
      )
      AnimatedVisibility(
        visible = uiState.cacheSize > 0,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
      ) {
        ListItemExpressive(
          headlineText = stringResource(Res.string.settings_clear_cache),
          icon = Symbols.Cached,
          onClick = {
            clearCache()
            // TODO snackbar showToast(mContext, "👌")
          },
          shape = ListItemExpressiveDefaults.middleShape,
        )
      }
      if (Config.STORE_LINK.isNotEmpty()) {
        ListItemExpressive(
          icon = Symbols.RateReview,
          headlineText = stringResource(Res.string.settings_rate_this_app),
          onClick = { linkOpener.launch(Config.STORE_LINK) },
          shape = ListItemExpressiveDefaults.middleShape,
        )
      }
      ListItemExpressive(
        icon = Symbols.Info,
        headlineText = stringResource(Res.string.settings_about_unitto),
        supportingText = stringResource(Res.string.settings_about_unitto_support),
        onClick = { navControllerAction(AboutRoute) },
        shape = ListItemExpressiveDefaults.lastShape,
      )
    }
  }
}

@Preview
@Composable
private fun PreviewSettingsScreen() {
  var uiState by remember {
    mutableStateOf(
      SettingsUIState.Ready(
        enableVibrations = false,
        cacheSize = 2,
        showUpdateChangelog = true,
        enableKeepScreenOn = false,
      )
    )
  }

  SettingsScreen(
    uiState = uiState,
    openDrawer = {},
    navControllerAction = {},
    updateLastReadChangelog = { uiState = uiState.copy(showUpdateChangelog = false) },
    updateVibrations = { uiState = uiState.copy(enableVibrations = it) },
    updateKeepScreenOn = { uiState = uiState.copy(enableKeepScreenOn = it) },
    clearCache = { uiState = uiState.copy(cacheSize = 0) },
  )
}
