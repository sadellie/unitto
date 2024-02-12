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
import com.sadellie.unitto.data.converter.UnitID
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.ReverseUnit
import java.math.BigDecimal

val flowRateCollection: List<AbstractUnit> by lazy {
    listOf(
        ReverseUnit(UnitID.liter_per_hour,                BigDecimal("3600000"),          UnitGroup.FLOW_RATE, R.string.unit_liter_per_hour,               R.string.unit_liter_per_hour_short),
        ReverseUnit(UnitID.liter_per_minute,              BigDecimal("60000"),            UnitGroup.FLOW_RATE, R.string.unit_liter_per_minute,             R.string.unit_liter_per_minute_short),
        ReverseUnit(UnitID.liter_per_second,              BigDecimal("1000"),             UnitGroup.FLOW_RATE, R.string.unit_liter_per_second,             R.string.unit_liter_per_second_short),
        ReverseUnit(UnitID.milliliter_per_hour,           BigDecimal("3600000000"),       UnitGroup.FLOW_RATE, R.string.unit_milliliter_per_hour,          R.string.unit_milliliter_per_hour_short),
        ReverseUnit(UnitID.milliliter_per_minute,         BigDecimal("60000000"),         UnitGroup.FLOW_RATE, R.string.unit_milliliter_per_minute,        R.string.unit_milliliter_per_minute_short),
        ReverseUnit(UnitID.milliliter_per_second,         BigDecimal("1000000"),          UnitGroup.FLOW_RATE, R.string.unit_milliliter_per_second,        R.string.unit_milliliter_per_second_short),
        ReverseUnit(UnitID.cubic_meter_per_hour,          BigDecimal("3600"),             UnitGroup.FLOW_RATE, R.string.unit_cubic_meter_per_hour,         R.string.unit_cubic_meter_per_hour_short),
        ReverseUnit(UnitID.cubic_meter_per_minute,        BigDecimal("60"),               UnitGroup.FLOW_RATE, R.string.unit_cubic_meter_per_minute,       R.string.unit_cubic_meter_per_minute_short),
        ReverseUnit(UnitID.cubic_meter_per_second,        BigDecimal("1"),                UnitGroup.FLOW_RATE, R.string.unit_cubic_meter_per_second,       R.string.unit_cubic_meter_per_second_short),
        ReverseUnit(UnitID.cubic_millimeter_per_hour,     BigDecimal("3600000000000"),    UnitGroup.FLOW_RATE, R.string.unit_cubic_millimeter_per_hour,    R.string.unit_cubic_millimeter_per_hour_short),
        ReverseUnit(UnitID.cubic_millimeter_per_minute,   BigDecimal("60000000000"),      UnitGroup.FLOW_RATE, R.string.unit_cubic_millimeter_per_minute,  R.string.unit_cubic_millimeter_per_minute_short),
        ReverseUnit(UnitID.cubic_millimeter_per_second,   BigDecimal("1000000000"),       UnitGroup.FLOW_RATE, R.string.unit_cubic_millimeter_per_second,  R.string.unit_cubic_millimeter_per_second_short),
        ReverseUnit(UnitID.cubic_foot_per_hour,           BigDecimal("127132.80019736"),  UnitGroup.FLOW_RATE, R.string.unit_cubic_foot_per_hour,          R.string.unit_cubic_foot_per_hour_short),
        ReverseUnit(UnitID.cubic_foot_per_minute,         BigDecimal("2118.8800032893"),  UnitGroup.FLOW_RATE, R.string.unit_cubic_foot_per_minute,        R.string.unit_cubic_foot_per_minute_short),
        ReverseUnit(UnitID.cubic_foot_per_second,         BigDecimal("35.314666721489"),  UnitGroup.FLOW_RATE, R.string.unit_cubic_foot_per_second,        R.string.unit_cubic_foot_per_second_short),
        ReverseUnit(UnitID.gallons_per_hour_us,           BigDecimal("951019.38848933"),  UnitGroup.FLOW_RATE, R.string.unit_gallon_per_hour_us,           R.string.unit_gallon_per_hour_us_short),
        ReverseUnit(UnitID.gallons_per_minute_us,         BigDecimal("15850.323141489"),  UnitGroup.FLOW_RATE, R.string.unit_gallon_per_minute_us,         R.string.unit_gallon_per_minute_us_short),
        ReverseUnit(UnitID.gallons_per_second_us,         BigDecimal("264.17205235815"),  UnitGroup.FLOW_RATE, R.string.unit_gallon_per_second_us,         R.string.unit_gallon_per_second_us_short),
        ReverseUnit(UnitID.gallons_per_hour_imperial,     BigDecimal("791889.29387672"),  UnitGroup.FLOW_RATE, R.string.unit_gallon_per_hour_imperial,     R.string.unit_gallon_per_hour_imperial_short),
        ReverseUnit(UnitID.gallons_per_minute_imperial,   BigDecimal("13198.154897945"),  UnitGroup.FLOW_RATE, R.string.unit_gallon_per_minute_imperial,   R.string.unit_gallon_per_minute_imperial_short),
        ReverseUnit(UnitID.gallons_per_second_imperial,   BigDecimal("219.96924829909"),  UnitGroup.FLOW_RATE, R.string.unit_gallon_per_second_imperial,   R.string.unit_gallon_per_second_imperial_short),
    )
}
