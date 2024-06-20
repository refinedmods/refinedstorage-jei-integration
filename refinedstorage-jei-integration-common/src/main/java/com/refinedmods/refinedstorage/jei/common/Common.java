package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage2.platform.api.PlatformApi;
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

    public static void init(final PlatformApi platformApi) {
        platformApi.addIngredientConverter(new JeiRecipeModIngredientConverter());
        platformApi.getGridSynchronizerRegistry().register(
            new ResourceLocation(Common.MOD_ID, "jei"),
            new JeiGridSynchronizer(false)
        );
        platformApi.getGridSynchronizerRegistry().register(
            new ResourceLocation(Common.MOD_ID, "jei_two_way"),
            new JeiGridSynchronizer(true)
        );
    }
}
