/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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
