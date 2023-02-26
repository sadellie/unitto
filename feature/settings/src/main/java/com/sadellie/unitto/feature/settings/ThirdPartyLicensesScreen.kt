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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.R
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.UnittoScreenWithLargeTopBar
import com.sadellie.unitto.core.ui.openLink
import com.sadellie.unitto.data.licenses.ALL_LIBRARIES

/**
 * Screen with used third party libraries
 *
 * @param navigateUpAction Action to be called when clicking back button in top bar
 */
@Stable
@Composable
internal fun ThirdPartyLicensesScreen(
    navigateUpAction: () -> Unit = {}
) {
    val mContext = LocalContext.current

    UnittoScreenWithLargeTopBar(
        title = stringResource(R.string.third_party_licenses),
        navigationIcon = { NavigateUpButton(navigateUpAction) }
    ) { padding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = padding.calculateTopPadding(),
                bottom = 24.dp
            )
        ) {
            items(ALL_LIBRARIES) {
                OutlinedCard(
                    Modifier.clickable { it.website?.let { url -> openLink(mContext, url) } }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp),
                            text = it.dev ?: "",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = it.description ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            modifier = Modifier.align(Alignment.End),
                            text = it.license ?: "",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}
