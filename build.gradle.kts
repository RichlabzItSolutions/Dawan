buildscript {
    val agp_version by extra("8.2.0")
    dependencies {
       // classpath("com.google.gms:google-services:4.3.13")
        classpath("com.google.gms:google-services:4.3.14")
        classpath("com.android.tools.build:gradle:$agp_version")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
}