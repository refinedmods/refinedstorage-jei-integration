plugins {
    id("com.refinedmods.refinedarchitect.neoforge")
}

repositories {
    maven {
        name = "Refined Storage"
        url = uri("https://maven.creeperhost.net")
        content {
            includeGroup("com.refinedmods.refinedstorage")
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
    publishing {
        maven = true
        curseForge = "1230497"
        curseForgeRequiredDependencies = listOf("refined-storage", "jei")
        modrinth = "VzR5wiLo"
        modrinthRequiredDependencies = listOf("refined-storage", "jei")
    }
}

base {
    archivesName.set("refinedstorage-jei-integration-neoforge")
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
    api("com.refinedmods.refinedstorage:refinedstorage-neoforge:${refinedstorageVersion}")
    runtimeOnly("mezz.jei:jei-${minecraftVersion}-neoforge:${jeiVersion}")
    compileOnlyApi("mezz.jei:jei-${minecraftVersion}-common-api:${jeiVersion}")
    testCompileOnly("mezz.jei:jei-${minecraftVersion}-common:${jeiVersion}")
    compileOnlyApi("mezz.jei:jei-${minecraftVersion}-neoforge-api:${jeiVersion}")
    compileOnlyApi("com.refinedmods.refinedstorage:refinedstorage-quartz-arsenal-neoforge:${refinedstorageQuartzArsenalVersion}")
    // runtimeOnly("com.refinedmods.refinedstorage:refinedstorage-quartz-arsenal-neoforge:${refinedstorageQuartzArsenalVersion}")
}
