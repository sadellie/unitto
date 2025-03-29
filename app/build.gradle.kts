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
  // Basic stuff
  id("com.android.application")
  id("kotlin-android")

  alias(libs.plugins.ksp)
  alias(libs.plugins.hilt)
  alias(libs.plugins.compose.compiler)
}

android {
  namespace = "com.sadellie.unitto"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.sadellie.unitto"
    minSdk = 21
    targetSdk = 35
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

  flavorDimensions += "distribution"
  productFlavors {
    create("boring") {}
    create("thriving") { this.applicationId = "io.github.sadellie" }
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
    create("preview") { initWith(getByName("release")) }
    create("cancer") { initWith(getByName("release")) }
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

  kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
  composeOptions.kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
  packaging.resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
}

dependencies {
  implementation(project(":core:navigation"))
  implementation(project(":core:designsystem"))
  implementation(project(":core:ui"))
  implementation(project(":core:themmo"))
  implementation(project(":core:datastore"))
  implementation(project(":feature:bodymass"))
  implementation(project(":feature:calculator"))
  implementation(project(":feature:converter"))
  implementation(project(":feature:datecalculator"))
  implementation(project(":feature:glance"))
  implementation(project(":feature:graphing"))
  implementation(project(":feature:timezone"))
  implementation(project(":feature:settings"))

  add("coreLibraryDesugaring", libs.com.android.tools.desugar.jdk.libs)

  implementation(libs.androidx.core.core.ktx)
  implementation(libs.androidx.appcompat.appcompat)
  implementation(libs.androidx.lifecycle.lifecycle.runtime.compose)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.material3.window.size)
  implementation(libs.androidx.navigation.navigation.compose)

  implementation(libs.androidx.hilt.hilt.navigation.compose)
  implementation(libs.com.google.dagger.android.hilt.android)
  ksp(libs.com.google.dagger.dagger.android.processor)
  ksp(libs.com.google.dagger.hilt.compiler)
}
