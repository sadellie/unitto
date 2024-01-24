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

package com.sadellie.unitto.feature.converter

import android.content.Context
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.math.BigDecimal

@RunWith(RobolectricTestRunner::class)
class ConverterUIStateKtTest {

    @Test
    fun format() {
        val formatterSymbols = FormatterSymbols.Spaces
        var basicValue = BigDecimal.valueOf(1)
        val mContext: Context = RuntimeEnvironment.getApplication().applicationContext

        fun String.formatTime() = formatTime(basicValue.multiply(BigDecimal(this)))
            .format(mContext, formatterSymbols)

        // Edgy things (minus, decimals and zeros)
        Assert.assertEquals("−28as", "-28".formatTime())
        Assert.assertEquals("−0.05as", "-0.05".formatTime())
        Assert.assertEquals("0", "0".formatTime())

        basicValue = BigDecimal.valueOf(86_400_000_000_000_000_000_000.0)
        Assert.assertEquals("−28d", "-28".formatTime())
        Assert.assertEquals("−1h 12m", "-0.05".formatTime())
        Assert.assertEquals("0", "0".formatTime())
        Assert.assertEquals("0", "-0".formatTime())

        // DAYS
        basicValue = BigDecimal.valueOf(86_400_000_000_000_000_000_000.0)
        Assert.assertEquals("12h", "0.5".formatTime())
        Assert.assertEquals("1h 12m", "0.05".formatTime())
        Assert.assertEquals("7m 12s", "0.005".formatTime())
        Assert.assertEquals("28d", "28".formatTime())
        Assert.assertEquals("90d", "90".formatTime())
        Assert.assertEquals("90d 12h", "90.5".formatTime())
        Assert.assertEquals("90d 7m 12s", "90.005".formatTime())

        // HOURS
        basicValue = BigDecimal.valueOf(3_600_000_000_000_000_000_000.0)
        Assert.assertEquals("30m", "0.5".formatTime())
        Assert.assertEquals("3m", "0.05".formatTime())
        Assert.assertEquals("18s", "0.005".formatTime())
        Assert.assertEquals("1d 4h", "28".formatTime())
        Assert.assertEquals("3d 18h", "90".formatTime())
        Assert.assertEquals("3d 18h 30m", "90.5".formatTime())
        Assert.assertEquals("3d 18h 18s", "90.005".formatTime())

        // MINUTES
        basicValue = BigDecimal.valueOf(60_000_000_000_000_000_000.0)
        Assert.assertEquals("30s", "0.5".formatTime())
        Assert.assertEquals("3s", "0.05".formatTime())
        Assert.assertEquals("300ms", "0.005".formatTime())
        Assert.assertEquals("28m", "28".formatTime())
        Assert.assertEquals("1h 30m", "90".formatTime())
        Assert.assertEquals("1h 30m 30s", "90.5".formatTime())
        Assert.assertEquals("1h 30m 300ms", "90.005".formatTime())

        // SECONDS
        basicValue = BigDecimal.valueOf(1_000_000_000_000_000_000)
        Assert.assertEquals("500ms", "0.5".formatTime())
        Assert.assertEquals("50ms", "0.05".formatTime())
        Assert.assertEquals("5ms", "0.005".formatTime())
        Assert.assertEquals("28s", "28".formatTime())
        Assert.assertEquals("1m 30s", "90".formatTime())
        Assert.assertEquals("1m 30s 500ms", "90.5".formatTime())
        Assert.assertEquals("1m 30s 5ms", "90.005".formatTime())

        // MILLISECONDS
        basicValue = BigDecimal.valueOf(1_000_000_000_000_000)
        Assert.assertEquals("500µs", "0.5".formatTime())
        Assert.assertEquals("50µs", "0.05".formatTime())
        Assert.assertEquals("5µs", "0.005".formatTime())
        Assert.assertEquals("28ms", "28".formatTime())
        Assert.assertEquals("90ms", "90".formatTime())
        Assert.assertEquals("90ms 500µs", "90.5".formatTime())
        Assert.assertEquals("90ms 5µs", "90.005".formatTime())

        // MICROSECONDS
        basicValue = BigDecimal.valueOf(1_000_000_000_000)
        Assert.assertEquals("500ns", "0.5".formatTime())
        Assert.assertEquals("50ns", "0.05".formatTime())
        Assert.assertEquals("5ns", "0.005".formatTime())
        Assert.assertEquals("28µs", "28".formatTime())
        Assert.assertEquals("90µs", "90".formatTime())
        Assert.assertEquals("90µs 500ns", "90.5".formatTime())
        Assert.assertEquals("90µs 5ns", "90.005".formatTime())

        // NANOSECONDS
        basicValue = BigDecimal.valueOf(1_000_000_000)
        Assert.assertEquals("500 000 000as", "0.5".formatTime())
        Assert.assertEquals("50 000 000as", "0.05".formatTime())
        Assert.assertEquals("5 000 000as", "0.005".formatTime())
        Assert.assertEquals("28ns", "28".formatTime())
        Assert.assertEquals("90ns", "90".formatTime())
        Assert.assertEquals("90ns 500 000 000as", "90.5".formatTime())
        Assert.assertEquals("90ns 5 000 000as", "90.005".formatTime())

        // ATTOSECONDS
        basicValue = BigDecimal.valueOf(1)
        Assert.assertEquals("0.5as", "0.5".formatTime())
        Assert.assertEquals("0.05as", "0.05".formatTime())
        Assert.assertEquals("0.005as", "0.005".formatTime())
        Assert.assertEquals("28as", "28".formatTime())
        Assert.assertEquals("90as", "90".formatTime())
        Assert.assertEquals("90.5as", "90.5".formatTime())
        Assert.assertEquals("90.005as", "90.005".formatTime())
    }
}
