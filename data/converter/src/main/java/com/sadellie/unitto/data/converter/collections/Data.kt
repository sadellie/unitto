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
import com.sadellie.unitto.data.model.converter.unit.NormalUnit
import java.math.BigDecimal

internal val dataCollection: List<BasicUnit> by lazy {
    listOf(
        NormalUnit(UnitID.bit,       BigDecimal("1"),                          UnitGroup.DATA, R.string.unit_bit,       R.string.unit_bit_short),
        NormalUnit(UnitID.kibibit,   BigDecimal("1024"),                      UnitGroup.DATA, R.string.unit_kibibit,   R.string.unit_kibibit_short),
        NormalUnit(UnitID.kilobit,   BigDecimal("1000"),                      UnitGroup.DATA, R.string.unit_kilobit,   R.string.unit_kilobit_short),
        NormalUnit(UnitID.megabit,   BigDecimal("1000000"),                  UnitGroup.DATA, R.string.unit_megabit,   R.string.unit_megabit_short),
        NormalUnit(UnitID.mebibit,   BigDecimal("1048576"),                  UnitGroup.DATA, R.string.unit_mebibit,   R.string.unit_mebibit_short),
        NormalUnit(UnitID.gigabit,   BigDecimal("1000000000"),              UnitGroup.DATA, R.string.unit_gigabit,   R.string.unit_gigabit_short),
        NormalUnit(UnitID.gibibit,   BigDecimal("1073741824"),              UnitGroup.DATA, R.string.unit_gibibit,   R.string.unit_gibibit_short),
        NormalUnit(UnitID.terabit,   BigDecimal("1000000000000"),          UnitGroup.DATA, R.string.unit_terabit,   R.string.unit_terabit_short),
        NormalUnit(UnitID.petabit,   BigDecimal("1000000000000000"),      UnitGroup.DATA, R.string.unit_petabit,   R.string.unit_petabit_short),
        NormalUnit(UnitID.exabit,    BigDecimal("1000000000000000000"),  UnitGroup.DATA, R.string.unit_exabit,    R.string.unit_exabit_short),
        NormalUnit(UnitID.byte,      BigDecimal("8"),                          UnitGroup.DATA, R.string.unit_byte,     R.string.unit_byte_short),
        NormalUnit(UnitID.kibibyte,  BigDecimal("8192"),                      UnitGroup.DATA, R.string.unit_kibibyte,  R.string.unit_kibibyte_short),
        NormalUnit(UnitID.kilobyte,  BigDecimal("8000"),                      UnitGroup.DATA, R.string.unit_kilobyte,  R.string.unit_kilobyte_short),
        NormalUnit(UnitID.megabyte,  BigDecimal("8000000"),                  UnitGroup.DATA, R.string.unit_megabyte,  R.string.unit_megabyte_short),
        NormalUnit(UnitID.mebibyte,  BigDecimal("8388608"),                  UnitGroup.DATA, R.string.unit_mebibyte,  R.string.unit_mebibyte_short),
        NormalUnit(UnitID.gigabyte,  BigDecimal("8000000000"),              UnitGroup.DATA, R.string.unit_gigabyte,  R.string.unit_gigabyte_short),
        NormalUnit(UnitID.gibibyte,  BigDecimal("8589934592"),              UnitGroup.DATA, R.string.unit_gibibyte,  R.string.unit_gibibyte_short),
        NormalUnit(UnitID.terabyte,  BigDecimal("8000000000000"),          UnitGroup.DATA, R.string.unit_terabyte,  R.string.unit_terabyte_short),
        NormalUnit(UnitID.petabyte,  BigDecimal("8000000000000000"),      UnitGroup.DATA, R.string.unit_petabyte,  R.string.unit_petabyte_short),
        NormalUnit(UnitID.exabyte,   BigDecimal("8000000000000000000"),  UnitGroup.DATA, R.string.unit_exabyte,   R.string.unit_exabyte_short),
    )
}
