package com.sadellie.unitto.screen


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sadellie.unitto.data.preferences.UserPreferencesRepository
import com.sadellie.unitto.data.units.ALL_UNITS
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.database.MyBasedUnitDatabase
import com.sadellie.unitto.data.units.database.MyBasedUnitsRepository
import com.sadellie.unitto.screens.MainViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SwapUnitsTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        viewModel = MainViewModel(
            UserPreferencesRepository(context),
            MyBasedUnitsRepository(
                Room.inMemoryDatabaseBuilder(
                    context,
                    MyBasedUnitDatabase::class.java
                ).build().myBasedUnitDao()
            ),
            ApplicationProvider.getApplicationContext()
        )
    }

    @Test
    fun swapUnits() {
        val mile = ALL_UNITS.first { it.unitId == MyUnitIDS.mile }
        val kilometer = ALL_UNITS.first { it.unitId == MyUnitIDS.kilometer }

        viewModel.changeUnitFrom(kilometer)
        viewModel.changeUnitTo(mile)
        viewModel.swapUnits()

        assertEquals(mile, viewModel.unitFrom)
        assertEquals(kilometer,viewModel.unitTo)
    }
}