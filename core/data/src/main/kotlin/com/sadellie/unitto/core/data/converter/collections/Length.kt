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

package com.sadellie.unitto.core.data.converter.collections

import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import java.math.BigDecimal

internal val lengthCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.attometer,
      BigDecimal("1"),
      UnitGroup.LENGTH,
      R.string.unit_attometer,
      R.string.unit_attometer_short,
    ),
    NormalUnit(
      UnitID.nanometer,
      BigDecimal("1000000000"),
      UnitGroup.LENGTH,
      R.string.unit_nanometer,
      R.string.unit_nanometer_short,
    ),
    NormalUnit(
      UnitID.micrometer,
      BigDecimal("1000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_micrometer,
      R.string.unit_micrometer_short,
    ),
    NormalUnit(
      UnitID.millimeter,
      BigDecimal("1000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_millimeter,
      R.string.unit_millimeter_short,
    ),
    NormalUnit(
      UnitID.centimeter,
      BigDecimal("10000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_centimeter,
      R.string.unit_centimeter_short,
    ),
    NormalUnit(
      UnitID.decimeter,
      BigDecimal("100000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_decimeter,
      R.string.unit_decimeter_short,
    ),
    NormalUnit(
      UnitID.meter,
      BigDecimal("1000000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_meter,
      R.string.unit_meter_short,
    ),
    NormalUnit(
      UnitID.kilometer,
      BigDecimal("1000000000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_kilometer,
      R.string.unit_kilometer_short,
    ),
    NormalUnit(
      UnitID.nautical_mile,
      BigDecimal("1852000000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_nautical_mile,
      R.string.unit_nautical_mile_short,
    ),
    NormalUnit(
      UnitID.inch,
      BigDecimal("25400000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_inch,
      R.string.unit_inch_short,
    ),
    NormalUnit(
      UnitID.foot,
      BigDecimal("304800000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_foot,
      R.string.unit_foot_short,
    ),
    NormalUnit(
      UnitID.yard,
      BigDecimal("914400000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_yard,
      R.string.unit_yard_short,
    ),
    NormalUnit(
      UnitID.mile,
      BigDecimal("1609344000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_mile,
      R.string.unit_mile_short,
    ),
    NormalUnit(
      UnitID.light_year,
      BigDecimal("9460730472000000000000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_light_year,
      R.string.unit_light_year_short,
    ),
    NormalUnit(
      UnitID.parsec,
      BigDecimal("30856775814913600000000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_parsec,
      R.string.unit_parsec_short,
    ),
    NormalUnit(
      UnitID.kiloparsec,
      BigDecimal("30856775814913600000000000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_kiloparsec,
      R.string.unit_kiloparsec_short,
    ),
    NormalUnit(
      UnitID.megaparsec,
      BigDecimal("30856775814913600000000000000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_megaparsec,
      R.string.unit_megaparsec_short,
    ),
    NormalUnit(
      UnitID.mercury_equatorial_radius,
      BigDecimal("2439700000000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_mercury_equatorial_radius,
      R.string.unit_mercury_equatorial_radius_short,
    ),
    NormalUnit(
      UnitID.venus_equatorial_radius,
      BigDecimal("6051800000000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_venus_equatorial_radius,
      R.string.unit_venus_equatorial_radius_short,
    ),
    NormalUnit(
      UnitID.earth_equatorial_radius,
      BigDecimal("6371000000000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_earth_equatorial_radius,
      R.string.unit_earth_equatorial_radius_short,
    ),
    NormalUnit(
      UnitID.mars_equatorial_radius,
      BigDecimal("3389500000000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_mars_equatorial_radius,
      R.string.unit_mars_equatorial_radius_short,
    ),
    NormalUnit(
      UnitID.jupiter_equatorial_radius,
      BigDecimal("69911000000000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_jupiter_equatorial_radius,
      R.string.unit_jupiter_equatorial_radius_short,
    ),
    NormalUnit(
      UnitID.saturn_equatorial_radius,
      BigDecimal("58232000000000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_saturn_equatorial_radius,
      R.string.unit_saturn_equatorial_radius_short,
    ),
    NormalUnit(
      UnitID.uranus_equatorial_radius,
      BigDecimal("25362000000000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_uranus_equatorial_radius,
      R.string.unit_uranus_equatorial_radius_short,
    ),
    NormalUnit(
      UnitID.neptune_equatorial_radius,
      BigDecimal("24622000000000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_neptune_equatorial_radius,
      R.string.unit_neptune_equatorial_radius_short,
    ),
    NormalUnit(
      UnitID.sun_equatorial_radius,
      BigDecimal("695508000000000000000000000"),
      UnitGroup.LENGTH,
      R.string.unit_sun_equatorial_radius,
      R.string.unit_sun_equatorial_radius_short,
    ),
  )
}
