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
import com.sadellie.unitto.core.ui.common.textfield.addTokens
import com.sadellie.unitto.core.ui.common.textfield.deleteTokens
import com.sadellie.unitto.core.ui.common.textfield.getTextField
import com.sadellie.unitto.data.common.format
import com.sadellie.unitto.data.common.stateIn
import com.sadellie.unitto.data.model.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadellie.evaluatto.RPNCalculation
import io.github.sadellie.evaluatto.RPNResult
import io.github.sadellie.evaluatto.perform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
internal class RPNCalculatorViewModel @Inject constructor(
    userPrefsRepository: UserPreferencesRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _inputKey = "RPN_CALCULATOR_INPUT"
    private val _input = MutableStateFlow(savedStateHandle.getTextField(_inputKey))
    private val _stack = MutableStateFlow(emptyList<BigDecimal>())
    private val _prefs = userPrefsRepository.calculatorPrefs.stateIn(viewModelScope, null)

    val uiState = combine(
        _prefs,
        _input,
        _stack
    ) { prefs, input, stack ->
        prefs ?: return@combine RPNCalculatorUIState.Loading

        return@combine RPNCalculatorUIState.Ready(
            input = input,
            stack = stack,
            precision = prefs.precision,
            outputFormat = prefs.outputFormat,
            formatterSymbols = AllFormatterSymbols.getById(prefs.separator),
            allowVibration = prefs.enableVibrations,
            middleZero = prefs.middleZero
        )
    }
        .stateIn(viewModelScope, RPNCalculatorUIState.Loading)

    fun onInputEdit(action: RPNInputEdit) {
        val input = _input.value
        val newInput = when (action) {
            is RPNInputEdit.Digit -> input.addTokens(action.value)
            RPNInputEdit.Dot -> {
                if (_input.value.text.contains(Token.Digit.dot)) return
                input.addTokens(Token.Digit.dot)
            }
            RPNInputEdit.Delete -> input.deleteTokens()
        }

        _input.update { newInput }
        savedStateHandle[_inputKey] = newInput.text
    }

    fun onCalculationClick(action: RPNCalculation) = viewModelScope.launch {
        val prefs = _prefs.value ?: return@launch

        val newResult = withContext(Dispatchers.Default) {
            action.perform(_input.value.text, _stack.value)
        }

        when (newResult) {
            is RPNResult.Result -> {
                val newInput = newResult.input?.format(prefs.precision, prefs.outputFormat) ?: ""
                _input.update { TextFieldValue(newInput, TextRange(newInput.length)) }
                _stack.update { newResult.stack }
            }

            is RPNResult.NewStack -> {
                _stack.update { newResult.stack }
            }

            is RPNResult.NewInput -> {
                val newInput = newResult.input.format(prefs.precision, prefs.outputFormat)
                _input.update { TextFieldValue(newInput, TextRange(newInput.length)) }
            }
            RPNResult.BadInput, RPNResult.DivideByZero -> Unit
        }
    }

    fun onCursorChange(selection: TextRange) = _input.update { it.copy(selection = selection) }
}
