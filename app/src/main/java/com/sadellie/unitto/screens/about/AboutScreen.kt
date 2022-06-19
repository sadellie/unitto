/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

package com.sadellie.unitto.screens.about

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.R
import com.sadellie.unitto.data.ALL_LIBRARIES
import com.sadellie.unitto.screens.openLink


/**
 * Screen with used third party libraries
 *
 * @param navigateUpAction Action to be called when clicking back button in top bar
 */
@Stable
@Composable
fun AboutScreen(
    navigateUpAction: () -> Unit = {}
) {
    val mContext = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberSplineBasedDecay(), rememberTopAppBarScrollState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.third_party_licenses))
                },
                navigationIcon = {
                    IconButton(onClick = navigateUpAction) {
                        Icon(
                            Icons.Outlined.Close,
                            contentDescription = stringResource(id = R.string.navigate_up_description)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { padding ->
        LazyColumn(
            Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = padding
        ) {
            items(items = ALL_LIBRARIES.value) {
                OutlinedCard(
                    Modifier.clickable { it.website?.let { url -> openLink(mContext, url) } }
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                    ) {
                        Text(text = it.name, style = MaterialTheme.typography.titleLarge)
                        Text(
                            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp),
                            text = it.dev ?: "",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(text = it.description ?: "", style = MaterialTheme.typography.bodyMedium)
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
