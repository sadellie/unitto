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

import com.sadellie.unitto.data.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import java.math.BigDecimal

internal val dataTransferCollection: List<AbstractUnit> by lazy {
    listOf(
        MyUnit(MyUnitIDS.bit_per_second,        BigDecimal.valueOf(1),                          UnitGroup.DATA_TRANSFER, R.string.bit_per_second,        R.string.bit_per_second_short),
        MyUnit(MyUnitIDS.kibibit_per_second,    BigDecimal.valueOf(1_024),                      UnitGroup.DATA_TRANSFER, R.string.kibibit_per_second,    R.string.kibibit_per_second_short),
        MyUnit(MyUnitIDS.kilobit_per_second,    BigDecimal.valueOf(1_000),                      UnitGroup.DATA_TRANSFER, R.string.kilobit_per_second,    R.string.kilobit_per_second_short),
        MyUnit(MyUnitIDS.megabit_per_second,    BigDecimal.valueOf(1_000_000),                  UnitGroup.DATA_TRANSFER, R.string.megabit_per_second,    R.string.megabit_per_second_short),
        MyUnit(MyUnitIDS.mebibit_per_second,    BigDecimal.valueOf(1_048_576),                  UnitGroup.DATA_TRANSFER, R.string.mebibit_per_second,    R.string.mebibit_per_second_short),
        MyUnit(MyUnitIDS.gigabit_per_second,    BigDecimal.valueOf(1_000_000_000),              UnitGroup.DATA_TRANSFER, R.string.gigabit_per_second,    R.string.gigabit_per_second_short),
        MyUnit(MyUnitIDS.terabit_per_second,    BigDecimal.valueOf(1_000_000_000_000),          UnitGroup.DATA_TRANSFER, R.string.terabit_per_second,    R.string.terabit_per_second_short),
        MyUnit(MyUnitIDS.petabit_per_second,    BigDecimal.valueOf(1_000_000_000_000_000),      UnitGroup.DATA_TRANSFER, R.string.petabit_per_second,    R.string.petabit_per_second_short),
        MyUnit(MyUnitIDS.exabit_per_second,     BigDecimal.valueOf(1_000_000_000_000_000_000),  UnitGroup.DATA_TRANSFER, R.string.exabit_per_second,     R.string.exabit_per_second_short),
        MyUnit(MyUnitIDS.byte_per_second,       BigDecimal.valueOf(8),                          UnitGroup.DATA_TRANSFER, R.string.byte_per_second,       R.string.byte_per_second_short),
        MyUnit(MyUnitIDS.kibibyte_per_second,   BigDecimal.valueOf(8_192),                      UnitGroup.DATA_TRANSFER, R.string.kibibyte_per_second,   R.string.kibibyte_per_second_short),
        MyUnit(MyUnitIDS.kilobyte_per_second,   BigDecimal.valueOf(8_000),                      UnitGroup.DATA_TRANSFER, R.string.kilobyte_per_second,   R.string.kilobyte_per_second_short),
        MyUnit(MyUnitIDS.megabyte_per_second,   BigDecimal.valueOf(8_000_000),                  UnitGroup.DATA_TRANSFER, R.string.megabyte_per_second,   R.string.megabyte_per_second_short),
        MyUnit(MyUnitIDS.mebibyte_per_second,   BigDecimal.valueOf(8_388_608),                  UnitGroup.DATA_TRANSFER, R.string.mebibyte_per_second,   R.string.mebibyte_per_second_short),
        MyUnit(MyUnitIDS.gigabyte_per_second,   BigDecimal.valueOf(8_000_000_000),              UnitGroup.DATA_TRANSFER, R.string.gigabyte_per_second,   R.string.gigabyte_per_second_short),
        MyUnit(MyUnitIDS.terabyte_per_second,   BigDecimal.valueOf(8_000_000_000_000),          UnitGroup.DATA_TRANSFER, R.string.terabyte_per_second,   R.string.terabyte_per_second_short),
        MyUnit(MyUnitIDS.petabyte_per_second,   BigDecimal.valueOf(8_000_000_000_000_000),      UnitGroup.DATA_TRANSFER, R.string.petabyte_per_second,   R.string.petabyte_per_second_short),
        MyUnit(MyUnitIDS.exabyte_per_second,    BigDecimal.valueOf(8_000_000_000_000_000_000),  UnitGroup.DATA_TRANSFER, R.string.exabyte_per_second,    R.string.exabyte_per_second_short),
    )
}