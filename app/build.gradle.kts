plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    id ("kotlin-parcelize")
    id ("androidx.navigation.safeargs.kotlin")
    id ("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.ali.clothsapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ali.clothsapp"
        minSdk = 23
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

buildFeatures {
    viewBinding =true
}
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
   // implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Navigation component
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")

    //loading button
    implementation("com.github.razir.progressbutton:progressbutton:2.1.0")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.13.0")

    //circular image
    implementation ("de.hdodenhof:circleimageview:3.1.0")

   //ViewPagerIndicator
   // implementation("com.github.vejei:ViewPagerIndicator:1.0.0-alpha.1")
    implementation("com.tbuonomo:dotsindicator:4.3")


    //stepView
    //implementation (":StepView")
    //implementation ("com.shuhart.stepview:stepview:1.5.1")

    //Dagger hilt
    implementation ("com.google.dagger:hilt-android:2.46.1")
    kapt ("com.google.dagger:hilt-compiler:2.46.1")


    //firebase
    //implementation("com.google.firebase:firebase-auth:22.3.1")
    //implementation("com.google.firebase:firebase-firestore:24.10.1")
   // implementation("com.google.firebase:firebase-database:20.3.1")


    // ✅ Firebase BoM (centralized version management)
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))

    // Firebase dependencies (no versions needed)
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-database")

}


