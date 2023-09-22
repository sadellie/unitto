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

package com.sadellie.unitto.feature.converter

import androidx.compose.ui.text.input.TextFieldValue
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.UnitsListSorting
import com.sadellie.unitto.data.model.unit.AbstractUnit

internal sealed class RightSideUIState {
    data object Loading : RightSideUIState()

    data class Ready(
        val unitFrom: AbstractUnit,
        val unitTo: AbstractUnit,
        val query: TextFieldValue,
        val units: Map<UnitGroup, List<AbstractUnit>>,
        val favorites: Boolean,
        val sorting: UnitsListSorting,
        val input: String,
        val scale: Int,
        val outputFormat: Int,
        val formatterSymbols: FormatterSymbols,
        val currencyRateUpdateState: CurrencyRateUpdateState,
    ) : RightSideUIState()
}
