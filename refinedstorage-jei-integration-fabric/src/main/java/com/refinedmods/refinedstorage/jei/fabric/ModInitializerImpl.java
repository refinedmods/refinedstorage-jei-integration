package com.refinedmods.refinedstorage.jei.fabric;

import com.refinedmods.refinedstorage.jei.common.Common;

import net.fabricmc.api.ModInitializer;

public class ModInitializerImpl implements ModInitializer {
    @Override
    public void onInitialize() {
        Common.setPlatform(new FabricPlatform());
    }
}
