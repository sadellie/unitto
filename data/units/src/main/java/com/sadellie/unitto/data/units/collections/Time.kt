/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.NormalUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import java.math.BigDecimal

internal val timeCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(MyUnitIDS.attosecond,    BigDecimal.valueOf(1),                                  UnitGroup.TIME, R.string.unit_attosecond,    R.string.unit_attosecond_short),
        NormalUnit(MyUnitIDS.nanosecond,    BigDecimal.valueOf(1_000_000_000),                      UnitGroup.TIME, R.string.unit_nanosecond,    R.string.unit_nanosecond_short),
        NormalUnit(MyUnitIDS.microsecond,   BigDecimal.valueOf(1_000_000_000_000),                  UnitGroup.TIME, R.string.unit_microsecond,   R.string.unit_microsecond_short),
        NormalUnit(MyUnitIDS.millisecond,   BigDecimal.valueOf(1_000_000_000_000_000),              UnitGroup.TIME, R.string.unit_millisecond,   R.string.unit_millisecond_short),
        NormalUnit(MyUnitIDS.jiffy,         BigDecimal.valueOf(10_000_000_000_000_000),             UnitGroup.TIME, R.string.unit_jiffy,         R.string.unit_jiffy_short),
        NormalUnit(MyUnitIDS.second,        BigDecimal.valueOf(1_000_000_000_000_000_000),          UnitGroup.TIME, R.string.unit_second,        R.string.unit_second_short),
        NormalUnit(MyUnitIDS.minute,        BigDecimal.valueOf(60_000_000_000_000_000_000.0),       UnitGroup.TIME, R.string.unit_minute,        R.string.unit_minute_short),
        NormalUnit(MyUnitIDS.hour,          BigDecimal.valueOf(3_600_000_000_000_000_000_000.0),    UnitGroup.TIME, R.string.unit_hour,          R.string.unit_hour_short),
        NormalUnit(MyUnitIDS.day,           BigDecimal.valueOf(86_400_000_000_000_000_000_000.0),   UnitGroup.TIME, R.string.unit_day,           R.string.unit_day_short),
        NormalUnit(MyUnitIDS.week,          BigDecimal.valueOf(604_800_000_000_000_000_000_000.0),  UnitGroup.TIME, R.string.unit_week,          R.string.unit_week_short),
    )
}
