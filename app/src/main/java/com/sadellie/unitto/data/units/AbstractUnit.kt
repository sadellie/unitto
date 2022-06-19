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
import java.math.BigDecimal

/**
 * This is a basic representation of what a unit must have (properties and convert function)
 *
 * @property unitId Unit ID from [MyUnitIDS]
 * @property displayName String resource with long name, i.e. kilometer
 * @property shortName String resource with short name, i.e. km
 * @property basicUnit Used for conversion. Basically tells how big this unit is if comparing with
 * basicUnit. For example, in [UnitGroup.LENGTH] basic unit is an attometer (1), then nanometer is
 * 1.0E+9 times bigger than that. This number (1.0E+9) is a basic unit for nanometer
 * @property group [UnitGroup] of this unit
 * @property renderedName Used a cache. Stores long name string for this specific device. Need for
 * search functionality
 * @property isFavorite Whether this unit is favorite.
 * @property isEnabled Whether we need to show this unit or not
 * @property pairedUnit Latest paired unit on the right
 * @property counter The amount of time this unit was chosen
 */
abstract class AbstractUnit(
    val unitId: String,
    @StringRes val displayName: Int,
    @StringRes val shortName: Int,
    var basicUnit: BigDecimal,
    val group: UnitGroup,
    var renderedName: String = String(),
    var isFavorite: Boolean = false,
    var isEnabled: Boolean = true,
    var pairedUnit: String? = null,
    var counter: Int = 0
) {
    /**
     * Convert this unit into another
     *
     * @param unitTo Unit we want to convert to (right side unit)
     * @param value The amount to convert
     * @param scale Which scale to use (number of decimal places)
     * @return
     */
    abstract fun convert(
        unitTo: AbstractUnit,
        value: BigDecimal,
        scale: Int
    ): BigDecimal
}
