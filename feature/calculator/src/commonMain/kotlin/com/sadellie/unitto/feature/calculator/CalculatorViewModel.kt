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

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import co.touchlab.kermit.Logger
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.KRoundingMode
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.isExpression
import com.sadellie.unitto.core.common.isGreaterThan
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.common.toFormattedString
import com.sadellie.unitto.core.data.CalculatorHistoryRepository
import com.sadellie.unitto.core.data.calculator.Calculator
import com.sadellie.unitto.core.datastore.CalculatorHistoryPrefsRepository
import com.sadellie.unitto.core.datastore.CalculatorPrefsRepository
import com.sadellie.unitto.core.datastore.FormatterPrefsRepository
import com.sadellie.unitto.core.datastore.KeypadPrefsRepository
import com.sadellie.unitto.core.model.calculator.CalculatorHistoryModel
import com.sadellie.unitto.core.ui.calculators.CalculationResult
import com.sadellie.unitto.core.ui.calculators.CalculatorHistoryListItem
import com.sadellie.unitto.core.ui.textfield.TextFieldStateTokenExtensionsMath.addBracket
import com.sadellie.unitto.core.ui.textfield.TextFieldStateTokenExtensionsMath.addTokens
import com.sadellie.unitto.core.ui.textfield.TextFieldStateTokenExtensionsMath.deleteTokens
import com.sadellie.unitto.core.ui.textfield.observe
import com.sadellie.unitto.core.ui.textfield.placeCursorAtTheEnd
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import io.github.sadellie.evaluatto.ExpressionException
import io.github.sadellie.evaluatto.math.Operation
import io.github.sadellie.evaluatto.math.calculateExpressionAndExtractRepeatableOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class CalculatorViewModel(
  formatterPrefsRepository: FormatterPrefsRepository,
  keypadPrefsRepository: KeypadPrefsRepository,
  private val calculatorPrefsRepository: CalculatorPrefsRepository,
  @Calculator private val calculatorHistoryRepository: CalculatorHistoryRepository,
  calculatorHistoryPrefsRepository: CalculatorHistoryPrefsRepository,
) : ViewModel() {
  private var _calculationJob: Job? = null
  private val _input = TextFieldState()
  private val _result = MutableStateFlow<CalculationResult>(CalculationResult.Empty)
  private val _calculatorPrefs = calculatorPrefsRepository.prefs.stateIn(viewModelScope, null)
  private val _formatterPrefs = formatterPrefsRepository.prefs.stateIn(viewModelScope, null)

  /** Last result that was set after calling [onEqualClick]. Equal was clicked when not empty */
  private val _lastResult = MutableStateFlow("")
  private val _lastRepeatableOperation = MutableStateFlow<Operation?>(null)
  private val historyFlow =
    calculatorHistoryRepository.historyFlow
      .map { pagingData ->
        pagingData.map { item ->
          when (item) {
            is CalculatorHistoryModel.Header -> CalculatorHistoryListItem.Header(item.instant)
            is CalculatorHistoryModel.Item ->
              CalculatorHistoryListItem.Item(
                id = item.id,
                timestamp = item.timestamp,
                expression = item.expression,
                result = item.result,
                isFavorite = item.isFavorite,
                label = item.label,
              )
          }
        }
      }
      .cachedIn(viewModelScope)

  internal val uiState: StateFlow<CalculatorUIState> =
    combine(
        _result,
        _calculatorPrefs,
        _formatterPrefs,
        keypadPrefsRepository.prefs,
        calculatorHistoryPrefsRepository.prefs,
      ) { result, calculatorPrefs, formatterPrefs, displayPrefs, calculatorHistoryPrefs ->
        calculatorPrefs ?: return@combine CalculatorUIState.Loading
        formatterPrefs ?: return@combine CalculatorUIState.Loading

        return@combine CalculatorUIState.Ready(
          input = _input,
          output = result,
          radianMode = calculatorPrefs.radianMode,
          precision = formatterPrefs.digitsPrecision,
          outputFormat = formatterPrefs.outputFormat,
          formatterSymbols = formatterPrefs.formatterSymbols,
          history = historyFlow,
          middleZero = displayPrefs.middleZero,
          acButton = displayPrefs.acButton,
          additionalButtons = calculatorPrefs.additionalButtons,
          inverseMode = calculatorPrefs.inverseMode,
          partialHistoryView = calculatorHistoryPrefs.partialHistoryView,
          steppedPartialHistoryView = calculatorHistoryPrefs.steppedPartialHistoryView,
          initialPartialHistoryView = calculatorPrefs.initialPartialHistoryView,
          openHistoryViewButton = calculatorHistoryPrefs.openHistoryViewButton,
        )
      }
      .stateIn(viewModelScope, CalculatorUIState.Loading)

  internal suspend fun observeInput() {
    _input.observe().collectLatest {
      val lastResult = _lastResult.value
      if (lastResult == it && lastResult.isNotEmpty()) {
        // do not process last result (do not clear fractional in result field)
        return@collectLatest
      }
      calculateInput()
    }
  }

  /** Method called before [_input] changes without buttons - hardware keyboard or clipboard */
  internal fun onHardwareInput() {
    // TODO replace input when on equals was clicked prior
    _lastResult.update { "" }
  }

  internal fun addTokens(tokens: String) {
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

  internal fun addBracket() {
    if (isEqualClicked()) {
      // Cursor is set to 0 when equal is clicked
      _input.placeCursorAtTheEnd()
      _lastResult.update { "" }
    }
    _input.addBracket()
  }

  internal fun deleteTokens() {
    if (isEqualClicked()) {
      _input.clearText()
      _lastResult.update { "" }
    } else {
      _input.deleteTokens()
    }
  }

  internal fun clearInput() {
    _input.clearText()
    _lastResult.update { "" }
  }

  internal fun updateRadianMode(newValue: Boolean) = viewModelScope.launch {
    calculatorPrefsRepository.updateRadianMode(newValue)
    _lastResult.update { "" }
    calculateInput(radian = newValue)
  }

  internal fun updateAdditionalButtons(newValue: Boolean) = viewModelScope.launch {
    calculatorPrefsRepository.updateAdditionalButtons(newValue)
  }

  internal fun updateInverseMode(newValue: Boolean) = viewModelScope.launch {
    calculatorPrefsRepository.updateInverseMode(newValue)
  }

  internal fun updateInitialPartialHistoryView(newValue: Boolean) = viewModelScope.launch {
    calculatorPrefsRepository.updateInitialPartialHistoryView(newValue)
  }

  internal fun clearHistory() = viewModelScope.launch { calculatorHistoryRepository.clear() }

  internal fun deleteHistoryItem(item: CalculatorHistoryListItem.Item) = viewModelScope.launch {
    calculatorHistoryRepository.delete(item.id)
  }

  internal fun updateHistoryItemLabel(item: CalculatorHistoryListItem.Item, label: String) =
    viewModelScope.launch {
      calculatorHistoryRepository.updateLabel(item.id, label)
    }

  internal fun onEqualClick() = viewModelScope.launch {
    val calculatorPrefs = _calculatorPrefs.value ?: return@launch
    val formatterPrefs = _formatterPrefs.value ?: return@launch
    var inputValue = _input.text.toString()
    val lastResult = _lastResult.value
    val lastRepeatableOperation = _lastRepeatableOperation.value
    if (
      calculatorPrefs.constantCalculation &&
        lastResult.isNotEmpty() &&
        lastRepeatableOperation != null
    ) {
      // equal was already clicked, get last operation and apply it
      inputValue = lastRepeatableOperation.generateExpression(inputValue)
    }
    if (!inputValue.isExpression()) return@launch

    val (result, operation) =
      try {
        calculate(
          inputValue,
          calculatorPrefs.radianMode,
          KRoundingMode.HALF_EVEN,
          calculatorPrefs.constantCalculation,
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
        .toFormattedString(formatterPrefs.digitsPrecision, formatterPrefs.outputFormat)
        .replace("-", Token.Minus.symbol) // minus is not recognized by evaluatto
    calculatorHistoryRepository.add(expression = inputValue, result = formattedResult)

    // _input processing will not recalculate and invalidate _result for this value
    _lastResult.update { formattedResult }
    _input.setTextAndPlaceCursorAtEnd(formattedResult)
    val fractional =
      if (calculatorPrefs.fractionalOutput) {
        try {
          // Different rounding mode to properly calculate fractional
          calculate(inputValue, calculatorPrefs.radianMode, KRoundingMode.DOWN)
            .first
            .toFractionalString()
        } catch (e: Exception) {
          Logger.e(e, TAG) { "Failed to find fractional for: $inputValue" }
          ""
        }
      } else {
        // User doesn't want fractional output, clear result field
        ""
      }
    _result.update { CalculationResult.Success(fractional) }
  }

  private fun calculateInput(radian: Boolean? = null) {
    _calculationJob?.cancel()
    _calculationJob = viewModelScope.launch {
      if (!_input.text.toString().isExpression()) {
        _result.update { CalculationResult.Empty }
        return@launch
      }

      val calculatorPrefs = _calculatorPrefs.value ?: return@launch
      val formatterPrefs = _formatterPrefs.value ?: return@launch
      val newResult =
        try {
          val (calculated, _) =
            calculate(
              input = _input.text.toString(),
              radianMode = radian ?: calculatorPrefs.radianMode,
              roundingMode = KRoundingMode.HALF_EVEN,
            )
          CalculationResult.Success(
            calculated.toFormattedString(
              formatterPrefs.digitsPrecision,
              formatterPrefs.outputFormat,
            )
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
      if (result.first.abs().isGreaterThan(maxCalculationResult)) throw ExpressionException.TooBig()
      result
    }

  private fun isEqualClicked() = _lastResult.value.isNotEmpty()

  private val maxCalculationResult = KBigDecimal.valueOf(Double.MAX_VALUE)
}

private const val TAG = "CalculatorViewModel"
