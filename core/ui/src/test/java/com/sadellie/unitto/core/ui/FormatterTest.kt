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

package com.sadellie.unitto.core.ui

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.formatExpression
import com.sadellie.unitto.core.ui.common.textfield.formatTime
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.math.BigDecimal

private const val ENG_VALUE = "123E+21"
private const val ENG_VALUE_FRACTIONAL = "123.3E+21"
private const val COMPLETE_VALUE = "123456.789"
private const val INCOMPLETE_VALUE = "123456."
private const val NO_FRACTIONAL_VALUE = "123456"
private const val INCOMPLETE_EXPR = "50+123456÷8×0.8-12+"
private const val COMPLETE_EXPR = "50+123456÷8×0.8-12+0-√9×4^9+2×(9+8×7)"
private const val LONG_HALF_COMPLETE_EXPR = "50+123456÷89078..9×0.8-12+0-√9×4^9+2×(9+8×7)×sin(13sin123cos"
private const val SOME_BRACKETS = "(((((((("

@RunWith(RobolectricTestRunner::class)
class FormatterTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun setSeparatorSpaces() {
        fun String.format(): String = formatExpression(FormatterSymbols.Spaces)
        assertEquals("123E+21", ENG_VALUE.format())
        assertEquals("123.3E+21", ENG_VALUE_FRACTIONAL.format())
        assertEquals("123 456.789", COMPLETE_VALUE.format())
        assertEquals("123 456.", INCOMPLETE_VALUE.format())
        assertEquals("123 456", NO_FRACTIONAL_VALUE.format())
        assertEquals("50+123 456÷8×0.8−12+", INCOMPLETE_EXPR.format())
        assertEquals("50+123 456÷8×0.8−12+0−√9×4^9+2×(9+8×7)", COMPLETE_EXPR.format())
        assertEquals("50+123 456÷89 078..9×0.8−12+0−√9×4^9+2×(9+8×7)×sin(13sin123cos", LONG_HALF_COMPLETE_EXPR.format())
        assertEquals("((((((((", SOME_BRACKETS.format())
    }

    @Test
    fun setSeparatorComma() {
        fun String.format(): String = formatExpression(FormatterSymbols.Comma)
        assertEquals("123E+21", ENG_VALUE.format())
        assertEquals("123.3E+21", ENG_VALUE_FRACTIONAL.format())
        assertEquals("123,456.789", COMPLETE_VALUE.format())
        assertEquals("123,456.", INCOMPLETE_VALUE.format())
        assertEquals("123,456", NO_FRACTIONAL_VALUE.format())
        assertEquals("50+123,456÷8×0.8−12+", INCOMPLETE_EXPR.format())
        assertEquals("50+123,456÷8×0.8−12+0−√9×4^9+2×(9+8×7)", COMPLETE_EXPR.format())
        assertEquals("50+123,456÷89,078..9×0.8−12+0−√9×4^9+2×(9+8×7)×sin(13sin123cos", LONG_HALF_COMPLETE_EXPR.format())
        assertEquals("((((((((", SOME_BRACKETS.format())
    }

    @Test
    fun setSeparatorPeriod() {
        fun String.format(): String = formatExpression(FormatterSymbols.Period)
        assertEquals("123E+21", ENG_VALUE.format())
        assertEquals("123,3E+21", ENG_VALUE_FRACTIONAL.format())
        assertEquals("123.456,789", COMPLETE_VALUE.format())
        assertEquals("123.456,", INCOMPLETE_VALUE.format())
        assertEquals("123.456", NO_FRACTIONAL_VALUE.format())
        assertEquals("50+123.456÷8×0,8−12+", INCOMPLETE_EXPR.format())
        assertEquals("50+123.456÷8×0,8−12+0−√9×4^9+2×(9+8×7)", COMPLETE_EXPR.format())
        assertEquals("50+123.456÷89.078,,9×0,8−12+0−√9×4^9+2×(9+8×7)×sin(13sin123cos", LONG_HALF_COMPLETE_EXPR.format())
        assertEquals("((((((((", SOME_BRACKETS.format())
    }

    @Test
    fun formatTimeTest() {
        val formatterSymbols = FormatterSymbols.Spaces
        var basicValue = BigDecimal.valueOf(1)
        val mContext: Context = RuntimeEnvironment.getApplication().applicationContext

        fun String.formatTime() = this.formatTime(mContext, basicValue, formatterSymbols)

        assertEquals("−28", "-28".formatTime())
        assertEquals("−0.05", "-0.05".formatTime())
        assertEquals("0", "0".formatTime())
        assertEquals("0", "−0".formatTime())

        basicValue = BigDecimal.valueOf(86_400_000_000_000_000_000_000.0)
        assertEquals("−28d", "-28".formatTime())
        assertEquals("−1h 12m", "-0.05".formatTime())
        assertEquals("0", "0".formatTime())
        assertEquals("0", "-0".formatTime())

        // DAYS
        basicValue = BigDecimal.valueOf(86_400_000_000_000_000_000_000.0)
        assertEquals("12h","0.5".formatTime())
        assertEquals("1h 12m","0.05".formatTime())
        assertEquals("7m 12s","0.005".formatTime())
        assertEquals("28d","28".formatTime())
        assertEquals("90d","90".formatTime())
        assertEquals("90d 12h","90.5".formatTime())
        assertEquals("90d 7m 12s","90.005".formatTime())

        // HOURS
        basicValue = BigDecimal.valueOf(3_600_000_000_000_000_000_000.0)
        assertEquals("30m", "0.5".formatTime())
        assertEquals("3m", "0.05".formatTime())
        assertEquals("18s", "0.005".formatTime())
        assertEquals("1d 4h", "28".formatTime())
        assertEquals("3d 18h", "90".formatTime())
        assertEquals("3d 18h 30m", "90.5".formatTime())
        assertEquals("3d 18h 18s", "90.005".formatTime())

        // MINUTES
        basicValue = BigDecimal.valueOf(60_000_000_000_000_000_000.0)
        assertEquals("30s", "0.5".formatTime())
        assertEquals("3s", "0.05".formatTime())
        assertEquals("300ms", "0.005".formatTime())
        assertEquals("28m", "28".formatTime())
        assertEquals("1h 30m", "90".formatTime())
        assertEquals("1h 30m 30s", "90.5".formatTime())
        assertEquals("1h 30m 300ms", "90.005".formatTime())

        // SECONDS
        basicValue = BigDecimal.valueOf(1_000_000_000_000_000_000)
        assertEquals("500ms", "0.5".formatTime())
        assertEquals("50ms", "0.05".formatTime())
        assertEquals("5ms", "0.005".formatTime())
        assertEquals("28s", "28".formatTime())
        assertEquals("1m 30s", "90".formatTime())
        assertEquals("1m 30s 500ms", "90.5".formatTime())
        assertEquals("1m 30s 5ms", "90.005".formatTime())

        // MILLISECONDS
        basicValue = BigDecimal.valueOf(1_000_000_000_000_000)
        assertEquals("500µs", "0.5".formatTime())
        assertEquals("50µs", "0.05".formatTime())
        assertEquals("5µs", "0.005".formatTime())
        assertEquals("28ms", "28".formatTime())
        assertEquals("90ms", "90".formatTime())
        assertEquals("90ms 500µs", "90.5".formatTime())
        assertEquals("90ms 5µs", "90.005".formatTime())

        // MICROSECONDS
        basicValue = BigDecimal.valueOf(1_000_000_000_000)
        assertEquals("500ns", "0.5".formatTime())
        assertEquals("50ns", "0.05".formatTime())
        assertEquals("5ns", "0.005".formatTime())
        assertEquals("28µs", "28".formatTime())
        assertEquals("90µs", "90".formatTime())
        assertEquals("90µs 500ns", "90.5".formatTime())
        assertEquals("90µs 5ns", "90.005".formatTime())

        // NANOSECONDS
        basicValue = BigDecimal.valueOf(1_000_000_000)
        assertEquals("500 000 000as", "0.5".formatTime())
        assertEquals("50 000 000as", "0.05".formatTime())
        assertEquals("5 000 000as", "0.005".formatTime())
        assertEquals("28ns", "28".formatTime())
        assertEquals("90ns", "90".formatTime())
        assertEquals("90ns 500 000 000as", "90.5".formatTime())
        assertEquals("90ns 5 000 000as", "90.005".formatTime())

        // ATTOSECONDS
        basicValue = BigDecimal.valueOf(1)
        assertEquals("0.5", "0.5".formatTime())
        assertEquals("0.05", "0.05".formatTime())
        assertEquals("0.005", "0.005".formatTime())
        assertEquals("28", "28".formatTime())
        assertEquals("90", "90".formatTime())
        assertEquals("90.5", "90.5".formatTime())
        assertEquals("90.005", "90.005".formatTime())
    }
}