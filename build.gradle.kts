plugins {
    id("refinedarchitect.root")
}

refinedarchitect {
    sonarQube("refinedmods_refinedstorage-jei-integration", "refinedmods")
}

subprojects {
    group = "com.refinedmods.refinedstorage"
}
