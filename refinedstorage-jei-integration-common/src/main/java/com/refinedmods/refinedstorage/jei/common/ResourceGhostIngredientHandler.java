package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.support.AbstractBaseScreen;
import com.refinedmods.refinedstorage.common.support.containermenu.AbstractResourceContainerMenu;
import com.refinedmods.refinedstorage.common.support.containermenu.ResourceSlot;
import com.refinedmods.refinedstorage.common.support.packet.c2s.C2SPackets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.inventory.Slot;

@SuppressWarnings("rawtypes")
class ResourceGhostIngredientHandler implements IGhostIngredientHandler<AbstractBaseScreen> {
    @Override
    public <I> List<Target<I>> getTargetsTyped(final AbstractBaseScreen screen,
                                               final ITypedIngredient<I> ingredient,
                                               final boolean doStart) {
        if (screen.getMenu() instanceof AbstractResourceContainerMenu resourceMenu) {
            final List<Target<I>> targets = new ArrayList<>();
            RefinedStorageApi.INSTANCE.getIngredientConverter().convertToResource(ingredient.getIngredient())
                .ifPresent(resource -> {
                    for (final ResourceSlot slot : resourceMenu.getResourceSlots()) {
                        if (slot.isActive() && slot.isFilter() && slot.isValid(resource)) {
                            final Rect2i bounds = getBounds(screen, slot);
                            targets.add(new TargetImpl<>(bounds, slot.index));
                        }
                    }
                });
            return targets;
        }
        return Collections.emptyList();
    }

    private static Rect2i getBounds(final AbstractBaseScreen screen, final Slot slot) {
        return new Rect2i(screen.getLeftPos() + slot.x, screen.getTopPos() + slot.y, 17, 17);
    }

    @Override
    public void onComplete() {
        // no op
    }

    private static class TargetImpl<I> implements Target<I> {
        private final Rect2i area;
        private final int slotIndex;

        TargetImpl(final Rect2i area, final int slotIndex) {
            this.area = area;
            this.slotIndex = slotIndex;
        }

        @Override
        public Rect2i getArea() {
            return area;
        }

        @Override
        public void accept(final I ingredient) {
            RefinedStorageApi.INSTANCE.getIngredientConverter().convertToResource(ingredient).ifPresent(
                convertedResource -> C2SPackets.sendResourceFilterSlotChange(convertedResource, slotIndex)
            );
        }
    }
}

