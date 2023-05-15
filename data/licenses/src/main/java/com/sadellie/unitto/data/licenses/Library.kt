/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

package com.sadellie.unitto.data.licenses

data class AppLibrary(
    val name: String,
    val dev: String?,
    val website: String?,
    val license: String?,
    val description: String?
)

val ALL_LIBRARIES by lazy {
    listOf(
        AppLibrary(
            name = "currency-api",
            dev = "Fawaz Ahmed (fawazahmed0)",
            website = "https://github.com/fawazahmed0/currency-api",
            license = "The Unlicense",
            description = "Free Currency Rates API"
        ),
        AppLibrary(
            name = "Core Kotlin Extensions",
            dev = "The Android Open Source Project",
            website = "https://developer.android.com/jetpack/androidx/releases/core",
            license = "Apache-2.0",
            description = "Kotlin extensions for 'core' artifact"
        ),
        AppLibrary(
            name = "Material Components for Android",
            dev = "The Android Open Source Project",
            website = "https://github.com/material-components/material-components-android",
            license = "Apache-2.0",
            description = "Material Components for Android is a static library that you can add to your Android application in order to use APIs that provide implementations of the Material Design specification. Compatible on devices running API 14 or later."
        ),
        AppLibrary(
            name = "Compose UI primitives",
            dev = "The Android Open Source Project",
            website = "https://developer.android.com/jetpack/androidx/releases/compose-ui",
            license = "Apache-2.0",
            description = "Compose UI primitives. This library contains the primitives that form the Compose UI Toolkit, such as drawing, measurement and layout."
        ),
        AppLibrary(
            name = "Compose Navigation",
            dev = "The Android Open Source Project",
            website = "https://developer.android.com/jetpack/androidx/releases/navigation",
            license = "Apache-2.0",
            description = "Compose integration with Navigation"
        ),
        AppLibrary(
            name = "Compose Material3 Components",
            dev = "The Android Open Source Project",
            website = "https://developer.android.com/jetpack/androidx/releases/compose-material3",
            license = "Apache-2.0",
            description = "Compose Material You Design Components library"
        ),
        AppLibrary(
            name = "Navigation Compose Hilt Integration",
            dev = "The Android Open Source Project",
            website = "https://developer.android.com/jetpack/androidx/releases/hilt",
            license = "Apache-2.0",
            description = "Navigation Compose Hilt Integration"
        ),
        AppLibrary(
            name = "Hilt Android",
            dev = "The Android Open Source Project",
            website = "https://github.com/google/dagger",
            license = "Apache-2.0",
            description = "A fast dependency injector for Android and Java."
        ),
        AppLibrary(
            name = "Compose Material Icons Extended",
            dev = "The Android Open Source Project",
            website = "https://developer.android.com/jetpack/androidx/releases/compose-material",
            license = "Apache-2.0",
            description = "Compose Material Design extended icons. This module contains all Material icons. It is a very large dependency and should not be included directly."
        ),
        AppLibrary(
            name = "Android Preferences DataStore",
            dev = "The Android Open Source Project",
            website = "https://developer.android.com/jetpack/androidx/releases/datastore",
            license = "Apache-2.0",
            description = "Android Preferences DataStore"
        ),
        AppLibrary(
            name = "Accompanist System UI Controller library",
            dev = "Google",
            website = "https://github.com/google/accompanist/",
            license = "Apache-2.0",
            description = "Utilities for Jetpack Compose"
        ),
        AppLibrary(
            name = "firebase-analytics-ktx",
            dev = "Google",
            website = "https://developer.android.com/studio/terms.html",
            license = "ASDKL",
            description = "Library to collect and send usage statistics"
        ),
        AppLibrary(
            name = "firebase-crashlytics-ktx",
            dev = "Google",
            website = "https://developer.android.com/studio/terms.html",
            license = "Apache-2.0",
            description = "Library to collect and send crash logs"
        ),
        AppLibrary(
            name = "Compose Tooling API",
            dev = "The Android Open Source Project",
            website = "https://developer.android.com/jetpack/androidx/releases/compose-ui",
            license = "Apache-2.0",
            description = "Compose tooling library API. This library provides the API required to declare @Preview composables in user apps."
        )
    )
}
