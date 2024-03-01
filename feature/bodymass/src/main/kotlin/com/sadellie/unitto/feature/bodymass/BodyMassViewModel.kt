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
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
import com.sadellie.unitto.core.ui.textfield.observe
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.BigDecimal
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class BodyMassViewModel
@Inject
constructor(userPreferencesRepository: UserPreferencesRepository) : ViewModel() {
  private var _calculateJob: Job? = null
  private val _isMetric = MutableStateFlow(getInitialIsMetric())
  private val _height1 = TextFieldState()
  private val _height2 = TextFieldState()
  private val _weight = TextFieldState()
  private val _result = MutableStateFlow(BigDecimal.ZERO)
  private val _normalWeightRange = MutableStateFlow(BigDecimal.ZERO to BigDecimal.ZERO)

  val uiState =
    combine(userPreferencesRepository.bodyMassPrefs, _isMetric, _result, _normalWeightRange) {
        userPrefs,
        isMetric,
        result,
        normalWeightRange ->
        UIState.Ready(
          isMetric = isMetric,
          height1 = _height1,
          height2 = _height2,
          weight = _weight,
          result = result,
          normalWeightRange = normalWeightRange,
          formatterSymbols = userPrefs.formatterSymbols,
        )
      }
      .stateIn(viewModelScope, UIState.Loading)

  suspend fun observeInput() {
    val height1Flow = _height1.observe()
    val height2Flow = _height2.observe()
    val weightFlow = _weight.observe()

    combine(height1Flow, height2Flow, weightFlow, _isMetric) {
        height1FlowValue,
        height2FlowValue,
        weightFlowValue,
        isMetricValue ->
        calculate(
          height1FlowValue.toString(),
          height2FlowValue.toString(),
          weightFlowValue.toString(),
          isMetricValue,
        )
      }
      .collectLatest {}
  }

  fun updateIsMetric(isMetric: Boolean) = this._isMetric.update { isMetric }

  private fun calculate(
    height1Input: String,
    height2Input: String,
    weightInput: String,
    isMetric: Boolean,
  ) {
    _calculateJob?.cancel()
    _calculateJob =
      viewModelScope.launch(Dispatchers.Default) {
        try {
          val height1 = BigDecimal(height1Input.ifEmpty { "0" })
          val weight = BigDecimal(weightInput.ifEmpty { "0" })

          if (isMetric) {
            _result.update { calculateMetric(height1, weight) }
            _normalWeightRange.update { calculateNormalWeightMetric(height1) }
          } else {
            val height2 = BigDecimal(height2Input.ifEmpty { "0" })

            _result.update { calculateImperial(height1, height2, weight) }
            _normalWeightRange.update { calculateNormalWeightImperial(height1, height2) }
          }
        } catch (e: Exception) {
          _result.update { BigDecimal.ZERO }
          _normalWeightRange.update { BigDecimal.ZERO to BigDecimal.ZERO }
        }
      }
  }

  private fun getInitialIsMetric(): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) return true
    return LocaleData.getMeasurementSystem(ULocale.getDefault()) != LocaleData.MeasurementSystem.US
  }
}
