dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/refinedmods/refinedarchitect")
            credentials {
                username = "anything"
                password = "\u0067hp_oGjcDFCn8jeTzIj4Ke9pLoEVtpnZMP4VQgaX"
            }
        }
    }
    versionCatalogs {
        create("libs") {
            from("com.refinedmods.refinedarchitect:refinedarchitect-versioning:0.16.2")
        }
    }
}

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
        maven {
            name = "NeoForge"
            url = uri("https://maven.neoforged.net/releases")
        }
    }
    plugins {
        id("refinedarchitect.root").version("0.16.2")
        id("refinedarchitect.base").version("0.16.2")
        id("refinedarchitect.common").version("0.16.2")
        id("refinedarchitect.neoforge").version("0.16.2")
        id("refinedarchitect.fabric").version("0.16.2")
    }
}

rootProject.name = "refinedstorage-jei-integration"
include("refinedstorage-jei-integration-common")
include("refinedstorage-jei-integration-neoforge")
include("refinedstorage-jei-integration-fabric")
