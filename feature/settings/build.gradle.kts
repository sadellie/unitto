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
  alias(libs.plugins.serialization)
}

android {
  namespace = "com.sadellie.unitto.feature.settings"
  defaultConfig.stringConfigField("VERSION_NAME", libs.versions.versionName.get())
  defaultConfig.stringConfigField("VERSION_CODE", libs.versions.versionCode.get())
  defaultConfig.stringConfigField("STORE_LINK", "https://github.com/sadellie/unitto")
  buildTypes.getByName("cancer").stringConfigField("STORE_LINK", "https://play.google.com/store/apps/details?id=com.sadellie.unitto")
  buildFeatures.compose = true
  buildFeatures.buildConfig = true
  composeOptions.kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
}

dependencies {
  implementation(project(":core:common"))
  implementation(project(":core:ui"))
  implementation(project(":core:navigation"))
  implementation(project(":core:designsystem"))
  implementation(project(":core:data"))
  implementation(project(":core:datastore"))
  implementation(project(":core:database"))
  implementation(project(":core:backup"))
  implementation(project(":core:model"))
  implementation(project(":core:themmo"))
  implementation(project(":core:licenses"))

  implementation(project.dependencies.platform(libs.io.insert.koin.koin.bom))
  implementation(libs.io.insert.koin.koin.compose.viewmodel)
  implementation(libs.io.insert.koin.koin.core.coroutines)

  implementation(libs.androidx.appcompat.appcompat)
  implementation(libs.androidx.compose.foundation.foundation)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.material3.window.size)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.navigation.navigation.compose)

  implementation(libs.sh.calvin.reorderable.reorderable)
  implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
  testImplementation(libs.junit.junit)
  testImplementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.test)
}

fun com.android.build.api.dsl.VariantDimension.stringConfigField(name: String, url: String) {
  buildConfigField("String", name, "\"${url}\"")
}
