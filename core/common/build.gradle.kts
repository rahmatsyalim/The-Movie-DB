plugins {
   id("themoviedb.android.library")
   id("dagger.hilt.android.plugin")
   kotlin("kapt")
}

dependencies {
   implementation("androidx.annotation:annotation:1.4.0")
   implementation(libs.kotlinx.coroutines.android)
   implementation(libs.hilt.android)
   kapt(libs.hilt.android.compiler)

   testImplementation(libs.junit4)
   testImplementation(libs.truth)
}