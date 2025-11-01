/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2025 Elshan Agaev
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

package com.sadellie.unitto.feature.settings.backup

import android.content.ActivityNotFoundException
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.designsystem.icons.symbols.FileSave
import com.sadellie.unitto.core.designsystem.icons.symbols.RestorePage
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.ListItemExpressiveDefaults
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.settings_back_up
import unitto.core.common.generated.resources.settings_backup
import unitto.core.common.generated.resources.settings_favorite_time_zones
import unitto.core.common.generated.resources.settings_favorite_units
import unitto.core.common.generated.resources.settings_restore
import unitto.core.common.generated.resources.settings_saved_expressions
import unitto.core.common.generated.resources.settings_used_units

@Composable
internal fun BackupRoute(
  viewModel: BackupViewModel = koinViewModel(),
  navigateUpAction: () -> Unit,
) {
  when (val uiState: BackupUIState = viewModel.uiState.collectAsStateWithLifecycle().value) {
    BackupUIState.Loading -> EmptyScreen()
    BackupUIState.InProgress -> BackupScreenInProgress()
    is BackupUIState.Ready ->
      BackupScreenReady(
        uiState = uiState,
        navigateUpAction = navigateUpAction,
        onBackup = viewModel::backup,
        onRestore = viewModel::restore,
      )
  }
}

@Composable
private fun BackupScreenReady(
  uiState: BackupUIState.Ready,
  navigateUpAction: () -> Unit,
  onBackup: (Context, Uri) -> Unit,
  onRestore: (Context, Uri) -> Unit,
) {
  ScaffoldWithLargeTopBar(
    title = stringResource(Res.string.settings_backup),
    navigationIcon = { NavigateUpButton(navigateUpAction) },
  ) { paddingValues ->
    val scrollState = rememberScrollState()
    Column(
      modifier =
        Modifier.verticalScroll(scrollState)
          .padding(paddingValues)
          .padding(start = Sizes.large, end = Sizes.large, bottom = Sizes.large),
      verticalArrangement = ListItemExpressiveDefaults.ListArrangement,
    ) {
      BackupRestoreControls(
        modifier = Modifier.padding(vertical = Sizes.large),
        onBackup = onBackup,
        onRestore = onRestore,
      )
      ListItemExpressive(
        headlineContent = { Text(stringResource(Res.string.settings_saved_expressions)) },
        supportingContent = { Text("${uiState.savedExpressions}") },
        shape = ListItemExpressiveDefaults.firstShape,
        onClick = null,
      )
      ListItemExpressive(
        headlineContent = { Text(stringResource(Res.string.settings_favorite_units)) },
        supportingContent = { Text("${uiState.favoriteUnits}") },
        shape = ListItemExpressiveDefaults.middleShape,
        onClick = null,
      )
      ListItemExpressive(
        headlineContent = { Text(stringResource(Res.string.settings_used_units)) },
        supportingContent = { Text("${uiState.usedUnits}") },
        shape = ListItemExpressiveDefaults.middleShape,
        onClick = null,
      )
      ListItemExpressive(
        headlineContent = { Text(stringResource(Res.string.settings_favorite_time_zones)) },
        supportingContent = { Text("${uiState.favoriteTimeZones}") },
        shape = ListItemExpressiveDefaults.lastShape,
        onClick = null,
      )
    }
  }
}

@Composable
private fun BackupRestoreControls(
  modifier: Modifier,
  onBackup: (Context, Uri) -> Unit,
  onRestore: (Context, Uri) -> Unit,
) {
  val mContext = LocalContext.current
  val backupLauncher =
    rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument(BACKUP_MIME_TYPE)) {
      pickedUri ->
      if (pickedUri != null) onBackup(mContext, pickedUri)
    }

  val restoreLauncher =
    rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { pickedUri ->
      if (pickedUri != null) onRestore(mContext, pickedUri)
    }

  Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
    BackupRestoreButton(
      modifier = Modifier,
      onClick = { backupLauncher.launchSafely(backupFileName()) },
      imageVector = Symbols.FileSave,
      label = stringResource(Res.string.settings_back_up),
    )
    BackupRestoreButton(
      modifier = Modifier,
      onClick = { restoreLauncher.launchSafely(arrayOf(BACKUP_MIME_TYPE)) },
      imageVector = Symbols.RestorePage,
      label = stringResource(Res.string.settings_restore),
    )
  }
}

@Composable
private fun BackupScreenInProgress() {
  BackHandler {}
  Scaffold(containerColor = MaterialTheme.colorScheme.surfaceContainer) { padding ->
    Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
      CircularWavyProgressIndicator()
    }
  }
}

@Composable
private fun BackupRestoreButton(
  modifier: Modifier,
  onClick: () -> Unit,
  imageVector: ImageVector,
  label: String,
) {
  val interactionSource = remember { MutableInteractionSource() }
  Column(
    modifier =
      modifier.clickable(
        onClick = onClick,
        indication = null,
        interactionSource = interactionSource,
      ),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(Sizes.small),
  ) {
    FilledIconButton(
      onClick = onClick,
      shapes = IconButtonDefaults.shapes(),
      interactionSource = interactionSource,
      colors =
        IconButtonDefaults.filledIconButtonColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
      modifier =
        Modifier.size(
          IconButtonDefaults.mediumContainerSize(IconButtonDefaults.IconButtonWidthOption.Wide)
        ),
    ) {
      Icon(
        imageVector = imageVector,
        contentDescription = label,
        modifier = Modifier.size(IconButtonDefaults.mediumIconSize),
      )
    }
    Text(
      text = label,
      color = MaterialTheme.colorScheme.onSurface,
      style = MaterialTheme.typography.labelLarge,
    )
  }
}

private fun <T> ActivityResultLauncher<T>.launchSafely(input: T) {
  try {
    this.launch(input)
  } catch (_: ActivityNotFoundException) {
    Log.e("SettingsScreen", "launchSafely: ActivityNotFoundException")
  }
}

private fun backupFileName(): String {
  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")
  val formattedCurrentDate = ZonedDateTime.now().format(formatter)
  return "$formattedCurrentDate.unitto"
}

private const val BACKUP_MIME_TYPE = "application/octet-stream"

@Composable
@Preview
private fun PreviewBackupScreenReady() {
  BackupScreenReady(
    uiState =
      BackupUIState.Ready(
        favoriteUnits = 123,
        savedExpressions = 123,
        favoriteTimeZones = 123,
        usedUnits = 123,
      ),
    navigateUpAction = {},
    onBackup = { _, _ -> },
    onRestore = { _, _ -> },
  )
}

@Composable
@Preview
private fun PreviewBackupScreenInProgress() {
  BackupScreenInProgress()
}
