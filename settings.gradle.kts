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
include(":data:converter")
include(":core:base")
include(":core:ui")
include(":feature:converter")
include(":feature:calculator")
include(":feature:datecalculator")
include(":feature:timezone")
include(":feature:settings")
include(":data:userprefs")
include(":data:licenses")
include(":data:calculator")
include(":data:database")
include(":data:model")
include(":data:common")
include(":data:evaluatto")
include(":data:timezone")
include(":data:backup")
