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

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import kotlin.jvm.optionals.getOrNull

@Suppress("UNUSED")
class UnittoMultiplatformLibraryPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      apply(plugin = libs.getPlugin("multiplatform"))
      apply(plugin = libs.getPlugin("android.library"))

      extensions.configure<KotlinMultiplatformExtension> {
        androidTarget {
          @OptIn(ExperimentalKotlinGradlePluginApi::class)
          compilerOptions { jvmTarget.set(JvmTarget.JVM_11) }
        }
        @OptIn(ExperimentalWasmDsl::class)
        wasmJs {
          browser { testTask { useKarma().useFirefoxHeadless() } }
          binaries.executable()
        }
        compilerOptions {
          optIn.addAll(
            "androidx.compose.material3.ExperimentalMaterial3Api",
            "androidx.compose.material3.ExperimentalMaterial3ExpressiveApi",
            "androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi",
            "androidx.compose.animation.ExperimentalAnimationApi",
            "androidx.compose.foundation.ExperimentalFoundationApi",
            "androidx.compose.foundation.layout.ExperimentalLayoutApi",
            "androidx.compose.ui.unit.ExperimentalUnitApi",
            "kotlinx.coroutines.ExperimentalCoroutinesApi",
          )
        }
        targets.configureEach {
          compilations.configureEach {
            compileTaskProvider.get().compilerOptions {
              freeCompilerArgs.add("-Xexpect-actual-classes")
            }
          }
        }
      }
      extensions.configure<LibraryExtension> {
        compileSdk = 36
        defaultConfig.minSdk = 23
        compileOptions.isCoreLibraryDesugaringEnabled = true
      }
      dependencies {
        "coreLibraryDesugaring"(libs.findLibrary("com.android.tools.desugar.jdk.libs").get())
      }
    }
  }
}

@Suppress("UNUSED")
class UnittoLibraryPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      apply(plugin = libs.getPlugin("kotlin.android"))
      apply(plugin = libs.getPlugin("android.library"))

      extensions.configure<LibraryExtension> {
        compileSdk = 36
        defaultConfig.minSdk = 23

        buildFeatures {
          compose = false
          aidl = false
          renderScript = false
          shaders = false
          buildConfig = false
          resValues = false
        }

        packaging.resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")

        compileOptions {
          sourceCompatibility = JavaVersion.VERSION_11
          targetCompatibility = JavaVersion.VERSION_11
          isCoreLibraryDesugaringEnabled = true
        }

        configureKotlin<KotlinAndroidProjectExtension>()

        defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        defaultConfig.consumerProguardFiles("consumer-rules.pro")
      }

      dependencies {
        "implementation"(libs.findLibrary("androidx.core.core.ktx").get())
        "coreLibraryDesugaring"(libs.findLibrary("com.android.tools.desugar.jdk.libs").get())

        "testImplementation"(libs.findLibrary("junit.junit").get())

        "androidTestImplementation"(libs.findLibrary("androidx.test.core").get())
        "androidTestImplementation"(libs.findLibrary("androidx.test.ext.junit.ktx").get())
        "androidTestImplementation"(libs.findLibrary("androidx.test.runner").get())
      }
    }
  }
}

private val Project.libs
  get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

private fun VersionCatalog.getPlugin(name: String) =
  findPlugin(name).getOrNull()?.get()?.pluginId ?: error("$name not found")

/** Configure base Kotlin options */
private inline fun <reified T : KotlinBaseExtension> Project.configureKotlin() =
  configure<T> {
    // Treat all Kotlin warnings as errors (disabled by default)
    // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
    val warningsAsErrors: String? by project
    val compilerOptions =
      when (this) {
        is KotlinAndroidProjectExtension -> compilerOptions
        is KotlinJvmProjectExtension -> compilerOptions
        else -> error("Unsupported project extension $this ${T::class}")
      }

    compilerOptions.jvmTarget = JvmTarget.JVM_11
    compilerOptions.allWarningsAsErrors = warningsAsErrors.toBoolean()
    compilerOptions.freeCompilerArgs.addAll(
      "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
      "-opt-in=androidx.compose.material3.ExperimentalMaterial3ExpressiveApi",
      "-opt-in=androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi",
      "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
      "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
      "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
      "-opt-in=androidx.compose.ui.unit.ExperimentalUnitApi",
      "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
    )
  }
