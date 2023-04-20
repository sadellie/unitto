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

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.ui.R
import com.sadellie.unitto.core.ui.common.Header
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.SegmentedButton
import com.sadellie.unitto.core.ui.common.SegmentedButtonRow
import com.sadellie.unitto.core.ui.common.UnittoListItem
import com.sadellie.unitto.core.ui.common.UnittoScreenWithLargeTopBar
import com.sadellie.unitto.data.model.LauncherIcon
import com.sadellie.unitto.feature.settings.components.ColorSelector
import com.sadellie.unitto.feature.settings.components.IconsSelector
import io.github.sadellie.themmo.ThemingMode
import io.github.sadellie.themmo.ThemmoController

private val colorSchemes: List<Color> by lazy {
    listOf(
        Color(0xFFE91E63),
        Color(0xFFFF9800),
        Color(0xFF4CAF50),
        Color(0xFF2196F3),
        Color(0xFF9C27B0),
        Color(0xFF5C76AA),
        Color(0xFF756FAA),
        Color(0xFF9E6C2A),
    )
}

@Composable
internal fun ThemesRoute(
    navigateUpAction: () -> Unit = {},
    themmoController: ThemmoController,
    viewModel: SettingsViewModel
) {
    val userPrefs = viewModel.userPrefs.collectAsStateWithLifecycle()

    ThemesScreen(
        navigateUpAction = navigateUpAction,
        currentThemingMode = themmoController.currentThemingMode,
        onThemeChange = {
            themmoController.setThemingMode(it)
            viewModel.updateThemingMode(it)
        },
        isDynamicThemeEnabled = themmoController.isDynamicThemeEnabled,
        onDynamicThemeChange = {
            themmoController.enableDynamicTheme(it)
            viewModel.updateDynamicTheme(it)
        },
        isAmoledThemeEnabled = themmoController.isAmoledThemeEnabled,
        onAmoledThemeChange = {
            themmoController.enableAmoledTheme(it)
            viewModel.updateAmoledTheme(it)
        },
        selectedColor = themmoController.currentCustomColor,
        onColorChange = {
            themmoController.setCustomColor(it)
            viewModel.updateCustomColor(it)
        },
        currentIcon = userPrefs.value.launcherIcon,
        onIconChange = { newValue, callback ->
            viewModel.updateLauncherIcon(newValue, callback)
        },
    )
}

@Composable
private fun ThemesScreen(
    navigateUpAction: () -> Unit,
    currentThemingMode: ThemingMode,
    onThemeChange: (ThemingMode) -> Unit,
    isDynamicThemeEnabled: Boolean,
    onDynamicThemeChange: (Boolean) -> Unit,
    isAmoledThemeEnabled: Boolean,
    onAmoledThemeChange: (Boolean) -> Unit,
    selectedColor: Color,
    onColorChange: (Color) -> Unit,
    currentIcon: LauncherIcon,
    onIconChange: (LauncherIcon, () -> Unit) -> Unit
) {
    val mContext = LocalContext.current
    val themingModes by remember {
        mutableStateOf(
            mapOf(
                ThemingMode.AUTO to R.string.force_auto_mode,
                ThemingMode.FORCE_LIGHT to R.string.force_light_mode,
                ThemingMode.FORCE_DARK to R.string.force_dark_mode
            )
        )
    }
    var showIconChangeWarning: Boolean by rememberSaveable { mutableStateOf(false) }

    var selectedLauncherIcon: LauncherIcon by remember { mutableStateOf(currentIcon) }
    var selectedLauncherIconColor: Color by remember { mutableStateOf(Color(currentIcon.backgroundColor)) }
    val selectedLauncherIconColorAnim = animateColorAsState(targetValue = selectedLauncherIconColor)
    val launcherIcons: List<LauncherIcon> by remember(selectedLauncherIcon) {
        derivedStateOf {
            LauncherIcon
                .values()
                .toList()
                .filter { Color(it.backgroundColor) == selectedLauncherIconColor }
        }
    }

    UnittoScreenWithLargeTopBar(
        title = stringResource(R.string.theme_setting),
        navigationIcon = { NavigateUpButton(navigateUpAction) }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {

            item {
                ListItem(
                    headlineContent = { Text(stringResource(R.string.selected_color)) },
                    supportingContent = {
                        ColorSelector(
                            modifier = Modifier.padding(top = 12.dp),
                            selected = selectedLauncherIconColor,
                            onItemClick = {
                                selectedLauncherIconColor = it
                            },
                            colorSchemes = remember {
                                LauncherIcon.values().map { Color(it.backgroundColor) }
                                    .distinct()
                            },
                        )
                    },
                    modifier = Modifier.padding(start = 40.dp)
                )
            }

            item {
                ListItem(
                    headlineContent = { Text(stringResource(R.string.selected_launcher_icon)) },
                    supportingContent = {
                        IconsSelector(
                            modifier = Modifier.padding(top = 12.dp),
                            selectedColor = selectedLauncherIconColorAnim.value,
                            icons = launcherIcons,
                            selectedIcon = selectedLauncherIcon,
                            onIconChange = { selectedLauncherIcon = it }
                        )
                    },
                    modifier = Modifier.padding(start = 40.dp)
                )
            }

            item {
                FilledTonalButton(
                    modifier = Modifier.padding(start = 56.dp),
                    enabled = currentIcon != selectedLauncherIcon,
                    onClick = { showIconChangeWarning = true },
                ) {
                    Text(stringResource(R.string.apply_label))
                }
            }

            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Palette,
                            stringResource(R.string.color_theme),
                        )
                    },
                    headlineContent = { Text(stringResource(R.string.color_theme)) },
                    supportingContent = { Text(stringResource(R.string.color_theme_support)) },
                )
            }

            item {
                SegmentedButtonRow(
                    modifier = Modifier.padding(56.dp, 8.dp, 24.dp, 2.dp)
                ) {
                    themingModes.forEach { (mode, stringRes) ->
                        SegmentedButton(
                            onClick = { onThemeChange(mode) },
                            selected = currentThemingMode == mode,
                            content = { Text(stringResource(stringRes)) }
                        )
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = currentThemingMode != ThemingMode.FORCE_LIGHT,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut(),
                ) {
                    UnittoListItem(
                        leadingContent = {
                            Icon(
                                Icons.Default.DarkMode,
                                stringResource(R.string.force_amoled_mode),
                            )
                        },
                        label = stringResource(R.string.force_amoled_mode),
                        supportContent = stringResource(R.string.force_amoled_mode_support),
                        switchState = isAmoledThemeEnabled,
                        onSwitchChange = onAmoledThemeChange
                    )
                }
            }

            item { Header(stringResource(R.string.color_scheme)) }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                item {
                    UnittoListItem(
                        leadingContent = {
                            Icon(
                                Icons.Default.Colorize,
                                stringResource(R.string.enable_dynamic_colors),
                            )
                        },
                        label = stringResource(R.string.enable_dynamic_colors),
                        supportContent = stringResource(R.string.enable_dynamic_colors_support),
                        switchState = isDynamicThemeEnabled,
                        onSwitchChange = onDynamicThemeChange
                    )
                }
            }

            item {
                AnimatedVisibility(
                    visible = !isDynamicThemeEnabled,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut(),
                ) {
                    ListItem(
                        headlineContent = { Text(stringResource(R.string.selected_color)) },
                        supportingContent = {
                            ColorSelector(
                                modifier = Modifier.padding(top = 12.dp),
                                selected = selectedColor,
                                onItemClick = onColorChange,
                                colorSchemes = colorSchemes,
                                defaultColor = Color(0xFF186c31)
                            )
                        },
                        modifier = Modifier.padding(start = 40.dp)
                    )
                }
            }
        }
    }

    if (showIconChangeWarning) {
        AlertDialog(
            icon = {
                Icon(Icons.Default.Warning, stringResource(R.string.warning_label))
            },
            title = {
                Text(stringResource(R.string.warning_label))
            },
            text = {
                Text(
                    stringResource(R.string.icon_change_warning)
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onIconChange(selectedLauncherIcon) {
                            mContext.changeIcon(selectedLauncherIcon)
                            (mContext as Activity).finish()
                        }
                        showIconChangeWarning = false
                    }
                ) {
                    Text(stringResource(R.string.apply_label))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showIconChangeWarning = false }
                ) {
                    Text(stringResource(R.string.cancel_label))
                }
            },
            onDismissRequest = { showIconChangeWarning = false }
        )
    }
}

private fun Context.changeIcon(newIcon: LauncherIcon) {
    // Enable new icon
    packageManager.setComponentEnabledSetting(
        ComponentName(this, newIcon.component),
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP
    )

    val packages = LauncherIcon.values().toList() - newIcon

    // We make sure that other icons are disabled to avoid bugs.
    packages.forEach {
        packageManager.setComponentEnabledSetting(
            ComponentName(this, it.component),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}

@Preview
@Composable
private fun Preview() {
    ThemesScreen(
        navigateUpAction = {},
        currentThemingMode = ThemingMode.AUTO,
        onThemeChange = {},
        isDynamicThemeEnabled = false,
        onDynamicThemeChange = {},
        isAmoledThemeEnabled = false,
        onAmoledThemeChange = {},
        selectedColor = Color.Unspecified,
        onColorChange = {},
        currentIcon = LauncherIcon.MAIN_DEFAULT,
        onIconChange = {_,_->}
    )
}
