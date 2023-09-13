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

package com.sadellie.unitto.feature.settings.formatting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.base.MAX_PRECISION
import com.sadellie.unitto.core.ui.common.textfield.AllFormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.formatExpression
import com.sadellie.unitto.data.common.setMinimumRequiredScale
import com.sadellie.unitto.data.common.stateIn
import com.sadellie.unitto.data.common.toStringWith
import com.sadellie.unitto.data.common.trimZeros
import com.sadellie.unitto.data.userprefs.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class FormattingViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _prefs = userPreferencesRepository.formattingPrefs
    private val _fractional = MutableStateFlow(false)

    val uiState = combine(_prefs, _fractional) { mainPrefs, fractional ->
        val formatterSymbols = AllFormatterSymbols.getById(mainPrefs.separator)

        return@combine FormattingUIState(
            preview = updatePreview(
                fractional = fractional,
                precision = mainPrefs.digitsPrecision,
                outputFormat = mainPrefs.outputFormat,
                formatterSymbols = formatterSymbols
            ),
            precision = mainPrefs.digitsPrecision,
            separator = mainPrefs.separator,
            outputFormat = mainPrefs.outputFormat,
            formatterSymbols = formatterSymbols
        )
    }
        .stateIn(viewModelScope, FormattingUIState())

    fun togglePreview() = _fractional.update { !it }

    private fun updatePreview(
        fractional: Boolean,
        precision: Int,
        outputFormat: Int,
        formatterSymbols: FormatterSymbols
    ): String {
        val bigD = when {
            fractional -> "0.${"1".padStart(precision, '0')}"
            precision > 0 -> "123456.${"789123456".repeat(ceil(precision.toDouble() / 9.0).toInt())}"
            else -> "123456"
        }

        return BigDecimal(bigD)
            .setMinimumRequiredScale(precision)
            .trimZeros()
            .toStringWith(outputFormat)
            .formatExpression(formatterSymbols)
    }

    /**
     * @see UserPreferencesRepository.updateDigitsPrecision
     */
    fun updatePrecision(precision: Int) = viewModelScope.launch {
        // In UI the slider for precision goes from 0 to 16, where 16 is treated as 1000 (MAX)
        val newPrecision = if (precision > 15) MAX_PRECISION else precision
        userPreferencesRepository.updateDigitsPrecision(newPrecision)
    }

    /**
     * @see UserPreferencesRepository.updateSeparator
     */
    fun updateSeparator(separator: Int) = viewModelScope.launch {
        userPreferencesRepository.updateSeparator(separator)
    }

    /**
     * @see UserPreferencesRepository.updateOutputFormat
     */
    fun updateOutputFormat(outputFormat: Int) = viewModelScope.launch {
        userPreferencesRepository.updateOutputFormat(outputFormat)
    }
}
