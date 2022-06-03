package com.sadellie.unitto.screens

import org.junit.Assert.assertEquals
import org.junit.Test

class LevenshteinTest {

    @Test
    fun levIdentical() {
        assertEquals(0, "popcorn and bananas".lev("popcorn and bananas"))
    }

    @Test
    fun levOneReplace() {
        assertEquals(1, "popcorn and bananas".lev("popcorn and bananan"))
    }

    @Test
    fun levOneAdd() {
        assertEquals(1, "popcorn and bananas".lev("popcorn and banana"))
    }

    @Test
    fun levOneDelete() {
        assertEquals(1, "popcorn and bananas".lev("popcorn and bananasa"))
    }

    @Test
    fun levCompletelyDifferent() {
        assertEquals(10, "red truck".lev("tinny kitty"))
    }

    @Test
    fun levEmptyA() {
        assertEquals(11, "".lev("tinny kitty"))
    }

    @Test
    fun levEmptyB() {
        assertEquals(9, "red truck".lev(""))
    }

    @Test
    fun levDifferentCases() {
        assertEquals(0, "red truck".lev("red TRUCK"))
    }
}