plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = "com.example.medicalsupply"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.medicalsupply"
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.annotation:annotation:1.7.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //ssp
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    //sdp
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth")
    // navigation component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Gson converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // gson
    implementation("com.google.code.gson:gson:2.10.1")
    // rx with retrofit
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    // rxAndroid
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    //circle image
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:2.6.1")
    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.6.1")
    // optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:2.6.1")
    // lottie
    implementation("com.airbnb.android:lottie:6.4.0")


    //loading button
    implementation("br.com.simplepass:loading-button-android:2.2.0")
    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    //viewpager2 indicatior
    implementation("io.github.vejei.viewpagerindicator:viewpagerindicator:1.0.0-alpha.1")
    //stepView
    implementation("com.shuhart.stepview:stepview:1.5.1")
    //Android Ktx
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    //Dagger hilt
    implementation("com.google.dagger:hilt-android:2.51")
    kapt("com.google.dagger:hilt-compiler:2.51")

    // Firebase Firestore
    implementation("com.google.firebase:firebase-firestore:24.10.3")
    // firebase storage
    implementation("com.google.firebase:firebase-storage:20.3.0")
    // Color picker
    implementation("com.github.skydoves:colorpickerview:2.3.0")
    // coroutines with firebase
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.0")

}