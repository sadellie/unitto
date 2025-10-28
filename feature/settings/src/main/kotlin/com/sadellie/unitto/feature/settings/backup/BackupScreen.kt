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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.common.showToast
import com.sadellie.unitto.core.designsystem.icons.symbols.FileSave
import com.sadellie.unitto.core.designsystem.icons.symbols.RestorePage
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.shapes.Shapes
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListArrangement
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import com.sadellie.unitto.core.ui.firstShape
import com.sadellie.unitto.core.ui.lastShape
import com.sadellie.unitto.core.ui.middleShape
import com.sadellie.unitto.feature.settings.R
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
internal fun BackupRoute(
  viewModel: BackupViewModel = hiltViewModel(),
  navigateUpAction: () -> Unit,
) {
  val context = LocalContext.current
  val resources = LocalResources.current
  val uiState: BackupUIState = viewModel.uiState.collectAsStateWithLifecycle().value
  val showErrorToast: Boolean =
    viewModel.showErrorToast.collectAsStateWithLifecycle(initialValue = false).value

  LaunchedEffect(showErrorToast) {
    if (showErrorToast) showToast(context, resources.getString(R.string.common_error))
  }

  when (uiState) {
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
    title = stringResource(R.string.settings_backup),
    navigationIcon = { NavigateUpButton(navigateUpAction) },
  ) { paddingValues ->
    val scrollState = rememberScrollState()
    Column(
      modifier =
        Modifier.verticalScroll(scrollState)
          .padding(paddingValues)
          .padding(start = Sizes.large, end = Sizes.large, bottom = Sizes.large),
      verticalArrangement = ListArrangement,
    ) {
      BackupRestoreControls(
        modifier = Modifier.padding(vertical = Sizes.large),
        onBackup = onBackup,
        onRestore = onRestore,
      )
      ListItemExpressive(
        headlineContent = { Text(stringResource(R.string.settings_saved_expressions)) },
        supportingContent = { Text("${uiState.savedExpressions}") },
        shape = ListItemDefaults.firstShape,
      )
      ListItemExpressive(
        headlineContent = { Text(stringResource(R.string.settings_favorite_units)) },
        supportingContent = { Text("${uiState.favoriteUnits}") },
        shape = ListItemDefaults.middleShape,
      )
      ListItemExpressive(
        headlineContent = { Text(stringResource(R.string.settings_used_units)) },
        supportingContent = { Text("${uiState.usedUnits}") },
        shape = ListItemDefaults.middleShape,
      )
      ListItemExpressive(
        headlineContent = { Text(stringResource(R.string.settings_favorite_time_zones)) },
        supportingContent = { Text("${uiState.favoriteTimeZones}") },
        shape = ListItemDefaults.lastShape,
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

  Row(
    modifier = modifier.fillMaxWidth().clip(Shapes.ExtraLarge),
    horizontalArrangement = Arrangement.spacedBy(2.dp),
  ) {
    BackupRestoreButton(
      modifier = Modifier.weight(1f),
      onClick = { backupLauncher.launchSafely(backupFileName()) },
      imageVector = Symbols.FileSave,
      label = stringResource(R.string.settings_back_up),
    )
    BackupRestoreButton(
      modifier = Modifier.weight(1f),
      onClick = { restoreLauncher.launchSafely(arrayOf(BACKUP_MIME_TYPE)) },
      imageVector = Symbols.RestorePage,
      label = stringResource(R.string.settings_restore),
    )
  }
}

@Composable
private fun BackupScreenInProgress() {
  BackHandler {}
  Scaffold { padding ->
    Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
      CircularProgressIndicator()
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
  Column(
    modifier =
      modifier
        .clickable(onClick = onClick)
        .background(MaterialTheme.colorScheme.surfaceVariant)
        .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(4.dp),
  ) {
    Icon(imageVector, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
