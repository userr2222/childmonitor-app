plugins {
    id("com.android.application")
}

android {
    compileSdkVersion(34)

    defaultConfig {
        multiDexEnabled = true // Use '=' instead of 'true' as an argument
        applicationId = "com.example.fasterpro10"
        minSdkVersion(23)
        targetSdkVersion(34)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Set the namespace as a standalone property
    ext {
        namespace = "com.example.fasterpro10"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

buildscript {
    repositories {
        google() // Add Google Maven repository
        // other repositories if needed
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.4.0")
        // Other dependencies may be listed here
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Add JavaMail API dependency
   // implementation("javax.mail:javax.mail-api:1.6.2")
    implementation("com.sun.mail:android-mail:1.6.2")
    implementation("com.sun.mail:android-activation:1.6.2")
    // MultiDex support
    implementation("androidx.multidex:multidex:2.0.1")

}
