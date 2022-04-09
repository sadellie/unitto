package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import java.math.BigDecimal

val ANGLE_COLLECTION: List<AbstractUnit> by lazy {
    listOf(
        MyUnit(MyUnitIDS.angle_second,  BigDecimal.valueOf(1),              UnitGroup.ANGLE,    R.string.angle_second,  R.string.angle_second_short),
        MyUnit(MyUnitIDS.angle_minute,  BigDecimal.valueOf(60),             UnitGroup.ANGLE,    R.string.angle_minute,  R.string.angle_minute_short),
        MyUnit(MyUnitIDS.degree,        BigDecimal.valueOf(3600),           UnitGroup.ANGLE,    R.string.degree,        R.string.degree_short),
        MyUnit(MyUnitIDS.radian,        BigDecimal.valueOf(206264.8062471), UnitGroup.ANGLE,    R.string.radian,        R.string.radian_short),
        MyUnit(MyUnitIDS.sextant,       BigDecimal.valueOf(216000),         UnitGroup.ANGLE,    R.string.sextant,       R.string.sextant_short),
        MyUnit(MyUnitIDS.turn,          BigDecimal.valueOf(1296000),        UnitGroup.ANGLE,    R.string.turn,          R.string.turn_short),
    )
}
