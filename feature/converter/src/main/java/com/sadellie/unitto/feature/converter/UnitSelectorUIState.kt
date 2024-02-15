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

package com.sadellie.unitto.feature.converter

import androidx.compose.ui.text.input.TextFieldValue
import com.sadellie.unitto.core.base.FormatterSymbols
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.UnitsListSorting
import com.sadellie.unitto.data.model.unit.AbstractUnit

internal sealed class UnitSelectorUIState {
    data object Loading : UnitSelectorUIState()

    data class UnitFrom(
        val query: TextFieldValue,
        val unitFrom: AbstractUnit,
        val shownUnitGroups: List<UnitGroup>,
        val showFavoritesOnly: Boolean,
        val units: UnitSearchResult,
        val selectedUnitGroup: UnitGroup?,
        val sorting: UnitsListSorting,
    ) : UnitSelectorUIState()

    data class UnitTo(
        val query: TextFieldValue,
        val unitFrom: AbstractUnit,
        val unitTo: AbstractUnit,
        val showFavoritesOnly: Boolean,
        val units: UnitSearchResult,
        val input: String?,
        val sorting: UnitsListSorting,
        val scale: Int,
        val outputFormat: Int,
        val formatterSymbols: FormatterSymbols,
    ) : UnitSelectorUIState()
}

internal sealed class UnitSearchResult {
    data object Empty : UnitSearchResult()

    data object Loading : UnitSearchResult()

    data class Success(
        val units: Map<UnitGroup, List<AbstractUnit>>,
    ) : UnitSearchResult()
}
