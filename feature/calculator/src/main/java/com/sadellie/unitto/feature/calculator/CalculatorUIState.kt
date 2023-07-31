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

package com.sadellie.unitto.feature.calculator

import androidx.annotation.StringRes
import androidx.compose.ui.text.input.TextFieldValue
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.data.model.HistoryItem

data class CalculatorUIState(
    val input: TextFieldValue = TextFieldValue(),
    val output: CalculationResult = CalculationResult.Default(),
    val radianMode: Boolean = true,
    val history: List<HistoryItem> = emptyList(),
    val allowVibration: Boolean = false,
    val formatterSymbols: FormatterSymbols = FormatterSymbols.Spaces,
    val middleZero: Boolean = false,
)

sealed class CalculationResult(@StringRes val label: Int? = null) {
    data class Default(val text: String = "") : CalculationResult()
    object DivideByZeroError : CalculationResult(R.string.divide_by_zero_error)
    object Error : CalculationResult(R.string.error_label)
}
