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

val luminanceCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.candela_per_square_meter,
      BigDecimal("31415926.5359"),
      UnitGroup.LUMINANCE,
      R.string.unit_candela_per_square_meter,
      R.string.unit_candela_per_square_meter_short,
    ),
    NormalUnit(
      UnitID.candela_per_square_centimeter,
      BigDecimal("314159265359"),
      UnitGroup.LUMINANCE,
      R.string.unit_candela_per_square_centimeter,
      R.string.unit_candela_per_square_centimeter_short,
    ),
    NormalUnit(
      UnitID.candela_per_square_foot,
      BigDecimal("338158218.89"),
      UnitGroup.LUMINANCE,
      R.string.unit_candela_per_square_foot,
      R.string.unit_candela_per_square_foot_short,
    ),
    NormalUnit(
      UnitID.candela_per_square_inch,
      BigDecimal("48694783520"),
      UnitGroup.LUMINANCE,
      R.string.unit_candela_per_square_inch,
      R.string.unit_candela_per_square_inch_short,
    ),
    NormalUnit(
      UnitID.kilocandela_per_square_meter,
      BigDecimal("31415926535.9"),
      UnitGroup.LUMINANCE,
      R.string.unit_kilocandela_per_square_meter,
      R.string.unit_kilocandela_per_square_meter_short,
    ),
    NormalUnit(
      UnitID.stilb,
      BigDecimal("314159265359"),
      UnitGroup.LUMINANCE,
      R.string.unit_stilb,
      R.string.unit_stilb_short,
    ),
    NormalUnit(
      UnitID.lumen_per_square_meter_per_steradian,
      BigDecimal("31415926.5359"),
      UnitGroup.LUMINANCE,
      R.string.unit_lumen_per_square_meter_per_steradian,
      R.string.unit_lumen_per_square_meter_per_steradian_short,
    ),
    NormalUnit(
      UnitID.lumen_per_square_centimeter_per_steradian,
      BigDecimal("314159265359"),
      UnitGroup.LUMINANCE,
      R.string.unit_lumen_per_square_centimeter_per_steradian,
      R.string.unit_lumen_per_square_centimeter_per_steradian_short,
    ),
    NormalUnit(
      UnitID.lumen_per_square_foot_per_steradian,
      BigDecimal("338158218.89"),
      UnitGroup.LUMINANCE,
      R.string.unit_lumen_per_square_foot_per_steradian,
      R.string.unit_lumen_per_square_foot_per_steradian_short,
    ),
    NormalUnit(
      UnitID.watt_per_square_centimeter_per_steradian,
      BigDecimal("214570778240185"),
      UnitGroup.LUMINANCE,
      R.string.unit_watt_per_square_centimeter_per_steradian,
      R.string.unit_watt_per_square_centimeter_per_steradian_short,
    ),
    NormalUnit(
      UnitID.nit,
      BigDecimal("31415926.5359"),
      UnitGroup.LUMINANCE,
      R.string.unit_nit,
      R.string.unit_nit_short,
    ),
    NormalUnit(
      UnitID.millinit,
      BigDecimal("31415.9265359"),
      UnitGroup.LUMINANCE,
      R.string.unit_millinit,
      R.string.unit_millinit_short,
    ),
    NormalUnit(
      UnitID.lambert,
      BigDecimal("100000000000"),
      UnitGroup.LUMINANCE,
      R.string.unit_lambert,
      R.string.unit_lambert_short,
    ),
    NormalUnit(
      UnitID.millilambert,
      BigDecimal("100000000"),
      UnitGroup.LUMINANCE,
      R.string.unit_millilambert,
      R.string.unit_millilambert_short,
    ),
    NormalUnit(
      UnitID.foot_lambert,
      BigDecimal("107639104.167"),
      UnitGroup.LUMINANCE,
      R.string.unit_foot_lambert,
      R.string.unit_foot_lambert_short,
    ),
    NormalUnit(
      UnitID.apostilb,
      BigDecimal("10000000"),
      UnitGroup.LUMINANCE,
      R.string.unit_apostilb,
      R.string.unit_apostilb_short,
    ),
    NormalUnit(
      UnitID.blondel,
      BigDecimal("10000000"),
      UnitGroup.LUMINANCE,
      R.string.unit_blondel,
      R.string.unit_blondel_short,
    ),
    NormalUnit(
      UnitID.skot,
      BigDecimal("10000"),
      UnitGroup.LUMINANCE,
      R.string.unit_skot,
      R.string.unit_skot_short,
    ),
    NormalUnit(
      UnitID.bril,
      BigDecimal("1"),
      UnitGroup.LUMINANCE,
      R.string.unit_bril,
      R.string.unit_bril_short,
    ),
  )
}
