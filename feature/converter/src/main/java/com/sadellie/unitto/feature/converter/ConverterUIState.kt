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

import com.sadellie.unitto.core.base.KEY_0
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
 * @property formatTime If true will format output when converting time.
 * @property showTools If true will show tools button in TopBar.
 */
data class ConverterUIState(
    val inputValue: String = KEY_0,
    val calculatedValue: String? = null,
    val resultValue: String = KEY_0,
    val showLoading: Boolean = true,
    val showError: Boolean = false,
    val unitFrom: AbstractUnit? = null,
    val unitTo: AbstractUnit? = null,
    val mode: ConverterMode = ConverterMode.DEFAULT,
    val formatTime: Boolean = true,
    val showTools: Boolean = false
)

enum class ConverterMode {
    DEFAULT,
    BASE,
}
