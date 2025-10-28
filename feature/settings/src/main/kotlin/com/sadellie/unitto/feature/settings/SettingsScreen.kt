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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.common.openLink
import com.sadellie.unitto.core.common.showToast
import com.sadellie.unitto.core.designsystem.LocalWindowSize
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
import com.sadellie.unitto.core.ui.ListArrangement
import com.sadellie.unitto.core.ui.ListHeader
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import com.sadellie.unitto.core.ui.firstShape
import com.sadellie.unitto.core.ui.lastShape
import com.sadellie.unitto.core.ui.middleShape
import com.sadellie.unitto.feature.settings.components.AnnoyingBox
import com.sadellie.unitto.feature.settings.navigation.AboutRoute
import com.sadellie.unitto.feature.settings.navigation.BackupRoute
import com.sadellie.unitto.feature.settings.navigation.CalculatorSettingsRoute
import com.sadellie.unitto.feature.settings.navigation.ConverterSettingsRoute
import com.sadellie.unitto.feature.settings.navigation.DisplayRoute
import com.sadellie.unitto.feature.settings.navigation.FormattingRoute
import com.sadellie.unitto.feature.settings.navigation.StartingScreenRoute

@Composable
internal fun SettingsRoute(
  viewModel: SettingsViewModel = hiltViewModel(),
  openDrawer: () -> Unit,
  navControllerAction: (route: Route) -> Unit,
) {
  val context = LocalContext.current
  val resources = LocalResources.current
  val uiState: SettingsUIState = viewModel.uiState.collectAsStateWithLifecycle().value
  val showErrorToast: Boolean =
    viewModel.showErrorToast.collectAsStateWithLifecycle(initialValue = false).value

  LaunchedEffect(showErrorToast) {
    if (showErrorToast) showToast(context, resources.getString(R.string.common_error))
  }

  when (uiState) {
    SettingsUIState.Loading -> EmptyScreen()

    is SettingsUIState.Ready ->
      SettingsScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        navControllerAction = navControllerAction,
        updateLastReadChangelog = viewModel::updateLastReadChangelog,
        updateVibrations = viewModel::updateVibrations,
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
  clearCache: () -> Unit,
) {
  ScaffoldWithLargeTopBar(
    title = stringResource(R.string.settings_title),
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
      verticalArrangement = ListArrangement,
    ) {
      AnimatedVisibility(
        modifier = Modifier.fillMaxWidth(),
        visible = uiState.showUpdateChangelog,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
      ) {
        val mContext = LocalContext.current
        val title = stringResource(R.string.settings_updated, stringResource(R.string.app_name))
        AnnoyingBox(
          modifier = Modifier.padding(vertical = Sizes.small).fillMaxWidth(),
          onClick = {
            openLink(mContext, "https://github.com/sadellie/unitto/releases/latest")
            updateLastReadChangelog(BuildConfig.VERSION_CODE)
          },
          imageVector = Symbols.NewReleases,
          imageVectorContentDescription = title,
          title = title,
          support = stringResource(R.string.settings_updated_support, BuildConfig.VERSION_NAME),
        )
      }
      ListItemExpressive(
        icon = Symbols.Palette,
        headlineText = stringResource(R.string.settings_display),
        supportingText = stringResource(R.string.settings_display_support),
        modifier = Modifier.clickable { navControllerAction(DisplayRoute) },
        shape = ListItemDefaults.firstShape,
      )
      ListItemExpressive(
        icon = Symbols.Home,
        headlineText = stringResource(R.string.settings_starting_screen),
        supportingText = stringResource(R.string.settings_starting_screen_support),
        modifier = Modifier.clickable { navControllerAction(StartingScreenRoute) },
        shape = ListItemDefaults.middleShape,
      )
      ListItemExpressive(
        icon = Symbols._123,
        headlineText = stringResource(R.string.settings_formatting),
        supportingText = stringResource(R.string.settings_formatting_support),
        modifier = Modifier.clickable { navControllerAction(FormattingRoute) },
        shape = ListItemDefaults.middleShape,
      )
      ListItemExpressive(
        icon = Symbols.Calculate,
        headlineText = stringResource(R.string.calculator_title),
        supportingText = stringResource(R.string.settings_calculator_support),
        modifier = Modifier.clickable { navControllerAction(CalculatorSettingsRoute) },
        shape = ListItemDefaults.middleShape,
      )
      ListItemExpressive(
        icon = Symbols.SwapHoriz,
        headlineText = stringResource(R.string.converter_title),
        supportingText = stringResource(R.string.settings_converter_support),
        modifier = Modifier.clickable { navControllerAction(ConverterSettingsRoute) },
        shape = ListItemDefaults.lastShape,
      )
      ListHeader(stringResource(R.string.settings_additional))
      ListItemExpressive(
        icon = Symbols.Vibration,
        headlineText = stringResource(R.string.settings_vibrations),
        supportingText = stringResource(R.string.settings_vibrations_support),
        switchState = uiState.enableVibrations,
        onSwitchChange = updateVibrations,
        shape = ListItemDefaults.firstShape,
      )
      ListItemExpressive(
        icon = Symbols.RestorePage,
        headlineText = stringResource(R.string.settings_backup),
        supportingText = stringResource(R.string.settings_backup_support),
        modifier = Modifier.clickable { navControllerAction(BackupRoute) },
        shape = ListItemDefaults.middleShape,
      )
      AnimatedVisibility(
        visible = uiState.cacheSize > 0,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
      ) {
        val mContext = LocalContext.current
        ListItemExpressive(
          headlineText = stringResource(R.string.settings_clear_cache),
          icon = Symbols.Cached,
          modifier =
            Modifier.clickable {
              clearCache()
              showToast(mContext, "👌")
            },
          shape = ListItemDefaults.middleShape,
        )
      }
      if (BuildConfig.STORE_LINK.isNotEmpty()) {
        val mContext = LocalContext.current
        ListItemExpressive(
          icon = Symbols.RateReview,
          headlineText = stringResource(R.string.settings_rate_this_app),
          modifier = Modifier.clickable { openLink(mContext, BuildConfig.STORE_LINK) },
          shape = ListItemDefaults.middleShape,
        )
      }
      ListItemExpressive(
        icon = Symbols.Info,
        headlineText = stringResource(R.string.settings_about_unitto),
        supportingText = stringResource(R.string.settings_about_unitto_support),
        modifier = Modifier.clickable { navControllerAction(AboutRoute) },
        shape = ListItemDefaults.lastShape,
      )
    }
  }
}

@Preview
@Composable
private fun PreviewSettingsScreen() {
  var uiState by remember {
    mutableStateOf(
      SettingsUIState.Ready(enableVibrations = false, cacheSize = 2, showUpdateChangelog = true)
    )
  }

  SettingsScreen(
    uiState = uiState,
    openDrawer = {},
    navControllerAction = {},
    updateLastReadChangelog = { uiState = uiState.copy(showUpdateChangelog = false) },
    updateVibrations = {},
    clearCache = { uiState = uiState.copy(cacheSize = 0) },
  )
}
