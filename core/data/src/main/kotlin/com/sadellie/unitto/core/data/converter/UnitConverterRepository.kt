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

package com.sadellie.unitto.core.data.converter

import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import kotlinx.coroutines.flow.MutableStateFlow

interface UnitConverterRepository {
  val currencyRateUpdateState: MutableStateFlow<CurrencyRateUpdateState>

  suspend fun getById(id: String): BasicUnit

  suspend fun getPairId(id: String): String

  suspend fun incrementCounter(id: String)

  suspend fun setPair(id: String, pairId: String)

  suspend fun favorite(id: String)

  suspend fun filterUnits(
    query: String,
    unitGroups: List<UnitGroup>,
    favoritesOnly: Boolean,
    sorting: UnitsListSorting,
  ): Map<UnitGroup, List<UnitSearchResultItem>>

  suspend fun filterUnitsAndBatchConvert(
    query: String,
    unitGroup: UnitGroup,
    favoritesOnly: Boolean,
    sorting: UnitsListSorting,
    unitFromId: String,
    input1: String,
    input2: String,
    scale: Int,
  ): Map<UnitGroup, List<UnitSearchResultItem>>

  suspend fun convert(
    unitFromId: String,
    unitToId: String,
    value1: String,
    value2: String,
    formatTime: Boolean,
    scale: Int,
  ): ConverterResult
}
