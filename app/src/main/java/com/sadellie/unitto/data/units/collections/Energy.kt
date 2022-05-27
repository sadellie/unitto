package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import java.math.BigDecimal

val ENERGY_COLLECTION: List<AbstractUnit> by lazy {
    listOf(
        MyUnit(MyUnitIDS.electron_volt,             BigDecimal.valueOf(0.160217733),   UnitGroup.ENERGY,   R.string.electron_volt,             R.string.electron_volt_short),
        MyUnit(MyUnitIDS.attojoule,                 BigDecimal.valueOf(1),             UnitGroup.ENERGY,   R.string.attojoule,                 R.string.attojoule_short),
        MyUnit(MyUnitIDS.joule,                     BigDecimal.valueOf(1E+18),         UnitGroup.ENERGY,   R.string.joule,                     R.string.joule_short),
        MyUnit(MyUnitIDS.kilojoule,                 BigDecimal.valueOf(1E+21),         UnitGroup.ENERGY,   R.string.kilojoule,                 R.string.kilojoule_short),
        MyUnit(MyUnitIDS.megajoule,                 BigDecimal.valueOf(1E+24),         UnitGroup.ENERGY,   R.string.megajoule,                 R.string.megajoule_short),
        MyUnit(MyUnitIDS.gigajoule,                 BigDecimal.valueOf(1E+27),         UnitGroup.ENERGY,   R.string.gigajoule,                 R.string.gigajoule_short),
        MyUnit(MyUnitIDS.energy_ton,                BigDecimal.valueOf(4.184E+27),     UnitGroup.ENERGY,   R.string.energy_ton,                R.string.energy_ton_short),
        MyUnit(MyUnitIDS.kiloton,                   BigDecimal.valueOf(4.184E+30),     UnitGroup.ENERGY,   R.string.kiloton,                   R.string.kiloton_short),
        MyUnit(MyUnitIDS.megaton,                   BigDecimal.valueOf(4.184E+33),     UnitGroup.ENERGY,   R.string.megaton,                   R.string.megaton_short),
        MyUnit(MyUnitIDS.gigaton,                   BigDecimal.valueOf(4.184E+36),     UnitGroup.ENERGY,   R.string.gigaton,                   R.string.gigaton_short),
        MyUnit(MyUnitIDS.energy_horse_power_metric, BigDecimal.valueOf(2.6477955E+24), UnitGroup.ENERGY,   R.string.energy_horse_power_metric, R.string.energy_horse_power_metric_short),
        MyUnit(MyUnitIDS.calorie_th,                BigDecimal.valueOf(4184E+15),      UnitGroup.ENERGY,   R.string.calorie_th,                R.string.calorie_th_short),
        MyUnit(MyUnitIDS.kilocalorie_th,            BigDecimal.valueOf(4184E+18),      UnitGroup.ENERGY,   R.string.kilocalorie_th,            R.string.kilocalorie_th_short),
    )
}
