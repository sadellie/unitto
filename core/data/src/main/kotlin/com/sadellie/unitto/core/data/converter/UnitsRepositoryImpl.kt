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

package com.sadellie.unitto.core.data.converter

import android.content.Context
import android.util.Log
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.isEqualTo
import com.sadellie.unitto.core.common.isLessThan
import com.sadellie.unitto.core.common.setMaxScale
import com.sadellie.unitto.core.data.converter.collections.accelerationCollection
import com.sadellie.unitto.core.data.converter.collections.angleCollection
import com.sadellie.unitto.core.data.converter.collections.areaCollection
import com.sadellie.unitto.core.data.converter.collections.currencyCollection
import com.sadellie.unitto.core.data.converter.collections.dataCollection
import com.sadellie.unitto.core.data.converter.collections.dataTransferCollection
import com.sadellie.unitto.core.data.converter.collections.electrostaticCapacitance
import com.sadellie.unitto.core.data.converter.collections.energyCollection
import com.sadellie.unitto.core.data.converter.collections.flowRateCollection
import com.sadellie.unitto.core.data.converter.collections.fluxCollection
import com.sadellie.unitto.core.data.converter.collections.forceCollection
import com.sadellie.unitto.core.data.converter.collections.fuelConsumptionCollection
import com.sadellie.unitto.core.data.converter.collections.lengthCollection
import com.sadellie.unitto.core.data.converter.collections.luminanceCollection
import com.sadellie.unitto.core.data.converter.collections.massCollection
import com.sadellie.unitto.core.data.converter.collections.numberBaseCollection
import com.sadellie.unitto.core.data.converter.collections.powerCollection
import com.sadellie.unitto.core.data.converter.collections.prefixCollection
import com.sadellie.unitto.core.data.converter.collections.pressureCollection
import com.sadellie.unitto.core.data.converter.collections.speedCollection
import com.sadellie.unitto.core.data.converter.collections.temperatureCollection
import com.sadellie.unitto.core.data.converter.collections.timeCollection
import com.sadellie.unitto.core.data.converter.collections.torqueCollection
import com.sadellie.unitto.core.data.converter.collections.volumeCollection
import com.sadellie.unitto.core.database.CurrencyRatesDao
import com.sadellie.unitto.core.database.CurrencyRatesEntity
import com.sadellie.unitto.core.database.UnitsDao
import com.sadellie.unitto.core.database.UnitsEntity
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import com.sadellie.unitto.core.remote.CurrencyApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.sadellie.evaluatto.Expression
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class UnitsRepositoryImpl
@Inject
constructor(
  private val unitsDao: UnitsDao,
  private val currencyRatesDao: CurrencyRatesDao,
  private val currencyApiService: CurrencyApiService,
  @ApplicationContext private val mContext: Context,
) : UnitsRepository {
  private val inMemory =
    lengthCollection +
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

  override val currencyRateUpdateState =
    MutableStateFlow<CurrencyRateUpdateState>(CurrencyRateUpdateState.Nothing)

  override suspend fun getById(id: String): BasicUnit =
    withContext(Dispatchers.Default) { inMemory.first { it.id == id } }

  override suspend fun getPairId(id: String): String =
    withContext(Dispatchers.IO) {
      val basedUnitPair = getUnitStats(id).pairedUnitId
      if (basedUnitPair != null) return@withContext basedUnitPair

      // unit has no pair, get most popular unit
      val unit = getById(id)
      // same group, except for unit itself
      val potentialPairIds = inMemory.filter { it.group == unit.group && it.id != id }.map { it.id }
      val unitsStatsForUnitsInUnitGroup =
        unitsDao.getByIdsSortedByFavoriteAndFrequencyDesc(potentialPairIds)

      // fallback in specific order
      // most popular favorite, just popular or first in collection
      return@withContext unitsStatsForUnitsInUnitGroup?.unitId ?: potentialPairIds.first()
    }

  override suspend fun incrementCounter(id: String) =
    withContext(Dispatchers.IO) {
      val basedUnit = unitsDao.getById(id)

      if (basedUnit == null) {
        unitsDao.insertUnit(UnitsEntity(unitId = id, frequency = 1))
      } else {
        unitsDao.insertUnit(
          UnitsEntity(
            unitId = basedUnit.unitId,
            isFavorite = basedUnit.isFavorite,
            pairedUnitId = basedUnit.pairedUnitId,
            frequency = basedUnit.frequency + 1,
          )
        )
      }
    }

  override suspend fun setPair(id: String, pairId: String) =
    withContext(Dispatchers.IO) {
      val basedUnit = unitsDao.getById(id)

      if (basedUnit == null) {
        unitsDao.insertUnit(UnitsEntity(unitId = id, pairedUnitId = pairId))
      } else {
        unitsDao.insertUnit(
          UnitsEntity(
            unitId = basedUnit.unitId,
            isFavorite = basedUnit.isFavorite,
            pairedUnitId = pairId,
            frequency = basedUnit.frequency,
          )
        )
      }
    }

  override suspend fun favorite(id: String) =
    withContext(Dispatchers.IO) {
      val basedUnit = unitsDao.getById(id)

      if (basedUnit == null) {
        unitsDao.insertUnit(UnitsEntity(unitId = id, isFavorite = true))
      } else {
        unitsDao.insertUnit(
          UnitsEntity(
            unitId = basedUnit.unitId,
            isFavorite = !basedUnit.isFavorite,
            pairedUnitId = basedUnit.pairedUnitId,
            frequency = basedUnit.frequency,
          )
        )
      }
    }

  override suspend fun filterUnits(
    query: String,
    unitGroups: List<UnitGroup>,
    favoritesOnly: Boolean,
    sorting: UnitsListSorting,
  ): Map<UnitGroup, List<UnitSearchResultItem>> =
    withContext(Dispatchers.IO) {
      return@withContext filterUnitCollections(
          query = query,
          unitGroups = unitGroups,
          favoritesOnly = favoritesOnly,
          sorting = sorting,
        )
        .groupBy { it.basicUnit.group }
    }

  override suspend fun filterUnitsAndBatchConvert(
    query: String,
    unitGroup: UnitGroup,
    favoritesOnly: Boolean,
    sorting: UnitsListSorting,
    unitFromId: String,
    input1: String,
    input2: String,
  ): Map<UnitGroup, List<UnitSearchResultItem>> =
    withContext(Dispatchers.IO) {
      val unitFrom = getById(unitFromId)
      val units =
        filterUnitCollections(
          query = query,
          unitGroups = listOf(unitGroup),
          favoritesOnly = favoritesOnly,
          sorting = sorting,
        )

      val unitWithConversions =
        try {
          when {
            unitGroup == UnitGroup.NUMBER_BASE ->
              convertNumberBaseBatch(
                input = input1,
                unitSearchResultItems = units,
                unitFrom = unitFrom as BasicUnit.NumberBase,
              )

            unitGroup == UnitGroup.CURRENCY ->
              convertCurrenciesBatch(
                input = input1,
                unitSearchResultItems = units,
                unitFrom = unitFrom as BasicUnit.Default,
              )

            // foot and inches input
            unitFrom.id == UnitID.foot ->
              convertDefaultBatch(
                input = calculateFootAndInchesInput(input1, input2),
                unitSearchResultItems = units,
                unitFrom = unitFrom as BasicUnit.Default,
              )

            else ->
              convertDefaultBatch(
                input = calculateInput(input1),
                unitSearchResultItems = units,
                unitFrom = unitFrom as BasicUnit.Default,
              )
          }
        } catch (e: Exception) {
          Log.e("UnitsRepositoryImpl", "Failed to batch convert: $e")
          units.toList()
        }

      return@withContext unitWithConversions.groupBy { it.basicUnit.group }
    }

  override suspend fun convert(
    unitFromId: String,
    unitToId: String,
    value1: String,
    value2: String,
    formatTime: Boolean,
  ): ConverterResult {
    val unitFrom = getById(unitFromId)
    val unitTo = getById(unitToId)

    return when {
      unitFrom.group == UnitGroup.NUMBER_BASE && unitTo.group == UnitGroup.NUMBER_BASE ->
        convertNumberBase(
          unitFrom = unitFrom as BasicUnit.NumberBase,
          unitTo = unitTo as BasicUnit.NumberBase,
          value = value1,
        )

      unitFrom.group == UnitGroup.TIME && unitTo.group == UnitGroup.TIME && formatTime ->
        convertTimeAndFormatToHumanReadable(
          unitFrom = unitFrom as BasicUnit.Default,
          value = calculateInput(value1),
        )

      unitFrom.group == UnitGroup.CURRENCY && unitTo.group == UnitGroup.CURRENCY ->
        convertCurrencies(
          unitFrom = unitFrom as BasicUnit.Default,
          unitTo = unitTo as BasicUnit.Default,
          value = calculateInput(value1),
        )

      // foot and inches output and input
      unitTo.id == UnitID.foot && unitFrom.id == UnitID.foot ->
        convertFoot(
          footUnit = unitTo as BasicUnit.Default,
          inchUnit = getById(UnitID.inch) as BasicUnit.Default,
          feetInput = calculateFootAndInchesInput(value1, value2),
        )

      // foot and inches output
      unitTo.id == UnitID.foot ->
        convertFoot(
          footUnit = unitTo as BasicUnit.Default,
          inchUnit = getById(UnitID.inch) as BasicUnit.Default,
          feetInput = (unitFrom as BasicUnit.Default).convert(unitTo, calculateInput(value1)),
        )

      // foot and inches input
      unitFrom.id == UnitID.foot ->
        convertDefault(
          unitFrom = unitFrom as BasicUnit.Default,
          unitTo = unitTo as BasicUnit.Default,
          value = calculateFootAndInchesInput(value1, value2),
        )

      else ->
        convertDefault(
          unitFrom = unitFrom as BasicUnit.Default,
          unitTo = unitTo as BasicUnit.Default,
          value = calculateInput(value1),
        )
    }
  }

  private suspend fun getUnitStats(id: String): UnitStats {
    val unitStatsFromDatabase = withContext(Dispatchers.IO) { unitsDao.getById(id) }
    return unitStatsFromDatabase?.toUnitStats() ?: UnitStats(id)
  }

  private suspend fun getUnitStats(ids: List<String>): List<UnitStats> {
    val unitStatsFromDatabase = withContext(Dispatchers.IO) { unitsDao.getByIds(ids) }
    if (unitStatsFromDatabase.isEmpty()) return emptyList()
    return unitStatsFromDatabase.map { it.toUnitStats() }
  }

  private suspend fun convertNumberBaseBatch(
    input: String,
    unitSearchResultItems: Sequence<UnitSearchResultItem>,
    unitFrom: BasicUnit.NumberBase,
  ) =
    withContext(Dispatchers.Default) {
      val result = mutableListOf<UnitSearchResultItem>()
      unitSearchResultItems.forEach { unitSearchResultItem ->
        val conversion =
          convertNumberBase(
            unitFrom = unitFrom,
            unitTo = unitSearchResultItem.basicUnit as BasicUnit.NumberBase,
            value = input,
          )
        result.add(unitSearchResultItem.copy(conversion = conversion))
      }
      return@withContext result
    }

  private suspend fun convertCurrenciesBatch(
    input: String,
    unitSearchResultItems: Sequence<UnitSearchResultItem>,
    unitFrom: BasicUnit.Default,
  ) =
    withContext(Dispatchers.Default) {
      val calculatedInput = calculateInput(input)
      if (calculatedInput.isEqualTo(BigDecimal.ZERO))
        return@withContext unitSearchResultItems.toList()

      val result = mutableListOf<UnitSearchResultItem>()
      refreshCurrencyRates(unitFrom.id)
      unitSearchResultItems.forEach { unitSearchResultItem ->
        val latestRate =
          currencyRatesDao
            .getLatestRate(unitFrom.id, unitSearchResultItem.basicUnit.id)
            ?.pairUnitValue

        if (latestRate != null) {
          val conversion =
            ConverterResult.Default(
              calculatedInput
                .multiply(latestRate)
                .setScale(BATCH_CURRENCY_CONVERSION_SCALE, RoundingMode.HALF_EVEN),
              calculatedInput,
            )
          result.add(unitSearchResultItem.copy(conversion = conversion))
        }
      }

      return@withContext result
    }

  private suspend fun convertDefaultBatch(
    input: BigDecimal,
    unitSearchResultItems: Sequence<UnitSearchResultItem>,
    unitFrom: BasicUnit.Default,
  ) =
    withContext(Dispatchers.Default) {
      val result = mutableListOf<UnitSearchResultItem>()
      unitSearchResultItems.forEach { unitSearchResultItem ->
        val conversion =
          convertDefault(
            unitFrom = unitFrom,
            unitTo = unitSearchResultItem.basicUnit as BasicUnit.Default,
            value = input,
          )
        result.add(unitSearchResultItem.copy(conversion = conversion))
      }
      return@withContext result
    }

  private fun convertDefault(
    unitFrom: BasicUnit.Default,
    unitTo: BasicUnit.Default,
    value: BigDecimal,
  ): ConverterResult.Default {
    return ConverterResult.Default(unitFrom.convert(unitTo, value), value)
  }

  private fun convertNumberBase(
    unitFrom: BasicUnit.NumberBase,
    unitTo: BasicUnit.NumberBase,
    value: String,
  ): ConverterResult.NumberBase {
    val conversion = unitFrom.convert(unitTo, value)
    return ConverterResult.NumberBase(conversion)
  }

  private fun convertTimeAndFormatToHumanReadable(
    unitFrom: BasicUnit.Default,
    value: BigDecimal,
  ): ConverterResult.Time {
    if (value.isEqualTo(BigDecimal.ZERO)) return ConverterResult.Time()

    val input = value.multiply(unitFrom.factor)

    val negative = input < BigDecimal.ZERO
    val inputAbs = input.abs()

    if (inputAbs.isLessThan(nanosecondBasicUnit)) {
      // no conversion needed, will be definitely formatted as attoseconds
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

  private fun convertFoot(
    footUnit: BasicUnit.Default,
    inchUnit: BasicUnit.Default,
    feetInput: BigDecimal,
  ): ConverterResult.FootInch {
    val (integral, fractional) = feetInput.divideAndRemainder(BigDecimal.ONE)
    val fractionInInches = footUnit.convert(inchUnit, fractional)

    return ConverterResult.FootInch(integral, fractionInInches)
  }

  private fun calculateInput(value: String): BigDecimal {
    // Calculate expression in first text field
    val calculated = Expression(value.ifEmpty { Token.Digit.DIGIT_0 }).calculate()
    return calculated
  }

  /** Takes input from both text fields and returns amount of feet */
  private suspend fun calculateFootAndInchesInput(
    footInput: String,
    inchInput: String,
  ): BigDecimal {
    // Calculate expression in first text field
    var calculated = Expression(footInput.ifEmpty { Token.Digit.DIGIT_0 }).calculate()

    val calculatedInches = Expression(inchInput.ifEmpty { Token.Digit.DIGIT_0 }).calculate()
    // turn inches into feet so that it all comes down to converting from feet only
    val inches = getById(UnitID.inch) as BasicUnit.Default
    val feet = getById(UnitID.foot) as BasicUnit.Default
    val inchesConvertedToFeet = inches.convert(feet, calculatedInches)

    calculated += inchesConvertedToFeet

    return calculated
  }

  private suspend fun convertCurrencies(
    unitFrom: BasicUnit.Default,
    unitTo: BasicUnit.Default,
    value: BigDecimal,
  ): ConverterResult =
    withContext(Dispatchers.IO) {
      refreshCurrencyRates(unitFrom.id)

      val latestRate = currencyRatesDao.getLatestRate(unitFrom.id, unitTo.id)
      val pairUnitValue = latestRate?.pairUnitValue
      if (pairUnitValue == null) {
        currencyRateUpdateState.update { CurrencyRateUpdateState.Error }
        return@withContext ConverterResult.Error.CurrencyError
      }
      currencyRateUpdateState.update {
        CurrencyRateUpdateState.Ready(LocalDate.ofEpochDay(latestRate.date))
      }

      val conversion = value.multiply(pairUnitValue).setMaxScale()

      return@withContext ConverterResult.Default(value = conversion, calculation = value)
    }

  private suspend fun refreshCurrencyRates(unitFromId: String) =
    withContext(Dispatchers.IO) {
      val latestUpdateDate = currencyRatesDao.getLatestRateTimeStamp(unitFromId)
      val currentDate = LocalDate.now().toEpochDay()

      if (latestUpdateDate != currentDate) {
        // Update cache if needed
        currencyRateUpdateState.update { CurrencyRateUpdateState.Loading }
        try {
          val conversions = currencyApiService.getCurrencyPairs(unitFromId)
          val rates =
            conversions.currency.map { (pairId, pairValue) ->
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

  private suspend fun filterUnitCollections(
    query: String,
    unitGroups: List<UnitGroup>,
    favoritesOnly: Boolean,
    sorting: UnitsListSorting,
  ): Sequence<UnitSearchResultItem> =
    withContext(Dispatchers.IO) {
      val unitsInUnitGroup = inMemory.filter { it.group in unitGroups }
      val unitStats = getUnitStats(unitsInUnitGroup.map { it.id }).toSet()
      val initialUnitSearchResult = mutableListOf<UnitSearchResultItem>()
      for (unit in unitsInUnitGroup) {
        val stats = unitStats.firstOrNull { it.id == unit.id } ?: UnitStats(unit.id)
        val unitSearchResultItem = UnitSearchResultItem(unit, stats, null)
        initialUnitSearchResult.add(unitSearchResultItem)
      }

      var units = initialUnitSearchResult.asSequence()
      if (favoritesOnly) {
        units = units.filter { it.stats.isFavorite }
      }
      units =
        when (sorting) {
          UnitsListSorting.USAGE -> units.sortedByDescending { it.stats.frequency }
          UnitsListSorting.ALPHABETICAL ->
            units.sortedBy { mContext.getString(it.basicUnit.displayName) }
          UnitsListSorting.SCALE_ASC -> units.sortedBy { it.basicUnit.factor }
          UnitsListSorting.SCALE_DESC -> units.sortedByDescending { it.basicUnit.factor }
        }
      units =
        if (query.isEmpty()) {
          units.sortedByDescending { it.stats.isFavorite }
        } else {
          units.filterByLev(query, mContext)
        }
      return@withContext units
    }

  private fun UnitsEntity.toUnitStats(): UnitStats =
    UnitStats(unitId, isFavorite, pairedUnitId, frequency)
}

private val dayBasicUnit by lazy { BigDecimal("86400000000000000000000") }
private val hourBasicUnit by lazy { BigDecimal("3600000000000000000000") }
private val minuteBasicUnit by lazy { BigDecimal("60000000000000000000") }
private val secondBasicUnit by lazy { BigDecimal("1000000000000000000") }
private val millisecondBasicUnit by lazy { BigDecimal("1000000000000000") }
private val microsecondBasicUnit by lazy { BigDecimal("1000000000000") }
private val nanosecondBasicUnit by lazy { BigDecimal("1000000000") }
internal const val BATCH_CURRENCY_CONVERSION_SCALE = 10
