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
import com.sadellie.unitto.data.R
import com.sadellie.unitto.data.setMinimumRequiredScale
import com.sadellie.unitto.data.trimZeros
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import java.math.BigDecimal
import java.math.RoundingMode

internal val temperatureCollection: List<AbstractUnit> by lazy {
    listOf(
        object : AbstractUnit(
            unitId = MyUnitIDS.celsius,
            basicUnit = BigDecimal.ONE,
            group = UnitGroup.TEMPERATURE,
            displayName = R.string.celsius,
            shortName = R.string.celsius_short,
        ) {
            override fun convert(unitTo: AbstractUnit, value: BigDecimal, scale: Int): BigDecimal {
                return when (unitTo.unitId) {
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
                    .setMinimumRequiredScale(scale)
                    .trimZeros()
            }
        },
        object : AbstractUnit(
            unitId = MyUnitIDS.fahrenheit,
            basicUnit = BigDecimal.ONE,
            group = UnitGroup.TEMPERATURE,
            displayName = R.string.fahrenheit,
            shortName = R.string.fahrenheit_short,
        ) {
            override fun convert(unitTo: AbstractUnit, value: BigDecimal, scale: Int): BigDecimal {
                return when (unitTo.unitId) {
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
                    .setMinimumRequiredScale(scale)
                    .trimZeros()
            }
        },
        object : AbstractUnit(
            unitId = MyUnitIDS.kelvin,
            basicUnit = BigDecimal.ONE,
            group = UnitGroup.TEMPERATURE,
            displayName = R.string.kelvin,
            shortName = R.string.kelvin_short,
        ) {
            override fun convert(unitTo: AbstractUnit, value: BigDecimal, scale: Int): BigDecimal {
                return when (unitTo.unitId) {
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
                    .setMinimumRequiredScale(scale)
                    .trimZeros()
            }
        },
    )
}