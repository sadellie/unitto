/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.data.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import java.math.BigDecimal

internal val lengthCollection: List<AbstractUnit> by lazy {
    listOf(
        MyUnit(MyUnitIDS.attometer,                 BigDecimal.valueOf(1.0),                             UnitGroup.LENGTH, R.string.attometer,                 R.string.attometer_short),
        MyUnit(MyUnitIDS.nanometer,                 BigDecimal.valueOf(1.0E+9),                          UnitGroup.LENGTH, R.string.nanometer,                 R.string.nanometer_short),
        MyUnit(MyUnitIDS.micrometer,                BigDecimal.valueOf(1.0E+12),                         UnitGroup.LENGTH, R.string.micrometer,                R.string.micrometer_short),
        MyUnit(MyUnitIDS.millimeter,                BigDecimal.valueOf(1.0E+15),                         UnitGroup.LENGTH, R.string.millimeter,                R.string.millimeter_short),
        MyUnit(MyUnitIDS.centimeter,                BigDecimal.valueOf(1.0E+16),                         UnitGroup.LENGTH, R.string.centimeter,                R.string.centimeter_short),
        MyUnit(MyUnitIDS.decimeter,                 BigDecimal.valueOf(1.0E+17),                         UnitGroup.LENGTH, R.string.decimeter,                 R.string.decimeter_short),
        MyUnit(MyUnitIDS.meter,                     BigDecimal.valueOf(1.0E+18),                         UnitGroup.LENGTH, R.string.meter,                     R.string.meter_short),
        MyUnit(MyUnitIDS.kilometer,                 BigDecimal.valueOf(1.0E+21),                         UnitGroup.LENGTH, R.string.kilometer,                 R.string.kilometer_short),
        MyUnit(MyUnitIDS.inch,                      BigDecimal.valueOf(25_400_000_000_000_000),          UnitGroup.LENGTH, R.string.inch,                      R.string.inch_short),
        MyUnit(MyUnitIDS.foot,                      BigDecimal.valueOf(304_800_000_000_002_200),         UnitGroup.LENGTH, R.string.foot,                      R.string.foot_short),
        MyUnit(MyUnitIDS.yard,                      BigDecimal.valueOf(914_400_000_000_006_400),         UnitGroup.LENGTH, R.string.yard,                      R.string.yard_short),
        MyUnit(MyUnitIDS.mile,                      BigDecimal.valueOf(1_609_344_000_000_010_500_000.0), UnitGroup.LENGTH, R.string.mile,                      R.string.mile_short),
        MyUnit(MyUnitIDS.light_year,                BigDecimal.valueOf(9.460730472E+33),                 UnitGroup.LENGTH, R.string.light_year,                R.string.light_year_short),
        MyUnit(MyUnitIDS.parsec,                    BigDecimal.valueOf(3.08567758149136E+34),            UnitGroup.LENGTH, R.string.parsec,                    R.string.parsec_short),
        MyUnit(MyUnitIDS.kiloparsec,                BigDecimal.valueOf(3.08567758149136E+37),            UnitGroup.LENGTH, R.string.kiloparsec,                R.string.kiloparsec_short),
        MyUnit(MyUnitIDS.megaparsec,                BigDecimal.valueOf(3.08567758149136E+40),            UnitGroup.LENGTH, R.string.megaparsec,                R.string.megaparsec_short),
        MyUnit(MyUnitIDS.mercury_equatorial_radius, BigDecimal.valueOf(2.4397E+24),                      UnitGroup.LENGTH, R.string.mercury_equatorial_radius, R.string.mercury_equatorial_radius_short),
        MyUnit(MyUnitIDS.venus_equatorial_radius,   BigDecimal.valueOf(6.0518E+24),                      UnitGroup.LENGTH, R.string.venus_equatorial_radius,   R.string.venus_equatorial_radius_short),
        MyUnit(MyUnitIDS.earth_equatorial_radius,   BigDecimal.valueOf(6.371E+24),                       UnitGroup.LENGTH, R.string.earth_equatorial_radius,   R.string.earth_equatorial_radius_short),
        MyUnit(MyUnitIDS.mars_equatorial_radius,    BigDecimal.valueOf(3.3895E+24),                      UnitGroup.LENGTH, R.string.mars_equatorial_radius,    R.string.mars_equatorial_radius_short),
        MyUnit(MyUnitIDS.jupiter_equatorial_radius, BigDecimal.valueOf(6.9911E+25),                      UnitGroup.LENGTH, R.string.jupiter_equatorial_radius, R.string.jupiter_equatorial_radius_short),
        MyUnit(MyUnitIDS.saturn_equatorial_radius,  BigDecimal.valueOf(5.8232E+25),                      UnitGroup.LENGTH, R.string.saturn_equatorial_radius,  R.string.saturn_equatorial_radius_short),
        MyUnit(MyUnitIDS.uranus_equatorial_radius,  BigDecimal.valueOf(2.5362E+25),                      UnitGroup.LENGTH, R.string.uranus_equatorial_radius,  R.string.uranus_equatorial_radius_short),
        MyUnit(MyUnitIDS.neptune_equatorial_radius, BigDecimal.valueOf(2.4622E+25),                      UnitGroup.LENGTH, R.string.neptune_equatorial_radius, R.string.neptune_equatorial_radius_short),
        MyUnit(MyUnitIDS.sun_equatorial_radius,     BigDecimal.valueOf(6.95508E+26),                     UnitGroup.LENGTH, R.string.sun_equatorial_radius,     R.string.sun_equatorial_radius_short),
    )
}