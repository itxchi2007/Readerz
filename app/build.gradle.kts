
plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.readerz"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.readerz"
        minSdk = 26
        targetSdk = 35
        versionCode = 3
        versionName = "0.3"
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("androidx.documentfile:documentfile:1.0.1")
    implementation("org.apache.poi:poi:5.2.3")
    implementation("org.apache.poi:poi-ooxml:5.2.3")
    implementation("com.tom-roush:pdfbox-android:2.0.27.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("com.github.koral--:epublib-core:3.1") // lightweight epub reader
    implementation("androidx.webkit:webkit:1.9.0") // for WebView rendering
    implementation("androidx.multidex:multidex:2.0.1")
}
