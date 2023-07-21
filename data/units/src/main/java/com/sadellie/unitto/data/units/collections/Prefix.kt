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

val prefixCollection: List<AbstractUnit> by lazy {
    listOf(
        DefaultUnit(MyUnitIDS.prefix_quetta, BigDecimal.valueOf(1E+30),  UnitGroup.PREFIX, R.string.prefix_quetta,   R.string.prefix_quetta_short),
        DefaultUnit(MyUnitIDS.prefix_ronna,  BigDecimal.valueOf(1E+27),  UnitGroup.PREFIX, R.string.prefix_ronna,    R.string.prefix_ronna_short),
        DefaultUnit(MyUnitIDS.prefix_yotta,  BigDecimal.valueOf(1E+24),  UnitGroup.PREFIX, R.string.prefix_yotta,    R.string.prefix_yotta_short),
        DefaultUnit(MyUnitIDS.prefix_zetta,  BigDecimal.valueOf(1E+21),  UnitGroup.PREFIX, R.string.prefix_zetta,    R.string.prefix_zetta_short),
        DefaultUnit(MyUnitIDS.prefix_exa,    BigDecimal.valueOf(1E+18),  UnitGroup.PREFIX, R.string.prefix_exa,      R.string.prefix_exa_short),
        DefaultUnit(MyUnitIDS.prefix_peta,   BigDecimal.valueOf(1E+15),  UnitGroup.PREFIX, R.string.prefix_peta,     R.string.prefix_peta_short),
        DefaultUnit(MyUnitIDS.prefix_tera,   BigDecimal.valueOf(1E+12),  UnitGroup.PREFIX, R.string.prefix_tera,     R.string.prefix_tera_short),
        DefaultUnit(MyUnitIDS.prefix_giga,   BigDecimal.valueOf(1E+9),   UnitGroup.PREFIX, R.string.prefix_giga,     R.string.prefix_giga_short),
        DefaultUnit(MyUnitIDS.prefix_mega,   BigDecimal.valueOf(1E+6),   UnitGroup.PREFIX, R.string.prefix_mega,     R.string.prefix_mega_short),
        DefaultUnit(MyUnitIDS.prefix_kilo,   BigDecimal.valueOf(1E+3),   UnitGroup.PREFIX, R.string.prefix_kilo,     R.string.prefix_kilo_short),
        DefaultUnit(MyUnitIDS.prefix_hecto,  BigDecimal.valueOf(1E+2),   UnitGroup.PREFIX, R.string.prefix_hecto,    R.string.prefix_hecto_short),
        DefaultUnit(MyUnitIDS.prefix_deca,   BigDecimal.valueOf(1E+1),   UnitGroup.PREFIX, R.string.prefix_deca,     R.string.prefix_deca_short),
        DefaultUnit(MyUnitIDS.prefix_base,   BigDecimal.valueOf(1E+0),   UnitGroup.PREFIX, R.string.prefix_base,     R.string.prefix_base_short),
        DefaultUnit(MyUnitIDS.prefix_deci,   BigDecimal.valueOf(1E-1),   UnitGroup.PREFIX, R.string.prefix_deci,     R.string.prefix_deci_short),
        DefaultUnit(MyUnitIDS.prefix_centi,  BigDecimal.valueOf(1E-2),   UnitGroup.PREFIX, R.string.prefix_centi,    R.string.prefix_centi_short),
        DefaultUnit(MyUnitIDS.prefix_milli,  BigDecimal.valueOf(1E-3),   UnitGroup.PREFIX, R.string.prefix_milli,    R.string.prefix_milli_short),
        DefaultUnit(MyUnitIDS.prefix_micro,  BigDecimal.valueOf(1E-6),   UnitGroup.PREFIX, R.string.prefix_micro,    R.string.prefix_micro_short),
        DefaultUnit(MyUnitIDS.prefix_nano,   BigDecimal.valueOf(1E-9),   UnitGroup.PREFIX, R.string.prefix_nano,     R.string.prefix_nano_short),
        DefaultUnit(MyUnitIDS.prefix_pico,   BigDecimal.valueOf(1E-12),  UnitGroup.PREFIX, R.string.prefix_pico,     R.string.prefix_pico_short),
        DefaultUnit(MyUnitIDS.prefix_femto,  BigDecimal.valueOf(1E-15),  UnitGroup.PREFIX, R.string.prefix_femto,    R.string.prefix_femto_short),
        DefaultUnit(MyUnitIDS.prefix_atto,   BigDecimal.valueOf(1E-18),  UnitGroup.PREFIX, R.string.prefix_atto,     R.string.prefix_atto_short),
        DefaultUnit(MyUnitIDS.prefix_zepto,  BigDecimal.valueOf(1E-21),  UnitGroup.PREFIX, R.string.prefix_zepto,    R.string.prefix_zepto_short),
        DefaultUnit(MyUnitIDS.prefix_yocto,  BigDecimal.valueOf(1E-24),  UnitGroup.PREFIX, R.string.prefix_yocto,    R.string.prefix_yocto_short),
        DefaultUnit(MyUnitIDS.prefix_ronto,  BigDecimal.valueOf(1E-27),  UnitGroup.PREFIX, R.string.prefix_ronto,    R.string.prefix_ronto_short),
        DefaultUnit(MyUnitIDS.prefix_quecto, BigDecimal.valueOf(1E-30),  UnitGroup.PREFIX, R.string.prefix_quecto,   R.string.prefix_quecto_short),
    )
}
