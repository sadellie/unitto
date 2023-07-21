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
import com.sadellie.unitto.data.model.AbstractUnit
import com.sadellie.unitto.data.model.FlowRateUnit
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.units.MyUnitIDS
import java.math.BigDecimal

val flowRateCollection: List<AbstractUnit> by lazy {
    listOf(
        FlowRateUnit(MyUnitIDS.liter_per_hour,                BigDecimal.valueOf(3600000),          UnitGroup.FLOW_RATE, R.string.liter_per_hour,               R.string.liter_per_hour_short),
        FlowRateUnit(MyUnitIDS.liter_per_minute,              BigDecimal.valueOf(60000),            UnitGroup.FLOW_RATE, R.string.liter_per_minute,             R.string.liter_per_minute_short),
        FlowRateUnit(MyUnitIDS.liter_per_second,              BigDecimal.valueOf(1000),             UnitGroup.FLOW_RATE, R.string.liter_per_second,             R.string.liter_per_second_short),
        FlowRateUnit(MyUnitIDS.milliliter_per_hour,           BigDecimal.valueOf(3600000000),       UnitGroup.FLOW_RATE, R.string.milliliter_per_hour,          R.string.milliliter_per_hour_short),
        FlowRateUnit(MyUnitIDS.milliliter_per_minute,         BigDecimal.valueOf(60000000),         UnitGroup.FLOW_RATE, R.string.milliliter_per_minute,        R.string.milliliter_per_minute_short),
        FlowRateUnit(MyUnitIDS.milliliter_per_second,         BigDecimal.valueOf(1000000),          UnitGroup.FLOW_RATE, R.string.milliliter_per_second,        R.string.milliliter_per_second_short),
        FlowRateUnit(MyUnitIDS.cubic_meter_per_hour,          BigDecimal.valueOf(3600),             UnitGroup.FLOW_RATE, R.string.cubic_meter_per_hour,         R.string.cubic_meter_per_hour_short),
        FlowRateUnit(MyUnitIDS.cubic_meter_per_minute,        BigDecimal.valueOf(60),               UnitGroup.FLOW_RATE, R.string.cubic_meter_per_minute,       R.string.cubic_meter_per_minute_short),
        FlowRateUnit(MyUnitIDS.cubic_meter_per_second,        BigDecimal.valueOf(1),                UnitGroup.FLOW_RATE, R.string.cubic_meter_per_second,       R.string.cubic_meter_per_second_short),
        FlowRateUnit(MyUnitIDS.cubic_millimeter_per_hour,     BigDecimal.valueOf(3600000000000),    UnitGroup.FLOW_RATE, R.string.cubic_millimeter_per_hour,    R.string.cubic_millimeter_per_hour_short),
        FlowRateUnit(MyUnitIDS.cubic_millimeter_per_minute,   BigDecimal.valueOf(60000000000),      UnitGroup.FLOW_RATE, R.string.cubic_millimeter_per_minute,  R.string.cubic_millimeter_per_minute_short),
        FlowRateUnit(MyUnitIDS.cubic_millimeter_per_second,   BigDecimal.valueOf(1000000000),       UnitGroup.FLOW_RATE, R.string.cubic_millimeter_per_second,  R.string.cubic_millimeter_per_second_short),
        FlowRateUnit(MyUnitIDS.cubic_foot_per_hour,           BigDecimal.valueOf(127132.80019736),  UnitGroup.FLOW_RATE, R.string.cubic_foot_per_hour,          R.string.cubic_foot_per_hour_short),
        FlowRateUnit(MyUnitIDS.cubic_foot_per_minute,         BigDecimal.valueOf(2118.8800032893),  UnitGroup.FLOW_RATE, R.string.cubic_foot_per_minute,        R.string.cubic_foot_per_minute_short),
        FlowRateUnit(MyUnitIDS.cubic_foot_per_second,         BigDecimal.valueOf(35.314666721489),  UnitGroup.FLOW_RATE, R.string.cubic_foot_per_second,        R.string.cubic_foot_per_second_short),
        FlowRateUnit(MyUnitIDS.gallons_per_hour_us,           BigDecimal.valueOf(951019.38848933),  UnitGroup.FLOW_RATE, R.string.gallon_per_hour_us,           R.string.gallon_per_hour_us_short),
        FlowRateUnit(MyUnitIDS.gallons_per_minute_us,         BigDecimal.valueOf(15850.323141489),  UnitGroup.FLOW_RATE, R.string.gallon_per_minute_us,         R.string.gallon_per_minute_us_short),
        FlowRateUnit(MyUnitIDS.gallons_per_second_us,         BigDecimal.valueOf(264.17205235815),  UnitGroup.FLOW_RATE, R.string.gallon_per_second_us,         R.string.gallon_per_second_us_short),
        FlowRateUnit(MyUnitIDS.gallons_per_hour_imperial,     BigDecimal.valueOf(791889.29387672),  UnitGroup.FLOW_RATE, R.string.gallon_per_hour_imperial,     R.string.gallon_per_hour_imperial_short),
        FlowRateUnit(MyUnitIDS.gallons_per_minute_imperial,   BigDecimal.valueOf(13198.154897945),  UnitGroup.FLOW_RATE, R.string.gallon_per_minute_imperial,   R.string.gallon_per_minute_imperial_short),
        FlowRateUnit(MyUnitIDS.gallons_per_second_imperial,   BigDecimal.valueOf(219.96924829909),  UnitGroup.FLOW_RATE, R.string.gallon_per_second_imperial,   R.string.gallon_per_second_imperial_short),
    )
}
