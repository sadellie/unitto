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

import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.NormalUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import java.math.BigDecimal

internal val lengthCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(MyUnitIDS.attometer,                 BigDecimal.valueOf(1.0),                             UnitGroup.LENGTH, R.string.unit_attometer,                 R.string.unit_attometer_short),
        NormalUnit(MyUnitIDS.nanometer,                 BigDecimal.valueOf(1.0E+9),                          UnitGroup.LENGTH, R.string.unit_nanometer,                 R.string.unit_nanometer_short),
        NormalUnit(MyUnitIDS.micrometer,                BigDecimal.valueOf(1.0E+12),                         UnitGroup.LENGTH, R.string.unit_micrometer,                R.string.unit_micrometer_short),
        NormalUnit(MyUnitIDS.millimeter,                BigDecimal.valueOf(1.0E+15),                         UnitGroup.LENGTH, R.string.unit_millimeter,                R.string.unit_millimeter_short),
        NormalUnit(MyUnitIDS.centimeter,                BigDecimal.valueOf(1.0E+16),                         UnitGroup.LENGTH, R.string.unit_centimeter,                R.string.unit_centimeter_short),
        NormalUnit(MyUnitIDS.decimeter,                 BigDecimal.valueOf(1.0E+17),                         UnitGroup.LENGTH, R.string.unit_decimeter,                 R.string.unit_decimeter_short),
        NormalUnit(MyUnitIDS.meter,                     BigDecimal.valueOf(1.0E+18),                         UnitGroup.LENGTH, R.string.unit_meter,                     R.string.unit_meter_short),
        NormalUnit(MyUnitIDS.kilometer,                 BigDecimal.valueOf(1.0E+21),                         UnitGroup.LENGTH, R.string.unit_kilometer,                 R.string.unit_kilometer_short),
        NormalUnit(MyUnitIDS.nautical_mile,             BigDecimal.valueOf(1.852E+21),                       UnitGroup.LENGTH, R.string.unit_nautical_mile,             R.string.unit_nautical_mile_short),
        NormalUnit(MyUnitIDS.inch,                      BigDecimal.valueOf(25_400_000_000_000_000),          UnitGroup.LENGTH, R.string.unit_inch,                      R.string.unit_inch_short),
        NormalUnit(MyUnitIDS.foot,                      BigDecimal.valueOf(304_800_000_000_002_200),         UnitGroup.LENGTH, R.string.unit_foot,                      R.string.unit_foot_short),
        NormalUnit(MyUnitIDS.yard,                      BigDecimal.valueOf(914_400_000_000_006_400),         UnitGroup.LENGTH, R.string.unit_yard,                      R.string.unit_yard_short),
        NormalUnit(MyUnitIDS.mile,                      BigDecimal.valueOf(1_609_344_000_000_010_500_000.0), UnitGroup.LENGTH, R.string.unit_mile,                      R.string.unit_mile_short),
        NormalUnit(MyUnitIDS.light_year,                BigDecimal.valueOf(9.460730472E+33),                 UnitGroup.LENGTH, R.string.unit_light_year,                R.string.unit_light_year_short),
        NormalUnit(MyUnitIDS.parsec,                    BigDecimal.valueOf(3.08567758149136E+34),            UnitGroup.LENGTH, R.string.unit_parsec,                    R.string.unit_parsec_short),
        NormalUnit(MyUnitIDS.kiloparsec,                BigDecimal.valueOf(3.08567758149136E+37),            UnitGroup.LENGTH, R.string.unit_kiloparsec,                R.string.unit_kiloparsec_short),
        NormalUnit(MyUnitIDS.megaparsec,                BigDecimal.valueOf(3.08567758149136E+40),            UnitGroup.LENGTH, R.string.unit_megaparsec,                R.string.unit_megaparsec_short),
        NormalUnit(MyUnitIDS.mercury_equatorial_radius, BigDecimal.valueOf(2.4397E+24),                      UnitGroup.LENGTH, R.string.unit_mercury_equatorial_radius, R.string.unit_mercury_equatorial_radius_short),
        NormalUnit(MyUnitIDS.venus_equatorial_radius,   BigDecimal.valueOf(6.0518E+24),                      UnitGroup.LENGTH, R.string.unit_venus_equatorial_radius,   R.string.unit_venus_equatorial_radius_short),
        NormalUnit(MyUnitIDS.earth_equatorial_radius,   BigDecimal.valueOf(6.371E+24),                       UnitGroup.LENGTH, R.string.unit_earth_equatorial_radius,   R.string.unit_earth_equatorial_radius_short),
        NormalUnit(MyUnitIDS.mars_equatorial_radius,    BigDecimal.valueOf(3.3895E+24),                      UnitGroup.LENGTH, R.string.unit_mars_equatorial_radius,    R.string.unit_mars_equatorial_radius_short),
        NormalUnit(MyUnitIDS.jupiter_equatorial_radius, BigDecimal.valueOf(6.9911E+25),                      UnitGroup.LENGTH, R.string.unit_jupiter_equatorial_radius, R.string.unit_jupiter_equatorial_radius_short),
        NormalUnit(MyUnitIDS.saturn_equatorial_radius,  BigDecimal.valueOf(5.8232E+25),                      UnitGroup.LENGTH, R.string.unit_saturn_equatorial_radius,  R.string.unit_saturn_equatorial_radius_short),
        NormalUnit(MyUnitIDS.uranus_equatorial_radius,  BigDecimal.valueOf(2.5362E+25),                      UnitGroup.LENGTH, R.string.unit_uranus_equatorial_radius,  R.string.unit_uranus_equatorial_radius_short),
        NormalUnit(MyUnitIDS.neptune_equatorial_radius, BigDecimal.valueOf(2.4622E+25),                      UnitGroup.LENGTH, R.string.unit_neptune_equatorial_radius, R.string.unit_neptune_equatorial_radius_short),
        NormalUnit(MyUnitIDS.sun_equatorial_radius,     BigDecimal.valueOf(6.95508E+26),                     UnitGroup.LENGTH, R.string.unit_sun_equatorial_radius,     R.string.unit_sun_equatorial_radius_short),
    )
}
