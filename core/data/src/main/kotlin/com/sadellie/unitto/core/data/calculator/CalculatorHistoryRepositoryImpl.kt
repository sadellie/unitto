/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CalculatorHistoryRepositoryImpl(private val calculatorHistoryDao: CalculatorHistoryDao) :
  CalculatorHistoryRepository {
  override val historyFlow: Flow<List<CalculatorHistoryItem>> =
    calculatorHistoryDao.getAllDescending().map { it.toHistoryItemList() }.flowOn(Dispatchers.IO)

  override suspend fun add(expression: String, result: String) =
    withContext(Dispatchers.IO) {
      calculatorHistoryDao.insert(
        CalculatorHistoryEntity(
          timestamp = System.currentTimeMillis(),
          expression = expression,
          result = result,
        )
      )
    }

  override suspend fun delete(itemId: Int) =
    withContext(Dispatchers.IO) { calculatorHistoryDao.delete(itemId) }

  override suspend fun clear() = withContext(Dispatchers.IO) { calculatorHistoryDao.clear() }

  private suspend fun List<CalculatorHistoryEntity>.toHistoryItemList():
    List<CalculatorHistoryItem> =
    withContext(Dispatchers.Default) {
      this@toHistoryItemList.map {
        CalculatorHistoryItem(
          id = it.entityId,
          date = Date(it.timestamp),
          expression = it.expression,
          result = it.result,
        )
      }
    }
}
