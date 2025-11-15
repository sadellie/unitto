/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

package com.sadellie.unitto.core.data.converter.collections

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.unit_attosecond
import unitto.core.common.generated.resources.unit_attosecond_short
import unitto.core.common.generated.resources.unit_day
import unitto.core.common.generated.resources.unit_day_short
import unitto.core.common.generated.resources.unit_hour
import unitto.core.common.generated.resources.unit_hour_short
import unitto.core.common.generated.resources.unit_jiffy
import unitto.core.common.generated.resources.unit_jiffy_short
import unitto.core.common.generated.resources.unit_microsecond
import unitto.core.common.generated.resources.unit_microsecond_short
import unitto.core.common.generated.resources.unit_millisecond
import unitto.core.common.generated.resources.unit_millisecond_short
import unitto.core.common.generated.resources.unit_minute
import unitto.core.common.generated.resources.unit_minute_short
import unitto.core.common.generated.resources.unit_nanosecond
import unitto.core.common.generated.resources.unit_nanosecond_short
import unitto.core.common.generated.resources.unit_second
import unitto.core.common.generated.resources.unit_second_short
import unitto.core.common.generated.resources.unit_week
import unitto.core.common.generated.resources.unit_week_short

internal val timeCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.attosecond,
      KBigDecimal("1"),
      UnitGroup.TIME,
      Res.string.unit_attosecond,
      Res.string.unit_attosecond_short,
    ),
    NormalUnit(
      UnitID.nanosecond,
      KBigDecimal("1000000000"),
      UnitGroup.TIME,
      Res.string.unit_nanosecond,
      Res.string.unit_nanosecond_short,
    ),
    NormalUnit(
      UnitID.microsecond,
      KBigDecimal("1000000000000"),
      UnitGroup.TIME,
      Res.string.unit_microsecond,
      Res.string.unit_microsecond_short,
    ),
    NormalUnit(
      UnitID.millisecond,
      KBigDecimal("1000000000000000"),
      UnitGroup.TIME,
      Res.string.unit_millisecond,
      Res.string.unit_millisecond_short,
    ),
    NormalUnit(
      UnitID.jiffy,
      KBigDecimal("10000000000000000"),
      UnitGroup.TIME,
      Res.string.unit_jiffy,
      Res.string.unit_jiffy_short,
    ),
    NormalUnit(
      UnitID.second,
      KBigDecimal("1000000000000000000"),
      UnitGroup.TIME,
      Res.string.unit_second,
      Res.string.unit_second_short,
    ),
    NormalUnit(
      UnitID.minute,
      KBigDecimal("60000000000000000000"),
      UnitGroup.TIME,
      Res.string.unit_minute,
      Res.string.unit_minute_short,
    ),
    NormalUnit(
      UnitID.hour,
      KBigDecimal("3600000000000000000000"),
      UnitGroup.TIME,
      Res.string.unit_hour,
      Res.string.unit_hour_short,
    ),
    NormalUnit(
      UnitID.day,
      KBigDecimal("86400000000000000000000"),
      UnitGroup.TIME,
      Res.string.unit_day,
      Res.string.unit_day_short,
    ),
    NormalUnit(
      UnitID.week,
      KBigDecimal("604800000000000000000000"),
      UnitGroup.TIME,
      Res.string.unit_week,
      Res.string.unit_week_short,
    ),
  )
}
