plugins {
   id("themoviedb.android.feature")
}

dependencies {
   implementation(projects.featureMovies.domain)
   implementation(projects.featureMovies.data)
   implementation(projects.library.youtubeplayer)

   implementation(libs.androidx.paging.runtime.ktx)
   implementation(libs.androidx.recyclerview)

}