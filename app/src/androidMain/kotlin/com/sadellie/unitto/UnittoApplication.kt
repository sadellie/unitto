/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2025 Elshan Agaev
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

package com.sadellie.unitto

import android.app.Application
import com.sadellie.unitto.core.data.dataModule
import com.sadellie.unitto.core.database.unittoDatabaseModule
import com.sadellie.unitto.core.datastore.dataStoreModule
import com.sadellie.unitto.core.remote.currencyApiModule
import com.sadellie.unitto.feature.bodymass.bodyMassModule
import com.sadellie.unitto.feature.calculator.calculatorModule
import com.sadellie.unitto.feature.converter.converterModule
import com.sadellie.unitto.feature.datecalculator.dateCalculatorModule
import com.sadellie.unitto.feature.glance.converter.converterWidgetModule
import com.sadellie.unitto.feature.settings.settingsModule
import com.sadellie.unitto.feature.timezone.timeZoneModule
import org.koin.android.ext.koin.androidContext
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.lazyModules
import org.koin.dsl.koinConfiguration

@OptIn(KoinExperimentalAPI::class)
internal class UnittoApplication : Application(), KoinStartup {
  override fun onKoinStartup() = koinConfiguration {
    androidContext(this@UnittoApplication)
    lazyModules(
      listOf(
        unittoDatabaseModule,
        dataModule,
        dataStoreModule,
        bodyMassModule,
        calculatorModule,
        converterModule,
        currencyApiModule,
        dateCalculatorModule,
        settingsModule,
        timeZoneModule,
        converterWidgetModule,
      )
    )
  }
}
