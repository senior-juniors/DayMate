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
    sourceSets {
        getByName("main") {
            assets {
                srcDirs("src\\main\\assets", "src\\main\\assets")
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation ("androidx.compose.ui:ui:1.7.6")
    implementation("androidx.compose.material:material:1.7.6")
    implementation(libs.firebase.auth.ktx)
    implementation (libs.androidx.navigation.compose)
    implementation(libs.androidx.storage)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //authentication
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // Firebase
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation (platform("com.google.firebase:firebase-bom:33.12.0"))
    implementation ("com.google.firebase:firebase-firestore")
   // implementation ("com.google.firebase:firebase-firestore-24.10.0")
    implementation ("com.google.firebase:firebase-common:21.0.0")
    //implementation("com.google.firebase:firebase-common:21.0.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.5.0")


    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:21.3.0")

    // Coil (for profile picture)
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //animations
    implementation ("com.airbnb.android:lottie-compose:6.1.0")

    //Moshi  for json file calling
    implementation("com.squareup.moshi:moshi:1.13.0") // Moshi for JSON parsing
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")// Moshi Kotlin adapter for using Kotlin data classes

    implementation("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")// If you're using Kotlin serialization (make sure you're using this if needed):
    implementation ("com.squareup.moshi:moshi-adapters:1.13.0")

    //livedata
    implementation("androidx.compose.runtime:runtime-livedata:1.7.6")

    // WorkManager
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation ("androidx.compose.material:material-icons-extended:1.6.1")
    implementation ("androidx.navigation:navigation-compose:2.7.5")



    // Hilt for ViewModel + Compose
    implementation ("com.google.dagger:hilt-android:2.48")
    kapt ("com.google.dagger:hilt-android-compiler:2.48")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")




}

kapt {
    correctErrorTypes = true
}