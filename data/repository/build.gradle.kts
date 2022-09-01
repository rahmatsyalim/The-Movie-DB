plugins {
   id("themoviedb.android.library")
   id("dagger.hilt.android.plugin")
   kotlin("kapt")
}



dependencies {
   implementation(projects.core.common)
   implementation(projects.data.local)
   implementation(projects.data.remote)
   implementation(projects.data.connectivity)
   implementation(projects.domain.movie)
   implementation(projects.domain.connectivity)

   implementation(libs.androidx.paging.common.ktx)
   implementation(libs.kotlinx.coroutines.core)
   implementation(libs.hilt.android)
   kapt(libs.hilt.android.compiler)
   implementation(libs.retrofit2.retrofit)
}