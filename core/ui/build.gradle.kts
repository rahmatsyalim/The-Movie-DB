plugins {
   id("themoviedb.android.library")
   kotlin("kapt")
}

android {
   buildFeatures {
      viewBinding = true
   }
}

dependencies {
   implementation(projects.core.common)
   implementation(projects.library.customviews)

   implementation(libs.androidx.core.ktx)
   implementation(libs.androidx.appcompat)
   implementation(libs.material)
   implementation(libs.androidx.recyclerview)
   implementation(libs.androidx.swiperefreshlayout)
   implementation(libs.androidx.paging.runtime.ktx)
   implementation(libs.facebook.shimmer)
   implementation(libs.coil.base)
   implementation(libs.kotlinx.coroutines.core)
   implementation(libs.hilt.android)
   kapt(libs.hilt.android.compiler)

}