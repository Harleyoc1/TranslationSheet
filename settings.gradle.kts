pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = ("TranslationSheet")

include(":example")
includeBuild("plugin-build")
