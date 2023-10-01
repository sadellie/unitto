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

val prefixCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(MyUnitIDS.prefix_quetta, BigDecimal.valueOf(1E+30),  UnitGroup.PREFIX, R.string.unit_prefix_quetta,   R.string.unit_prefix_quetta_short),
        NormalUnit(MyUnitIDS.prefix_ronna,  BigDecimal.valueOf(1E+27),  UnitGroup.PREFIX, R.string.unit_prefix_ronna,    R.string.unit_prefix_ronna_short),
        NormalUnit(MyUnitIDS.prefix_yotta,  BigDecimal.valueOf(1E+24),  UnitGroup.PREFIX, R.string.unit_prefix_yotta,    R.string.unit_prefix_yotta_short),
        NormalUnit(MyUnitIDS.prefix_zetta,  BigDecimal.valueOf(1E+21),  UnitGroup.PREFIX, R.string.unit_prefix_zetta,    R.string.unit_prefix_zetta_short),
        NormalUnit(MyUnitIDS.prefix_exa,    BigDecimal.valueOf(1E+18),  UnitGroup.PREFIX, R.string.unit_prefix_exa,      R.string.unit_prefix_exa_short),
        NormalUnit(MyUnitIDS.prefix_peta,   BigDecimal.valueOf(1E+15),  UnitGroup.PREFIX, R.string.unit_prefix_peta,     R.string.unit_prefix_peta_short),
        NormalUnit(MyUnitIDS.prefix_tera,   BigDecimal.valueOf(1E+12),  UnitGroup.PREFIX, R.string.unit_prefix_tera,     R.string.unit_prefix_tera_short),
        NormalUnit(MyUnitIDS.prefix_giga,   BigDecimal.valueOf(1E+9),   UnitGroup.PREFIX, R.string.unit_prefix_giga,     R.string.unit_prefix_giga_short),
        NormalUnit(MyUnitIDS.prefix_mega,   BigDecimal.valueOf(1E+6),   UnitGroup.PREFIX, R.string.unit_prefix_mega,     R.string.unit_prefix_mega_short),
        NormalUnit(MyUnitIDS.prefix_kilo,   BigDecimal.valueOf(1E+3),   UnitGroup.PREFIX, R.string.unit_prefix_kilo,     R.string.unit_prefix_kilo_short),
        NormalUnit(MyUnitIDS.prefix_hecto,  BigDecimal.valueOf(1E+2),   UnitGroup.PREFIX, R.string.unit_prefix_hecto,    R.string.unit_prefix_hecto_short),
        NormalUnit(MyUnitIDS.prefix_deca,   BigDecimal.valueOf(1E+1),   UnitGroup.PREFIX, R.string.unit_prefix_deca,     R.string.unit_prefix_deca_short),
        NormalUnit(MyUnitIDS.prefix_base,   BigDecimal.valueOf(1E+0),   UnitGroup.PREFIX, R.string.unit_prefix_base,     R.string.unit_prefix_base_short),
        NormalUnit(MyUnitIDS.prefix_deci,   BigDecimal.valueOf(1E-1),   UnitGroup.PREFIX, R.string.unit_prefix_deci,     R.string.unit_prefix_deci_short),
        NormalUnit(MyUnitIDS.prefix_centi,  BigDecimal.valueOf(1E-2),   UnitGroup.PREFIX, R.string.unit_prefix_centi,    R.string.unit_prefix_centi_short),
        NormalUnit(MyUnitIDS.prefix_milli,  BigDecimal.valueOf(1E-3),   UnitGroup.PREFIX, R.string.unit_prefix_milli,    R.string.unit_prefix_milli_short),
        NormalUnit(MyUnitIDS.prefix_micro,  BigDecimal.valueOf(1E-6),   UnitGroup.PREFIX, R.string.unit_prefix_micro,    R.string.unit_prefix_micro_short),
        NormalUnit(MyUnitIDS.prefix_nano,   BigDecimal.valueOf(1E-9),   UnitGroup.PREFIX, R.string.unit_prefix_nano,     R.string.unit_prefix_nano_short),
        NormalUnit(MyUnitIDS.prefix_pico,   BigDecimal.valueOf(1E-12),  UnitGroup.PREFIX, R.string.unit_prefix_pico,     R.string.unit_prefix_pico_short),
        NormalUnit(MyUnitIDS.prefix_femto,  BigDecimal.valueOf(1E-15),  UnitGroup.PREFIX, R.string.unit_prefix_femto,    R.string.unit_prefix_femto_short),
        NormalUnit(MyUnitIDS.prefix_atto,   BigDecimal.valueOf(1E-18),  UnitGroup.PREFIX, R.string.unit_prefix_atto,     R.string.unit_prefix_atto_short),
        NormalUnit(MyUnitIDS.prefix_zepto,  BigDecimal.valueOf(1E-21),  UnitGroup.PREFIX, R.string.unit_prefix_zepto,    R.string.unit_prefix_zepto_short),
        NormalUnit(MyUnitIDS.prefix_yocto,  BigDecimal.valueOf(1E-24),  UnitGroup.PREFIX, R.string.unit_prefix_yocto,    R.string.unit_prefix_yocto_short),
        NormalUnit(MyUnitIDS.prefix_ronto,  BigDecimal.valueOf(1E-27),  UnitGroup.PREFIX, R.string.unit_prefix_ronto,    R.string.unit_prefix_ronto_short),
        NormalUnit(MyUnitIDS.prefix_quecto, BigDecimal.valueOf(1E-30),  UnitGroup.PREFIX, R.string.unit_prefix_quecto,   R.string.unit_prefix_quecto_short),
    )
}
