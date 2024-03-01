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

package com.sadellie.unitto.feature.calculator

import androidx.activity.ComponentActivity
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.common.Token
import org.junit.Rule
import org.junit.Test

class CalculatorScreenTest {
  @get:Rule val composeTestRule = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun ready(): Unit =
    with(composeTestRule) {
      setContent {
        Ready(
          uiState =
            CalculatorUIState.Ready(
              input = TextFieldState(),
              output = CalculationResult.Empty,
              radianMode = false,
              precision = 3,
              outputFormat = OutputFormat.PLAIN,
              formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
              history = emptyList(),
              middleZero = false,
              acButton = true,
              additionalButtons = false,
              inverseMode = false,
              partialHistoryView = true,
            ),
          openDrawer = {},
          onAddTokenClick = {},
          onBracketsClick = {},
          onDeleteClick = {},
          onClearClick = {},
          onEqualClick = {},
          onRadianModeClick = {},
          onAdditionalButtonsClick = {},
          onInverseModeClick = {},
          onClearHistoryClick = {},
          onDeleteHistoryItemClick = {},
        )
      }

      onNodeWithTag("loading").assertDoesNotExist()
      onNodeWithTag("ready").assertExists()
    }

  @Test
  fun ready_swipeForHistory(): Unit =
    with(composeTestRule) {
      setContent {
        Ready(
          uiState =
            CalculatorUIState.Ready(
              input = TextFieldState(),
              output = CalculationResult.Empty,
              radianMode = false,
              precision = 3,
              outputFormat = OutputFormat.PLAIN,
              formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
              history = emptyList(),
              middleZero = false,
              acButton = true,
              additionalButtons = false,
              inverseMode = false,
              partialHistoryView = true,
            ),
          openDrawer = {},
          onAddTokenClick = {},
          onBracketsClick = {},
          onDeleteClick = {},
          onClearClick = {},
          onEqualClick = {},
          onRadianModeClick = {},
          onAdditionalButtonsClick = {},
          onInverseModeClick = {},
          onClearHistoryClick = {},
          onDeleteHistoryItemClick = {},
        )
      }

      onNodeWithTag("inputBox").performTouchInput { swipeDown() }
      onNodeWithTag("historyButton").performClick()
      onNodeWithText(composeTestRule.activity.getString(R.string.calculator_clear_history_support))
        .assertExists()
    }
}
