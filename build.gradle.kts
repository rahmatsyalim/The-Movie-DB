// Top-level build file where you can add configuration options common to all sub-projects/modules.
import Utils.Companion.isStable
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
   dependencies {
      classpath(BuildClasspath.navigationSafeArgs)
      classpath(BuildClasspath.daggerHilt)
      classpath(BuildClasspath.secretsGradle)
   }
}

plugins {
   id(BuildPlugins.agpApplication) version Versions.agp apply false
   id(BuildPlugins.agpLibrary) version Versions.agp apply false
   id(BuildPlugins.kotlinAndroid) version Versions.kotlin apply false
   id(BuildPlugins.benManesVersions) version Versions.benManesVersions apply true
}

tasks.register("clean", Delete::class) {
   delete(rootProject.buildDir)
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
   rejectVersionIf {
      isStable(candidate.version).not()
   }
   checkForGradleUpdate = false
}