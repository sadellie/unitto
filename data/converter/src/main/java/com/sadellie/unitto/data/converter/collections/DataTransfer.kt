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

package com.sadellie.unitto.data.converter.collections

import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.NormalUnit
import com.sadellie.unitto.data.converter.MyUnitIDS
import java.math.BigDecimal

internal val dataTransferCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(MyUnitIDS.bit_per_second,        BigDecimal.valueOf(1),                          UnitGroup.DATA_TRANSFER, R.string.unit_bit_per_second,        R.string.unit_bit_per_second_short),
        NormalUnit(MyUnitIDS.kibibit_per_second,    BigDecimal.valueOf(1_024),                      UnitGroup.DATA_TRANSFER, R.string.unit_kibibit_per_second,    R.string.unit_kibibit_per_second_short),
        NormalUnit(MyUnitIDS.kilobit_per_second,    BigDecimal.valueOf(1_000),                      UnitGroup.DATA_TRANSFER, R.string.unit_kilobit_per_second,    R.string.unit_kilobit_per_second_short),
        NormalUnit(MyUnitIDS.megabit_per_second,    BigDecimal.valueOf(1_000_000),                  UnitGroup.DATA_TRANSFER, R.string.unit_megabit_per_second,    R.string.unit_megabit_per_second_short),
        NormalUnit(MyUnitIDS.mebibit_per_second,    BigDecimal.valueOf(1_048_576),                  UnitGroup.DATA_TRANSFER, R.string.unit_mebibit_per_second,    R.string.unit_mebibit_per_second_short),
        NormalUnit(MyUnitIDS.gigabit_per_second,    BigDecimal.valueOf(1_000_000_000),              UnitGroup.DATA_TRANSFER, R.string.unit_gigabit_per_second,    R.string.unit_gigabit_per_second_short),
        NormalUnit(MyUnitIDS.gibibit_per_second,    BigDecimal.valueOf(1_073_741_824),              UnitGroup.DATA_TRANSFER, R.string.unit_gibibit_per_second,    R.string.unit_gibibit_per_second_short),
        NormalUnit(MyUnitIDS.terabit_per_second,    BigDecimal.valueOf(1_000_000_000_000),          UnitGroup.DATA_TRANSFER, R.string.unit_terabit_per_second,    R.string.unit_terabit_per_second_short),
        NormalUnit(MyUnitIDS.petabit_per_second,    BigDecimal.valueOf(1_000_000_000_000_000),      UnitGroup.DATA_TRANSFER, R.string.unit_petabit_per_second,    R.string.unit_petabit_per_second_short),
        NormalUnit(MyUnitIDS.exabit_per_second,     BigDecimal.valueOf(1_000_000_000_000_000_000),  UnitGroup.DATA_TRANSFER, R.string.unit_exabit_per_second,     R.string.unit_exabit_per_second_short),
        NormalUnit(MyUnitIDS.byte_per_second,       BigDecimal.valueOf(8),                          UnitGroup.DATA_TRANSFER, R.string.unit_byte_per_second,       R.string.unit_byte_per_second_short),
        NormalUnit(MyUnitIDS.kibibyte_per_second,   BigDecimal.valueOf(8_192),                      UnitGroup.DATA_TRANSFER, R.string.unit_kibibyte_per_second,   R.string.unit_kibibyte_per_second_short),
        NormalUnit(MyUnitIDS.kilobyte_per_second,   BigDecimal.valueOf(8_000),                      UnitGroup.DATA_TRANSFER, R.string.unit_kilobyte_per_second,   R.string.unit_kilobyte_per_second_short),
        NormalUnit(MyUnitIDS.megabyte_per_second,   BigDecimal.valueOf(8_000_000),                  UnitGroup.DATA_TRANSFER, R.string.unit_megabyte_per_second,   R.string.unit_megabyte_per_second_short),
        NormalUnit(MyUnitIDS.mebibyte_per_second,   BigDecimal.valueOf(8_388_608),                  UnitGroup.DATA_TRANSFER, R.string.unit_mebibyte_per_second,   R.string.unit_mebibyte_per_second_short),
        NormalUnit(MyUnitIDS.gigabyte_per_second,   BigDecimal.valueOf(8_000_000_000),              UnitGroup.DATA_TRANSFER, R.string.unit_gigabyte_per_second,   R.string.unit_gigabyte_per_second_short),
        NormalUnit(MyUnitIDS.gibibyte_per_second,   BigDecimal.valueOf(8_589_934_592),              UnitGroup.DATA_TRANSFER, R.string.unit_gibibyte_per_second,   R.string.unit_gibibyte_per_second_short),
        NormalUnit(MyUnitIDS.terabyte_per_second,   BigDecimal.valueOf(8_000_000_000_000),          UnitGroup.DATA_TRANSFER, R.string.unit_terabyte_per_second,   R.string.unit_terabyte_per_second_short),
        NormalUnit(MyUnitIDS.petabyte_per_second,   BigDecimal.valueOf(8_000_000_000_000_000),      UnitGroup.DATA_TRANSFER, R.string.unit_petabyte_per_second,   R.string.unit_petabyte_per_second_short),
        NormalUnit(MyUnitIDS.exabyte_per_second,    BigDecimal.valueOf(8_000_000_000_000_000_000),  UnitGroup.DATA_TRANSFER, R.string.unit_exabyte_per_second,    R.string.unit_exabyte_per_second_short),
    )
}
