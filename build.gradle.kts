// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.43")

        // Google services
        classpath("com.google.gms:google-services:4.3.14")

        // Crashlytics
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.2")

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
                "-opt-in=androidx.lifecycle.compose.ExperimentalLifecycleComposeApi"
            )
        }
    }

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}