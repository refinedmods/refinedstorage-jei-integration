package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.platform.common.support.AbstractBaseScreen;

import java.util.List;

import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;

class ExclusionZonesGuiContainerHandler implements IGuiContainerHandler<AbstractBaseScreen<?>> {
    @Override
    public List<Rect2i> getGuiExtraAreas(final AbstractBaseScreen<?> screen) {
        return screen.getExclusionZones();
    }
}
