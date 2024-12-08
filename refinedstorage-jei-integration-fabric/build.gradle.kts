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
    publishing {
        maven = true
    }
}

base {
    archivesName.set("refinedstorage-jei-integration-fabric")
}

val refinedstorageVersion: String by project
val refinedstorageQuartzArsenalVersion: String by project
val jeiVersion: String by project
val minecraftVersion: String by project

val commonJava by configurations.existing
val commonResources by configurations.existing

dependencies {
    compileOnly(project(":refinedstorage-jei-integration-common"))
    commonJava(project(path = ":refinedstorage-jei-integration-common", configuration = "commonJava"))
    commonResources(project(path = ":refinedstorage-jei-integration-common", configuration = "commonResources"))
    modApi("com.refinedmods.refinedstorage:refinedstorage-fabric:${refinedstorageVersion}")
    modRuntimeOnly("mezz.jei:jei-${minecraftVersion}-fabric:${jeiVersion}")
    modCompileOnlyApi("mezz.jei:jei-${minecraftVersion}-common-api:${jeiVersion}")
    modCompileOnlyApi("mezz.jei:jei-${minecraftVersion}-common:${jeiVersion}")
    modCompileOnlyApi("mezz.jei:jei-${minecraftVersion}-fabric-api:${jeiVersion}")
    modCompileOnlyApi("com.refinedmods.refinedstorage:refinedstorage-quartz-arsenal-fabric:${refinedstorageQuartzArsenalVersion}")
    // modRuntimeOnly("com.refinedmods.refinedstorage:refinedstorage-quartz-arsenal-fabric:${refinedstorageQuartzArsenalVersion}")
}
