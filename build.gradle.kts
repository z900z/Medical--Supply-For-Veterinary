// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}

buildscript {
    val agp_version by extra("8.1.0")
    val agp_version1 by extra("8.1.2")
    repositories {
        google()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.5")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.40.1")
        classpath ("com.google.gms:google-services:4.3.13")
    }
}
