package com.sadellie.unitto.screens.second

import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.UnitGroup

/**
 * Second (unit list) screen UI state.
 *
 * @property favoritesOnly Whether or not show only favorite [AbstractUnit]s.
 * @property unitsToShow Grouped list of [AbstractUnit]s.
 * @property searchQuery Search query in search bar.
 * @property chosenUnitGroup Currently selected chip. Nul means that no chip is selected.
 */
data class SecondScreenUIState(
    val favoritesOnly: Boolean = false,
    val unitsToShow: Map<UnitGroup, List<AbstractUnit>> = emptyMap(),
    val searchQuery: String = "",
    val chosenUnitGroup: UnitGroup? = null
)
