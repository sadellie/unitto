/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

package com.sadellie.unitto.core.data.converter.collections

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.unit_centimeter_per_hour
import unitto.core.common.generated.resources.unit_centimeter_per_hour_short
import unitto.core.common.generated.resources.unit_centimeter_per_minute
import unitto.core.common.generated.resources.unit_centimeter_per_minute_short
import unitto.core.common.generated.resources.unit_centimeter_per_second
import unitto.core.common.generated.resources.unit_centimeter_per_second_short
import unitto.core.common.generated.resources.unit_cosmic_velocity_first
import unitto.core.common.generated.resources.unit_cosmic_velocity_first_short
import unitto.core.common.generated.resources.unit_cosmic_velocity_second
import unitto.core.common.generated.resources.unit_cosmic_velocity_second_short
import unitto.core.common.generated.resources.unit_cosmic_velocity_third
import unitto.core.common.generated.resources.unit_cosmic_velocity_third_short
import unitto.core.common.generated.resources.unit_earths_orbital_speed
import unitto.core.common.generated.resources.unit_earths_orbital_speed_short
import unitto.core.common.generated.resources.unit_foot_per_hour
import unitto.core.common.generated.resources.unit_foot_per_hour_short
import unitto.core.common.generated.resources.unit_foot_per_minute
import unitto.core.common.generated.resources.unit_foot_per_minute_short
import unitto.core.common.generated.resources.unit_foot_per_second
import unitto.core.common.generated.resources.unit_foot_per_second_short
import unitto.core.common.generated.resources.unit_hour_per_kilometer
import unitto.core.common.generated.resources.unit_hour_per_kilometer_short
import unitto.core.common.generated.resources.unit_hour_per_mile
import unitto.core.common.generated.resources.unit_hour_per_mile_short
import unitto.core.common.generated.resources.unit_kilometer_per_hour
import unitto.core.common.generated.resources.unit_kilometer_per_hour_short
import unitto.core.common.generated.resources.unit_kilometer_per_minute
import unitto.core.common.generated.resources.unit_kilometer_per_minute_short
import unitto.core.common.generated.resources.unit_kilometer_per_second
import unitto.core.common.generated.resources.unit_kilometer_per_second_short
import unitto.core.common.generated.resources.unit_knot
import unitto.core.common.generated.resources.unit_knot_short
import unitto.core.common.generated.resources.unit_mach
import unitto.core.common.generated.resources.unit_mach_short
import unitto.core.common.generated.resources.unit_mach_si_standard
import unitto.core.common.generated.resources.unit_mach_si_standard_short
import unitto.core.common.generated.resources.unit_meter_per_hour
import unitto.core.common.generated.resources.unit_meter_per_hour_short
import unitto.core.common.generated.resources.unit_meter_per_minute
import unitto.core.common.generated.resources.unit_meter_per_minute_short
import unitto.core.common.generated.resources.unit_meter_per_second
import unitto.core.common.generated.resources.unit_meter_per_second_short
import unitto.core.common.generated.resources.unit_mile_per_hour
import unitto.core.common.generated.resources.unit_mile_per_hour_short
import unitto.core.common.generated.resources.unit_mile_per_minute
import unitto.core.common.generated.resources.unit_mile_per_minute_short
import unitto.core.common.generated.resources.unit_mile_per_second
import unitto.core.common.generated.resources.unit_mile_per_second_short
import unitto.core.common.generated.resources.unit_millimeter_per_hour
import unitto.core.common.generated.resources.unit_millimeter_per_hour_short
import unitto.core.common.generated.resources.unit_millimeter_per_minute
import unitto.core.common.generated.resources.unit_millimeter_per_minute_short
import unitto.core.common.generated.resources.unit_millimeter_per_second
import unitto.core.common.generated.resources.unit_millimeter_per_second_short
import unitto.core.common.generated.resources.unit_minute_per_kilometer
import unitto.core.common.generated.resources.unit_minute_per_kilometer_short
import unitto.core.common.generated.resources.unit_minute_per_mile
import unitto.core.common.generated.resources.unit_minute_per_mile_short
import unitto.core.common.generated.resources.unit_velocity_of_light_in_vacuum
import unitto.core.common.generated.resources.unit_velocity_of_light_in_vacuum_short
import unitto.core.common.generated.resources.unit_yard_per_hour
import unitto.core.common.generated.resources.unit_yard_per_hour_short
import unitto.core.common.generated.resources.unit_yard_per_minute
import unitto.core.common.generated.resources.unit_yard_per_minute_short
import unitto.core.common.generated.resources.unit_yard_per_second
import unitto.core.common.generated.resources.unit_yard_per_second_short

internal val speedCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.millimeter_per_hour,
      KBigDecimal("1"),
      UnitGroup.SPEED,
      Res.string.unit_millimeter_per_hour,
      Res.string.unit_millimeter_per_hour_short,
    ),
    NormalUnit(
      UnitID.millimeter_per_minute,
      KBigDecimal("60"),
      UnitGroup.SPEED,
      Res.string.unit_millimeter_per_minute,
      Res.string.unit_millimeter_per_minute_short,
    ),
    NormalUnit(
      UnitID.millimeter_per_second,
      KBigDecimal("3600"),
      UnitGroup.SPEED,
      Res.string.unit_millimeter_per_second,
      Res.string.unit_millimeter_per_second_short,
    ),
    NormalUnit(
      UnitID.centimeter_per_hour,
      KBigDecimal("10"),
      UnitGroup.SPEED,
      Res.string.unit_centimeter_per_hour,
      Res.string.unit_centimeter_per_hour_short,
    ),
    NormalUnit(
      UnitID.centimeter_per_minute,
      KBigDecimal("600"),
      UnitGroup.SPEED,
      Res.string.unit_centimeter_per_minute,
      Res.string.unit_centimeter_per_minute_short,
    ),
    NormalUnit(
      UnitID.centimeter_per_second,
      KBigDecimal("36000"),
      UnitGroup.SPEED,
      Res.string.unit_centimeter_per_second,
      Res.string.unit_centimeter_per_second_short,
    ),
    NormalUnit(
      UnitID.meter_per_hour,
      KBigDecimal("1000"),
      UnitGroup.SPEED,
      Res.string.unit_meter_per_hour,
      Res.string.unit_meter_per_hour_short,
    ),
    NormalUnit(
      UnitID.meter_per_minute,
      KBigDecimal("60000"),
      UnitGroup.SPEED,
      Res.string.unit_meter_per_minute,
      Res.string.unit_meter_per_minute_short,
    ),
    NormalUnit(
      UnitID.meter_per_second,
      KBigDecimal("3600000"),
      UnitGroup.SPEED,
      Res.string.unit_meter_per_second,
      Res.string.unit_meter_per_second_short,
    ),
    NormalUnit(
      UnitID.kilometer_per_hour,
      KBigDecimal("1000000"),
      UnitGroup.SPEED,
      Res.string.unit_kilometer_per_hour,
      Res.string.unit_kilometer_per_hour_short,
    ),
    NormalUnit(
      UnitID.kilometer_per_minute,
      KBigDecimal("60000000"),
      UnitGroup.SPEED,
      Res.string.unit_kilometer_per_minute,
      Res.string.unit_kilometer_per_minute_short,
    ),
    NormalUnit(
      UnitID.kilometer_per_second,
      KBigDecimal("3600000000"),
      UnitGroup.SPEED,
      Res.string.unit_kilometer_per_second,
      Res.string.unit_kilometer_per_second_short,
    ),
    NormalUnit(
      UnitID.foot_per_hour,
      KBigDecimal("304.8"),
      UnitGroup.SPEED,
      Res.string.unit_foot_per_hour,
      Res.string.unit_foot_per_hour_short,
    ),
    NormalUnit(
      UnitID.foot_per_minute,
      KBigDecimal("18288"),
      UnitGroup.SPEED,
      Res.string.unit_foot_per_minute,
      Res.string.unit_foot_per_minute_short,
    ),
    NormalUnit(
      UnitID.foot_per_second,
      KBigDecimal("1097280"),
      UnitGroup.SPEED,
      Res.string.unit_foot_per_second,
      Res.string.unit_foot_per_second_short,
    ),
    NormalUnit(
      UnitID.yard_per_hour,
      KBigDecimal("914.4"),
      UnitGroup.SPEED,
      Res.string.unit_yard_per_hour,
      Res.string.unit_yard_per_hour_short,
    ),
    NormalUnit(
      UnitID.yard_per_minute,
      KBigDecimal("54864"),
      UnitGroup.SPEED,
      Res.string.unit_yard_per_minute,
      Res.string.unit_yard_per_minute_short,
    ),
    NormalUnit(
      UnitID.yard_per_second,
      KBigDecimal("3291840"),
      UnitGroup.SPEED,
      Res.string.unit_yard_per_second,
      Res.string.unit_yard_per_second_short,
    ),
    NormalUnit(
      UnitID.mile_per_hour,
      KBigDecimal("1609344"),
      UnitGroup.SPEED,
      Res.string.unit_mile_per_hour,
      Res.string.unit_mile_per_hour_short,
    ),
    NormalUnit(
      UnitID.mile_per_minute,
      KBigDecimal("96560640"),
      UnitGroup.SPEED,
      Res.string.unit_mile_per_minute,
      Res.string.unit_mile_per_minute_short,
    ),
    NormalUnit(
      UnitID.mile_per_second,
      KBigDecimal("5793638400"),
      UnitGroup.SPEED,
      Res.string.unit_mile_per_second,
      Res.string.unit_mile_per_second_short,
    ),
    NormalUnit(
      UnitID.minute_per_kilometer,
      KBigDecimal("60000000"),
      UnitGroup.SPEED,
      Res.string.unit_minute_per_kilometer,
      Res.string.unit_minute_per_kilometer_short,
      true,
    ),
    NormalUnit(
      UnitID.minute_per_mile,
      KBigDecimal("96560640"),
      UnitGroup.SPEED,
      Res.string.unit_minute_per_mile,
      Res.string.unit_minute_per_mile_short,
      true,
    ),
    NormalUnit(
      UnitID.hour_per_kilometer,
      KBigDecimal("1000000"),
      UnitGroup.SPEED,
      Res.string.unit_hour_per_kilometer,
      Res.string.unit_hour_per_kilometer_short,
      true,
    ),
    NormalUnit(
      UnitID.hour_per_mile,
      KBigDecimal("1609344"),
      UnitGroup.SPEED,
      Res.string.unit_hour_per_mile,
      Res.string.unit_hour_per_mile_short,
      true,
    ),
    NormalUnit(
      UnitID.knot,
      KBigDecimal("1852000"),
      UnitGroup.SPEED,
      Res.string.unit_knot,
      Res.string.unit_knot_short,
    ),
    NormalUnit(
      UnitID.velocity_of_light_in_vacuum,
      KBigDecimal("1079252848799998"),
      UnitGroup.SPEED,
      Res.string.unit_velocity_of_light_in_vacuum,
      Res.string.unit_velocity_of_light_in_vacuum_short,
    ),
    NormalUnit(
      UnitID.cosmic_velocity_first,
      KBigDecimal("28440000000"),
      UnitGroup.SPEED,
      Res.string.unit_cosmic_velocity_first,
      Res.string.unit_cosmic_velocity_first_short,
    ),
    NormalUnit(
      UnitID.cosmic_velocity_second,
      KBigDecimal("40320000000"),
      UnitGroup.SPEED,
      Res.string.unit_cosmic_velocity_second,
      Res.string.unit_cosmic_velocity_second_short,
    ),
    NormalUnit(
      UnitID.cosmic_velocity_third,
      KBigDecimal("60012000000"),
      UnitGroup.SPEED,
      Res.string.unit_cosmic_velocity_third,
      Res.string.unit_cosmic_velocity_third_short,
    ),
    NormalUnit(
      UnitID.earths_orbital_speed,
      KBigDecimal("107154000000"),
      UnitGroup.SPEED,
      Res.string.unit_earths_orbital_speed,
      Res.string.unit_earths_orbital_speed_short,
    ),
    NormalUnit(
      UnitID.mach,
      KBigDecimal("1236960000"),
      UnitGroup.SPEED,
      Res.string.unit_mach,
      Res.string.unit_mach_short,
    ),
    NormalUnit(
      UnitID.mach_si_standard,
      KBigDecimal("1062167040"),
      UnitGroup.SPEED,
      Res.string.unit_mach_si_standard,
      Res.string.unit_mach_si_standard_short,
    ),
  )
}
