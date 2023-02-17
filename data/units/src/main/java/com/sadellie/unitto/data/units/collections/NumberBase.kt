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

import com.sadellie.unitto.data.model.AbstractUnit
import com.sadellie.unitto.data.model.NumberBaseUnit
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.R

internal val numberBaseCollection: List<AbstractUnit> by lazy {
    listOf(
        NumberBaseUnit(MyUnitIDS.binary,        2,  UnitGroup.NUMBER_BASE,  R.string.binary,        R.string.binary_short),
        NumberBaseUnit(MyUnitIDS.ternary,       3,  UnitGroup.NUMBER_BASE,  R.string.ternary,       R.string.ternary_short),
        NumberBaseUnit(MyUnitIDS.quaternary,    4,  UnitGroup.NUMBER_BASE,  R.string.quaternary,    R.string.quaternary_short),
        NumberBaseUnit(MyUnitIDS.quinary,       5,  UnitGroup.NUMBER_BASE,  R.string.quinary,       R.string.quinary_short),
        NumberBaseUnit(MyUnitIDS.senary,        6,  UnitGroup.NUMBER_BASE,  R.string.senary,        R.string.senary_short),
        NumberBaseUnit(MyUnitIDS.septenary,     7,  UnitGroup.NUMBER_BASE,  R.string.septenary,     R.string.septenary_short),
        NumberBaseUnit(MyUnitIDS.octal,         8,  UnitGroup.NUMBER_BASE,  R.string.octal,         R.string.octal_short),
        NumberBaseUnit(MyUnitIDS.nonary,        9,  UnitGroup.NUMBER_BASE,  R.string.nonary,        R.string.nonary_short),
        NumberBaseUnit(MyUnitIDS.decimal,       10, UnitGroup.NUMBER_BASE,  R.string.decimal,       R.string.decimal_short),
        NumberBaseUnit(MyUnitIDS.undecimal,     11, UnitGroup.NUMBER_BASE,  R.string.undecimal,     R.string.undecimal_short),
        NumberBaseUnit(MyUnitIDS.duodecimal,    12, UnitGroup.NUMBER_BASE,  R.string.duodecimal,    R.string.duodecimal_short),
        NumberBaseUnit(MyUnitIDS.tridecimal,    13, UnitGroup.NUMBER_BASE,  R.string.tridecimal,    R.string.tridecimal_short),
        NumberBaseUnit(MyUnitIDS.tetradecimal,  14, UnitGroup.NUMBER_BASE,  R.string.tetradecimal,  R.string.tetradecimal_short),
        NumberBaseUnit(MyUnitIDS.pentadecimal,  15, UnitGroup.NUMBER_BASE,  R.string.pentadecimal,  R.string.pentadecimal_short),
        NumberBaseUnit(MyUnitIDS.hexadecimal,   16, UnitGroup.NUMBER_BASE,  R.string.hexadecimal,   R.string.hexadecimal_short),
    )
}