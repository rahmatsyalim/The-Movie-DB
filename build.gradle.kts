// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
   repositories {
      google()
   }
   dependencies {
      classpath(Dependencies.BuildPlugins.secretsGradle)
      classpath(Dependencies.BuildPlugins.navigationSafeArgs)
      classpath(Dependencies.BuildPlugins.daggerHilt)
   }
}

plugins {
   id("com.android.application") version Dependencies.Versions.agp apply false
   id("com.android.library") version Dependencies.Versions.agp apply false
   kotlin("android") version Dependencies.Versions.kotlin apply false
   kotlin("jvm") version Dependencies.Versions.kotlin apply false
   id ("com.github.ben-manes.versions") version "0.42.0"
}

tasks.register("clean", Delete::class) {
   delete(rootProject.buildDir)
}

fun String.isStable(): Boolean {
   val stableKeyword =
      listOf("RELEASE", "FINAL", "GA","CANDIDATE").any { toUpperCase(java.util.Locale.getDefault()).contains(it) }
   val regex = "^[0-9,.v-]+(-r)?$".toRegex()
   return stableKeyword || regex.matches(this)
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
   rejectVersionIf {
      !candidate.version.isStable()
   }
   checkForGradleUpdate = false
}