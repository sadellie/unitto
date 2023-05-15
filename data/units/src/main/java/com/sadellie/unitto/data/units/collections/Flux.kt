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

internal val fluxCollection: List<AbstractUnit> by lazy {
    listOf(
        DefaultUnit(MyUnitIDS.maxwell,    BigDecimal.valueOf(1),                     UnitGroup.FLUX, R.string.maxwell,       R.string.maxwell_short),
        DefaultUnit(MyUnitIDS.microweber, BigDecimal.valueOf(100),                   UnitGroup.FLUX, R.string.microweber,    R.string.microweber_short),
        DefaultUnit(MyUnitIDS.milliweber, BigDecimal.valueOf(100000),                UnitGroup.FLUX, R.string.milliweber,    R.string.milliweber_short),
        DefaultUnit(MyUnitIDS.weber,      BigDecimal.valueOf(100000000),             UnitGroup.FLUX, R.string.weber,         R.string.weber_short),
        DefaultUnit(MyUnitIDS.kiloweber,  BigDecimal.valueOf(100000000000),          UnitGroup.FLUX, R.string.kiloweber,     R.string.kiloweber_short),
        DefaultUnit(MyUnitIDS.megaweber,  BigDecimal.valueOf(100000000000000),       UnitGroup.FLUX, R.string.megaweber,     R.string.megaweber_short),
        DefaultUnit(MyUnitIDS.gigaweber,  BigDecimal.valueOf(100000000000000000),    UnitGroup.FLUX, R.string.gigaweber,     R.string.gigaweber_short),
    )
}