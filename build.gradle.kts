/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2025 Elshan Agaev
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

import dev.iurysouza.modulegraph.Theme
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.multiplatform) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.room) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.android.test) apply false
  alias(libs.plugins.serialization) apply false

  alias(libs.plugins.detekt) apply true
  alias(libs.plugins.dev.iurysouza.modulegraph) apply true
}

tasks.register("detektProjectReport", Detekt::class) {
  description = "Generate detekt report"

  buildUponDefaultConfig = true
  ignoreFailures = false
  parallel = false
  config = rootProject.files("/detekt/config.yml")
  baseline = rootProject.file("/detekt/baseline.xml")

  setSource(files(rootDir))
  include("**/*.kt")
  exclude(
    "**/*.kts",
    "**/resources/**",
    "**/build/**",
    "**/icons/symbols/**",
    "**/evaluatto/**",
    "**/graphing/**",
  )

  reports {
    xml.required = false
    txt.required = false
    md.required = false
    sarif.required = false
    html.required = true
    html.outputLocation = rootProject.file("/detekt/report.html")
  }
}

tasks.register("detektProjectBaseline", DetektCreateBaselineTask::class) {
  description = "Update detekt baseline"

  buildUponDefaultConfig = true
  ignoreFailures = true
  parallel = false
  config = rootProject.files("/detekt/config.yml")
  baseline = rootProject.file("/detekt/baseline.xml")

  setSource(files(rootDir))
  include("**/*.kt")
  exclude(
    "**/*.kts",
    "**/resources/**",
    "**/build/**",
    "**/icons/symbols/**",
    "**/evaluatto/**",
    "**/graphing/**",
  )
}

// Use createModuleGraph
moduleGraphConfig {
  readmePath.set("./ARCHITECTURE.md")
  heading = "## Module ~~spaghetti~~ Graph"
  theme.set(Theme.DARK)

  val modules = allprojects.filter { "Unitto" !in it.name }
  modules.forEach { project: Project ->
    val readmePath = "${project.projectDir.path}/README.md"
    graph(readmePath, "## Graph") {
      focusedModulesRegex = ".*(${project.name}).*"
      // exception to exclude datastore and database
      if (project.name == "data") {
        focusedModulesRegex = ".*(${project.name})"
      }
      theme = Theme.DARK
    }
  }
}
