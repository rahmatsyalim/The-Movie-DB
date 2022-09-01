import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Created by Rahmat Syalim on 2022/08/31
 * rahmatsyalim@gmail.com
 */

class AndroidFeature : Plugin<Project> {
   override fun apply(target: Project) {
      with(target) {
         pluginManager.apply {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
            apply("org.jetbrains.kotlin.kapt")
         }
         extensions.configure<LibraryExtension> {
            configureAndroid()
            configureProguard()
            defaultConfig {
               targetSdk = AndroidConfigs.targetSdk
            }
            buildFeatures {
               viewBinding = true
            }
         }
         val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
         dependencies {
            add("implementation", project(":core:common"))
            // logging
            add("implementation", libs.findLibrary("timber").get())
         }
      }
   }
}