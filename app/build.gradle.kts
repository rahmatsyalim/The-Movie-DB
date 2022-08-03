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

}

dependencies {

   implementation(Dependencies.Libs.coreKtx)
   implementation(Dependencies.Libs.appcompat)
   implementation(Dependencies.Libs.materialDesign)
   implementation(Dependencies.Libs.constraintLayout)
   implementation(Dependencies.Libs.navigationFragment)
   implementation(Dependencies.Libs.navigationUi)
   implementation(Dependencies.Libs.swipeRefreshLayout)
   implementation(Dependencies.Libs.recyclerView)
   implementation(Dependencies.Libs.paging3)
   implementation(Dependencies.Libs.viewPager2)
   implementation(Dependencies.Libs.coroutinesCore)
   implementation(Dependencies.Libs.coroutinesAndroid)
   implementation(Dependencies.Libs.lifecycleViewModel)
   implementation(Dependencies.Libs.lifecycleLiveData)
   implementation(Dependencies.Libs.hiltAndroid)
   implementation(Dependencies.Libs.hiltNavigationFragment)
   kapt(Dependencies.Libs.hiltCompiler)
   implementation(Dependencies.Libs.room)
   implementation(Dependencies.Libs.roomKtx)
   kapt(Dependencies.Libs.roomCompiler)
   implementation(Dependencies.Libs.retrofit)
   implementation(Dependencies.Libs.gson)
   implementation(Dependencies.Libs.okhttp)
   implementation(Dependencies.Libs.okhttpLogging)
   implementation(Dependencies.Libs.glide)
   kapt(Dependencies.Libs.glideCompiler)
   implementation(Dependencies.Libs.facebookShimmer)
   implementation(Dependencies.Libs.timber)

   testImplementation(Dependencies.TestLibs.junit)
   testImplementation(Dependencies.TestLibs.truth)
   testImplementation(Dependencies.TestLibs.archCore)
   testImplementation(Dependencies.TestLibs.coroutines)
   testImplementation(Dependencies.TestLibs.mockito)

   androidTestImplementation(Dependencies.TestLibs.extJunit)
   androidTestImplementation(Dependencies.TestLibs.espresso)
   androidTestImplementation(Dependencies.TestLibs.truth)
   androidTestImplementation(Dependencies.TestLibs.archCore)
   androidTestImplementation(Dependencies.TestLibs.coroutines)
   androidTestImplementation(Dependencies.TestLibs.mockito)
   androidTestImplementation(Dependencies.TestLibs.hilt)
   kaptAndroidTest(Dependencies.Libs.hiltCompiler)

}