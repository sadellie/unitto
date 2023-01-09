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
}

if (!gradle.startParameter.taskRequests.toString().contains("Fdroid")) {
    // Google Services and Firebase Crashlytics are for all flavors except fdroid
    apply(plugin="com.google.gms.google-services")
    apply(plugin="com.google.firebase.crashlytics")
} else {
    println("Didn't add Google Services since F-Droid flavor was chosen.")
}

android {
    namespace = "com.sadellie.unitto.core.base"

    defaultConfig {
        buildConfigField("String", "APP_NAME", """"${libs.versions.appName.get()}"""")
        buildConfigField("String", "APP_CODE", """"${libs.versions.appCode.get()}"""")
        buildConfigField("Boolean", "ANALYTICS", "true")
    }

    productFlavors {
        getByName("playStore") {
            buildConfigField(
                "String",
                "STORE_LINK",
                "\"http://play.google.com/store/apps/details?id=com.sadellie.unitto\""
            )
        }
        getByName("appGallery") {
            buildConfigField(
                "String",
                "STORE_LINK",
                "\"https://appgallery.huawei.com/app/C105740875\""
            )
        }
        getByName("ruPlayStore") {
            buildConfigField("String", "STORE_LINK", "\"\"")
        }
        getByName("fdroid") {
            buildConfigField(
                "String",
                "STORE_LINK",
                "\"https://github.com/sadellie/unitto\""
            )
            buildConfigField("Boolean", "ANALYTICS", "false")
        }
    }

    sourceSets {
        // Making specified flavors use same source as "playStore" flavor
        listOf("appGallery", "ruPlayStore").forEach {
            getByName(it).java.srcDirs("src/playStore/java")
            getByName(it).manifest.srcFile("src/playStore")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    lint {
        this.warning.add("MissingTranslation")
    }
}

configurations {
    val playStoreImplementation = getByName("playStoreImplementation")
    listOf("appGallery", "ruPlayStore").forEach {
        getByName("${it}Implementation").extendsFrom(playStoreImplementation)
    }
}

dependencies {
    "playStoreImplementation"(platform(libs.com.google.firebase.bom))
    "playStoreImplementation"(libs.com.google.firebase.analytics)
    "playStoreImplementation"(libs.com.google.firebase.crashlytics)
}