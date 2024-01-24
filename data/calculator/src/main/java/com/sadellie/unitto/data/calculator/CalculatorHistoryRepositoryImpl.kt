/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

package com.sadellie.unitto.data.calculator

import com.sadellie.unitto.data.database.CalculatorHistoryDao
import com.sadellie.unitto.data.database.CalculatorHistoryEntity
import com.sadellie.unitto.data.model.HistoryItem
import com.sadellie.unitto.data.model.repository.CalculatorHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class CalculatorHistoryRepositoryImpl @Inject constructor(
    private val calculatorHistoryDao: CalculatorHistoryDao
) : CalculatorHistoryRepository {
    /**
     * Calculator history sorted by [CalculatorHistoryEntity.timestamp] from new to old (DESC).
     */
    override val historyFlow: Flow<List<HistoryItem>> = calculatorHistoryDao
        .getAllDescending()
        .map { it.toHistoryItemList() }
        .flowOn(Dispatchers.IO)

    override suspend fun add(
        expression: String,
        result: String
    ) {
        calculatorHistoryDao.insert(
            CalculatorHistoryEntity(
                timestamp = System.currentTimeMillis(),
                expression = expression,
                result = result
            )
        )
    }

    override suspend fun clear() {
        calculatorHistoryDao.clear()
    }

    private fun List<CalculatorHistoryEntity>.toHistoryItemList(): List<HistoryItem> {
        return this.map {
            HistoryItem(
                id = it.entityId,
                date = Date(it.timestamp),
                expression = it.expression,
                result = it.result
            )
        }
    }
}
