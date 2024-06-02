package com.refinedmods.refinedstorage.jei.common;

import java.util.List;

import com.refinedmods.refinedstorage2.platform.common.support.AbstractBaseScreen;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;

class ExclusionZonesGuiContainerHandler implements IGuiContainerHandler<AbstractBaseScreen<?>> {
    @Override
    public List<Rect2i> getGuiExtraAreas(final AbstractBaseScreen<?> screen) {
        return screen.getExclusionZones();
    }
}
