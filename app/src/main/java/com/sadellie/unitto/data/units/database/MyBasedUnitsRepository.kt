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