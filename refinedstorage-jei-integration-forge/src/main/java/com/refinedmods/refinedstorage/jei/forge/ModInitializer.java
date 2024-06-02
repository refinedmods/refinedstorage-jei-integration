package com.refinedmods.refinedstorage.jei.forge;

import com.refinedmods.refinedstorage.jei.common.Common;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Common.MOD_ID)
public class ModInitializer {
    public ModInitializer(final IEventBus eventBus) {
        Common.setPlatform(new ForgePlatform());
    }
}
