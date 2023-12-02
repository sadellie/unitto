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

package com.sadellie.unitto.data.converter.collections

import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.NormalUnit
import com.sadellie.unitto.data.converter.MyUnitIDS
import java.math.BigDecimal

internal val volumeCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(MyUnitIDS.attoliter,             BigDecimal.valueOf(1),                          UnitGroup.VOLUME, R.string.unit_attoliter,            R.string.unit_attoliter_short),
        NormalUnit(MyUnitIDS.milliliter,            BigDecimal.valueOf(1_000_000_000_000_000),      UnitGroup.VOLUME, R.string.unit_milliliter,           R.string.unit_milliliter_short),
        NormalUnit(MyUnitIDS.liter,                 BigDecimal.valueOf(1_000_000_000_000_000_000),  UnitGroup.VOLUME, R.string.unit_liter,                R.string.unit_liter_short),
        NormalUnit(MyUnitIDS.us_liquid_gallon,      BigDecimal.valueOf(3_785_411_783_999_977_000),  UnitGroup.VOLUME, R.string.unit_us_liquid_gallon,     R.string.unit_us_liquid_gallon_short),
        NormalUnit(MyUnitIDS.us_liquid_quart,       BigDecimal.valueOf(946_352_945_999_994_200),    UnitGroup.VOLUME, R.string.unit_us_liquid_quart,      R.string.unit_us_liquid_quart_short),
        NormalUnit(MyUnitIDS.us_liquid_pint,        BigDecimal.valueOf(473_176_472_999_997_100),    UnitGroup.VOLUME, R.string.unit_us_liquid_pint,       R.string.unit_us_liquid_pint_short),
        NormalUnit(MyUnitIDS.us_legal_cup,          BigDecimal.valueOf(236_588_236_499_998_560),    UnitGroup.VOLUME, R.string.unit_us_legal_cup,         R.string.unit_us_legal_cup_short),
        NormalUnit(MyUnitIDS.us_fluid_ounce,        BigDecimal.valueOf(29_573_529_562_499_996),     UnitGroup.VOLUME, R.string.unit_us_fluid_ounce,       R.string.unit_us_fluid_ounce_short),
        NormalUnit(MyUnitIDS.us_tablespoon,         BigDecimal.valueOf(14_786_764_781_249_998),     UnitGroup.VOLUME, R.string.unit_us_tablespoon,        R.string.unit_us_tablespoon_short),
        NormalUnit(MyUnitIDS.us_teaspoon,           BigDecimal.valueOf(4_928_921_593_749_952),      UnitGroup.VOLUME, R.string.unit_us_teaspoon,          R.string.unit_us_teaspoon_short),
        NormalUnit(MyUnitIDS.imperial_gallon,       BigDecimal.valueOf(4_546_089_999_999_954_400),  UnitGroup.VOLUME, R.string.unit_imperial_gallon,      R.string.unit_imperial_gallon_short),
        NormalUnit(MyUnitIDS.imperial_quart,        BigDecimal.valueOf(1_136_522_500_000_001_400),  UnitGroup.VOLUME, R.string.unit_imperial_quart,       R.string.unit_imperial_quart_short),
        NormalUnit(MyUnitIDS.imperial_pint,         BigDecimal.valueOf(568_261_250_000_000_700),    UnitGroup.VOLUME, R.string.unit_imperial_pint,        R.string.unit_imperial_pint_short),
        NormalUnit(MyUnitIDS.imperial_cup,          BigDecimal.valueOf(284_130_625_000_000_350),    UnitGroup.VOLUME, R.string.unit_imperial_cup,         R.string.unit_imperial_cup_short),
        NormalUnit(MyUnitIDS.imperial_fluid_ounce,  BigDecimal.valueOf(28_413_062_500_000_036),     UnitGroup.VOLUME, R.string.unit_imperial_fluid_ounce, R.string.unit_imperial_fluid_ounce_short),
        NormalUnit(MyUnitIDS.imperial_tablespoon,   BigDecimal.valueOf(17_758_164_062_500_148),     UnitGroup.VOLUME, R.string.unit_imperial_tablespoon,  R.string.unit_imperial_tablespoon_short),
        NormalUnit(MyUnitIDS.imperial_teaspoon,     BigDecimal.valueOf(5_919_388_020_833_314),      UnitGroup.VOLUME, R.string.unit_imperial_teaspoon,    R.string.unit_imperial_teaspoon_short),
        NormalUnit(MyUnitIDS.cubic_millimeter,      BigDecimal.valueOf(1_000_000_000_000),          UnitGroup.VOLUME, R.string.unit_cubic_millimeter,     R.string.unit_cubic_millimeter_short),
        NormalUnit(MyUnitIDS.cubic_centimeter,      BigDecimal.valueOf(1_000_000_000_000_000),      UnitGroup.VOLUME, R.string.unit_cubic_centimeter,     R.string.unit_cubic_centimeter_short),
        NormalUnit(MyUnitIDS.cubic_meter,           BigDecimal.valueOf(1.0E+21),                    UnitGroup.VOLUME, R.string.unit_cubic_meter,          R.string.unit_cubic_meter_short),
        NormalUnit(MyUnitIDS.cubic_kilometer,       BigDecimal.valueOf(1.0E+30),                    UnitGroup.VOLUME, R.string.unit_cubic_kilometer,      R.string.unit_cubic_kilometer_short),
    )
}
