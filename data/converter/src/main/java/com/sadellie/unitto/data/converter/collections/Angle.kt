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
import com.sadellie.unitto.data.model.converter.UnitGroup
import com.sadellie.unitto.data.model.converter.unit.BasicUnit
import com.sadellie.unitto.data.model.converter.unit.NormalUnit
import java.math.BigDecimal

internal val angleCollection: List<BasicUnit> by lazy {
    listOf(
        NormalUnit(UnitID.angle_second,  BigDecimal("1"),              UnitGroup.ANGLE,    R.string.unit_angle_second,  R.string.unit_angle_second_short),
        NormalUnit(UnitID.angle_minute,  BigDecimal("60"),             UnitGroup.ANGLE,    R.string.unit_angle_minute,  R.string.unit_angle_minute_short),
        NormalUnit(UnitID.degree,        BigDecimal("3600"),           UnitGroup.ANGLE,    R.string.unit_degree,        R.string.unit_degree_short),
        NormalUnit(UnitID.radian,        BigDecimal("206264.8062471"), UnitGroup.ANGLE,    R.string.unit_radian,        R.string.unit_radian_short),
        NormalUnit(UnitID.sextant,       BigDecimal("216000"),         UnitGroup.ANGLE,    R.string.unit_sextant,       R.string.unit_sextant_short),
        NormalUnit(UnitID.turn,          BigDecimal("1296000"),        UnitGroup.ANGLE,    R.string.unit_turn,          R.string.unit_turn_short),
    )
}
