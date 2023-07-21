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

package com.sadellie.unitto.feature.unitslist

import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.data.model.AbstractUnit
import com.sadellie.unitto.data.model.UnitGroup

/**
 * Second (unit list) screen UI state.
 *
 * @property favoritesOnly Whether or not show only favorite [AbstractUnit]s.
 * @property unitsToShow Grouped list of [AbstractUnit]s.
 * @property searchQuery Search query in search bar.
 * @property shownUnitGroups All [UnitGroup]s that can be seen in chips row
 * @property chosenUnitGroup Currently selected chip. Nul means that no chip is selected.
 */
data class SecondScreenUIState(
    val favoritesOnly: Boolean = false,
    val unitsToShow: Map<UnitGroup, List<AbstractUnit>> = emptyMap(),
    val searchQuery: String = "",
    val shownUnitGroups: List<UnitGroup> = listOf(),
    val chosenUnitGroup: UnitGroup? = null,
    val formatterSymbols: FormatterSymbols = FormatterSymbols.Spaces,
)
