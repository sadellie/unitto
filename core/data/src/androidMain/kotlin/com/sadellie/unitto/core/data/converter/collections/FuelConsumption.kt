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
import unitto.core.common.generated.resources.unit_gallon_uk_per_100_mile
import unitto.core.common.generated.resources.unit_gallon_uk_per_100_mile_short
import unitto.core.common.generated.resources.unit_gallon_uk_per_mile
import unitto.core.common.generated.resources.unit_gallon_uk_per_mile_short
import unitto.core.common.generated.resources.unit_gallon_us_per_100_mile
import unitto.core.common.generated.resources.unit_gallon_us_per_100_mile_short
import unitto.core.common.generated.resources.unit_gallon_us_per_mile
import unitto.core.common.generated.resources.unit_gallon_us_per_mile_short
import unitto.core.common.generated.resources.unit_km_per_l
import unitto.core.common.generated.resources.unit_km_per_l_short
import unitto.core.common.generated.resources.unit_l_per_100_km
import unitto.core.common.generated.resources.unit_l_per_100_km_short
import unitto.core.common.generated.resources.unit_l_per_km
import unitto.core.common.generated.resources.unit_l_per_km_short
import unitto.core.common.generated.resources.unit_mi_per_gallon_uk
import unitto.core.common.generated.resources.unit_mi_per_gallon_uk_short
import unitto.core.common.generated.resources.unit_mi_per_gallon_us
import unitto.core.common.generated.resources.unit_mi_per_gallon_us_short
import unitto.core.common.generated.resources.unit_mi_us_per_l
import unitto.core.common.generated.resources.unit_mi_us_per_l_short

val fuelConsumptionCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.kilometer_per_liter,
      KBigDecimal("1"),
      UnitGroup.FUEL_CONSUMPTION,
      Res.string.unit_km_per_l,
      Res.string.unit_km_per_l_short,
    ),
    NormalUnit(
      UnitID.liter_per_kilometer,
      KBigDecimal("1"),
      UnitGroup.FUEL_CONSUMPTION,
      Res.string.unit_l_per_km,
      Res.string.unit_l_per_km_short,
      true,
    ),
    NormalUnit(
      UnitID.liter_per_100_kilometer,
      KBigDecimal("100"),
      UnitGroup.FUEL_CONSUMPTION,
      Res.string.unit_l_per_100_km,
      Res.string.unit_l_per_100_km_short,
      true,
    ),
    NormalUnit(
      UnitID.mile_per_gallon_uk,
      KBigDecimal("0.35400619"),
      UnitGroup.FUEL_CONSUMPTION,
      Res.string.unit_mi_per_gallon_uk,
      Res.string.unit_mi_per_gallon_uk_short,
    ),
    NormalUnit(
      UnitID.mile_per_gallon_us,
      KBigDecimal("0.4251437075"),
      UnitGroup.FUEL_CONSUMPTION,
      Res.string.unit_mi_per_gallon_us,
      Res.string.unit_mi_per_gallon_us_short,
    ),
    NormalUnit(
      UnitID.mile_us_per_liter,
      KBigDecimal("1.609344"),
      UnitGroup.FUEL_CONSUMPTION,
      Res.string.unit_mi_us_per_l,
      Res.string.unit_mi_us_per_l_short,
    ),
    NormalUnit(
      UnitID.gallon_us_per_mile,
      KBigDecimal("0.4251437075"),
      UnitGroup.FUEL_CONSUMPTION,
      Res.string.unit_gallon_us_per_mile,
      Res.string.unit_gallon_us_per_mile_short,
      true,
    ),
    NormalUnit(
      UnitID.gallon_uk_per_mile,
      KBigDecimal("0.35400619"),
      UnitGroup.FUEL_CONSUMPTION,
      Res.string.unit_gallon_uk_per_mile,
      Res.string.unit_gallon_uk_per_mile_short,
      true,
    ),
    NormalUnit(
      UnitID.gallon_us_per_100_mile,
      KBigDecimal("42.51437075"),
      UnitGroup.FUEL_CONSUMPTION,
      Res.string.unit_gallon_us_per_100_mile,
      Res.string.unit_gallon_us_per_100_mile_short,
      true,
    ),
    NormalUnit(
      UnitID.gallon_uk_per_100_mile,
      KBigDecimal("35.400618996"),
      UnitGroup.FUEL_CONSUMPTION,
      Res.string.unit_gallon_uk_per_100_mile,
      Res.string.unit_gallon_uk_per_100_mile_short,
      true,
    ),
  )
}
