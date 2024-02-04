/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
rootProject.name = "Unitto"
include(":app")
include(":benchmark")
include(":core:base")
include(":core:ui")
include(":data:backup")
include(":data:calculator")
include(":data:common")
include(":data:converter")
include(":data:database")
include(":data:evaluatto")
include(":data:licenses")
include(":data:model")
include(":data:timezone")
include(":data:userprefs")
include(":feature:bodymass")
include(":feature:calculator")
include(":feature:converter")
include(":feature:datecalculator")
include(":feature:glance")
include(":feature:settings")
include(":feature:timezone")
