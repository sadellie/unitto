/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.feature.bodymass

import android.icu.util.LocaleData
import android.icu.util.ULocale
import android.os.Build
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.base.MAX_PRECISION
import com.sadellie.unitto.core.ui.common.textfield.AllFormatterSymbols
import com.sadellie.unitto.data.common.combine
import com.sadellie.unitto.data.common.stateIn
import com.sadellie.unitto.data.model.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
internal class BodyMassViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
    private val _isMetric = MutableStateFlow(getInitialIsMetric())
    private val _height1 = MutableStateFlow(TextFieldValue())
    private val _height2 = MutableStateFlow(TextFieldValue())
    private val _weight = MutableStateFlow(TextFieldValue())
    private val _result = MutableStateFlow<BigDecimal>(BigDecimal.ZERO)
    private val cmToMFactor = BigDecimal("10000")
    private val footToInchFactor = BigDecimal("12")
    private val metricToImperialFactor = BigDecimal("703")

    val uiState = combine(
        userPreferencesRepository.bodyMassPrefs,
        _isMetric,
        _height1,
        _height2,
        _weight,
        _result
    ) { userPrefs, isMetric, height1, height2, weight, result ->
        UIState.Ready(
            isMetric = isMetric,
            height1 = height1,
            height2 = height2,
            weight = weight,
            result = result,
            allowVibration = userPrefs.enableVibrations,
            formatterSymbols = AllFormatterSymbols.getById(userPrefs.separator)
        )
    }
        .mapLatest { ui ->
            val newResult: BigDecimal = try {
                val height1 = BigDecimal(ui.height1.text.ifEmpty { "0" })
                val weight = BigDecimal(ui.weight.text.ifEmpty { "0" })

                if (ui.isMetric) {
                    calculateMetric(height1, weight)
                } else {
                    val height2 = BigDecimal(ui.height2.text.ifEmpty { "0" })
                    calculateImperial(height1, height2, weight)
                }
            } catch (e: Exception) {
                BigDecimal.ZERO
            }

            _result.update { newResult }

            ui
        }
        .stateIn(viewModelScope, UIState.Loading)

    fun updateHeight1(textFieldValue: TextFieldValue) = _height1.update { textFieldValue }
    fun updateHeight2(textFieldValue: TextFieldValue) = _height2.update { textFieldValue }
    fun updateWeight(textFieldValue: TextFieldValue) = _weight.update { textFieldValue }
    fun updateIsMetric(isMetric: Boolean) = _isMetric.update { isMetric }

    private suspend  fun calculateMetric(
        height: BigDecimal,
        weight: BigDecimal,
    ) = withContext(Dispatchers.Default) {
        return@withContext weight
            .divide(height.pow(2), MAX_PRECISION, RoundingMode.HALF_EVEN)
            .multiply(cmToMFactor)
    }

    private suspend fun calculateImperial(
        height1: BigDecimal,
        height2: BigDecimal,
        weight: BigDecimal
    ) = withContext(Dispatchers.Default) {
        val height = height1
            .multiply(footToInchFactor)
            .plus(height2)

        return@withContext weight
            .divide(height.pow(2), MAX_PRECISION, RoundingMode.HALF_EVEN)
            .multiply(metricToImperialFactor) // Approximate lbs/inch^2 to kg/m^2
    }

    private fun getInitialIsMetric(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) return true
        return LocaleData.getMeasurementSystem(ULocale.getDefault()) != LocaleData.MeasurementSystem.US
    }
}
