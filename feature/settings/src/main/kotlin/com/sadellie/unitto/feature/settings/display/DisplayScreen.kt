/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

package com.sadellie.unitto.feature.settings.display

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.datastore.DisplayPreferences
import com.sadellie.unitto.core.designsystem.icons.iconpack.ClearBold
import com.sadellie.unitto.core.designsystem.icons.iconpack.IconPack
import com.sadellie.unitto.core.designsystem.icons.symbols.Colorize
import com.sadellie.unitto.core.designsystem.icons.symbols.DarkMode
import com.sadellie.unitto.core.designsystem.icons.symbols.DarkModeFill
import com.sadellie.unitto.core.designsystem.icons.symbols.ExposureZero
import com.sadellie.unitto.core.designsystem.icons.symbols.HdrAuto
import com.sadellie.unitto.core.designsystem.icons.symbols.Language
import com.sadellie.unitto.core.designsystem.icons.symbols.LightMode
import com.sadellie.unitto.core.designsystem.icons.symbols.Palette
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.ui.ColorSelector
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.Header
import com.sadellie.unitto.core.ui.ListItem
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import com.sadellie.unitto.core.ui.SegmentedButton
import com.sadellie.unitto.core.ui.SegmentedButtonsRow
import com.sadellie.unitto.feature.settings.components.MonetModeSelector
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.ThemmoController
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode

@Composable
internal fun DisplayRoute(
  viewModel: DisplayViewModel = hiltViewModel(),
  navigateUp: () -> Unit = {},
  themmoController: ThemmoController,
  navigateToLanguages: () -> Unit,
) {
  when (val prefs = viewModel.prefs.collectAsStateWithLifecycle().value) {
    null -> EmptyScreen()
    else ->
      DisplayScreen(
        navigateUp = navigateUp,
        prefs = prefs,
        controller = themmoController,
        onThemeChange = { newValue ->
          themmoController.setThemingMode(newValue)
          viewModel.updateThemingMode(newValue)
        },
        onDynamicThemeChange = { newValue ->
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            themmoController.enableDynamicTheme(newValue)
            viewModel.updateDynamicTheme(newValue)
          }
        },
        onAmoledThemeChange = { newValue ->
          themmoController.enableAmoledTheme(newValue)
          viewModel.updateAmoledTheme(newValue)
        },
        onColorChange = { newValue ->
          themmoController.setCustomColor(newValue)
          viewModel.updateCustomColor(newValue)
        },
        onMonetModeChange = { newValue ->
          themmoController.setMonetMode(newValue)
          viewModel.updateMonetMode(newValue)
        },
        updateAcButton = viewModel::updateAcButton,
        updateMiddleZero = viewModel::updateMiddleZero,
        navigateToLanguages = navigateToLanguages,
      )
  }
}

@Composable
private fun DisplayScreen(
  navigateUp: () -> Unit,
  prefs: DisplayPreferences,
  controller: ThemmoController,
  onThemeChange: (ThemingMode) -> Unit,
  onDynamicThemeChange: (Boolean) -> Unit,
  onAmoledThemeChange: (Boolean) -> Unit,
  onColorChange: (Color) -> Unit,
  onMonetModeChange: (MonetMode) -> Unit,
  updateAcButton: (Boolean) -> Unit,
  updateMiddleZero: (Boolean) -> Unit,
  navigateToLanguages: () -> Unit,
) {
  ScaffoldWithLargeTopBar(
    title = stringResource(R.string.settings_display),
    navigationIcon = { NavigateUpButton(navigateUp) },
  ) { paddingValues ->
    Column(
      modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(paddingValues)
    ) {
      ListItem(
        leadingContent = { Icon(Symbols.Palette, stringResource(R.string.settings_color_theme)) },
        headlineContent = { Text(stringResource(R.string.settings_color_theme)) },
        supportingContent = { Text(stringResource(R.string.settings_color_theme_support)) },
      )

      ThemingModeSelector(onThemeChange, controller.currentThemingMode)

      AnimatedVisibility(
        visible = controller.currentThemingMode != ThemingMode.FORCE_LIGHT,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
      ) {
        ListItem(
          icon = Symbols.DarkModeFill,
          headlineText = stringResource(R.string.settings_amoled_dark),
          supportingText = stringResource(R.string.settings_amoled_dark_support),
          switchState = controller.isAmoledThemeEnabled,
          onSwitchChange = onAmoledThemeChange,
        )
      }

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        ListItem(
          icon = Symbols.Colorize,
          headlineText = stringResource(R.string.settings_dynamic_colors),
          supportingText = stringResource(R.string.settings_dynamic_colors_support),
          switchState = controller.isDynamicThemeEnabled,
          onSwitchChange = onDynamicThemeChange,
        )

        AnimatedVisibility(
          visible = !controller.isDynamicThemeEnabled,
          enter = expandVertically() + fadeIn(),
          exit = shrinkVertically() + fadeOut(),
        ) {
          ListItem(
            headlineContent = { Text(stringResource(R.string.settings_selected_color)) },
            supportingContent = {
              ColorSelector(
                modifier = Modifier.padding(top = 12.dp),
                currentColor =
                  // Disable custom color and use brand color scheme
                  if (controller.currentCustomColor == Color.Unspecified) brandColor
                  else controller.currentCustomColor,
                onColorClick = { onColorChange(if (it == brandColor) Color.Unspecified else it) },
                colors = colorSchemes,
              )
            },
            modifier = Modifier.padding(start = 40.dp),
          )
        }

        AnimatedVisibility(
          visible =
            (!controller.isDynamicThemeEnabled) and
              (controller.currentCustomColor != Color.Unspecified),
          enter = expandVertically() + fadeIn(),
          exit = shrinkVertically() + fadeOut(),
        ) {
          ListItem(
            headlineContent = { Text(stringResource(R.string.settings_selected_style)) },
            supportingContent = {
              MonetModeSelector(
                modifier = Modifier.padding(top = 12.dp),
                selected = controller.currentMonetMode,
                onItemClick = onMonetModeChange,
                customColor = controller.currentCustomColor,
                themingMode = controller.currentThemingMode,
              )
            },
            modifier = Modifier.padding(start = 40.dp),
          )
        }
      }

      Header(stringResource(R.string.settings_additional))

      ListItem(
        icon = IconPack.ClearBold,
        headlineText = stringResource(R.string.settings_ac_button),
        supportingText = stringResource(R.string.settings_ac_button_support),
        switchState = prefs.acButton,
        onSwitchChange = updateAcButton,
      )

      ListItem(
        icon = Symbols.ExposureZero,
        headlineText = stringResource(R.string.settings_middle_zero),
        supportingText = stringResource(R.string.settings_middle_zero_support),
        switchState = prefs.middleZero,
        onSwitchChange = updateMiddleZero,
      )

      ListItem(
        icon = Symbols.Language,
        headlineText = stringResource(R.string.settings_language),
        supportingText = stringResource(R.string.settings_language_support),
        modifier = Modifier.clickable { navigateToLanguages() },
      )
    }
  }
}

@Composable
private fun ThemingModeSelector(
  onThemeChange: (ThemingMode) -> Unit,
  currentThemingMode: ThemingMode,
) {
  Row(Modifier.horizontalScroll(rememberScrollState()).wrapContentWidth()) {
    SegmentedButtonsRow(modifier = Modifier.padding(56.dp, 8.dp, 24.dp, 2.dp)) {
      SegmentedButton(
        label = stringResource(R.string.settings_auto),
        onClick = { onThemeChange(ThemingMode.AUTO) },
        selected = ThemingMode.AUTO == currentThemingMode,
        icon = Symbols.HdrAuto,
      )
      SegmentedButton(
        label = stringResource(R.string.settings_light_mode),
        onClick = { onThemeChange(ThemingMode.FORCE_LIGHT) },
        selected = ThemingMode.FORCE_LIGHT == currentThemingMode,
        icon = Symbols.LightMode,
      )
      SegmentedButton(
        label = stringResource(R.string.settings_dark_mode),
        onClick = { onThemeChange(ThemingMode.FORCE_DARK) },
        selected = ThemingMode.FORCE_DARK == currentThemingMode,
        icon = Symbols.DarkMode,
      )
    }
  }
}

@RequiresApi(Build.VERSION_CODES.O_MR1)
@Preview
@Composable
private fun Preview() {
  Themmo { themmoController ->
    DisplayScreen(
      navigateUp = {},
      prefs = DisplayPreferences(middleZero = true, acButton = true),
      controller = themmoController,
      onThemeChange = themmoController::setThemingMode,
      onDynamicThemeChange = themmoController::enableDynamicTheme,
      onAmoledThemeChange = themmoController::enableAmoledTheme,
      onColorChange = themmoController::setCustomColor,
      onMonetModeChange = themmoController::setMonetMode,
      updateAcButton = {},
      updateMiddleZero = {},
      navigateToLanguages = {},
    )
  }
}
