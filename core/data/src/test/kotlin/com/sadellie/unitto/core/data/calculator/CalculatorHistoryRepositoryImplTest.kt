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

package com.sadellie.unitto.core.data.calculator

import com.sadellie.unitto.core.database.CalculatorHistoryDao
import com.sadellie.unitto.core.database.CalculatorHistoryEntity
import com.sadellie.unitto.core.model.calculator.CalculatorHistoryItem
import java.util.Date
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * This only verifies correct binding with dao and conversion from [CalculatorHistoryEntity] to
 * [CalculatorHistoryItem] since [CalculatorHistoryRepository] is a wrapper for
 * [CalculatorHistoryDao].
 */
class CalculatorHistoryRepositoryImplTest {
  private val testScope = TestScope(UnconfinedTestDispatcher())

  private val calculatorHistoryDao =
    object : CalculatorHistoryDao {
      private val entries = MutableStateFlow(emptyList<CalculatorHistoryEntity>())

      override fun getAllDescending(): Flow<List<CalculatorHistoryEntity>> =
        entries.mapLatest { currentEntries -> currentEntries.sortedByDescending { it.timestamp } }

      override suspend fun insert(vararg historyEntity: CalculatorHistoryEntity) =
        entries.update { currentEntities ->
          val maxId = currentEntities.maxOfOrNull { it.entityId } ?: -1
          val newEntities =
            historyEntity.asList().mapIndexed { index, entity ->
              entity.copy(entityId = maxId + 1 + index)
            }

          (currentEntities + newEntities).distinctBy { it.entityId }
        }

      override suspend fun delete(entityId: Int) =
        entries.update { currentEntries -> currentEntries.filter { it.entityId != entityId } }

      override suspend fun clear() = entries.update { emptyList() }
    }

  private val calculatorHistoryRepository = CalculatorHistoryRepositoryImpl(calculatorHistoryDao)

  @Test
  fun historyFlow_returnCorrectItems() =
    testScope.runTest {
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
          CalculatorHistoryItem(2, Date(2), "expression 2", "result 2"),
          CalculatorHistoryItem(1, Date(1), "expression 1", "result 1"),
          CalculatorHistoryItem(0, Date(0), "expression 0", "result 0"),
        )
      val actual = calculatorHistoryRepository.historyFlow.first()

      assertEquals(expected, actual)
    }

  @Test
  fun add_addToFlow() =
    testScope.runTest {
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
      val actual = calculatorHistoryRepository.historyFlow.first().first()
      // timestamp is handled internally and not exposed, can't compare entire item
      assertEquals("expression 3", actual.expression)
      assertEquals("result 3", actual.result)
    }

  @Test
  fun delete_removesFromFlow() =
    testScope.runTest {
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
          CalculatorHistoryItem(2, Date(2), "expression 2", "result 2"),
          CalculatorHistoryItem(0, Date(0), "expression 0", "result 0"),
        )
      val actual = calculatorHistoryRepository.historyFlow.first()

      assertEquals(expected, actual)
    }

  @Test
  fun clear_emptyFlow() =
    testScope.runTest {
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
      val expected = emptyList<CalculatorHistoryItem>()
      val actual = calculatorHistoryRepository.historyFlow.first()

      assertEquals(expected, actual)
    }
}
