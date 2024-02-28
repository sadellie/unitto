/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.ui.common.textfield.getTextField
import com.sadellie.unitto.data.common.combine
import com.sadellie.unitto.data.common.stateIn
import com.sadellie.unitto.data.converter.ConverterResult
import com.sadellie.unitto.data.converter.UnitsRepositoryImpl
import com.sadellie.unitto.data.model.converter.unit.BasicUnit
import com.sadellie.unitto.data.model.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ConverterViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val unitsRepo: UnitsRepositoryImpl,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private var conversionJob: Job? = null

    private val converterInputKey1 = "CONVERTER_INPUT_1"
    private val converterInputKey2 = "CONVERTER_INPUT_2"
    private val input1 = MutableStateFlow(savedStateHandle.getTextField(converterInputKey1))
    private val input2 = MutableStateFlow(savedStateHandle.getTextField(converterInputKey2))
    private val output = MutableStateFlow<ConverterResult>(ConverterResult.Loading)

    private val unitFromId = MutableStateFlow<String?>(null)
    private val unitToId = MutableStateFlow<String?>(null)

    private val unitFrom = unitFromId
        .mapLatest { it?.let { unitsRepo.getById(it) } }
        .flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, null)

    private val unitTo = unitToId
        .mapLatest { it?.let { unitsRepo.getById(it) } }
        .flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, null)

    val converterUIState: StateFlow<UnitConverterUIState> = combine(
        input1,
        input2,
        output,
        unitFrom,
        unitTo,
        userPrefsRepository.converterPrefs,
    ) { input1Value, input2Value, outputValue, unitFromValue, unitToValue, prefs ->
        if (unitFromValue == null) return@combine UnitConverterUIState.Loading
        if (unitToValue == null) return@combine UnitConverterUIState.Loading

        whenBothAre<BasicUnit.Default>(
            value1 = unitFromValue,
            value2 = unitToValue,
        ) { unitFrom, unitTo ->
            return@combine UnitConverterUIState.Default(
                input1 = input1Value,
                input2 = input2Value,
                result = outputValue,
                unitFrom = unitFrom,
                unitTo = unitTo,
                middleZero = prefs.middleZero,
                formatterSymbols = prefs.formatterSymbols,
                scale = prefs.precision,
                outputFormat = OutputFormat.PLAIN,
                formatTime = prefs.unitConverterFormatTime,
                currencyRateUpdateState = CurrencyRateUpdateState.Nothing,
                acButton = prefs.acButton,
            )
        }

        whenBothAre<BasicUnit.NumberBase>(
            value1 = unitFromValue,
            value2 = unitToValue,
        ) { unitFrom, unitTo ->
            return@combine UnitConverterUIState.NumberBase(
                input = input1Value,
                result = outputValue,
                unitFrom = unitFrom,
                unitTo = unitTo,
            )
        }

        return@combine UnitConverterUIState.Loading
    }
        .stateIn(viewModelScope, UnitConverterUIState.Loading)

    fun updateInput1(value: TextFieldValue) {
        input1.update { value }
        savedStateHandle[converterInputKey1] = value.text
    }

    fun updateInput2(value: TextFieldValue) {
        input2.update { value }
        savedStateHandle[converterInputKey2] = value.text
    }

    fun updateUnitFromId(id: String) = viewModelScope.launch {
        val pairId = unitsRepo.getPairId(id)

        unitFromId.update { id }
        unitToId.update { pairId }

        unitsRepo.incrementCounter(id)
        updateLatestPairOfUnits()
    }

    fun updateUnitToId(id: String) = viewModelScope.launch {
        unitToId.update { id }
        unitsRepo.incrementCounter(id)
        setPair()
        updateLatestPairOfUnits()
    }

    fun swapUnits(
        newUnitFromId: String,
        newInputToId: String,
    ) = viewModelScope.launch {
        unitFromId.update { newUnitFromId }
        unitToId.update { newInputToId }
        setPair()
        updateLatestPairOfUnits()
    }

    fun convertDefault() {
        conversionJob?.cancel()
        conversionJob = viewModelScope.launch {
            val result = unitsRepo.convertDefault(
                unitFromId = unitFromId.value ?: return@launch,
                unitToId = unitToId.value ?: return@launch,
                value1 = input1.value.text,
                value2 = input2.value.text,
                formatTime = userPrefsRepository.converterPrefs.first().unitConverterFormatTime,
            )

            when (result) {
                is ConverterResult.Error.BadInput -> Unit
                else -> output.update { result }
            }
        }
    }

    fun convertNumberBase() {
        conversionJob?.cancel()
        conversionJob = viewModelScope.launch {
            unitsRepo.convertNumberBase(
                unitFromId = unitFromId.value ?: return@launch,
                unitToId = unitToId.value ?: return@launch,
                value = input1.value.text,
            )
        }
    }

    private fun loadInitialUnits() = viewModelScope.launch {
        val prefs = userPrefsRepository.converterPrefs.first()
        unitFromId.update { prefs.latestLeftSideUnit }
        unitToId.update { prefs.latestRightSideUnit }
    }

    private fun setPair() = viewModelScope.launch {
        unitsRepo.setPair(
            id = unitFromId.value ?: return@launch,
            pairId = unitToId.value ?: return@launch,
        )
    }

    private fun updateLatestPairOfUnits() = viewModelScope.launch {
        userPrefsRepository
            .updateLatestPairOfUnits(
                unitFrom = unitFromId.value ?: return@launch,
                unitTo = unitToId.value ?: return@launch,
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

    init {
        loadInitialUnits()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
