plugins {
   id("themoviedb.android.library")
   kotlin("kapt")
}

dependencies {
   implementation(projects.core.common)
   implementation(projects.core.network)
   implementation(projects.core.database)
   implementation(projects.featureMovies.domain)

   implementation(libs.androidx.paging.common.ktx)
   implementation(libs.kotlinx.coroutines.core)
   implementation(libs.hilt.android)
   kapt(libs.hilt.android.compiler)
   implementation(libs.retrofit2.retrofit)
}