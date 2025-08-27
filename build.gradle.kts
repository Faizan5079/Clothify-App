
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Hilt Gradle plugin
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")

        // Google services plugin
        classpath("com.google.gms:google-services:4.4.0")

        // Safe Args
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.2")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}