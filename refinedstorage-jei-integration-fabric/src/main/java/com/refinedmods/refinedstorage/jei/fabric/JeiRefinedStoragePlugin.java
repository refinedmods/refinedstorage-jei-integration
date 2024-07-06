package com.refinedmods.refinedstorage.jei.fabric;

import com.refinedmods.refinedstorage.platform.api.PlatformApi;
import com.refinedmods.refinedstorage.platform.api.RefinedStoragePlugin;

import static com.refinedmods.refinedstorage.jei.common.Common.init;

public class JeiRefinedStoragePlugin implements RefinedStoragePlugin {
    @Override
    public void onPlatformApiAvailable(final PlatformApi platformApi) {
        init(platformApi);
    }
}
