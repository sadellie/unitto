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

  alias(libs.plugins.ksp)
  alias(libs.plugins.hilt)
}

android {
  namespace = "com.sadellie.unitto.core.data"
  testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
  implementation(project(":core:model"))
  implementation(project(":core:common"))
  implementation(project(":core:remote"))
  implementation(project(":core:evaluatto"))
  implementation(project(":core:database"))
  implementation(project(":core:themmo"))

  implementation(libs.com.google.dagger.android.hilt.android)
  ksp(libs.com.google.dagger.dagger.android.processor)
  ksp(libs.com.google.dagger.hilt.compiler)

  testImplementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.test)
  testImplementation(libs.org.robolectric.robolectric)
}
