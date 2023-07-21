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
import com.sadellie.unitto.data.model.AbstractUnit
import com.sadellie.unitto.data.model.DefaultUnit
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.units.MyUnitIDS
import java.math.BigDecimal

val luminanceCollection: List<AbstractUnit> by lazy {
    listOf(
        DefaultUnit(MyUnitIDS.candela_per_square_meter,                     BigDecimal.valueOf(31415926.5359),      UnitGroup.LUMINANCE,    R.string.candela_per_square_meter,                  R.string.candela_per_square_meter_short),
        DefaultUnit(MyUnitIDS.candela_per_square_centimeter,                BigDecimal.valueOf(314159265359),       UnitGroup.LUMINANCE,    R.string.candela_per_square_centimeter,             R.string.candela_per_square_centimeter_short),
        DefaultUnit(MyUnitIDS.candela_per_square_foot,                      BigDecimal.valueOf(338158218.89),       UnitGroup.LUMINANCE,    R.string.candela_per_square_foot,                   R.string.candela_per_square_foot_short),
        DefaultUnit(MyUnitIDS.candela_per_square_inch,                      BigDecimal.valueOf(48694783520),        UnitGroup.LUMINANCE,    R.string.candela_per_square_inch,                   R.string.candela_per_square_inch_short),
        DefaultUnit(MyUnitIDS.kilocandela_per_square_meter,                 BigDecimal.valueOf(31415926535.9),      UnitGroup.LUMINANCE,    R.string.kilocandela_per_square_meter,              R.string.kilocandela_per_square_meter_short),
        DefaultUnit(MyUnitIDS.stilb,                                        BigDecimal.valueOf(314159265359),       UnitGroup.LUMINANCE,    R.string.stilb,                                     R.string.stilb_short),
        DefaultUnit(MyUnitIDS.lumen_per_square_meter_per_steradian,         BigDecimal.valueOf(31415926.5359),      UnitGroup.LUMINANCE,    R.string.lumen_per_square_meter_per_steradian,      R.string.lumen_per_square_meter_per_steradian_short),
        DefaultUnit(MyUnitIDS.lumen_per_square_centimeter_per_steradian,    BigDecimal.valueOf(314159265359),       UnitGroup.LUMINANCE,    R.string.lumen_per_square_centimeter_per_steradian, R.string.lumen_per_square_centimeter_per_steradian_short),
        DefaultUnit(MyUnitIDS.lumen_per_square_foot_per_steradian,          BigDecimal.valueOf(338158218.89),       UnitGroup.LUMINANCE,    R.string.lumen_per_square_foot_per_steradian,       R.string.lumen_per_square_foot_per_steradian_short),
        DefaultUnit(MyUnitIDS.watt_per_square_centimeter_per_steradian,     BigDecimal.valueOf(214570778240185),    UnitGroup.LUMINANCE,    R.string.watt_per_square_centimeter_per_steradian,  R.string.watt_per_square_centimeter_per_steradian_short),
        DefaultUnit(MyUnitIDS.nit,                                          BigDecimal.valueOf(31415926.5359),      UnitGroup.LUMINANCE,    R.string.nit,                                       R.string.nit_short),
        DefaultUnit(MyUnitIDS.millinit,                                     BigDecimal.valueOf(31415.9265359),      UnitGroup.LUMINANCE,    R.string.millinit,                                  R.string.millinit_short),
        DefaultUnit(MyUnitIDS.lambert,                                      BigDecimal.valueOf(100000000000),       UnitGroup.LUMINANCE,    R.string.lambert,                                   R.string.lambert_short),
        DefaultUnit(MyUnitIDS.millilambert,                                 BigDecimal.valueOf(100000000),          UnitGroup.LUMINANCE,    R.string.millilambert,                              R.string.millilambert_short),
        DefaultUnit(MyUnitIDS.foot_lambert,                                 BigDecimal.valueOf(107639104.167),      UnitGroup.LUMINANCE,    R.string.foot_lambert,                              R.string.foot_lambert_short),
        DefaultUnit(MyUnitIDS.apostilb,                                     BigDecimal.valueOf(10000000),           UnitGroup.LUMINANCE,    R.string.apostilb,                                  R.string.apostilb_short),
        DefaultUnit(MyUnitIDS.blondel,                                      BigDecimal.valueOf(10000000),           UnitGroup.LUMINANCE,    R.string.blondel,                                   R.string.blondel_short),
        DefaultUnit(MyUnitIDS.skot,                                         BigDecimal.valueOf(10000),              UnitGroup.LUMINANCE,    R.string.skot,                                      R.string.skot_short),
        DefaultUnit(MyUnitIDS.bril,                                         BigDecimal.valueOf(1),                  UnitGroup.LUMINANCE,    R.string.bril,                                      R.string.bril_short),
    )
}
