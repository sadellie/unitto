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
import com.sadellie.unitto.R

val ALL_UNIT_GROUPS: List<UnitGroup> by lazy {
    UnitGroup.values().toList()
}

/**
 * As not all measurements can be converted between each other, we separate them into groups.
 * Within one group all measurements can be converted
 */
enum class UnitGroup(
    @StringRes val res: Int,
    val canNegate: Boolean = false
) {
    LENGTH(res = R.string.length),
    CURRENCY(res = R.string.currency),
    MASS(res = R.string.mass),
    SPEED(res = R.string.speed),
    TEMPERATURE(res = R.string.temperature, canNegate = true),
    AREA(res = R.string.area),
    TIME(res = R.string.time),
    VOLUME(res = R.string.volume),
    DATA(res = R.string.data),
    PRESSURE(res = R.string.pressure),
    ACCELERATION(res = R.string.acceleration),
    ENERGY(res = R.string.energy),
    POWER(res = R.string.power),
    ANGLE(res = R.string.angle),
    DATA_TRANSFER(res = R.string.data_transfer),
    FLUX(res = R.string.flux),
}
