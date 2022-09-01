plugins {
   id("themoviedb.android.library")
   id("dagger.hilt.android.plugin")
   id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
   kotlin("kapt")
}

secrets {
   defaultPropertiesFileName = "secrets.properties"
}

dependencies {
   implementation(libs.hilt.android)
   kapt(libs.hilt.android.compiler)
   implementation(libs.retrofit2.retrofit)
   implementation(libs.retrofit2.gson)
   implementation(libs.okhttp3.okhttp)
   implementation(libs.okhttp3.logging)
}