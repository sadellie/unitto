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

internal val areaCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.cent,
      BigDecimal("40468564223999.24"),
      UnitGroup.AREA,
      R.string.unit_cent,
      R.string.unit_cent_short,
    ),
    NormalUnit(
      UnitID.are,
      BigDecimal("100000000000000"),
      UnitGroup.AREA,
      R.string.unit_are,
      R.string.unit_are_short,
    ),
    NormalUnit(
      UnitID.acre,
      BigDecimal("4046856422399924"),
      UnitGroup.AREA,
      R.string.unit_acre,
      R.string.unit_acre_short,
    ),
    NormalUnit(
      UnitID.hectare,
      BigDecimal("10000000000000000"),
      UnitGroup.AREA,
      R.string.unit_hectare,
      R.string.unit_hectare_short,
    ),
    NormalUnit(
      UnitID.square_foot,
      BigDecimal("92903040000"),
      UnitGroup.AREA,
      R.string.unit_square_foot,
      R.string.unit_square_foot_short,
    ),
    NormalUnit(
      UnitID.square_mile,
      BigDecimal("2589988110336000000"),
      UnitGroup.AREA,
      R.string.unit_square_mile,
      R.string.unit_square_mile_short,
    ),
    NormalUnit(
      UnitID.square_yard,
      BigDecimal("836127360000"),
      UnitGroup.AREA,
      R.string.unit_square_yard,
      R.string.unit_square_yard_short,
    ),
    NormalUnit(
      UnitID.square_inch,
      BigDecimal("645160000"),
      UnitGroup.AREA,
      R.string.unit_square_inch,
      R.string.unit_square_inch_short,
    ),
    NormalUnit(
      UnitID.square_micrometer,
      BigDecimal("1"),
      UnitGroup.AREA,
      R.string.unit_square_micrometer,
      R.string.unit_square_micrometer_short,
    ),
    NormalUnit(
      UnitID.square_millimeter,
      BigDecimal("1000000"),
      UnitGroup.AREA,
      R.string.unit_square_millimeter,
      R.string.unit_square_millimeter_short,
    ),
    NormalUnit(
      UnitID.square_centimeter,
      BigDecimal("100000000"),
      UnitGroup.AREA,
      R.string.unit_square_centimeter,
      R.string.unit_square_centimeter_short,
    ),
    NormalUnit(
      UnitID.square_decimeter,
      BigDecimal("10000000000"),
      UnitGroup.AREA,
      R.string.unit_square_decimeter,
      R.string.unit_square_decimeter_short,
    ),
    NormalUnit(
      UnitID.square_meter,
      BigDecimal("1000000000000"),
      UnitGroup.AREA,
      R.string.unit_square_meter,
      R.string.unit_square_meter_short,
    ),
    NormalUnit(
      UnitID.square_kilometer,
      BigDecimal("1000000000000000000"),
      UnitGroup.AREA,
      R.string.unit_square_kilometer,
      R.string.unit_square_kilometer_short,
    ),
    NormalUnit(
      UnitID.electron_cross_section,
      BigDecimal("0.00000000000000006588"),
      UnitGroup.AREA,
      R.string.unit_electron_cross_section,
      R.string.unit_electron_cross_section_short,
    ),
  )
}
