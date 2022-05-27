package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import java.math.BigDecimal

val MASS_COLLECTION: List<AbstractUnit> by lazy {
    listOf(
        MyUnit(MyUnitIDS.electron_mass_rest, BigDecimal.valueOf(9.1093897E-28), UnitGroup.MASS, R.string.electron_mass_rest, R.string.electron_mass_rest_short),
        MyUnit(MyUnitIDS.atomic_mass_unit,   BigDecimal.valueOf(1.6605402E-24), UnitGroup.MASS, R.string.atomic_mass_unit,   R.string.atomic_mass_unit_short),
        MyUnit(MyUnitIDS.milligram,          BigDecimal.valueOf(1E-3),          UnitGroup.MASS, R.string.milligram,          R.string.milligram_short),
        MyUnit(MyUnitIDS.gram,               BigDecimal.valueOf(1),             UnitGroup.MASS, R.string.gram,               R.string.gram_short),
        MyUnit(MyUnitIDS.kilogram,           BigDecimal.valueOf(1E+3),          UnitGroup.MASS, R.string.kilogram,           R.string.kilogram_short),
        MyUnit(MyUnitIDS.metric_ton,         BigDecimal.valueOf(1E+6),          UnitGroup.MASS, R.string.metric_ton,         R.string.metric_ton_short),
        MyUnit(MyUnitIDS.imperial_ton,       BigDecimal.valueOf(1016046.9088),  UnitGroup.MASS, R.string.imperial_ton,       R.string.imperial_ton_short),
        MyUnit(MyUnitIDS.ounce,              BigDecimal.valueOf(28.349523125),  UnitGroup.MASS, R.string.ounce,              R.string.ounce_short),
        MyUnit(MyUnitIDS.carat,              BigDecimal.valueOf(0.2),           UnitGroup.MASS, R.string.carat,              R.string.carat_short),
        MyUnit(MyUnitIDS.pound,              BigDecimal.valueOf(453.59237),     UnitGroup.MASS, R.string.pound,              R.string.pound_short),
        MyUnit(MyUnitIDS.mercury_mass,       BigDecimal.valueOf(3.30104E+26),   UnitGroup.MASS, R.string.mercury_mass,       R.string.mercury_mass_short),
        MyUnit(MyUnitIDS.venus_mass,         BigDecimal.valueOf(4.86732E+27),   UnitGroup.MASS, R.string.venus_mass,         R.string.venus_mass_short),
        MyUnit(MyUnitIDS.earth_mass,         BigDecimal.valueOf(5.97219E+27),   UnitGroup.MASS, R.string.earth_mass,         R.string.earth_mass_short),
        MyUnit(MyUnitIDS.mars_mass,          BigDecimal.valueOf(6.41693E+26),   UnitGroup.MASS, R.string.mars_mass,          R.string.mars_mass_short),
        MyUnit(MyUnitIDS.jupiter_mass,       BigDecimal.valueOf(1.89813E+30),   UnitGroup.MASS, R.string.jupiter_mass,       R.string.jupiter_mass_short),
        MyUnit(MyUnitIDS.saturn_mass,        BigDecimal.valueOf(5.68319E+29),   UnitGroup.MASS, R.string.saturn_mass,        R.string.saturn_mass_short),
        MyUnit(MyUnitIDS.uranus_mass,        BigDecimal.valueOf(8.68103E+28),   UnitGroup.MASS, R.string.uranus_mass,        R.string.uranus_mass_short),
        MyUnit(MyUnitIDS.neptune_mass,       BigDecimal.valueOf(1.0241E+29),    UnitGroup.MASS, R.string.neptune_mass,       R.string.neptune_mass_short),
        MyUnit(MyUnitIDS.sun_mass,           BigDecimal.valueOf(1.9891E+33),    UnitGroup.MASS, R.string.sun_mass,           R.string.sun_mass_short),
    )
}
