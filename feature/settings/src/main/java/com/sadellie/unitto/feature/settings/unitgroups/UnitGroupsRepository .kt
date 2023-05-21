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

package com.sadellie.unitto.feature.settings.unitgroups

import com.sadellie.unitto.data.model.ALL_UNIT_GROUPS
import com.sadellie.unitto.data.model.UnitGroup
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.burnoutcrew.reorderable.ItemPosition
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that holds information about shown and hidden [UnitGroup]s and provides methods to
 * show/hide [UnitGroup]s.
 */
@Singleton
class UnitGroupsRepository @Inject constructor() {

    /**
     * Mutex is need needed because we work with flow (sync stuff).
     */
    private val mutex = Mutex()

    /**
     * Currently shown [UnitGroup]s.
     */
    var shownUnitGroups = MutableStateFlow(listOf<UnitGroup>())
        private set

    /**
     * Currently hidden [UnitGroup]s.
     */
    var hiddenUnitGroups = MutableStateFlow(listOf<UnitGroup>())
        private set

    /**
     * Sets [shownUnitGroups] and updates [hiddenUnitGroups] as a side effect. [hiddenUnitGroups] is
     * everything from [ALL_UNIT_GROUPS] that was not in [shownUnitGroups].
     *
     * @param list List of [UnitGroup]s that need to be shown.
     */
    suspend fun updateShownGroups(list: List<UnitGroup>) {
        mutex.withLock {
            shownUnitGroups.value = list
            hiddenUnitGroups.value = ALL_UNIT_GROUPS - list.toSet()
        }
    }

    /**
     * Moves [UnitGroup] from [shownUnitGroups] to [hiddenUnitGroups]
     *
     * @param unitGroup [UnitGroup] to hide.
     */
    suspend fun markUnitGroupAsHidden(unitGroup: UnitGroup) {
        mutex.withLock {
            shownUnitGroups.value = shownUnitGroups.value - unitGroup
            // Newly hidden unit will appear at the top of the list
            hiddenUnitGroups.value = listOf(unitGroup) + hiddenUnitGroups.value
        }
    }

    /**
     * Moves [UnitGroup] from [hiddenUnitGroups] to [shownUnitGroups]
     *
     * @param unitGroup [UnitGroup] to show.
     */
    suspend fun markUnitGroupAsShown(unitGroup: UnitGroup) {
        mutex.withLock {
            hiddenUnitGroups.value = hiddenUnitGroups.value - unitGroup
            shownUnitGroups.value = shownUnitGroups.value + unitGroup
        }
    }

    /**
     * Moves [UnitGroup] in [shownUnitGroups] from one index to another (reorder).
     *
     * @param from Position from which we need to move from
     * @param to Position where to put [UnitGroup]
     */
    suspend fun moveShownUnitGroups(from: ItemPosition, to: ItemPosition) {
        mutex.withLock {
            shownUnitGroups.value = shownUnitGroups.value.toMutableList().apply {
                val initialIndex = shownUnitGroups.value.indexOfFirst { it == from.key }
                /**
                 * No such item. Happens when dragging item and clicking "remove" while item is
                 * still being dragged.
                 */
                if (initialIndex == -1) return

                add(
                    shownUnitGroups.value.indexOfFirst { it == to.key },
                    removeAt(initialIndex)
                )
            }
        }
    }
}
