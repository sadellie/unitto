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

package com.sadellie.unitto.data.model.unit

import com.sadellie.unitto.data.model.UnitGroup
import java.math.BigDecimal

data class TemperatureUnit(
    override val id: String,
    override val basicUnit: BigDecimal,
    override val group: UnitGroup,
    override val displayName: Int,
    override val shortName: Int,
    override val isFavorite: Boolean = false,
    override val pairId: String? = null,
    override val counter: Int = 0,
    val customConvert: (unitTo: DefaultUnit, value: BigDecimal) -> BigDecimal
): DefaultUnit {
    override fun convert(unitTo: DefaultUnit, value: BigDecimal): BigDecimal = customConvert(unitTo, value)

    override fun clone(
        id: String,
        basicUnit: BigDecimal,
        group: UnitGroup,
        displayName: Int,
        shortName: Int,
        isFavorite: Boolean,
        pairId: String?,
        counter: Int,
    ): TemperatureUnit = copy(
        id = id,
        basicUnit = basicUnit,
        group = group,
        displayName = displayName,
        shortName = shortName,
        isFavorite = isFavorite,
        pairId = pairId,
        counter = counter,
    )
}
