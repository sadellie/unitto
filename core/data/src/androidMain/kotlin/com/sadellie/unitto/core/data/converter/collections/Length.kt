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
import unitto.core.common.generated.resources.unit_attometer
import unitto.core.common.generated.resources.unit_attometer_short
import unitto.core.common.generated.resources.unit_centimeter
import unitto.core.common.generated.resources.unit_centimeter_short
import unitto.core.common.generated.resources.unit_decimeter
import unitto.core.common.generated.resources.unit_decimeter_short
import unitto.core.common.generated.resources.unit_earth_equatorial_radius
import unitto.core.common.generated.resources.unit_earth_equatorial_radius_short
import unitto.core.common.generated.resources.unit_foot
import unitto.core.common.generated.resources.unit_foot_short
import unitto.core.common.generated.resources.unit_inch
import unitto.core.common.generated.resources.unit_inch_short
import unitto.core.common.generated.resources.unit_jupiter_equatorial_radius
import unitto.core.common.generated.resources.unit_jupiter_equatorial_radius_short
import unitto.core.common.generated.resources.unit_kilometer
import unitto.core.common.generated.resources.unit_kilometer_short
import unitto.core.common.generated.resources.unit_kiloparsec
import unitto.core.common.generated.resources.unit_kiloparsec_short
import unitto.core.common.generated.resources.unit_light_year
import unitto.core.common.generated.resources.unit_light_year_short
import unitto.core.common.generated.resources.unit_mars_equatorial_radius
import unitto.core.common.generated.resources.unit_mars_equatorial_radius_short
import unitto.core.common.generated.resources.unit_megaparsec
import unitto.core.common.generated.resources.unit_megaparsec_short
import unitto.core.common.generated.resources.unit_mercury_equatorial_radius
import unitto.core.common.generated.resources.unit_mercury_equatorial_radius_short
import unitto.core.common.generated.resources.unit_meter
import unitto.core.common.generated.resources.unit_meter_short
import unitto.core.common.generated.resources.unit_micrometer
import unitto.core.common.generated.resources.unit_micrometer_short
import unitto.core.common.generated.resources.unit_mile
import unitto.core.common.generated.resources.unit_mile_short
import unitto.core.common.generated.resources.unit_millimeter
import unitto.core.common.generated.resources.unit_millimeter_short
import unitto.core.common.generated.resources.unit_nanometer
import unitto.core.common.generated.resources.unit_nanometer_short
import unitto.core.common.generated.resources.unit_nautical_mile
import unitto.core.common.generated.resources.unit_nautical_mile_short
import unitto.core.common.generated.resources.unit_neptune_equatorial_radius
import unitto.core.common.generated.resources.unit_neptune_equatorial_radius_short
import unitto.core.common.generated.resources.unit_parsec
import unitto.core.common.generated.resources.unit_parsec_short
import unitto.core.common.generated.resources.unit_saturn_equatorial_radius
import unitto.core.common.generated.resources.unit_saturn_equatorial_radius_short
import unitto.core.common.generated.resources.unit_sun_equatorial_radius
import unitto.core.common.generated.resources.unit_sun_equatorial_radius_short
import unitto.core.common.generated.resources.unit_uranus_equatorial_radius
import unitto.core.common.generated.resources.unit_uranus_equatorial_radius_short
import unitto.core.common.generated.resources.unit_venus_equatorial_radius
import unitto.core.common.generated.resources.unit_venus_equatorial_radius_short
import unitto.core.common.generated.resources.unit_yard
import unitto.core.common.generated.resources.unit_yard_short

internal val lengthCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.attometer,
      KBigDecimal("1"),
      UnitGroup.LENGTH,
      Res.string.unit_attometer,
      Res.string.unit_attometer_short,
    ),
    NormalUnit(
      UnitID.nanometer,
      KBigDecimal("1000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_nanometer,
      Res.string.unit_nanometer_short,
    ),
    NormalUnit(
      UnitID.micrometer,
      KBigDecimal("1000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_micrometer,
      Res.string.unit_micrometer_short,
    ),
    NormalUnit(
      UnitID.millimeter,
      KBigDecimal("1000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_millimeter,
      Res.string.unit_millimeter_short,
    ),
    NormalUnit(
      UnitID.centimeter,
      KBigDecimal("10000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_centimeter,
      Res.string.unit_centimeter_short,
    ),
    NormalUnit(
      UnitID.decimeter,
      KBigDecimal("100000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_decimeter,
      Res.string.unit_decimeter_short,
    ),
    NormalUnit(
      UnitID.meter,
      KBigDecimal("1000000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_meter,
      Res.string.unit_meter_short,
    ),
    NormalUnit(
      UnitID.kilometer,
      KBigDecimal("1000000000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_kilometer,
      Res.string.unit_kilometer_short,
    ),
    NormalUnit(
      UnitID.nautical_mile,
      KBigDecimal("1852000000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_nautical_mile,
      Res.string.unit_nautical_mile_short,
    ),
    NormalUnit(
      UnitID.inch,
      KBigDecimal("25400000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_inch,
      Res.string.unit_inch_short,
    ),
    NormalUnit(
      UnitID.foot,
      KBigDecimal("304800000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_foot,
      Res.string.unit_foot_short,
    ),
    NormalUnit(
      UnitID.yard,
      KBigDecimal("914400000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_yard,
      Res.string.unit_yard_short,
    ),
    NormalUnit(
      UnitID.mile,
      KBigDecimal("1609344000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_mile,
      Res.string.unit_mile_short,
    ),
    NormalUnit(
      UnitID.light_year,
      KBigDecimal("9460730472000000000000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_light_year,
      Res.string.unit_light_year_short,
    ),
    NormalUnit(
      UnitID.parsec,
      KBigDecimal("30856775814913600000000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_parsec,
      Res.string.unit_parsec_short,
    ),
    NormalUnit(
      UnitID.kiloparsec,
      KBigDecimal("30856775814913600000000000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_kiloparsec,
      Res.string.unit_kiloparsec_short,
    ),
    NormalUnit(
      UnitID.megaparsec,
      KBigDecimal("30856775814913600000000000000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_megaparsec,
      Res.string.unit_megaparsec_short,
    ),
    NormalUnit(
      UnitID.mercury_equatorial_radius,
      KBigDecimal("2439700000000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_mercury_equatorial_radius,
      Res.string.unit_mercury_equatorial_radius_short,
    ),
    NormalUnit(
      UnitID.venus_equatorial_radius,
      KBigDecimal("6051800000000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_venus_equatorial_radius,
      Res.string.unit_venus_equatorial_radius_short,
    ),
    NormalUnit(
      UnitID.earth_equatorial_radius,
      KBigDecimal("6371000000000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_earth_equatorial_radius,
      Res.string.unit_earth_equatorial_radius_short,
    ),
    NormalUnit(
      UnitID.mars_equatorial_radius,
      KBigDecimal("3389500000000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_mars_equatorial_radius,
      Res.string.unit_mars_equatorial_radius_short,
    ),
    NormalUnit(
      UnitID.jupiter_equatorial_radius,
      KBigDecimal("69911000000000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_jupiter_equatorial_radius,
      Res.string.unit_jupiter_equatorial_radius_short,
    ),
    NormalUnit(
      UnitID.saturn_equatorial_radius,
      KBigDecimal("58232000000000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_saturn_equatorial_radius,
      Res.string.unit_saturn_equatorial_radius_short,
    ),
    NormalUnit(
      UnitID.uranus_equatorial_radius,
      KBigDecimal("25362000000000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_uranus_equatorial_radius,
      Res.string.unit_uranus_equatorial_radius_short,
    ),
    NormalUnit(
      UnitID.neptune_equatorial_radius,
      KBigDecimal("24622000000000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_neptune_equatorial_radius,
      Res.string.unit_neptune_equatorial_radius_short,
    ),
    NormalUnit(
      UnitID.sun_equatorial_radius,
      KBigDecimal("695508000000000000000000000"),
      UnitGroup.LENGTH,
      Res.string.unit_sun_equatorial_radius,
      Res.string.unit_sun_equatorial_radius_short,
    ),
  )
}
