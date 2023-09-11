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
import com.sadellie.unitto.data.model.unit.NumberBaseUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import java.math.BigDecimal

internal val numberBaseCollection: List<AbstractUnit> by lazy {
    listOf(
        NumberBaseUnit(MyUnitIDS.binary,        BigDecimal.valueOf(2.0),  UnitGroup.NUMBER_BASE,  R.string.binary,        R.string.binary_short),
        NumberBaseUnit(MyUnitIDS.ternary,       BigDecimal.valueOf(3.0),  UnitGroup.NUMBER_BASE,  R.string.ternary,       R.string.ternary_short),
        NumberBaseUnit(MyUnitIDS.quaternary,    BigDecimal.valueOf(4.0),  UnitGroup.NUMBER_BASE,  R.string.quaternary,    R.string.quaternary_short),
        NumberBaseUnit(MyUnitIDS.quinary,       BigDecimal.valueOf(5.0),  UnitGroup.NUMBER_BASE,  R.string.quinary,       R.string.quinary_short),
        NumberBaseUnit(MyUnitIDS.senary,        BigDecimal.valueOf(6.0),  UnitGroup.NUMBER_BASE,  R.string.senary,        R.string.senary_short),
        NumberBaseUnit(MyUnitIDS.septenary,     BigDecimal.valueOf(7.0),  UnitGroup.NUMBER_BASE,  R.string.septenary,     R.string.septenary_short),
        NumberBaseUnit(MyUnitIDS.octal,         BigDecimal.valueOf(8.0),  UnitGroup.NUMBER_BASE,  R.string.octal,         R.string.octal_short),
        NumberBaseUnit(MyUnitIDS.nonary,        BigDecimal.valueOf(9.0),  UnitGroup.NUMBER_BASE,  R.string.nonary,        R.string.nonary_short),
        NumberBaseUnit(MyUnitIDS.decimal,       BigDecimal.valueOf(10.0), UnitGroup.NUMBER_BASE,  R.string.decimal,       R.string.decimal_short),
        NumberBaseUnit(MyUnitIDS.undecimal,     BigDecimal.valueOf(11.0), UnitGroup.NUMBER_BASE,  R.string.undecimal,     R.string.undecimal_short),
        NumberBaseUnit(MyUnitIDS.duodecimal,    BigDecimal.valueOf(12.0), UnitGroup.NUMBER_BASE,  R.string.duodecimal,    R.string.duodecimal_short),
        NumberBaseUnit(MyUnitIDS.tridecimal,    BigDecimal.valueOf(13.0), UnitGroup.NUMBER_BASE,  R.string.tridecimal,    R.string.tridecimal_short),
        NumberBaseUnit(MyUnitIDS.tetradecimal,  BigDecimal.valueOf(14.0), UnitGroup.NUMBER_BASE,  R.string.tetradecimal,  R.string.tetradecimal_short),
        NumberBaseUnit(MyUnitIDS.pentadecimal,  BigDecimal.valueOf(15.0), UnitGroup.NUMBER_BASE,  R.string.pentadecimal,  R.string.pentadecimal_short),
        NumberBaseUnit(MyUnitIDS.hexadecimal,   BigDecimal.valueOf(16.0), UnitGroup.NUMBER_BASE,  R.string.hexadecimal,   R.string.hexadecimal_short),
    )
}
