/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2026 Elshan Agaev
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

package com.sadellie.unitto.core.data.calculator

import androidx.paging.testing.asSnapshot
import com.sadellie.unitto.core.database.CalculatorHistoryDaoInMemory
import com.sadellie.unitto.core.database.CalculatorHistoryEntity
import com.sadellie.unitto.core.model.calculator.CalculatorHistoryModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant
import kotlinx.coroutines.test.runTest

class CalculatorHistoryRepositoryImplTest {
  private val calculatorHistoryDao = CalculatorHistoryDaoInMemory()
  private val calculatorHistoryRepository = CalculatorHistoryRepositoryImpl(calculatorHistoryDao)

  @Test
  fun historyFlow_returnCorrectItems() = runTest {
    // insert some entries
    val entries =
      listOf(
        CalculatorHistoryEntity(0, 0, "expression 0", "result 0"),
        CalculatorHistoryEntity(1, 1, "expression 1", "result 1"),
        CalculatorHistoryEntity(2, 2, "expression 2", "result 2"),
      )
    entries.forEach { calculatorHistoryDao.insert(it) }

    // get back same data but converted and in right order
    val expected =
      listOf(
        CalculatorHistoryModel.Item(2, 2, "expression 2", "result 2", false, null),
        CalculatorHistoryModel.Item(1, 1, "expression 1", "result 1", false, null),
        CalculatorHistoryModel.Item(0, 0, "expression 0", "result 0", false, null),
        CalculatorHistoryModel.Header(Instant.fromEpochMilliseconds(0)),
      )
    val actual = calculatorHistoryRepository.historyFlow.asSnapshot()

    assertEquals(expected, actual)
  }

  @Test
  fun add_addToFlow() = runTest {
    // insert some entries
    val entries =
      listOf(
        CalculatorHistoryEntity(0, 0, "expression 0", "result 0"),
        CalculatorHistoryEntity(1, 1, "expression 1", "result 1"),
        CalculatorHistoryEntity(2, 2, "expression 2", "result 2"),
      )
    entries.forEach { calculatorHistoryDao.insert(it) }

    // add one
    calculatorHistoryRepository.add("expression 3", "result 3")

    // descending list, latest added item is first
    val actual =
      calculatorHistoryRepository.historyFlow.asSnapshot().first() as CalculatorHistoryModel.Item
    // timestamp is handled internally and not exposed, can't compare entire item
    assertEquals("expression 3", actual.expression)
    assertEquals("result 3", actual.result)
  }

  @Test
  fun delete_removesFromFlow() = runTest {
    // insert some entries
    val entries =
      listOf(
        CalculatorHistoryEntity(0, 0, "expression 0", "result 0"),
        CalculatorHistoryEntity(1, 1, "expression 1", "result 1"),
        CalculatorHistoryEntity(2, 2, "expression 2", "result 2"),
      )
    entries.forEach { calculatorHistoryDao.insert(it) }

    // remove one
    calculatorHistoryRepository.delete(1)

    // make sure it is removed and other entries are in place
    val expected =
      listOf(
        CalculatorHistoryModel.Item(2, 2, "expression 2", "result 2", false, null),
        CalculatorHistoryModel.Item(0, 0, "expression 0", "result 0", false, null),
        CalculatorHistoryModel.Header(Instant.fromEpochMilliseconds(0)),
      )
    val actual = calculatorHistoryRepository.historyFlow.asSnapshot()

    assertEquals(expected, actual)
  }

  @Test
  fun clear_emptyFlow() = runTest {
    // insert some entries
    val entries =
      listOf(
        CalculatorHistoryEntity(0, 0, "expression 0", "result 0"),
        CalculatorHistoryEntity(1, 1, "expression 1", "result 1"),
        CalculatorHistoryEntity(2, 2, "expression 2", "result 2"),
      )
    entries.forEach { calculatorHistoryDao.insert(it) }

    // clear database
    calculatorHistoryRepository.clear()

    // make sure flow is empty
    val expected = emptyList<CalculatorHistoryModel>()
    val actual = calculatorHistoryRepository.historyFlow.asSnapshot()

    assertEquals(expected, actual)
  }
}
