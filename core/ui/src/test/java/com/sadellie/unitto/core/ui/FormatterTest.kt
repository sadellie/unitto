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
private const val LONG_HALF_COMPLETE_EXPR = "50+123456÷89078..9×0.8–12+0-√9*4^9+2×(9+8×7)×sin(13sin123cos"
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
        assertEquals("50+123 456÷89 078..9×0.8–12+0–√9×4^9+2×(9+8×7)×sin(13sin123cos", formatter.format(LONG_HALF_COMPLETE_EXPR))
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
        assertEquals("50+123,456÷89,078..9×0.8–12+0–√9×4^9+2×(9+8×7)×sin(13sin123cos", formatter.format(LONG_HALF_COMPLETE_EXPR))
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
        assertEquals("50+123.456÷89.078,,9×0,8–12+0–√9×4^9+2×(9+8×7)×sin(13sin123cos", formatter.format(LONG_HALF_COMPLETE_EXPR))
        assertEquals("((((((((", formatter.format(SOME_BRACKETS))
    }

    @Test
    fun formatTimeTest() {
        formatter.setSeparator(Separator.SPACES)
        composeTestRule.setContent {
            var basicValue = BigDecimal.valueOf(1)
            assertEquals("-28", formatter.formatTime("-28", basicValue))
            assertEquals("-0.05", formatter.formatTime("-0.05", basicValue))
            assertEquals("0", formatter.formatTime("0", basicValue))
            assertEquals("0", formatter.formatTime("-0", basicValue))

            basicValue = BigDecimal.valueOf(86_400_000_000_000_000_000_000.0)
            assertEquals("-28d", formatter.formatTime("-28", basicValue))
            assertEquals("-1h 12m", formatter.formatTime("-0.05", basicValue))
            assertEquals("0", formatter.formatTime("0", basicValue))
            assertEquals("0", formatter.formatTime("-0", basicValue))

            // DAYS
            basicValue = BigDecimal.valueOf(86_400_000_000_000_000_000_000.0)
            assertEquals("12h", formatter.formatTime("0.5", basicValue))
            assertEquals("1h 12m", formatter.formatTime("0.05", basicValue))
            assertEquals("7m 12s", formatter.formatTime("0.005", basicValue))
            assertEquals("28d", formatter.formatTime("28", basicValue))
            assertEquals("90d", formatter.formatTime("90", basicValue))
            assertEquals("90d 12h", formatter.formatTime("90.5", basicValue))
            assertEquals("90d 7m 12s", formatter.formatTime("90.005", basicValue))

            // HOURS
            basicValue = BigDecimal.valueOf(3_600_000_000_000_000_000_000.0)
            assertEquals("30m", formatter.formatTime("0.5", basicValue))
            assertEquals("3m", formatter.formatTime("0.05", basicValue))
            assertEquals("18s", formatter.formatTime("0.005", basicValue))
            assertEquals("1d 4h", formatter.formatTime("28", basicValue))
            assertEquals("3d 18h", formatter.formatTime("90", basicValue))
            assertEquals("3d 18h 30m", formatter.formatTime("90.5", basicValue))
            assertEquals("3d 18h 18s", formatter.formatTime("90.005", basicValue))

            // MINUTES
            basicValue = BigDecimal.valueOf(60_000_000_000_000_000_000.0)
            assertEquals("30s", formatter.formatTime("0.5", basicValue))
            assertEquals("3s", formatter.formatTime("0.05", basicValue))
            assertEquals("300ms", formatter.formatTime("0.005", basicValue))
            assertEquals("28m", formatter.formatTime("28", basicValue))
            assertEquals("1h 30m", formatter.formatTime("90", basicValue))
            assertEquals("1h 30m 30s", formatter.formatTime("90.5", basicValue))
            assertEquals("1h 30m 300ms", formatter.formatTime("90.005", basicValue))

            // SECONDS
            basicValue = BigDecimal.valueOf(1_000_000_000_000_000_000)
            assertEquals("500ms", formatter.formatTime("0.5", basicValue))
            assertEquals("50ms", formatter.formatTime("0.05", basicValue))
            assertEquals("5ms", formatter.formatTime("0.005", basicValue))
            assertEquals("28s", formatter.formatTime("28", basicValue))
            assertEquals("1m 30s", formatter.formatTime("90", basicValue))
            assertEquals("1m 30s 500ms", formatter.formatTime("90.5", basicValue))
            assertEquals("1m 30s 5ms", formatter.formatTime("90.005", basicValue))

            // MILLISECONDS
            basicValue = BigDecimal.valueOf(1_000_000_000_000_000)
            assertEquals("500µs", formatter.formatTime("0.5", basicValue))
            assertEquals("50µs", formatter.formatTime("0.05", basicValue))
            assertEquals("5µs", formatter.formatTime("0.005", basicValue))
            assertEquals("28ms", formatter.formatTime("28", basicValue))
            assertEquals("90ms", formatter.formatTime("90", basicValue))
            assertEquals("90ms 500µs", formatter.formatTime("90.5", basicValue))
            assertEquals("90ms 5µs", formatter.formatTime("90.005", basicValue))

            // MICROSECONDS
            basicValue = BigDecimal.valueOf(1_000_000_000_000)
            assertEquals("500ns", formatter.formatTime("0.5", basicValue))
            assertEquals("50ns", formatter.formatTime("0.05", basicValue))
            assertEquals("5ns", formatter.formatTime("0.005", basicValue))
            assertEquals("28µs", formatter.formatTime("28", basicValue))
            assertEquals("90µs", formatter.formatTime("90", basicValue))
            assertEquals("90µs 500ns", formatter.formatTime("90.5", basicValue))
            assertEquals("90µs 5ns", formatter.formatTime("90.005", basicValue))

            // NANOSECONDS
            basicValue = BigDecimal.valueOf(1_000_000_000)
            assertEquals("500 000 000as", formatter.formatTime("0.5", basicValue))
            assertEquals("50 000 000as", formatter.formatTime("0.05", basicValue))
            assertEquals("5 000 000as", formatter.formatTime("0.005", basicValue))
            assertEquals("28ns", formatter.formatTime("28", basicValue))
            assertEquals("90ns", formatter.formatTime("90", basicValue))
            assertEquals("90ns 500 000 000as", formatter.formatTime("90.5", basicValue))
            assertEquals("90ns 5 000 000as", formatter.formatTime("90.005", basicValue))

            // ATTOSECONDS
            basicValue = BigDecimal.valueOf(1)
            assertEquals("0.5", formatter.formatTime("0.5", basicValue))
            assertEquals("0.05", formatter.formatTime("0.05", basicValue))
            assertEquals("0.005", formatter.formatTime("0.005", basicValue))
            assertEquals("28", formatter.formatTime("28", basicValue))
            assertEquals("90", formatter.formatTime("90", basicValue))
            assertEquals("90.5", formatter.formatTime("90.5", basicValue))
            assertEquals("90.005", formatter.formatTime("90.005", basicValue))
        }
    }
}