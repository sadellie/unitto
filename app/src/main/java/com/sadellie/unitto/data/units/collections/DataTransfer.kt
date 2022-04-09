package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import java.math.BigDecimal

val DATA_TRANSFER_COLLECTION: List<AbstractUnit> by lazy {
    listOf(
        MyUnit(MyUnitIDS.bit_per_second,        BigDecimal.valueOf(1),                          UnitGroup.DATA_TRANSFER, R.string.bit_per_second,        R.string.bit_per_second_short),
        MyUnit(MyUnitIDS.kibibit_per_second,    BigDecimal.valueOf(1_024),                      UnitGroup.DATA_TRANSFER, R.string.kibibit_per_second,    R.string.kibibit_per_second_short),
        MyUnit(MyUnitIDS.kilobit_per_second,    BigDecimal.valueOf(1_000),                      UnitGroup.DATA_TRANSFER, R.string.kilobit_per_second,    R.string.kilobit_per_second_short),
        MyUnit(MyUnitIDS.megabit_per_second,    BigDecimal.valueOf(1_000_000),                  UnitGroup.DATA_TRANSFER, R.string.megabit_per_second,    R.string.megabit_per_second_short),
        MyUnit(MyUnitIDS.gigabit_per_second,    BigDecimal.valueOf(1_000_000_000),              UnitGroup.DATA_TRANSFER, R.string.gigabit_per_second,    R.string.gigabit_per_second_short),
        MyUnit(MyUnitIDS.terabit_per_second,    BigDecimal.valueOf(1_000_000_000_000),          UnitGroup.DATA_TRANSFER, R.string.terabit_per_second,    R.string.terabit_per_second_short),
        MyUnit(MyUnitIDS.petabit_per_second,    BigDecimal.valueOf(1_000_000_000_000_000),      UnitGroup.DATA_TRANSFER, R.string.petabit_per_second,    R.string.petabit_per_second_short),
        MyUnit(MyUnitIDS.exabit_per_second,     BigDecimal.valueOf(1_000_000_000_000_000_000),  UnitGroup.DATA_TRANSFER, R.string.exabit_per_second,     R.string.exabit_per_second_short),
        MyUnit(MyUnitIDS.byte_per_second,       BigDecimal.valueOf(8),                          UnitGroup.DATA_TRANSFER, R.string.byte_per_second,       R.string.byte_per_second_short),
        MyUnit(MyUnitIDS.kibibyte_per_second,   BigDecimal.valueOf(8_192),                      UnitGroup.DATA_TRANSFER, R.string.kibibyte_per_second,   R.string.kibibyte_per_second_short),
        MyUnit(MyUnitIDS.kilobyte_per_second,   BigDecimal.valueOf(8_000),                      UnitGroup.DATA_TRANSFER, R.string.kilobyte_per_second,   R.string.kilobyte_per_second_short),
        MyUnit(MyUnitIDS.megabyte_per_second,   BigDecimal.valueOf(8_000_000),                  UnitGroup.DATA_TRANSFER, R.string.megabyte_per_second,   R.string.megabyte_per_second_short),
        MyUnit(MyUnitIDS.gigabyte_per_second,   BigDecimal.valueOf(8_000_000_000),              UnitGroup.DATA_TRANSFER, R.string.gigabyte_per_second,   R.string.gigabyte_per_second_short),
        MyUnit(MyUnitIDS.terabyte_per_second,   BigDecimal.valueOf(8_000_000_000_000),          UnitGroup.DATA_TRANSFER, R.string.terabyte_per_second,   R.string.terabyte_per_second_short),
        MyUnit(MyUnitIDS.petabyte_per_second,   BigDecimal.valueOf(8_000_000_000_000_000),      UnitGroup.DATA_TRANSFER, R.string.petabyte_per_second,   R.string.petabyte_per_second_short),
        MyUnit(MyUnitIDS.exabyte_per_second,    BigDecimal.valueOf(8_000_000_000_000_000_000),  UnitGroup.DATA_TRANSFER, R.string.exabyte_per_second,    R.string.exabyte_per_second_short),
    )
}
