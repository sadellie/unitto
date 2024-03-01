/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.ui.DrawerButton
import com.sadellie.unitto.core.ui.ScaffoldWithTopBar
import com.sadellie.unitto.feature.datecalculator.addsubtract.AddSubtractPage
import com.sadellie.unitto.feature.datecalculator.difference.DateDifferencePage
import kotlinx.coroutines.launch

@Composable
internal fun DateCalculatorRoute(openDrawer: () -> Unit) {
  DateCalculatorScreen(openDrawer = openDrawer)
}

@Composable
internal fun DateCalculatorScreen(openDrawer: () -> Unit) {
  val addSubtractLabel =
    "${stringResource(R.string.date_calculator_add)}/${stringResource(R.string.date_calculator_subtract)}"
  val differenceLabel = stringResource(R.string.date_calculator_difference)
  val focusManager = LocalFocusManager.current

  val allTabs = remember { listOf(addSubtractLabel, differenceLabel) }
  val pagerState = rememberPagerState { allTabs.size }
  val coroutineScope = rememberCoroutineScope()

  ScaffoldWithTopBar(
    modifier = Modifier,
    title = { Text(stringResource(R.string.date_calculator_title)) },
    navigationIcon = { DrawerButton(openDrawer) },
  ) { paddingValues ->
    Column(Modifier.consumeWindowInsets(paddingValues).padding(paddingValues)) {
      PrimaryTabRow(selectedTabIndex = pagerState.currentPage) {
        allTabs.forEachIndexed { index, tab ->
          Tab(
            selected = index == pagerState.currentPage,
            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
            text = { Text(tab) },
          )
        }
      }

      HorizontalPager(state = pagerState, verticalAlignment = Alignment.Top) { page ->
        when (page) {
          0 -> AddSubtractPage()
          1 -> {
            SideEffect { focusManager.clearFocus(true) }
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
  DateCalculatorScreen(openDrawer = {})
}
