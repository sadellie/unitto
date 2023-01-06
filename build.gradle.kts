// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.com.android.tools.build)
        classpath(libs.org.jetbrains.kotlin)
        classpath(libs.com.google.dagger)

        // Google services
        classpath(libs.com.google.gms)

        // Crashlytics
        classpath(libs.com.google.firebase)

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