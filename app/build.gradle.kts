plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.androidassignments"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.androidassignments"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.media3.common)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.espresso.contrib)
    testImplementation(libs.junit)
    testImplementation(libs.core)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.junit.v113)
    testImplementation(libs.core.v140)
    testImplementation(libs.runner)
    testImplementation(libs.rules)
    testImplementation(libs.robolectric)
    androidTestImplementation(libs.rules)

    androidTestImplementation(libs.runner.v161)
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")



}
