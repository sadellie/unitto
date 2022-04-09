package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import java.math.BigDecimal

val LENGTH_COLLECTION: List<AbstractUnit> by lazy {
    listOf(
        MyUnit(MyUnitIDS.attometer,  BigDecimal.valueOf(1.0),                               UnitGroup.LENGTH,   R.string.attometer,     R.string.attometer_short),
        MyUnit(MyUnitIDS.nanometer,  BigDecimal.valueOf(1.0E+9),                            UnitGroup.LENGTH,   R.string.nanometer,     R.string.nanometer_short),
        MyUnit(MyUnitIDS.micrometer, BigDecimal.valueOf(1.0E+12),                           UnitGroup.LENGTH,   R.string.micrometer,    R.string.micrometer_short),
        MyUnit(MyUnitIDS.millimeter, BigDecimal.valueOf(1.0E+15),                           UnitGroup.LENGTH,   R.string.millimeter,    R.string.millimeter_short),
        MyUnit(MyUnitIDS.centimeter, BigDecimal.valueOf(1.0E+16),                           UnitGroup.LENGTH,   R.string.centimeter,    R.string.centimeter_short),
        MyUnit(MyUnitIDS.decimeter,  BigDecimal.valueOf(1.0E+17),                           UnitGroup.LENGTH,   R.string.decimeter,     R.string.decimeter_short),
        MyUnit(MyUnitIDS.meter,      BigDecimal.valueOf(1.0E+18),                           UnitGroup.LENGTH,   R.string.meter,         R.string.meter_short),
        MyUnit(MyUnitIDS.kilometer,  BigDecimal.valueOf(1.0E+21),                           UnitGroup.LENGTH,   R.string.kilometer,     R.string.kilometer_short),
        MyUnit(MyUnitIDS.inch,       BigDecimal.valueOf(25_400_000_000_000_308),            UnitGroup.LENGTH,   R.string.inch,          R.string.inch_short),
        MyUnit(MyUnitIDS.foot,       BigDecimal.valueOf(304_800_000_000_002_200),           UnitGroup.LENGTH,   R.string.foot,          R.string.foot_short),
        MyUnit(MyUnitIDS.yard,       BigDecimal.valueOf(914_400_000_000_006_400),           UnitGroup.LENGTH,   R.string.yard,          R.string.yard_short),
        MyUnit(MyUnitIDS.mile,       BigDecimal.valueOf(1_609_344_000_000_010_500_000.0),   UnitGroup.LENGTH,   R.string.mile,          R.string.mile_short),
        MyUnit(MyUnitIDS.light_year, BigDecimal.valueOf(9.460730472E+33),                   UnitGroup.LENGTH,   R.string.light_year,    R.string.light_year_short),
    )
}
