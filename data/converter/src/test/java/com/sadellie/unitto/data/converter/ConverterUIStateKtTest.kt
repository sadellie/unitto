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

package com.sadellie.unitto.data.converter

import android.content.Context
import com.sadellie.unitto.data.database.CurrencyRatesDao
import com.sadellie.unitto.data.database.CurrencyRatesEntity
import com.sadellie.unitto.data.database.UnitsDao
import com.sadellie.unitto.data.database.UnitsEntity
import com.sadellie.unitto.data.model.converter.UnitGroup
import com.sadellie.unitto.data.model.converter.unit.NormalUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.math.BigDecimal

@RunWith(RobolectricTestRunner::class)
class ConverterUIStateKtTest {

    @Test
    fun convertTime() {
        var basicValue = BigDecimal("1")
        val mContext: Context = RuntimeEnvironment.getApplication().applicationContext
        val repo = UnitsRepositoryImpl(
            unitsDao = object : UnitsDao {
                override fun getAllFlow(): Flow<List<UnitsEntity>> = emptyFlow()
                override suspend fun insertUnit(unit: UnitsEntity) {}
                override suspend fun getById(unitId: String): UnitsEntity? = null
                override suspend fun clear() {}
            },
            currencyRatesDao = object : CurrencyRatesDao {
                override suspend fun insertRates(currencyRates: List<CurrencyRatesEntity>) {}
                override suspend fun getLatestRateTimeStamp(baseId: String): Long? = null
                override suspend fun getLatestRates(baseId: String): List<CurrencyRatesEntity> = emptyList()
                override suspend fun getLatestRate(baseId: String, pairId: String): CurrencyRatesEntity? = null
                override fun size(): Flow<Int> = emptyFlow()
                override suspend fun clear() {}
            },
            mContext = mContext,
        )

        fun String.formatTime(): ConverterResult.Time {
            val unitFrom = NormalUnit(
                id = "",
                factor = basicValue,
                group = UnitGroup.TIME,
                displayName = 0,
                shortName = 0,
                backward = false,
            )

            return repo.convertTime(unitFrom, BigDecimal(this))
        }

        // Edgy things (minus, decimals and zeros)
        assertEquals(
            ConverterResult.Time(
                negative = true,
                attosecond = BigDecimal("28"),
            ),
            "-28".formatTime(),
        )

        assertEquals(
            ConverterResult.Time(
                negative = true,
                attosecond = BigDecimal("0.05"),
            ),
            "-0.05".formatTime(),
        )

        assertEquals(
            ConverterResult.Time(),
            "0".formatTime(),
        )

        basicValue = BigDecimal("86400000000000000000000")
        assertEquals(
            ConverterResult.Time(
                negative = true,
                day = BigDecimal("28"),
            ),
            "-28".formatTime(),
        )
        assertEquals(
            ConverterResult.Time(
                negative = true,
                hour = BigDecimal("1"),
                minute = BigDecimal("12"),
            ),
            "-0.05".formatTime(),
        )
        assertEquals(
            ConverterResult.Time(),
            "0".formatTime(),
        )
        assertEquals(
            ConverterResult.Time(),
            "-0".formatTime(),
        )

        // DAYS
        basicValue = BigDecimal("86400000000000000000000")
        assertEquals(
            ConverterResult.Time(
                hour = BigDecimal("12"),
            ),
            "0.5".formatTime(),
        )
        assertEquals(
            ConverterResult.Time(
                day = BigDecimal("90"),
                minute = BigDecimal("7"),
                second = BigDecimal("12"),
            ),
            "90.005".formatTime(),
        )

        // HOURS
        basicValue = BigDecimal("3600000000000000000000")
        assertEquals(
            ConverterResult.Time(
                minute = BigDecimal("30"),
            ),
            "0.5".formatTime(),
        )
        assertEquals(
            ConverterResult.Time(
                day = BigDecimal("3"),
                hour = BigDecimal("18"),
                second = BigDecimal("18"),
            ),
            "90.005".formatTime(),
        )

        // MINUTES
        basicValue = BigDecimal("60000000000000000000")
        assertEquals(
            ConverterResult.Time(
                second = BigDecimal("30"),
            ),
            "0.5".formatTime(),
        )
        assertEquals(
            ConverterResult.Time(
                hour = BigDecimal("1"),
                minute = BigDecimal("30"),
                millisecond = BigDecimal("300"),
            ),
            "90.005".formatTime(),
        )

        // SECONDS
        basicValue = BigDecimal("1000000000000000000")
        assertEquals(
            ConverterResult.Time(
                millisecond = BigDecimal("500"),
            ),
            "0.5".formatTime(),
        )
        assertEquals(
            ConverterResult.Time(
                minute = BigDecimal("1"),
                second = BigDecimal("30"),
                millisecond = BigDecimal("5"),
            ),
            "90.005".formatTime(),
        )

        // MILLISECONDS
        basicValue = BigDecimal("1000000000000000")
        assertEquals(
            ConverterResult.Time(
                microsecond = BigDecimal("500"),
            ),
            "0.5".formatTime(),
        )
        assertEquals(
            ConverterResult.Time(
                millisecond = BigDecimal("90"),
                microsecond = BigDecimal("5"),
            ),
            "90.005".formatTime(),
        )

        // MICROSECONDS
        basicValue = BigDecimal("1000000000000")
        assertEquals(
            ConverterResult.Time(
                nanosecond = BigDecimal("500"),
            ),
            "0.5".formatTime(),
        )
        assertEquals(
            ConverterResult.Time(
                microsecond = BigDecimal("90"),
                nanosecond = BigDecimal("5"),
            ),
            "90.005".formatTime(),
        )

        // NANOSECONDS
        basicValue = BigDecimal("1000000000")
        assertEquals(
            ConverterResult.Time(
                nanosecond = BigDecimal("90"),
                attosecond = BigDecimal("5000000"),
            ),
            "90.005".formatTime(),
        )

        // ATTOSECONDS
        basicValue = BigDecimal("1")
        assertEquals(
            ConverterResult.Time(
                attosecond = BigDecimal("0.5"),
            ),
            "0.5".formatTime(),
        )
        assertEquals(
            ConverterResult.Time(
                attosecond = BigDecimal("90.005"),
            ),
            "90.005".formatTime(),
        )
    }
}
