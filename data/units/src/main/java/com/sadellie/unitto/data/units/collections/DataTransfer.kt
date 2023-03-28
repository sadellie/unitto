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
import com.sadellie.unitto.data.model.DefaultUnit
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.R
import java.math.BigDecimal

internal val dataTransferCollection: List<AbstractUnit> by lazy {
    listOf(
        DefaultUnit(MyUnitIDS.bit_per_second,        BigDecimal.valueOf(1),                          UnitGroup.DATA_TRANSFER, R.string.bit_per_second,        R.string.bit_per_second_short),
        DefaultUnit(MyUnitIDS.kibibit_per_second,    BigDecimal.valueOf(1_024),                      UnitGroup.DATA_TRANSFER, R.string.kibibit_per_second,    R.string.kibibit_per_second_short),
        DefaultUnit(MyUnitIDS.kilobit_per_second,    BigDecimal.valueOf(1_000),                      UnitGroup.DATA_TRANSFER, R.string.kilobit_per_second,    R.string.kilobit_per_second_short),
        DefaultUnit(MyUnitIDS.megabit_per_second,    BigDecimal.valueOf(1_000_000),                  UnitGroup.DATA_TRANSFER, R.string.megabit_per_second,    R.string.megabit_per_second_short),
        DefaultUnit(MyUnitIDS.mebibit_per_second,    BigDecimal.valueOf(1_048_576),                  UnitGroup.DATA_TRANSFER, R.string.mebibit_per_second,    R.string.mebibit_per_second_short),
        DefaultUnit(MyUnitIDS.gigabit_per_second,    BigDecimal.valueOf(1_000_000_000),              UnitGroup.DATA_TRANSFER, R.string.gigabit_per_second,    R.string.gigabit_per_second_short),
        DefaultUnit(MyUnitIDS.terabit_per_second,    BigDecimal.valueOf(1_000_000_000_000),          UnitGroup.DATA_TRANSFER, R.string.terabit_per_second,    R.string.terabit_per_second_short),
        DefaultUnit(MyUnitIDS.petabit_per_second,    BigDecimal.valueOf(1_000_000_000_000_000),      UnitGroup.DATA_TRANSFER, R.string.petabit_per_second,    R.string.petabit_per_second_short),
        DefaultUnit(MyUnitIDS.exabit_per_second,     BigDecimal.valueOf(1_000_000_000_000_000_000),  UnitGroup.DATA_TRANSFER, R.string.exabit_per_second,     R.string.exabit_per_second_short),
        DefaultUnit(MyUnitIDS.byte_per_second,       BigDecimal.valueOf(8),                          UnitGroup.DATA_TRANSFER, R.string.byte_per_second,       R.string.byte_per_second_short),
        DefaultUnit(MyUnitIDS.kibibyte_per_second,   BigDecimal.valueOf(8_192),                      UnitGroup.DATA_TRANSFER, R.string.kibibyte_per_second,   R.string.kibibyte_per_second_short),
        DefaultUnit(MyUnitIDS.kilobyte_per_second,   BigDecimal.valueOf(8_000),                      UnitGroup.DATA_TRANSFER, R.string.kilobyte_per_second,   R.string.kilobyte_per_second_short),
        DefaultUnit(MyUnitIDS.megabyte_per_second,   BigDecimal.valueOf(8_000_000),                  UnitGroup.DATA_TRANSFER, R.string.megabyte_per_second,   R.string.megabyte_per_second_short),
        DefaultUnit(MyUnitIDS.mebibyte_per_second,   BigDecimal.valueOf(8_388_608),                  UnitGroup.DATA_TRANSFER, R.string.mebibyte_per_second,   R.string.mebibyte_per_second_short),
        DefaultUnit(MyUnitIDS.gigabyte_per_second,   BigDecimal.valueOf(8_000_000_000),              UnitGroup.DATA_TRANSFER, R.string.gigabyte_per_second,   R.string.gigabyte_per_second_short),
        DefaultUnit(MyUnitIDS.terabyte_per_second,   BigDecimal.valueOf(8_000_000_000_000),          UnitGroup.DATA_TRANSFER, R.string.terabyte_per_second,   R.string.terabyte_per_second_short),
        DefaultUnit(MyUnitIDS.petabyte_per_second,   BigDecimal.valueOf(8_000_000_000_000_000),      UnitGroup.DATA_TRANSFER, R.string.petabyte_per_second,   R.string.petabyte_per_second_short),
        DefaultUnit(MyUnitIDS.exabyte_per_second,    BigDecimal.valueOf(8_000_000_000_000_000_000),  UnitGroup.DATA_TRANSFER, R.string.exabyte_per_second,    R.string.exabyte_per_second_short),
    )
}