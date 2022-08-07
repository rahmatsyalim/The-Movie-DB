/**
 * Created by Rahmat Syalim on 2022/08/05
 * rahmatsyalim@gmail.com
 */
class Utils {
   companion object {
      @Suppress("DefaultLocale")
      fun isStable(version: String): Boolean {
         val stableKeyword =
            listOf("RELEASE", "FINAL", "GA", "CANDIDATE").any {
               version.toUpperCase().contains(it)
            }
         val regex = "^[0-9,.v-]+(-r)?$".toRegex()
         return stableKeyword || regex.matches(version)
      }
   }
}