import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
   alias(libs.plugins.android.application) apply false
   alias(libs.plugins.android.library) apply false
   alias(libs.plugins.kotlin.android) apply false
   alias(libs.plugins.secrets) apply false
   alias(libs.plugins.kotlin.jvm) apply false
   alias(libs.plugins.hilt) apply false
   alias(libs.plugins.androidx.navigation.safeargs) apply false
   alias(libs.plugins.ben.manes.versions)
}

tasks.register("clean", Delete::class) {
   delete(rootProject.buildDir)
}

fun String.isNonStable(): Boolean {
   val stableKeyword = listOf("RELEASE", "FINAL", "GA").any {
      toUpperCase(java.util.Locale.ROOT).contains(it)
   }
   val regex = "^[0-9,.v-]+(-r)?$".toRegex()
   val isStable = stableKeyword || regex.matches(this)
   return isStable.not()
}

tasks.withType<DependencyUpdatesTask>()