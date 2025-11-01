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
    implementation(project(":core:common"))
    implementation(project(":core:navigation"))
    implementation(project(":core:designsystem"))
    implementation(libs.org.jetbrains.compose.foundation.foundation)
    implementation(libs.org.jetbrains.compose.components.components.resources)
    implementation(libs.org.jetbrains.compose.ui.ui.tooling.preview)
    implementation(libs.org.jetbrains.compose.material3.material3)
    implementation(libs.org.jetbrains.compose.material3.material3.window.size)
    implementation(libs.org.jetbrains.androidx.lifecycle.lifecycle.viewmodel.savedstate)
  }
  sourceSets.androidMain.dependencies {
    implementation(libs.org.jetbrains.compose.ui.ui.tooling)
    implementation(libs.androidx.activity.activity.compose)
  }
  sourceSets.androidUnitTest.dependencies { implementation(libs.org.robolectric.robolectric) }
  sourceSets.commonTest.dependencies {
    implementation(libs.org.jetbrains.kotlin.kotlin.test)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.test)
  }
  sourceSets.androidInstrumentedTest.dependencies {
    implementation(libs.androidx.compose.ui.test.junit4)
  }
}

android {
  namespace = "com.sadellie.unitto.core.ui"
  buildFeatures.compose = true
  composeOptions.kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
  testOptions.unitTests.isIncludeAndroidResources = true
}
