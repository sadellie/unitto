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

internal val powerCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(MyUnitIDS.attowatt,                  BigDecimal.valueOf(1),                              UnitGroup.POWER,    R.string.unit_attowatt,                  R.string.unit_attowatt_short),
        NormalUnit(MyUnitIDS.watt,                      BigDecimal.valueOf(1_000_000_000_000_000_000),      UnitGroup.POWER,    R.string.unit_watt,                      R.string.unit_watt_short),
        NormalUnit(MyUnitIDS.kilowatt,                  BigDecimal.valueOf(1.0E+21),                        UnitGroup.POWER,    R.string.unit_kilowatt,                  R.string.unit_kilowatt_short),
        NormalUnit(MyUnitIDS.megawatt,                  BigDecimal.valueOf(1.0E+24),                        UnitGroup.POWER,    R.string.unit_megawatt,                  R.string.unit_megawatt_short),
        NormalUnit(MyUnitIDS.horse_power_mechanical,    BigDecimal.valueOf(745_699_871_582_285_700_000.0),  UnitGroup.POWER,    R.string.unit_horse_power_mechanical,    R.string.unit_horse_power_mechanical_short),
    )
}
