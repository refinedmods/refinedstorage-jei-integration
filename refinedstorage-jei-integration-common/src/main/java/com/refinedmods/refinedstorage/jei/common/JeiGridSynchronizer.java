package com.refinedmods.refinedstorage.jei.common;

import javax.annotation.Nullable;

import com.refinedmods.refinedstorage2.platform.common.grid.AbstractGridSynchronizer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import static com.refinedmods.refinedstorage.jei.common.Common.MOD_ID;

class JeiGridSynchronizer extends AbstractGridSynchronizer {
    private static final MutableComponent TITLE = Component.translatable("gui.%s.grid.synchronizer".formatted(MOD_ID));
    private static final MutableComponent TITLE_TWO_WAY = Component.translatable(
        "gui.%s.grid.synchronizer.two_way".formatted(MOD_ID)
    );
    private static final Component HELP = Component.translatable("gui.%s.grid.synchronizer.help".formatted(MOD_ID));
    private static final Component HELP_TWO_WAY = Component.translatable(
        "gui.%s.grid.synchronizer.two_way.help".formatted(MOD_ID)
    );

    private final JeiProxy jeiProxy;
    private final boolean twoWay;

    JeiGridSynchronizer(final JeiProxy jeiProxy, final boolean twoWay) {
        this.jeiProxy = jeiProxy;
        this.twoWay = twoWay;
    }

    @Override
    public MutableComponent getTitle() {
        return twoWay ? TITLE_TWO_WAY : TITLE;
    }

    @Override
    public Component getHelpText() {
        return twoWay ? HELP_TWO_WAY : HELP;
    }

    @Override
    public void synchronizeFromGrid(final String text) {
        jeiProxy.setSearchFieldText(text);
    }

    @Override
    @Nullable
    public String getTextToSynchronizeToGrid() {
        return twoWay ? jeiProxy.getSearchFieldText() : null;
    }

    @Override
    public int getXTexture() {
        return twoWay ? 32 : 48;
    }
}
