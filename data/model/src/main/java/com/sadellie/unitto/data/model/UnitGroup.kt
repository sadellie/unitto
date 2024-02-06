/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

package com.sadellie.unitto.data.model

import androidx.annotation.StringRes
import com.sadellie.unitto.core.base.R

enum class UnitGroup(
    @StringRes val res: Int
) {
    // NOTE: This order is used as default for new users
    LENGTH(res = R.string.unit_group_length),
    CURRENCY(res = R.string.unit_group_currency),
    MASS(res = R.string.unit_group_mass),
    SPEED(res = R.string.unit_group_speed),
    TEMPERATURE(res = R.string.unit_group_temperature),
    AREA(res = R.string.unit_group_area),
    TIME(res = R.string.unit_group_time),
    VOLUME(res = R.string.unit_group_volume),
    DATA(res = R.string.unit_group_data),
    PRESSURE(res = R.string.unit_group_pressure),
    ACCELERATION(res = R.string.unit_group_acceleration),
    ENERGY(res = R.string.unit_group_energy),
    POWER(res = R.string.unit_group_power),
    ANGLE(res = R.string.unit_group_angle),
    DATA_TRANSFER(res = R.string.unit_group_data_transfer),
    FLUX(res = R.string.unit_group_flux),
    NUMBER_BASE(res = R.string.unit_group_number_base),
    ELECTROSTATIC_CAPACITANCE(res = R.string.unit_group_electrostatic_capacitance),
    PREFIX(res = R.string.unit_group_prefix),
    FORCE(res = R.string.unit_group_force),
    TORQUE(res = R.string.unit_group_torque),
    FLOW_RATE(res = R.string.unit_group_flow_rate),
    LUMINANCE(res = R.string.unit_group_luminance),
    FUEL_CONSUMPTION(res = R.string.unit_group_fuel_consumption)
}
