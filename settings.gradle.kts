pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://maven.pkg.github.com/refinedmods/refinedarchitect")
            credentials {
                username = "anything"
                password = "\u0067hp_oGjcDFCn8jeTzIj4Ke9pLoEVtpnZMP4VQgaX"
            }
        }
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
    }
    val refinedarchitectVersion: String by settings
    plugins {
        id("refinedarchitect.root").version(refinedarchitectVersion)
        id("refinedarchitect.base").version(refinedarchitectVersion)
        id("refinedarchitect.common").version(refinedarchitectVersion)
        id("refinedarchitect.neoforge").version(refinedarchitectVersion)
        id("refinedarchitect.fabric").version(refinedarchitectVersion)
    }
}

rootProject.name = "refinedstorage-jei-integration"
include("refinedstorage-jei-integration-common")
include("refinedstorage-jei-integration-neoforge")
include("refinedstorage-jei-integration-fabric")
