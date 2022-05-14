// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    val composeVersion by extra ("1.2.0-beta01")

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.40.1")

        // Google services
        classpath("com.google.gms:google-services:4.3.10")

        // Crashlytics
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.8.1")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

// This block was added to make opt-in project wide
allprojects {

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
                "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                "-opt-in=androidx.compose.ui.unit.ExperimentalUnitApi",
            )
        }
    }

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}