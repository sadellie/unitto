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

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.textfield.AllFormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.addBracket
import com.sadellie.unitto.core.ui.common.textfield.addTokens
import com.sadellie.unitto.core.ui.common.textfield.deleteTokens
import com.sadellie.unitto.data.calculator.CalculatorHistoryRepository
import com.sadellie.unitto.data.common.isExpression
import com.sadellie.unitto.data.common.setMinimumRequiredScale
import com.sadellie.unitto.data.common.stateIn
import com.sadellie.unitto.data.common.toStringWith
import com.sadellie.unitto.data.common.trimZeros
import com.sadellie.unitto.data.userprefs.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadellie.evaluatto.Expression
import io.github.sadellie.evaluatto.ExpressionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
internal class CalculatorViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val calculatorHistoryRepository: CalculatorHistoryRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _inputKey = "CALCULATOR_INPUT"
    private val _input = MutableStateFlow(
        with(savedStateHandle[_inputKey] ?: "") {
            TextFieldValue(this, TextRange(this.length))
        }
    )
    private val _result = MutableStateFlow<CalculationResult>(CalculationResult.Default())
    private val _equalClicked = MutableStateFlow(false)

    val uiState: StateFlow<CalculatorUIState> = combine(
        _input,
        _result,
        userPrefsRepository.calculatorPrefs,
        calculatorHistoryRepository.historyFlow,
        _equalClicked,
    ) { input, result, prefs, history, showError ->
        return@combine CalculatorUIState.Ready(
            input = input,
            output = if (!showError and (result !is CalculationResult.Default)) CalculationResult.Default() else result,
            radianMode = prefs.radianMode,
            precision = prefs.precision,
            outputFormat = prefs.outputFormat,
            formatterSymbols = AllFormatterSymbols.getById(prefs.separator),
            history = history,
            allowVibration = prefs.enableVibrations,
            middleZero = prefs.middleZero,
            acButton = prefs.acButton,
            partialHistoryView = prefs.partialHistoryView,
        )
    }
        .mapLatest { ui ->
            calculate(
                input = ui.input.text,
                radianMode = ui.radianMode,
                outputFormat = ui.outputFormat,
                precision = ui.precision
            )

            ui
        }
        .stateIn(viewModelScope, CalculatorUIState.Loading)

    fun addTokens(tokens: String) = _input.update {
        val newValue = if (_equalClicked.value) {
            _equalClicked.update { false }
            TextFieldValue().addTokens(tokens)
        } else {
            it.addTokens(tokens)
        }
        savedStateHandle[_inputKey] = newValue.text
        newValue
    }

    fun addBracket() = _input.update {
        val newValue = if (_equalClicked.value) {
            _equalClicked.update { false }
            TextFieldValue().addBracket()
        } else {
            it.addBracket()
        }
        savedStateHandle[_inputKey] = newValue.text
        newValue
    }

    fun deleteTokens() = _input.update {
        val newValue = if (_equalClicked.value) {
            _equalClicked.update { false }
            TextFieldValue().deleteTokens()
        } else {
            it.deleteTokens()
        }
        savedStateHandle[_inputKey] = newValue.text
        newValue
    }

    fun clearInput() = _input.update {
        savedStateHandle[_inputKey] = ""
        TextFieldValue()
    }
    fun onCursorChange(selection: TextRange) = _input.update { it.copy(selection = selection) }

    fun updateRadianMode(newValue: Boolean) = viewModelScope.launch {
        userPrefsRepository.updateRadianMode(newValue)
    }

    fun clearHistory() = viewModelScope.launch(Dispatchers.IO) {
        calculatorHistoryRepository.clear()
    }

    fun evaluate() = viewModelScope.launch(Dispatchers.IO) {
        when (val result = _result.value) {
            is CalculationResult.Default -> {
                calculatorHistoryRepository.add(
                    expression = _input.value.text.replace("-", Token.Operator.minus),
                    result = result.text
                )
                _input.update { TextFieldValue(result.text, TextRange(result.text.length)) }
                _result.update { CalculationResult.Default() }
                _equalClicked.update { true }
            }

            is CalculationResult.DivideByZeroError -> {
                _equalClicked.update { true }
            }

            is CalculationResult.Error -> {
                // skip for generic error (bad expression and stuff
            }
        }
    }

    private fun calculate(
        input: String,
        radianMode: Boolean,
        outputFormat: Int,
        precision: Int,
    ) = viewModelScope.launch(Dispatchers.Default) {
        if (!input.isExpression()) {
            _result.update { CalculationResult.Default() }
            return@launch
        }

        _result.update {
            try {
                CalculationResult.Default(
                    Expression(input, radianMode = radianMode)
                        .calculate()
                        .also {
                            if (it > BigDecimal.valueOf(Double.MAX_VALUE)) throw ExpressionException.TooBig()
                        }
                        .setMinimumRequiredScale(precision)
                        .trimZeros()
                        .toStringWith(outputFormat)
                )
            } catch (e: ExpressionException.DivideByZero) {
                CalculationResult.DivideByZeroError
            } catch (e: Exception) {
                CalculationResult.Error
            }
        }
    }
}
