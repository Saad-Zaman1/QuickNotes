plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.saad.quicknotes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.saad.quicknotes"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        debug {
            isDebuggable = true
            buildConfigField(
                "String",
                "ADMOB_BANNER",
                "\"" + project.findProperty("DEV_BANNER_ID") + "\""
            )
            buildConfigField(
                "String",
                "ADMOB_REWARD",
                "\"" + project.findProperty("DEV_REWARD_ID") + "\""
            )
            buildConfigField(
                "String",
                "ADMOB_NATIVE",
                "\"" + project.findProperty("DEV_NATIVE_ID") + "\""
            )
            buildConfigField(
                "String",
                "ADMOB_INTERSTITIAL",
                "\"" + project.findProperty("DEV_INTERSTITIAL_ID") + "\""
            )
            buildConfigField(
                "String",
                "ADMOB_APP_OPEN",
                "\"" + project.findProperty("DEV_APPOPEN_ID") + "\""
            )
            buildConfigField(
                "String",
                "ADMOB_APP_ID",
                "\"" + project.findProperty("DEV_ADMOB_APP_ID") + "\""
            )
        }
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
    buildFeatures {
        //noinspection DataBindingWithoutKapt
        dataBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation("com.google.firebase:firebase-config:21.6.0")
    implementation("com.google.firebase:firebase-analytics:21.5.0")
    implementation("com.google.firebase:firebase-perf-ktx:20.5.1")

 
    val nav_version = "2.7.5"

    //Ads
    implementation("com.google.android.gms:play-services-ads:22.6.0")

    //Hilt DI
    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:dagger-compiler:2.48")
    ksp("com.google.dagger:hilt-compiler:2.48")

    //Room
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    //Lifecycle ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //FlexBox (flexible recycle item)
    implementation("com.google.android.flexbox:flexbox:3.0.0")

    val lifecycle_version = "2.6.2"
    implementation("androidx.lifecycle:lifecycle-process:$lifecycle_version")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}