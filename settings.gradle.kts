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
include(":core:database")
include(":core:network")
include(":core:ui")
include(":core:navigation")
include(":framework:connectivity")
include(":feature-movies:ui")
include(":feature-movies:domain")
include(":feature-movies:data")
include(":feature-bookmarks:ui")
include(":feature-bookmarks:domain")
include(":feature-tvshows:ui")
include(":feature-tvshows:domain")
include(":feature-tvshows:data")
include(":library:youtubeplayer")
include(":library:customviews")
