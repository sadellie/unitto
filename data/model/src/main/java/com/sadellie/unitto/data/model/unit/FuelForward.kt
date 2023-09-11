/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

import com.sadellie.unitto.core.base.MAX_PRECISION
import com.sadellie.unitto.data.model.UnitGroup
import java.math.BigDecimal
import java.math.RoundingMode

data class FuelForward(
    override val id: String,
    override val basicUnit: BigDecimal,
    override val group: UnitGroup,
    override val displayName: Int,
    override val shortName: Int,
    override val isFavorite: Boolean = false,
    override val pairId: String? = null,
    override val counter: Int = 0,
) : DefaultUnit {
    override fun convert(unitTo: DefaultUnit, value: BigDecimal): BigDecimal {
        // Avoid division by zero
        if (unitTo.basicUnit.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO

        return when (unitTo) {
            is FuelForward -> this
                .basicUnit
                .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                .div(unitTo.basicUnit).multiply(value)

            is FuelBackward -> unitTo
                .basicUnit
                .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                .div(this.basicUnit)
                .div(value)

            else -> BigDecimal.ZERO
        }
    }
}
