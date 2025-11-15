/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2025 Elshan Agaev
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

package com.sadellie.unitto.feature.converter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import co.touchlab.kermit.Logger
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.data.converter.ConverterResult
import com.sadellie.unitto.core.data.converter.UnitConverterRepository
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import com.sadellie.unitto.core.navigation.ConverterStartRoute
import com.sadellie.unitto.core.ui.textfield.getTextFieldState
import com.sadellie.unitto.core.ui.textfield.observe
import io.github.sadellie.evaluatto.ExpressionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ConverterViewModel(
  private val userPrefsRepository: UserPreferencesRepository,
  private val unitsRepo: UnitConverterRepository,
  private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private var _conversionJob: Job? = null
  private val _converterInputKey1 = "CONVERTER_INPUT_1"
  private val _converterInputKey2 = "CONVERTER_INPUT_2"
  private val _input1 = savedStateHandle.getTextFieldState(_converterInputKey1)
  private val _input2 = savedStateHandle.getTextFieldState(_converterInputKey2)
  private val _output = MutableStateFlow<ConverterResult>(ConverterResult.Loading)
  private val _currencyRateUpdateState = unitsRepo.currencyRateUpdateState
  private val _unitFromId = MutableStateFlow<String?>(null)
  private val _unitToId = MutableStateFlow<String?>(null)
  private val _unitFrom =
    _unitFromId
      .mapLatest { it?.let { unitsRepo.getById(it) } }
      .flowOn(Dispatchers.Default)
      .stateIn(viewModelScope, null)

  private val _unitTo =
    _unitToId
      .mapLatest { it?.let { unitsRepo.getById(it) } }
      .flowOn(Dispatchers.Default)
      .stateIn(viewModelScope, null)

  val uiState: StateFlow<ConverterUIState> =
    combine(
        _output,
        _unitFrom,
        _unitTo,
        _currencyRateUpdateState,
        userPrefsRepository.converterPrefs,
      ) { outputValue, unitFromValue, unitToValue, currencyRateUpdateState, prefs ->
        if (unitFromValue == null) return@combine ConverterUIState.Loading
        if (unitToValue == null) return@combine ConverterUIState.Loading

        whenBothAre<BasicUnit.Default>(unitFromValue, unitToValue) { unitFrom, unitTo ->
          return@combine ConverterUIState.Default(
            input1 = _input1,
            input2 = _input2,
            result = outputValue,
            unitFrom = unitFrom,
            unitTo = unitTo,
            middleZero = prefs.middleZero,
            formatterSymbols = prefs.formatterSymbols,
            scale = prefs.precision,
            outputFormat = OutputFormat.PLAIN,
            formatTime = prefs.unitConverterFormatTime,
            currencyRateUpdateState = currencyRateUpdateState,
            acButton = prefs.acButton,
          )
        }

        whenBothAre<BasicUnit.NumberBase>(unitFromValue, unitToValue) { unitFrom, unitTo ->
          return@combine ConverterUIState.NumberBase(
            input = _input1,
            result = outputValue,
            unitFrom = unitFrom,
            unitTo = unitTo,
          )
        }

        return@combine ConverterUIState.Loading
      }
      .stateIn(viewModelScope, ConverterUIState.Loading)

  init {
    loadInitialUnits()
  }

  suspend fun observeInput() {
    val input1Flow =
      _input1.observe().onEach { savedStateHandle[_converterInputKey1] = _input1.text }
    val input2Flow =
      _input2.observe().onEach { savedStateHandle[_converterInputKey2] = _input2.text }

    combine(input1Flow, input2Flow, _unitFromId, _unitToId, userPrefsRepository.converterPrefs) {
        input1Value,
        input2Value,
        unitFromIdValue,
        unitToIdValue,
        converterPrefsValue ->
        convert(
          input1Value.toString(),
          input2Value.toString(),
          unitFromIdValue,
          unitToIdValue,
          converterPrefsValue.unitConverterFormatTime,
        )
      }
      .collectLatest {}
  }

  fun retryConvert() {
    viewModelScope.launch {
      val prefs = userPrefsRepository.converterPrefs.first()
      convert(
        _input1.text.toString(),
        _input2.text.toString(),
        _unitFromId.value,
        _unitToId.value,
        prefs.unitConverterFormatTime,
      )
    }
  }

  fun updateUnitFromId(id: String) =
    viewModelScope.launch {
      val pairId = unitsRepo.getPairId(id)

      _unitFromId.update { id }
      _unitToId.update { pairId }

      unitsRepo.incrementCounter(id)
      updateLatestPairOfUnits()
    }

  fun updateUnitToId(id: String) =
    viewModelScope.launch {
      _unitToId.update { id }
      unitsRepo.incrementCounter(id)
      setPair()
      updateLatestPairOfUnits()
    }

  fun swapUnits(newUnitFromId: String, newInputToId: String) =
    viewModelScope.launch {
      _unitFromId.update { newUnitFromId }
      _unitToId.update { newInputToId }
      setPair()
      updateLatestPairOfUnits()
    }

  fun convert(
    input1Value: String,
    input2Value: String,
    unitFromIdValue: String?,
    unitToIdValue: String?,
    formatTime: Boolean,
  ) {
    _conversionJob?.cancel()
    _conversionJob =
      viewModelScope.launch {
        val result =
          try {
            unitsRepo.convert(
              unitFromId = unitFromIdValue ?: return@launch,
              unitToId = unitToIdValue ?: return@launch,
              value1 = input1Value,
              value2 = input2Value,
              formatTime = formatTime,
            )
          } catch (e: ExpressionException) {
            Logger.w(TAG, e) { "Failed to convert" }
            return@launch
          } catch (e: NumberFormatException) {
            Logger.w(TAG, e) { "Failed to convert" }
            return@launch
          } catch (e: Exception) {
            Logger.w(TAG, e) { "Failed to convert" }
            return@launch
          }
        _output.update { result }
      }
  }

  private fun loadInitialUnits() =
    viewModelScope.launch {
      val args = savedStateHandle.toRoute<ConverterStartRoute>()
      if (args.unitFromId != null && args.unitToId != null) {
        _unitFromId.update { args.unitFromId }
        _unitToId.update { args.unitToId }
      } else {
        val prefs = userPrefsRepository.converterPrefs.first()
        _unitFromId.update { prefs.latestLeftSideUnit }
        _unitToId.update { prefs.latestRightSideUnit }
      }
    }

  private fun setPair() =
    viewModelScope.launch {
      unitsRepo.setPair(
        id = _unitFromId.value ?: return@launch,
        pairId = _unitToId.value ?: return@launch,
      )
    }

  private fun updateLatestPairOfUnits() =
    viewModelScope.launch {
      userPrefsRepository.updateLatestPairOfUnits(
        unitFrom = _unitFromId.value ?: return@launch,
        unitTo = _unitToId.value ?: return@launch,
      )
    }

  /**
   * Will call [block] if both [value1] and [value2] are [T].
   *
   * @param T What to check against.
   * @param value1 Value to check.
   * @param value2 Value to check.
   * @param block Block that will be called if the has passed.
   */
  private inline fun <reified T> whenBothAre(
    value1: Any?,
    value2: Any?,
    block: (v1: T, v2: T) -> Unit,
  ) {
    if ((value1 is T) and (value2 is T)) block(value1 as T, value2 as T)
  }

  override fun onCleared() {
    super.onCleared()
    viewModelScope.cancel()
  }
}

private const val TAG = "ConverterViewModel"
