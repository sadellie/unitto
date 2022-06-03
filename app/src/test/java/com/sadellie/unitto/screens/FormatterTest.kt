package com.sadellie.unitto.screens

import com.sadellie.unitto.data.preferences.Separator
import org.junit.Assert.assertEquals
import org.junit.Test

private val formatter = Formatter

private const val ENG_VALUE = "123.3E+21"
private const val COMPLETE_VALUE = "123456.789"
private const val INCOMPLETE_VALUE = "123456."

class FormatterTest {

    @Test
    fun setSeparatorSpaces() {
        formatter.setSeparator(Separator.SPACES)
        assertEquals(".", formatter.fractional)
    }

    @Test
    fun setSeparatorComma() {
        formatter.setSeparator(Separator.COMMA)
        assertEquals(".", formatter.fractional)
    }

    @Test
    fun setSeparatorPeriod() {
        formatter.setSeparator(Separator.PERIOD)
        assertEquals(",", formatter.fractional)
    }

    // ENGINEERING
    @Test
    fun formatEngineeringWithSpaces() {
        formatter.setSeparator(Separator.SPACES)
        assertEquals("123.3E+21", formatter.format(ENG_VALUE))
    }

    @Test
    fun formatEngineeringWithComma() {
        formatter.setSeparator(Separator.COMMA)
        assertEquals("123.3E+21", formatter.format(ENG_VALUE))
    }

    @Test
    fun formatEngineeringWithPeriod() {
        formatter.setSeparator(Separator.PERIOD)
        assertEquals("123,3E+21", formatter.format(ENG_VALUE))
    }

    // COMPLETE
    @Test
    fun formatCompleteWithSpaces() {
        formatter.setSeparator(Separator.SPACES)
        assertEquals("123 456.789", formatter.format(COMPLETE_VALUE))
    }

    @Test
    fun formatCompleteWithComma() {
        formatter.setSeparator(Separator.COMMA)
        assertEquals("123,456.789", formatter.format(COMPLETE_VALUE))
    }

    @Test
    fun formatCompleteWithPeriod() {
        formatter.setSeparator(Separator.PERIOD)
        assertEquals("123.456,789", formatter.format(COMPLETE_VALUE))
    }

    // INCOMPLETE
    @Test
    fun formatIncompleteWithSpaces() {
        formatter.setSeparator(Separator.SPACES)
        assertEquals("123 456.", formatter.format(INCOMPLETE_VALUE))
    }

    @Test
    fun formatIncompleteWithComma() {
        formatter.setSeparator(Separator.COMMA)
        assertEquals("123,456.", formatter.format(INCOMPLETE_VALUE))
    }

    @Test
    fun formatIncompleteWithPeriod() {
        formatter.setSeparator(Separator.PERIOD)
        assertEquals("123.456,", formatter.format(INCOMPLETE_VALUE))
    }

}