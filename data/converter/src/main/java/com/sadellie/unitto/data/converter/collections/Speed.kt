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

import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.data.converter.UnitID
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.BackwardUnit
import com.sadellie.unitto.data.model.unit.NormalUnit
import java.math.BigDecimal

internal val speedCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(  UnitID.millimeter_per_hour,         BigDecimal("1"),                      UnitGroup.SPEED,    R.string.unit_millimeter_per_hour,          R.string.unit_millimeter_per_hour_short),
        NormalUnit(  UnitID.millimeter_per_minute,       BigDecimal("60"),                     UnitGroup.SPEED,    R.string.unit_millimeter_per_minute,        R.string.unit_millimeter_per_minute_short),
        NormalUnit(  UnitID.millimeter_per_second,       BigDecimal("3600"),                  UnitGroup.SPEED,    R.string.unit_millimeter_per_second,        R.string.unit_millimeter_per_second_short),
        NormalUnit(  UnitID.centimeter_per_hour,         BigDecimal("10"),                     UnitGroup.SPEED,    R.string.unit_centimeter_per_hour,          R.string.unit_centimeter_per_hour_short),
        NormalUnit(  UnitID.centimeter_per_minute,       BigDecimal("600"),                    UnitGroup.SPEED,    R.string.unit_centimeter_per_minute,        R.string.unit_centimeter_per_minute_short),
        NormalUnit(  UnitID.centimeter_per_second,       BigDecimal("36000"),                 UnitGroup.SPEED,    R.string.unit_centimeter_per_second,        R.string.unit_centimeter_per_second_short),
        NormalUnit(  UnitID.meter_per_hour,              BigDecimal("1000"),                  UnitGroup.SPEED,    R.string.unit_meter_per_hour,               R.string.unit_meter_per_hour_short),
        NormalUnit(  UnitID.meter_per_minute,            BigDecimal("60000"),                 UnitGroup.SPEED,    R.string.unit_meter_per_minute,             R.string.unit_meter_per_minute_short),
        NormalUnit(  UnitID.meter_per_second,            BigDecimal("3600000"),              UnitGroup.SPEED,    R.string.unit_meter_per_second,             R.string.unit_meter_per_second_short),
        NormalUnit(  UnitID.kilometer_per_hour,          BigDecimal("1000000"),              UnitGroup.SPEED,    R.string.unit_kilometer_per_hour,           R.string.unit_kilometer_per_hour_short),
        NormalUnit(  UnitID.kilometer_per_minute,        BigDecimal("60000000"),             UnitGroup.SPEED,    R.string.unit_kilometer_per_minute,         R.string.unit_kilometer_per_minute_short),
        NormalUnit(  UnitID.kilometer_per_second,        BigDecimal("3600000000"),          UnitGroup.SPEED,    R.string.unit_kilometer_per_second,         R.string.unit_kilometer_per_second_short),
        NormalUnit(  UnitID.foot_per_hour,               BigDecimal("304.8"),                  UnitGroup.SPEED,    R.string.unit_foot_per_hour,                R.string.unit_foot_per_hour_short),
        NormalUnit(  UnitID.foot_per_minute,             BigDecimal("18288"),                 UnitGroup.SPEED,    R.string.unit_foot_per_minute,              R.string.unit_foot_per_minute_short),
        NormalUnit(  UnitID.foot_per_second,             BigDecimal("1097280"),              UnitGroup.SPEED,    R.string.unit_foot_per_second,              R.string.unit_foot_per_second_short),
        NormalUnit(  UnitID.yard_per_hour,               BigDecimal("914.4"),                  UnitGroup.SPEED,    R.string.unit_yard_per_hour,                R.string.unit_yard_per_hour_short),
        NormalUnit(  UnitID.yard_per_minute,             BigDecimal("54864"),                 UnitGroup.SPEED,    R.string.unit_yard_per_minute,              R.string.unit_yard_per_minute_short),
        NormalUnit(  UnitID.yard_per_second,             BigDecimal("3291840"),              UnitGroup.SPEED,    R.string.unit_yard_per_second,              R.string.unit_yard_per_second_short),
        NormalUnit(  UnitID.mile_per_hour,               BigDecimal("1609344"),              UnitGroup.SPEED,    R.string.unit_mile_per_hour,                R.string.unit_mile_per_hour_short),
        NormalUnit(  UnitID.mile_per_minute,             BigDecimal("96560640"),             UnitGroup.SPEED,    R.string.unit_mile_per_minute,              R.string.unit_mile_per_minute_short),
        NormalUnit(  UnitID.mile_per_second,             BigDecimal("5793638400"),          UnitGroup.SPEED,    R.string.unit_mile_per_second,              R.string.unit_mile_per_second_short),
        BackwardUnit(UnitID.minute_per_kilometer,        BigDecimal("60000000"),             UnitGroup.SPEED,    R.string.unit_minute_per_kilometer,         R.string.unit_minute_per_kilometer_short),
        BackwardUnit(UnitID.minute_per_mile,             BigDecimal("96560640"),             UnitGroup.SPEED,    R.string.unit_minute_per_mile,              R.string.unit_minute_per_mile_short),
        BackwardUnit(UnitID.hour_per_kilometer,          BigDecimal("1000000"),              UnitGroup.SPEED,    R.string.unit_hour_per_kilometer,           R.string.unit_hour_per_kilometer_short),
        BackwardUnit(UnitID.hour_per_mile,               BigDecimal("1609344"),              UnitGroup.SPEED,    R.string.unit_hour_per_mile,                R.string.unit_hour_per_mile_short),
        NormalUnit(  UnitID.knot,                        BigDecimal("1852000"),              UnitGroup.SPEED,    R.string.unit_knot,                         R.string.unit_knot_short),
        NormalUnit(  UnitID.velocity_of_light_in_vacuum, BigDecimal("1079252848799998"),  UnitGroup.SPEED,    R.string.unit_velocity_of_light_in_vacuum,  R.string.unit_velocity_of_light_in_vacuum_short),
        NormalUnit(  UnitID.cosmic_velocity_first,       BigDecimal("28440000000"),         UnitGroup.SPEED,    R.string.unit_cosmic_velocity_first,        R.string.unit_cosmic_velocity_first_short),
        NormalUnit(  UnitID.cosmic_velocity_second,      BigDecimal("40320000000"),         UnitGroup.SPEED,    R.string.unit_cosmic_velocity_second,       R.string.unit_cosmic_velocity_second_short),
        NormalUnit(  UnitID.cosmic_velocity_third,       BigDecimal("60012000000"),         UnitGroup.SPEED,    R.string.unit_cosmic_velocity_third,        R.string.unit_cosmic_velocity_third_short),
        NormalUnit(  UnitID.earths_orbital_speed,        BigDecimal("107154000000"),        UnitGroup.SPEED,    R.string.unit_earths_orbital_speed,         R.string.unit_earths_orbital_speed_short),
        NormalUnit(  UnitID.mach,                        BigDecimal("1236960000"),          UnitGroup.SPEED,    R.string.unit_mach,                         R.string.unit_mach_short),
        NormalUnit(  UnitID.mach_si_standard,            BigDecimal("1062167040"),          UnitGroup.SPEED,    R.string.unit_mach_si_standard,             R.string.unit_mach_si_standard_short),
    )
}
