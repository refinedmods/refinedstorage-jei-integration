package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.common.Platform;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;

import static com.refinedmods.refinedstorage.jei.common.Common.MOD_ID;

class PatternGridRecipeTransferError extends AbstractRecipeTransferError {
    private static final List<ClientTooltipComponent> ALL_AUTOCRAFTABLE_TOOLTIP = List.of(
        MOVE_ITEMS,
        createAutocraftableHint(Component.translatable("gui.%s.transfer.all_autocraftable".formatted(MOD_ID)))
    );
    private static final List<ClientTooltipComponent> SOME_AUTOCRAFTABLE_TOOLTIP = List.of(
        MOVE_ITEMS,
        createAutocraftableHint(Component.translatable("gui.%s.transfer.some_autocraftable".formatted(MOD_ID)))
    );

    private final List<IRecipeSlotView> autocraftableSlots;
    private final boolean allAreAutocraftable;

    PatternGridRecipeTransferError(final List<IRecipeSlotView> autocraftableSlots,
                                   final boolean allAreAutocraftable) {
        this.autocraftableSlots = autocraftableSlots;
        this.allAreAutocraftable = allAreAutocraftable;
    }

    @Override
    public int getButtonHighlightColor() {
        return AUTOCRAFTABLE_COLOR;
    }

    @Override
    public Type getType() {
        return Type.COSMETIC;
    }

    @Override
    public void showError(final GuiGraphics graphics,
                          final int mouseX,
                          final int mouseY,
                          final IRecipeSlotsView recipeSlotsView,
                          final int recipeX,
                          final int recipeY) {
        final PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate(recipeX, recipeY, 0);
        autocraftableSlots.forEach(input -> input.drawHighlight(graphics, AUTOCRAFTABLE_COLOR));
        poseStack.popPose();
        Platform.INSTANCE.renderTooltip(
            graphics,
            allAreAutocraftable ? ALL_AUTOCRAFTABLE_TOOLTIP : SOME_AUTOCRAFTABLE_TOOLTIP,
            mouseX,
            mouseY
        );
    }
}
