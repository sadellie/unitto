/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.feature.datecalculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.MenuButton
import com.sadellie.unitto.core.ui.common.SettingsButton
import com.sadellie.unitto.core.ui.common.UnittoScreenWithTopBar
import com.sadellie.unitto.feature.datecalculator.addsubtract.AddSubtractPage
import com.sadellie.unitto.feature.datecalculator.difference.DateDifferencePage
import kotlinx.coroutines.launch

@Composable
internal fun DateCalculatorRoute(
    navigateToMenu: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    DateCalculatorScreen(
        navigateToMenu = navigateToMenu,
        navigateToSettings = navigateToSettings,
    )
}

@Composable
internal fun DateCalculatorScreen(
    navigateToMenu: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    val addSubtractLabel = "${stringResource(R.string.add)}/${stringResource(R.string.subtract)}"
    val differenceLabel = stringResource(R.string.difference)
    val focusManager = LocalFocusManager.current
    var topBarShown by remember { mutableStateOf(true) }

    val allTabs = remember { mutableListOf(addSubtractLabel, differenceLabel) }
    val pagerState = rememberPagerState { allTabs.size }
    val coroutineScope = rememberCoroutineScope()

    UnittoScreenWithTopBar(
        modifier = Modifier,
        title = { Text(stringResource(R.string.date_calculator)) },
        navigationIcon = { MenuButton(navigateToMenu) },
        actions = { SettingsButton(navigateToSettings) },
        showTopBar = topBarShown,
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
            ) {
                allTabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = index == pagerState.currentPage,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(tab) }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) { page ->
                when (page) {
                    0 -> AddSubtractPage(
                        toggleTopBar = { topBarShown = it }
                    )
                    1 -> {
                        focusManager.clearFocus(true)
                        topBarShown = true

                        DateDifferencePage()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DateCalculatorScreenPreview() {
    DateCalculatorScreen(
        navigateToMenu = {},
        navigateToSettings = {},
    )
}
