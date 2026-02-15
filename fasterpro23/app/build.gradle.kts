plugins {
    id("com.android.application")
    kotlin("android") version "1.8.10"
    alias(libs.plugins.google.gms.google.services) // Firebase Google Services plugin
}

android {
    compileSdk = 34 // সর্বোচ্চ SDK ভার্সন

    defaultConfig {
        applicationId = "com.example.fasterpro11"
        minSdk = 23  // সর্বনিম্ন SDK ভার্সন
        targetSdk = 34  // নতুন API ভার্সন
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

    // ✅ ✅ ✅ **সমস্যা সমাধানের জন্য Packaging Options**
    packagingOptions {
        exclude("META-INF/NOTICE.md")
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE-notice.md")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/mimetypes.default")
        exclude("META-INF/mailcap.default") // ⚡ নতুন ফাইল exclude করা হয়েছে
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    // ✅ AndroidX লাইব্রেরি - backward compatibility নিশ্চিত করার জন্য
    implementation("androidx.appcompat:appcompat:1.6.1") // ActionBar, Material Design কম্পোনেন্ট
    implementation("com.google.android.material:material:1.11.0") // Material Design ফিচার
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // ConstraintLayout
    implementation("androidx.multidex:multidex:2.0.1") // MultiDex সাপোর্ট

    // ✅ Activity এবং Fragment জন্য Kotlin Extensions (Backward compatibility)
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.fragment:fragment-ktx:1.5.7")

    // ✅ Lifecycle লাইব্রেরি (ViewModel, LiveData, etc.)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    // ✅ WorkManager (ব্যাকগ্রাউন্ড টাস্ক ব্যবস্থাপনা)
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    // ✅ OkHttp লাইব্রেরি (Networking)
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    // ✅ JavaMail API (Updated for Android 11+)
    implementation("com.sun.mail:android-mail:1.6.7") {
        exclude(group = "javax.activation", module = "activation")
    }
    implementation("com.sun.mail:android-activation:1.6.7") {
        exclude(group = "javax.activation", module = "activation")
    }
    implementation("org.eclipse.angus:angus-activation:2.0.1")

    // ✅ Firebase BOM (Bill of Materials) - আপডেটেড সংস্করণ
    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-functions")
    implementation("com.google.android.gms:play-services-location:21.2.0")
    implementation(libs.ui.android) // Play Services Location

    // ✅ Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
