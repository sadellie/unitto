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

import com.sadellie.unitto.data.model.AbstractUnit
import com.sadellie.unitto.data.model.DefaultUnit
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.R
import java.math.BigDecimal

internal val pressureCollection: List<AbstractUnit> by lazy {
    listOf(
        DefaultUnit(MyUnitIDS.attopascal,            BigDecimal.valueOf(1),                      UnitGroup.PRESSURE, R.string.attopascal,            R.string.attopascal_short),
        DefaultUnit(MyUnitIDS.femtopascal,           BigDecimal.valueOf(1E+3),                   UnitGroup.PRESSURE, R.string.femtopascal,           R.string.femtopascal_short),
        DefaultUnit(MyUnitIDS.picopascal,            BigDecimal.valueOf(1E+6),                   UnitGroup.PRESSURE, R.string.picopascal,            R.string.picopascal_short),
        DefaultUnit(MyUnitIDS.nanopascal,            BigDecimal.valueOf(1E+9),                   UnitGroup.PRESSURE, R.string.nanopascal,            R.string.nanopascal_short),
        DefaultUnit(MyUnitIDS.micropascal,           BigDecimal.valueOf(1E+12),                  UnitGroup.PRESSURE, R.string.micropascal,           R.string.micropascal_short),
        DefaultUnit(MyUnitIDS.millipascal,           BigDecimal.valueOf(1E+15),                  UnitGroup.PRESSURE, R.string.millipascal,           R.string.millipascal_short),
        DefaultUnit(MyUnitIDS.centipascal,           BigDecimal.valueOf(1E+16),                  UnitGroup.PRESSURE, R.string.centipascal,           R.string.centipascal_short),
        DefaultUnit(MyUnitIDS.decipascal,            BigDecimal.valueOf(1E+17),                  UnitGroup.PRESSURE, R.string.decipascal,            R.string.decipascal_short),
        DefaultUnit(MyUnitIDS.pascal,                BigDecimal.valueOf(1E+18),                  UnitGroup.PRESSURE, R.string.pascal,                R.string.pascal_short),
        DefaultUnit(MyUnitIDS.dekapascal,            BigDecimal.valueOf(1E+19),                  UnitGroup.PRESSURE, R.string.dekapascal,            R.string.dekapascal_short),
        DefaultUnit(MyUnitIDS.hectopascal,           BigDecimal.valueOf(1E+20),                  UnitGroup.PRESSURE, R.string.hectopascal,           R.string.hectopascal_short),
        DefaultUnit(MyUnitIDS.millibar,              BigDecimal.valueOf(1E+20),                  UnitGroup.PRESSURE, R.string.millibar,              R.string.millibar_short),
        DefaultUnit(MyUnitIDS.bar,                   BigDecimal.valueOf(1E+23),                  UnitGroup.PRESSURE, R.string.bar,                   R.string.bar_short),
        DefaultUnit(MyUnitIDS.kilopascal,            BigDecimal.valueOf(1E+21),                  UnitGroup.PRESSURE, R.string.kilopascal,            R.string.kilopascal_short),
        DefaultUnit(MyUnitIDS.megapascal,            BigDecimal.valueOf(1E+24),                  UnitGroup.PRESSURE, R.string.megapascal,            R.string.megapascal_short),
        DefaultUnit(MyUnitIDS.gigapascal,            BigDecimal.valueOf(1E+27),                  UnitGroup.PRESSURE, R.string.gigapascal,            R.string.gigapascal_short),
        DefaultUnit(MyUnitIDS.terapascal,            BigDecimal.valueOf(1E+30),                  UnitGroup.PRESSURE, R.string.terapascal,            R.string.terapascal_short),
        DefaultUnit(MyUnitIDS.petapascal,            BigDecimal.valueOf(1E+33),                  UnitGroup.PRESSURE, R.string.petapascal,            R.string.petapascal_short),
        DefaultUnit(MyUnitIDS.exapascal,             BigDecimal.valueOf(1E+36),                  UnitGroup.PRESSURE, R.string.exapascal,             R.string.exapascal_short),
        DefaultUnit(MyUnitIDS.psi,                   BigDecimal.valueOf(6.8947572931783E+21),    UnitGroup.PRESSURE, R.string.psi,                   R.string.psi_short),
        DefaultUnit(MyUnitIDS.ksi,                   BigDecimal.valueOf(6.8947572931783E+24),    UnitGroup.PRESSURE, R.string.ksi,                   R.string.ksi_short),
        DefaultUnit(MyUnitIDS.standard_atmosphere,   BigDecimal.valueOf(101.325E+21),            UnitGroup.PRESSURE, R.string.standard_atmosphere,   R.string.standard_atmosphere_short),
        DefaultUnit(MyUnitIDS.torr,                  BigDecimal.valueOf(1.3332236842108281E+20), UnitGroup.PRESSURE, R.string.torr,                  R.string.torr_short),
        DefaultUnit(MyUnitIDS.micron_of_mercury,     BigDecimal.valueOf(1.3332236842108281E+17), UnitGroup.PRESSURE, R.string.micron_of_mercury,     R.string.micron_of_mercury_short),
        DefaultUnit(MyUnitIDS.millimeter_of_mercury, BigDecimal.valueOf(1.3332236842108281E+20), UnitGroup.PRESSURE, R.string.millimeter_of_mercury, R.string.millimeter_of_mercury_short),
    )
}