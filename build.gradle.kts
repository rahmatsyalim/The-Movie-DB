// Top-level build file where you can add configuration options common to all sub-projects/modules.
import Utils.isStable
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
   dependencies {
      classpath(Libs.Navigation.safeArgsGradlePlugin)
      classpath(Libs.DaggerHilt.gradlePlugin)
      classpath(Libs.secretsGradlePlugin)
   }
}

plugins {
   id(Libs.Android.application) version Versions.android apply false
   id(Libs.Android.library) version  Versions.android apply false
   id(Libs.Kotlin.android) version Versions.kotlin apply false
   id(Libs.benManesVersions) version Versions.benManes apply true
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