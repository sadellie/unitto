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

import androidx.compose.ui.test.junit4.createComposeRule
import com.sadellie.unitto.core.base.Separator
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.math.BigDecimal

private val formatter = Formatter

private const val ENG_VALUE = "123E+21"
private const val ENG_VALUE_FRACTIONAL = "123.3E+21"
private const val COMPLETE_VALUE = "123456.789"
private const val INCOMPLETE_VALUE = "123456."
private const val NO_FRACTIONAL_VALUE = "123456"
private const val INCOMPLETE_EXPR = "50+123456÷8×0.8–12+"
private const val COMPLETE_EXPR = "50+123456÷8×0.8–12+0-√9*4^9+2×(9+8×7)"
private const val SOME_BRACKETS = "(((((((("

@RunWith(RobolectricTestRunner::class)
class FormatterTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun setSeparatorSpaces() {
        formatter.setSeparator(Separator.SPACES)
        assertEquals(".", formatter.fractional)
        assertEquals("123E+21", formatter.format(ENG_VALUE))
        assertEquals("123.3E+21", formatter.format(ENG_VALUE_FRACTIONAL))
        assertEquals("123 456.789", formatter.format(COMPLETE_VALUE))
        assertEquals("123 456.", formatter.format(INCOMPLETE_VALUE))
        assertEquals("123 456", formatter.format(NO_FRACTIONAL_VALUE))
        assertEquals("50+123 456÷8×0.8–12+", formatter.format(INCOMPLETE_EXPR))
        assertEquals("50+123 456÷8×0.8–12+0–√9×4^9+2×(9+8×7)", formatter.format(COMPLETE_EXPR))
        assertEquals("((((((((", formatter.format(SOME_BRACKETS))
    }

    @Test
    fun setSeparatorComma() {
        formatter.setSeparator(Separator.COMMA)
        assertEquals(".", formatter.fractional)
        assertEquals("123E+21", formatter.format(ENG_VALUE))
        assertEquals("123.3E+21", formatter.format(ENG_VALUE_FRACTIONAL))
        assertEquals("123,456.789", formatter.format(COMPLETE_VALUE))
        assertEquals("123,456.", formatter.format(INCOMPLETE_VALUE))
        assertEquals("123,456", formatter.format(NO_FRACTIONAL_VALUE))
        assertEquals("50+123,456÷8×0.8–12+", formatter.format(INCOMPLETE_EXPR))
        assertEquals("50+123,456÷8×0.8–12+0–√9×4^9+2×(9+8×7)", formatter.format(COMPLETE_EXPR))
        assertEquals("((((((((", formatter.format(SOME_BRACKETS))
    }

    @Test
    fun setSeparatorPeriod() {
        formatter.setSeparator(Separator.PERIOD)
        assertEquals(",", formatter.fractional)
        assertEquals("123E+21", formatter.format(ENG_VALUE))
        assertEquals("123,3E+21", formatter.format(ENG_VALUE_FRACTIONAL))
        assertEquals("123.456,789", formatter.format(COMPLETE_VALUE))
        assertEquals("123.456,", formatter.format(INCOMPLETE_VALUE))
        assertEquals("123.456", formatter.format(NO_FRACTIONAL_VALUE))
        assertEquals("50+123.456÷8×0,8–12+", formatter.format(INCOMPLETE_EXPR))
        assertEquals("50+123.456÷8×0,8–12+0–√9×4^9+2×(9+8×7)", formatter.format(COMPLETE_EXPR))
        assertEquals("((((((((", formatter.format(SOME_BRACKETS))
    }

    @Test
    fun formatTimeTest() {
        formatter.setSeparator(Separator.SPACES)
        composeTestRule.setContent {
            var basicValue = BigDecimal.valueOf(1)
            assertEquals("-28", Formatter.formatTime("-28", basicValue))
            assertEquals("-0.05", Formatter.formatTime("-0.05", basicValue))
            assertEquals("0", Formatter.formatTime("0", basicValue))
            assertEquals("0", Formatter.formatTime("-0", basicValue))

            basicValue = BigDecimal.valueOf(86_400_000_000_000_000_000_000.0)
            assertEquals("-28d", Formatter.formatTime("-28", basicValue))
            assertEquals("-1h 12m", Formatter.formatTime("-0.05", basicValue))
            assertEquals("0", Formatter.formatTime("0", basicValue))
            assertEquals("0", Formatter.formatTime("-0", basicValue))

            // DAYS
            basicValue = BigDecimal.valueOf(86_400_000_000_000_000_000_000.0)
            assertEquals("12h", Formatter.formatTime("0.5", basicValue))
            assertEquals("1h 12m", Formatter.formatTime("0.05", basicValue))
            assertEquals("7m 12s", Formatter.formatTime("0.005", basicValue))
            assertEquals("28d", Formatter.formatTime("28", basicValue))
            assertEquals("90d", Formatter.formatTime("90", basicValue))
            assertEquals("90d 12h", Formatter.formatTime("90.5", basicValue))
            assertEquals("90d 7m 12s", Formatter.formatTime("90.005", basicValue))

            // HOURS
            basicValue = BigDecimal.valueOf(3_600_000_000_000_000_000_000.0)
            assertEquals("30m", Formatter.formatTime("0.5", basicValue))
            assertEquals("3m", Formatter.formatTime("0.05", basicValue))
            assertEquals("18s", Formatter.formatTime("0.005", basicValue))
            assertEquals("1d 4h", Formatter.formatTime("28", basicValue))
            assertEquals("3d 18h", Formatter.formatTime("90", basicValue))
            assertEquals("3d 18h 30m", Formatter.formatTime("90.5", basicValue))
            assertEquals("3d 18h 18s", Formatter.formatTime("90.005", basicValue))

            // MINUTES
            basicValue = BigDecimal.valueOf(60_000_000_000_000_000_000.0)
            assertEquals("30s", Formatter.formatTime("0.5", basicValue))
            assertEquals("3s", Formatter.formatTime("0.05", basicValue))
            assertEquals("300ms", Formatter.formatTime("0.005", basicValue))
            assertEquals("28m", Formatter.formatTime("28", basicValue))
            assertEquals("1h 30m", Formatter.formatTime("90", basicValue))
            assertEquals("1h 30m 30s", Formatter.formatTime("90.5", basicValue))
            assertEquals("1h 30m 300ms", Formatter.formatTime("90.005", basicValue))

            // SECONDS
            basicValue = BigDecimal.valueOf(1_000_000_000_000_000_000)
            assertEquals("500ms", Formatter.formatTime("0.5", basicValue))
            assertEquals("50ms", Formatter.formatTime("0.05", basicValue))
            assertEquals("5ms", Formatter.formatTime("0.005", basicValue))
            assertEquals("28s", Formatter.formatTime("28", basicValue))
            assertEquals("1m 30s", Formatter.formatTime("90", basicValue))
            assertEquals("1m 30s 500ms", Formatter.formatTime("90.5", basicValue))
            assertEquals("1m 30s 5ms", Formatter.formatTime("90.005", basicValue))

            // MILLISECONDS
            basicValue = BigDecimal.valueOf(1_000_000_000_000_000)
            assertEquals("500µs", Formatter.formatTime("0.5", basicValue))
            assertEquals("50µs", Formatter.formatTime("0.05", basicValue))
            assertEquals("5µs", Formatter.formatTime("0.005", basicValue))
            assertEquals("28ms", Formatter.formatTime("28", basicValue))
            assertEquals("90ms", Formatter.formatTime("90", basicValue))
            assertEquals("90ms 500µs", Formatter.formatTime("90.5", basicValue))
            assertEquals("90ms 5µs", Formatter.formatTime("90.005", basicValue))

            // MICROSECONDS
            basicValue = BigDecimal.valueOf(1_000_000_000_000)
            assertEquals("500ns", Formatter.formatTime("0.5", basicValue))
            assertEquals("50ns", Formatter.formatTime("0.05", basicValue))
            assertEquals("5ns", Formatter.formatTime("0.005", basicValue))
            assertEquals("28µs", Formatter.formatTime("28", basicValue))
            assertEquals("90µs", Formatter.formatTime("90", basicValue))
            assertEquals("90µs 500ns", Formatter.formatTime("90.5", basicValue))
            assertEquals("90µs 5ns", Formatter.formatTime("90.005", basicValue))

            // NANOSECONDS
            basicValue = BigDecimal.valueOf(1_000_000_000)
            assertEquals("500 000 000as", Formatter.formatTime("0.5", basicValue))
            assertEquals("50 000 000as", Formatter.formatTime("0.05", basicValue))
            assertEquals("5 000 000as", Formatter.formatTime("0.005", basicValue))
            assertEquals("28ns", Formatter.formatTime("28", basicValue))
            assertEquals("90ns", Formatter.formatTime("90", basicValue))
            assertEquals("90ns 500 000 000as", Formatter.formatTime("90.5", basicValue))
            assertEquals("90ns 5 000 000as", Formatter.formatTime("90.005", basicValue))

            // ATTOSECONDS
            basicValue = BigDecimal.valueOf(1)
            assertEquals("0.5", Formatter.formatTime("0.5", basicValue))
            assertEquals("0.05", Formatter.formatTime("0.05", basicValue))
            assertEquals("0.005", Formatter.formatTime("0.005", basicValue))
            assertEquals("28", Formatter.formatTime("28", basicValue))
            assertEquals("90", Formatter.formatTime("90", basicValue))
            assertEquals("90.5", Formatter.formatTime("90.5", basicValue))
            assertEquals("90.005", Formatter.formatTime("90.005", basicValue))
        }
    }
}