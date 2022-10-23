plugins {
   id("themoviedb.android.application")
   id("dagger.hilt.android.plugin")
   id("androidx.navigation.safeargs.kotlin")
   kotlin("kapt")
}

android {
   compileSdk = AndroidConfigs.compileSdk
   defaultConfig {
      applicationId = AndroidConfigs.applicationId
      minSdk = AndroidConfigs.minSdk
      targetSdk = AndroidConfigs.targetSdk
      versionCode = AndroidConfigs.versionCode
      versionName = AndroidConfigs.versionName
      testInstrumentationRunner = AndroidConfigs.androidJunitRunner
      vectorDrawables {
         useSupportLibrary = true
      }
   }

   buildTypes {
      getByName("release") {
         isMinifyEnabled = true
         proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      }

      getByName("debug") {
         applicationIdSuffix = ".debug"
      }

      create("pre-release") {
         applicationIdSuffix = ".pre_release"
         isMinifyEnabled = true
         isDebuggable = false
         signingConfig = signingConfigs.getByName("debug")
         proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      }
   }

   buildFeatures {
      viewBinding = true
   }

   hilt {
      enableAggregatingTask = true
   }

}

dependencies {
   implementation(projects.core.ui)
   implementation(projects.core.common)
   implementation(projects.core.navigation)
   implementation(projects.featureMovies.ui)
   implementation(projects.featureTvshows.ui)
   implementation(projects.featureBookmarks.ui)
   implementation(projects.framework.connectivity)

   implementation(libs.androidx.core.ktx)
   implementation(libs.androidx.appcompat)
   implementation(libs.material)
   implementation(libs.androidx.swiperefreshlayout)
   implementation(libs.androidx.recyclerview)
   implementation(libs.androidx.viewpager2)
   implementation(libs.androidx.navigation.fragment.ktx)
   implementation(libs.androidx.navigation.ui.ktx)
   implementation(libs.androidx.paging.runtime.ktx)
   implementation(libs.androidx.lifecycle.viewmodel.ktx)
   implementation(libs.androidx.lifecycle.livedata.ktx)
   implementation(libs.kotlinx.coroutines.android)
   implementation(libs.hilt.android)
   kapt(libs.hilt.android.compiler)
   implementation(libs.hilt.ext.navigation)
   implementation(libs.facebook.shimmer)
   // logging
   implementation(libs.timber)
   // test
   testImplementation(libs.junit4)
   androidTestImplementation(libs.androidx.test.ext)
   androidTestImplementation(libs.androidx.test.espresso.core)

}