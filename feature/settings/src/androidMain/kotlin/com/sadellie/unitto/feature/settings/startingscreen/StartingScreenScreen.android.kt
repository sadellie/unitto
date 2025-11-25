/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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

package com.sadellie.unitto.feature.settings.startingscreen

import android.os.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.sadellie.unitto.core.designsystem.icons.symbols.AppShortcut
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.navigation.DrawerItem
import com.sadellie.unitto.core.navigation.addShortcut
import kotlinx.coroutines.launch

@Composable
internal actual fun AddShortcutButton(destination: DrawerItem) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
    val coroutineScope = rememberCoroutineScope()
    val mContext = LocalContext.current
    IconButton(onClick = { coroutineScope.launch { destination.addShortcut(mContext) } }) {
      Icon(Symbols.AppShortcut, null)
    }
  }
}
