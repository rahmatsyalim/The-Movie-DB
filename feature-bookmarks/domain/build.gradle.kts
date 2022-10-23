plugins {
   id("themoviedb.kotlin.library")
}

dependencies {
   implementation(projects.core.common)

   implementation(libs.javax.inject)
   implementation(libs.kotlinx.coroutines.core)
}