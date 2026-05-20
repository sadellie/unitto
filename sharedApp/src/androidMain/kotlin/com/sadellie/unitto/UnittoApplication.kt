/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2026 Elshan Agaev
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
import android.content.Context
import com.sadellie.unitto.core.data.TimeZoneDataBindings
import com.sadellie.unitto.core.data.calculator.CalculatorDataBindings
import com.sadellie.unitto.core.data.converter.ConverterDataBindings
import com.sadellie.unitto.core.datastore.DataStoreBindings
import com.sadellie.unitto.core.remote.RemoteBindings
import com.sadellie.unitto.feature.glance.GraphProvider
import com.sadellie.unitto.feature.glance.WidgetDependencies
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.createGraphFactory

class UnittoApplication : Application() {
  val appGraph by lazy { createGraphFactory<AndroidAppGraph.Factory>().create(application = this) }

  override fun onCreate() {
    super.onCreate()
    GraphProvider.widgetDependencies = appGraph
  }
}

@DependencyGraph(
  AppScope::class,
  bindingContainers =
    [
      DataStoreBindings::class,
      CalculatorDataBindings::class,
      ConverterDataBindings::class,
      TimeZoneDataBindings::class,
      RemoteBindings::class,
    ],
)
interface AndroidAppGraph : AppGraph, WidgetDependencies {
  @DependencyGraph.Factory
  fun interface Factory {
    fun create(@Provides application: Application): AndroidAppGraph
  }

  @Provides
  private fun provideContext(application: Application): Context = application.applicationContext
}

internal fun getApplicationGraph(context: Context): AndroidAppGraph =
  (context as? UnittoApplication)?.appGraph ?: error("wrong $context type")
