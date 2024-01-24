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

package com.sadellie.unitto.data.converter.collections

import com.sadellie.unitto.core.base.MAX_PRECISION
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.TemperatureUnit
import com.sadellie.unitto.data.converter.UnitID
import java.math.BigDecimal
import java.math.RoundingMode

internal val temperatureCollection: List<AbstractUnit> by lazy {
    listOf(
        TemperatureUnit(
            id = UnitID.celsius,
            basicUnit = BigDecimal.ONE,
            group = UnitGroup.TEMPERATURE,
            displayName = R.string.unit_celsius,
            shortName = R.string.unit_celsius_short,
            customConvert = { unitTo, value ->
                when (unitTo.id) {
                    UnitID.fahrenheit -> {
                        value
                            .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                            .times(BigDecimal.valueOf(1.8))
                            .plus(BigDecimal(32))
                    }

                    UnitID.kelvin -> {
                        value
                            .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                            .plus(BigDecimal.valueOf(273.15))
                    }

                    else -> value
                }
            }
        ),
        TemperatureUnit(
            id = UnitID.fahrenheit,
            basicUnit = BigDecimal.ONE,
            group = UnitGroup.TEMPERATURE,
            displayName = R.string.unit_fahrenheit,
            shortName = R.string.unit_fahrenheit_short,
            customConvert = { unitTo, value ->
                when (unitTo.id) {
                    UnitID.celsius -> {
                        value
                            .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                            .minus(BigDecimal(32))
                            .times(BigDecimal(5))
                            .div(BigDecimal(9))
                    }
                    UnitID.kelvin -> {
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
        ),
        TemperatureUnit(
            id = UnitID.kelvin,
            basicUnit = BigDecimal.ONE,
            group = UnitGroup.TEMPERATURE,
            displayName = R.string.unit_kelvin,
            shortName = R.string.unit_kelvin_short,
            customConvert = { unitTo, value ->
                when (unitTo.id) {
                    UnitID.celsius -> {
                        value
                            .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                            .minus(BigDecimal(273.15))
                    }
                    UnitID.fahrenheit -> {
                        value
                            .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                            .minus(BigDecimal.valueOf(273.15))
                            .times(BigDecimal.valueOf(1.8))
                            .plus(BigDecimal(32))
                    }
                    else -> value
                }
            }
        ),
    )
}
