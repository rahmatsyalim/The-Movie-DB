import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Created by Rahmat Syalim on 2022/09/03
 * rahmatsyalim@gmail.com
 */

class KotlinLibrary : Plugin<Project> {

   override fun apply(target: Project) {
      with(target) {
         pluginManager.apply {
            apply("kotlin")
         }
         val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
         dependencies {

            // test
            add("testImplementation", libs.findLibrary("junit4").get())
         }
      }
   }
}