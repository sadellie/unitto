/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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
import co.touchlab.kermit.Logger
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.KRoundingMode
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.isExpression
import com.sadellie.unitto.core.common.isGreaterThan
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.common.toFormattedString
import com.sadellie.unitto.core.data.calculator.CalculatorHistoryRepository
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
import com.sadellie.unitto.core.model.calculator.CalculatorHistoryItem
import com.sadellie.unitto.core.ui.textfield.TextFieldStateTokenExtensionsMath.addBracket
import com.sadellie.unitto.core.ui.textfield.TextFieldStateTokenExtensionsMath.addTokens
import com.sadellie.unitto.core.ui.textfield.TextFieldStateTokenExtensionsMath.deleteTokens
import com.sadellie.unitto.core.ui.textfield.getTextFieldState
import com.sadellie.unitto.core.ui.textfield.observe
import com.sadellie.unitto.core.ui.textfield.placeCursorAtTheEnd
import io.github.sadellie.evaluatto.ExpressionException
import io.github.sadellie.evaluatto.math.Operation
import io.github.sadellie.evaluatto.math.calculateExpressionAndExtractRepeatableOperation
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

internal class CalculatorViewModel(
  private val userPrefsRepository: UserPreferencesRepository,
  private val calculatorHistoryRepository: CalculatorHistoryRepository,
  private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private var _calculationJob: Job? = null
  private val _inputKey = "CALCULATOR_INPUT"
  private val _input = savedStateHandle.getTextFieldState(_inputKey)
  private val _result = MutableStateFlow<CalculationResult>(CalculationResult.Empty)
  private val _prefs = userPrefsRepository.calculatorPrefs.stateIn(viewModelScope, null)

  /** Last result that was set after calling [onEqualClick]. Equal was clicked when not empty */
  private val _lastResult = MutableStateFlow("")
  private val _lastRepeatableOperation = MutableStateFlow<Operation?>(null)

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
      val lastResult = _lastResult.value
      if (lastResult == it && lastResult.isNotEmpty()) {
        // do not process last result (do not clear fractional in result field)
        return@collectLatest
      }
      savedStateHandle[_inputKey] = it.toString()
      calculateInput()
    }
  }

  /** Method called before [_input] changes without buttons - hardware keyboard or clipboard */
  fun onHardwareInput() {
    // TODO replace input when on equals was clicked prior
    _lastResult.update { "" }
  }

  fun addTokens(tokens: String) {
    val isEqualClicked = isEqualClicked()
    if (isEqualClicked) {
      when {
        // Equal was clicked and user tries to type a digit or dot
        tokens in Token.digitsWithDotSymbols -> _input.clearText()
        // Equal was clicked and user tries to add operator or something
        else -> _input.placeCursorAtTheEnd()
      }
      _lastResult.update { "" }
    }
    _input.addTokens(tokens)
  }

  fun addBracket() {
    if (isEqualClicked()) {
      // Cursor is set to 0 when equal is clicked
      _input.placeCursorAtTheEnd()
      _lastResult.update { "" }
    }
    _input.addBracket()
  }

  fun deleteTokens() {
    if (isEqualClicked()) {
      _input.clearText()
      _lastResult.update { "" }
    } else {
      _input.deleteTokens()
    }
  }

  fun clearInput() {
    _input.clearText()
    _lastResult.update { "" }
  }

  fun updateRadianMode(newValue: Boolean) =
    viewModelScope.launch {
      userPrefsRepository.updateRadianMode(newValue)
      _lastResult.update { "" }
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
      var inputValue = _input.text.toString()
      val lastResult = _lastResult.value
      val lastRepeatableOperation = _lastRepeatableOperation.value
      if (prefs.constantCalculation && lastResult.isNotEmpty() && lastRepeatableOperation != null) {
        // equal was already clicked, get last operation and apply it
        inputValue = lastRepeatableOperation.generateExpression(inputValue)
      }
      if (!inputValue.isExpression()) return@launch

      val (result, operation) =
        try {
          calculate(
            inputValue,
            prefs.radianMode,
            KRoundingMode.HALF_EVEN,
            prefs.constantCalculation,
          )
        } catch (_: ExpressionException.DivideByZero) {
          _result.update { CalculationResult.DivideByZeroError }
          return@launch
        } catch (_: Exception) {
          _result.update { CalculationResult.Error }
          return@launch
        }
      _lastRepeatableOperation.update { operation }

      val formattedResult =
        result
          .toFormattedString(prefs.precision, prefs.outputFormat)
          .replace("-", Token.Minus.symbol) // minus is not recognized by evaluatto
      calculatorHistoryRepository.add(expression = inputValue, result = formattedResult)

      // _input processing will not recalculate and invalidate _result for this value
      _lastResult.update { formattedResult }
      _input.setTextAndPlaceCursorAtEnd(formattedResult)
      val fractional =
        if (prefs.fractionalOutput) {
          try {
            // Different rounding mode to properly calculate fractional
            calculate(inputValue, prefs.radianMode, KRoundingMode.DOWN).first.toFractionalString()
          } catch (e: Exception) {
            _result.update { CalculationResult.Error }
            Logger.e(e, TAG) { "Failed to find fractional for: $inputValue" }
            ""
          }
        } else {
          // User doesn't want fractional output, clear result field
          ""
        }
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
            val (calculated, _) =
              calculate(_input.text.toString(), prefs.radianMode, KRoundingMode.HALF_EVEN)
            CalculationResult.Success(
              calculated.toFormattedString(prefs.precision, prefs.outputFormat)
            )
          } catch (_: Exception) {
            CalculationResult.Empty
          }
        _result.update { newResult }
      }
  }

  private suspend fun calculate(
    input: String,
    radianMode: Boolean,
    roundingMode: KRoundingMode = KRoundingMode.HALF_EVEN,
    extractRepeating: Boolean = false,
  ): Pair<KBigDecimal, Operation?> =
    withContext(Dispatchers.Default) {
      val result =
        calculateExpressionAndExtractRepeatableOperation(
          input = input,
          radianMode = radianMode,
          roundingMode = roundingMode,
          extractRepeatable = extractRepeating,
        )
      if (result.first.isGreaterThan(maxCalculationResult)) throw ExpressionException.TooBig()
      result
    }

  private fun isEqualClicked() = _lastResult.value.isNotEmpty()

  private val maxCalculationResult = KBigDecimal.valueOf(Double.MAX_VALUE)

  override fun onCleared() {
    viewModelScope.cancel()
    super.onCleared()
  }
}

private const val TAG = "CalculatorViewModel"
