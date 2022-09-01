plugins {
   id("themoviedb.android.library")
}

dependencies {
   implementation(projects.core.common)
   implementation(libs.javax.inject)
   implementation(libs.kotlinx.coroutines.core)
   implementation(libs.androidx.paging.common.ktx)
}