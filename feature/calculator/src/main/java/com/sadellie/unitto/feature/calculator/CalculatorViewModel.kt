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

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.textfield.addBracket
import com.sadellie.unitto.core.ui.common.textfield.addTokens
import com.sadellie.unitto.core.ui.common.textfield.deleteTokens
import com.sadellie.unitto.core.ui.common.textfield.getTextField
import com.sadellie.unitto.core.ui.common.textfield.placeCursorAtTheEnd
import com.sadellie.unitto.data.common.format
import com.sadellie.unitto.data.common.isExpression
import com.sadellie.unitto.data.common.stateIn
import com.sadellie.unitto.data.model.HistoryItem
import com.sadellie.unitto.data.model.repository.CalculatorHistoryRepository
import com.sadellie.unitto.data.model.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadellie.evaluatto.Expression
import io.github.sadellie.evaluatto.ExpressionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
internal class CalculatorViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val calculatorHistoryRepository: CalculatorHistoryRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val inputKey = "CALCULATOR_INPUT"
    private val input = MutableStateFlow(savedStateHandle.getTextField(inputKey))
    private val result = MutableStateFlow<CalculationResult>(CalculationResult.Empty)
    private val equalClicked = MutableStateFlow(false)
    private val prefs = userPrefsRepository.calculatorPrefs
        .stateIn(viewModelScope, null)
    private var fractionJob: Job? = null

    val uiState: StateFlow<CalculatorUIState> = combine(
        input,
        result,
        prefs,
        calculatorHistoryRepository.historyFlow,
    ) { input, result, prefs, history ->
        prefs ?: return@combine CalculatorUIState.Loading

        return@combine CalculatorUIState.Ready(
            input = input,
            output = result,
            radianMode = prefs.radianMode,
            precision = prefs.precision,
            outputFormat = prefs.outputFormat,
            formatterSymbols = prefs.formatterSymbols,
            history = history,
            middleZero = prefs.middleZero,
            acButton = prefs.acButton,
            additionalButtons = prefs.additionalButtons,
            inverseMode = prefs.inverseMode,
            partialHistoryView = prefs.partialHistoryView,
        )
    }
        .mapLatest { ui ->
            if (ui !is CalculatorUIState.Ready) return@mapLatest ui
            if (equalClicked.value) return@mapLatest ui

            if (!ui.input.text.isExpression()) {
                result.update { CalculationResult.Empty }
                return@mapLatest ui
            }

            result.update {
                try {
                    CalculationResult.Default(
                        calculate(
                            input = ui.input.text,
                            radianMode = ui.radianMode,
                        )
                            .format(ui.precision, ui.outputFormat),
                    )
                } catch (e: ExpressionException.DivideByZero) {
                    CalculationResult.Empty
                } catch (e: Exception) {
                    CalculationResult.Empty
                }
            }

            ui
        }
        .stateIn(viewModelScope, CalculatorUIState.Loading)

    fun addTokens(tokens: String) {
        val isClearInputNeeded = equalClicked.value and Token.Digit.allWithDot.contains(tokens)
        val newValue = when {
            // Clean input after clicking "=" and any token that is a Digit
            isClearInputNeeded -> TextFieldValue()
            equalClicked.value -> input.value.placeCursorAtTheEnd()
            else -> input.value
        }.addTokens(tokens)
        updateInput(newValue)
    }

    fun addBracket() {
        val newValue = if (equalClicked.value) {
            // Cursor is set to 0 when equal is clicked
            input.value.placeCursorAtTheEnd()
        } else {
            input.value
        }.addBracket()
        updateInput(newValue)
    }

    fun deleteTokens() {
        val newValue = if (equalClicked.value) {
            TextFieldValue()
        } else {
            input.value.deleteTokens()
        }
        updateInput(newValue)
    }

    fun clearInput() = updateInput(TextFieldValue())

    fun updateInput(value: TextFieldValue) {
        fractionJob?.cancel()
        equalClicked.update { false }
        input.update { value }
        savedStateHandle[inputKey] = value.text
    }

    fun updateRadianMode(newValue: Boolean) = viewModelScope.launch {
        userPrefsRepository.updateRadianMode(newValue)
    }

    fun updateAdditionalButtons(newValue: Boolean) = viewModelScope.launch {
        userPrefsRepository.updateAdditionalButtons(newValue)
    }

    fun updateInverseMode(newValue: Boolean) = viewModelScope.launch {
        userPrefsRepository.updateInverseMode(newValue)
    }

    fun clearHistory() = viewModelScope.launch(Dispatchers.IO) {
        calculatorHistoryRepository.clear()
    }

    fun deleteHistoryItem(item: HistoryItem) = viewModelScope.launch(Dispatchers.IO) {
        calculatorHistoryRepository.delete(item)
    }

    fun equal() = viewModelScope.launch {
        val prefs = prefs.value ?: return@launch
        if (equalClicked.value) return@launch
        if (!input.value.text.isExpression()) return@launch

        val result = try {
            calculate(input.value.text, prefs.radianMode, RoundingMode.DOWN)
        } catch (e: ExpressionException.DivideByZero) {
            equalClicked.update { true }
            result.update { CalculationResult.DivideByZeroError }
            return@launch
        } catch (e: ExpressionException.FactorialCalculation) {
            equalClicked.update { true }
            result.update { CalculationResult.Error }
            return@launch
        } catch (e: Exception) {
            equalClicked.update { true }
            result.update { CalculationResult.Error }
            return@launch
        }

        equalClicked.update { true }

        val resultFormatted = result
            .format(prefs.precision, prefs.outputFormat)
            .replace("-", Token.Operator.minus)

        withContext(Dispatchers.IO) {
            calculatorHistoryRepository.add(
                expression = input.value.text.replace("-", Token.Operator.minus),
                result = resultFormatted,
            )
        }

        fractionJob?.cancel()
        fractionJob = launch(Dispatchers.Default) {
            val fraction = result.toFractionalString()

            input.update { TextFieldValue(resultFormatted, TextRange.Zero) }
            this@CalculatorViewModel.result.update { CalculationResult.Fraction(fraction) }
        }
    }

    private suspend fun calculate(
        input: String,
        radianMode: Boolean,
        roundingMode: RoundingMode = RoundingMode.HALF_EVEN,
    ): BigDecimal = withContext(Dispatchers.Default) {
        Expression(input, radianMode, roundingMode)
            .calculate()
            .also {
                if (it > BigDecimal.valueOf(Double.MAX_VALUE)) throw ExpressionException.TooBig()
            }
    }
}
