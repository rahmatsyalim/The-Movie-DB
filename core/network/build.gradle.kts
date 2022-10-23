plugins {
   id("themoviedb.android.library")
   id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
   kotlin("kapt")
}

secrets {
   defaultPropertiesFileName = "secrets.properties"
}

dependencies {
   implementation(projects.core.common)
   implementation(libs.kotlinx.coroutines.core)
   implementation(libs.hilt.android)
   kapt(libs.hilt.android.compiler)
   implementation(libs.retrofit2.retrofit)
   implementation(libs.retrofit2.gson)
   implementation(libs.okhttp3.okhttp)
   implementation(libs.okhttp3.logging)
}