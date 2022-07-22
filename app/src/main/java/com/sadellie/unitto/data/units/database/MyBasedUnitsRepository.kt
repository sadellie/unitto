/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

package com.sadellie.unitto.data.units.database

import javax.inject.Inject

class MyBasedUnitsRepository @Inject constructor (private val myBasedUnitDao: MyBasedUnitDao) {
    /**
     * Method to insert units. Will rewrite row if unit's id is already in database
     *
     * @param unit Unit to add
     */
    suspend fun insertUnits(unit: MyBasedUnit) {
        myBasedUnitDao.insertUnits(unit)
    }

    /**
     * Method to get all units from units table
     *
     * @return List of [MyBasedUnit] objects that represent one row in table
     */
    suspend fun getAll(): List<MyBasedUnit> {
        return myBasedUnitDao.getAll()
    }
}