package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import java.math.BigDecimal

val TIME_COLLECTION: List<AbstractUnit> by lazy {
    listOf(
        MyUnit(MyUnitIDS.attosecond,    BigDecimal.valueOf(1),                                  UnitGroup.TIME, R.string.attosecond,    R.string.attosecond_short),
        MyUnit(MyUnitIDS.nanosecond,    BigDecimal.valueOf(1_000_000_000),                      UnitGroup.TIME, R.string.nanosecond,    R.string.nanosecond_short),
        MyUnit(MyUnitIDS.microsecond,   BigDecimal.valueOf(1_000_000_000_000),                  UnitGroup.TIME, R.string.microsecond,   R.string.microsecond_short),
        MyUnit(MyUnitIDS.millisecond,   BigDecimal.valueOf(1_000_000_000_000_000),              UnitGroup.TIME, R.string.millisecond,   R.string.millisecond_short),
        MyUnit(MyUnitIDS.jiffy,         BigDecimal.valueOf(10_000_000_000_000_000),             UnitGroup.TIME, R.string.jiffy,         R.string.jiffy_short),
        MyUnit(MyUnitIDS.second,        BigDecimal.valueOf(1_000_000_000_000_000_000),          UnitGroup.TIME, R.string.second,        R.string.second_short),
        MyUnit(MyUnitIDS.minute,        BigDecimal.valueOf(60_000_000_000_000_000_000.0),       UnitGroup.TIME, R.string.minute,        R.string.minute_short),
        MyUnit(MyUnitIDS.hour,          BigDecimal.valueOf(3_600_000_000_000_000_000_000.0),    UnitGroup.TIME, R.string.hour,          R.string.hour_short),
        MyUnit(MyUnitIDS.day,           BigDecimal.valueOf(86_400_000_000_000_000_000_000.0),   UnitGroup.TIME, R.string.day,           R.string.day_short),
        MyUnit(MyUnitIDS.week,          BigDecimal.valueOf(604_800_000_000_000_000_000_000.0),  UnitGroup.TIME, R.string.week,          R.string.week_short),
    )
}
