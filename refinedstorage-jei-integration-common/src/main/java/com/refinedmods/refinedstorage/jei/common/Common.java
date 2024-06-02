package com.refinedmods.refinedstorage.jei.common;

import static java.util.Objects.requireNonNull;

public final class Common {
    public static final String MOD_ID = "refinedstorage_jei_integration";

    private static Platform platform;

    private Common() {
    }

    public static void setPlatform(final Platform platform) {
        Common.platform = platform;
    }

    public static Platform getPlatform() {
        return requireNonNull(platform, "Platform isn't set yet");
    }
}
