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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.Separator
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.textfield.AllFormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.addTokens
import com.sadellie.unitto.core.ui.common.textfield.deleteTokens
import com.sadellie.unitto.data.calculator.CalculatorHistoryRepository
import com.sadellie.unitto.data.common.isExpression
import com.sadellie.unitto.data.common.setMinimumRequiredScale
import com.sadellie.unitto.data.common.toStringWith
import com.sadellie.unitto.data.common.trimZeros
import com.sadellie.unitto.data.userprefs.CalculatorPreferences
import com.sadellie.unitto.data.userprefs.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadellie.evaluatto.Expression
import io.github.sadellie.evaluatto.ExpressionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
internal class CalculatorViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val calculatorHistoryRepository: CalculatorHistoryRepository,
) : ViewModel() {
    private val _prefs: StateFlow<CalculatorPreferences> =
        userPrefsRepository.calculatorPrefs.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CalculatorPreferences(
                radianMode = false,
                enableVibrations = false,
                separator = Separator.SPACE,
                middleZero = false,
                partialHistoryView = true,
                precision = 3,
                outputFormat = OutputFormat.PLAIN
            )
        )

    private val _input: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    private val _output: MutableStateFlow<CalculationResult> =
        MutableStateFlow(CalculationResult.Default())
    private val _history = calculatorHistoryRepository.historyFlow

    val uiState = combine(
        _input, _output, _history, _prefs
    ) { input, output, history, userPrefs ->
        return@combine CalculatorUIState.Ready(
            input = input,
            output = output,
            radianMode = userPrefs.radianMode,
            history = history,
            allowVibration = userPrefs.enableVibrations,
            formatterSymbols = AllFormatterSymbols.getById(userPrefs.separator),
            middleZero = userPrefs.middleZero,
            partialHistoryView = userPrefs.partialHistoryView,
        )
    }
        .stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), CalculatorUIState.Loading
    )

    fun addTokens(tokens: String) = _input.update { it.addTokens(tokens) }
    fun deleteTokens() = _input.update { it.deleteTokens() }
    fun clearInput() = _input.update { TextFieldValue() }
    fun onCursorChange(selection: TextRange) = _input.update { it.copy(selection = selection) }

    // Called when user clicks "=" on a keyboard
    fun evaluate() = viewModelScope.launch(Dispatchers.IO) {
        when (val calculationResult = calculateInput()) {
            is CalculationResult.Default -> {
                if (calculationResult.text.isEmpty()) return@launch

                // We can get negative number and they use ugly minus symbol
                val calculationText = calculationResult.text.replace("-", Token.Operator.minus)

                calculatorHistoryRepository.add(
                    expression = _input.value.text,
                    result = calculationText
                )
                _input.update {
                    TextFieldValue(calculationText, TextRange(calculationText.length))
                }
                _output.update { CalculationResult.Default() }
            }
            // Show the error
            else -> _output.update { calculationResult }
        }
    }

    fun toggleCalculatorMode() = viewModelScope.launch {
        userPrefsRepository.updateRadianMode(!_prefs.value.radianMode)
    }

    fun clearHistory() = viewModelScope.launch(Dispatchers.IO) {
        calculatorHistoryRepository.clear()
    }

    private fun calculateInput(): CalculationResult {
        val currentInput = _input.value.text
        // Input is empty or not an expression, don't calculate
        if (!currentInput.isExpression()) return CalculationResult.Default()

        return try {
            CalculationResult.Default(
                Expression(currentInput, radianMode = _prefs.value.radianMode)
                    .calculate()
                    .also {
                        if (it > BigDecimal.valueOf(Double.MAX_VALUE)) throw ExpressionException.TooBig()
                    }
                    .setMinimumRequiredScale(_prefs.value.precision)
                    .trimZeros()
                    .toStringWith(_prefs.value.outputFormat)
            )
        } catch (e: ExpressionException.DivideByZero) {
            CalculationResult.DivideByZeroError
        } catch (e: Exception) {
            CalculationResult.Error
        }
    }

    init {
        // Observe and invoke calculation without UI lag.
        viewModelScope.launch(Dispatchers.Default) {
            merge(_prefs, _input).collectLatest {
                val calculated = calculateInput()
                _output.update {
                    // Don't show error when simply entering stuff
                    if (calculated !is CalculationResult.Default) CalculationResult.Default() else calculated
                }
            }
        }
    }
}
