/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

    id("unitto.android.hilt")
}

android {
    namespace = "com.sadellie.unitto"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.sadellie.unitto"
        minSdk = 21
        targetSdk = 33
        versionCode = 14
        versionName = "Glaucous"
    }

    buildTypes {
        // Debug. No Analytics, not minified, debuggable
        debug {
            isDebuggable = true
            isShrinkResources = false
            isMinifyEnabled = false
        }

        // Release with analytics and minified, not debuggable
        release {
            initWith(getByName("debug"))
            isShrinkResources = true
            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += "mainFlavorDimension"
    productFlavors {
        create("playStore") {}
        create("appGallery") {}
        create("ruStore") {}
        create("nashStore") {}
        create("ruMarket") {}
        create("fdroid") {}
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=androidx.lifecycle.compose.ExperimentalLifecycleComposeApi"
        )
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
}

dependencies {
    implementation(libs.androidx.core)
    coreLibraryDesugaring(libs.android.desugarJdkLibs)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.com.github.sadellie.themmo)
    implementation(libs.com.google.accompanist.systemuicontroller)

    implementation(project(mapOf("path" to ":feature:converter")))
    implementation(project(mapOf("path" to ":feature:calculator")))
    implementation(project(mapOf("path" to ":feature:settings")))
    implementation(project(mapOf("path" to ":feature:unitslist")))
    implementation(project(mapOf("path" to ":feature:tools")))
    implementation(project(mapOf("path" to ":feature:epoch")))
    implementation(project(mapOf("path" to ":data:units")))
    implementation(project(mapOf("path" to ":data:userprefs")))
    implementation(project(mapOf("path" to ":core:ui")))
}
