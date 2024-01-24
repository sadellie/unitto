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

internal val fluxCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(UnitID.maxwell,    BigDecimal.valueOf(1),                     UnitGroup.FLUX, R.string.unit_maxwell,       R.string.unit_maxwell_short),
        NormalUnit(UnitID.microweber, BigDecimal.valueOf(100),                   UnitGroup.FLUX, R.string.unit_microweber,    R.string.unit_microweber_short),
        NormalUnit(UnitID.milliweber, BigDecimal.valueOf(100000),                UnitGroup.FLUX, R.string.unit_milliweber,    R.string.unit_milliweber_short),
        NormalUnit(UnitID.weber,      BigDecimal.valueOf(100000000),             UnitGroup.FLUX, R.string.unit_weber,         R.string.unit_weber_short),
        NormalUnit(UnitID.kiloweber,  BigDecimal.valueOf(100000000000),          UnitGroup.FLUX, R.string.unit_kiloweber,     R.string.unit_kiloweber_short),
        NormalUnit(UnitID.megaweber,  BigDecimal.valueOf(100000000000000),       UnitGroup.FLUX, R.string.unit_megaweber,     R.string.unit_megaweber_short),
        NormalUnit(UnitID.gigaweber,  BigDecimal.valueOf(100000000000000000),    UnitGroup.FLUX, R.string.unit_gigaweber,     R.string.unit_gigaweber_short),
    )
}
