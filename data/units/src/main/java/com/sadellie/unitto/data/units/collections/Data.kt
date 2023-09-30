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
import com.sadellie.unitto.data.model.unit.NormalUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import java.math.BigDecimal

internal val dataCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(MyUnitIDS.bit,       BigDecimal.valueOf(1),                          UnitGroup.DATA, R.string.bit,       R.string.bit_short),
        NormalUnit(MyUnitIDS.kibibit,   BigDecimal.valueOf(1_024),                      UnitGroup.DATA, R.string.kibibit,   R.string.kibibit_short),
        NormalUnit(MyUnitIDS.kilobit,   BigDecimal.valueOf(1_000),                      UnitGroup.DATA, R.string.kilobit,   R.string.kilobit_short),
        NormalUnit(MyUnitIDS.megabit,   BigDecimal.valueOf(1_000_000),                  UnitGroup.DATA, R.string.megabit,   R.string.megabit_short),
        NormalUnit(MyUnitIDS.mebibit,   BigDecimal.valueOf(1_048_576),                  UnitGroup.DATA, R.string.mebibit,   R.string.mebibit_short),
        NormalUnit(MyUnitIDS.gigabit,   BigDecimal.valueOf(1_000_000_000),              UnitGroup.DATA, R.string.gigabit,   R.string.gigabit_short),
        NormalUnit(MyUnitIDS.gibibit,   BigDecimal.valueOf(1_073_741_824),              UnitGroup.DATA, R.string.gibibit,   R.string.gibibit_short),
        NormalUnit(MyUnitIDS.terabit,   BigDecimal.valueOf(1_000_000_000_000),          UnitGroup.DATA, R.string.terabit,   R.string.terabit_short),
        NormalUnit(MyUnitIDS.petabit,   BigDecimal.valueOf(1_000_000_000_000_000),      UnitGroup.DATA, R.string.petabit,   R.string.petabit_short),
        NormalUnit(MyUnitIDS.exabit,    BigDecimal.valueOf(1_000_000_000_000_000_000),  UnitGroup.DATA, R.string.exabit,    R.string.exabit_short),
        NormalUnit(MyUnitIDS.byte,      BigDecimal.valueOf(8),                          UnitGroup.DATA, R.string.byte_,     R.string.byte_short),
        NormalUnit(MyUnitIDS.kibibyte,  BigDecimal.valueOf(8_192),                      UnitGroup.DATA, R.string.kibibyte,  R.string.kibibyte_short),
        NormalUnit(MyUnitIDS.kilobyte,  BigDecimal.valueOf(8_000),                      UnitGroup.DATA, R.string.kilobyte,  R.string.kilobyte_short),
        NormalUnit(MyUnitIDS.megabyte,  BigDecimal.valueOf(8_000_000),                  UnitGroup.DATA, R.string.megabyte,  R.string.megabyte_short),
        NormalUnit(MyUnitIDS.mebibyte,  BigDecimal.valueOf(8_388_608),                  UnitGroup.DATA, R.string.mebibyte,  R.string.mebibyte_short),
        NormalUnit(MyUnitIDS.gigabyte,  BigDecimal.valueOf(8_000_000_000),              UnitGroup.DATA, R.string.gigabyte,  R.string.gigabyte_short),
        NormalUnit(MyUnitIDS.gibibyte,  BigDecimal.valueOf(8_589_934_592),              UnitGroup.DATA, R.string.gibibyte,  R.string.gibibyte_short),
        NormalUnit(MyUnitIDS.terabyte,  BigDecimal.valueOf(8_000_000_000_000),          UnitGroup.DATA, R.string.terabyte,  R.string.terabyte_short),
        NormalUnit(MyUnitIDS.petabyte,  BigDecimal.valueOf(8_000_000_000_000_000),      UnitGroup.DATA, R.string.petabyte,  R.string.petabyte_short),
        NormalUnit(MyUnitIDS.exabyte,   BigDecimal.valueOf(8_000_000_000_000_000_000),  UnitGroup.DATA, R.string.exabyte,   R.string.exabyte_short),
    )
}
