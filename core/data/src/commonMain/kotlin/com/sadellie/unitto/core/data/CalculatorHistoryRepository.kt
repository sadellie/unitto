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

package com.sadellie.unitto.core.data

import androidx.paging.PagingData
import androidx.paging.insertSeparators
import com.sadellie.unitto.core.model.calculator.CalculatorHistoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

interface CalculatorHistoryRepository {
  /** Calculator history sorted by items timestamp from new to old (DESC). */
  val historyFlow: Flow<PagingData<CalculatorHistoryModel>>

  /**
   * Save [expression] and [result] in calculator history. Both parameters must use tokens
   * recognized by evalutatto - don't forget to replace minus symbol.
   */
  suspend fun add(expression: String, result: String)

  /** Delete [CalculatorHistoryModel] by id from calculator history */
  suspend fun delete(itemId: Int)

  /**
   * Update [CalculatorHistoryModel.Item.label] and toggle [CalculatorHistoryModel.Item.isFavorite]
   */
  suspend fun updateLabel(itemId: Int, label: String)

  /** Deletes all entries from calculator history. */
  suspend fun clear()
}

internal fun PagingData<CalculatorHistoryModel.Item>.insertDateSeparators(systemTZ: TimeZone) =
  this.insertSeparators { before: CalculatorHistoryModel.Item?, after: CalculatorHistoryModel.Item?
    ->
    // reverse logic for reverse list in UI. before is higher, after is lower
    // bottom of the list, never insert header
    if (before == null) return@insertSeparators null
    // top of the list, always insert header
    if (after == null)
      return@insertSeparators CalculatorHistoryModel.Header(
        Instant.fromEpochMilliseconds(before.timestamp)
      )
    val beforeInstant = Instant.fromEpochMilliseconds(before.timestamp)
    val beforeDate = beforeInstant.toLocalDateTime(systemTZ).date
    val afterInstant = Instant.fromEpochMilliseconds(after.timestamp)
    val afterDate = afterInstant.toLocalDateTime(systemTZ).date
    if (beforeDate != afterDate) {
      // different date between items, insert header
      return@insertSeparators CalculatorHistoryModel.Header(beforeInstant)
    }
    // same date between items
    return@insertSeparators null
  }
