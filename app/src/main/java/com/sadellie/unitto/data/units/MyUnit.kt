package com.sadellie.unitto.data.units

import androidx.annotation.StringRes
import com.sadellie.unitto.data.preferences.MAX_PRECISION
import com.sadellie.unitto.screens.setMinimumRequiredScale
import java.math.BigDecimal

/**
 * This class represents a Measurement object
 * @param[displayName] The string resource, i.e. kilometer
 * @param[shortName] The string resource for a short name, i.e. km
 * @param[basicUnit] One unit of this measurement in basic unit
 * @param[group] THe group this measurement belongs to
 */
class MyUnit(
    unitId: String,
    basicUnit: BigDecimal,
    group: UnitGroup,
    @StringRes displayName: Int,
    @StringRes shortName: Int,
) : AbstractUnit(
    unitId = unitId,
    displayName = displayName,
    shortName = shortName,
    basicUnit = basicUnit,
    group = group,
) {
    override fun convert(
        unitTo: AbstractUnit,
        value: BigDecimal,
        scale: Int
    ): BigDecimal {
        return this
            .basicUnit
            .setScale(MAX_PRECISION)
            .multiply(value)
            .div(unitTo.basicUnit)
            .setMinimumRequiredScale(scale)
            .stripTrailingZeros()
    }
}
