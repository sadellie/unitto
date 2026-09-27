/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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
  alias(libs.plugins.metro)
}

kotlin {
  android { namespace = "com.sadellie.unitto.shared" }
  sourceSets.commonMain.dependencies {
    implementation(libs.dev.zacsweers.metro.metrox.viewmodel.compose)
    implementation(libs.org.jetbrains.compose.material3.material3)
    implementation(libs.org.jetbrains.compose.ui.ui.tooling.preview)
    implementation(libs.org.jetbrains.compose.material3.material3.window.size)
    implementation(libs.org.jetbrains.androidx.navigation3.navigation3.ui)
    implementation(libs.org.jetbrains.androidx.lifecycle.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.datastore.datastore.core)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.datetime)
    implementation(libs.com.eygraber.uri.kmp)
    implementation(project(":core:themmo"))
    implementation(project(":core:navigation"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:remote"))
    implementation(project(":feature:calculator"))
    implementation(project(":feature:converter"))
    implementation(project(":feature:bodymass"))
    implementation(project(":feature:datecalculator"))
    implementation(project(":feature:timezone"))
    implementation(project(":feature:programmer"))
    implementation(project(":feature:settings"))
  }
  sourceSets.androidMain.dependencies {
    implementation(libs.androidx.appcompat.appcompat)
    implementation(project(":feature:glance"))
  }
}
