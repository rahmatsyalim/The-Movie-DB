import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
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

class AndroidApplication : Plugin<Project> {
   override fun apply(target: Project) {
      with(target) {
         pluginManager.apply {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
         }
         extensions.configure<BaseAppModuleExtension> {
            compileOptions {
               sourceCompatibility = AndroidConfigs.javaCompileVersion
               targetCompatibility = AndroidConfigs.javaCompileVersion
            }

            kotlinOptions {
               jvmTarget = AndroidConfigs.javaCompileVersion.toString()
            }
         }
         val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
         dependencies {
            // logging
            add("implementation", libs.findLibrary("timber").get())
         }
      }
   }
}