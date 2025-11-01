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
import com.sadellie.unitto.core.model.converter.unit.NumberBaseUnit
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.unit_binary
import unitto.core.common.generated.resources.unit_binary_short
import unitto.core.common.generated.resources.unit_decimal
import unitto.core.common.generated.resources.unit_decimal_short
import unitto.core.common.generated.resources.unit_duodecimal
import unitto.core.common.generated.resources.unit_duodecimal_short
import unitto.core.common.generated.resources.unit_hexadecimal
import unitto.core.common.generated.resources.unit_hexadecimal_short
import unitto.core.common.generated.resources.unit_nonary
import unitto.core.common.generated.resources.unit_nonary_short
import unitto.core.common.generated.resources.unit_octal
import unitto.core.common.generated.resources.unit_octal_short
import unitto.core.common.generated.resources.unit_pentadecimal
import unitto.core.common.generated.resources.unit_pentadecimal_short
import unitto.core.common.generated.resources.unit_quaternary
import unitto.core.common.generated.resources.unit_quaternary_short
import unitto.core.common.generated.resources.unit_quinary
import unitto.core.common.generated.resources.unit_quinary_short
import unitto.core.common.generated.resources.unit_senary
import unitto.core.common.generated.resources.unit_senary_short
import unitto.core.common.generated.resources.unit_septenary
import unitto.core.common.generated.resources.unit_septenary_short
import unitto.core.common.generated.resources.unit_ternary
import unitto.core.common.generated.resources.unit_ternary_short
import unitto.core.common.generated.resources.unit_tetradecimal
import unitto.core.common.generated.resources.unit_tetradecimal_short
import unitto.core.common.generated.resources.unit_tridecimal
import unitto.core.common.generated.resources.unit_tridecimal_short
import unitto.core.common.generated.resources.unit_undecimal
import unitto.core.common.generated.resources.unit_undecimal_short

internal val numberBaseCollection: List<BasicUnit> by lazy {
  listOf(
    NumberBaseUnit(
      UnitID.binary,
      KBigDecimal("2.0"),
      UnitGroup.NUMBER_BASE,
      Res.string.unit_binary,
      Res.string.unit_binary_short,
    ),
    NumberBaseUnit(
      UnitID.ternary,
      KBigDecimal("3.0"),
      UnitGroup.NUMBER_BASE,
      Res.string.unit_ternary,
      Res.string.unit_ternary_short,
    ),
    NumberBaseUnit(
      UnitID.quaternary,
      KBigDecimal("4.0"),
      UnitGroup.NUMBER_BASE,
      Res.string.unit_quaternary,
      Res.string.unit_quaternary_short,
    ),
    NumberBaseUnit(
      UnitID.quinary,
      KBigDecimal("5.0"),
      UnitGroup.NUMBER_BASE,
      Res.string.unit_quinary,
      Res.string.unit_quinary_short,
    ),
    NumberBaseUnit(
      UnitID.senary,
      KBigDecimal("6.0"),
      UnitGroup.NUMBER_BASE,
      Res.string.unit_senary,
      Res.string.unit_senary_short,
    ),
    NumberBaseUnit(
      UnitID.septenary,
      KBigDecimal("7.0"),
      UnitGroup.NUMBER_BASE,
      Res.string.unit_septenary,
      Res.string.unit_septenary_short,
    ),
    NumberBaseUnit(
      UnitID.octal,
      KBigDecimal("8.0"),
      UnitGroup.NUMBER_BASE,
      Res.string.unit_octal,
      Res.string.unit_octal_short,
    ),
    NumberBaseUnit(
      UnitID.nonary,
      KBigDecimal("9.0"),
      UnitGroup.NUMBER_BASE,
      Res.string.unit_nonary,
      Res.string.unit_nonary_short,
    ),
    NumberBaseUnit(
      UnitID.decimal,
      KBigDecimal("10.0"),
      UnitGroup.NUMBER_BASE,
      Res.string.unit_decimal,
      Res.string.unit_decimal_short,
    ),
    NumberBaseUnit(
      UnitID.undecimal,
      KBigDecimal("11.0"),
      UnitGroup.NUMBER_BASE,
      Res.string.unit_undecimal,
      Res.string.unit_undecimal_short,
    ),
    NumberBaseUnit(
      UnitID.duodecimal,
      KBigDecimal("12.0"),
      UnitGroup.NUMBER_BASE,
      Res.string.unit_duodecimal,
      Res.string.unit_duodecimal_short,
    ),
    NumberBaseUnit(
      UnitID.tridecimal,
      KBigDecimal("13.0"),
      UnitGroup.NUMBER_BASE,
      Res.string.unit_tridecimal,
      Res.string.unit_tridecimal_short,
    ),
    NumberBaseUnit(
      UnitID.tetradecimal,
      KBigDecimal("14.0"),
      UnitGroup.NUMBER_BASE,
      Res.string.unit_tetradecimal,
      Res.string.unit_tetradecimal_short,
    ),
    NumberBaseUnit(
      UnitID.pentadecimal,
      KBigDecimal("15.0"),
      UnitGroup.NUMBER_BASE,
      Res.string.unit_pentadecimal,
      Res.string.unit_pentadecimal_short,
    ),
    NumberBaseUnit(
      UnitID.hexadecimal,
      KBigDecimal("16.0"),
      UnitGroup.NUMBER_BASE,
      Res.string.unit_hexadecimal,
      Res.string.unit_hexadecimal_short,
    ),
  )
}
