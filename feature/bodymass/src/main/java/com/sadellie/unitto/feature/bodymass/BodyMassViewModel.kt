/*
 * Unitto is a calculator for Android
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
import javax.inject.Inject

@HiltViewModel
internal class BodyMassViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    private val isMetric = MutableStateFlow(getInitialIsMetric())
    private val height1 = MutableStateFlow(TextFieldValue())
    private val height2 = MutableStateFlow(TextFieldValue())
    private val weight = MutableStateFlow(TextFieldValue())
    private val result = MutableStateFlow<BigDecimal>(BigDecimal.ZERO)
    private val normalWeightRange = MutableStateFlow<Pair<BigDecimal, BigDecimal>>(BigDecimal.ZERO to BigDecimal.ZERO)

    val uiState = combine(
        userPreferencesRepository.bodyMassPrefs,
        isMetric,
        height1,
        height2,
        weight,
        result,
        normalWeightRange,
    ) { userPrefs, isMetric, height1, height2, weight, result, normalWeightRange ->
        UIState.Ready(
            isMetric = isMetric,
            height1 = height1,
            height2 = height2,
            weight = weight,
            result = result,
            normalWeightRange = normalWeightRange,
            formatterSymbols = userPrefs.formatterSymbols,
        )
    }
        .mapLatest { ui ->
            withContext(Dispatchers.Default) {
                try {
                    val height1 = BigDecimal(ui.height1.text.ifEmpty { "0" })
                    val weight = BigDecimal(ui.weight.text.ifEmpty { "0" })

                    if (ui.isMetric) {
                        result.update { calculateMetric(height1, weight) }
                        normalWeightRange.update { calculateNormalWeightMetric(height1) }
                    } else {
                        val height2 = BigDecimal(ui.height2.text.ifEmpty { "0" })

                        result.update { calculateImperial(height1, height2, weight) }
                        normalWeightRange.update { calculateNormalWeightImperial(height1, height2) }
                    }
                } catch (e: Exception) {
                    result.update { BigDecimal.ZERO }
                    normalWeightRange.update { BigDecimal.ZERO to BigDecimal.ZERO }
                }
            }

            ui
        }
        .stateIn(viewModelScope, UIState.Loading)

    fun updateHeight1(textFieldValue: TextFieldValue) = height1.update { textFieldValue }
    fun updateHeight2(textFieldValue: TextFieldValue) = height2.update { textFieldValue }
    fun updateWeight(textFieldValue: TextFieldValue) = weight.update { textFieldValue }
    fun updateIsMetric(isMetric: Boolean) = this.isMetric.update { isMetric }

    private fun getInitialIsMetric(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) return true
        return LocaleData.getMeasurementSystem(ULocale.getDefault()) != LocaleData.MeasurementSystem.US
    }
}
