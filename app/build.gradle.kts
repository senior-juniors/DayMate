plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.example.daymate"
    compileSdk = 34

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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation ("com.google.firebase:firebase-auth:23.1.0")
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-firestore:25.1.1")

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



}