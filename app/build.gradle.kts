plugins {
   id("com.android.application")
   kotlin("android")
   kotlin("kapt")
   id("dagger.hilt.android.plugin")
   id("androidx.navigation.safeargs.kotlin")
   id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
   compileSdk = 32

   defaultConfig {
      applicationId = "com.syalim.themoviedb"
      minSdk = 21
      targetSdk = 32
      versionCode = 1
      versionName = "1.0.0"

      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
      vectorDrawables {
         useSupportLibrary = true
      }
   }

   buildTypes {
      getByName("release") {
         isMinifyEnabled = true
         isShrinkResources = true
         proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
         buildConfigField("boolean", "IS_RELEASE","true")
      }
      getByName("debug") {
         applicationIdSuffix = ".debug"
         buildConfigField("boolean","IS_RELEASE", "false")
      }
   }

   compileOptions {
      sourceCompatibility = JavaVersion.VERSION_11
      targetCompatibility = JavaVersion.VERSION_11
   }

   kotlinOptions {
      jvmTarget = JavaVersion.VERSION_11.toString()
   }

   buildFeatures {
      viewBinding = true
   }

   android.sourceSets.all {
      java.srcDir("src/$name/kotlin")
   }

}

dependencies {

   implementation(Libs.coreKtx)
   implementation(Libs.appcompat)
   implementation(Libs.constraintLayout)
   implementation(Libs.material)
   implementation(Libs.swipeRefreshLayout)
   implementation(Libs.recyclerView)
   implementation(Libs.paging3)
   implementation(Libs.viewPager2)
   implementation(Libs.Navigation.fragmentKtx)
   implementation(Libs.Navigation.uiKtx)
   implementation(Libs.Lifecycle.viewModel)
   implementation(Libs.Lifecycle.liveData)
   implementation(Libs.Coroutines.core)
   implementation(Libs.Coroutines.android)
   implementation(Libs.DaggerHilt.hilt)
   implementation(Libs.hiltNavigationFragment)
   kapt(Libs.DaggerHilt.compiler)
   implementation(Libs.Room.runtime)
   implementation(Libs.Room.ktx)
   kapt(Libs.Room.compiler)
   implementation(Libs.Retrofit2.retrofit)
   implementation(Libs.Retrofit2.gson)
   implementation(Libs.Okhttp3.okhttp)
   implementation(Libs.Okhttp3.logging)
   implementation(Libs.Glide.glide)
   kapt(Libs.Glide.compiler)
   implementation(Libs.facebookShimmer)
   implementation(Libs.timber)

   testImplementation(Libs.Test.junit)
   testImplementation(Libs.Test.truth)
   testImplementation(Libs.Test.archCore)
   testImplementation(Libs.Test.coroutines)
   testImplementation(Libs.Test.mockito)
   androidTestImplementation(Libs.Test.extJunit)
   androidTestImplementation(Libs.Test.espresso)
   androidTestImplementation(Libs.Test.truth)
   androidTestImplementation(Libs.Test.archCore)
   androidTestImplementation(Libs.Test.coroutines)
   androidTestImplementation(Libs.Test.mockito)
   androidTestImplementation(Libs.Test.hilt)
   kaptAndroidTest(Libs.DaggerHilt.compiler)

}