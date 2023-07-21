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
import com.sadellie.unitto.data.model.DefaultUnit
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.units.MyUnitIDS
import java.math.BigDecimal

internal val energyCollection: List<AbstractUnit> by lazy {
    listOf(
        DefaultUnit(MyUnitIDS.electron_volt,             BigDecimal.valueOf(0.160217733),   UnitGroup.ENERGY,   R.string.electron_volt,             R.string.electron_volt_short),
        DefaultUnit(MyUnitIDS.attojoule,                 BigDecimal.valueOf(1),             UnitGroup.ENERGY,   R.string.attojoule,                 R.string.attojoule_short),
        DefaultUnit(MyUnitIDS.joule,                     BigDecimal.valueOf(1E+18),         UnitGroup.ENERGY,   R.string.joule,                     R.string.joule_short),
        DefaultUnit(MyUnitIDS.kilojoule,                 BigDecimal.valueOf(1E+21),         UnitGroup.ENERGY,   R.string.kilojoule,                 R.string.kilojoule_short),
        DefaultUnit(MyUnitIDS.megajoule,                 BigDecimal.valueOf(1E+24),         UnitGroup.ENERGY,   R.string.megajoule,                 R.string.megajoule_short),
        DefaultUnit(MyUnitIDS.gigajoule,                 BigDecimal.valueOf(1E+27),         UnitGroup.ENERGY,   R.string.gigajoule,                 R.string.gigajoule_short),
        DefaultUnit(MyUnitIDS.energy_ton,                BigDecimal.valueOf(4.184E+27),     UnitGroup.ENERGY,   R.string.energy_ton,                R.string.energy_ton_short),
        DefaultUnit(MyUnitIDS.kiloton,                   BigDecimal.valueOf(4.184E+30),     UnitGroup.ENERGY,   R.string.kiloton,                   R.string.kiloton_short),
        DefaultUnit(MyUnitIDS.megaton,                   BigDecimal.valueOf(4.184E+33),     UnitGroup.ENERGY,   R.string.megaton,                   R.string.megaton_short),
        DefaultUnit(MyUnitIDS.gigaton,                   BigDecimal.valueOf(4.184E+36),     UnitGroup.ENERGY,   R.string.gigaton,                   R.string.gigaton_short),
        DefaultUnit(MyUnitIDS.energy_horse_power_metric, BigDecimal.valueOf(2.6477955E+24), UnitGroup.ENERGY,   R.string.energy_horse_power_metric, R.string.energy_horse_power_metric_short),
        DefaultUnit(MyUnitIDS.calorie_th,                BigDecimal.valueOf(4184E+15),      UnitGroup.ENERGY,   R.string.calorie_th,                R.string.calorie_th_short),
        DefaultUnit(MyUnitIDS.kilocalorie_th,            BigDecimal.valueOf(4184E+18),      UnitGroup.ENERGY,   R.string.kilocalorie_th,            R.string.kilocalorie_th_short),
    )
}