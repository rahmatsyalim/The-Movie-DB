pluginManagement {
   repositories {
      gradlePluginPortal()
      google()
      mavenCentral()
      includeBuild("build-logic")
   }
}
dependencyResolutionManagement {
   repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
   repositories {
      google()
      mavenCentral()
   }
}
rootProject.name = "themoviedb"

include(":app")
include(":core:common")
include(":data:repository")
include(":data:local")
include(":data:remote")
include(":data:connectivity")
include(":domain:movie")
include(":domain:connectivity")
