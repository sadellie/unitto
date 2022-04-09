package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import java.math.BigDecimal

val SPEED_COLLECTION: List<AbstractUnit> by lazy {
    listOf(
        MyUnit(MyUnitIDS.millimeter_per_hour,           BigDecimal.valueOf(1),                      UnitGroup.SPEED,    R.string.millimeter_per_hour,           R.string.millimeter_per_hour_short),
        MyUnit(MyUnitIDS.millimeter_per_minute,         BigDecimal.valueOf(60),                     UnitGroup.SPEED,    R.string.millimeter_per_minute,         R.string.millimeter_per_minute_short),
        MyUnit(MyUnitIDS.millimeter_per_second,         BigDecimal.valueOf(3_600),                  UnitGroup.SPEED,    R.string.millimeter_per_second,         R.string.millimeter_per_second_short),
        MyUnit(MyUnitIDS.centimeter_per_hour,           BigDecimal.valueOf(10),                     UnitGroup.SPEED,    R.string.centimeter_per_hour,           R.string.centimeter_per_hour_short),
        MyUnit(MyUnitIDS.centimeter_per_minute,         BigDecimal.valueOf(600),                    UnitGroup.SPEED,    R.string.centimeter_per_minute,         R.string.centimeter_per_minute_short),
        MyUnit(MyUnitIDS.centimeter_per_second,         BigDecimal.valueOf(36_000),                 UnitGroup.SPEED,    R.string.centimeter_per_second,         R.string.centimeter_per_second_short),
        MyUnit(MyUnitIDS.meter_per_hour,                BigDecimal.valueOf(1_000),                  UnitGroup.SPEED,    R.string.meter_per_hour,                R.string.meter_per_hour_short),
        MyUnit(MyUnitIDS.meter_per_minute,              BigDecimal.valueOf(60_000),                 UnitGroup.SPEED,    R.string.meter_per_minute,              R.string.meter_per_minute_short),
        MyUnit(MyUnitIDS.meter_per_second,              BigDecimal.valueOf(3_600_000),              UnitGroup.SPEED,    R.string.meter_per_second,              R.string.meter_per_second_short),
        MyUnit(MyUnitIDS.kilometer_per_hour,            BigDecimal.valueOf(1_000_000),              UnitGroup.SPEED,    R.string.kilometer_per_hour,            R.string.kilometer_per_hour_short),
        MyUnit(MyUnitIDS.kilometer_per_minute,          BigDecimal.valueOf(60_000_000),             UnitGroup.SPEED,    R.string.kilometer_per_minute,          R.string.kilometer_per_minute_short),
        MyUnit(MyUnitIDS.kilometer_per_second,          BigDecimal.valueOf(3_600_000_000),          UnitGroup.SPEED,    R.string.kilometer_per_second,          R.string.kilometer_per_second_short),
        MyUnit(MyUnitIDS.foot_per_hour,                 BigDecimal.valueOf(304),                    UnitGroup.SPEED,    R.string.foot_per_hour,                 R.string.foot_per_hour_short),
        MyUnit(MyUnitIDS.foot_per_minute,               BigDecimal.valueOf(18_288),                 UnitGroup.SPEED,    R.string.foot_per_minute,               R.string.foot_per_minute_short),
        MyUnit(MyUnitIDS.foot_per_second,               BigDecimal.valueOf(1_097_280),              UnitGroup.SPEED,    R.string.foot_per_second,               R.string.foot_per_second_short),
        MyUnit(MyUnitIDS.yard_per_hour,                 BigDecimal.valueOf(914.4),                  UnitGroup.SPEED,    R.string.yard_per_hour,                 R.string.yard_per_hour_short),
        MyUnit(MyUnitIDS.yard_per_minute,               BigDecimal.valueOf(54_864),                 UnitGroup.SPEED,    R.string.yard_per_minute,               R.string.yard_per_minute_short),
        MyUnit(MyUnitIDS.yard_per_second,               BigDecimal.valueOf(3_291_840),              UnitGroup.SPEED,    R.string.yard_per_second,               R.string.yard_per_second_short),
        MyUnit(MyUnitIDS.mile_per_hour,                 BigDecimal.valueOf(1_609_344),              UnitGroup.SPEED,    R.string.mile_per_hour,                 R.string.mile_per_hour_short),
        MyUnit(MyUnitIDS.mile_per_minute,               BigDecimal.valueOf(96_560_640),             UnitGroup.SPEED,    R.string.mile_per_minute,               R.string.mile_per_minute_short),
        MyUnit(MyUnitIDS.mile_per_second,               BigDecimal.valueOf(5_793_638_400),          UnitGroup.SPEED,    R.string.mile_per_second,               R.string.mile_per_second_short),
        MyUnit(MyUnitIDS.knot,                          BigDecimal.valueOf(1_852_000),              UnitGroup.SPEED,    R.string.knot,                          R.string.knot_short),
        MyUnit(MyUnitIDS.velocity_of_light_in_vacuum,   BigDecimal.valueOf(1_079_252_848_799_998),  UnitGroup.SPEED,    R.string.velocity_of_light_in_vacuum,   R.string.velocity_of_light_in_vacuum_short),
        MyUnit(MyUnitIDS.cosmic_velocity_first,         BigDecimal.valueOf(28_440_000_000),         UnitGroup.SPEED,    R.string.cosmic_velocity_first,         R.string.cosmic_velocity_first_short),
        MyUnit(MyUnitIDS.cosmic_velocity_second,        BigDecimal.valueOf(40_320_000_000),         UnitGroup.SPEED,    R.string.cosmic_velocity_second,        R.string.cosmic_velocity_second_short),
        MyUnit(MyUnitIDS.cosmic_velocity_third,         BigDecimal.valueOf(60_012_000_000),         UnitGroup.SPEED,    R.string.cosmic_velocity_third,         R.string.cosmic_velocity_third_short),
        MyUnit(MyUnitIDS.earths_orbital_speed,          BigDecimal.valueOf(107_208_000_000),        UnitGroup.SPEED,    R.string.earths_orbital_speed,          R.string.earths_orbital_speed_short),
        MyUnit(MyUnitIDS.mach,                          BigDecimal.valueOf(1_236_960_000),          UnitGroup.SPEED,    R.string.mach,                          R.string.mach_short),
        MyUnit(MyUnitIDS.mach_si_standard,              BigDecimal.valueOf(1_062_167_040),          UnitGroup.SPEED,    R.string.mach_si_standard,              R.string.mach_si_standard_short),)
}
