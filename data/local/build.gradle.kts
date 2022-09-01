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
   implementation(libs.room.runtime)
   implementation(libs.room.ktx)
   kapt(libs.room.compiler)
   implementation(libs.retrofit2.gson)
}