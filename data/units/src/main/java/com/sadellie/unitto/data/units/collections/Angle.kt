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

internal val angleCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(MyUnitIDS.angle_second,  BigDecimal.valueOf(1),              UnitGroup.ANGLE,    R.string.angle_second,  R.string.angle_second_short),
        NormalUnit(MyUnitIDS.angle_minute,  BigDecimal.valueOf(60),             UnitGroup.ANGLE,    R.string.angle_minute,  R.string.angle_minute_short),
        NormalUnit(MyUnitIDS.degree,        BigDecimal.valueOf(3600),           UnitGroup.ANGLE,    R.string.degree,        R.string.degree_short),
        NormalUnit(MyUnitIDS.radian,        BigDecimal.valueOf(206264.8062471), UnitGroup.ANGLE,    R.string.radian,        R.string.radian_short),
        NormalUnit(MyUnitIDS.sextant,       BigDecimal.valueOf(216000),         UnitGroup.ANGLE,    R.string.sextant,       R.string.sextant_short),
        NormalUnit(MyUnitIDS.turn,          BigDecimal.valueOf(1296000),        UnitGroup.ANGLE,    R.string.turn,          R.string.turn_short),
    )
}
