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

import com.sadellie.unitto.data.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import java.math.BigDecimal

internal val volumeCollection: List<AbstractUnit> by lazy {
    listOf(
        MyUnit(MyUnitIDS.attoliter,             BigDecimal.valueOf(1),                          UnitGroup.VOLUME, R.string.attoliter,            R.string.attoliter_short),
        MyUnit(MyUnitIDS.milliliter,            BigDecimal.valueOf(1_000_000_000_000_000),      UnitGroup.VOLUME, R.string.milliliter,           R.string.milliliter_short),
        MyUnit(MyUnitIDS.liter,                 BigDecimal.valueOf(1_000_000_000_000_000_000),  UnitGroup.VOLUME, R.string.liter,                R.string.liter_short),
        MyUnit(MyUnitIDS.us_liquid_gallon,      BigDecimal.valueOf(3_785_411_783_999_977_000),  UnitGroup.VOLUME, R.string.us_liquid_gallon,     R.string.us_liquid_gallon_short),
        MyUnit(MyUnitIDS.us_liquid_quart,       BigDecimal.valueOf(946_352_945_999_994_200),    UnitGroup.VOLUME, R.string.us_liquid_quart,      R.string.us_liquid_quart_short),
        MyUnit(MyUnitIDS.us_liquid_pint,        BigDecimal.valueOf(473_176_472_999_997_100),    UnitGroup.VOLUME, R.string.us_liquid_pint,       R.string.us_liquid_pint_short),
        MyUnit(MyUnitIDS.us_legal_cup,          BigDecimal.valueOf(236_588_236_499_998_560),    UnitGroup.VOLUME, R.string.us_legal_cup,         R.string.us_legal_cup_short),
        MyUnit(MyUnitIDS.us_fluid_ounce,        BigDecimal.valueOf(29_573_529_562_499_996),     UnitGroup.VOLUME, R.string.us_fluid_ounce,       R.string.us_fluid_ounce_short),
        MyUnit(MyUnitIDS.us_tablespoon,         BigDecimal.valueOf(14_786_764_781_249_998),     UnitGroup.VOLUME, R.string.us_tablespoon,        R.string.us_tablespoon_short),
        MyUnit(MyUnitIDS.us_teaspoon,           BigDecimal.valueOf(4_928_921_593_749_952),      UnitGroup.VOLUME, R.string.us_teaspoon,          R.string.us_teaspoon_short),
        MyUnit(MyUnitIDS.imperial_gallon,       BigDecimal.valueOf(4_546_089_999_999_954_400),  UnitGroup.VOLUME, R.string.imperial_gallon,      R.string.imperial_gallon_short),
        MyUnit(MyUnitIDS.imperial_quart,        BigDecimal.valueOf(1_136_522_500_000_001_400),  UnitGroup.VOLUME, R.string.imperial_quart,       R.string.imperial_quart_short),
        MyUnit(MyUnitIDS.imperial_pint,         BigDecimal.valueOf(568_261_250_000_000_700),    UnitGroup.VOLUME, R.string.imperial_pint,        R.string.imperial_pint_short),
        MyUnit(MyUnitIDS.imperial_cup,          BigDecimal.valueOf(284_130_625_000_000_350),    UnitGroup.VOLUME, R.string.imperial_cup,         R.string.imperial_cup_short),
        MyUnit(MyUnitIDS.imperial_fluid_ounce,  BigDecimal.valueOf(28_413_062_500_000_036),     UnitGroup.VOLUME, R.string.imperial_fluid_ounce, R.string.imperial_fluid_ounce_short),
        MyUnit(MyUnitIDS.imperial_tablespoon,   BigDecimal.valueOf(17_758_164_062_500_148),     UnitGroup.VOLUME, R.string.imperial_tablespoon,  R.string.imperial_tablespoon_short),
        MyUnit(MyUnitIDS.imperial_teaspoon,     BigDecimal.valueOf(5_919_388_020_833_314),      UnitGroup.VOLUME, R.string.imperial_teaspoon,    R.string.imperial_teaspoon_short),
        MyUnit(MyUnitIDS.cubic_millimeter,      BigDecimal.valueOf(1_000_000_000_000),          UnitGroup.VOLUME, R.string.cubic_millimeter,     R.string.cubic_millimeter_short),
        MyUnit(MyUnitIDS.cubic_centimeter,      BigDecimal.valueOf(1_000_000_000_000_000),      UnitGroup.VOLUME, R.string.cubic_centimeter,     R.string.cubic_centimeter_short),
        MyUnit(MyUnitIDS.cubic_meter,           BigDecimal.valueOf(1.0E+21),                    UnitGroup.VOLUME, R.string.cubic_meter,          R.string.cubic_meter_short),
        MyUnit(MyUnitIDS.cubic_kilometer,       BigDecimal.valueOf(1.0E+30),                    UnitGroup.VOLUME, R.string.cubic_kilometer,      R.string.cubic_kilometer_short),
    )
}