import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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
  id("unitto.multiplatform.library")
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.compose)
  alias(libs.plugins.metro)
}

kotlin {
  android { namespace = "com.sadellie.unitto.shared" }
  sourceSets.commonMain.dependencies {
    implementation(libs.dev.zacsweers.metro.metrox.viewmodel.compose)
    implementation(libs.org.jetbrains.compose.material3.material3)
    implementation(libs.org.jetbrains.compose.ui.ui.tooling.preview)
    implementation(libs.org.jetbrains.compose.material3.material3.window.size)
    implementation(libs.org.jetbrains.androidx.navigation3.navigation3.ui)
    implementation(libs.org.jetbrains.androidx.lifecycle.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.datastore.datastore.core)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.datetime)
    implementation(libs.com.eygraber.uri.kmp)
    implementation(project(":core:themmo"))
    implementation(project(":core:navigation"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:remote"))
    implementation(project(":feature:calculator"))
    implementation(project(":feature:converter"))
    implementation(project(":feature:bodymass"))
    implementation(project(":feature:datecalculator"))
    implementation(project(":feature:timezone"))
    implementation(project(":feature:programmer"))
    implementation(project(":feature:settings"))
  }
  sourceSets.androidMain.dependencies {
    implementation(libs.androidx.appcompat.appcompat)
    implementation(project(":feature:glance"))
  }
}

@OptIn(ExperimentalTime::class)
tasks.register("wasmUpdatePrecacheValues") {
  group = "pwa"
  description = "update cache version and precached assets in sw on dist build"

  // Run after the distribution is built
  dependsOn("wasmJsBrowserDistribution")
  val buildFile = layout.buildDirectory.asFile.get()
  val sourceSw = layout.projectDirectory.file("src/wasmJsMain/resources/sw.js").asFile
  val replacementTag = """[/*__PRECACHED_ASSETS__*/]"""
  val versionTag = "{VERSION}"
  doLast {
    val distPath = buildFile.resolve("dist/wasmJs/productionExecutable")
    if (!distPath.exists()) throw GradleException("dist not found: $distPath")

    val targetSw = distPath.resolve("sw.js")
    if (!sourceSw.exists()) throw GradleException("source sw not found: $sourceSw")
    if (!targetSw.exists()) throw GradleException("target sw not found: $targetSw")

    // update assets
    val assets =
      distPath
        .walkTopDown()
        .filter { file ->
          file.isFile && file.name != "sw.js" && !file.isHidden
        }
        .map { file ->
          file.relativeTo(distPath).path.replace(File.separatorChar, '/')
        }
        .sorted()
        .toList()
    println("inject ${assets.size} assets into sw.js:")
    assets.forEach { println("- $it") }
    val assetsArray = assets.joinToString(",") { "\"$it\"" }
    val replacement = """["./",$assetsArray]"""
    val content = targetSw.readText()
    if (replacementTag !in content)
      throw GradleException("placeholder $replacementTag not found in sw.js")

    var updatedContent = content.replace(replacementTag, replacement)

    // update cache version
    if (versionTag !in updatedContent)
      throw GradleException("placeholder $versionTag not found in sw.js")
    val version = Clock.System.now().toEpochMilliseconds().toString()
    println("set cache version version: $version")
    updatedContent = updatedContent.replace(versionTag, version)

    targetSw.writeText(updatedContent)
    println("sw.js updated")
  }
}

tasks.named("wasmJsBrowserDistribution") {
  finalizedBy("wasmUpdatePrecacheValues")
}
