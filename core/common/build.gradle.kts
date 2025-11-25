import com.codingfeline.buildkonfig.compiler.FieldSpec

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
  alias(libs.plugins.com.codingfeline.buildkonfig)
}

buildkonfig {
  packageName = "com.sadellie.unitto.core.common"
  defaultConfigs {
    buildConfigField(FieldSpec.Type.STRING, "VERSION_CODE", libs.versions.versionCode.get())
    buildConfigField(FieldSpec.Type.STRING, "VERSION_NAME", libs.versions.versionName.get())
  }
}

kotlin {
  sourceSets.commonMain.dependencies {
    implementation(libs.org.jetbrains.compose.foundation.foundation)
    implementation(libs.org.jetbrains.compose.components.components.resources)
    implementation(libs.co.touchlab.kermit)
  }
  sourceSets.androidMain.dependencies {
    implementation(libs.big.math)
    implementation(libs.androidx.core.core.ktx)
  }
  // do not mix native BigDecimal binding from kt-math and pure java BigDecimal
  sourceSets.wasmJsMain.dependencies { implementation(project(":kt-math")) }
  sourceSets.commonTest.dependencies { implementation(libs.org.jetbrains.kotlin.kotlin.test) }
}

compose.resources { publicResClass = true }

android {
  namespace = "com.sadellie.unitto.core.common"
  lint.warning.add("MissingTranslation")
  compileOptions.isCoreLibraryDesugaringEnabled = true
}

dependencies { coreLibraryDesugaring(libs.com.android.tools.desugar.jdk.libs) }
