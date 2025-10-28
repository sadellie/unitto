/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2025 Elshan Agaev
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

package com.sadellie.unitto.core.licenses

data class ThirdParty(
  val name: String,
  val dev: String,
  val website: String,
  val license: String,
  val description: String,
) {
  companion object {
    fun allThirdParty(): List<ThirdParty> =
      listOf(
        ThirdParty(
          name = "currency-api",
          dev = "Fawaz Ahmed (fawazahmed0)",
          website = "https://github.com/fawazahmed0/exchange-api",
          license = "CC0 1.0 Universal",
          description = "Free Currency Rates API",
        ),
        ThirdParty(
          name = "Lato",
          dev = "Łukasz Dziedzic",
          website = "https://fonts.google.com/specimen/Lato/about",
          license = "Open Font License",
          description =
            "Lato is a sans serif typeface family started in the summer of 2010 by Warsaw-based designer Łukasz Dziedzic (“Lato” means “Summer” in Polish). In December 2010 the Lato family was published under the Open Font License by his foundry tyPoland, with support from Google.",
        ),
        ThirdParty(
          name = "Core Kotlin Extensions",
          dev = "The Android Open Source Project",
          website = "https://developer.android.com/jetpack/androidx/releases/core",
          license = "Apache-2.0",
          description = "Kotlin extensions for 'core' artifact",
        ),
        ThirdParty(
          name = "Compose UI primitives",
          dev = "The Android Open Source Project",
          website = "https://developer.android.com/jetpack/androidx/releases/compose-ui",
          license = "Apache-2.0",
          description =
            "Compose UI primitives. This library contains the primitives that form the Compose UI Toolkit, such as drawing, measurement and layout.",
        ),
        ThirdParty(
          name = "Compose Navigation",
          dev = "The Android Open Source Project",
          website = "https://developer.android.com/jetpack/androidx/releases/navigation",
          license = "Apache-2.0",
          description = "Compose integration with Navigation",
        ),
        ThirdParty(
          name = "Compose Material3 Components",
          dev = "The Android Open Source Project",
          website = "https://developer.android.com/jetpack/androidx/releases/compose-material3",
          license = "Apache-2.0",
          description = "Compose Material You Design Components library",
        ),
        ThirdParty(
          name = "Navigation Compose Hilt Integration",
          dev = "The Android Open Source Project",
          website = "https://developer.android.com/jetpack/androidx/releases/hilt",
          license = "Apache-2.0",
          description = "Navigation Compose Hilt Integration",
        ),
        ThirdParty(
          name = "Hilt Android",
          dev = "The Android Open Source Project",
          website = "https://github.com/google/dagger",
          license = "Apache-2.0",
          description = "A fast dependency injector for Android and Java.",
        ),
        ThirdParty(
          name = "Material Symbols",
          dev = "Google Fonts",
          website = "https://fonts.google.com/",
          license = "Apache-2.0",
          description = "Icons",
        ),
        ThirdParty(
          name = "Android Preferences DataStore",
          dev = "The Android Open Source Project",
          website = "https://developer.android.com/jetpack/androidx/releases/datastore",
          license = "Apache-2.0",
          description = "Android Preferences DataStore",
        ),
        ThirdParty(
          name = "Compose Tooling API",
          dev = "The Android Open Source Project",
          website = "https://developer.android.com/jetpack/androidx/releases/compose-ui",
          license = "Apache-2.0",
          description =
            "Compose tooling library API. This library provides the API required to declare @Preview composables in user apps.",
        ),
        ThirdParty(
          name = "Reorderable",
          dev = "Calvin Liang (Calvin-LL)",
          website = "https://github.com/Calvin-LL/Reorderable",
          license = "Apache-2.0",
          description =
            "Reorder items in Lists and Grids in Jetpack Compose and Compose Multiplatform with drag and drop",
        ),
        ThirdParty(
          name = "m3color",
          dev = "Kyant0",
          website = "https://github.com/Kyant0/m3color",
          license = "Apache-2.0",
          description = "Material 3 color palette Java library",
        ),
        ThirdParty(
          name = "big-math",
          dev = "eobermuhlner (Eric Obermühlner)",
          website = "https://github.com/eobermuhlner/big-math",
          license = "MIT",
          description =
            "Advanced Java BigDecimal math functions (pow, sqrt, log, sin, …) using arbitrary precision",
        ),
      )
  }
}
