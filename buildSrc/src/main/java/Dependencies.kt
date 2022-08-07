/**
 * Created by Rahmat Syalim on 2022/08/02
 * rahmatsyalim@gmail.com
 */

object Versions {
   const val agp = "7.2.2"
   const val kotlin = "1.7.10"

   const val benManesVersions = "0.42.0"

   const val coreKtx = "1.8.0"
   const val secretsGradle = "2.0.1"
   const val appcompat = "1.4.2"
   const val materialDesign = "1.6.1"
   const val constraintLayout = "2.1.4"
   const val navigation = "2.5.1"
   const val swipeRefreshLayout = "1.1.0"
   const val recyclerView = "1.2.1"
   const val viewPager2 = "1.0.0"
   const val paging3 = "3.1.1"
   const val lifecycle = "2.5.1"
   const val coroutines = "1.6.4"
   const val daggerHilt = "2.43.2"
   const val hiltNavigationFragment = "1.0.0"
   const val room = "2.4.3"
   const val retrofit = "2.9.0"
   const val okhttp = "4.10.0"
   const val glide = "4.13.2"
   const val facebookShimmer = "0.5.0"
   const val timber = "5.0.1"

   const val junit = "4.13.2"
   const val extJunit = "1.1.3"
   const val espresso = "3.4.0"
   const val truth = "1.1.3"
   const val archCore = "2.1.0"
   const val mockito = "4.6.1"
}

object BuildPlugins {
   // android
   val agpApplication by lazy { "com.android.application" }
   val agpLibrary by lazy { "com.android.library" }

   // kotlin
   val kotlinAndroid by lazy { "org.jetbrains.kotlin.android" }

   // versions checker
   val benManesVersions by lazy { "com.github.ben-manes.versions" }
}

object BuildClasspath {
   // Navigation safe args
   val navigationSafeArgs by lazy { "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}" }

   // dagger-hilt
   val daggerHilt by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHilt}" }

   // secrets gradle
   val secretsGradle by lazy {
      "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${Versions.secretsGradle}"
   }

}

object Libs {
   val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
   val appcompat by lazy { "androidx.appcompat:appcompat:${Versions.appcompat}" }
   val materialDesign by lazy { "com.google.android.material:material:${Versions.materialDesign}" }
   val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }

   // Navigation
   val navigationFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}" }
   val navigationUi by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navigation}" }

   // Swipe refresh layout
   val swipeRefreshLayout by lazy { "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}" }

   // Recyclerview
   val recyclerView by lazy { "androidx.recyclerview:recyclerview:${Versions.recyclerView}" }

   // Paging 3
   val paging3 by lazy { "androidx.paging:paging-runtime:${Versions.paging3}" }

   // ViewPager 2
   val viewPager2 by lazy { "androidx.viewpager2:viewpager2:${Versions.viewPager2}" }

   // Coroutines
   val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}" }
   val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}" }

   // lifecycle
   val lifecycleViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}" }
   val lifecycleLiveData by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}" }

   // Hilt
   val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.daggerHilt}" }
   val hiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}" }
   val hiltNavigationFragment by lazy { "androidx.hilt:hilt-navigation-fragment:${Versions.hiltNavigationFragment}" }

   // Room
   val room by lazy { "androidx.room:room-runtime:${Versions.room}" }
   val roomKtx by lazy { "androidx.room:room-ktx:${Versions.room}" }
   val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.room}" }

   // Retrofit
   val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }

   // Gson
   val gson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }

   // Okhttp
   val okhttp by lazy { "com.squareup.okhttp3:okhttp:${Versions.okhttp}" }
   val okhttpLogging by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}" }

   // Glide
   val glide by lazy { "com.github.bumptech.glide:glide:${Versions.glide}" }
   val glideCompiler by lazy { "com.github.bumptech.glide:compiler:${Versions.glide}" }

   // Facebook Shimmer
   val facebookShimmer by lazy { "com.facebook.shimmer:shimmer:${Versions.facebookShimmer}" }

   // Timber
   val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }
}

object TestLibs {
   // Unit
   val junit by lazy { "junit:junit:${Versions.junit}" }

   // Integration
   val extJunit by lazy { "androidx.test.ext:junit:${Versions.extJunit}" }
   val espresso by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }

   // Assertions helper
   val truth by lazy { "com.google.truth:truth:${Versions.truth}" }

   // Arch core for livedata
   val archCore by lazy { "androidx.arch.core:core-testing:${Versions.archCore}" }

   // Coroutines
   val coroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}" }

   // Mockito
   val mockito by lazy { "org.mockito:mockito-core:${Versions.mockito}" }

   // Hilt
   val hilt by lazy { "com.google.dagger:hilt-android-testing:${Versions.daggerHilt}" }
}