/*
 * Unitto is a unit converter for Android
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
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.NormalUnit
import com.sadellie.unitto.data.converter.UnitID
import java.math.BigDecimal

internal val lengthCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(UnitID.attometer,                 BigDecimal.valueOf(1.0),                             UnitGroup.LENGTH, R.string.unit_attometer,                 R.string.unit_attometer_short),
        NormalUnit(UnitID.nanometer,                 BigDecimal.valueOf(1.0E+9),                          UnitGroup.LENGTH, R.string.unit_nanometer,                 R.string.unit_nanometer_short),
        NormalUnit(UnitID.micrometer,                BigDecimal.valueOf(1.0E+12),                         UnitGroup.LENGTH, R.string.unit_micrometer,                R.string.unit_micrometer_short),
        NormalUnit(UnitID.millimeter,                BigDecimal.valueOf(1.0E+15),                         UnitGroup.LENGTH, R.string.unit_millimeter,                R.string.unit_millimeter_short),
        NormalUnit(UnitID.centimeter,                BigDecimal.valueOf(1.0E+16),                         UnitGroup.LENGTH, R.string.unit_centimeter,                R.string.unit_centimeter_short),
        NormalUnit(UnitID.decimeter,                 BigDecimal.valueOf(1.0E+17),                         UnitGroup.LENGTH, R.string.unit_decimeter,                 R.string.unit_decimeter_short),
        NormalUnit(UnitID.meter,                     BigDecimal.valueOf(1.0E+18),                         UnitGroup.LENGTH, R.string.unit_meter,                     R.string.unit_meter_short),
        NormalUnit(UnitID.kilometer,                 BigDecimal.valueOf(1.0E+21),                         UnitGroup.LENGTH, R.string.unit_kilometer,                 R.string.unit_kilometer_short),
        NormalUnit(UnitID.nautical_mile,             BigDecimal.valueOf(1.852E+21),                       UnitGroup.LENGTH, R.string.unit_nautical_mile,             R.string.unit_nautical_mile_short),
        NormalUnit(UnitID.inch,                      BigDecimal.valueOf(25_400_000_000_000_000),          UnitGroup.LENGTH, R.string.unit_inch,                      R.string.unit_inch_short),
        NormalUnit(UnitID.foot,                      BigDecimal.valueOf(304_800_000_000_002_200),         UnitGroup.LENGTH, R.string.unit_foot,                      R.string.unit_foot_short),
        NormalUnit(UnitID.yard,                      BigDecimal.valueOf(914_400_000_000_006_400),         UnitGroup.LENGTH, R.string.unit_yard,                      R.string.unit_yard_short),
        NormalUnit(UnitID.mile,                      BigDecimal.valueOf(1_609_344_000_000_010_500_000.0), UnitGroup.LENGTH, R.string.unit_mile,                      R.string.unit_mile_short),
        NormalUnit(UnitID.light_year,                BigDecimal.valueOf(9.460730472E+33),                 UnitGroup.LENGTH, R.string.unit_light_year,                R.string.unit_light_year_short),
        NormalUnit(UnitID.parsec,                    BigDecimal.valueOf(3.08567758149136E+34),            UnitGroup.LENGTH, R.string.unit_parsec,                    R.string.unit_parsec_short),
        NormalUnit(UnitID.kiloparsec,                BigDecimal.valueOf(3.08567758149136E+37),            UnitGroup.LENGTH, R.string.unit_kiloparsec,                R.string.unit_kiloparsec_short),
        NormalUnit(UnitID.megaparsec,                BigDecimal.valueOf(3.08567758149136E+40),            UnitGroup.LENGTH, R.string.unit_megaparsec,                R.string.unit_megaparsec_short),
        NormalUnit(UnitID.mercury_equatorial_radius, BigDecimal.valueOf(2.4397E+24),                      UnitGroup.LENGTH, R.string.unit_mercury_equatorial_radius, R.string.unit_mercury_equatorial_radius_short),
        NormalUnit(UnitID.venus_equatorial_radius,   BigDecimal.valueOf(6.0518E+24),                      UnitGroup.LENGTH, R.string.unit_venus_equatorial_radius,   R.string.unit_venus_equatorial_radius_short),
        NormalUnit(UnitID.earth_equatorial_radius,   BigDecimal.valueOf(6.371E+24),                       UnitGroup.LENGTH, R.string.unit_earth_equatorial_radius,   R.string.unit_earth_equatorial_radius_short),
        NormalUnit(UnitID.mars_equatorial_radius,    BigDecimal.valueOf(3.3895E+24),                      UnitGroup.LENGTH, R.string.unit_mars_equatorial_radius,    R.string.unit_mars_equatorial_radius_short),
        NormalUnit(UnitID.jupiter_equatorial_radius, BigDecimal.valueOf(6.9911E+25),                      UnitGroup.LENGTH, R.string.unit_jupiter_equatorial_radius, R.string.unit_jupiter_equatorial_radius_short),
        NormalUnit(UnitID.saturn_equatorial_radius,  BigDecimal.valueOf(5.8232E+25),                      UnitGroup.LENGTH, R.string.unit_saturn_equatorial_radius,  R.string.unit_saturn_equatorial_radius_short),
        NormalUnit(UnitID.uranus_equatorial_radius,  BigDecimal.valueOf(2.5362E+25),                      UnitGroup.LENGTH, R.string.unit_uranus_equatorial_radius,  R.string.unit_uranus_equatorial_radius_short),
        NormalUnit(UnitID.neptune_equatorial_radius, BigDecimal.valueOf(2.4622E+25),                      UnitGroup.LENGTH, R.string.unit_neptune_equatorial_radius, R.string.unit_neptune_equatorial_radius_short),
        NormalUnit(UnitID.sun_equatorial_radius,     BigDecimal.valueOf(6.95508E+26),                     UnitGroup.LENGTH, R.string.unit_sun_equatorial_radius,     R.string.unit_sun_equatorial_radius_short),
    )
}
