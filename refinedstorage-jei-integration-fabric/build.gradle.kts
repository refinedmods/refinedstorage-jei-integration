plugins {
    id("refinedarchitect.fabric")
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
        name = "ModMenu"
        url = uri("https://maven.terraformersmc.com/")
    }
    maven {
        name = "Cloth Config"
        url = uri("https://maven.shedaniel.me/")
    }
    maven {
        name = "JEI"
        url = uri("https://maven.blamejared.com/")
    }
}

refinedarchitect {
    modId = "refinedstorage_jei_integration"
    fabric()
    compileWithProject(project(":refinedstorage-jei-integration-common"))
    publishing {
        maven = true
    }
}

base {
    archivesName.set("refinedstorage-jei-integration-fabric")
}

val refinedstorageVersion: String by project
val jeiVersion: String by project
val minecraftVersion: String by project

dependencies {
    modApi("com.refinedmods.refinedstorage:refinedstorage-platform-fabric:${refinedstorageVersion}")
    modRuntimeOnly("mezz.jei:jei-${minecraftVersion}-fabric:${jeiVersion}")
    modCompileOnlyApi("mezz.jei:jei-${minecraftVersion}-common-api:${jeiVersion}")
    modCompileOnlyApi("mezz.jei:jei-${minecraftVersion}-fabric-api:${jeiVersion}")
}
