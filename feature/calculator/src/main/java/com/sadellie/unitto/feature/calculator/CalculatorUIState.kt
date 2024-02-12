/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

import androidx.annotation.StringRes
import androidx.compose.ui.text.input.TextFieldValue
import com.sadellie.unitto.core.base.FormatterSymbols
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.data.model.HistoryItem

internal sealed class CalculatorUIState {
    data object Loading : CalculatorUIState()

    data class Ready(
        val input: TextFieldValue,
        val output: CalculationResult,
        val radianMode: Boolean,
        val precision: Int,
        val outputFormat: Int,
        val formatterSymbols: FormatterSymbols,
        val history: List<HistoryItem>,
        val middleZero: Boolean,
        val acButton: Boolean,
        val partialHistoryView: Boolean,
    ) : CalculatorUIState()
}

sealed class CalculationResult {
    data class Default(val text: String) : CalculationResult()

    data class Fraction(val text: String) : CalculationResult()

    data object Empty : CalculationResult()

    data object DivideByZeroError : CalculationResult() {
        @StringRes
        val label: Int = R.string.calculator_divide_by_zero_error
    }

    data object Error : CalculationResult() {
        @StringRes
        val label: Int = R.string.error_label
    }
}
