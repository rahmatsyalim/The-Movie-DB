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

class AndroidLibrary : Plugin<Project> {
   override fun apply(target: Project) {
      with(target) {
         pluginManager.apply {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
         }
         extensions.configure<LibraryExtension> {
            configureAndroid()
            defaultConfig.targetSdk = AndroidConfigs.targetSdk
            configureProguard()
         }
         val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
         dependencies {
            // logging
            add("implementation", libs.findLibrary("timber").get())

            // test
            add("testImplementation", libs.findLibrary("junit4").get())
            add("androidTestImplementation", libs.findLibrary("androidx.test.ext").get())
         }
      }
   }
}