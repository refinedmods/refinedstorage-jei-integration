package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.common.support.AbstractBaseScreen;
import com.refinedmods.refinedstorage.common.support.containermenu.FilterSlot;
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
class FilterGhostIngredientHandler implements IGhostIngredientHandler<AbstractBaseScreen> {
    @Override
    public <I> List<Target<I>> getTargetsTyped(final AbstractBaseScreen screen,
                                               final ITypedIngredient<I> ingredient,
                                               final boolean doStart) {
        final I i = ingredient.getIngredient();
        if (i instanceof ItemStack stack) {
            final List<Target<I>> targets = new ArrayList<>();
            for (final Slot slot : screen.getMenu().slots) {
                if (slot instanceof FilterSlot filterSlot && filterSlot.isActive() && filterSlot.mayPlace(stack)) {
                    final Rect2i bounds = getBounds(screen, filterSlot);
                    targets.add(new TargetImpl<>(bounds, filterSlot.index));
                }
            }
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
            if (ingredient instanceof ItemStack stack) {
                C2SPackets.sendFilterSlotChange(stack, slotIndex);
            }
        }
    }
}

