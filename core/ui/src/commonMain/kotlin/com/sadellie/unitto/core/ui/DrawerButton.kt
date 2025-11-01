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

package com.sadellie.unitto.core.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sadellie.unitto.core.designsystem.icons.symbols.Menu
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_open_menu_description

/**
 * Button that is used in Top bars to open drawer.
 *
 * @param onClick Action to be called when button is clicked.
 */
@Composable
fun DrawerButton(
  onClick: () -> Unit,
  colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
) {
  IconButton(
    onClick = onClick,
    shapes = IconButtonDefaults.shapes(),
    colors = colors,
    modifier =
      Modifier.size(
        IconButtonDefaults.smallContainerSize(IconButtonDefaults.IconButtonWidthOption.Uniform)
      ),
  ) {
    Icon(
      imageVector = Symbols.Menu,
      contentDescription = stringResource(Res.string.common_open_menu_description),
      modifier = Modifier.size(IconButtonDefaults.smallIconSize),
    )
  }
}
