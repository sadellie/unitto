/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.data.converter.UnitSearchResultItem
import com.sadellie.unitto.core.data.converter.UnitStats
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.unit_kilometer
import unitto.core.common.generated.resources.unit_kilometer_short

class SortUnitGroupsByUsage {
  private val testScope = TestScope(UnconfinedTestDispatcher())

  private fun unitWithFrequency(group: UnitGroup, value: Int): UnitSearchResultItem {
    // sorting algorithm cares about frequency only, group is taken from map
    val unit =
      NormalUnit(
        id = "unit",
        factor = KBigDecimal.ZERO,
        group = group,
        displayName = Res.string.unit_kilometer,
        shortName = Res.string.unit_kilometer_short,
        backward = false,
      )

    return UnitSearchResultItem(
      basicUnit = unit,
      stats = UnitStats(unit.id, isFavorite = true, frequency = value),
      conversion = null,
    )
  }

  @Test
  fun sortUnitGroupsByUsage_empty() =
    testScope.runTest {
      val units = emptySequence<UnitSearchResultItem>()
      val actual = sortUnitGroupsByUsage(units)
      val expected = emptyList<UnitGroup>()

      assertEquals(actual, expected)
    }

  @Test
  fun autoSortUnitGroups_single() =
    testScope.runTest {
      val units =
        listOf(
            unitWithFrequency(UnitGroup.LENGTH, 10),
            unitWithFrequency(UnitGroup.LENGTH, 8),
            unitWithFrequency(UnitGroup.LENGTH, 6),
            unitWithFrequency(UnitGroup.LENGTH, 4),
          )
          .asSequence()
      val actual = sortUnitGroupsByUsage(units)
      val expected = listOf(UnitGroup.LENGTH)

      assertEquals(actual, expected)
    }

  @Test
  fun sortUnitGroupsByUsage_multiple() =
    testScope.runTest {
      val units =
        sequenceOf(
          unitWithFrequency(UnitGroup.LENGTH, 10),
          unitWithFrequency(UnitGroup.LENGTH, 8),
          unitWithFrequency(UnitGroup.LENGTH, 6),
          unitWithFrequency(UnitGroup.LENGTH, 4),
          unitWithFrequency(UnitGroup.AREA, 4),
          unitWithFrequency(UnitGroup.AREA, 3),
          unitWithFrequency(UnitGroup.AREA, 2),
          unitWithFrequency(UnitGroup.AREA, 1),
          unitWithFrequency(UnitGroup.DATA, 100),
          unitWithFrequency(UnitGroup.DATA, 80),
          unitWithFrequency(UnitGroup.DATA, 60),
          unitWithFrequency(UnitGroup.DATA, 40),
        )
      val actual = sortUnitGroupsByUsage(units)
      val expected = listOf(UnitGroup.DATA, UnitGroup.LENGTH, UnitGroup.AREA)

      assertEquals(actual, expected)
    }
}
