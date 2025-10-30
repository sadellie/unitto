/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.isExpression
import com.sadellie.unitto.core.common.isGreaterThan
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.common.toFormattedString
import com.sadellie.unitto.core.data.calculator.CalculatorHistoryRepository
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
import com.sadellie.unitto.core.model.calculator.CalculatorHistoryItem
import com.sadellie.unitto.core.ui.textfield.addBracket
import com.sadellie.unitto.core.ui.textfield.addTokens
import com.sadellie.unitto.core.ui.textfield.deleteTokens
import com.sadellie.unitto.core.ui.textfield.getTextFieldState
import com.sadellie.unitto.core.ui.textfield.observe
import com.sadellie.unitto.core.ui.textfield.placeCursorAtTheEnd
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadellie.evaluatto.Expression
import io.github.sadellie.evaluatto.ExpressionException
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
internal class CalculatorViewModel
@Inject
constructor(
  private val userPrefsRepository: UserPreferencesRepository,
  private val calculatorHistoryRepository: CalculatorHistoryRepository,
  private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private var _calculationJob: Job? = null
  private val _inputKey = "CALCULATOR_INPUT"
  private val _input = savedStateHandle.getTextFieldState(_inputKey)
  private val _result = MutableStateFlow<CalculationResult>(CalculationResult.Empty)
  private val _equalClicked = MutableStateFlow(false)
  private val _prefs = userPrefsRepository.calculatorPrefs.stateIn(viewModelScope, null)

  val uiState: StateFlow<CalculatorUIState> =
    combine(_result, _prefs, calculatorHistoryRepository.historyFlow) { result, prefs, history ->
        prefs ?: return@combine CalculatorUIState.Loading

        return@combine CalculatorUIState.Ready(
          input = _input,
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
          steppedPartialHistoryView = prefs.steppedPartialHistoryView,
          initialPartialHistoryView = prefs.initialPartialHistoryView,
          openHistoryViewButton = prefs.openHistoryViewButton,
        )
      }
      .stateIn(viewModelScope, CalculatorUIState.Loading)

  suspend fun observeInput() {
    _input.observe().collectLatest {
      // Do not process input if equal was clicked to keep fractional output
      if (_equalClicked.value) {
        _equalClicked.update { false }
        return@collectLatest
      }
      savedStateHandle[_inputKey] = it.toString()
      calculateInput()
    }
  }

  fun addTokens(tokens: String) {
    when {
      // Equal was clicked and user tries to type a digit or dot
      _equalClicked.value && tokens in Token.Digit.allWithDot -> _input.clearText()
      // Equal was clicked and user tries to add operator or something
      _equalClicked.value -> _input.placeCursorAtTheEnd()
    }
    _input.addTokens(tokens)
    _equalClicked.update { false }
  }

  fun addBracket() {
    if (_equalClicked.value) {
      // Cursor is set to 0 when equal is clicked
      _input.placeCursorAtTheEnd()
    }
    _input.addBracket()
    _equalClicked.update { false }
  }

  fun deleteTokens() {
    if (_equalClicked.value) {
      _input.clearText()
    } else {
      _input.deleteTokens()
    }
    _equalClicked.update { false }
  }

  fun clearInput() {
    _input.clearText()
    _equalClicked.update { false }
  }

  fun updateRadianMode(newValue: Boolean) =
    viewModelScope.launch {
      userPrefsRepository.updateRadianMode(newValue)
      _equalClicked.update { false }
      calculateInput()
    }

  fun updateAdditionalButtons(newValue: Boolean) =
    viewModelScope.launch { userPrefsRepository.updateAdditionalButtons(newValue) }

  fun updateInverseMode(newValue: Boolean) =
    viewModelScope.launch { userPrefsRepository.updateInverseMode(newValue) }

  fun updateInitialPartialHistoryView(newValue: Boolean) =
    viewModelScope.launch { userPrefsRepository.updateInitialPartialHistoryView(newValue) }

  fun clearHistory() = viewModelScope.launch { calculatorHistoryRepository.clear() }

  fun deleteHistoryItem(item: CalculatorHistoryItem) =
    viewModelScope.launch { calculatorHistoryRepository.delete(item.id) }

  fun onEqualClick() =
    viewModelScope.launch {
      val prefs = _prefs.value ?: return@launch
      if (_equalClicked.value) return@launch
      val inputValue = _input.text.toString()
      if (!inputValue.isExpression()) return@launch

      val calculated =
        try {
            calculate(inputValue, prefs.radianMode, RoundingMode.HALF_EVEN)
              .toFormattedString(prefs.precision, prefs.outputFormat)
          } catch (e: ExpressionException.DivideByZero) {
            _result.update { CalculationResult.DivideByZeroError }
            return@launch
          } catch (e: Exception) {
            _result.update { CalculationResult.Error }
            return@launch
          }
          // replace minus symbols since it is not recognized by evaluatto
          .replace("-", Token.Operator.MINUS)

      val fractional =
        if (prefs.fractionalOutput) {
          try {
            // Different rounding mode to properly calculate fractional
            calculate(inputValue, prefs.radianMode, RoundingMode.DOWN).toFractionalString()
          } catch (e: Exception) {
            _result.update { CalculationResult.Error }
            return@launch
          }
        } else {
          // User doesn't want fractional output, clear result field
          ""
        }

      calculatorHistoryRepository.add(expression = inputValue, result = calculated)

      _equalClicked.update { true }
      _input.setTextAndPlaceCursorAtEnd(calculated)
      _result.update { CalculationResult.Success(fractional) }
    }

  private fun calculateInput() {
    _calculationJob?.cancel()
    _calculationJob =
      viewModelScope.launch {
        if (!_input.text.toString().isExpression()) {
          _result.update { CalculationResult.Empty }
          return@launch
        }

        val prefs = _prefs.value ?: return@launch
        val newResult =
          try {
            val calculated =
              calculate(
                input = _input.text.toString(),
                radianMode = prefs.radianMode,
                roundingMode = RoundingMode.HALF_EVEN,
              )
            CalculationResult.Success(
              calculated.toFormattedString(prefs.precision, prefs.outputFormat)
            )
          } catch (e: Exception) {
            CalculationResult.Empty
          }
        _result.update { newResult }
      }
  }

  private suspend fun calculate(
    input: String,
    radianMode: Boolean,
    roundingMode: RoundingMode = RoundingMode.HALF_EVEN,
  ): BigDecimal =
    withContext(Dispatchers.Default) {
      Expression(input, radianMode, roundingMode).calculate().also {
        if (it.isGreaterThan(maxCalculationResult)) throw ExpressionException.TooBig()
      }
    }

  private val maxCalculationResult = BigDecimal.valueOf(Double.MAX_VALUE)

  override fun onCleared() {
    viewModelScope.cancel()
    super.onCleared()
  }
}
