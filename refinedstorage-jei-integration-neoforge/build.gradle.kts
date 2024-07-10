plugins {
    id("refinedarchitect.neoforge")
}

repositories {
    maven {
        url = uri("https://maven.pkg.github.com/refinedmods/refinedstorage2")
        credentials {
            username = "anything"
            password = "\u0067hp_oGjcDFCn8jeTzIj4Ke9pLoEVtpnZMP4VQgaX"
        }
    }
    maven {
        name = "JEI"
        url = uri("https://maven.blamejared.com/")
    }
}

refinedarchitect {
    modId = "refinedstorage_jei_integration"
    neoForge()
    compileWithProject(project(":refinedstorage-jei-integration-common"))
    publishing {
        maven = true
    }
}

base {
    archivesName.set("refinedstorage-jei-integration-neoforge")
}

val refinedstorageVersion: String by project
val jeiVersion: String by project
val minecraftVersion: String by project

dependencies {
    api("com.refinedmods.refinedstorage:refinedstorage-platform-neoforge:${refinedstorageVersion}")
    runtimeOnly("mezz.jei:jei-${minecraftVersion}-neoforge:${jeiVersion}")
    compileOnlyApi("mezz.jei:jei-${minecraftVersion}-common-api:${jeiVersion}")
    compileOnlyApi("mezz.jei:jei-${minecraftVersion}-neoforge-api:${jeiVersion}")
}
