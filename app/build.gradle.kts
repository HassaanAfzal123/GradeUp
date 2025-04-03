plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id ("com.google.gms.google-services")
}

android {
    namespace = "com.example.swat1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.swat1"
        minSdk = 24
        targetSdk = 35
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
}

dependencies {
    // ✅ Core AndroidX Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // ✅ Jetpack Compose BOM (Bill of Materials)
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // ✅ Material Design Components
    implementation("com.google.android.material:material:1.9.0")

    // ✅ Navigation for Jetpack Compose
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // ✅ Accompanist (for animations, insets, and pager)
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.5-beta")
    implementation("com.google.accompanist:accompanist-pager:0.31.5-beta")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.31.5-beta")

    // ✅ ViewModel support for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // ✅ LiveData support in Compose
    implementation("androidx.compose.runtime:runtime-livedata:1.6.1")

    // ✅ Coil (for Image Loading)
    implementation("io.coil-kt:coil-compose:2.4.0")

    // ✅ Room Database (if needed)
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // ✅ Coroutine Support
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // ✅ Debugging Tools
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ✅ Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // ✅ Jetpack Compose UI Essentials
    implementation("androidx.compose.ui:ui:1.6.1")
    implementation("androidx.compose.ui:ui-text:1.6.1")
    implementation("androidx.compose.ui:ui-graphics:1.6.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.1")

    // ✅ Firebase BOM (Ensures all Firebase dependencies use compatible versions)
    implementation(platform("com.google.firebase:firebase-bom:32.7.2")) // ✅ MUST be at the top of Firebase dependencies

    // ✅ Firebase Firestore for metadata storage
    implementation("com.google.firebase:firebase-firestore-ktx")

    // ✅ Firebase Storage (for PDF uploads)
    implementation("com.google.firebase:firebase-storage-ktx")

    // ✅ Firebase App Check (required for Firestore security)
    implementation("com.google.firebase:firebase-appcheck-playintegrity")

    // ✅ Firebase Analytics (optional)
    implementation("com.google.firebase:firebase-analytics-ktx")

    implementation ("androidx.compose.material:material-icons-extended:1.5.0")
    implementation ("androidx.compose.material:material-icons-extended:1.0.0")

}
