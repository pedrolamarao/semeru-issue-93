enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
    plugins {
        id("com.google.protobuf") version("0.9.4")
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "semeru-issue-93"

include("api")
include("grpc")