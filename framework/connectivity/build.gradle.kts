plugins {
   id("themoviedb.android.library")
   kotlin("kapt")
}

dependencies {
   implementation(projects.core.common)

   implementation(libs.kotlinx.coroutines.core)
   implementation(libs.hilt.android)
   kapt(libs.hilt.android.compiler)
}