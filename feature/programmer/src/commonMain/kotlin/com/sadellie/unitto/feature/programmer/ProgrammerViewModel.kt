/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
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

package com.sadellie.unitto.feature.programmer

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import co.touchlab.kermit.Logger
import com.sadellie.unitto.core.common.DataUnit
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.data.CalculatorHistoryRepository
import com.sadellie.unitto.core.data.programmer.Programmer
import com.sadellie.unitto.core.datastore.CalculatorHistoryPrefsRepository
import com.sadellie.unitto.core.datastore.FormatterPrefsRepository
import com.sadellie.unitto.core.datastore.KeypadPrefsRepository
import com.sadellie.unitto.core.datastore.ProgrammerPrefsRepository
import com.sadellie.unitto.core.model.calculator.CalculatorHistoryModel
import com.sadellie.unitto.core.model.programmer.ShiftType
import com.sadellie.unitto.core.ui.calculators.CalculationResult
import com.sadellie.unitto.core.ui.calculators.CalculatorHistoryListItem
import com.sadellie.unitto.core.ui.textfield.TextFieldStateTokenExtensionsProgrammer
import com.sadellie.unitto.core.ui.textfield.observe
import com.sadellie.unitto.core.ui.textfield.placeCursorAtTheEnd
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import io.github.sadellie.evaluatto.ExpressionException
import io.github.sadellie.evaluatto.programmer.programmerCalculateExpression
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class ProgrammerViewModel(
  private val programmerPrefsRepository: ProgrammerPrefsRepository,
  @Programmer private val programmerHistoryRepository: CalculatorHistoryRepository,
  formatterPrefsRepository: FormatterPrefsRepository,
  keypadPrefsRepository: KeypadPrefsRepository,
  calculatorHistoryPrefsRepository: CalculatorHistoryPrefsRepository,
) : ViewModel() {
  private val textFieldExtension = TextFieldStateTokenExtensionsProgrammer()
  private var _calculationJob: Job? = null
  private val _input = TextFieldState()
  private val _result = MutableStateFlow<CalculationResult>(CalculationResult.Empty)
  private val _lastResult = MutableStateFlow("")
  private val _programmerPrefs = programmerPrefsRepository.prefs.stateIn(viewModelScope, null)
  private val _formatterPrefs = formatterPrefsRepository.prefs.stateIn(viewModelScope, null)
  private val historyFlow =
    programmerHistoryRepository.historyFlow
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

  internal val uiState =
    combine(
        _formatterPrefs,
        keypadPrefsRepository.prefs,
        _programmerPrefs,
        calculatorHistoryPrefsRepository.prefs,
        _result,
      ) { formatterPrefs, keypadPrefs, programmerPrefs, calculatorHistoryPrefs, result ->
        formatterPrefs ?: return@combine ProgrammerScreenUIState.Loading
        programmerPrefs ?: return@combine ProgrammerScreenUIState.Loading

        ProgrammerScreenUIState.Ready(
          input = _input,
          output = result,
          showAcButton = keypadPrefs.acButton,
          formatterSymbols = formatterPrefs.formatterSymbols,
          middleZero = keypadPrefs.middleZero,
          dataUnit = programmerPrefs.dataUnit,
          base = programmerPrefs.base,
          shiftType = programmerPrefs.shiftType,
          partialHistoryView = calculatorHistoryPrefs.partialHistoryView,
          steppedPartialHistoryView = calculatorHistoryPrefs.steppedPartialHistoryView,
          initialPartialHistoryView = programmerPrefs.initialPartialHistoryView,
          openHistoryViewButton = calculatorHistoryPrefs.openHistoryViewButton,
          history = historyFlow,
        )
      }
      .stateIn(viewModelScope, ProgrammerScreenUIState.Loading)

  internal suspend fun observeInput() {
    _input.observe().collectLatest { input ->
      val lastResult = _lastResult.value
      // skip
      if (lastResult == input && lastResult.isNotEmpty()) return@collectLatest
      val prefs = _programmerPrefs.value ?: return@collectLatest
      calculate(prefs.base, prefs.dataUnit)
    }
  }

  internal fun addTokens(token: String) {
    val isEqualClicked = isEqualClicked()
    if (isEqualClicked) {
      when {
        token in Token.digitsWithDotSymbols -> _input.clearText()
        else -> _input.placeCursorAtTheEnd()
      }
      _lastResult.update { "" }
    }
    with(textFieldExtension) { _input.addTokens(token) }
  }

  internal fun addBracket() {
    if (isEqualClicked()) {
      _input.placeCursorAtTheEnd()
      _lastResult.update { "" }
    }
    with(textFieldExtension) { _input.addBracket() }
  }

  internal fun deleteTokens() {
    if (isEqualClicked()) {
      _input.clearText()
      _lastResult.update { "" }
    } else {
      with(textFieldExtension) { _input.deleteTokens() }
    }
  }

  internal fun cleanInput() {
    _input.clearText()
    _result.update { CalculationResult.Empty }
  }

  internal fun toggleSize() {
    val programmerPrefs = _programmerPrefs.value ?: return
    val currentSize = programmerPrefs.dataUnit
    val newSize =
      when (currentSize) {
        DataUnit.QWORD -> DataUnit.WORD
        DataUnit.WORD -> DataUnit.BYTE
        DataUnit.BYTE -> DataUnit.QWORD
      }

    viewModelScope.launch {
      try {
        programmerPrefsRepository.updateDataUnit(newSize)
      } catch (e: Exception) {
        Logger.e(throwable = e, tag = TAG) { "Failed to update data unit" }
      }
    }

    calculate(programmerPrefs.base, newSize)
  }

  internal fun toggleBase() {
    val programmerPrefs = _programmerPrefs.value ?: return
    val newBase =
      when (programmerPrefs.base) {
        2 -> 8
        8 -> 10
        10 -> 16
        else -> 2
      }

    val currentExpression = _input.text.toString()
    if (currentExpression.isNotEmpty()) {
      val convertedExpression =
        convertExpressionBase(
          expression = currentExpression,
          fromRadix = programmerPrefs.base,
          toRadix = newBase,
          dataUnit = programmerPrefs.dataUnit,
        )
      _input.setTextAndPlaceCursorAtEnd(convertedExpression)
      _lastResult.update { "" }
    }

    viewModelScope.launch {
      try {
        programmerPrefsRepository.updateBase(newBase)
      } catch (e: Exception) {
        Logger.e(throwable = e, tag = TAG) { "Failed to update base" }
      }
    }

    calculate(newBase, programmerPrefs.dataUnit)
  }

  internal fun toggleShiftType() {
    val currentShiftType = _programmerPrefs.value?.shiftType ?: return
    viewModelScope.launch {
      val newShiftType =
        when (currentShiftType) {
          ShiftType.SHIFT -> ShiftType.ROTATE
          ShiftType.ROTATE -> ShiftType.SHIFT
        }
      try {
        programmerPrefsRepository.updateShiftType(newShiftType)
      } catch (e: Exception) {
        Logger.e(throwable = e, tag = TAG) { "Failed to update shift type" }
      }
    }
  }

  internal fun updateInitialPartialHistoryView(newValue: Boolean) = viewModelScope.launch {
    programmerPrefsRepository.updateInitialPartialHistoryView(newValue)
  }

  internal fun clearHistory() = viewModelScope.launch { programmerHistoryRepository.clear() }

  internal fun deleteHistoryItem(item: CalculatorHistoryListItem.Item) = viewModelScope.launch {
    programmerHistoryRepository.delete(item.id)
  }

  internal fun updateHistoryItemLabel(item: CalculatorHistoryListItem.Item, label: String) =
    viewModelScope.launch {
      programmerHistoryRepository.updateLabel(item.id, label)
    }

  internal fun onEqual() = viewModelScope.launch {
    val programmerPrefs = _programmerPrefs.value ?: return@launch
    val inputValue = _input.text.toString()
    val result =
      try {
        CalculationResult.Success(
          programmerCalculateExpression(inputValue, programmerPrefs.base, programmerPrefs.dataUnit)
        )
      } catch (_: ExpressionException.DivideByZero) {
        CalculationResult.DivideByZeroError
      } catch (_: Exception) {
        CalculationResult.Error
      }

    if (result is CalculationResult.Success) {
      programmerHistoryRepository.add(
        expression = inputValue,
        result = result.text.replace("-", Token.Minus.symbol),
      )
    }
    _result.update { result }
  }

  private fun calculate(
    base: Int,
    dataUnit: DataUnit,
  ) {
    _calculationJob?.cancel()
    _calculationJob = viewModelScope.launch {
      val currentText = _input.text.toString()
      val newResult =
        try {
          CalculationResult.Success(programmerCalculateExpression(currentText, base, dataUnit))
        } catch (e: Exception) {
          Logger.e(throwable = e, tag = TAG) { "Failed to calculate" }
          CalculationResult.Empty
        }

      Logger.d(tag = TAG) { "Calculate: $newResult" }
      _result.update { newResult }
    }
  }

  private fun isEqualClicked() = _lastResult.value.isNotEmpty()

  companion object {
    private const val TAG = "ProgrammerViewModel"
  }
}
