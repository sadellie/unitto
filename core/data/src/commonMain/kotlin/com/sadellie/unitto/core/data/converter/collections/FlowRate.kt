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
import unitto.core.common.generated.resources.unit_cubic_foot_per_hour
import unitto.core.common.generated.resources.unit_cubic_foot_per_hour_short
import unitto.core.common.generated.resources.unit_cubic_foot_per_minute
import unitto.core.common.generated.resources.unit_cubic_foot_per_minute_short
import unitto.core.common.generated.resources.unit_cubic_foot_per_second
import unitto.core.common.generated.resources.unit_cubic_foot_per_second_short
import unitto.core.common.generated.resources.unit_cubic_meter_per_hour
import unitto.core.common.generated.resources.unit_cubic_meter_per_hour_short
import unitto.core.common.generated.resources.unit_cubic_meter_per_minute
import unitto.core.common.generated.resources.unit_cubic_meter_per_minute_short
import unitto.core.common.generated.resources.unit_cubic_meter_per_second
import unitto.core.common.generated.resources.unit_cubic_meter_per_second_short
import unitto.core.common.generated.resources.unit_cubic_millimeter_per_hour
import unitto.core.common.generated.resources.unit_cubic_millimeter_per_hour_short
import unitto.core.common.generated.resources.unit_cubic_millimeter_per_minute
import unitto.core.common.generated.resources.unit_cubic_millimeter_per_minute_short
import unitto.core.common.generated.resources.unit_cubic_millimeter_per_second
import unitto.core.common.generated.resources.unit_cubic_millimeter_per_second_short
import unitto.core.common.generated.resources.unit_gallon_per_hour_imperial
import unitto.core.common.generated.resources.unit_gallon_per_hour_imperial_short
import unitto.core.common.generated.resources.unit_gallon_per_hour_us
import unitto.core.common.generated.resources.unit_gallon_per_hour_us_short
import unitto.core.common.generated.resources.unit_gallon_per_minute_imperial
import unitto.core.common.generated.resources.unit_gallon_per_minute_imperial_short
import unitto.core.common.generated.resources.unit_gallon_per_minute_us
import unitto.core.common.generated.resources.unit_gallon_per_minute_us_short
import unitto.core.common.generated.resources.unit_gallon_per_second_imperial
import unitto.core.common.generated.resources.unit_gallon_per_second_imperial_short
import unitto.core.common.generated.resources.unit_gallon_per_second_us
import unitto.core.common.generated.resources.unit_gallon_per_second_us_short
import unitto.core.common.generated.resources.unit_liter_per_hour
import unitto.core.common.generated.resources.unit_liter_per_hour_short
import unitto.core.common.generated.resources.unit_liter_per_minute
import unitto.core.common.generated.resources.unit_liter_per_minute_short
import unitto.core.common.generated.resources.unit_liter_per_second
import unitto.core.common.generated.resources.unit_liter_per_second_short
import unitto.core.common.generated.resources.unit_milliliter_per_hour
import unitto.core.common.generated.resources.unit_milliliter_per_hour_short
import unitto.core.common.generated.resources.unit_milliliter_per_minute
import unitto.core.common.generated.resources.unit_milliliter_per_minute_short
import unitto.core.common.generated.resources.unit_milliliter_per_second
import unitto.core.common.generated.resources.unit_milliliter_per_second_short

val flowRateCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.liter_per_hour,
      KBigDecimal("3600000"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_liter_per_hour,
      Res.string.unit_liter_per_hour_short,
      true,
    ),
    NormalUnit(
      UnitID.liter_per_minute,
      KBigDecimal("60000"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_liter_per_minute,
      Res.string.unit_liter_per_minute_short,
      true,
    ),
    NormalUnit(
      UnitID.liter_per_second,
      KBigDecimal("1000"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_liter_per_second,
      Res.string.unit_liter_per_second_short,
      true,
    ),
    NormalUnit(
      UnitID.milliliter_per_hour,
      KBigDecimal("3600000000"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_milliliter_per_hour,
      Res.string.unit_milliliter_per_hour_short,
      true,
    ),
    NormalUnit(
      UnitID.milliliter_per_minute,
      KBigDecimal("60000000"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_milliliter_per_minute,
      Res.string.unit_milliliter_per_minute_short,
      true,
    ),
    NormalUnit(
      UnitID.milliliter_per_second,
      KBigDecimal("1000000"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_milliliter_per_second,
      Res.string.unit_milliliter_per_second_short,
      true,
    ),
    NormalUnit(
      UnitID.cubic_meter_per_hour,
      KBigDecimal("3600"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_cubic_meter_per_hour,
      Res.string.unit_cubic_meter_per_hour_short,
      true,
    ),
    NormalUnit(
      UnitID.cubic_meter_per_minute,
      KBigDecimal("60"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_cubic_meter_per_minute,
      Res.string.unit_cubic_meter_per_minute_short,
      true,
    ),
    NormalUnit(
      UnitID.cubic_meter_per_second,
      KBigDecimal("1"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_cubic_meter_per_second,
      Res.string.unit_cubic_meter_per_second_short,
      true,
    ),
    NormalUnit(
      UnitID.cubic_millimeter_per_hour,
      KBigDecimal("3600000000000"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_cubic_millimeter_per_hour,
      Res.string.unit_cubic_millimeter_per_hour_short,
      true,
    ),
    NormalUnit(
      UnitID.cubic_millimeter_per_minute,
      KBigDecimal("60000000000"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_cubic_millimeter_per_minute,
      Res.string.unit_cubic_millimeter_per_minute_short,
      true,
    ),
    NormalUnit(
      UnitID.cubic_millimeter_per_second,
      KBigDecimal("1000000000"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_cubic_millimeter_per_second,
      Res.string.unit_cubic_millimeter_per_second_short,
      true,
    ),
    NormalUnit(
      UnitID.cubic_foot_per_hour,
      KBigDecimal("127132.80019736"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_cubic_foot_per_hour,
      Res.string.unit_cubic_foot_per_hour_short,
      true,
    ),
    NormalUnit(
      UnitID.cubic_foot_per_minute,
      KBigDecimal("2118.8800032893"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_cubic_foot_per_minute,
      Res.string.unit_cubic_foot_per_minute_short,
      true,
    ),
    NormalUnit(
      UnitID.cubic_foot_per_second,
      KBigDecimal("35.314666721489"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_cubic_foot_per_second,
      Res.string.unit_cubic_foot_per_second_short,
      true,
    ),
    NormalUnit(
      UnitID.gallons_per_hour_us,
      KBigDecimal("951019.38848933"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_gallon_per_hour_us,
      Res.string.unit_gallon_per_hour_us_short,
      true,
    ),
    NormalUnit(
      UnitID.gallons_per_minute_us,
      KBigDecimal("15850.323141489"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_gallon_per_minute_us,
      Res.string.unit_gallon_per_minute_us_short,
      true,
    ),
    NormalUnit(
      UnitID.gallons_per_second_us,
      KBigDecimal("264.17205235815"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_gallon_per_second_us,
      Res.string.unit_gallon_per_second_us_short,
      true,
    ),
    NormalUnit(
      UnitID.gallons_per_hour_imperial,
      KBigDecimal("791889.29387672"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_gallon_per_hour_imperial,
      Res.string.unit_gallon_per_hour_imperial_short,
      true,
    ),
    NormalUnit(
      UnitID.gallons_per_minute_imperial,
      KBigDecimal("13198.154897945"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_gallon_per_minute_imperial,
      Res.string.unit_gallon_per_minute_imperial_short,
      true,
    ),
    NormalUnit(
      UnitID.gallons_per_second_imperial,
      KBigDecimal("219.96924829909"),
      UnitGroup.FLOW_RATE,
      Res.string.unit_gallon_per_second_imperial,
      Res.string.unit_gallon_per_second_imperial_short,
      true,
    ),
  )
}
