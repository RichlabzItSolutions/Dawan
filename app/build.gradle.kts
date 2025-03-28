plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.dawan"
    compileSdk = 34


  /*  signingConfigs {
        create("release") {
            storeFile = file("D:\\dawan.jks")
            storePassword = "dawan@123"
            keyPassword = "dawan@123"
            keyAlias = "dawan"
        }
    }*/

    defaultConfig {
        applicationId = "com.dawan"
        minSdk = 26
        targetSdk = 34
        versionCode = 9
        versionName = "1.9"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
       // signingConfig = signingConfigs.getByName("release")
    }

    buildTypes {
          release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

       /* getByName("debug") {
            versionNameSuffix = ".rel"
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
        }
        create("production") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }*/


    }


 /*   applicationVariants.all {
        // this method is used to rename your all apk whether
        // it may be signed or unsigned(debug apk)
        outputs.all {
            // on below line we are setting a
            // name to our apk as TFSPlay.apk
            val output = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            val variant = this@all
            val name = "TFSPlay_(${variant.versionName}).apk"
            // on below line we are setting the
            // outputFileName to our apk file.
            output.outputFileName = name
        }
    }*/


    buildFeatures {
        viewBinding=true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-messaging:23.0.6")
    implementation("androidx.activity:activity:1.8.0")
    implementation("com.google.firebase:firebase-crashlytics:18.6.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //ssp sdp
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    implementation ("com.intuit.ssp:ssp-android:1.1.0")

    //retrofit

    implementation ("com.squareup.retrofit2:retrofit:2.7.2")
    implementation ("com.squareup.retrofit2:converter-gson:2.7.2")
    implementation ("com.squareup.okhttp3:okhttp:3.12.1")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation ("androidx.core:core-splashscreen:1.0.1")

    implementation ("com.google.android.gms:play-services-tasks:18.0.2")

    implementation ("org.jsoup:jsoup:1.14.3")


}