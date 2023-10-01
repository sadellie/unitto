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
    id("unitto.library")
    id("unitto.android.library.jacoco")
}

android {
    namespace = "com.sadellie.unitto.core.base"

    defaultConfig {
        buildConfigField("String", "APP_NAME", """"${libs.versions.appName.get()}"""")
        buildConfigField("String", "APP_CODE", """"${libs.versions.appCode.get()}"""")
    }

    productFlavors {
        getByName("playStore") {
            storeLink("http://play.google.com/store/apps/details?id=com.sadellie.unitto")
        }
        getByName("ruStore") {
            storeLink("https://apps.rustore.ru/app/com.sadellie.unitto")
        }
        getByName("fdroid") {
            storeLink("https://github.com/sadellie/unitto")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    testOptions.unitTests.isIncludeAndroidResources = true

    lint.warning.add("MissingTranslation")
}

fun com.android.build.api.dsl.VariantDimension.storeLink(url: String) {
    buildConfigField(
        "String",
        "STORE_LINK",
        "\"${url}\""
    )
}

dependencies {
    testImplementation(libs.junit.junit)
}
