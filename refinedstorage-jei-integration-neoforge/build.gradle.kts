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
}
