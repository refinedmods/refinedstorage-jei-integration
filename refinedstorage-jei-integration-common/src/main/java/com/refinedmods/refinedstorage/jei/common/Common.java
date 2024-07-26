package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;

import net.minecraft.resources.ResourceLocation;

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

    public static void init(final RefinedStorageApi api) {
        api.addIngredientConverter(new JeiRecipeModIngredientConverter());
        api.getGridSynchronizerRegistry().register(
            ResourceLocation.fromNamespaceAndPath(Common.MOD_ID, "jei"),
            new JeiGridSynchronizer(false)
        );
        api.getGridSynchronizerRegistry().register(
            ResourceLocation.fromNamespaceAndPath(Common.MOD_ID, "jei_two_way"),
            new JeiGridSynchronizer(true)
        );
    }
}
