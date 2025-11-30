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

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.compose)
  alias(libs.plugins.multiplatform)
  alias(libs.plugins.android.application)
}

kotlin {
  androidTarget {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions { jvmTarget.set(JvmTarget.JVM_11) }
  }
  @OptIn(ExperimentalWasmDsl::class)
  wasmJs {
    browser()
    binaries.executable()
  }
  sourceSets.commonMain.dependencies {
    implementation(libs.org.jetbrains.compose.material3.material3)
    implementation(libs.org.jetbrains.compose.ui.ui.tooling.preview)
    implementation(libs.org.jetbrains.compose.material3.material3.window.size)
    implementation(libs.org.jetbrains.androidx.navigation3.navigation3.ui)
    implementation(libs.org.jetbrains.androidx.lifecycle.lifecycle.viewmodel.navigation3)
    implementation(libs.com.eygraber.uri.kmp)
    implementation(project.dependencies.platform(libs.io.insert.koin.koin.bom))
    implementation(libs.io.insert.koin.koin.core)
    implementation(libs.io.insert.koin.koin.compose.navigation3)
    implementation(project(":core:themmo"))
    implementation(project(":core:navigation"))
    implementation(project(":core:remote"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":feature:calculator"))
    implementation(project(":feature:converter"))
    implementation(project(":feature:bodymass"))
    implementation(project(":feature:datecalculator"))
    implementation(project(":feature:settings"))
  }
  sourceSets.androidMain.dependencies {
    implementation(project(":feature:glance"))
    implementation(project(":feature:timezone"))

    implementation(libs.androidx.core.core.ktx)
    implementation(libs.androidx.appcompat.appcompat)
    implementation(libs.androidx.lifecycle.lifecycle.runtime.compose)
    implementation(libs.org.jetbrains.compose.ui.ui.tooling)

    implementation(libs.io.insert.koin.koin.androidx.startup)
    implementation(libs.io.insert.koin.koin.core.coroutines)
  }
}

android {
  namespace = "com.sadellie.unitto"
  compileSdk = 36

  defaultConfig {
    applicationId = "com.sadellie.unitto"
    minSdk = 23
    targetSdk = 36
    versionCode = libs.versions.versionCode.get().toInt()
    versionName = libs.versions.versionName.get()
    androidResources.localeFilters +=
      setOf(
        "en",
        "en-rGB",
        "de",
        "es",
        "fr",
        "hu",
        "in",
        "it",
        "nl",
        "pt-rBR",
        "ru",
        "tr",
        "zh-rCN",
      )
  }

  buildTypes {
    debug {
      isDebuggable = true
      isMinifyEnabled = false
      isShrinkResources = false
      applicationIdSuffix = ".debug"
    }
    release {
      initWith(getByName("debug"))
      isDebuggable = false
      isMinifyEnabled = true
      isShrinkResources = true
      applicationIdSuffix = ""
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  buildFeatures {
    compose = true
    aidl = false
    renderScript = false
    shaders = false
    buildConfig = false
    resValues = false
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    isCoreLibraryDesugaringEnabled = true
  }
  kotlin.jvmToolchain(11)
  composeOptions.kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
  packaging.resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
}

dependencies { coreLibraryDesugaring(libs.com.android.tools.desugar.jdk.libs) }
