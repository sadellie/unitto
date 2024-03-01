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

package com.sadellie.unitto.core.data.calculator

import com.sadellie.unitto.core.database.CalculatorHistoryEntity
import com.sadellie.unitto.core.model.calculator.CalculatorHistoryItem
import kotlinx.coroutines.flow.Flow

interface CalculatorHistoryRepository {
  /** Calculator history sorted by [CalculatorHistoryEntity.timestamp] from new to old (DESC). */
  val historyFlow: Flow<List<CalculatorHistoryItem>>

  /**
   * Save [expression] and [result] in calculator history. Both parameters must use tokens
   * recognized by evalutatto - don't forget to replace minus symbol.
   */
  suspend fun add(expression: String, result: String)

  /** Delete [CalculatorHistoryItem] by id from calculator history */
  suspend fun delete(itemId: Int)

  /** Deletes all entries from calculator history. */
  suspend fun clear()
}
