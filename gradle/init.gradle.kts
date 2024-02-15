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

val ktlintVersion = "1.0.1"

initscript {
    val spotlessVersion = "6.23.3"

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("com.diffplug.spotless:spotless-plugin-gradle:$spotlessVersion")
    }
}

rootProject {
    subprojects {
        apply<com.diffplug.gradle.spotless.SpotlessPlugin>()
        extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
            kotlin {
                target("**/*.kt")
                targetExclude(
                    "**/build/**/*.kt",
                    "**/com/sadellie/unitto/data/converter/collections/*.kt",
                    "**/com/sadellie/unitto/core/ui/common/icons/iconpack/*.kt",
                )
                ktlint(ktlintVersion).editorConfigOverride(
                    mapOf(
                        "android" to "true",
                    ),
                )
                toggleOffOn()
                // TODO licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
            }
            format("kts") {
                target("**/*.kts")
                targetExclude("**/build/**/*.kts")
                // Look for the first line that doesn't have a block comment (assumed to be the license)
                // TODO licenseHeaderFile(rootProject.file("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
            }
            format("xml") {
                target("**/*.xml")
                targetExclude("**/build/**/*.xml")
                // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
                // TODO licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
            }
        }
    }
}
