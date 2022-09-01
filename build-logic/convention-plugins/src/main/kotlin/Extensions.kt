import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions


/**
 * Created by Rahmat Syalim on 2022/08/31
 * rahmatsyalim@gmail.com
 */

fun CommonExtension<*, *, *, *>.configureAndroid() {
   defaultConfig {
      compileSdk = AndroidConfigs.compileSdk
      minSdk = AndroidConfigs.minSdk
      testInstrumentationRunner = AndroidConfigs.androidJunitRunner
   }

   compileOptions {
      sourceCompatibility = AndroidConfigs.javaCompileVersion
      targetCompatibility = AndroidConfigs.javaCompileVersion
   }

   kotlinOptions {
      jvmTarget = AndroidConfigs.javaCompileVersion.toString()
   }

}


fun LibraryExtension.configureProguard() {
   buildTypes {
      getByName("release") {
         consumerProguardFiles("consumer-rules.pro")
      }

      create("pre-release") {
         signingConfig = signingConfigs.getByName("debug")
         consumerProguardFiles("consumer-rules.pro")
      }
   }
}

internal fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
   (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}