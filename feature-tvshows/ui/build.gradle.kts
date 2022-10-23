plugins {
   id("themoviedb.android.feature")
}

dependencies {
   implementation(projects.featureTvshows.domain)
   implementation(projects.featureTvshows.data)

   implementation(libs.androidx.paging.runtime.ktx)
   implementation(libs.androidx.recyclerview)
}