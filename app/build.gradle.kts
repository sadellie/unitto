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

@file:Suppress("UnstableApiUsage")

plugins {
    // Basic stuff
    id("com.android.application")
    id("kotlin-android")

    id("unitto.android.hilt")
}

android {
    namespace = "com.sadellie.unitto"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sadellie.unitto"
        minSdk = 21
        targetSdk = 34
        versionCode = 23
        versionName = "Mikado Yellow"
        resourceConfigurations += setOf("en", "de", "en-rGB", "fr", "it", "ru")
    }

    buildTypes {
        // Debug. No Analytics, not minified, debuggable
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
        }

        // Release with analytics and minified, not debuggable
        release {
            initWith(getByName("debug"))
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            applicationIdSuffix = ""
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += "mainFlavorDimension"
    productFlavors {
        create("playStore") {}
        create("ruStore") {}
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=androidx.lifecycle.compose.ExperimentalLifecycleComposeApi"
        )
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(libs.androidx.core)
    coreLibraryDesugaring(libs.android.desugarJdkLibs)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.com.github.sadellie.themmo)
    implementation(libs.com.google.accompanist.systemuicontroller)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.appcompat)

    implementation(project(mapOf("path" to ":feature:converter")))
    implementation(project(mapOf("path" to ":feature:calculator")))
    implementation(project(mapOf("path" to ":feature:settings")))
    implementation(project(mapOf("path" to ":feature:datecalculator")))
    implementation(project(mapOf("path" to ":feature:timezone")))
    implementation(project(mapOf("path" to ":data:model")))
    implementation(project(mapOf("path" to ":data:userprefs")))
    implementation(project(mapOf("path" to ":core:ui")))
    implementation(project(mapOf("path" to ":core:base")))
}
