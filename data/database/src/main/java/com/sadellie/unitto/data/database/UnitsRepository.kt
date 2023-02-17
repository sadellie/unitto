/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

package com.sadellie.unitto.data.database

import javax.inject.Inject

class UnitsRepository @Inject constructor (private val unitsDao: UnitsDao) {
    /**
     * Method to insert units. Will rewrite row if unit's id is already in database
     *
     * @param unit Unit to add
     */
    suspend fun insertUnits(unit: UnitsEntity) {
        unitsDao.insertUnits(unit)
    }

    /**
     * Method to get all units from units table
     *
     * @return List of [UnitsEntity] objects that represent one row in table
     */
    suspend fun getAll(): List<UnitsEntity> {
        return unitsDao.getAll()
    }
}