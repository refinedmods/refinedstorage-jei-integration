plugins {
    id("com.refinedmods.refinedarchitect.root")
    id("com.refinedmods.refinedarchitect.base")
}

refinedarchitect {
    sonarQube("refinedmods_refinedstorage-jei-integration", "refinedmods")
}

subprojects {
    group = "com.refinedmods.refinedstorage"
}
