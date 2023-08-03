/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.data.model.AbstractUnit

/**
 * Represents current state of the ConverterScreen
 *
 * @property inputValue Current input value. Can be expression or a simple number.
 * @property calculatedValue Currently calculated value. Can be null if not needed (same as input or
 * expression in input is invalid).
 * @property resultValue Current output value.
 * @property showLoading Whether we are loading data from network.
 * @property showError Whether there was an error while loading data from network
 * @property unitFrom Unit on the left.
 * @property unitTo Unit on the right.
 * @property mode
 * @property allowVibration When true will vibrate on button clicks.
 */
data class ConverterUIState(
    val inputValue: TextFieldValue = TextFieldValue(),
    val calculatedValue: String? = null,
    val resultValue: ConversionResult = ConversionResult.Default(Token.Digit._0),
    val showLoading: Boolean = true,
    val showError: Boolean = false,
    val unitFrom: AbstractUnit? = null,
    val unitTo: AbstractUnit? = null,
    val mode: ConverterMode = ConverterMode.DEFAULT,
    val allowVibration: Boolean = false,
    val middleZero: Boolean = false,
    val formatterSymbols: FormatterSymbols = FormatterSymbols.Spaces,
)

enum class ConverterMode {
    DEFAULT,
    BASE,
}

sealed class ConversionResult {
    data class Default(val result: String) : ConversionResult()
    data class Time(val result: String) : ConversionResult()
    data class NumberBase(val result: String) : ConversionResult()
    data object Loading : ConversionResult()
    data object Error : ConversionResult()
}
