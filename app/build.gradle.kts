plugins {
    // Basic stuff
    id("com.android.application")
    id("kotlin-android")

    // Hilt
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")

    // Google Services
    id("com.google.gms.google-services")

    // Firebase Crashlytics
    id("com.google.firebase.crashlytics")
}

val composeVersion = "1.2.0-beta03"

kapt {
    correctErrorTypes = true
    useBuildCache = true
}

android {
    namespace = "com.sadellie.unitto"
    compileSdkVersion = "android-32"

    defaultConfig {
        applicationId = "com.sadellie.unitto"
        minSdk = 21
        targetSdk = 32
        versionCode = 8
        versionName = "Cornsilk"

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
        create("releaseNoAnal") {
            initWith(getByName("release"))
            manifestPlaceholders["analytics_deactivated"] = true
            manifestPlaceholders["crashlytics_enabled"] = false
        }
    }

    flavorDimensions += "mainFlavorDimension"
    productFlavors {
        create("playStore") {
            buildConfigField(
                "String",
                "StoreLink",
                "\"http://play.google.com/store/apps/details?id=com.sadellie.unitto\""
            )
        }
        create("appGallery") {
            buildConfigField(
                "String",
                "StoreLink",
                "\"https://appgallery.huawei.com/app/C105740875\""
            )
        }
        create("ruPlayStore") {
            buildConfigField("String", "StoreLink", "\"\"")
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
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
    packagingOptions {
        jniLibs.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.8.0")
    androidTestImplementation("androidx.test:core:1.4.0")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test:rules:1.4.0")

    // Compose and navigation
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.navigation:navigation-compose:2.5.0")

    // Material Design 3
    implementation("androidx.compose.material3:material3:1.0.0-alpha14")

    // Hilt and navigation
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:dagger-android-processor:2.42")
    implementation("com.google.dagger:hilt-android:2.42")
    kapt("com.google.dagger:hilt-compiler:2.42")

    // There are a lot of icons
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // This is for system status bar color
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.17.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:30.1.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")

    // Crashlytics and Analytics
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    // Room
    implementation("androidx.room:room-runtime:2.4.2")
    implementation("androidx.room:room-ktx:2.4.2")
    kapt("androidx.room:room-compiler:2.4.2")

    // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")

    // Retrofit with Moshi Converter
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // Themmo
    implementation("com.github.sadellie:themmo:0.0.2")
}