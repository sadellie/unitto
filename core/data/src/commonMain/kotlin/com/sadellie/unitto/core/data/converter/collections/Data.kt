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
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.unit_bit
import unitto.core.common.generated.resources.unit_bit_short
import unitto.core.common.generated.resources.unit_byte
import unitto.core.common.generated.resources.unit_byte_short
import unitto.core.common.generated.resources.unit_exabit
import unitto.core.common.generated.resources.unit_exabit_short
import unitto.core.common.generated.resources.unit_exabyte
import unitto.core.common.generated.resources.unit_exabyte_short
import unitto.core.common.generated.resources.unit_gibibit
import unitto.core.common.generated.resources.unit_gibibit_short
import unitto.core.common.generated.resources.unit_gibibyte
import unitto.core.common.generated.resources.unit_gibibyte_short
import unitto.core.common.generated.resources.unit_gigabit
import unitto.core.common.generated.resources.unit_gigabit_short
import unitto.core.common.generated.resources.unit_gigabyte
import unitto.core.common.generated.resources.unit_gigabyte_short
import unitto.core.common.generated.resources.unit_kibibit
import unitto.core.common.generated.resources.unit_kibibit_short
import unitto.core.common.generated.resources.unit_kibibyte
import unitto.core.common.generated.resources.unit_kibibyte_short
import unitto.core.common.generated.resources.unit_kilobit
import unitto.core.common.generated.resources.unit_kilobit_short
import unitto.core.common.generated.resources.unit_kilobyte
import unitto.core.common.generated.resources.unit_kilobyte_short
import unitto.core.common.generated.resources.unit_mebibit
import unitto.core.common.generated.resources.unit_mebibit_short
import unitto.core.common.generated.resources.unit_mebibyte
import unitto.core.common.generated.resources.unit_mebibyte_short
import unitto.core.common.generated.resources.unit_megabit
import unitto.core.common.generated.resources.unit_megabit_short
import unitto.core.common.generated.resources.unit_megabyte
import unitto.core.common.generated.resources.unit_megabyte_short
import unitto.core.common.generated.resources.unit_petabit
import unitto.core.common.generated.resources.unit_petabit_short
import unitto.core.common.generated.resources.unit_petabyte
import unitto.core.common.generated.resources.unit_petabyte_short
import unitto.core.common.generated.resources.unit_terabit
import unitto.core.common.generated.resources.unit_terabit_short
import unitto.core.common.generated.resources.unit_terabyte
import unitto.core.common.generated.resources.unit_terabyte_short

internal val dataCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.bit,
      KBigDecimal("1"),
      UnitGroup.DATA,
      Res.string.unit_bit,
      Res.string.unit_bit_short,
    ),
    NormalUnit(
      UnitID.kibibit,
      KBigDecimal("1024"),
      UnitGroup.DATA,
      Res.string.unit_kibibit,
      Res.string.unit_kibibit_short,
    ),
    NormalUnit(
      UnitID.kilobit,
      KBigDecimal("1000"),
      UnitGroup.DATA,
      Res.string.unit_kilobit,
      Res.string.unit_kilobit_short,
    ),
    NormalUnit(
      UnitID.megabit,
      KBigDecimal("1000000"),
      UnitGroup.DATA,
      Res.string.unit_megabit,
      Res.string.unit_megabit_short,
    ),
    NormalUnit(
      UnitID.mebibit,
      KBigDecimal("1048576"),
      UnitGroup.DATA,
      Res.string.unit_mebibit,
      Res.string.unit_mebibit_short,
    ),
    NormalUnit(
      UnitID.gigabit,
      KBigDecimal("1000000000"),
      UnitGroup.DATA,
      Res.string.unit_gigabit,
      Res.string.unit_gigabit_short,
    ),
    NormalUnit(
      UnitID.gibibit,
      KBigDecimal("1073741824"),
      UnitGroup.DATA,
      Res.string.unit_gibibit,
      Res.string.unit_gibibit_short,
    ),
    NormalUnit(
      UnitID.terabit,
      KBigDecimal("1000000000000"),
      UnitGroup.DATA,
      Res.string.unit_terabit,
      Res.string.unit_terabit_short,
    ),
    NormalUnit(
      UnitID.petabit,
      KBigDecimal("1000000000000000"),
      UnitGroup.DATA,
      Res.string.unit_petabit,
      Res.string.unit_petabit_short,
    ),
    NormalUnit(
      UnitID.exabit,
      KBigDecimal("1000000000000000000"),
      UnitGroup.DATA,
      Res.string.unit_exabit,
      Res.string.unit_exabit_short,
    ),
    NormalUnit(
      UnitID.byte,
      KBigDecimal("8"),
      UnitGroup.DATA,
      Res.string.unit_byte,
      Res.string.unit_byte_short,
    ),
    NormalUnit(
      UnitID.kibibyte,
      KBigDecimal("8192"),
      UnitGroup.DATA,
      Res.string.unit_kibibyte,
      Res.string.unit_kibibyte_short,
    ),
    NormalUnit(
      UnitID.kilobyte,
      KBigDecimal("8000"),
      UnitGroup.DATA,
      Res.string.unit_kilobyte,
      Res.string.unit_kilobyte_short,
    ),
    NormalUnit(
      UnitID.megabyte,
      KBigDecimal("8000000"),
      UnitGroup.DATA,
      Res.string.unit_megabyte,
      Res.string.unit_megabyte_short,
    ),
    NormalUnit(
      UnitID.mebibyte,
      KBigDecimal("8388608"),
      UnitGroup.DATA,
      Res.string.unit_mebibyte,
      Res.string.unit_mebibyte_short,
    ),
    NormalUnit(
      UnitID.gigabyte,
      KBigDecimal("8000000000"),
      UnitGroup.DATA,
      Res.string.unit_gigabyte,
      Res.string.unit_gigabyte_short,
    ),
    NormalUnit(
      UnitID.gibibyte,
      KBigDecimal("8589934592"),
      UnitGroup.DATA,
      Res.string.unit_gibibyte,
      Res.string.unit_gibibyte_short,
    ),
    NormalUnit(
      UnitID.terabyte,
      KBigDecimal("8000000000000"),
      UnitGroup.DATA,
      Res.string.unit_terabyte,
      Res.string.unit_terabyte_short,
    ),
    NormalUnit(
      UnitID.petabyte,
      KBigDecimal("8000000000000000"),
      UnitGroup.DATA,
      Res.string.unit_petabyte,
      Res.string.unit_petabyte_short,
    ),
    NormalUnit(
      UnitID.exabyte,
      KBigDecimal("8000000000000000000"),
      UnitGroup.DATA,
      Res.string.unit_exabyte,
      Res.string.unit_exabyte_short,
    ),
  )
}
