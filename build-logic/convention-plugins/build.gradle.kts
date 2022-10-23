plugins {
   `kotlin-dsl`
   `kotlin-dsl-precompiled-script-plugins`
}

java {
   sourceCompatibility = JavaVersion.VERSION_11
   targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
   compileOnly(libs.android.gradle.plugin)
   compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
   plugins {
      register("androidLibrary") {
         id = "themoviedb.android.library"
         implementationClass = "AndroidLibrary"
      }
      register("androidApplication") {
         id = "themoviedb.android.application"
         implementationClass = "AndroidApplication"
      }
      register("androidFeature") {
         id = "themoviedb.android.feature"
         implementationClass = "AndroidFeature"
      }
      register("kotlinLibrary") {
         id = "themoviedb.kotlin.library"
         implementationClass = "KotlinLibrary"
      }
   }
}