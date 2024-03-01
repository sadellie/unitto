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

  alias(libs.plugins.ksp)
  alias(libs.plugins.hilt)
  alias(libs.plugins.room)
}

android.namespace = "com.sadellie.unitto.core.database"

room {
  val schemaLocation = "$projectDir/schemas"
  schemaDirectory(schemaLocation)
  println("Exported Database schema to $schemaLocation")
}

dependencies {
  implementation(libs.com.google.dagger.android.hilt.android)
  ksp(libs.com.google.dagger.dagger.android.processor)
  ksp(libs.com.google.dagger.hilt.compiler)

  implementation(libs.androidx.room.runtime)
  implementation(libs.androidx.room.ktx)
  ksp(libs.androidx.room.compiler)
}
