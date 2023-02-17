pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

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
include(":data:units")
include(":core:base")
include(":core:ui")
include(":feature:converter")
include(":feature:unitslist")
include(":feature:calculator")
include(":feature:settings")
include(":feature:tools")
include(":feature:epoch")
include(":data:userprefs")
include(":data:unitgroups")
include(":data:licenses")
include(":data:epoch")
include(":data:calculator")
include(":data:database")
include(":data:model")
include(":data:common")
