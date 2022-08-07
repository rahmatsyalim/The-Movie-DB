plugins {
   id("com.android.application")
   kotlin("android")
   kotlin("kapt")
   id("dagger.hilt.android.plugin")
   id("androidx.navigation.safeargs.kotlin")
   id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
   compileSdk = AppConfig.compileSdk

   defaultConfig {
      applicationId = AppConfig.applicationId
      minSdk = AppConfig.minSdk
      targetSdk = AppConfig.targetSdk
      versionCode = AppConfig.versionCode
      versionName = AppConfig.versionName

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
   implementation(Libs.materialDesign)
   implementation(Libs.constraintLayout)
   implementation(Libs.navigationFragment)
   implementation(Libs.navigationUi)
   implementation(Libs.swipeRefreshLayout)
   implementation(Libs.recyclerView)
   implementation(Libs.paging3)
   implementation(Libs.viewPager2)
   implementation(Libs.coroutinesCore)
   implementation(Libs.coroutinesAndroid)
   implementation(Libs.lifecycleViewModel)
   implementation(Libs.lifecycleLiveData)
   implementation(Libs.hiltAndroid)
   implementation(Libs.hiltNavigationFragment)
   kapt(Libs.hiltCompiler)
   implementation(Libs.room)
   implementation(Libs.roomKtx)
   kapt(Libs.roomCompiler)
   implementation(Libs.retrofit)
   implementation(Libs.gson)
   implementation(Libs.okhttp)
   implementation(Libs.okhttpLogging)
   implementation(Libs.glide)
   kapt(Libs.glideCompiler)
   implementation(Libs.facebookShimmer)
   implementation(Libs.timber)

   testImplementation(TestLibs.junit)
   testImplementation(TestLibs.truth)
   testImplementation(TestLibs.archCore)
   testImplementation(TestLibs.coroutines)
   testImplementation(TestLibs.mockito)

   androidTestImplementation(TestLibs.extJunit)
   androidTestImplementation(TestLibs.espresso)
   androidTestImplementation(TestLibs.truth)
   androidTestImplementation(TestLibs.archCore)
   androidTestImplementation(TestLibs.coroutines)
   androidTestImplementation(TestLibs.mockito)
   androidTestImplementation(TestLibs.hilt)
   kaptAndroidTest(Libs.hiltCompiler)

}