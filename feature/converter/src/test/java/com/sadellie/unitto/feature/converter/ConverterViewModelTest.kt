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

package com.sadellie.unitto.feature.converter

import androidx.room.Room
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.data.database.UnitsRepository
import com.sadellie.unitto.data.database.UnittoDatabase
import com.sadellie.unitto.data.units.AllUnitsRepository
import com.sadellie.unitto.data.userprefs.DataStoreModule
import com.sadellie.unitto.data.userprefs.UserPreferencesRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class ConverterViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: ConverterViewModel
    private val allUnitsRepository = AllUnitsRepository()
    private val database = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.getApplication(),
        UnittoDatabase::class.java
    ).build()

    @Before
    fun setUp() {
        viewModel = ConverterViewModel(
            userPrefsRepository = UserPreferencesRepository(
                DataStoreModule()
                    .provideUserPreferencesDataStore(
                        RuntimeEnvironment.getApplication()
                    )
            ),
            unitRepository = UnitsRepository(
                database.unitsDao()
            ),
            allUnitsRepository = allUnitsRepository
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `test 0`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiStateFlow.collect()
        }

        inputOutputTest("0", "0")
        inputOutputTest("123000", "123000")
        inputOutputTest("123.000", "123.000")
        inputOutputTest("-000", "-0")
        inputOutputTest("12+000", "12+0")
        inputOutputTest("√000", "√0")
        inputOutputTest("(000", "(0")
        inputOutputTest("(1+12)000", "(1+12)*0")
        inputOutputTest("(1.002+120)000", "(1.002+120)*0")

        collectJob.cancel()
    }

    @Test
    fun `test digits from 1 to 9`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiStateFlow.collect()
        }
        inputOutputTest("123456789", "123456789")
        inputOutputTest("(1+1)111", "(1+1)*111")
        collectJob.cancel()
    }

    @Test
    fun `test plus, divide, multiply and exponent operators`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiStateFlow.collect()
        }
        inputOutputTest("0+++", "0")
        inputOutputTest("123+++", "123+")
        inputOutputTest("1-***", "1*")
        inputOutputTest("1/-+++", "1+")
        inputOutputTest("0^^^", "0")
        inputOutputTest("12^^^", "12^")
        inputOutputTest("(^^^", "(")
        inputOutputTest("(8+9)^^^", "(8+9)^")
        collectJob.cancel()
    }

    @Test
    fun `test dot`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiStateFlow.collect()
        }
        inputOutputTest("0...", "0.")
        inputOutputTest("1...", "1.")
        inputOutputTest("1+...", "1+.")
        inputOutputTest("√...", "√.")
        inputOutputTest("√21...", "√21.")
        inputOutputTest("√21+1.01-.23...", "√21+1.01-.23")
        collectJob.cancel()
    }

    @Test
    fun `test minus`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiStateFlow.collect()
        }
        inputOutputTest("0---", "-")
        inputOutputTest("12---", "12-")
        inputOutputTest("12+---", "12-")
        inputOutputTest("12/---", "12/-")
        inputOutputTest("√---", "√-")
        inputOutputTest("√///", "√")
        inputOutputTest("12^----", "12^-")
        collectJob.cancel()
    }

    @Test
    fun `test brackets`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiStateFlow.collect()
        }
        inputOutputTest("0)))", "0")
        inputOutputTest("0(((", "(((")
        inputOutputTest("√(10+2)(", "√(10+2)*(")
        inputOutputTest("√(10+2./(", "√(10+2./(")
        inputOutputTest("0()()))((", "((((")
        inputOutputTest("√(10+2)^(", "√(10+2)^(")
        collectJob.cancel()
    }

    @Test
    fun `test square root`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiStateFlow.collect()
        }
        inputOutputTest("0√√√", "√√√")
        inputOutputTest("123√√√", "123*√√√")
        collectJob.cancel()
    }

    @Test
    fun deleteSymbolTest() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiStateFlow.collect()
        }

        listOf(
            Token._1, Token._2, Token._3, Token._4, Token._5,
            Token._6, Token._7, Token._8, Token._9, Token._0,
            Token.dot, Token.comma, Token.leftBracket, Token.rightBracket,
            Token.plus, Token.minus, Token.divide, Token.multiply,
            Token.exponent, Token.sqrt
        ).forEach {
            // We enter one symbol and delete it, should be default as a result
            viewModel.processInput(it)
            viewModel.deleteDigit()
            assertEquals("0", viewModel.uiStateFlow.value.inputValue)
        }
        viewModel.clearInput()

        // This should not delete default input (0)
        viewModel.deleteDigit()

        // Now we check that we can delete multiple values
        viewModel.processInput(Token._3)
        viewModel.processInput(Token.sqrt)
        viewModel.processInput(Token._9)
        viewModel.deleteDigit()
        assertEquals("3*√", viewModel.uiStateFlow.value.inputValue)

        collectJob.cancel()
    }

    @Test
    fun clearInputTest() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiStateFlow.collect()
        }

        viewModel.processInput(Token._3)
        viewModel.clearInput()
        assertEquals(null, viewModel.uiStateFlow.value.calculatedValue)

        viewModel.processInput(Token._3)
        viewModel.processInput(Token.multiply)
        viewModel.clearInput()
        assertEquals(null, viewModel.uiStateFlow.value.calculatedValue)

        collectJob.cancel()
    }

    @Test
    fun swapUnitsTest() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiStateFlow.collect()
        }
        val initialFrom = viewModel.uiStateFlow.value.unitFrom?.unitId
        val initialTo = viewModel.uiStateFlow.value.unitTo?.unitId

        viewModel.swapUnits()
        assertEquals(initialTo, viewModel.uiStateFlow.value.unitFrom?.unitId)
        assertEquals(initialFrom, viewModel.uiStateFlow.value.unitTo?.unitId)

        collectJob.cancel()
    }

    /**
     * Takes [input] sequence as a single string (e.g. "123-23") and compares it with [output].
     */
    private fun inputOutputTest(input: String, output: String) {
        // Enter everything
        input.forEach {
            viewModel.processInput(it.toString())
        }
        assertEquals(output, viewModel.uiStateFlow.value.inputValue)
        viewModel.clearInput()
    }
}
