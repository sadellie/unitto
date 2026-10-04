/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sadellie.unitto.core.common.defaultIODispatcher
import com.sadellie.unitto.core.data.CalculatorHistoryRepository
import com.sadellie.unitto.core.data.insertDateSeparators
import com.sadellie.unitto.core.database.CalculatorHistoryDao
import com.sadellie.unitto.core.database.CalculatorHistoryEntity
import com.sadellie.unitto.core.model.calculator.CalculatorHistoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlin.time.Clock

class CalculatorHistoryRepositoryImpl(private val calculatorHistoryDao: CalculatorHistoryDao) :
  CalculatorHistoryRepository {

  override val historyFlow: Flow<PagingData<CalculatorHistoryModel>>
    get() {
      val systemTZ = TimeZone.currentSystemDefault()
      return Pager(
          config = PagingConfig(pageSize = 50, enablePlaceholders = true),
          pagingSourceFactory = { calculatorHistoryDao.getAllDescending() },
        )
        .flow
        .map { pagingData ->
          pagingData
            .map { entity ->
              entity.toDomain()
            }
            .insertDateSeparators(systemTZ)
        }
        .flowOn(defaultIODispatcher)
    }

  override suspend fun add(expression: String, result: String) =
    withContext(defaultIODispatcher) {
      calculatorHistoryDao.insert(
        CalculatorHistoryEntity(
          timestamp = Clock.System.now().toEpochMilliseconds(),
          expression = expression,
          result = result,
        )
      )
    }

  override suspend fun delete(itemId: Int) =
    withContext(defaultIODispatcher) { calculatorHistoryDao.delete(itemId) }

  override suspend fun updateLabel(itemId: Int, label: String) =
    withContext(defaultIODispatcher) { calculatorHistoryDao.updateLabel(itemId, label) }

  override suspend fun clear() = withContext(defaultIODispatcher) { calculatorHistoryDao.clear() }

  private fun CalculatorHistoryEntity.toDomain(): CalculatorHistoryModel.Item =
    CalculatorHistoryModel.Item(
      id = this.entityId,
      timestamp = this.timestamp,
      expression = this.expression,
      result = this.result,
      isFavorite = this.isFavorite,
      label = this.label,
    )
}
