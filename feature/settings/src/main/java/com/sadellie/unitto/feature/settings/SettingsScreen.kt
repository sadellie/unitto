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

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled._123
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
    val mContext = LocalContext.current

    val errorLabel = stringResource(R.string.error_label)

    val uiState: SettingsUIState = viewModel.uiState.collectAsStateWithLifecycle().value
    val backupFileUri: Uri? = viewModel.backupFileUri.collectAsStateWithLifecycle(initialValue = null).value
    val showErrorToast: Boolean = viewModel.showErrorToast.collectAsStateWithLifecycle(initialValue = false).value

    // Share backup file when it's emitted
    LaunchedEffect(backupFileUri) {
        if (backupFileUri == null) return@LaunchedEffect
        mContext.share(backupFileUri)
    }

    LaunchedEffect(showErrorToast) {
        if (showErrorToast) Toast.makeText(mContext, errorLabel, Toast.LENGTH_SHORT).show()
    }

    Crossfade(targetState = uiState) { state ->
        when (state) {
            SettingsUIState.Loading -> UnittoEmptyScreen()

            SettingsUIState.BackupInProgress -> BackingUpScreen()

            is SettingsUIState.Ready -> SettingsScreen(
                uiState = state,
                navigateUp = navigateUp,
                navControllerAction = navControllerAction,
                updateVibrations = viewModel::updateVibrations,
                clearCache = viewModel::clearCache,
                backup =  viewModel::backup,
                restore = viewModel::restore
            )
        }
    }
}

@Composable
private fun BackingUpScreen() {
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    }

    BackHandler {}
}

@Composable
private fun SettingsScreen(
    uiState: SettingsUIState.Ready,
    navigateUp: () -> Unit,
    navControllerAction: (String) -> Unit,
    updateVibrations: (Boolean) -> Unit,
    clearCache: () -> Unit,
    backup: () -> Unit,
    restore: (Uri) -> Unit = {},
) {
    val mContext = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }

    // Pass picked file uri to BackupManager
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { pickedUri ->
        if (pickedUri != null) restore(pickedUri)
    }

    UnittoScreenWithLargeTopBar(
        title = stringResource(R.string.settings_title),
        navigationIcon = { NavigateUpButton(navigateUp) },
        actions = {
            IconButton(
                onClick = { showMenu = !showMenu },
                content = { Icon(Icons.Default.MoreVert, null) }
            )
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    onClick = backup,
                    text = { Text("Backup") }
                )
                DropdownMenuItem(
                    onClick = { launcher.launch(arrayOf(backupMimeType)) },
                    text = { Text("Restore") }
                )
            }
        }
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
                switchState = uiState.enableVibrations,
                onSwitchChange = updateVibrations
            )

            AnimatedVisibility(
                visible = uiState.cacheSize > 0,
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

private fun Context.share(uri: Uri) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, uri)
        type = backupMimeType // This is a fucking war crime, it should be text/json
    }

    startActivity(shareIntent)
}

private const val backupMimeType = "application/octet-stream"

@Preview
@Composable
private fun PreviewSettingsScreen() {
    var cacheSize by remember { mutableFloatStateOf(0.9f) }

    SettingsScreen(
        uiState = SettingsUIState.Ready(
            enableVibrations = false,
            cacheSize = 2,
        ),
        navigateUp = {},
        navControllerAction = {},
        updateVibrations = {},
        clearCache = { cacheSize = 0f },
        backup = {}
    )
}

@Preview
@Composable
private fun PreviewBackingUpScreen() {
    BackingUpScreen()
}
