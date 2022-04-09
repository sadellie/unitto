package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import java.math.BigDecimal

val DATA_COLLECTION: List<AbstractUnit> by lazy {
    listOf(
        MyUnit(MyUnitIDS.bit,       BigDecimal.valueOf(1),                          UnitGroup.DATA, R.string.bit,       R.string.bit_short),
        MyUnit(MyUnitIDS.kibibit,   BigDecimal.valueOf(1_024),                      UnitGroup.DATA, R.string.kibibit,   R.string.kibibit_short),
        MyUnit(MyUnitIDS.kilobit,   BigDecimal.valueOf(1_000),                      UnitGroup.DATA, R.string.kilobit,   R.string.kilobit_short),
        MyUnit(MyUnitIDS.megabit,   BigDecimal.valueOf(1_000_000),                  UnitGroup.DATA, R.string.megabit,   R.string.megabit_short),
        MyUnit(MyUnitIDS.gigabit,   BigDecimal.valueOf(1_000_000_000),              UnitGroup.DATA, R.string.gigabit,   R.string.gigabit_short),
        MyUnit(MyUnitIDS.terabit,   BigDecimal.valueOf(1_000_000_000_000),          UnitGroup.DATA, R.string.terabit,   R.string.terabit_short),
        MyUnit(MyUnitIDS.petabit,   BigDecimal.valueOf(1_000_000_000_000_000),      UnitGroup.DATA, R.string.petabit,   R.string.petabit_short),
        MyUnit(MyUnitIDS.exabit,    BigDecimal.valueOf(1_000_000_000_000_000_000),  UnitGroup.DATA, R.string.exabit,    R.string.exabit_short),
        MyUnit(MyUnitIDS.byte,      BigDecimal.valueOf(8),                          UnitGroup.DATA, R.string.byte_,     R.string.byte_short),
        MyUnit(MyUnitIDS.kibibyte,  BigDecimal.valueOf(8_192),                      UnitGroup.DATA, R.string.kibibyte,  R.string.kibibyte_short),
        MyUnit(MyUnitIDS.kilobyte,  BigDecimal.valueOf(8_000),                      UnitGroup.DATA, R.string.kilobyte,  R.string.kilobyte_short),
        MyUnit(MyUnitIDS.megabyte,  BigDecimal.valueOf(8_000_000),                  UnitGroup.DATA, R.string.megabyte,  R.string.megabyte_short),
        MyUnit(MyUnitIDS.gigabyte,  BigDecimal.valueOf(8_000_000_000),              UnitGroup.DATA, R.string.gigabyte,  R.string.gigabyte_short),
        MyUnit(MyUnitIDS.terabyte,  BigDecimal.valueOf(8_000_000_000_000),          UnitGroup.DATA, R.string.terabyte,  R.string.terabyte_short),
        MyUnit(MyUnitIDS.petabyte,  BigDecimal.valueOf(8_000_000_000_000_000),      UnitGroup.DATA, R.string.petabyte,  R.string.petabyte_short),
        MyUnit(MyUnitIDS.exabyte,   BigDecimal.valueOf(8_000_000_000_000_000_000),  UnitGroup.DATA, R.string.exabyte,   R.string.exabyte_short),
    )
}
