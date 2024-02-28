/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

package com.sadellie.unitto.data.converter

import android.content.Context
import android.util.Log
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.data.common.isGreaterThan
import com.sadellie.unitto.data.common.isLessThan
import com.sadellie.unitto.data.common.trimZeros
import com.sadellie.unitto.data.converter.collections.accelerationCollection
import com.sadellie.unitto.data.converter.collections.angleCollection
import com.sadellie.unitto.data.converter.collections.areaCollection
import com.sadellie.unitto.data.converter.collections.currencyCollection
import com.sadellie.unitto.data.converter.collections.dataCollection
import com.sadellie.unitto.data.converter.collections.dataTransferCollection
import com.sadellie.unitto.data.converter.collections.electrostaticCapacitance
import com.sadellie.unitto.data.converter.collections.energyCollection
import com.sadellie.unitto.data.converter.collections.flowRateCollection
import com.sadellie.unitto.data.converter.collections.fluxCollection
import com.sadellie.unitto.data.converter.collections.forceCollection
import com.sadellie.unitto.data.converter.collections.fuelConsumptionCollection
import com.sadellie.unitto.data.converter.collections.lengthCollection
import com.sadellie.unitto.data.converter.collections.luminanceCollection
import com.sadellie.unitto.data.converter.collections.massCollection
import com.sadellie.unitto.data.converter.collections.numberBaseCollection
import com.sadellie.unitto.data.converter.collections.powerCollection
import com.sadellie.unitto.data.converter.collections.prefixCollection
import com.sadellie.unitto.data.converter.collections.pressureCollection
import com.sadellie.unitto.data.converter.collections.speedCollection
import com.sadellie.unitto.data.converter.collections.temperatureCollection
import com.sadellie.unitto.data.converter.collections.timeCollection
import com.sadellie.unitto.data.converter.collections.torqueCollection
import com.sadellie.unitto.data.converter.collections.volumeCollection
import com.sadellie.unitto.data.converter.remote.CurrencyApi
import com.sadellie.unitto.data.database.CurrencyRatesDao
import com.sadellie.unitto.data.database.CurrencyRatesEntity
import com.sadellie.unitto.data.database.UnitsDao
import com.sadellie.unitto.data.database.UnitsEntity
import com.sadellie.unitto.data.model.converter.UnitGroup
import com.sadellie.unitto.data.model.converter.UnitsListSorting
import com.sadellie.unitto.data.model.converter.unit.BasicUnit
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.sadellie.evaluatto.Expression
import io.github.sadellie.evaluatto.ExpressionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import javax.inject.Inject

class UnitsRepositoryImpl @Inject constructor(
    private val unitsDao: UnitsDao,
    private val currencyRatesDao: CurrencyRatesDao,
    @ApplicationContext private val mContext: Context,
) {
    private val inMemory = lengthCollection +
        currencyCollection +
        massCollection +
        speedCollection +
        temperatureCollection +
        areaCollection +
        timeCollection +
        volumeCollection +
        dataCollection +
        pressureCollection +
        accelerationCollection +
        energyCollection +
        powerCollection +
        angleCollection +
        dataTransferCollection +
        fluxCollection +
        numberBaseCollection +
        electrostaticCapacitance +
        prefixCollection +
        forceCollection +
        torqueCollection +
        flowRateCollection +
        luminanceCollection +
        fuelConsumptionCollection

    suspend fun getById(id: String): BasicUnit = withContext(Dispatchers.Default) {
        return@withContext inMemory.first { it.id == id }
    }

    suspend fun getPairId(id: String): String = withContext(Dispatchers.IO) {
        val basedUnitPair = getUnitStats(id).pairedUnitId
        if (basedUnitPair != null) return@withContext basedUnitPair

        val inMemoryUnit = inMemory.first { it.id == id }
        val collection = inMemory.filter { it.group == inMemoryUnit.group }

        val pair = collection
            .map { getById(it.id) to getUnitStats(it.id) }
            .sortedByDescending { it.second.frequency }
            .firstOrNull { it.second.isFavorite }?.first ?: collection.first()

        return@withContext pair.id
    }

    suspend fun incrementCounter(id: String) = withContext(Dispatchers.IO) {
        val basedUnit = unitsDao.getById(id)

        if (basedUnit == null) {
            unitsDao.insertUnit(
                UnitsEntity(
                    unitId = id,
                    frequency = 1,
                ),
            )
        } else {
            unitsDao.insertUnit(
                UnitsEntity(
                    unitId = basedUnit.unitId,
                    isFavorite = basedUnit.isFavorite,
                    pairedUnitId = basedUnit.pairedUnitId,
                    frequency = basedUnit.frequency + 1,
                ),
            )
        }
    }

    suspend fun setPair(
        id: String,
        pairId: String,
    ) = withContext(Dispatchers.IO) {
        val basedUnit = unitsDao.getById(id)

        if (basedUnit == null) {
            unitsDao.insertUnit(
                UnitsEntity(
                    unitId = id,
                    pairedUnitId = pairId,
                ),
            )
        } else {
            unitsDao.insertUnit(
                UnitsEntity(
                    unitId = basedUnit.unitId,
                    isFavorite = basedUnit.isFavorite,
                    pairedUnitId = pairId,
                    frequency = basedUnit.frequency,
                ),
            )
        }
    }

    suspend fun favorite(id: String) = withContext(Dispatchers.IO) {
        val basedUnit = unitsDao.getById(id)

        if (basedUnit == null) {
            unitsDao.insertUnit(
                UnitsEntity(
                    unitId = id,
                    isFavorite = true,
                ),
            )
        } else {
            unitsDao.insertUnit(
                UnitsEntity(
                    unitId = basedUnit.unitId,
                    isFavorite = !basedUnit.isFavorite,
                    pairedUnitId = basedUnit.pairedUnitId,
                    frequency = basedUnit.frequency,
                ),
            )
        }
    }

    suspend fun filterUnits(
        query: String,
        unitGroups: List<UnitGroup>,
        favoritesOnly: Boolean,
        sorting: UnitsListSorting,
    ): Map<UnitGroup, List<UnitSearchResultItem>> = withContext(Dispatchers.IO) {
        return@withContext filterUnitCollections(
            query = query,
            unitGroups = unitGroups,
            favoritesOnly = favoritesOnly,
            sorting = sorting,
        )
            .groupBy { it.basicUnit.group }
    }

    suspend fun filterUnitsAndBatchConvert(
        query: String,
        unitGroup: UnitGroup,
        favoritesOnly: Boolean,
        sorting: UnitsListSorting,
        unitFromId: String,
        input: String?,
    ): Map<UnitGroup, List<UnitSearchResultItem>> = withContext(Dispatchers.IO) {
        val units = filterUnitCollections(
            query = query,
            unitGroups = listOf(unitGroup),
            favoritesOnly = favoritesOnly,
            sorting = sorting,
        )

        if (input == null) {
            return@withContext units.groupBy { it.basicUnit.group }
        }

        val unitWithConversions = try {
            when (unitGroup) {
                UnitGroup.CURRENCY -> {
                    val inputBD = BigDecimal(input)

                    val validCurrencyPairs = withContext(Dispatchers.IO) {
                        currencyRatesDao.getLatestRates(unitFromId)
                    }
                        .filter { it.pairUnitValue?.isGreaterThan(BigDecimal.ZERO) ?: false }

                    val validIds = validCurrencyPairs.map { it.pairUnitId }

                    units
                        .filter { it.basicUnit.id in validIds }
                        .map { unitTo ->
                            unitTo.basicUnit as BasicUnit.Default
                            val factor = validCurrencyPairs
                                .first { it.pairUnitId == unitTo.basicUnit.id }
                                .pairUnitValue
                            val conversion = inputBD.multiply(factor)

                            unitTo.copy(
                                conversion = DefaultBatchConvertResult(conversion),
                            )
                        }
                }

                UnitGroup.NUMBER_BASE -> {
                    val unitFrom = getById(unitFromId) as BasicUnit.NumberBase

                    units.map { unitTo ->
                        unitTo.basicUnit as BasicUnit.NumberBase
                        val conversion = unitFrom.convert(unitTo.basicUnit, input)

                        unitTo.copy(
                            conversion = NumberBaseBatchConvertResult(conversion),
                        )
                    }
                }

                else -> {
                    val unitFrom = getById(unitFromId) as BasicUnit.Default
                    val inputBD = BigDecimal(input)

                    units.map { unitTo ->
                        unitTo.basicUnit as BasicUnit.Default
                        val conversion = unitFrom.convert(unitTo.basicUnit, inputBD)

                        unitTo.copy(
                            conversion = DefaultBatchConvertResult(conversion),
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("UnitsRepositoryImpl", "Failed to batch convert: $e")
            units
        }

        return@withContext unitWithConversions.groupBy { it.basicUnit.group }
    }

    suspend fun convertDefault(
        unitFromId: String,
        unitToId: String,
        value1: String,
        value2: String,
        formatTime: Boolean,
    ): ConverterResult = withContext(Dispatchers.Default) {
        val calculated: BigDecimal = try {
            // Calculate expression in first text field
            var calculated1 = Expression(value1.ifEmpty { Token.Digit._0 }).calculate()

            // Calculate expression in second text field
            if (unitFromId == UnitID.foot) {
                val calculatedInches = Expression(value2.ifEmpty { Token.Digit._0 }).calculate()
                // turn inches into feet so that it all comes down to converting from feet only
                val inches = getById(UnitID.inch) as BasicUnit.Default
                val feet = getById(UnitID.foot) as BasicUnit.Default
                val inchesConvertedToFeet = inches.convert(feet, calculatedInches)

                calculated1 += inchesConvertedToFeet
            }

            calculated1
        } catch (e: ExpressionException.DivideByZero) {
            return@withContext ConverterResult.Error.DivideByZero
        } catch (e: Exception) {
            return@withContext ConverterResult.Error.BadInput
        }

        return@withContext try {
            val unitFrom = getById(unitFromId) as BasicUnit.Default
            val unitTo = getById(unitToId) as BasicUnit.Default

            when {
                (unitFrom.group == UnitGroup.TIME) and (formatTime) ->
                    convertTime(unitFrom, calculated)

                unitTo.id == UnitID.foot ->
                    convertToFoot(unitFrom, unitTo, calculated)

                unitFrom.group == UnitGroup.CURRENCY ->
                    convertCurrencies(unitFromId, unitToId, calculated)

                else ->
                    convertDefault(unitFrom, unitTo, calculated)
            }
        } catch (e: Exception) {
            ConverterResult.Error.ConversionError
        }
    }

    suspend fun convertNumberBase(
        unitFromId: String,
        unitToId: String,
        value: String,
    ): ConverterResult = withContext(Dispatchers.Default) {
        return@withContext try {
            val unitFrom = getById(unitFromId) as BasicUnit.NumberBase
            val unitTo = getById(unitToId) as BasicUnit.NumberBase
            val conversion = unitFrom.convert(unitTo, value)

            ConverterResult.NumberBase(conversion)
        } catch (e: Exception) {
            ConverterResult.Error.ConversionError
        }
    }

    private suspend fun getUnitStats(id: String): UnitsEntity = withContext(Dispatchers.IO) {
        unitsDao.getById(id) ?: UnitsEntity(unitId = id)
    }

    private fun convertDefault(
        unitFrom: BasicUnit.Default,
        unitTo: BasicUnit.Default,
        value: BigDecimal,
    ): ConverterResult.Default = ConverterResult.Default(unitFrom.convert(unitTo, value), value)

    internal fun convertTime(
        unitFrom: BasicUnit.Default,
        value: BigDecimal,
    ): ConverterResult.Time {
        val input = value.multiply(unitFrom.factor)

        val negative = input < BigDecimal.ZERO
        val inputAbs = input.abs()

        if (inputAbs.isLessThan(attosecondBasicUnit)) {
            return ConverterResult.Time(
                negative = negative,
                day = BigDecimal.ZERO,
                hour = BigDecimal.ZERO,
                minute = BigDecimal.ZERO,
                second = BigDecimal.ZERO,
                millisecond = BigDecimal.ZERO,
                microsecond = BigDecimal.ZERO,
                nanosecond = BigDecimal.ZERO,
                attosecond = inputAbs,
            )
        }

        if (inputAbs.isLessThan(nanosecondBasicUnit)) {
            return ConverterResult.Time(
                negative = negative,
                day = BigDecimal.ZERO,
                hour = BigDecimal.ZERO,
                minute = BigDecimal.ZERO,
                second = BigDecimal.ZERO,
                millisecond = BigDecimal.ZERO,
                microsecond = BigDecimal.ZERO,
                nanosecond = BigDecimal.ZERO,
                attosecond = inputAbs.trimZeros(),
            )
        }

        // DAY
        var division = inputAbs.divideAndRemainder(dayBasicUnit)
        val day = division.component1().setScale(0, RoundingMode.HALF_EVEN)
        var remainingSeconds = division.component2().setScale(0, RoundingMode.HALF_EVEN)

        division = remainingSeconds.divideAndRemainder(hourBasicUnit)
        val hour = division.component1()
        remainingSeconds = division.component2()

        division = remainingSeconds.divideAndRemainder(minuteBasicUnit)
        val minute = division.component1()
        remainingSeconds = division.component2()

        division = remainingSeconds.divideAndRemainder(secondBasicUnit)
        val second = division.component1()
        remainingSeconds = division.component2()

        division = remainingSeconds.divideAndRemainder(millisecondBasicUnit)
        val millisecond = division.component1()
        remainingSeconds = division.component2()

        division = remainingSeconds.divideAndRemainder(microsecondBasicUnit)
        val microsecond = division.component1()
        remainingSeconds = division.component2()

        division = remainingSeconds.divideAndRemainder(nanosecondBasicUnit)
        val nanosecond = division.component1()
        remainingSeconds = division.component2()

        val attosecond = remainingSeconds

        return ConverterResult.Time(
            negative = negative,
            day = day,
            hour = hour,
            minute = minute,
            second = second,
            millisecond = millisecond,
            microsecond = microsecond,
            nanosecond = nanosecond,
            attosecond = attosecond,
        )
    }

    private suspend fun convertToFoot(
        unitFrom: BasicUnit.Default,
        unitTo: BasicUnit.Default,
        value: BigDecimal,
    ): ConverterResult.FootInch = ConverterResult.FootInch.fromBigDecimal(
        input = unitFrom.convert(unitTo, value),
        footUnit = unitTo,
        inchUnit = getById(UnitID.inch) as BasicUnit.Default,
    )

    private suspend fun convertCurrencies(
        unitFromId: String,
        unitToId: String,
        value: BigDecimal,
    ): ConverterResult = withContext(Dispatchers.IO) {
        refreshCurrencyRates(unitFromId)

        val latestRate = currencyRatesDao.getLatestRate(unitFromId, unitToId)
        if (latestRate?.pairUnitValue == null) return@withContext ConverterResult.Error.Currency

        val conversion = value.multiply(latestRate.pairUnitValue)

        return@withContext ConverterResult.Default(conversion, value)
    }

    private suspend fun filterUnitCollections(
        query: String,
        unitGroups: List<UnitGroup>,
        favoritesOnly: Boolean,
        sorting: UnitsListSorting,
    ): Sequence<UnitSearchResultItem> = withContext(Dispatchers.IO) {
        var units = inMemory
            .filter { it.group in unitGroups }
            .map { UnitSearchResultItem(it, getUnitStats(it.id), null) }
            .asSequence()

        if (favoritesOnly) {
            units = units.filter { it.stats.isFavorite }
        }

        units = when (sorting) {
            UnitsListSorting.USAGE -> units.sortedByDescending { it.stats.frequency }
            UnitsListSorting.ALPHABETICAL -> units.sortedBy { mContext.getString(it.basicUnit.displayName) }
            UnitsListSorting.SCALE_ASC -> units.sortedBy { it.basicUnit.factor }
            UnitsListSorting.SCALE_DESC -> units.sortedByDescending { it.basicUnit.factor }
            else -> units
        }

        units = if (query.isEmpty()) {
            units.sortedByDescending { it.stats.isFavorite }
        } else {
            units.filterByLev(query, mContext)
        }
        return@withContext units
    }

    private suspend fun refreshCurrencyRates(unitFromId: String) = withContext(Dispatchers.IO) {
        val latestUpdateDate = currencyRatesDao.getLatestRateTimeStamp(unitFromId)
        val currentDate = LocalDate.now().toEpochDay()

        if (latestUpdateDate != currentDate) {
            // Need to update cache needed
            try {
                val conversions = CurrencyApi.service.getCurrencyPairs(unitFromId)
                val rates = conversions.currency
                    .map { (pairId, pairValue) ->
                        CurrencyRatesEntity(
                            baseUnitId = unitFromId,
                            date = currentDate,
                            pairUnitId = pairId,
                            pairUnitValue = BigDecimal.valueOf(pairValue),
                        )
                    }
                currencyRatesDao.insertRates(rates)
            } catch (e: Exception) {
                Log.d("UnitsRepositoryImpl", "Skipped update: $e")
            }
        }
    }
}

private val dayBasicUnit by lazy { BigDecimal("86400000000000000000000") }
private val hourBasicUnit by lazy { BigDecimal("3600000000000000000000") }
private val minuteBasicUnit by lazy { BigDecimal("60000000000000000000") }
private val secondBasicUnit by lazy { BigDecimal("1000000000000000000") }
private val millisecondBasicUnit by lazy { BigDecimal("1000000000000000") }
private val microsecondBasicUnit by lazy { BigDecimal("1000000000000") }
private val nanosecondBasicUnit by lazy { BigDecimal("1000000000") }
private val attosecondBasicUnit by lazy { BigDecimal("1") }
