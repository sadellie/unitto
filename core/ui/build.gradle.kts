/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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
  id("unitto.library")
  alias(libs.plugins.compose.compiler)
}

android {
  namespace = "com.sadellie.unitto.core.ui"
  buildFeatures.compose = true
  composeOptions.kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
  testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
  implementation(project(":core:common"))
  implementation(project(":core:navigation"))
  implementation(project(":core:designsystem"))

  implementation(libs.androidx.compose.ui.tooling)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.foundation.foundation)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.activity.activity.compose)
  implementation(libs.androidx.compose.material3.window.size)

  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  testImplementation(libs.org.robolectric.robolectric)
}
