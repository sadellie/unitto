package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import java.math.BigDecimal

val POWER_COLLECTION: List<AbstractUnit> by lazy {
    listOf(
        MyUnit(MyUnitIDS.attowatt,                  BigDecimal.valueOf(1),                              UnitGroup.POWER,    R.string.attowatt,                  R.string.attowatt_short),
        MyUnit(MyUnitIDS.watt,                      BigDecimal.valueOf(1_000_000_000_000_000_000),      UnitGroup.POWER,    R.string.watt,                      R.string.watt_short),
        MyUnit(MyUnitIDS.kilowatt,                  BigDecimal.valueOf(1.0E+21),                        UnitGroup.POWER,    R.string.kilowatt,                  R.string.kilowatt_short),
        MyUnit(MyUnitIDS.megawatt,                  BigDecimal.valueOf(1.0E+24),                        UnitGroup.POWER,    R.string.megawatt,                  R.string.megawatt_short),
        MyUnit(MyUnitIDS.horse_power_mechanical,    BigDecimal.valueOf(745_699_871_582_285_700_000.0),  UnitGroup.POWER,    R.string.horse_power_mechanical,    R.string.horse_power_mechanical_short),
    )
}
