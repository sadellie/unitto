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

package com.sadellie.unitto.feature.settings.thirdparty

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.licenses.ThirdParty
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.ListItemExpressiveDefaults
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import com.sadellie.unitto.core.ui.rememberLinkOpener
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.settings_third_party_licenses

/**
 * Screen with used third party libraries
 *
 * @param navigateUpAction Action to be called when clicking back button in top bar
 */
@Composable
internal fun ThirdPartyLicensesScreen(navigateUpAction: () -> Unit = {}) {
  ScaffoldWithLargeTopBar(
    title = stringResource(Res.string.settings_third_party_licenses),
    navigationIcon = { NavigateUpButton(navigateUpAction) },
  ) { padding ->
    val allThirdParty = remember { ThirdParty.allThirdParty() }
    val linkOpener = rememberLinkOpener()
    LazyColumn(
      verticalArrangement = ListItemExpressiveDefaults.ListArrangement,
      contentPadding = padding,
      modifier = Modifier.padding(horizontal = Sizes.large),
    ) {
      itemsIndexed(allThirdParty) { index, item ->
        ListItemExpressive(
          onClick = { linkOpener.launch(item.website) },
          shape = ListItemExpressiveDefaults.listedShaped(index, allThirdParty.size),
          headlineContent = { Text("${item.name} (${item.license})") },
          supportingContent = {
            Column {
              Text(item.dev)
              Text(item.description)
            }
          },
        )
      }
    }
  }
}

@Composable
@Preview
private fun PreviewThirdPartyLicensesScreen() {
  ThirdPartyLicensesScreen(navigateUpAction = {})
}
