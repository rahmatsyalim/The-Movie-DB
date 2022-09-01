import org.gradle.api.JavaVersion

/**
 * Created by Rahmat Syalim on 2022/08/31
 * rahmatsyalim@gmail.com
 */

object AndroidConfigs {
   private const val versionMajor = 1
   private const val versionMinor = 0
   private const val versionPatch = 0
   private const val versionQualifier = "alpha1"

   private val generatedVersionCode by lazy { versionMajor * 10000 + versionMinor * 100 * versionPatch }
   private val generatedVersionName by lazy { "$versionMajor.$versionMinor.$versionPatch" }

   const val compileSdk = 32
   const val applicationId = "com.syalim.themoviedb"
   const val minSdk = 21
   const val targetSdk = 32
   val versionCode = generatedVersionCode
   val versionName = generatedVersionName
   const val androidJunitRunner = "androidx.test.runner.AndroidJUnitRunner"

   val javaCompileVersion = JavaVersion.VERSION_11

}