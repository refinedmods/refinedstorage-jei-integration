package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.support.AbstractBaseContainerMenu;
import com.refinedmods.refinedstorage.common.support.AbstractBaseScreen;
import com.refinedmods.refinedstorage.common.support.containermenu.AbstractResourceContainerMenu;
import com.refinedmods.refinedstorage.common.support.containermenu.FilterSlot;
import com.refinedmods.refinedstorage.common.support.containermenu.ResourceSlot;
import com.refinedmods.refinedstorage.common.support.packet.c2s.C2SPackets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("rawtypes")
class GhostIngredientHandler implements IGhostIngredientHandler<AbstractBaseScreen> {
    @Override
    public <I> List<Target<I>> getTargetsTyped(final AbstractBaseScreen screen,
                                               final ITypedIngredient<I> ingredient,
                                               final boolean doStart) {
        if (screen.getMenu() instanceof AbstractBaseContainerMenu menu) {
            return getTargets(screen, ingredient.getIngredient(), menu);
        }
        return Collections.emptyList();
    }

    private <I> List<Target<I>> getTargets(final AbstractBaseScreen screen,
                                           final I ingredient,
                                           final AbstractBaseContainerMenu menu) {
        final List<Target<I>> targets = new ArrayList<>();
        addResourceTargets(screen, ingredient, menu, targets);
        addFilterTargets(screen, ingredient, menu, targets);
        return targets;
    }

    private <I> void addResourceTargets(final AbstractBaseScreen screen,
                                        final I ingredient,
                                        final AbstractBaseContainerMenu menu,
                                        final List<Target<I>> targets) {
        if (menu instanceof AbstractResourceContainerMenu resourceMenu) {
            RefinedStorageApi.INSTANCE.getIngredientConverter().convertToResource(ingredient).ifPresent(resource -> {
                for (final ResourceSlot slot : resourceMenu.getResourceSlots()) {
                    if (slot.isActive() && slot.isFilter() && slot.isValid(resource)) {
                        final Rect2i bounds = getBounds(screen, slot);
                        targets.add(new TargetImpl<>(bounds, slot.index, true));
                    }
                }
            });
        }
    }

    private <I> void addFilterTargets(final AbstractBaseScreen screen,
                                      final I ingredient,
                                      final AbstractBaseContainerMenu menu,
                                      final List<Target<I>> targets) {
        if (ingredient instanceof ItemStack stack) {
            for (final Slot slot : menu.slots) {
                if (slot instanceof FilterSlot filterSlot && filterSlot.isActive() && filterSlot.mayPlace(stack)) {
                    final Rect2i bounds = getBounds(screen, filterSlot);
                    targets.add(new TargetImpl<>(bounds, filterSlot.index, false));
                }
            }
        }
    }

    private Rect2i getBounds(final AbstractBaseScreen screen, final Slot slot) {
        return new Rect2i(screen.getLeftPos() + slot.x, screen.getTopPos() + slot.y, 17, 17);
    }

    @Override
    public void onComplete() {
        // no op
    }

    private static class TargetImpl<I> implements Target<I> {
        private final Rect2i area;
        private final int slotIndex;
        private final boolean resource;

        TargetImpl(final Rect2i area, final int slotIndex, final boolean resource) {
            this.area = area;
            this.slotIndex = slotIndex;
            this.resource = resource;
        }

        @Override
        public Rect2i getArea() {
            return area;
        }

        @Override
        public void accept(final I ingredient) {
            if (resource) {
                RefinedStorageApi.INSTANCE.getIngredientConverter().convertToResource(ingredient).ifPresent(
                    convertedResource -> C2SPackets.sendResourceFilterSlotChange(convertedResource, slotIndex)
                );
            } else if (ingredient instanceof ItemStack stack) {
                C2SPackets.sendFilterSlotChange(stack, slotIndex);
            }
        }
    }
}

