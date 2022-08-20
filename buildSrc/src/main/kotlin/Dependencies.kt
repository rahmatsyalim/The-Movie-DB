/**
 * Created by Rahmat Syalim on 2022/08/02
 * rahmatsyalim@gmail.com
 */

object Versions {
   // Android Gradle Plugin
   const val android = "7.2.2"

   // Kotlin
   const val kotlin = "1.7.10"

   // Ben Manes Versions
   const val benManes = "0.42.0"

   // Secrets
   const val secrets = "2.0.1"

   // Core Ktx
   const val coreKtx = "1.8.0"

   // Appcompat
   const val appcompat = "1.5.0"

   // Constraint Layout
   const val constraintLayout = "2.1.4"

   // Coordinator Layout
   const val coordinatorLayout = "1.2.0"

   // Material
   const val material = "1.6.1"

   // Swipe Refresh Layout
   const val swipeRefreshLayout = "1.1.0"

   // Recycler View
   const val recyclerView = "1.2.1"

   // Paging 3
   const val paging3 = "3.1.1"

   // View Pager 2
   const val viewPager2 = "1.0.0"

   // Work Manager
   const val workManager = "2.7.1"

   // Navigation
   const val navigation = "2.5.1"

   // Lifecycle
   const val lifecycle = "2.5.1"

   // Coroutines
   const val coroutines = "1.6.4"

   // Dagger-hilt
   const val daggerHilt = "2.43.2"

   // Hilt Navigation Fragment
   const val hiltNavigationFragment = "1.0.0"

   // hilt Work
   const val hiltWork = "1.0.0"

   // Room
   const val room = "2.4.3"

   // Retrofit 2
   const val retrofit2 = "2.9.0"

   // Okhttp 3
   const val okhttp3 = "4.10.0"

   // Glide
   const val glide = "4.13.2"

   // Coil
   const val coil = "2.1.0"

   // Facebook Shimmer
   const val facebookShimmer = "0.5.0"

   // Timber
   const val timber = "5.0.1"

   // JUnit
   const val junit = "4.13.2"

   // Ext JUnit
   const val extJunit = "1.1.3"

   // Espresso
   const val espresso = "3.4.0"

   // Truth
   const val truth = "1.1.3"

   // Arch Core
   const val archCore = "2.1.0"

   // Mockito
   const val mockito = "4.6.1"
}

object Libs {
   // Android Gradle Plugin
   object Android {
      const val application = "com.android.application"
      const val library = "com.android.library"
   }

   // Kotlin
   object Kotlin {
      const val android = "org.jetbrains.kotlin.android"
      const val jvm = "org.jetbrains.kotlin.jvm"
   }

   // Ben Manes Versions
   const val benManesVersions = "com.github.ben-manes.versions"

   // Secrets
   const val secretsGradlePlugin =
      "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${Versions.secrets}"

   // Core Ktx
   const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"

   // Appcompat
   const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"

   // Constraint Layout
   const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

   // Coordinator Layout
   const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:${Versions.coordinatorLayout}"

   // Material
   const val material = "com.google.android.material:material:${Versions.material}"

   // Swipe Refresh Layout
   const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"

   // Recycler View
   const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"

   // View Pager 2
   const val viewPager2 = "androidx.viewpager2:viewpager2:${Versions.viewPager2}"

   // Work Manager
   const val workManager = "androidx.work:work-runtime-ktx:${Versions.workManager}"

   // Navigation
   object Navigation {
      const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
      const val uiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
      const val safeArgsGradlePlugin =
         "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
   }

   // Lifecycle
   object Lifecycle {
      const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
      const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
   }

   // Paging 3
   object Paging3 {
      const val runtime = "androidx.paging:paging-runtime-ktx:${Versions.paging3}"
      const val common = "androidx.paging:paging-common-ktx:${Versions.paging3}"
   }

   // Coroutines
   object Coroutines {
      const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
      const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
   }

   // Dagger-hilt
   object DaggerHilt {
      const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHilt}"
      const val hilt = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
      const val compiler = "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
   }

   // Hilt Navigation Fragment
   const val hiltNavigationFragment = "androidx.hilt:hilt-navigation-fragment:${Versions.hiltNavigationFragment}"

   // Hilt Work
   const val hiltWork = "androidx.hilt:hilt-work:${Versions.hiltWork}"

   // Room
   object Room {
      const val runtime = "androidx.room:room-runtime:${Versions.room}"
      const val ktx = "androidx.room:room-ktx:${Versions.room}"
      const val compiler = "androidx.room:room-compiler:${Versions.room}"
   }

   // Retrofit2
   object Retrofit2 {
      const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
      const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"
   }

   // Okhttp3
   object Okhttp3 {
      const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
      const val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"
   }

   // Glide
   object Glide {
      const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
      const val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
   }

   // Coil
   object Coil {
      const val coil = "io.coil-kt:coil:${Versions.coil}"
      const val coilBase = "io.coil-kt:coil-base:${Versions.coil}"
   }

   // Facebook Shimmer
   const val facebookShimmer = "com.facebook.shimmer:shimmer:${Versions.facebookShimmer}"

   // Timber
   const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

   object Test {
      // JUnit
      const val junit = "junit:junit:${Versions.junit}"

      // Ext JUnit
      const val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"

      // Espresso
      const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

      // Truth
      const val truth = "com.google.truth:truth:${Versions.truth}"

      // Arch Core
      const val archCore = "androidx.arch.core:core-testing:${Versions.archCore}"

      // Coroutines
      const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

      // Mockito
      const val mockito = "org.mockito:mockito-core:${Versions.mockito}"

      // Hilt
      const val hilt = "com.google.dagger:hilt-android-testing:${Versions.daggerHilt}"
   }
}