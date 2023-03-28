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
        getByName("nashStore") {
            storeLink("https://store.nashstore.ru/store/627de8394891a527a6efe56a")
        }
        getByName("ruStore") {
            storeLink("https://apps.rustore.ru/app/com.sadellie.unitto")
        }
        getByName("ruMarket") {
            storeLink("https://store.ruplay.market/app/com.sadellie.unitto")
        }
        getByName("fdroid") {
            storeLink("https://github.com/sadellie/unitto")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    lint {
        this.warning.add("MissingTranslation")
    }
}

fun com.android.build.api.dsl.VariantDimension.storeLink(url: String) {
    buildConfigField(
        "String",
        "STORE_LINK",
        "\"${url}\""
    )
}
