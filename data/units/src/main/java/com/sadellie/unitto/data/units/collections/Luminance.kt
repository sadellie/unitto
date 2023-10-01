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

val luminanceCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(MyUnitIDS.candela_per_square_meter,                     BigDecimal.valueOf(31415926.5359),      UnitGroup.LUMINANCE,    R.string.unit_candela_per_square_meter,                  R.string.unit_candela_per_square_meter_short),
        NormalUnit(MyUnitIDS.candela_per_square_centimeter,                BigDecimal.valueOf(314159265359),       UnitGroup.LUMINANCE,    R.string.unit_candela_per_square_centimeter,             R.string.unit_candela_per_square_centimeter_short),
        NormalUnit(MyUnitIDS.candela_per_square_foot,                      BigDecimal.valueOf(338158218.89),       UnitGroup.LUMINANCE,    R.string.unit_candela_per_square_foot,                   R.string.unit_candela_per_square_foot_short),
        NormalUnit(MyUnitIDS.candela_per_square_inch,                      BigDecimal.valueOf(48694783520),        UnitGroup.LUMINANCE,    R.string.unit_candela_per_square_inch,                   R.string.unit_candela_per_square_inch_short),
        NormalUnit(MyUnitIDS.kilocandela_per_square_meter,                 BigDecimal.valueOf(31415926535.9),      UnitGroup.LUMINANCE,    R.string.unit_kilocandela_per_square_meter,              R.string.unit_kilocandela_per_square_meter_short),
        NormalUnit(MyUnitIDS.stilb,                                        BigDecimal.valueOf(314159265359),       UnitGroup.LUMINANCE,    R.string.unit_stilb,                                     R.string.unit_stilb_short),
        NormalUnit(MyUnitIDS.lumen_per_square_meter_per_steradian,         BigDecimal.valueOf(31415926.5359),      UnitGroup.LUMINANCE,    R.string.unit_lumen_per_square_meter_per_steradian,      R.string.unit_lumen_per_square_meter_per_steradian_short),
        NormalUnit(MyUnitIDS.lumen_per_square_centimeter_per_steradian,    BigDecimal.valueOf(314159265359),       UnitGroup.LUMINANCE,    R.string.unit_lumen_per_square_centimeter_per_steradian, R.string.unit_lumen_per_square_centimeter_per_steradian_short),
        NormalUnit(MyUnitIDS.lumen_per_square_foot_per_steradian,          BigDecimal.valueOf(338158218.89),       UnitGroup.LUMINANCE,    R.string.unit_lumen_per_square_foot_per_steradian,       R.string.unit_lumen_per_square_foot_per_steradian_short),
        NormalUnit(MyUnitIDS.watt_per_square_centimeter_per_steradian,     BigDecimal.valueOf(214570778240185),    UnitGroup.LUMINANCE,    R.string.unit_watt_per_square_centimeter_per_steradian,  R.string.unit_watt_per_square_centimeter_per_steradian_short),
        NormalUnit(MyUnitIDS.nit,                                          BigDecimal.valueOf(31415926.5359),      UnitGroup.LUMINANCE,    R.string.unit_nit,                                       R.string.unit_nit_short),
        NormalUnit(MyUnitIDS.millinit,                                     BigDecimal.valueOf(31415.9265359),      UnitGroup.LUMINANCE,    R.string.unit_millinit,                                  R.string.unit_millinit_short),
        NormalUnit(MyUnitIDS.lambert,                                      BigDecimal.valueOf(100000000000),       UnitGroup.LUMINANCE,    R.string.unit_lambert,                                   R.string.unit_lambert_short),
        NormalUnit(MyUnitIDS.millilambert,                                 BigDecimal.valueOf(100000000),          UnitGroup.LUMINANCE,    R.string.unit_millilambert,                              R.string.unit_millilambert_short),
        NormalUnit(MyUnitIDS.foot_lambert,                                 BigDecimal.valueOf(107639104.167),      UnitGroup.LUMINANCE,    R.string.unit_foot_lambert,                              R.string.unit_foot_lambert_short),
        NormalUnit(MyUnitIDS.apostilb,                                     BigDecimal.valueOf(10000000),           UnitGroup.LUMINANCE,    R.string.unit_apostilb,                                  R.string.unit_apostilb_short),
        NormalUnit(MyUnitIDS.blondel,                                      BigDecimal.valueOf(10000000),           UnitGroup.LUMINANCE,    R.string.unit_blondel,                                   R.string.unit_blondel_short),
        NormalUnit(MyUnitIDS.skot,                                         BigDecimal.valueOf(10000),              UnitGroup.LUMINANCE,    R.string.unit_skot,                                      R.string.unit_skot_short),
        NormalUnit(MyUnitIDS.bril,                                         BigDecimal.valueOf(1),                  UnitGroup.LUMINANCE,    R.string.unit_bril,                                      R.string.unit_bril_short),
    )
}
