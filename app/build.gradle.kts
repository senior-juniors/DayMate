plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    alias(libs.plugins.kotlin.kapt)
    id("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.daymate"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.daymate"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1,DEPENDENCIES}")
    }
    sourceSets {
        getByName("main") {
            assets {
                srcDirs("src\\main\\assets", "src\\main\\assets")
            }
        }
    }
}

dependencies {

    // ================== AndroidX Core & Compose ==================
    // The Compose BOM (Bill of Materials) manages the versions for all Compose libraries.
    // This prevents version conflicts. You don't need to specify versions for individual compose libraries.
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material:material-icons-extended") // For extra icons
    implementation("androidx.compose.runtime:runtime-livedata")

    // ==== Compose Tooling & Test Support ====
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ===================== Navigation =====================
    // Use the version from your libs.versions.toml or specify one version
    implementation(libs.androidx.navigation.compose)

    // ================== Storage & Credentials ==================
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)

    // ====================== Lifecycle ======================
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // =================== Work Manager ======================
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    // =================== Room Database =====================
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // =================== Hilt DI ===========================
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // ==================== Firebase =========================
    // The Firebase BOM manages versions for Firebase libraries.
    implementation(platform("com.google.firebase:firebase-bom:33.1.2")) // Using a stable version
    implementation(libs.firebase.auth.ktx)
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-common")

    // =================== Google & Third-Party =======================
    // Use one consistent version for Google Play Services Auth
    implementation("com.google.android.gms:play-services-auth:21.2.0") // Stable version
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("com.airbnb.android:lottie-compose:6.1.0")

    // =================== Google Classroom API =======================
    // These are the libraries that were not importing correctly.
   // implementation("com.google.api-client:google-api-client-android:2.4.0")
//    implementation("com.google.apis:google-api-services-classroom:v1-rev20240226-1.43.0")
   // implementation("com.google.apis:google-api-services-classroom:v1-rev135-1.23.0")

    // ==================== Testing ============================
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

   // implementation("com.google.http-client:google-http-client-gson:1.47.1")


    implementation("com.google.api-client:google-api-client-android:2.4.0") {
        exclude(group = "com.google.guava", module = "guava-jdk5")
    }
    // implementation("com.google.apis:google-api-services-classroom:v1-rev20240226-2.0.0")
    implementation("com.google.apis:google-api-services-classroom:v1-rev135-1.23.0") {
        exclude(group = "com.google.guava", module = "guava-jdk5")
    }
    implementation("com.google.http-client:google-http-client-gson:1.44.2") { // Using a known stable version
        exclude(group = "com.google.guava", module = "guava-jdk5")
    }
}



kapt {
    correctErrorTypes = true
}



//plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.kotlin.compose)
//    id("com.google.gms.google-services")
//    alias(libs.plugins.kotlin.kapt)
//    id("kotlin-kapt")
//    id ("dagger.hilt.android.plugin")
//}
//
//android {
//    namespace = "com.example.daymate"
//    compileSdk = 35
//
//    defaultConfig {
//        applicationId = "com.example.daymate"
//        minSdk = 24
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//    kotlinOptions {
//        jvmTarget = "11"
//    }
//    buildFeatures {
//        compose = true
//    }
//    // Packaging options to handle duplicate files from different Jars
//    packagingOptions {
//        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
//    }
//    sourceSets {
//        getByName("main") {
//            assets {
//                srcDirs("src\\main\\assets", "src\\main\\assets")
//            }
//        }
//    }
//}
//
//dependencies {
//
//    // ================== AndroidX Core & Compose ==================
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//    implementation("androidx.compose.material:material-icons-extended")
//    implementation("androidx.compose.runtime:runtime-livedata")
//
//    // ==== Compose Tooling & Test Support ====
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)
//
//    // ===================== Navigation =====================
//    implementation(libs.androidx.navigation.compose)
//
//    // ================== Storage & Credentials ==================
//    implementation(libs.androidx.credentials)
//    implementation(libs.androidx.credentials.play.services.auth)
//
//    // ====================== Lifecycle ======================
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
//
//    // =================== Work Manager ======================
//    implementation("androidx.work:work-runtime-ktx:2.8.1")
//
//    // =================== Room Database =====================
//    implementation("androidx.room:room-runtime:2.6.1")
//    implementation("androidx.room:room-ktx:2.6.1")
//    kapt("androidx.room:room-compiler:2.6.1")
//
//    // =================== Hilt DI ===========================
//    implementation("com.google.dagger:hilt-android:2.48")
//    kapt("com.google.dagger:hilt-android-compiler:2.48")
//    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
//
//    // ==================== Firebase =========================
//    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
//    implementation(libs.firebase.auth.ktx)
//    implementation("com.google.firebase:firebase-analytics-ktx")
//    implementation("com.google.firebase:firebase-firestore")
//    implementation("com.google.firebase:firebase-common")
//
//    // =================== Google & Third-Party =======================
//    implementation("com.google.android.gms:play-services-auth:21.2.0")
//    implementation("io.coil-kt:coil-compose:2.4.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
//    implementation("com.airbnb.android:lottie-compose:6.1.0")
//
//    // =================== Google Classroom API =======================
//    // Exclude the old Guava dependency to resolve the duplicate class conflict.
//    implementation("com.google.api-client:google-api-client-android:2.4.0") {
//        exclude(group = "com.google.guava", module = "guava-jdk5")
//    }
//   // implementation("com.google.apis:google-api-services-classroom:v1-rev20240226-2.0.0")
//    implementation("com.google.apis:google-api-services-classroom:v1-rev135-1.23.0") {
//        exclude(group = "com.google.guava", module = "guava-jdk5")
//    }
//    implementation("com.google.http-client:google-http-client-gson:1.44.2") { // Using a known stable version
//        exclude(group = "com.google.guava", module = "guava-jdk5")
//    }
//
//    // ==================== Testing ============================
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//}
//
//kapt {
//    correctErrorTypes = true
//}
