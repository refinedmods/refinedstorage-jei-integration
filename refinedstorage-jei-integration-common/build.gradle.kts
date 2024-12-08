plugins {
    id("refinedarchitect.common")
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
    common()
    publishing {
        maven = true
    }
}

base {
    archivesName.set("refinedstorage-jei-integration-common")
}

val refinedstorageVersion: String by project
val refinedstorageQuartzArsenalVersion: String by project
val jeiVersion: String by project
val minecraftVersion: String by project

dependencies {
    api("com.refinedmods.refinedstorage:refinedstorage-common:${refinedstorageVersion}")
    api("mezz.jei:jei-${minecraftVersion}-common-api:${jeiVersion}")
    api("mezz.jei:jei-${minecraftVersion}-common:${jeiVersion}")
    compileOnlyApi("com.refinedmods.refinedstorage:refinedstorage-quartz-arsenal-common:${refinedstorageQuartzArsenalVersion}")
}
