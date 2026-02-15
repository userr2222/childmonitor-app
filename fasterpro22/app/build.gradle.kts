plugins {
    id("com.android.application")
    kotlin("android") version "1.8.10"
   // alias(libs.plugins.google.gms.google.services) // Kotlin plugin version
    id ("com.google.gms.google-services")
}

android {
    compileSdk = 34 // সর্বোচ্চ SDK

    defaultConfig {
        applicationId = "com.example.fasterpro11"
        minSdk = 14  // minSdkVersion 14 রাখা হয়েছে (পুরানো SDK ভার্সনের জন্য)
        targetSdk = 34  // নতুন SDK ভার্সন (API 34)
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    namespace = "com.example.fasterpro11"

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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
        viewBinding = true // ViewBinding ব্যবহার করা
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    // AndroidX লাইব্রেরি - backward compatibility নিশ্চিত করার জন্য
    implementation("androidx.appcompat:appcompat:1.6.1") // ActionBar, Material Design কম্পোনেন্ট
    implementation("com.google.android.material:material:1.11.0") // Material Design ফিচার
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // ConstraintLayout
    implementation("androidx.multidex:multidex:2.0.1") // MultiDex সাপোর্ট

    // Activity and Fragment জন্য Kotlin Extensions (Backward compatibility)
    implementation("androidx.activity:activity-ktx:1.6.1")
    implementation("androidx.fragment:fragment-ktx:1.5.5")

    // Lifecycle libraries (ViewModel, LiveData, etc.)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    // WorkManager for background tasks (Backward compatibility)
    implementation("androidx.work:work-runtime:2.8.0")

    // OkHttp library (Networking)
    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    // JavaMail dependencies (Email functionality)
    implementation("com.sun.mail:android-mail:1.6.2") {
        exclude(group = "javax.activation", module = "activation")
    }
    implementation("com.sun.mail:android-activation:1.6.2") {
        exclude(group = "javax.activation", module = "activation")
    }
    implementation(libs.firebase.database)

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))
    implementation ("com.google.firebase:firebase-analytics")
}
