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
  id("unitto.library")

  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.compose)
  alias(libs.plugins.serialization)
}

android {
  namespace = "com.sadellie.unitto.feature.glance"
  buildFeatures.compose = true
  composeOptions.kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
}

dependencies {
  implementation(project(":core:common"))
  implementation(project(":core:data"))
  implementation(project(":core:database"))
  implementation(project(":core:datastore"))
  implementation(project(":core:designsystem"))
  implementation(project(":core:evaluatto"))
  implementation(project(":core:model"))
  implementation(project(":core:navigation"))
  implementation(project(":core:themmo"))
  implementation(project(":core:ui"))

  implementation(libs.org.jetbrains.compose.components.components.resources)
  implementation(project.dependencies.platform(libs.io.insert.koin.koin.bom))
  implementation(libs.io.insert.koin.koin.core.coroutines)
  implementation(libs.io.insert.koin.koin.compose.viewmodel)

  implementation(libs.androidx.appcompat.appcompat)
  implementation(libs.org.jetbrains.compose.foundation.foundation)
  implementation(libs.org.jetbrains.compose.ui.ui)
  implementation(libs.org.jetbrains.compose.material3.material3)
  implementation(libs.org.jetbrains.compose.ui.ui.tooling.preview)
  implementation(libs.org.jetbrains.androidx.navigation.navigation.compose)
  implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
  implementation(libs.androidx.glance.glance)
  implementation(libs.androidx.glance.glance.appwidget)
  implementation(libs.androidx.glance.glance.appwidget.preview)
  implementation(libs.androidx.glance.glance.preview)
  implementation(libs.androidx.glance.glance.material3)
  implementation(libs.androidx.work.work.runtime.ktx)
}
