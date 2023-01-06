plugins {
    // Basic stuff
    id("com.android.application")
    id("kotlin-android")

    // Hilt
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

if (!gradle.startParameter.taskRequests.toString().contains("Fdroid")) {
    // Google Services and Firebase Crashlytics are for all flavors except fdroid
    apply(plugin="com.google.gms.google-services")
    apply(plugin="com.google.firebase.crashlytics")
} else {
    println("Didn't add Google Services since F-Droid flavor was chosen.")
}

// Flavor names
val playStore = "playStore"
val appGallery = "appGallery"
val ruPlayStore = "ruPlayStore"
val fdroid = "fdroid"

kapt {
    correctErrorTypes = true
    useBuildCache = true
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.sadellie.unitto"
    compileSdkVersion = "android-33"

    defaultConfig {
        applicationId = "com.sadellie.unitto"
        minSdk = 21
        targetSdk = 33
        versionCode = 13
        versionName = "Fire engine red"
        buildConfigField("Boolean", "ANALYTICS", "true")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        // Debug. No Analytics, not minified, debuggable
        getByName("debug") {
            manifestPlaceholders["analytics_deactivated"] = true
            manifestPlaceholders["crashlytics_enabled"] = false
            isDebuggable = true
            isShrinkResources = false
            isMinifyEnabled = false
        }

        // Release with analytics and minified, not debuggable
        getByName("release") {
            initWith(getByName("debug"))
            manifestPlaceholders["analytics_deactivated"] = false
            manifestPlaceholders["crashlytics_enabled"] = true
            isDebuggable = false
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        // Release without analytics and not debuggable, but minified
        create("releaseNoAnalytics") {
            initWith(getByName("release"))
            manifestPlaceholders["analytics_deactivated"] = true
            manifestPlaceholders["crashlytics_enabled"] = false
        }
    }

    flavorDimensions += "mainFlavorDimension"
    productFlavors {

        create(playStore) {
            buildConfigField(
                "String",
                "STORE_LINK",
                "\"http://play.google.com/store/apps/details?id=com.sadellie.unitto\""
            )
        }
        create(appGallery) {
            buildConfigField(
                "String",
                "STORE_LINK",
                "\"https://appgallery.huawei.com/app/C105740875\""
            )
        }
        create(ruPlayStore) {
            buildConfigField("String", "STORE_LINK", "\"\"")
        }
        create(fdroid) {
            // Not uploaded yet, no store link
            buildConfigField(
                "String",
                "STORE_LINK",
                "\"https://github.com/sadellie/unitto\""
            )
            buildConfigField("Boolean", "ANALYTICS", "false")
        }
    }

    sourceSets {
        // Making specified flavors use same source as "playStore" flavor
        listOf(appGallery, ruPlayStore).forEach {
            getByName(it).java.srcDirs("src/${playStore}/java")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true

        // These are unused features
        aidl = false
        renderScript = false
        shaders = false
    }
    lint {
        this.warning.add("MissingTranslation")
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0-alpha02"
    }
    packagingOptions {
        jniLibs.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

configurations {
    val playStoreImplementation = getByName("playStoreImplementation")
    listOf(appGallery, ruPlayStore).forEach {
        getByName("${it}Implementation").extendsFrom(playStoreImplementation)
    }
}

@Suppress("UnstableApiUsage")
dependencies {
    implementation(libs.androidx.core)
    androidTestImplementation(libs.androidx.test)
    androidTestImplementation(libs.androidx.test.ext)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    testImplementation(libs.org.robolectric)
    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)

    // Compose and navigation
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Material Design 3
    implementation(libs.androidx.compose.material3)

    // Hilt and navigation
    implementation(libs.androidx.hilt)
    kapt(libs.com.google.dagger.processor)
    implementation(libs.com.google.dagger.android)
    kapt(libs.com.google.dagger.complier)

    // There are a lot of icons
    implementation(libs.androidx.compose.material.icons.extended)

    // DataStore
    implementation(libs.androidx.datastore)

    // This is for system status bar color
    implementation(libs.com.google.accompanist.systemuicontroller)

    // Firebase
    "playStoreImplementation"(platform(libs.com.google.firebase.bom))
    // Crashlytics and Analytics
    "playStoreImplementation"(libs.com.google.firebase.analytics)
    "playStoreImplementation"(libs.com.google.firebase.crashlytics)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // Moshi
    implementation(libs.com.squareup.moshi)

    // Retrofit with Moshi Converter
    implementation(libs.com.squareup.retrofit2)

    // Themmo
    implementation(libs.com.github.sadellie.themmo)

    // ComposeReorderable
    implementation(libs.org.burnoutcrew.composereorderable)

    // ExprK
    implementation(libs.com.github.sadellie.exprk)
}
