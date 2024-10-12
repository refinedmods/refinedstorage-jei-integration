package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.common.Platform;
import com.refinedmods.refinedstorage.common.support.tooltip.HelpClientTooltipComponent;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;

import static com.refinedmods.refinedstorage.jei.common.Common.MOD_ID;

class CraftingGridRecipeTransferError extends AbstractRecipeTransferError {
    private static final Color MISSING_COLOR = new Color(1.0f, 0.0f, 0.0f, 0.4f);
    private static final ClientTooltipComponent MISSING_ITEMS = ClientTooltipComponent.create(
        Component.translatable("jei.tooltip.error.recipe.transfer.missing")
            .withStyle(ChatFormatting.RED)
            .getVisualOrderText());
    private static final List<ClientTooltipComponent> MISSING_TOOLTIP = List.of(MOVE_ITEMS, MISSING_ITEMS);
    private static final Component CTRL_CLICK_TO_AUTOCRAFT = Component.translatable(
        "gui.%s.transfer.ctrl_click_to_autocraft".formatted(MOD_ID)
    );

    private final List<TransferInput> transferInputs;
    private final TransferType type;

    CraftingGridRecipeTransferError(final List<TransferInput> transferInputs, final TransferType type) {
        this.transferInputs = transferInputs;
        this.type = type;
    }

    @Override
    public Type getType() {
        return Type.COSMETIC;
    }

    @Override
    public int getButtonHighlightColor() {
        return switch (type) {
            case MISSING -> MISSING_COLOR.getRGB();
            case MISSING_BUT_ALL_AUTOCRAFTABLE, MISSING_BUT_SOME_AUTOCRAFTABLE -> AUTOCRAFTABLE_COLOR;
            default -> 0;
        };
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
        transferInputs.forEach(input -> drawInputHighlight(graphics, input));
        poseStack.popPose();
        Platform.INSTANCE.renderTooltip(graphics, calculateTooltip(), mouseX, mouseY);
    }

    private List<ClientTooltipComponent> calculateTooltip() {
        return switch (type) {
            case MISSING -> MISSING_TOOLTIP;
            case MISSING_BUT_ALL_AUTOCRAFTABLE -> List.of(
                MOVE_ITEMS,
                createAutocraftableHint(
                    Component.translatable("gui.%s.transfer.missing_but_all_autocraftable".formatted(MOD_ID))
                ),
                HelpClientTooltipComponent.createAlwaysDisplayed(CTRL_CLICK_TO_AUTOCRAFT)
            );
            case MISSING_BUT_SOME_AUTOCRAFTABLE -> List.of(
                MOVE_ITEMS,
                createAutocraftableHint(
                    Component.translatable("gui.%s.transfer.missing_but_some_autocraftable".formatted(MOD_ID))
                ),
                HelpClientTooltipComponent.createAlwaysDisplayed(CTRL_CLICK_TO_AUTOCRAFT)
            );
            default -> Collections.emptyList();
        };
    }

    private static void drawInputHighlight(final GuiGraphics graphics, final TransferInput input) {
        switch (input.type()) {
            case MISSING -> input.view().drawHighlight(graphics, MISSING_COLOR.getRGB());
            case AUTOCRAFTABLE -> input.view().drawHighlight(graphics, AUTOCRAFTABLE_COLOR);
        }
    }
}
