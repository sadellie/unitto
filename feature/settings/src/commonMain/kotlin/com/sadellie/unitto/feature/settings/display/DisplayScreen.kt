/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2026 Elshan Agaev
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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.common.collectAsStateWithLifecycleKMP
import com.sadellie.unitto.core.datastore.KeypadPreferences
import com.sadellie.unitto.core.designsystem.icons.iconpack.ClearBold
import com.sadellie.unitto.core.designsystem.icons.iconpack.IconPack
import com.sadellie.unitto.core.designsystem.icons.symbols.Colorize
import com.sadellie.unitto.core.designsystem.icons.symbols.DarkModeFill
import com.sadellie.unitto.core.designsystem.icons.symbols.ExposureZero
import com.sadellie.unitto.core.designsystem.icons.symbols.Language
import com.sadellie.unitto.core.designsystem.icons.symbols.Palette
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.ui.ColorSelector
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListArrangement
import com.sadellie.unitto.core.ui.ListHeader
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import com.sadellie.unitto.core.ui.firstShapes
import com.sadellie.unitto.core.ui.lastShapes
import com.sadellie.unitto.core.ui.middleShapes
import dev.zacsweers.metrox.viewmodel.metroViewModel
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.ThemmoController
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode
import io.github.sadellie.themmo.rememberThemmoController
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.settings_ac_button
import unitto.core.common.generated.resources.settings_ac_button_support
import unitto.core.common.generated.resources.settings_additional
import unitto.core.common.generated.resources.settings_amoled_dark
import unitto.core.common.generated.resources.settings_amoled_dark_support
import unitto.core.common.generated.resources.settings_auto
import unitto.core.common.generated.resources.settings_color_theme
import unitto.core.common.generated.resources.settings_color_theme_support
import unitto.core.common.generated.resources.settings_dark_mode
import unitto.core.common.generated.resources.settings_display
import unitto.core.common.generated.resources.settings_dynamic_colors
import unitto.core.common.generated.resources.settings_dynamic_colors_support
import unitto.core.common.generated.resources.settings_language
import unitto.core.common.generated.resources.settings_language_support
import unitto.core.common.generated.resources.settings_light_mode
import unitto.core.common.generated.resources.settings_middle_zero
import unitto.core.common.generated.resources.settings_middle_zero_support
import unitto.core.common.generated.resources.settings_selected_color
import unitto.core.common.generated.resources.settings_selected_style

@Composable
internal fun DisplayRoute(
  navigateUp: () -> Unit = {},
  themmoController: ThemmoController,
  navigateToLanguages: () -> Unit,
) {
  val viewModel: DisplayViewModel = metroViewModel()
  when (val keypadPrefs = viewModel.keypadPrefs.collectAsStateWithLifecycleKMP().value) {
    null -> EmptyScreen()
    else ->
      DisplayScreen(
        navigateUp = navigateUp,
        keypadPrefs = keypadPrefs,
        controller = themmoController,
        onThemeChange = { newValue ->
          themmoController.setThemingMode(newValue)
          viewModel.updateThemingMode(newValue)
        },
        onDynamicThemeChange = { newValue ->
          themmoController.enableDynamicTheme(newValue)
          viewModel.updateDynamicTheme(newValue)
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
  keypadPrefs: KeypadPreferences,
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
    title = stringResource(Res.string.settings_display),
    navigationIcon = { NavigateUpButton(navigateUp) },
  ) { paddingValues ->
    Column(
      modifier =
        Modifier.fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(paddingValues)
          .padding(horizontal = Sizes.large),
      verticalArrangement = ListItemDefaults.ListArrangement,
    ) {
      ThemingModeSelector(
        onThemeChange = onThemeChange,
        currentThemingMode = controller.currentThemingMode,
      )

      AmoledSelector(
        currentThemingMode = controller.currentThemingMode,
        onAmoledThemeChange = onAmoledThemeChange,
        isAmoledThemeEnabled = controller.isAmoledThemeEnabled,
      )
      val isDynamicThemingSupported = isDynamicThemingSupported()
      val isColorSelectorEnabled =
        remember(controller.isDynamicThemeEnabled) { !controller.isDynamicThemeEnabled }
      if (isDynamicThemingSupported) {
        DynamicThemingSelector(
          isColorSelectorEnabled = isColorSelectorEnabled,
          onDynamicThemeChange = onDynamicThemeChange,
          isDynamicThemeEnabled = controller.isDynamicThemeEnabled,
        )
      }
      val isStyleSelectorEnabled =
        remember(isColorSelectorEnabled, controller.currentCustomColor) {
          isColorSelectorEnabled and (controller.currentCustomColor != Color.Unspecified)
        }
      ColorSelector(
        isColorSelectorEnabled = isColorSelectorEnabled,
        isStyleSelectorEnabled = isStyleSelectorEnabled,
        onColorChange = onColorChange,
        currentCustomColor = controller.currentCustomColor,
      )
      StyleSelector(
        isStyleSelectorEnabled = isStyleSelectorEnabled,
        onMonetModeChange = onMonetModeChange,
        currentMonetMode = controller.currentMonetMode,
        currentCustomColor = controller.currentCustomColor,
        currentThemingMode = controller.currentThemingMode,
      )

      ListHeader(stringResource(Res.string.settings_additional))

      ListItemExpressive(
        icon = IconPack.ClearBold,
        headlineText = stringResource(Res.string.settings_ac_button),
        supportingText = stringResource(Res.string.settings_ac_button_support),
        switchState = keypadPrefs.acButton,
        onSwitchChange = updateAcButton,
        shapes = ListItemDefaults.firstShapes,
      )

      ListItemExpressive(
        icon = Symbols.ExposureZero,
        headlineText = stringResource(Res.string.settings_middle_zero),
        supportingText = stringResource(Res.string.settings_middle_zero_support),
        switchState = keypadPrefs.middleZero,
        onSwitchChange = updateMiddleZero,
        shapes = ListItemDefaults.middleShapes,
      )

      ListItemExpressive(
        icon = Symbols.Language,
        headlineText = stringResource(Res.string.settings_language),
        supportingText = stringResource(Res.string.settings_language_support),
        onClick = { navigateToLanguages() },
        shapes = ListItemDefaults.lastShapes,
      )
    }
  }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ThemingModeSelector(
  onThemeChange: (ThemingMode) -> Unit,
  currentThemingMode: ThemingMode,
) {
  ListItemExpressive(
    shapes = ListItemDefaults.firstShapes,
    leadingContent = { Icon(Symbols.Palette, stringResource(Res.string.settings_color_theme)) },
    headlineContent = { Text(stringResource(Res.string.settings_color_theme)) },
    supportingContent = { Text(stringResource(Res.string.settings_color_theme_support)) },
    secondaryContent = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Sizes.small),
      ) {
        ToggleButton(
          onCheckedChange = { onThemeChange(ThemingMode.AUTO) },
          checked = ThemingMode.AUTO == currentThemingMode,
          shapes = ToggleButtonDefaults.shapes(),
        ) {
          Text(stringResource(Res.string.settings_auto), maxLines = 1)
        }
        ToggleButton(
          onCheckedChange = { onThemeChange(ThemingMode.FORCE_LIGHT) },
          checked = ThemingMode.FORCE_LIGHT == currentThemingMode,
          shapes = ToggleButtonDefaults.shapes(),
        ) {
          Text(stringResource(Res.string.settings_light_mode), maxLines = 1)
        }
        ToggleButton(
          onCheckedChange = { onThemeChange(ThemingMode.FORCE_DARK) },
          checked = ThemingMode.FORCE_DARK == currentThemingMode,
          shapes = ToggleButtonDefaults.shapes(),
        ) {
          Text(stringResource(Res.string.settings_dark_mode), maxLines = 1)
        }
      }
    },
  )
}

@Composable
private fun AmoledSelector(
  currentThemingMode: ThemingMode,
  onAmoledThemeChange: (Boolean) -> Unit,
  isAmoledThemeEnabled: Boolean,
) {
  AnimatedVisibility(
    visible = currentThemingMode != ThemingMode.FORCE_LIGHT,
    enter = expandVertically() + fadeIn(),
    exit = shrinkVertically() + fadeOut(),
  ) {
    ListItemExpressive(
      icon = Symbols.DarkModeFill,
      headlineText = stringResource(Res.string.settings_amoled_dark),
      supportingText = stringResource(Res.string.settings_amoled_dark_support),
      switchState = isAmoledThemeEnabled,
      onSwitchChange = onAmoledThemeChange,
      shapes = ListItemDefaults.middleShapes,
    )
  }
}

@Composable
private fun DynamicThemingSelector(
  isColorSelectorEnabled: Boolean,
  onDynamicThemeChange: (Boolean) -> Unit,
  isDynamicThemeEnabled: Boolean,
) {
  val shapes =
    if (isColorSelectorEnabled) ListItemDefaults.middleShapes else ListItemDefaults.lastShapes
  ListItemExpressive(
    icon = Symbols.Colorize,
    headlineText = stringResource(Res.string.settings_dynamic_colors),
    supportingText = stringResource(Res.string.settings_dynamic_colors_support),
    switchState = isDynamicThemeEnabled,
    onSwitchChange = onDynamicThemeChange,
    shapes = shapes,
  )
}

@Composable
private fun ColorSelector(
  isColorSelectorEnabled: Boolean,
  isStyleSelectorEnabled: Boolean,
  onColorChange: (Color) -> Unit,
  currentCustomColor: Color,
) {
  AnimatedVisibility(
    visible = isColorSelectorEnabled,
    enter = expandVertically() + fadeIn(),
    exit = shrinkVertically() + fadeOut(),
  ) {
    val shapes =
      if (isStyleSelectorEnabled) ListItemDefaults.middleShapes else ListItemDefaults.lastShapes
    ListItemExpressive(
      shapes = shapes,
      leadingContent = { Spacer(Modifier.size(24.dp)) }, // empty icon spacing
      headlineContent = { Text(stringResource(Res.string.settings_selected_color)) },
      secondaryContentPadding = PaddingValues(0.dp),
      secondaryContent = {
        ColorSelector(
          modifier = Modifier.fillMaxWidth(),
          contentPadding = PaddingValues(start = 56.dp, end = Sizes.large, bottom = Sizes.small),
          currentColor =
            // Disable custom color and use brand color scheme
            if (currentCustomColor == Color.Unspecified) brandColor else currentCustomColor,
          onColorClick = { onColorChange(if (it == brandColor) Color.Unspecified else it) },
          colors = colorSchemes,
        )
      },
    )
  }
}

@Composable
private fun StyleSelector(
  isStyleSelectorEnabled: Boolean,
  onMonetModeChange: (MonetMode) -> Unit,
  currentMonetMode: MonetMode,
  currentCustomColor: Color,
  currentThemingMode: ThemingMode,
) {
  AnimatedVisibility(
    visible = isStyleSelectorEnabled,
    enter = expandVertically() + fadeIn(),
    exit = shrinkVertically() + fadeOut(),
  ) {
    ListItemExpressive(
      shapes = ListItemDefaults.lastShapes,
      leadingContent = { Spacer(Modifier.size(24.dp)) }, // empty icon spacing
      headlineContent = { Text(stringResource(Res.string.settings_selected_style)) },
      secondaryContentPadding = PaddingValues(0.dp),
      secondaryContent = {
        MonetModeSelector(
          modifier = Modifier.fillMaxWidth(),
          selected = currentMonetMode,
          onItemClick = onMonetModeChange,
          customColor = currentCustomColor,
          themingMode = currentThemingMode,
          paddingValues = PaddingValues(start = 56.dp, end = Sizes.large, bottom = Sizes.small),
        )
      },
    )
  }
}

@Composable @Stable internal expect fun isDynamicThemingSupported(): Boolean

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
private fun Preview() {
  Themmo(
    themmoController =
      rememberThemmoController(dynamicThemeEnabled = false, customColor = colorSchemes[1])
  ) { themmoController ->
    DisplayScreen(
      navigateUp = {},
      keypadPrefs = KeypadPreferences(middleZero = true, acButton = true),
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
