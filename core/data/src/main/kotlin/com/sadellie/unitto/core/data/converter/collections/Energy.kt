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

package com.sadellie.unitto.core.data.converter.collections

import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import java.math.BigDecimal

internal val energyCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.electron_volt,
      BigDecimal("0.160217733"),
      UnitGroup.ENERGY,
      R.string.unit_electron_volt,
      R.string.unit_electron_volt_short,
    ),
    NormalUnit(
      UnitID.attojoule,
      BigDecimal("1.00"),
      UnitGroup.ENERGY,
      R.string.unit_attojoule,
      R.string.unit_attojoule_short,
    ),
    NormalUnit(
      UnitID.joule,
      BigDecimal("1000000000000000000"),
      UnitGroup.ENERGY,
      R.string.unit_joule,
      R.string.unit_joule_short,
    ),
    NormalUnit(
      UnitID.kilojoule,
      BigDecimal("1000000000000000000000"),
      UnitGroup.ENERGY,
      R.string.unit_kilojoule,
      R.string.unit_kilojoule_short,
    ),
    NormalUnit(
      UnitID.megajoule,
      BigDecimal("1000000000000000000000000"),
      UnitGroup.ENERGY,
      R.string.unit_megajoule,
      R.string.unit_megajoule_short,
    ),
    NormalUnit(
      UnitID.gigajoule,
      BigDecimal("1000000000000000000000000000"),
      UnitGroup.ENERGY,
      R.string.unit_gigajoule,
      R.string.unit_gigajoule_short,
    ),
    NormalUnit(
      UnitID.energy_ton,
      BigDecimal("4184000000000000000000000000"),
      UnitGroup.ENERGY,
      R.string.unit_energy_ton,
      R.string.unit_energy_ton_short,
    ),
    NormalUnit(
      UnitID.kiloton,
      BigDecimal("4184000000000000000000000000000"),
      UnitGroup.ENERGY,
      R.string.unit_kiloton,
      R.string.unit_kiloton_short,
    ),
    NormalUnit(
      UnitID.megaton,
      BigDecimal("4184000000000000000000000000000000"),
      UnitGroup.ENERGY,
      R.string.unit_megaton,
      R.string.unit_megaton_short,
    ),
    NormalUnit(
      UnitID.gigaton,
      BigDecimal("4184000000000000000000000000000000000"),
      UnitGroup.ENERGY,
      R.string.unit_gigaton,
      R.string.unit_gigaton_short,
    ),
    NormalUnit(
      UnitID.energy_horse_power_metric,
      BigDecimal("2647795500000000000000000"),
      UnitGroup.ENERGY,
      R.string.unit_energy_horse_power_metric,
      R.string.unit_energy_horse_power_metric_short,
    ),
    NormalUnit(
      UnitID.calorie_th,
      BigDecimal("4184000000000000000"),
      UnitGroup.ENERGY,
      R.string.unit_calorie_th,
      R.string.unit_calorie_th_short,
    ),
    NormalUnit(
      UnitID.kilocalorie_th,
      BigDecimal("4184000000000000000000"),
      UnitGroup.ENERGY,
      R.string.unit_kilocalorie_th,
      R.string.unit_kilocalorie_th_short,
    ),
  )
}
