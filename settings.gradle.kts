pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // NewPipeExtractor is published via JitPack.
        maven("https://jitpack.io")
    }
}

rootProject.name = "YZMusic"

// Android-only build: the app plus the two modules it depends on.
// (desktop, TV, Wear OS app, web and backend were left out of this copy.)
include(":shared")
include(":sharedUi")
include(":app")
