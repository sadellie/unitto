/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

package com.sadellie.unitto.data.converter.collections

import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.NormalUnit
import com.sadellie.unitto.data.converter.UnitID
import java.math.BigDecimal

internal val timeCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(UnitID.attosecond,    BigDecimal.valueOf(1),                                  UnitGroup.TIME, R.string.unit_attosecond,    R.string.unit_attosecond_short),
        NormalUnit(UnitID.nanosecond,    BigDecimal.valueOf(1_000_000_000),                      UnitGroup.TIME, R.string.unit_nanosecond,    R.string.unit_nanosecond_short),
        NormalUnit(UnitID.microsecond,   BigDecimal.valueOf(1_000_000_000_000),                  UnitGroup.TIME, R.string.unit_microsecond,   R.string.unit_microsecond_short),
        NormalUnit(UnitID.millisecond,   BigDecimal.valueOf(1_000_000_000_000_000),              UnitGroup.TIME, R.string.unit_millisecond,   R.string.unit_millisecond_short),
        NormalUnit(UnitID.jiffy,         BigDecimal.valueOf(10_000_000_000_000_000),             UnitGroup.TIME, R.string.unit_jiffy,         R.string.unit_jiffy_short),
        NormalUnit(UnitID.second,        BigDecimal.valueOf(1_000_000_000_000_000_000),          UnitGroup.TIME, R.string.unit_second,        R.string.unit_second_short),
        NormalUnit(UnitID.minute,        BigDecimal.valueOf(60_000_000_000_000_000_000.0),       UnitGroup.TIME, R.string.unit_minute,        R.string.unit_minute_short),
        NormalUnit(UnitID.hour,          BigDecimal.valueOf(3_600_000_000_000_000_000_000.0),    UnitGroup.TIME, R.string.unit_hour,          R.string.unit_hour_short),
        NormalUnit(UnitID.day,           BigDecimal.valueOf(86_400_000_000_000_000_000_000.0),   UnitGroup.TIME, R.string.unit_day,           R.string.unit_day_short),
        NormalUnit(UnitID.week,          BigDecimal.valueOf(604_800_000_000_000_000_000_000.0),  UnitGroup.TIME, R.string.unit_week,          R.string.unit_week_short),
    )
}
