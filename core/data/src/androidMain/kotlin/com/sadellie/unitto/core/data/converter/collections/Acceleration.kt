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
import unitto.core.common.generated.resources.unit_attometer_per_square_second
import unitto.core.common.generated.resources.unit_attometer_per_square_second_short
import unitto.core.common.generated.resources.unit_centimeter_per_square_second
import unitto.core.common.generated.resources.unit_centimeter_per_square_second_short
import unitto.core.common.generated.resources.unit_decimeter_per_square_second
import unitto.core.common.generated.resources.unit_decimeter_per_square_second_short
import unitto.core.common.generated.resources.unit_dekameter_per_square_second
import unitto.core.common.generated.resources.unit_dekameter_per_square_second_short
import unitto.core.common.generated.resources.unit_earth_surface_gravity
import unitto.core.common.generated.resources.unit_earth_surface_gravity_short
import unitto.core.common.generated.resources.unit_femtometer_per_square_second
import unitto.core.common.generated.resources.unit_femtometer_per_square_second_short
import unitto.core.common.generated.resources.unit_gal
import unitto.core.common.generated.resources.unit_gal_short
import unitto.core.common.generated.resources.unit_hectometer_per_square_second
import unitto.core.common.generated.resources.unit_hectometer_per_square_second_short
import unitto.core.common.generated.resources.unit_jupiter_surface_gravity
import unitto.core.common.generated.resources.unit_jupiter_surface_gravity_short
import unitto.core.common.generated.resources.unit_kilometer_per_square_second
import unitto.core.common.generated.resources.unit_kilometer_per_square_second_short
import unitto.core.common.generated.resources.unit_mars_surface_gravity
import unitto.core.common.generated.resources.unit_mars_surface_gravity_short
import unitto.core.common.generated.resources.unit_mercury_surface_gravity
import unitto.core.common.generated.resources.unit_mercury_surface_gravity_short
import unitto.core.common.generated.resources.unit_meter_per_square_second
import unitto.core.common.generated.resources.unit_meter_per_square_second_short
import unitto.core.common.generated.resources.unit_micrometer_per_square_second
import unitto.core.common.generated.resources.unit_micrometer_per_square_second_short
import unitto.core.common.generated.resources.unit_millimeter_per_square_second
import unitto.core.common.generated.resources.unit_millimeter_per_square_second_short
import unitto.core.common.generated.resources.unit_nanometer_per_square_second
import unitto.core.common.generated.resources.unit_nanometer_per_square_second_short
import unitto.core.common.generated.resources.unit_neptune_surface_gravity
import unitto.core.common.generated.resources.unit_neptune_surface_gravity_short
import unitto.core.common.generated.resources.unit_picometer_per_square_second
import unitto.core.common.generated.resources.unit_picometer_per_square_second_short
import unitto.core.common.generated.resources.unit_saturn_surface_gravity
import unitto.core.common.generated.resources.unit_saturn_surface_gravity_short
import unitto.core.common.generated.resources.unit_sun_surface_gravity
import unitto.core.common.generated.resources.unit_sun_surface_gravity_short
import unitto.core.common.generated.resources.unit_uranus_surface_gravity
import unitto.core.common.generated.resources.unit_uranus_surface_gravity_short
import unitto.core.common.generated.resources.unit_venus_surface_gravity
import unitto.core.common.generated.resources.unit_venus_surface_gravity_short

internal val accelerationCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.attometer_per_square_second,
      KBigDecimal("1"),
      UnitGroup.ACCELERATION,
      Res.string.unit_attometer_per_square_second,
      Res.string.unit_attometer_per_square_second_short,
    ),
    NormalUnit(
      UnitID.femtometer_per_square_second,
      KBigDecimal("1000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_femtometer_per_square_second,
      Res.string.unit_femtometer_per_square_second_short,
    ),
    NormalUnit(
      UnitID.picometer_per_square_second,
      KBigDecimal("1000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_picometer_per_square_second,
      Res.string.unit_picometer_per_square_second_short,
    ),
    NormalUnit(
      UnitID.nanometer_per_square_second,
      KBigDecimal("1000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_nanometer_per_square_second,
      Res.string.unit_nanometer_per_square_second_short,
    ),
    NormalUnit(
      UnitID.micrometer_per_square_second,
      KBigDecimal("1000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_micrometer_per_square_second,
      Res.string.unit_micrometer_per_square_second_short,
    ),
    NormalUnit(
      UnitID.millimeter_per_square_second,
      KBigDecimal("1000000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_millimeter_per_square_second,
      Res.string.unit_millimeter_per_square_second_short,
    ),
    NormalUnit(
      UnitID.centimeter_per_square_second,
      KBigDecimal("10000000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_centimeter_per_square_second,
      Res.string.unit_centimeter_per_square_second_short,
    ),
    NormalUnit(
      UnitID.decimeter_per_square_second,
      KBigDecimal("100000000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_decimeter_per_square_second,
      Res.string.unit_decimeter_per_square_second_short,
    ),
    NormalUnit(
      UnitID.meter_per_square_second,
      KBigDecimal("1000000000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_meter_per_square_second,
      Res.string.unit_meter_per_square_second_short,
    ),
    NormalUnit(
      UnitID.kilometer_per_square_second,
      KBigDecimal("1000000000000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_kilometer_per_square_second,
      Res.string.unit_kilometer_per_square_second_short,
    ),
    NormalUnit(
      UnitID.dekameter_per_square_second,
      KBigDecimal("10000000000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_dekameter_per_square_second,
      Res.string.unit_dekameter_per_square_second_short,
    ),
    NormalUnit(
      UnitID.hectometer_per_square_second,
      KBigDecimal("100000000000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_hectometer_per_square_second,
      Res.string.unit_hectometer_per_square_second_short,
    ),
    NormalUnit(
      UnitID.gal,
      KBigDecimal("10000000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_gal,
      Res.string.unit_gal_short,
    ),
    NormalUnit(
      UnitID.mercury_surface_gravity,
      KBigDecimal("3700000000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_mercury_surface_gravity,
      Res.string.unit_mercury_surface_gravity_short,
    ),
    NormalUnit(
      UnitID.venus_surface_gravity,
      KBigDecimal("8870000000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_venus_surface_gravity,
      Res.string.unit_venus_surface_gravity_short,
    ),
    NormalUnit(
      UnitID.earth_surface_gravity,
      KBigDecimal("9806550000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_earth_surface_gravity,
      Res.string.unit_earth_surface_gravity_short,
    ),
    NormalUnit(
      UnitID.mars_surface_gravity,
      KBigDecimal("3710000000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_mars_surface_gravity,
      Res.string.unit_mars_surface_gravity_short,
    ),
    NormalUnit(
      UnitID.jupiter_surface_gravity,
      KBigDecimal("24790000000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_jupiter_surface_gravity,
      Res.string.unit_jupiter_surface_gravity_short,
    ),
    NormalUnit(
      UnitID.saturn_surface_gravity,
      KBigDecimal("10440000000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_saturn_surface_gravity,
      Res.string.unit_saturn_surface_gravity_short,
    ),
    NormalUnit(
      UnitID.uranus_surface_gravity,
      KBigDecimal("8870000000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_uranus_surface_gravity,
      Res.string.unit_uranus_surface_gravity_short,
    ),
    NormalUnit(
      UnitID.neptune_surface_gravity,
      KBigDecimal("11150000000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_neptune_surface_gravity,
      Res.string.unit_neptune_surface_gravity_short,
    ),
    NormalUnit(
      UnitID.sun_surface_gravity,
      KBigDecimal("274000000000000000000"),
      UnitGroup.ACCELERATION,
      Res.string.unit_sun_surface_gravity,
      Res.string.unit_sun_surface_gravity_short,
    ),
  )
}
