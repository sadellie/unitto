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
    id("unitto.library")
    id("unitto.android.library.jacoco")
}

android {
    namespace = "com.sadellie.unitto.core.base"

    defaultConfig {
        stringConfigField("VERSION_NAME", libs.versions.versionName.get())
        stringConfigField("VERSION_CODE", libs.versions.versionCode.get())
    }

    productFlavors {
        getByName("playStore") {
            stringConfigField(
                "STORE_LINK",
                "http://play.google.com/store/apps/details?id=com.sadellie.unitto"
            )
        }
        getByName("fdroid") {
            stringConfigField(
                "STORE_LINK",
                "https://github.com/sadellie/unitto"
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }

    testOptions.unitTests.isIncludeAndroidResources = true

    lint.warning.add("MissingTranslation")
}

fun com.android.build.api.dsl.VariantDimension.stringConfigField(name: String, url: String) {
    buildConfigField(
        "String", name, "\"${url}\""
    )
}
