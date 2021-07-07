pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        jcenter()
    }
}

rootProject.name = ("TranslationSheet")

include(":example")
includeBuild("plugin-build")
