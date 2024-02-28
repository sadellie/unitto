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

package com.sadellie.unitto.data.converter.collections

import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.data.converter.UnitID
import com.sadellie.unitto.data.model.converter.UnitGroup
import com.sadellie.unitto.data.model.converter.unit.BasicUnit
import com.sadellie.unitto.data.model.converter.unit.NumberBaseUnit
import java.math.BigDecimal

internal val numberBaseCollection: List<BasicUnit> by lazy {
    listOf(
        NumberBaseUnit(UnitID.binary,        BigDecimal("2.0"),  UnitGroup.NUMBER_BASE,  R.string.unit_binary,        R.string.unit_binary_short),
        NumberBaseUnit(UnitID.ternary,       BigDecimal("3.0"),  UnitGroup.NUMBER_BASE,  R.string.unit_ternary,       R.string.unit_ternary_short),
        NumberBaseUnit(UnitID.quaternary,    BigDecimal("4.0"),  UnitGroup.NUMBER_BASE,  R.string.unit_quaternary,    R.string.unit_quaternary_short),
        NumberBaseUnit(UnitID.quinary,       BigDecimal("5.0"),  UnitGroup.NUMBER_BASE,  R.string.unit_quinary,       R.string.unit_quinary_short),
        NumberBaseUnit(UnitID.senary,        BigDecimal("6.0"),  UnitGroup.NUMBER_BASE,  R.string.unit_senary,        R.string.unit_senary_short),
        NumberBaseUnit(UnitID.septenary,     BigDecimal("7.0"),  UnitGroup.NUMBER_BASE,  R.string.unit_septenary,     R.string.unit_septenary_short),
        NumberBaseUnit(UnitID.octal,         BigDecimal("8.0"),  UnitGroup.NUMBER_BASE,  R.string.unit_octal,         R.string.unit_octal_short),
        NumberBaseUnit(UnitID.nonary,        BigDecimal("9.0"),  UnitGroup.NUMBER_BASE,  R.string.unit_nonary,        R.string.unit_nonary_short),
        NumberBaseUnit(UnitID.decimal,       BigDecimal("10.0"), UnitGroup.NUMBER_BASE,  R.string.unit_decimal,       R.string.unit_decimal_short),
        NumberBaseUnit(UnitID.undecimal,     BigDecimal("11.0"), UnitGroup.NUMBER_BASE,  R.string.unit_undecimal,     R.string.unit_undecimal_short),
        NumberBaseUnit(UnitID.duodecimal,    BigDecimal("12.0"), UnitGroup.NUMBER_BASE,  R.string.unit_duodecimal,    R.string.unit_duodecimal_short),
        NumberBaseUnit(UnitID.tridecimal,    BigDecimal("13.0"), UnitGroup.NUMBER_BASE,  R.string.unit_tridecimal,    R.string.unit_tridecimal_short),
        NumberBaseUnit(UnitID.tetradecimal,  BigDecimal("14.0"), UnitGroup.NUMBER_BASE,  R.string.unit_tetradecimal,  R.string.unit_tetradecimal_short),
        NumberBaseUnit(UnitID.pentadecimal,  BigDecimal("15.0"), UnitGroup.NUMBER_BASE,  R.string.unit_pentadecimal,  R.string.unit_pentadecimal_short),
        NumberBaseUnit(UnitID.hexadecimal,   BigDecimal("16.0"), UnitGroup.NUMBER_BASE,  R.string.unit_hexadecimal,   R.string.unit_hexadecimal_short),
    )
}
