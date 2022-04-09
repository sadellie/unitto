package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.R
import com.sadellie.unitto.data.preferences.MAX_PRECISION
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import com.sadellie.unitto.screens.setMinimumRequiredScale
import java.math.BigDecimal
import java.math.RoundingMode

val TEMPERATURE_COLLECTION: List<AbstractUnit> by lazy {
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
                    .stripTrailingZeros()
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
                    .stripTrailingZeros()
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
                    .stripTrailingZeros()
            }
        },
    )
}
