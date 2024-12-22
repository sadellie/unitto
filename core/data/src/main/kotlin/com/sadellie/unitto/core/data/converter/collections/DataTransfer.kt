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

package com.sadellie.unitto.core.data.converter.collections

import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import java.math.BigDecimal

internal val dataTransferCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.bit_per_second,
      BigDecimal("1"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_bit_per_second,
      R.string.unit_bit_per_second_short,
    ),
    NormalUnit(
      UnitID.kibibit_per_second,
      BigDecimal("1024"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_kibibit_per_second,
      R.string.unit_kibibit_per_second_short,
    ),
    NormalUnit(
      UnitID.kilobit_per_second,
      BigDecimal("1000"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_kilobit_per_second,
      R.string.unit_kilobit_per_second_short,
    ),
    NormalUnit(
      UnitID.megabit_per_second,
      BigDecimal("1000000"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_megabit_per_second,
      R.string.unit_megabit_per_second_short,
    ),
    NormalUnit(
      UnitID.mebibit_per_second,
      BigDecimal("1048576"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_mebibit_per_second,
      R.string.unit_mebibit_per_second_short,
    ),
    NormalUnit(
      UnitID.gigabit_per_second,
      BigDecimal("1000000000"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_gigabit_per_second,
      R.string.unit_gigabit_per_second_short,
    ),
    NormalUnit(
      UnitID.gibibit_per_second,
      BigDecimal("1073741824"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_gibibit_per_second,
      R.string.unit_gibibit_per_second_short,
    ),
    NormalUnit(
      UnitID.terabit_per_second,
      BigDecimal("1000000000000"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_terabit_per_second,
      R.string.unit_terabit_per_second_short,
    ),
    NormalUnit(
      UnitID.petabit_per_second,
      BigDecimal("1000000000000000"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_petabit_per_second,
      R.string.unit_petabit_per_second_short,
    ),
    NormalUnit(
      UnitID.exabit_per_second,
      BigDecimal("1000000000000000000"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_exabit_per_second,
      R.string.unit_exabit_per_second_short,
    ),
    NormalUnit(
      UnitID.byte_per_second,
      BigDecimal("8"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_byte_per_second,
      R.string.unit_byte_per_second_short,
    ),
    NormalUnit(
      UnitID.kibibyte_per_second,
      BigDecimal("8192"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_kibibyte_per_second,
      R.string.unit_kibibyte_per_second_short,
    ),
    NormalUnit(
      UnitID.kilobyte_per_second,
      BigDecimal("8000"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_kilobyte_per_second,
      R.string.unit_kilobyte_per_second_short,
    ),
    NormalUnit(
      UnitID.megabyte_per_second,
      BigDecimal("8000000"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_megabyte_per_second,
      R.string.unit_megabyte_per_second_short,
    ),
    NormalUnit(
      UnitID.mebibyte_per_second,
      BigDecimal("8388608"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_mebibyte_per_second,
      R.string.unit_mebibyte_per_second_short,
    ),
    NormalUnit(
      UnitID.gigabyte_per_second,
      BigDecimal("8000000000"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_gigabyte_per_second,
      R.string.unit_gigabyte_per_second_short,
    ),
    NormalUnit(
      UnitID.gibibyte_per_second,
      BigDecimal("8589934592"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_gibibyte_per_second,
      R.string.unit_gibibyte_per_second_short,
    ),
    NormalUnit(
      UnitID.terabyte_per_second,
      BigDecimal("8000000000000"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_terabyte_per_second,
      R.string.unit_terabyte_per_second_short,
    ),
    NormalUnit(
      UnitID.petabyte_per_second,
      BigDecimal("8000000000000000"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_petabyte_per_second,
      R.string.unit_petabyte_per_second_short,
    ),
    NormalUnit(
      UnitID.exabyte_per_second,
      BigDecimal("8000000000000000000"),
      UnitGroup.DATA_TRANSFER,
      R.string.unit_exabyte_per_second,
      R.string.unit_exabyte_per_second_short,
    ),
  )
}
