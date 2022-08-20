/**
 * Created by Rahmat Syalim on 2022/08/05
 * rahmatsyalim@gmail.com
 */


@Suppress("DefaultLocale")
fun String.isStable(): Boolean {
   val stableKeyword =
      listOf("RELEASE", "FINAL", "GA", "CANDIDATE").any {
         toUpperCase().contains(it)
      }
   val regex = "^[0-9,.v-]+(-r)?$".toRegex()
   return stableKeyword || regex.matches(this)
}