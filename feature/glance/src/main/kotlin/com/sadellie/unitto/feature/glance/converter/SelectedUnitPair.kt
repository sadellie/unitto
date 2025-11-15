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

package com.sadellie.unitto.feature.glance.converter

import android.util.Log
import com.sadellie.unitto.core.data.converter.UnitsRepository
import com.sadellie.unitto.core.database.ConverterWidgetUnitPairEntity
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal data class SelectedUnitPair(
  val id: Int = 0,
  val order: Int,
  val from: BasicUnit,
  val to: BasicUnit,
)

internal suspend fun entityListToDomainList(
  unitsRepo: UnitsRepository,
  entities: List<ConverterWidgetUnitPairEntity>,
): List<SelectedUnitPair> =
  withContext(Dispatchers.Default) {
    val result = mutableListOf<SelectedUnitPair>()

    for (entity in entities) {
      val foundFrom = unitsRepo.getByIdOrNull(entity.unitFromId)
      val foundTo = unitsRepo.getByIdOrNull(entity.unitToId)

      if (foundFrom == null || foundTo == null) {
        Log.e(ConverterWidget.LOG_TAG, "Failed to convert $entity")
        continue
      }

      result.add(
        SelectedUnitPair(
          id = entity.entityId,
          order = entity.position,
          from = foundFrom,
          to = foundTo,
        )
      )
    }

    return@withContext result
  }
