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

package com.sadellie.unitto

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

internal fun Project.configureKotlinAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>) {
  commonExtension.apply {
    compileSdk = 35

    defaultConfig.minSdk = 21

    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_11
      targetCompatibility = JavaVersion.VERSION_11
      isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
      compose = false
      aidl = false
      renderScript = false
      shaders = false
      buildConfig = false
      resValues = false
    }

    packaging.resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")

    configureKotlin<KotlinAndroidProjectExtension>()
  }

  val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

  dependencies {
    add("coreLibraryDesugaring", libs.findLibrary("com.android.tools.desugar.jdk.libs").get())
  }
}

/** Configure base Kotlin options */
private inline fun <reified T : KotlinBaseExtension> Project.configureKotlin() =
  configure<T> {
    // Treat all Kotlin warnings as errors (disabled by default)
    // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
    val warningsAsErrors: String? by project
    when (this) {
      is KotlinAndroidProjectExtension -> compilerOptions
      is KotlinJvmProjectExtension -> compilerOptions
      else -> error("Unsupported project extension $this ${T::class}")
    }.apply {
      jvmTarget = JvmTarget.JVM_11
      allWarningsAsErrors = warningsAsErrors.toBoolean()
      freeCompilerArgs.addAll(
        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
        "-opt-in=androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi",
        "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
        "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
        "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
        "-opt-in=androidx.compose.ui.unit.ExperimentalUnitApi",
        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
      )
    }
  }
