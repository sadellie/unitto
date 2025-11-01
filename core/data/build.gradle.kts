/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

plugins {
  id("unitto.multiplatform.library")
  alias(libs.plugins.compose)
  alias(libs.plugins.compose.compiler)
}

kotlin {
  sourceSets.commonMain.dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:themmo"))
    implementation(project(":core:evaluatto"))
    implementation(project(":core:database"))
    implementation(libs.org.jetbrains.compose.runtime.runtime)
    implementation(libs.org.jetbrains.compose.components.components.resources)
    implementation(project.dependencies.platform(libs.io.insert.koin.koin.bom))
    implementation(libs.io.insert.koin.koin.core)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines)
  }
  sourceSets.androidMain.dependencies {
    implementation(project(":core:remote"))
    implementation(libs.io.insert.koin.koin.android)
    implementation(libs.io.insert.koin.koin.core.coroutines)
  }
  sourceSets.commonTest.dependencies {
    implementation(libs.org.jetbrains.kotlin.kotlin.test)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.test)
  }
  sourceSets.androidUnitTest.dependencies { implementation(libs.org.robolectric.robolectric) }
}

android {
  namespace = "com.sadellie.unitto.core.data"
  testOptions.unitTests.isIncludeAndroidResources = true
}
