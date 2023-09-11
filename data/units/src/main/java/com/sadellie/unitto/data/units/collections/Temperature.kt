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

package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.core.base.MAX_PRECISION
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.DefaultUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import java.math.BigDecimal
import java.math.RoundingMode

internal val temperatureCollection: List<AbstractUnit> by lazy {
    listOf(Celsius, Fahrenheit, Kelvin)
}

private data object Celsius : DefaultUnit {
    override val id: String = MyUnitIDS.celsius
    override val basicUnit: BigDecimal = BigDecimal.ONE
    override val group: UnitGroup = UnitGroup.TEMPERATURE
    override val displayName: Int = R.string.celsius
    override val shortName: Int = R.string.celsius_short
    override val isFavorite: Boolean = false
    override val pairId: String? = null
    override val counter: Int = 0

    override fun convert(unitTo: DefaultUnit, value: BigDecimal): BigDecimal {
        return when (unitTo.id) {
            MyUnitIDS.fahrenheit -> {
                value
                    .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                    .times(BigDecimal.valueOf(1.8))
                    .plus(BigDecimal(32))
            }

            MyUnitIDS.kelvin -> {
                value
                    .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                    .plus(BigDecimal.valueOf(273.15))
            }

            else -> value
        }
    }
}

private data object Fahrenheit : DefaultUnit {
    override val id: String = MyUnitIDS.fahrenheit
    override val basicUnit: BigDecimal = BigDecimal.ONE
    override val group: UnitGroup = UnitGroup.TEMPERATURE
    override val displayName: Int = R.string.fahrenheit
    override val shortName: Int = R.string.fahrenheit_short
    override val isFavorite: Boolean = false
    override val pairId: String? = null
    override val counter: Int = 0

    override fun convert(unitTo: DefaultUnit, value: BigDecimal): BigDecimal {
        return when (unitTo.id) {
            MyUnitIDS.celsius -> {
                value
                    .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                    .minus(BigDecimal(32))
                    .times(BigDecimal(5))
                    .div(BigDecimal(9))
            }
            MyUnitIDS.kelvin -> {
                value
                    .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                    .minus(BigDecimal(32))
                    .times(BigDecimal(5))
                    .div(BigDecimal(9))
                    .add(BigDecimal.valueOf(273.15))
            }
            else -> value
        }
    }
}

private data object Kelvin : DefaultUnit {
    override val id: String = MyUnitIDS.kelvin
    override val basicUnit: BigDecimal = BigDecimal.ONE
    override val group: UnitGroup = UnitGroup.TEMPERATURE
    override val displayName: Int = R.string.kelvin
    override val shortName: Int = R.string.kelvin_short
    override val isFavorite: Boolean = false
    override val pairId: String? = null
    override val counter: Int = 0

    override fun convert(unitTo: DefaultUnit, value: BigDecimal): BigDecimal {
        return when (unitTo.id) {
            MyUnitIDS.celsius -> {
                value
                    .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                    .minus(BigDecimal(273.15))
            }
            MyUnitIDS.fahrenheit -> {
                value
                    .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                    .minus(BigDecimal.valueOf(273.15))
                    .times(BigDecimal.valueOf(1.8))
                    .plus(BigDecimal(32))
            }
            else -> value
        }
    }
}
