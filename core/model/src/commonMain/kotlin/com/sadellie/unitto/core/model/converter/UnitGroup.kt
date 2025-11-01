/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2025 Elshan Agaev
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

package com.sadellie.unitto.core.model.converter

import org.jetbrains.compose.resources.StringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.unit_group_acceleration
import unitto.core.common.generated.resources.unit_group_angle
import unitto.core.common.generated.resources.unit_group_area
import unitto.core.common.generated.resources.unit_group_currency
import unitto.core.common.generated.resources.unit_group_data
import unitto.core.common.generated.resources.unit_group_data_transfer
import unitto.core.common.generated.resources.unit_group_electrostatic_capacitance
import unitto.core.common.generated.resources.unit_group_energy
import unitto.core.common.generated.resources.unit_group_flow_rate
import unitto.core.common.generated.resources.unit_group_flux
import unitto.core.common.generated.resources.unit_group_force
import unitto.core.common.generated.resources.unit_group_fuel_consumption
import unitto.core.common.generated.resources.unit_group_length
import unitto.core.common.generated.resources.unit_group_luminance
import unitto.core.common.generated.resources.unit_group_mass
import unitto.core.common.generated.resources.unit_group_number_base
import unitto.core.common.generated.resources.unit_group_power
import unitto.core.common.generated.resources.unit_group_prefix
import unitto.core.common.generated.resources.unit_group_pressure
import unitto.core.common.generated.resources.unit_group_speed
import unitto.core.common.generated.resources.unit_group_temperature
import unitto.core.common.generated.resources.unit_group_time
import unitto.core.common.generated.resources.unit_group_torque
import unitto.core.common.generated.resources.unit_group_volume

enum class UnitGroup(val res: StringResource) {
  // NOTE: This order is used as default for new users
  LENGTH(res = Res.string.unit_group_length),
  CURRENCY(res = Res.string.unit_group_currency),
  MASS(res = Res.string.unit_group_mass),
  SPEED(res = Res.string.unit_group_speed),
  TEMPERATURE(res = Res.string.unit_group_temperature),
  AREA(res = Res.string.unit_group_area),
  TIME(res = Res.string.unit_group_time),
  VOLUME(res = Res.string.unit_group_volume),
  DATA(res = Res.string.unit_group_data),
  PRESSURE(res = Res.string.unit_group_pressure),
  ACCELERATION(res = Res.string.unit_group_acceleration),
  ENERGY(res = Res.string.unit_group_energy),
  POWER(res = Res.string.unit_group_power),
  ANGLE(res = Res.string.unit_group_angle),
  DATA_TRANSFER(res = Res.string.unit_group_data_transfer),
  FLUX(res = Res.string.unit_group_flux),
  NUMBER_BASE(res = Res.string.unit_group_number_base),
  ELECTROSTATIC_CAPACITANCE(res = Res.string.unit_group_electrostatic_capacitance),
  PREFIX(res = Res.string.unit_group_prefix),
  FORCE(res = Res.string.unit_group_force),
  TORQUE(res = Res.string.unit_group_torque),
  FLOW_RATE(res = Res.string.unit_group_flow_rate),
  LUMINANCE(res = Res.string.unit_group_luminance),
  FUEL_CONSUMPTION(res = Res.string.unit_group_fuel_consumption),
}
