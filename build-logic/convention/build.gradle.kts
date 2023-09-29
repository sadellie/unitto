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
    `kotlin-dsl`
}

group = "com.sadellie.unitto.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("unittoHilt") {
            id = "unitto.android.hilt"
            implementationClass = "UnittoHiltPlugin"
        }

        register("unittoLibraryPlugin") {
            id = "unitto.library"
            implementationClass = "UnittoLibraryPlugin"
        }

        register("unittoLibraryFeaturePlugin") {
            id = "unitto.library.feature"
            implementationClass = "UnittoLibraryFeaturePlugin"
        }

        register("unittoLibraryComposePlugin") {
            id = "unitto.library.compose"
            implementationClass = "UnittoLibraryComposePlugin"
        }

        register("unittoRoomPlugin") {
            id = "unitto.room"
            implementationClass = "UnittoRoomPlugin"
        }

        register("unittoAndroidApplicationJacoco") {
            id = "unitto.android.application.jacoco"
            implementationClass = "UnittoAndroidApplicationJacocoPlugin"
        }

        register("unittoAndroidLibraryJacocoPlugin") {
            id = "unitto.android.library.jacoco"
            implementationClass = "UnittoAndroidLibraryJacocoPlugin"
        }
    }
}
