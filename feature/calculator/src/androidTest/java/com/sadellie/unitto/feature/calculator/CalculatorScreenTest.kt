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

package com.sadellie.unitto.feature.calculator

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.text.input.TextFieldValue
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import org.junit.Rule
import org.junit.Test

class CalculatorScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loading_showLoadingKeyboard(): Unit = with(composeTestRule) {
        setContent {
            CalculatorScreen(
                uiState = CalculatorUIState.Loading,
                navigateToMenu = {},
                navigateToSettings = {},
                addTokens = {},
                clearInput = {},
                deleteTokens = {},
                onCursorChange = {},
                toggleCalculatorMode = {},
                evaluate = {},
                clearHistory = {},
                addBracket = {}
            )
        }

        onNodeWithTag("loading").assertExists()
    }

    @Test
    fun ready_showRealKeyboard(): Unit = with(composeTestRule) {
        setContent {
            CalculatorScreen(
                uiState = CalculatorUIState.Ready(
                    input = TextFieldValue(),
                    output = CalculationResult.Empty,
                    radianMode = false,
                    precision = 3,
                    outputFormat = OutputFormat.PLAIN,
                    formatterSymbols = FormatterSymbols.Spaces,
                    history = emptyList(),
                    allowVibration = false,
                    middleZero = false,
                    acButton = true,
                    partialHistoryView = true
                ),
                navigateToMenu = {},
                navigateToSettings = {},
                addTokens = {},
                clearInput = {},
                deleteTokens = {},
                onCursorChange = {},
                toggleCalculatorMode = {},
                evaluate = {},
                clearHistory = {},
                addBracket = {}
            )
        }

        onNodeWithTag("loading").assertDoesNotExist()
        onNodeWithTag("ready").assertExists()
    }

    @Test
    fun ready_swipeForHistory(): Unit = with(composeTestRule) {
        setContent {
            CalculatorScreen(
                uiState = CalculatorUIState.Ready(
                    input = TextFieldValue(),
                    output = CalculationResult.Empty,
                    radianMode = false,
                    precision = 3,
                    outputFormat = OutputFormat.PLAIN,
                    formatterSymbols = FormatterSymbols.Spaces,
                    history = emptyList(),
                    allowVibration = false,
                    middleZero = false,
                    acButton = true,
                    partialHistoryView = true
                ),
                navigateToMenu = {},
                navigateToSettings = {},
                addTokens = {},
                clearInput = {},
                deleteTokens = {},
                onCursorChange = {},
                toggleCalculatorMode = {},
                evaluate = {},
                clearHistory = {},
                addBracket = {}
            )
        }

        onNodeWithTag("inputBox")
            .performTouchInput { swipeDown() }
        onNodeWithTag("historyButton")
            .performClick()
        onNodeWithText(composeTestRule.activity.getString(R.string.calculator_clear_history_support))
            .assertExists()
    }
}
