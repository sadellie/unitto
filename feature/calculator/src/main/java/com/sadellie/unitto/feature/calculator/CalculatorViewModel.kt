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
import com.sadellie.unitto.data.common.isGreaterThan
import com.sadellie.unitto.data.common.stateIn
import com.sadellie.unitto.data.model.HistoryItem
import com.sadellie.unitto.data.model.repository.CalculatorHistoryRepository
import com.sadellie.unitto.data.model.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadellie.evaluatto.Expression
import io.github.sadellie.evaluatto.ExpressionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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
    private var calculationJob: Job? = null

    private val inputKey = "CALCULATOR_INPUT"
    private val input = MutableStateFlow(savedStateHandle.getTextField(inputKey))
    private val result = MutableStateFlow<CalculationResult>(CalculationResult.Empty)
    private val equalClicked = MutableStateFlow(false)
    private val prefs = userPrefsRepository.calculatorPrefs.stateIn(viewModelScope, null)

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
        equalClicked.update { false }
        input.update { value }
        savedStateHandle[inputKey] = value.text
        calculateInput()
    }

    fun updateRadianMode(newValue: Boolean) = viewModelScope.launch {
        userPrefsRepository.updateRadianMode(newValue)
        calculateInput()
    }

    fun updateAdditionalButtons(newValue: Boolean) = viewModelScope.launch {
        userPrefsRepository.updateAdditionalButtons(newValue)
    }

    fun updateInverseMode(newValue: Boolean) = viewModelScope.launch {
        userPrefsRepository.updateInverseMode(newValue)
    }

    fun clearHistory() = viewModelScope.launch {
        calculatorHistoryRepository.clear()
    }

    fun deleteHistoryItem(item: HistoryItem) = viewModelScope.launch {
        calculatorHistoryRepository.delete(item)
    }

    fun equal() = viewModelScope.launch {
        val prefs = prefs.value ?: return@launch
        val inputValue = input.value.text
        if (equalClicked.value) return@launch
        if (!inputValue.isExpression()) return@launch

        val calculated = try {
            calculate(inputValue, prefs.radianMode, RoundingMode.HALF_EVEN)
                .format(prefs.precision, prefs.outputFormat)
        } catch (e: ExpressionException.DivideByZero) {
            result.update { CalculationResult.DivideByZeroError }
            return@launch
        } catch (e: Exception) {
            result.update { CalculationResult.Error }
            return@launch
        }

        val fractional = try {
            calculate(inputValue, prefs.radianMode, RoundingMode.DOWN)
                .toFractionalString()
        } catch (e: Exception) {
            result.update { CalculationResult.Error }
            return@launch
        }

        calculatorHistoryRepository.add(
            expression = inputValue,
            result = calculated,
        )

        equalClicked.update { true }
        input.update { TextFieldValue(calculated.replace("-", Token.Operator.minus)) }
        result.update { CalculationResult.Success(fractional) }
    }

    private fun calculateInput() {
        calculationJob?.cancel()
        calculationJob = viewModelScope.launch {
            if (!input.value.text.isExpression()) {
                result.update { CalculationResult.Empty }
                return@launch
            }

            val prefs = prefs.value ?: return@launch
            val newResult = try {
                val calculated = calculate(
                    input = input.value.text,
                    radianMode = prefs.radianMode,
                    roundingMode = RoundingMode.HALF_EVEN,
                )
                CalculationResult.Success(
                    calculated.format(prefs.precision, prefs.outputFormat),
                )
            } catch (e: Exception) {
                CalculationResult.Empty
            }
            result.update { newResult }
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
                if (it.isGreaterThan(maxCalculationResult)) throw ExpressionException.TooBig()
            }
    }

    private val maxCalculationResult = BigDecimal.valueOf(Double.MAX_VALUE)

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}
