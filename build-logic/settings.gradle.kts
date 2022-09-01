dependencyResolutionManagement {
   repositories {
      google()
      mavenCentral()
   }
   versionCatalogs {
      create("libs") {
         from(files("../gradle/libs.versions.toml"))
      }
   }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include("convention-plugins")