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

import com.sadellie.unitto.core.data.converter.UnitSearchResultItem
import com.sadellie.unitto.core.data.converter.UnitStats
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import java.math.BigDecimal
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SortUnitGroupsByUsage {
  private val testScope = TestScope(UnconfinedTestDispatcher())

  private fun unitWithFrequency(value: Int): UnitSearchResultItem {
    // sorting algorithm cares about frequency only, group is taken from map
    val unit =
      NormalUnit(
        id = "unit",
        factor = BigDecimal.ZERO,
        group = UnitGroup.LENGTH,
        displayName = -1,
        shortName = -1,
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
      val units = emptyMap<UnitGroup, List<UnitSearchResultItem>>()
      val actual = sortUnitGroupsByUsage(units)
      val expected = emptyList<UnitGroup>()

      assertEquals(actual, expected)
    }

  @Test
  fun autoSortUnitGroups_single() =
    testScope.runTest {
      val units =
        mapOf(
          UnitGroup.LENGTH to
            listOf(
              unitWithFrequency(10),
              unitWithFrequency(8),
              unitWithFrequency(6),
              unitWithFrequency(4),
            )
        )
      val actual = sortUnitGroupsByUsage(units)
      val expected = listOf(UnitGroup.LENGTH)

      assertEquals(actual, expected)
    }

  @Test
  fun sortUnitGroupsByUsage_multiple() =
    testScope.runTest {
      val units =
        mapOf(
          UnitGroup.LENGTH to
            listOf(
              unitWithFrequency(10),
              unitWithFrequency(8),
              unitWithFrequency(6),
              unitWithFrequency(4),
            ),
          UnitGroup.AREA to
            listOf(
              unitWithFrequency(4),
              unitWithFrequency(3),
              unitWithFrequency(2),
              unitWithFrequency(1),
            ),
          UnitGroup.DATA to
            listOf(
              unitWithFrequency(100),
              unitWithFrequency(80),
              unitWithFrequency(60),
              unitWithFrequency(40),
            ),
        )
      val actual = sortUnitGroupsByUsage(units)
      val expected = listOf(UnitGroup.DATA, UnitGroup.LENGTH, UnitGroup.AREA)

      assertEquals(actual, expected)
    }
}
