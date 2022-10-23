plugins {
   id("themoviedb.kotlin.library")
}

dependencies {
   implementation(projects.core.common)

   implementation(libs.androidx.paging.common.ktx)
   implementation(libs.kotlinx.coroutines.core)
   implementation(libs.javax.inject)
}