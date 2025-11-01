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
import unitto.core.common.generated.resources.unit_acre
import unitto.core.common.generated.resources.unit_acre_short
import unitto.core.common.generated.resources.unit_are
import unitto.core.common.generated.resources.unit_are_short
import unitto.core.common.generated.resources.unit_cent
import unitto.core.common.generated.resources.unit_cent_short
import unitto.core.common.generated.resources.unit_electron_cross_section
import unitto.core.common.generated.resources.unit_electron_cross_section_short
import unitto.core.common.generated.resources.unit_hectare
import unitto.core.common.generated.resources.unit_hectare_short
import unitto.core.common.generated.resources.unit_square_centimeter
import unitto.core.common.generated.resources.unit_square_centimeter_short
import unitto.core.common.generated.resources.unit_square_decimeter
import unitto.core.common.generated.resources.unit_square_decimeter_short
import unitto.core.common.generated.resources.unit_square_foot
import unitto.core.common.generated.resources.unit_square_foot_short
import unitto.core.common.generated.resources.unit_square_inch
import unitto.core.common.generated.resources.unit_square_inch_short
import unitto.core.common.generated.resources.unit_square_kilometer
import unitto.core.common.generated.resources.unit_square_kilometer_short
import unitto.core.common.generated.resources.unit_square_meter
import unitto.core.common.generated.resources.unit_square_meter_short
import unitto.core.common.generated.resources.unit_square_micrometer
import unitto.core.common.generated.resources.unit_square_micrometer_short
import unitto.core.common.generated.resources.unit_square_mile
import unitto.core.common.generated.resources.unit_square_mile_short
import unitto.core.common.generated.resources.unit_square_millimeter
import unitto.core.common.generated.resources.unit_square_millimeter_short
import unitto.core.common.generated.resources.unit_square_yard
import unitto.core.common.generated.resources.unit_square_yard_short

internal val areaCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.cent,
      KBigDecimal("40468564223999.24"),
      UnitGroup.AREA,
      Res.string.unit_cent,
      Res.string.unit_cent_short,
    ),
    NormalUnit(
      UnitID.are,
      KBigDecimal("100000000000000"),
      UnitGroup.AREA,
      Res.string.unit_are,
      Res.string.unit_are_short,
    ),
    NormalUnit(
      UnitID.acre,
      KBigDecimal("4046856422399924"),
      UnitGroup.AREA,
      Res.string.unit_acre,
      Res.string.unit_acre_short,
    ),
    NormalUnit(
      UnitID.hectare,
      KBigDecimal("10000000000000000"),
      UnitGroup.AREA,
      Res.string.unit_hectare,
      Res.string.unit_hectare_short,
    ),
    NormalUnit(
      UnitID.square_foot,
      KBigDecimal("92903040000"),
      UnitGroup.AREA,
      Res.string.unit_square_foot,
      Res.string.unit_square_foot_short,
    ),
    NormalUnit(
      UnitID.square_mile,
      KBigDecimal("2589988110336000000"),
      UnitGroup.AREA,
      Res.string.unit_square_mile,
      Res.string.unit_square_mile_short,
    ),
    NormalUnit(
      UnitID.square_yard,
      KBigDecimal("836127360000"),
      UnitGroup.AREA,
      Res.string.unit_square_yard,
      Res.string.unit_square_yard_short,
    ),
    NormalUnit(
      UnitID.square_inch,
      KBigDecimal("645160000"),
      UnitGroup.AREA,
      Res.string.unit_square_inch,
      Res.string.unit_square_inch_short,
    ),
    NormalUnit(
      UnitID.square_micrometer,
      KBigDecimal("1"),
      UnitGroup.AREA,
      Res.string.unit_square_micrometer,
      Res.string.unit_square_micrometer_short,
    ),
    NormalUnit(
      UnitID.square_millimeter,
      KBigDecimal("1000000"),
      UnitGroup.AREA,
      Res.string.unit_square_millimeter,
      Res.string.unit_square_millimeter_short,
    ),
    NormalUnit(
      UnitID.square_centimeter,
      KBigDecimal("100000000"),
      UnitGroup.AREA,
      Res.string.unit_square_centimeter,
      Res.string.unit_square_centimeter_short,
    ),
    NormalUnit(
      UnitID.square_decimeter,
      KBigDecimal("10000000000"),
      UnitGroup.AREA,
      Res.string.unit_square_decimeter,
      Res.string.unit_square_decimeter_short,
    ),
    NormalUnit(
      UnitID.square_meter,
      KBigDecimal("1000000000000"),
      UnitGroup.AREA,
      Res.string.unit_square_meter,
      Res.string.unit_square_meter_short,
    ),
    NormalUnit(
      UnitID.square_kilometer,
      KBigDecimal("1000000000000000000"),
      UnitGroup.AREA,
      Res.string.unit_square_kilometer,
      Res.string.unit_square_kilometer_short,
    ),
    NormalUnit(
      UnitID.electron_cross_section,
      KBigDecimal("0.00000000000000006588"),
      UnitGroup.AREA,
      Res.string.unit_electron_cross_section,
      Res.string.unit_electron_cross_section_short,
    ),
  )
}
