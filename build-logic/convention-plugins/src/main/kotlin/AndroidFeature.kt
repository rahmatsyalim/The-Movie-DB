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
            apply("dagger.hilt.android.plugin")
            apply("androidx.navigation.safeargs.kotlin")
            apply("org.jetbrains.kotlin.kapt")
         }
         extensions.configure<LibraryExtension> {
            configureAndroid()
            defaultConfig.apply {
               targetSdk = AndroidConfigs.targetSdk
               testInstrumentationRunner = AndroidConfigs.androidJunitRunner
            }
            configureProguard()

            buildFeatures {
               viewBinding = true
            }
         }
         val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

         dependencies {
            add("implementation", project(":core:ui"))
            add("implementation", project(":core:common"))
            add("implementation", project(":core:navigation"))
            add("implementation", project(":library:customviews"))

            add("implementation", libs.findLibrary("androidx.core.ktx").get())
            add("implementation", libs.findLibrary("androidx.appcompat").get())
            add("implementation", libs.findLibrary("material").get())
            add("implementation", libs.findLibrary("androidx.swiperefreshlayout").get())
            add("implementation", libs.findLibrary("androidx.navigation.fragment.ktx").get())
            add("implementation", libs.findLibrary("androidx.navigation.ui.ktx").get())
            add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.ktx").get())
            add("implementation", libs.findLibrary("androidx.lifecycle.livedata.ktx").get())
            add("implementation", libs.findLibrary("facebook.shimmer").get())

            add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
            add("implementation", libs.findLibrary("hilt.android").get())
            add("kapt", libs.findLibrary("hilt.android.compiler").get())
            // logging
            add("implementation", libs.findLibrary("timber").get())

            // test
            add("testImplementation", libs.findLibrary("junit4").get())
            add("androidTestImplementation", libs.findLibrary("androidx.test.ext").get())
            add("androidTestImplementation", libs.findLibrary("androidx.test.espresso.core").get())
         }
      }
   }
}