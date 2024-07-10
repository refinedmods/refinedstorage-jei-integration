plugins {
    id("refinedarchitect.root")
    id("refinedarchitect.base")
}

refinedarchitect {
    sonarQube("refinedmods_refinedstorage-jei-integration", "refinedmods")
}

subprojects {
    group = "com.refinedmods.refinedstorage"
}
