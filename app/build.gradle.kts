plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-parcelize")
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.cinemate.cinemateapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cinemate.cinemateapp"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    flavorDimensions += "env"
    productFlavors {
        create("production") {
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = "\"https://api.themoviedb.org/3/movie/\"",
            )
            buildConfigField(
                type = "String",
                name = "KEY",
                value = "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMTg5MGU5NWM0NTc5NzgwZjc0MzQ3ODk3ZmM1MmNjOCIsInN1YiI6IjY2NDIwYjRjN2IxOTUyOGY2MDVhNTgyMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.B1uahWkZX8Goo1Id-4J5_jPTKo-CTQAXYm1MD3Gai0E\""
            )
        }
        create("integration") {
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = "\"https://api.themoviedb.org/3/movie/\"",
            )
            buildConfigField(
                type = "String",
                name = "KEY",
                value = "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMTg5MGU5NWM0NTc5NzgwZjc0MzQ3ODk3ZmM1MmNjOCIsInN1YiI6IjY2NDIwYjRjN2IxOTUyOGY2MDVhNTgyMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.B1uahWkZX8Goo1Id-4J5_jPTKo-CTQAXYm1MD3Gai0E\""
            )
        }
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    packagingOptions {
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE-notice.md")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.coil)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.fragment.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.coroutine.core)
    implementation(libs.coroutine.android)

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)

    implementation(libs.paging)

    implementation(libs.koin.android)

    androidTestImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.turbine)
    testImplementation(libs.core.testing)
}