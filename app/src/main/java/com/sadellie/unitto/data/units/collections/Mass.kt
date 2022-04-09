package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import java.math.BigDecimal

val MASS_COLLECTION: List<AbstractUnit> by lazy {
    listOf(
        MyUnit(MyUnitIDS.electron_mass_rest,    BigDecimal.valueOf(1),                      UnitGroup.MASS, R.string.electron_mass_rest,     R.string.electron_mass_rest_short),
        MyUnit(MyUnitIDS.atomic_mass_unit,      BigDecimal.valueOf(1_822.888530062548),     UnitGroup.MASS, R.string.atomic_mass_unit,  R.string.atomic_mass_unit_short),
        MyUnit(MyUnitIDS.milligram,             BigDecimal.valueOf(1.0977683828808E+24),    UnitGroup.MASS, R.string.milligram,         R.string.milligram_short),
        MyUnit(MyUnitIDS.gram,                  BigDecimal.valueOf(1.0977683828808E+27),    UnitGroup.MASS, R.string.gram,              R.string.gram_short),
        MyUnit(MyUnitIDS.kilogram,              BigDecimal.valueOf(1.0977683828808E+30),    UnitGroup.MASS, R.string.kilogram,          R.string.kilogram_short),
        MyUnit(MyUnitIDS.metric_ton,            BigDecimal.valueOf(1.0977683828808E+33),    UnitGroup.MASS, R.string.metric_ton,        R.string.metric_ton_short),
        MyUnit(MyUnitIDS.imperial_ton,          BigDecimal.valueOf(1.1153841720044124E+33), UnitGroup.MASS, R.string.imperial_ton,      R.string.imperial_ton_short),
        MyUnit(MyUnitIDS.ounce,                 BigDecimal.valueOf(3.1121210156373456E+28), UnitGroup.MASS, R.string.ounce,             R.string.ounce_short),
        MyUnit(MyUnitIDS.carat,                 BigDecimal.valueOf(2.1955367657615996E+26), UnitGroup.MASS, R.string.carat,             R.string.carat_short),
        MyUnit(MyUnitIDS.pound,                 BigDecimal.valueOf(4.979393625019642E+29),  UnitGroup.MASS, R.string.pound,             R.string.pound_short),
    )
}
