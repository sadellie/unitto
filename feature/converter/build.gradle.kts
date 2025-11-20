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
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.compose)
  alias(libs.plugins.serialization)
}

kotlin {
  sourceSets.commonMain.dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:datastore"))
    implementation(project(":core:evaluatto"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(libs.org.jetbrains.compose.ui.ui.tooling.preview)
    implementation(libs.org.jetbrains.compose.components.components.resources)
    implementation(libs.org.jetbrains.androidx.navigation3.navigation3.ui)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.datetime)
    implementation(libs.org.jetbrains.compose.material3.material3)
    implementation(libs.org.jetbrains.compose.material3.material3.window.size)
    implementation(project.dependencies.platform(libs.io.insert.koin.koin.bom))
    implementation(libs.io.insert.koin.koin.compose.viewmodel)
    implementation(libs.io.insert.koin.koin.compose.navigation3)
    implementation(libs.co.touchlab.kermit)
  }
  sourceSets.androidMain.dependencies {
    implementation(libs.io.insert.koin.koin.core.coroutines)
    implementation(libs.org.jetbrains.compose.ui.ui.tooling)
  }
  sourceSets.commonTest.dependencies {
    implementation(libs.org.jetbrains.kotlin.kotlin.test)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.test)
  }
  sourceSets.androidUnitTest.dependencies { implementation(libs.org.robolectric.robolectric) }
}

android {
  namespace = "com.sadellie.unitto.feature.converter"
  buildFeatures.compose = true
  composeOptions.kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
  testOptions.unitTests.isIncludeAndroidResources = true
}
