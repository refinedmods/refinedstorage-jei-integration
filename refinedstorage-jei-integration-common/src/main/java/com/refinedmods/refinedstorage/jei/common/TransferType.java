package com.refinedmods.refinedstorage.jei.common;

enum TransferType {
    AVAILABLE,
    MISSING,
    MISSING_BUT_ALL_AUTOCRAFTABLE,
    MISSING_BUT_SOME_AUTOCRAFTABLE;

    boolean canOpenAutocraftingPreview() {
        return this == TransferType.MISSING_BUT_ALL_AUTOCRAFTABLE
            || this == TransferType.MISSING_BUT_SOME_AUTOCRAFTABLE;
    }
}
