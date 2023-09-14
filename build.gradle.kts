@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.gradlePlugin) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}