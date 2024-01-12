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

import androidx.compose.ui.text.input.TextFieldValue
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import java.math.BigDecimal

internal sealed class UIState {
    data object Loading : UIState()

    data class Ready(
        val isMetric: Boolean,
        val height1: TextFieldValue,
        val height2: TextFieldValue,
        val weight: TextFieldValue,
        val normalWeightRange: Pair<BigDecimal, BigDecimal>,
        val result: BigDecimal,
        val allowVibration: Boolean,
        val formatterSymbols: FormatterSymbols,
    ) : UIState()
}
