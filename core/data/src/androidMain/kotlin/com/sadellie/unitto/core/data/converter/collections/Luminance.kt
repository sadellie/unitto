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
import unitto.core.common.generated.resources.unit_apostilb
import unitto.core.common.generated.resources.unit_apostilb_short
import unitto.core.common.generated.resources.unit_blondel
import unitto.core.common.generated.resources.unit_blondel_short
import unitto.core.common.generated.resources.unit_bril
import unitto.core.common.generated.resources.unit_bril_short
import unitto.core.common.generated.resources.unit_candela_per_square_centimeter
import unitto.core.common.generated.resources.unit_candela_per_square_centimeter_short
import unitto.core.common.generated.resources.unit_candela_per_square_foot
import unitto.core.common.generated.resources.unit_candela_per_square_foot_short
import unitto.core.common.generated.resources.unit_candela_per_square_inch
import unitto.core.common.generated.resources.unit_candela_per_square_inch_short
import unitto.core.common.generated.resources.unit_candela_per_square_meter
import unitto.core.common.generated.resources.unit_candela_per_square_meter_short
import unitto.core.common.generated.resources.unit_foot_lambert
import unitto.core.common.generated.resources.unit_foot_lambert_short
import unitto.core.common.generated.resources.unit_kilocandela_per_square_meter
import unitto.core.common.generated.resources.unit_kilocandela_per_square_meter_short
import unitto.core.common.generated.resources.unit_lambert
import unitto.core.common.generated.resources.unit_lambert_short
import unitto.core.common.generated.resources.unit_lumen_per_square_centimeter_per_steradian
import unitto.core.common.generated.resources.unit_lumen_per_square_centimeter_per_steradian_short
import unitto.core.common.generated.resources.unit_lumen_per_square_foot_per_steradian
import unitto.core.common.generated.resources.unit_lumen_per_square_foot_per_steradian_short
import unitto.core.common.generated.resources.unit_lumen_per_square_meter_per_steradian
import unitto.core.common.generated.resources.unit_lumen_per_square_meter_per_steradian_short
import unitto.core.common.generated.resources.unit_millilambert
import unitto.core.common.generated.resources.unit_millilambert_short
import unitto.core.common.generated.resources.unit_millinit
import unitto.core.common.generated.resources.unit_millinit_short
import unitto.core.common.generated.resources.unit_nit
import unitto.core.common.generated.resources.unit_nit_short
import unitto.core.common.generated.resources.unit_skot
import unitto.core.common.generated.resources.unit_skot_short
import unitto.core.common.generated.resources.unit_stilb
import unitto.core.common.generated.resources.unit_stilb_short
import unitto.core.common.generated.resources.unit_watt_per_square_centimeter_per_steradian
import unitto.core.common.generated.resources.unit_watt_per_square_centimeter_per_steradian_short

val luminanceCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.candela_per_square_meter,
      KBigDecimal("31415926.5359"),
      UnitGroup.LUMINANCE,
      Res.string.unit_candela_per_square_meter,
      Res.string.unit_candela_per_square_meter_short,
    ),
    NormalUnit(
      UnitID.candela_per_square_centimeter,
      KBigDecimal("314159265359"),
      UnitGroup.LUMINANCE,
      Res.string.unit_candela_per_square_centimeter,
      Res.string.unit_candela_per_square_centimeter_short,
    ),
    NormalUnit(
      UnitID.candela_per_square_foot,
      KBigDecimal("338158218.89"),
      UnitGroup.LUMINANCE,
      Res.string.unit_candela_per_square_foot,
      Res.string.unit_candela_per_square_foot_short,
    ),
    NormalUnit(
      UnitID.candela_per_square_inch,
      KBigDecimal("48694783520"),
      UnitGroup.LUMINANCE,
      Res.string.unit_candela_per_square_inch,
      Res.string.unit_candela_per_square_inch_short,
    ),
    NormalUnit(
      UnitID.kilocandela_per_square_meter,
      KBigDecimal("31415926535.9"),
      UnitGroup.LUMINANCE,
      Res.string.unit_kilocandela_per_square_meter,
      Res.string.unit_kilocandela_per_square_meter_short,
    ),
    NormalUnit(
      UnitID.stilb,
      KBigDecimal("314159265359"),
      UnitGroup.LUMINANCE,
      Res.string.unit_stilb,
      Res.string.unit_stilb_short,
    ),
    NormalUnit(
      UnitID.lumen_per_square_meter_per_steradian,
      KBigDecimal("31415926.5359"),
      UnitGroup.LUMINANCE,
      Res.string.unit_lumen_per_square_meter_per_steradian,
      Res.string.unit_lumen_per_square_meter_per_steradian_short,
    ),
    NormalUnit(
      UnitID.lumen_per_square_centimeter_per_steradian,
      KBigDecimal("314159265359"),
      UnitGroup.LUMINANCE,
      Res.string.unit_lumen_per_square_centimeter_per_steradian,
      Res.string.unit_lumen_per_square_centimeter_per_steradian_short,
    ),
    NormalUnit(
      UnitID.lumen_per_square_foot_per_steradian,
      KBigDecimal("338158218.89"),
      UnitGroup.LUMINANCE,
      Res.string.unit_lumen_per_square_foot_per_steradian,
      Res.string.unit_lumen_per_square_foot_per_steradian_short,
    ),
    NormalUnit(
      UnitID.watt_per_square_centimeter_per_steradian,
      KBigDecimal("214570778240185"),
      UnitGroup.LUMINANCE,
      Res.string.unit_watt_per_square_centimeter_per_steradian,
      Res.string.unit_watt_per_square_centimeter_per_steradian_short,
    ),
    NormalUnit(
      UnitID.nit,
      KBigDecimal("31415926.5359"),
      UnitGroup.LUMINANCE,
      Res.string.unit_nit,
      Res.string.unit_nit_short,
    ),
    NormalUnit(
      UnitID.millinit,
      KBigDecimal("31415.9265359"),
      UnitGroup.LUMINANCE,
      Res.string.unit_millinit,
      Res.string.unit_millinit_short,
    ),
    NormalUnit(
      UnitID.lambert,
      KBigDecimal("100000000000"),
      UnitGroup.LUMINANCE,
      Res.string.unit_lambert,
      Res.string.unit_lambert_short,
    ),
    NormalUnit(
      UnitID.millilambert,
      KBigDecimal("100000000"),
      UnitGroup.LUMINANCE,
      Res.string.unit_millilambert,
      Res.string.unit_millilambert_short,
    ),
    NormalUnit(
      UnitID.foot_lambert,
      KBigDecimal("107639104.167"),
      UnitGroup.LUMINANCE,
      Res.string.unit_foot_lambert,
      Res.string.unit_foot_lambert_short,
    ),
    NormalUnit(
      UnitID.apostilb,
      KBigDecimal("10000000"),
      UnitGroup.LUMINANCE,
      Res.string.unit_apostilb,
      Res.string.unit_apostilb_short,
    ),
    NormalUnit(
      UnitID.blondel,
      KBigDecimal("10000000"),
      UnitGroup.LUMINANCE,
      Res.string.unit_blondel,
      Res.string.unit_blondel_short,
    ),
    NormalUnit(
      UnitID.skot,
      KBigDecimal("10000"),
      UnitGroup.LUMINANCE,
      Res.string.unit_skot,
      Res.string.unit_skot_short,
    ),
    NormalUnit(
      UnitID.bril,
      KBigDecimal("1"),
      UnitGroup.LUMINANCE,
      Res.string.unit_bril,
      Res.string.unit_bril_short,
    ),
  )
}
